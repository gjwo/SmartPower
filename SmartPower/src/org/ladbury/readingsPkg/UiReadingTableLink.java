package org.ladbury.readingsPkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

//
//  UiReadingTableLink
//  this class provides the linkage between the CompactReadings class and the user interface
//  in order to display readings as a table
public class UiReadingTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	public static final int READINGS_TABLE_CC = 7;
	private static final int MAX_SELECT= 200;
	private static final String [] readingTblTitles = {"Timestamp", "Total Amps", "Count", "Delta (Amps)", "Watts", "On?", "Select"};
	
	
	private BooleanSelector m_selector = new BooleanSelector(MAX_SELECT);
	
	@Override
	public int getColumnCount() {
		return READINGS_TABLE_CC;
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= READINGS_TABLE_CC) | (col <0)){
			return "Bad Column";
		}
        return readingTblTitles[col];
    }

	@Override
	public int getRowCount() {
		if (SmartPower.getMain() == null) return 0;
		if (SmartPower.getMain().getCompactReadings() == null) return 0;
		return SmartPower.getMain().getCompactReadings().size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<?> getColumnClass(int col){
		Class c = Object.class;
		
		switch(col){
		case 0: c = String.class;
				break;
		case 1: c = Double.class;
				break;
		case 2: c = Integer.class;
				break;
		case 3: c= Double.class;
				break;
		case 4: c = Integer.class;
				break;
		case 5: c = Boolean.class;
				break;
		case 6: c = Boolean.class;
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
			case 0: r =  getCompactReading(row).getTimestampString();
					break;
			case 1: r =  new Double(getCompactReading(row).reading());
					break;
			case 2: r =  new Integer(getCompactReading(row).intervals());
					break;
			case 3: r =  new Double(getCompactReading(row).delta());
					break;
			case 4: r =  new Integer(Math.abs(getCompactReading(row).getConsumption()));
					break;
			case 5: r =  new Boolean(getCompactReading(row).on());
					break;
			case 6: r =  new Boolean(m_selector.isSelected(row));
					break;
		}
		return r;
	}

	@Override
	public void setValueAt(Object aValue,
            int row,
            int col){
		if(col == 6) m_selector.setSelect(row,((boolean)aValue));
	}
	public CompactReading getCompactReading(int row){
		return SmartPower.getMain().getCompactReadings().elementAt(row);
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
