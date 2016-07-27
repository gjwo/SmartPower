package org.ladbury.readingEventPkg;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.ladbury.abodePkg.Abode;
import org.ladbury.persistentData.Persistable;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.smartpowerPkg.Timestamped;

import java.util.Comparator;
/**
* ReadingEvent
* This class records an event or edge i.e a change in level in recorded power usage.
* simplest case is a single device has changed state (on or off), but given the sample
* time (e.g. 1 minute) and typical human behaviour it is likely that several things have
* happened at once. Typical scenario leave a room, turn off light, enter next room turn
* on a light and other equipment (kettle, TV etc), and in the sample the fridge compressor
* turns itself on autonomously!
 * 
 * @author GJWood
 * @version 1.1 2012/11/29 changed to be incorporated in a PersistentList
 * @see PersistentList
 * @see ReadingEvents
 */
@Entity
public class ReadingEvent implements 	Serializable,
										Comparator<ReadingEvent>,
										Comparable<ReadingEvent>,
										Persistable<ReadingEvent>,
										Timestamped<ReadingEvent>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5518371779878152294L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long		eventId;
	@Basic
	private Timestamp	timestamp;
	@Basic
	private int			consumption;
	@Basic
	private boolean 	onEdge; // true = an on transition, false = an off transition
	@ManyToOne
	private Abode abode;
	//
    // constructor used for initial set up from readings
    //
	public ReadingEvent(){
		timestamp=new Timestamp(0);
		consumption=0;
		onEdge=false;
		abode = null;
	}
    public ReadingEvent(Timestamp ts, int w){
    	timestamp = ts;
    	onEdge = w>0;
    	consumption = Math.abs(w);
		abode = null;
   }

    //
    // Output device event data in CSV format
    //
    public void outputDeviceEventCSV(PrintWriter pw){
		pw.printf("%s,%d,%b,%d", timestampString(), eventId, onEdge, consumption);
		pw.println();
	}

	//
    // access procedures
    //
    public int consumption(){
    	return this.consumption;
    }
	public void setConsumption(int c) {
		this.consumption = c;
		
	}
    public boolean isOn(){
    	return onEdge;
    }
    public void setEdge(boolean on){
    	this.onEdge = on;
    }
    public Timestamp timestamp(){
		return timestamp;
	}
    public void setTimestamp(Timestamp t){
		this.timestamp = t;
	}
    public String timestampString(){
		DateFormat df = new SimpleDateFormat(Timestamped.OUTPUTDATEFORMAT);
		return df.format(timestamp);
	}
	public Abode getAbode() {
	    return abode;
	}
	public void setAbode(Abode param) {
		this.abode = param;
	}
	//
	// Methods supporting interfaces
	//
	@Override
	public int compareTo(ReadingEvent re) {
		return this.timestamp.compareTo(re.timestamp());
	}
	@Override
	public int compare(ReadingEvent o1, ReadingEvent o2) {
		return o1.timestamp.compareTo(o2.timestamp);
	}
	@Override
	public boolean happenedBetween(Timestamp ts1, Timestamp ts2) {
		return ((timestamp().compareTo(ts1)>=0) && (timestamp().compareTo(ts2)<=0)  );
	}
	@Override
	public void updateFields(ReadingEvent re) {
    	timestamp = re.timestamp;
    	onEdge = re.onEdge;
    	consumption = re.consumption;
		abode = re.abode;
	}
    @Override
	public String toCSV(){
		return( this.eventId+","
				+this.timestamp+","
				+this.consumption+","
				+this.onEdge+","
				+this.abode);
	}
    @Override
	public String name(){
		return new String(timestampString()+"("+consumption+")");
	}
    public long id(){
    	return eventId;
    }
	@Override
	public String idString(){
		return new String("["+this.id()+"] "+this.name());
	}

}
