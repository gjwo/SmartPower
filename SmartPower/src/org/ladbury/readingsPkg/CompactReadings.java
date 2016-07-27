package org.ladbury.readingsPkg;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.ladbury.smartpowerPkg.Timestamped;
import org.ladbury.smartpowerPkg.Utility;

public class CompactReadings
	extends Vector <CompactReading>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static enum  ReadingStatus {EMPTY,NOT_EMPTY,COMPRESSED,DELTAS_CALCULATED};

	private String 			monitorName;
	private Date			firstTimestamp;
	private Date			lastTimestamp;
	private ReadingStatus	status;
	private int				edge;
	
    // Vector will contain the readings

    //
    // constructor
    //
    public CompactReadings() {
        super(100, 10);
       monitorName = null;
       firstTimestamp = null;
       lastTimestamp = null;
       status = ReadingStatus.EMPTY;
       edge = 0;
    }

    //
    // Add a reading to the vector, at the end
    //
    public boolean add(CompactReading reading) {
    	 super.addElement(reading);
        lastTimestamp = reading.timestamp();
        if (this.firstTimestamp == null) this.firstTimestamp = reading.timestamp();
        status = ReadingStatus.NOT_EMPTY;
        return true;
    }
    //
    // remove single readings between two identical readings
    // and replace with the surrounding values
    //
    protected void squelchTransients() {
    	int i;
    	double d1,d2;
    	
     	if (status!=ReadingStatus.NOT_EMPTY) return;
    	for ( i = 0; i < this.size()-3; i++){
    		d1= this.elementAt(i).reading();
    		d2=this.elementAt(i+2).reading();
    	   if (Utility.equal(d1,d2)){
    		   this.elementAt(i+1).setReading(d1);
    	   }
    	}	
    }
    //
    // Change slopes to cliffs by eliminating
    // the transitional values 
    // must be done before compacting!
    //
    public void squelchTransitions() {
    	int i;
    	double d1,d2,d3;
     	
     	if (status!=ReadingStatus.NOT_EMPTY) return;
    	for ( i = 0; i < this.size()-3; i++){
    		d1= this.elementAt(i).reading();
    		d2= this.elementAt(i+1).reading();
    		d3= this.elementAt(i+2).reading();
    	   if ((d1<d2) && (d2<d3)){ //upwards slope
    		   this.elementAt(i+1).setReading(d3); //emphasise peak
    	   }
    	   if ((d1>d2) && (d2>d3)){ //downwards slope
    		   this.elementAt(i+1).setReading(d1);//emphasise peak
    	   }
    	}	
    }

    //
    // compact the readings by recording counts of adjacent 
    // equal readings and removing the duplicates
    // compute in reverse order to avoid index confusion
    // vector elements start from 0 and go to size-1
    //
    public void compactTheReadings() {
    	int i;
     	
    	for ( i = this.size()-2; i >= 0; i--){
    	   if (Utility.matches(this.elementAt(i).reading(),this.elementAt(i+1).reading())){
    		   this.elementAt(i).addIntervals(this.elementAt(i+1).intervals());
    		   this.removeElementAt(i+1);
    	   }
    	}	
    	status = ReadingStatus.COMPRESSED;
    }
    //
    // this procedure fills in the deltas and the watts for each element in the vector
    //
    public void calculateDeltas() {
    	int i;
     	
    	for ( i = 1 ; i < this.size(); i++){
    	   this.elementAt(i).setConsumption(this.elementAt(i).reading()-this.elementAt(i-1).reading());
    	}	
    	status = ReadingStatus.DELTAS_CALCULATED;
    }
    //
    // this procedure finds a clean edge event in the readings, 
    // i.e. one that was stable before and after, not a glitch or transition
    // this is true if both readings have an interval count of 2 or more
    //
    public CompactReading firstEdge(){
    	int i;
    	CompactReading cr = null;
     	
    	if (status == ReadingStatus.DELTAS_CALCULATED){ //check it is safe to proceed
    		for ( i = 1; i < this.size(); i++){
    			if ((this.elementAt(i-1).intervals()>=2) & (this.elementAt(i).intervals()>=2)){ // we have two stable levels
    				cr = this.elementAt(i);
    				edge = i;
    				break; // found one so bail out
    			}
    		}
    	}
	return cr;
    }
    //
    // this procedure finds the next clean edge event in the readings, 
    // i.e. one that was stable before and after, not a glitch or transition
    // this is true if both readings have an interval count of 2 or more
    //
    public CompactReading nextEdge(){
    	int i;
    	CompactReading cr = null;
     	
    	if (edge > 0 ){ //check it is safe to proceed if value > 0 must be at DELTAS_CALCULATED
    		for ( i = edge+1; i < this.size()-1; i++){
    			if ((this.elementAt(i-1).intervals()>=2) & (this.elementAt(i).intervals()>=2)){ // we have two stable levels
    				cr = this.elementAt(i);
    				edge = i;
    				break; // found one so bail out
    			}
    		}
    	}
	return cr;
    }
 
    //
    // output the entire vector (Comma Separated Variables)
    //
    public void outputCSV(PrintWriter pw) {
    	Iterator <CompactReading> i;
    	
    	i = this.iterator(); 
    	while(i.hasNext()) 
    		i.next().outputCSV(pw);       
    }

    //
    // construct a systematic name for
    // this set of readings
    //
    protected String readingsName(){
		DateFormat df = new SimpleDateFormat(Timestamped.COMPACTDATEFORMAT);
		String s = "";
    	for( int i =0; i < monitorName.length(); i++){
    	    if( monitorName.charAt(i) != '"') s += monitorName.charAt(i);
        }  	  
    	return s+"_"+df.format(firstTimestamp)+"-"+df.format(lastTimestamp);
    }
    
    //
    // Access the name of the device
    // common to all readings
    //
    protected String monitorName(){
    	return this.monitorName;
    }
    
    //
    // Set the name of the device
    // common to all readings
    //
    public void setMonitorName(String mn){
    	this.monitorName = mn;
    }
    //
    // Access the start time of the recorded readings
    //
    protected Date earliest(){
    	return this.firstTimestamp;
    }

    //
    //  Access the end time of the recorded readings
    //
    protected Date latest(){
    	return this.lastTimestamp;
    }

    //
    // Access the current status
    //
     protected ReadingStatus status(){
    	return this.status;
    }
}
