package org.ladbury.deviceActivityPkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

public class UiActivityTableLink extends AbstractTableModel {
/**
 * This class implements the linkage between DeviceActivity the corresponding GUI table.
 * 
 * @author GJWood
 * @version 1.1 2012/11/29 changed to handle storage as elements in PersistentList
 */
	
	private static final long serialVersionUID = -8319401200901128800L;
	private static final int TABLE_CC = 6;
	private static final int MAX_SELECT= 200;
	private static final String [] eventTblTitles = {"ID", "Start", "Stop",  "Device", "Power", "Select"};
	
	private BooleanSelector booleanSelector = new BooleanSelector(MAX_SELECT);
	
	@Override
	public int getColumnCount() {
		return TABLE_CC;
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= TABLE_CC) || (col <0)){
			return "Bad Column";
		}
        return eventTblTitles[col];
    }

	@Override
	public int getRowCount() {
		if (SmartPower.getMain()==null) return 0;
		return SmartPower.getMain().getData().getActivity().size();
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
		case 2: c = String.class;
				break;
		case 3: c = String.class;
				break;
		case 4: c = Integer.class;
				break;
		case 5: c = Boolean.class;
				break;
		default:;		
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
		
		if (row < this.getRowCount()){
			switch(col){
				case 0: r =  new Long(getActivity(row).id());
						break;
				case 1: r =  new String(getActivity(row).startTimestampString());
						break;
				case 2: r =  new String(getActivity(row).endTimestampString());
						break;
				case 3:	if (getActivity(row).getDevice()==null){
							r = new String("Unknown");
						} else 	r =  new String(getActivity(row).getDevice().name());
						break;
				case 4: r = new Integer(getActivity(row).getConsumption());		
						break;
				case 5: r =  new Boolean(booleanSelector.isSelected(row));
						break;
			}
		}
		return r;
	}

	@Override
	public void setValueAt(Object aValue, int row, int col){
		
		if (row < this.getRowCount()){
			switch(col){
				case 0: break;
				case 1: break;
				case 2: break;
				case 3: break;
				case 4: break;
				case 5: booleanSelector.setSelect(row,((boolean)aValue));
						break;
				default:; //do nothing update not allowed
			}
		}
	}
	public DeviceActivity getActivity(int row){
		return SmartPower.getMain().getData().getActivity().get(row);
	}
}
