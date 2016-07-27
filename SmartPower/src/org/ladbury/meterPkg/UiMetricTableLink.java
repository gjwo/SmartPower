package org.ladbury.meterPkg;

//import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.ladbury.meterPkg.Metric.MetricType;
import org.ladbury.smartpowerPkg.SmartPower;
//import org.ladbury.userInterfacePkg.BooleanSelector;

//
//  UiMetricTableLink
//  this class provides the linkage between the CompactReadings class and the user interface
//  in order to display readings as a table
public class UiMetricTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	public static final int METRIC_TABLE_CC = 5;
	//private static final int MAX_SELECT= 200;
	private static final String [] readingTblTitles = {"Timestamp", "Total (Watts)", "Count", "Delta (Watts)", "On?"};
	//private ArrayList <TimedRecord> readings = new ArrayList<TimedRecord>();

	//
	// Constructor
	//
//	public UiMetricTableLink(){
//		readings = SmartPower.getMain().getMetric(Meter.POWER_REAL_FINE).getReadings();

//	}
	//private BooleanSelector m_selector = new BooleanSelector(MAX_SELECT);
	
	@Override
	public int getColumnCount() {
		return METRIC_TABLE_CC;
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= METRIC_TABLE_CC) | (col <0)){
			return "Bad Column";
		}
        return readingTblTitles[col];
    }

	@Override
	public int getRowCount() {
		if (SmartPower.getMain() == null) return 0;
		if (SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE) == null) return 0;
		return SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE).getReadings().size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<?> getColumnClass(int col){
		Class c = Object.class;
		
		switch(col){
		case 0: c = String.class;
				break;
		case 1: c = Integer.class;
				break;
		case 2: c = Integer.class;
				break;
		case 3: c= Integer.class;
				break;
		case 4: c = Boolean.class;
				break;
		}
		return c;
	}
	
	public boolean isCellEditable(int row,
            int col) {
		
		if (col == 6) return true;
		return false;
	}
	@Override
	public Object getValueAt(int row, int col) {
		Object r = "Empty"; // new Object();
		
		switch(col){
			case 0: r =  getRecord(row).timestampString();
					break;
			case 1: r =  new Integer(getRecord(row).value());
					break;
			case 2: r =  new Integer(getMetric().getIntervals(row));
					break;
			case 3: r =  new Integer(getMetric().getDelta(row));
					break;
			case 4: r =  new Boolean(getMetric().isOn(row));
					break;
		}
		return r;
	}

	@Override
	public void setValueAt(Object aValue,
            int row,
            int col){
		//if(col == 4) m_selector.setSelect(row,((boolean)aValue));
	}
	public Metric getMetric(){
		return SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE);
	}
	public TimedRecord getRecord(int row){
		return SmartPower.getMain().getData().getMeters().get(0).getMetric(MetricType.POWER_REAL_FINE).getRecord(row);
	}
/*	
	@Override
	public void fireTableDataChanged() {
		
	}

	@Override
	public void fireTableRowsInserted(int firstRow,
            int lastRow){
	}

	@Override
	public void fireTableRowsDeleted(int firstRow,
            int lastRow){
	}
*/	
}
