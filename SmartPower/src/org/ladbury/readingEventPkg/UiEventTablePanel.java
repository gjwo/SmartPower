package org.ladbury.readingEventPkg;

import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTablePanel;

public class UiEventTablePanel extends UiDataTablePanel{
	
	private static final long serialVersionUID = 1L;
	
	private UiEventTableLink	model; //this must change when replicating to match the object link table
	private TableRowSorter<UiEventTableLink> sorter;
	
	public UiEventTablePanel(ActionListener al){
		super(al);
		
		model = new UiEventTableLink();
		getTable().setModel(model);
		sorter = new TableRowSorter<UiEventTableLink>(model);
		getTable().setRowSorter(sorter);
		//{"ID", "Timestamp", "Consumption",  "On", "Select"}
		getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setMaxWidth(100);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setMaxWidth(40);
	}
	public AbstractTableModel getModel() {
		return model;
	}
	@Override
	public EntityType type() {
		return EntityType.EVENTS;
	}
	@Override
	public void cellSelect(JTable target, int row, int col) {
		if (col == 0){ //the ID column
			SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(),EntityType.EVENTS,row); //fire up a form dialogue
			repaint();
		}
	}

}
