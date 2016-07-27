package org.ladbury.readingsPkg;
import java.io.PrintWriter;
import java.text.*;

//
//OwlRawReading
//
//This class manages a time stamped set of reading values from a file of readings
//produced by an energy monitor or smart meter the columns are specific to the Owl monitor
//
public class OwlRawReading extends TimedReading{
    
	// values defined by OWL CM160
	private static final String OWLCM160DATEFORMAT = " dd/MM/yyyy HH:mm"; // note leading space
	
	//private	String	m_monitor; //held in super
	//private Date	m_timestamp;  //held in super
	private Double	m_GHG_Factor;
	private Double	m_Tariff_Cost;
	//private Double	m_Amps_Raw_Data; // held in super
	private Double	m_Amps_Raw_Data_Min;
	private Double	m_Amps_Raw_Data_Max;
	private Double	m_kW_Raw_Data;
	private Double	m_kW_Raw_Data_Min;
	private Double	m_kW_Raw_Data_Max;
	private Double	m_Cost_Raw_Data;
	private Double	m_Cost_Raw_Data_Min;
	private Double	m_Cost_Raw_Data_Max;
	private Double	m_GHG_Raw_Data;
	private Double	m_GHG_Raw_Data_Min;
	private Double	m_GHG_Raw_Data_Max;
	
	//
	// Constructor ()
	//
	public OwlRawReading(){
		super();
		super.setMonitorName("");
		super.setTimestamp(null);
		m_GHG_Factor = 0.0d;
		m_Tariff_Cost = 0.0d;
		super.setReading(0.0d); //Amps_Raw_Data
		m_Amps_Raw_Data_Min = 0.0d;
		m_Amps_Raw_Data_Max = 0.0d;
		m_kW_Raw_Data = 0.0d;
		m_kW_Raw_Data_Min = 0.0d;
		m_kW_Raw_Data_Max = 0.0d;
		m_Cost_Raw_Data = 0.0d;
		m_Cost_Raw_Data_Min = 0.0d;
		m_Cost_Raw_Data_Max = 0.0d;
		m_GHG_Raw_Data = 0.0d;
		m_GHG_Raw_Data_Min = 0.0d;
		m_GHG_Raw_Data_Max = 0.0d;

	}
	
	//
	// Constructor (String[] dataArray)
	//
	public OwlRawReading(String[] dataArray) throws ParseException,ArrayIndexOutOfBoundsException{
		DateFormat df = new SimpleDateFormat(OWLCM160DATEFORMAT); 
		
		java.util.Date utilDate = df.parse(dataArray[1]);
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
		super.setTimestamp(sqlTimestamp);
		
		super.setMonitorName(dataArray[0]);
		m_GHG_Factor = new Double(dataArray[2]);
		m_Tariff_Cost = new Double(dataArray[3]);
		super.setReading(new Double(dataArray[4]));  //Amps_Raw_Data
		m_Amps_Raw_Data_Min = new Double(dataArray[5]);
		m_Amps_Raw_Data_Max = new Double(dataArray[6]);
		m_kW_Raw_Data = new Double(dataArray[7]);
		m_kW_Raw_Data_Min = new Double(dataArray[8]);
		m_kW_Raw_Data_Max = new Double(dataArray[9]);
		m_Cost_Raw_Data = new Double(dataArray[10]);
		m_Cost_Raw_Data_Min = new Double(dataArray[11]);
		m_Cost_Raw_Data_Max = new Double(dataArray[12]);
		m_GHG_Raw_Data = new Double(dataArray[13]);
		m_GHG_Raw_Data_Min = new Double(dataArray[14]);
		m_GHG_Raw_Data_Max = new Double(dataArray[15]);
	}

	//
	// Output (Human readable)
	//
	public void println(PrintWriter pw){
		
		pw.printf("[%s]", super.monitorName());
		pw.printf("[%s]", super.getTimestampString());
		pw.printf(" %g", m_GHG_Factor);
		pw.printf(" %g", m_Tariff_Cost);
		pw.printf(" %g", super.reading());   //Amps_Raw_Data
		pw.printf(" %g", m_Amps_Raw_Data_Min);
		pw.printf(" %g", m_Amps_Raw_Data_Max);
		pw.printf(" %g", m_kW_Raw_Data);
		pw.printf(" %g", m_kW_Raw_Data_Min);
		pw.printf(" %g", m_kW_Raw_Data_Max);
		pw.printf(" %g", m_Cost_Raw_Data);
		pw.printf(" %g", m_Cost_Raw_Data_Min);
		pw.printf(" %g", m_Cost_Raw_Data_Max);
		pw.printf(" %g", m_GHG_Raw_Data);
		pw.printf(" %g", m_GHG_Raw_Data_Min);
		pw.printf(" %g", m_GHG_Raw_Data_Max);
		pw.println();
	}

}
