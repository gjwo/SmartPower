package org.ladbury.smartpowerPkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;

import org.ladbury.meterPkg.Meter;
import org.ladbury.meterPkg.Meter.MeterType;
import org.ladbury.meterPkg.Metric;
import org.ladbury.meterPkg.Metric.MetricType;
import org.ladbury.meterPkg.TimedRecord;
import org.ladbury.readingsPkg.*;


//
//
// FileAccess
//
//
class FileAccess
     {
    /**
	 * 
	 */
	
	private String inputPathName;
	private String outputPathName;
    private String inputFileName;
    private String outputFileName;
    private File inputFile, outputFile;
    private FileInputStream m_in;
    private BufferedReader m_br;
    private PrintWriter m_pw;
 
    protected void SPfile() {
    	/*
    	 *  Constructor
    	 */
    	inputPathName = null;
        inputFileName = null;
        inputFile = null;
        m_in = null;
        m_br = null;
        m_pw = null;
        
    }

    protected void openInput()
    //throws IOException
    // opens a file as a buffered stream
    {
        try {
        	//SmartPower.m_main.frame.displayLog("FileAccess Opening file " + inputPathName + inputFileName + "\n\r");
            inputFile = new File(inputPathName, inputFileName);
            m_in = new FileInputStream(inputFile);
            m_br = new BufferedReader(new InputStreamReader(m_in));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            SmartPower.getMain().getFrame().displayLog(ioe.toString());
            //throw ioe;
        }
    }

    protected void closeInput()
    //throws IOException
    {
        try {
            m_br = null;
            m_in.close();
            m_in = null;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe.toString());
            //throw ioe;
        }
    }

    protected void closeOutput()
    //throws IOException
    {
        try {
        	SmartPower.getMain().getFrame().displayLog("Closing output file " + inputPathName +
                    outputFileName + "\n\r");
            m_pw.close();
        	outputFile = null;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            //throw ioe;
        }
    }
    protected PrintWriter openOutput(String dirname, String filename)
    {
    	try{
        	SmartPower.getMain().getFrame().displayLog("Opening output file " + dirname +
                    filename + "\n\r");
        	outputFile = null;
            outputFile = new File(dirname, filename);
            // Open file to print output to
            m_pw = new PrintWriter( new FileOutputStream(outputFile), true);
    	}catch(Exception e){
            e.printStackTrace();
            System.out.println(e.toString());
    	}
        return m_pw;
    }

    protected PrintWriter outputWriter()
    {
        return m_pw;
    }
 
    
    protected String state() {
        if (inputFile == null) {
            return ("Closed");
        }
        else {
            return ("Open");
        }
    }

    //
    // process_file
    //
    // to turn each line of input tokens into readings tokens
    // then to group these into readings records for parsing
    //
    protected void processFile(MetricType metricT) {
 		String 	dataRow;
        Meter mtr = SmartPower.getMain().getData().getMeters().get(0);
        Metric mtc = mtr.getMetric(metricT);
        try {
        	switch (mtr.getType()){
        	case OWLCM160:      	
            	//SmartPower.m_main.frame.displayLog("Loading readings from file\n\r");
            	dataRow = m_br.readLine(); // Read the first line of data Headings ignore).
            	dataRow = m_br.readLine(); // Read the second line of data blank ignore.
            	dataRow = m_br.readLine(); // Read the first line of data.
            	// The while checks to see if the data is null. If it is, we've hit
            	// the end of the file. If not, process the data.
            	while (dataRow != null){
            		SmartPower.getMain().getRawReadings().add(new OwlRawReading(dataRow.split(",")));
            		dataRow = m_br.readLine(); // Read next line of data.
            	}
            	break; // finished as EOF reached
                
            case ONZO:
            	//SmartPower.m_main.frame.displayLog("Loading readings from file\n\r");
             	dataRow = m_br.readLine(); // Read the first line of data.
            	// The while checks to see if the data is null. If it is, we've hit
            	// the end of the file. If not, process the data.
             	while (dataRow != null){
            		mtc.appendRecord(new TimedRecord(dataRow.split(",")));
            		dataRow = m_br.readLine(); // Read next line of data.
            	}  
            	
            	break; // finished as EOF reached
 			default:
				return; //nothing to process
        	}
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe.toString());
            //throw ioe;
        }
        catch ( ParseException pe){
        	System.out.println(pe.toString());
        }
    }
    
    protected void OutputCSVFiles(){
    	MeterType t;
    	t = SmartPower.getMain().getData().getMeters().get(0).getType();
    	switch(t){
    	case OWLCM160:
        	String nameRoot = new String(SmartPower.getMain().getRawReadings().readingsName());
        	// construct output filename  and open file
        	outputFileName = nameRoot+"_raw"+".csv";
            openOutput(inputPathName, outputFileName); // same directory as source file
            //save raw readings
            SmartPower.getMain().getRawReadings().outputCSV(m_pw);
            closeOutput();
        	//SmartPower.m_main.frame.displayLog("Run: back from raw save\n");

        	// construct output filename and open file
        	outputFileName = nameRoot+"_compact"+".csv";
            openOutput(inputPathname(), outputFileName); // same directory as source file
            //save compact readings
            SmartPower.getMain().getCompactReadings().outputCSV(m_pw);
            closeOutput();
            //m_main.frame.displayLog("Run: back from compact save\n");

        	// construct output filename and open file
        	outputFileName = SmartPower.getMain().getData().getReadingEvents().eventsName()+"_events"+".csv";
            m_pw = openOutput(inputPathName, outputFileName); // same directory as source file
            //save device events
            SmartPower.getMain().getData().getReadingEvents().outputCSV(m_pw);
            closeOutput();
    		break;
    	case ONZO:
        	// construct output filename and open file
        	outputFileName = SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE).readingsName()+"_R.csv";
            m_pw = openOutput(outputPathName, outputFileName);
            //save Reading events
            SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE).outputReadingsCSV(m_pw);
            closeOutput();
            //save device Activity
        	outputFileName = SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE).readingsName()+"_DA.csv";
            m_pw = openOutput(outputPathName, outputFileName);
        	for(int i=0; i<SmartPower.getMain().getData().getActivity().size();i++){
         		m_pw.println(SmartPower.getMain().getData().getActivity().get(i).toCSV());
        	}
            closeOutput();
    		break;
    	default:
    		break;
    	}
        //m_main.frame.displayLog("Run: back from events save\n");
    }

    //
    // Accessor methods
    //

    protected void setInputFilename(String s){
    	inputFileName = s;
    }

    protected String inputFilename(){
    	return inputFileName;
    }
    
    protected void setInputPathname(String s){
    	inputPathName = s;
    }

    protected String inputPathname(){
    	return inputPathName;
    }
    

    protected void setOutputFilename(String s){
    	outputFileName = s;
    }

    protected String outputFilename(){
    	return outputFileName;
    }

    protected void setOutputPathname(String s){
    	outputPathName = s;
    }

    protected String outputPathname(){
    	return outputPathName;
    }
    
    protected PrintWriter pw(){
    	return m_pw;
    }
}