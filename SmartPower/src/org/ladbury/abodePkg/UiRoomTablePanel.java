package org.ladbury.abodePkg;

import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.abodePkg.Room.RoomType;
import org.ladbury.devicePkg.UiDeviceTablePanel;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTablePanel;

public class UiRoomTablePanel extends UiDataTablePanel{
	
	private static final long serialVersionUID = 201L;
	
	private UiRoomTableLink		model; //this must change when replicating to match the object link super.getTable()
	private TableRowSorter<UiRoomTableLink> sorter;
	private RowFilter<UiRoomTableLink,Integer> RoomTypeFilter;
	private RoomType 			typeFilter;

	public UiRoomTablePanel(ActionListener al){
		super(al);
		// construct table type specific items
		model = new UiRoomTableLink();
		typeFilter = RoomType.UNKNOWN;
		RoomTypeFilter = new RowFilter<UiRoomTableLink,Integer>() { 
			// implements the "include" method of the abstract class "RowFilter"
			// and constructs an object of that class using the
			// implied no argument constructor
			public boolean include(Entry<? extends UiRoomTableLink, ? extends Integer> entry) {
				UiRoomTableLink roomModel;
				Room room;
				roomModel= entry.getModel();
				room = roomModel.getRoom(entry.getIdentifier());
				if (typeFilter==RoomType.UNKNOWN) return true; // don't filter
				if (room.getType()==typeFilter){
					return true;
				}
				return false;
			}
		};
		getTable().setModel(model);
		sorter = new TableRowSorter<UiRoomTableLink>(model);
		getTable().setRowSorter(sorter);
		sorter.setRowFilter(RoomTypeFilter);
		//Columns "ID", "Name", "Type",  "Devices", "Select"
		getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setMaxWidth(100);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setMaxWidth(40);
	}

	@Override
	public AbstractTableModel getModel() {
		return model;
	}
	@Override
	public void clearFilter(){
		// called when the clear filter button is pressed
		typeFilter = RoomType.UNKNOWN;
		model.fireTableDataChanged();
	}
	@Override
	public EntityType type(){
		return EntityType.ROOM;
	}
	@Override
	public void cellSelect(JTable target, int row, int col) {
		UiDeviceTablePanel devicePanel;
		//called when the cell is double clicked
		if (col == 0){ //the ID column
			//SmartPower.getMain().getFrame().displayLog("mouse clicked on "+row+"\n\r");
			SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(),EntityType.ROOM,row); //fire up a form dialogue
			repaint();
		}
		if (col ==2){//the room type column
			typeFilter = (RoomType) model.getValueAt(row, col);
			model.fireTableDataChanged();
		}
		if (col == 3){ //the devices column
			SmartPower.getMain().getFrame().displayLog("mouse clicked on "+row+"\n\r");
			devicePanel = (UiDeviceTablePanel) SmartPower.getMain().getFrame().getDataDialogue().dataPanel().getTab(EntityType.DEVICE);
			devicePanel.setRoomNameFilterValue(model.getRoom(row).name()); // set filter to selected room
			devicePanel.getModel().fireTableDataChanged();
			SmartPower.getMain().getFrame().getDataDialogue().dataPanel().selectTab(EntityType.DEVICE); //switch to device panel
		}
	}
	public RoomType getTypeFilter() {
		return typeFilter;
	}
	public void setTypeFilter(RoomType filterValue) {
		this.typeFilter = filterValue;
	}
}
