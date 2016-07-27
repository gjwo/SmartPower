package org.ladbury.devicePkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.devicePkg.Device.DeviceType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

public class UiDeviceTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	public static final int DEVICE_TABLE_CC = 5;
	private static final int MAX_SELECT= 200;
	private static final String [] readingTblTitles = {"ID", "Name", "Type",  "Watts", "Select"};
	
	private BooleanSelector booleanSelector = new BooleanSelector(MAX_SELECT);
	
	@Override
	public int getColumnCount() {
		return DEVICE_TABLE_CC;
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= DEVICE_TABLE_CC) | (col <0)){
			return "Bad Column";
		}
        return readingTblTitles[col];
    }

	@Override
	public int getRowCount() {
		if (SmartPower.getMain()==null) return 0;
		return SmartPower.getMain().getData().getDevices().size();
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
		case 3: c = Integer.class;
				break;
		case 4: c = Boolean.class;
				break;
		}
		return c;
	}
	
	public boolean isCellEditable(int row,
            int col) {
		
		return (col<= 5) && (col > 0); 
	}
	@Override
	public Object getValueAt(int row, int col) {
		Object r = "Empty"; // new Object();
		
		if (row < getRowCount()){
			switch(col){
				case 0: r =  new Long(getDevice(row).id());
						break;
				case 1: r =  new String(getDevice(row).name());
						break;
				case 2: r =  new String( getDevice(row).getType().toString());
						break;
				case 3: r =  new Integer(Math.abs(getDevice(row).getConsumption()));
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
				case 0: getDevice(row).setDeviceId((long)aValue);
						break;
				case 1: getDevice(row).setName((String)aValue);
						break;
				case 2: getDevice(row).setType((DeviceType)aValue);
						break;
				case 3: getDevice(row).setConsumption((int)aValue);
						break;
				case 4: booleanSelector.setSelect(row,((boolean)aValue));
						break;
			}
		}
	}

	public Device getDevice(Integer row) {
		return SmartPower.getMain().getData().getDevices().get(row);
	}
}
