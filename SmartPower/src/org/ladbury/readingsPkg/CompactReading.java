package org.ladbury.readingsPkg;
import java.sql.Timestamp;
import java.io.PrintWriter;

//
//Compact TimedReading
//
//This class manages a time stamped set of reading values from a file of readings
//produced by an energy monitor or smart meter that can be processed into a more
//compact format, by storing a count of adjacent measurements with the same value
//rather than repeating the entry.
//
public class CompactReading extends TimedReading{
	
	protected static final int  VOLTAGE = 230;
	protected static final int  SECONDS = 60;
	
	//private	String	m_monitor; //held in super
	//private Date	m_timestamp;  //held in super
	//private Double	m_Amps_Raw_Data; // held in super
	private int		intervals;
	private double  delta;  //change in Amps
	private int		consumption; //in Watts
	
	//
	// Constructor
	//
	public CompactReading(){
		super();
		super.setMonitorName("");
		super.setTimestamp(null);
		super.setReading(0.0d); //Amps_Raw_Data
		intervals = 0;
		delta = 0.0d;
		consumption = 0;
	}
	//
	// Constructor
	//
	//
	// Constructor (initial state, no interval or delta calculations 
	//
	public CompactReading(Timestamp ts, double amps) {
		super.setMonitorName("");
		super.setTimestamp(ts);
		super.setReading(amps);  //Amps_Raw_Data
		intervals = 1;
		delta = 0.0d;
		consumption = 0;
	}
		protected void outputCSV(PrintWriter pw){
			
		super.outputCSV(pw);	
		pw.printf(",%d", intervals);
		pw.printf(",%g", delta,",");
		pw.printf(",%d,", consumption);
		if (delta>0) pw.printf("On"); else pw.printf("Off");
		pw.println();
	}

	public int intervals() {
		return intervals;
	}
	public void addIntervals(int i) {
		intervals += i;
		
	}
	public void setConsumption(double d) {
		delta = d;
		consumption = (int) Math.round(d*VOLTAGE*SECONDS); // suspect, this would be watt-minutes!
	}
	public double delta() {
		return delta;
	}
	public int getConsumption() {
		return consumption;
	}
	public Boolean on() {
		return consumption>0;
	}
}
