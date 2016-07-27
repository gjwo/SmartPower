package org.ladbury.readingEventPkg;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ladbury.persistentData.PersistentList;
import org.ladbury.smartpowerPkg.Timestamped;

public class ReadingEvents extends PersistentList<ReadingEvent>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4616680846040591059L;
	protected static enum 		DeviceEventStatus {EMPTY,POPULATED};

	private Timestamp 			firstTimestamp;
	private Timestamp 			lastTimestamp;
	private DeviceEventStatus 	status;
	private long				eventIdFromTimestampMatch;
	private int					eventRowFromTimestampMatch;

    //
    // constructors
    //
    public ReadingEvents() {
    	super(new ArrayList<ReadingEvent>(Collections.<ReadingEvent>emptyList()));
        firstTimestamp = null;
        lastTimestamp = null;
        status = DeviceEventStatus.EMPTY;
	}

    //
    // Load events from database, and set summary variables
    //
 	public ReadingEvents(List<ReadingEvent> resultList) { 
    	super(resultList);
    	
        firstTimestamp = null;
        lastTimestamp = null;
        eventIdFromTimestampMatch = -1;
        eventRowFromTimestampMatch = -1;
        status = DeviceEventStatus.EMPTY;
        if(super.size()>0){
        	firstTimestamp = super.get(0).timestamp();
        	lastTimestamp = super.get(super.size()-1).timestamp();
            status = DeviceEventStatus.POPULATED;
        }
        //Collections.sort(this);
	}

    //
    // Add a device event to the list, at the end
    //
    public boolean add(ReadingEvent re) {
    	if (!isTimestampUsed( re.timestamp())){
    		status = DeviceEventStatus.POPULATED;
    		super.add(re);
    		if (this.firstTimestamp == null) this.firstTimestamp = re.timestamp();
    		if (this.lastTimestamp == null) this.lastTimestamp = re.timestamp();
    		if (re.timestamp().before(this.firstTimestamp))this.firstTimestamp = re.timestamp();
    		if (re.timestamp().after(this.lastTimestamp))this.lastTimestamp = re.timestamp();
    		return true;
    	} else return false;
    }
	    
    //
    // output the entire list (Comma Separated Variables)
    //
    public void outputCSV(PrintWriter pw) {
    	Iterator <ReadingEvent> i = super.iterator();
    	while(i.hasNext()) 
    		i.next().outputDeviceEventCSV(pw);       
    }
    
    public String eventsName(){
    	if (super.size()==0) return "Empty";
		DateFormat df = new SimpleDateFormat(Timestamped.COMPACTDATEFORMAT);
    	return df.format(firstTimestamp)+"-"+df.format(lastTimestamp);
    }

    //
    // access procedures
    //
     
    public Timestamp earliest(){
    	return firstTimestamp;
    }
    public Timestamp latest(){
    	return lastTimestamp;
    }
    public boolean isTimestampUsed(Timestamp t){
     	if (firstTimestamp==null) return false;
    	if (t.before(firstTimestamp) || t.after(lastTimestamp)) return false; 
    	for(int i=0; i<this.size();i++){
    		if (this.get(i).timestamp().equals(t)){
    			eventIdFromTimestampMatch = this.get(i).id();
    			eventRowFromTimestampMatch = i; 
    			return true;
    		}
    		if (t.after(this.get(i).timestamp())) break;
    	} 
    	eventIdFromTimestampMatch = -1;
    	eventRowFromTimestampMatch = -1; 
    	return false;
    }
    public int getMatchedTimestampRow(){
    	return eventRowFromTimestampMatch;
    }
    public Long getMatchedTimestampId(){
    	return eventIdFromTimestampMatch;
    }
    public DeviceEventStatus getStatus(){
    	return status;
    }
}	