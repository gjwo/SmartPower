package org.ladbury.readingEventPkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

public class UiEventTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	private static final int TABLE_CC = 5;
	private static final int MAX_SELECT= 200;
	private static final String [] eventTblTitles = {"ID", "Timestamp", "Consumption",  "On", "Select"};
	
	
	private BooleanSelector booleanSelector = new BooleanSelector(MAX_SELECT);
	
	@Override
	public int getColumnCount() {
		return TABLE_CC;
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= TABLE_CC) | (col <0)){
			return "Bad Column";
		}
        return eventTblTitles[col];
    }

	@Override
	public int getRowCount() {
		if (SmartPower.getMain()==null) return 0;
		return SmartPower.getMain().getData().getReadingEvents().size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<?> getColumnClass(int col){
		Class c = Object.class;
		
		switch(col){
		case 0: c = Long.class;
				break;
		case 1: c = String.class;
				break;
		case 2: c = Integer.class;
				break;
		case 3: c = Boolean.class;
				break;
		case 4: c = Boolean.class;
				break;
		}
		return c;
	}
	@Override
	public boolean isCellEditable(int row,
            int col) {
		
		return (col == 4) ; 
	}
	@Override
	public Object getValueAt(int row, int col) {
		Object r = "Empty"; // new Object();
		
		if (row < getRowCount()){
			switch(col){
				case 0: r =  new Long(getReadingEvent(row).id());
						break;
				case 1: r =  new String(getReadingEvent(row).timestampString());
						break;
				case 2: r =  new Integer( getReadingEvent(row).consumption());
						break;
				case 3: r =  new Boolean(getReadingEvent(row).isOn());
						break;
				case 4: r =  new Boolean(booleanSelector.isSelected(row));
						break;
			}
		}
		return r;
	}

	@Override
	public void setValueAt(Object aValue, int row, int col){
		
		if (row < getRowCount()){
			switch(col){
				case 0: break;
				case 1: break;
				case 2: break;
				case 3: break;
				case 4: booleanSelector.setSelect(row,((boolean)aValue));
						break;
				default:break; //do nothing update not allowed
			}
		}
	}
	public ReadingEvent getReadingEvent(int row){
		return SmartPower.getMain().getData().getReadingEvents().get(row);
	}

}
