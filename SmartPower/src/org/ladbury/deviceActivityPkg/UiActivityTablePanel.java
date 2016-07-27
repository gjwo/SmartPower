package org.ladbury.deviceActivityPkg;

import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTablePanel;

public class UiActivityTablePanel extends UiDataTablePanel{
	
	private static final long serialVersionUID = 201L;
	
	private UiActivityTableLink	model; //this must change when replicating to match the object link table
	private TableRowSorter<UiActivityTableLink> sorter;
	
	public UiActivityTablePanel(ActionListener al){
		super(al);
		model = new UiActivityTableLink();
		getTable().setModel(model);
		sorter = new TableRowSorter<UiActivityTableLink>(model);
		getTable().setRowSorter(sorter);
		//{"ID", "Start", "Stop",  "Device", "Power" "EventC", "Select"}
		getTable().getColumnModel().getColumn(0).setPreferredWidth(20);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
		//getTable().getColumnModel().getColumn(1).setMaxWidth(100);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setMaxWidth(100);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(60);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(50);
		//getTable().getColumnModel().getColumn(4).setMaxWidth(50);
		getTable().getColumnModel().getColumn(5).setPreferredWidth(40);
		//getTable().getColumnModel().getColumn(5).setMaxWidth(40);

	}
	@Override
	public AbstractTableModel getModel() {
		return model;
	}
	@Override
	public EntityType type() {
		return EntityType.ACTIVITY;
	}
	@Override
	public void cellSelect(JTable target, int row, int col) {
		if (col == 0){ //the ID column
			SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(),EntityType.ACTIVITY,row); //fire up a form dialogue
			repaint();
		}
	}

}
