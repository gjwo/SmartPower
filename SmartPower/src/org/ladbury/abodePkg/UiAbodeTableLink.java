package org.ladbury.abodePkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

public class UiAbodeTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	private static final int TABLE_CC = 6;
	private static final int MAX_SELECT= 200;
	private static final String [] eventTblTitles = {"ID", "Address", "Type",  "Meters", "Rooms", "Select"};
	
	
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
		return SmartPower.getMain().getData().getAbodes().size();
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
		case 2: c = Enum.class;
				break;
		case 3: c = Integer.class;
				break;
		case 4: c = Integer.class;
				break;
		case 5: c = Boolean.class;
				break;
		}
		return c;
	}
	@Override
	public boolean isCellEditable(int row,
            int col) {
		
		return (col == 6) ; 
	}
	@Override
	public Object getValueAt(int row, int col) {
		Object r = "Empty"; // new Object();
		
		if (row < getRowCount()){
			switch(col){
				case 0: r =  new Long(getAbode(row).getAbodeId());
						break;
				case 1: r =  new String(getAbode(row).getAddress());
						break;
				case 2: r =  getAbode(row).getType();
						break;
				case 3: r =  new Integer(getAbode(row).meterCount());
						break;
				case 4: r =  new Integer(getAbode(row).roomCount());
						break;
				case 5: r =  new Boolean(booleanSelector.isSelected(row));
						break;
			}
		}
		return r;
	}

	@Override
	public void setValueAt(Object aValue, int row, int col){
		
		if (row < SmartPower.getMain().getData().getAbodes().size()){
			switch(col){
				case 6: booleanSelector.setSelect(row,((boolean)aValue));
						break;
				default:; //do nothing update not allowed
			}
		}
	}
	private Abode getAbode(int row){
		return SmartPower.getMain().getData().getAbodes().get(row);
	}
}
