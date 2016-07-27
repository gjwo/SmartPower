package org.ladbury.readingsPkg;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.ladbury.smartpowerPkg.Timestamped;

//
// RawReadings
//
// This class holds electrical readings.

public class RawReadings
    extends Vector <OwlRawReading> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String m_monitor_name;
	private Date m_first_timestamp;
	private Date m_last_timestamp;
	
    // Vector will contain the readings

    //
    // constructor
    //
    public RawReadings() {
        super(100, 10);
       m_monitor_name = null;
       m_first_timestamp = null;
       m_last_timestamp = null;
    }

    //
    // Add a token to the current line, at the end
    //
    public boolean add(OwlRawReading reading) {
    	 super.addElement(reading);
        m_monitor_name = reading.monitorName();
        m_last_timestamp = reading.timestamp();
        if (this.m_first_timestamp == null) this.m_first_timestamp = reading.timestamp();
        return true;
    }

    //
    // output the entire vector (human readable)
    //
    public void outputReadings(PrintWriter pw) {
    	Iterator <OwlRawReading> i;
    	
    	i = this.iterator(); 
    	while(i.hasNext()) 
    		i.next().println(pw);       
    }

    //
    // output the entire vector (Comma Separated Variables)
    //
    public void outputCSV(PrintWriter pw) {
    	Iterator <OwlRawReading> i;
    	
    	i = this.iterator(); 
    	while(i.hasNext()) 
    		i.next().outputCSV(pw);       
    }

    //
    // construct a systematic name for
    // this set of readings
    //
    public String readingsName(){
		DateFormat df = new SimpleDateFormat(Timestamped.COMPACTDATEFORMAT);
		String s = "";
    	for( int i =0; i < m_monitor_name.length(); i++){
    	    if(m_monitor_name.charAt(i) != '"') s += m_monitor_name.charAt(i);
        }  	  
    	return s+"_"+df.format(m_first_timestamp)+"-"+df.format(m_last_timestamp);
    }
    
    //
    // Access the name of the device
    // common to all readings
    //
    public String monitorName(){
    	return m_monitor_name;
    }

	protected void setMonitorName(String s) {
		m_monitor_name = s;
		
	}
    
    //
    // Access the start time of the recorded readings
    //
    protected Date earliest(){
    	return m_first_timestamp;
    }

	protected void setEarliest(Date ts) {
		m_first_timestamp = ts;
		
	}

    //
    //  Access the end time of the recorded readings
    //
    protected Date latest(){
    	return m_last_timestamp;
    }

	protected void setLatest(Date ts) {
		m_last_timestamp = ts;
		
	}
}
