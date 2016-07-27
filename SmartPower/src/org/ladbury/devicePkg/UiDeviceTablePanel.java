package org.ladbury.devicePkg;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTablePanel;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;

public class UiDeviceTablePanel extends UiDataTablePanel{
	
	private static final long serialVersionUID = 1L;
	
	public UiDeviceTableLink	model;
	private JButton				btnAdd;
	private TableRowSorter<UiDeviceTableLink> sorter;
	private RowFilter<UiDeviceTableLink,Integer> deviceIdFilter;
	private RowFilter<UiDeviceTableLink,Integer> roomFilter;
	private	Long				deviceIdFilterValue;
	private String				roomNameFilterValue;
	
	public UiDeviceTablePanel(ActionListener al){
		super(al);
		// set up devices tab
		
		model = new UiDeviceTableLink();
		sorter = new TableRowSorter<UiDeviceTableLink>(model);
		getTable().setModel(model);
		getTable().setRowSorter(sorter);
		deviceIdFilterValue = -1L;
		deviceIdFilter = new RowFilter<UiDeviceTableLink,Integer>() { 
			// implements the "include" method of the abstract class "RowFilter"
			// and constructs an object of that class using the
			// implied no argument constructor
			public boolean include(Entry<? extends UiDeviceTableLink, ? extends Integer> entry) {
				UiDeviceTableLink DeviceModel;
				Device device;
				DeviceModel= entry.getModel();
				device = DeviceModel.getDevice(entry.getIdentifier());
				if (deviceIdFilterValue<0) return true; // don't filter
				if (device.id() == deviceIdFilterValue){
					return true;
				}
				return false;
			}
		};

		sorter.setRowFilter(deviceIdFilter);
		sorter.setRowFilter(roomFilter);
		//{"ID", "Name", "Type",  "Watts", "Select"}
		getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setMaxWidth(50);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setMaxWidth(40);
		// add an extra button
		btnAdd = new JButton("Add Device");
		btnAdd.addActionListener(al);
		addButton(btnAdd);
	}
	public SPAction actionFromEventSource(Object o){
		if(o==getBtnCancel()) return SPAction.CANCEL;
		if(o==getBtnSelect()) return SPAction.SELECT;
		if(o==getBtnClearFilter()) return SPAction.CLEAR_FILTER;
		if(o==btnAdd) return SPAction.ADD_DEVICE;
		return SPAction.UNKNOWN;  
	}
	public AbstractTableModel getModel() {
		return model;
	}
	@Override
	public void clearFilter() {
		deviceIdFilterValue = -1L;
		roomNameFilterValue = null;
		model.fireTableDataChanged();
	}
	@Override
	public EntityType type() {
		return EntityType.DEVICE;
	}
	// Methods  to access filter variables
	public Long getDeviceIdFilterValue() {
		return deviceIdFilterValue;
	}
	public void setDeviceIdFilterValue(Long deviceIdFilterValue) {
		this.deviceIdFilterValue = deviceIdFilterValue;
	}
	public String getRoomNameFilterValue() {
		return roomNameFilterValue;
	}
	public void setRoomNameFilterValue(String roomNameFilterValue) {
		this.roomNameFilterValue = roomNameFilterValue;
	}
	@Override
	public void cellSelect(JTable target, int row, int col) {
		if (col == 0){ //the ID column
			SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(),EntityType.DEVICE,row); //fire up a form dialogue
			repaint();
		}
	}

}
