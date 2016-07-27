package org.ladbury.abodePkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

public class UiRoomTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	private static final int TABLE_CC = 5;
	private static final int MAX_SELECT= 200;
	private static final String [] eventTblTitles = {"ID", "Name", "Type",  "Cluster", "Select"};
	
	
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
		return SmartPower.getMain().getData().getRooms().size();
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
		case 3: c = String.class;
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
				case 0: r =  new Long(getRoom(row).id());
						break;
				case 1: r =  new String(getRoom(row).name());
						break;
				case 2: r =  getRoom(row).getType();
						break;
				case 3: r =  new String(getRoom(row).getCluster().name());
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
				case 4: booleanSelector.setSelect(row,((boolean)aValue));
						break;
				default:; //do nothing update not allowed
			}
		}
	}

	public Room getRoom(int row) {
		return SmartPower.getMain().getData().getRooms().get(row);
	}
}
