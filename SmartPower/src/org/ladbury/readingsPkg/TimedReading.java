package org.ladbury.readingsPkg;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import org.ladbury.smartpowerPkg.Timestamped;

//  TimedReading
//  this class provides the basic data for a reading
public class TimedReading {

	// values for onward processing

	private String m_monitor_name;
	private Timestamp m_timestamp; //accuracy depends on monitor, Owl recorded readings have a 1 minute granularity, Onzo 1 second
	private double m_reading; // consumption reading in fractions of an amp

	//
    // constructors
    //
    protected TimedReading() {
       m_monitor_name = null;
       m_timestamp = null;
       m_reading = 0.0d;
    }
    protected TimedReading(String m, Timestamp d) {
    	m_monitor_name = m;
        m_timestamp = d;
        m_reading = 0.0d;
    }
    protected TimedReading(String m, Timestamp d, Double r) {
    	m_monitor_name = m;
        m_timestamp = d;
        m_reading = r;
    }
    
	protected void outputCSV(PrintWriter pw){

		DateFormat df = new SimpleDateFormat(Timestamped.OUTPUTDATEFORMAT);
		
		pw.printf("%s,%g", df.format(m_timestamp), m_reading);
	}

    //
    //accessor methods
    //
	protected String monitorName(){
		return m_monitor_name;
	}
	protected void setMonitorName(String s) {
		this.m_monitor_name = s;
	}
	public Timestamp timestamp(){
		return m_timestamp;
	}
	protected void setTimestamp(Timestamp ts){
		m_timestamp = ts;
	}
	public String getTimestampString(){
		DateFormat df = new SimpleDateFormat(Timestamped.OUTPUTDATEFORMAT);
		return df.format(m_timestamp);
	}
	public double reading(){
		return m_reading;
	}
	protected void setReading(double r){
		m_reading = r;
	}
}