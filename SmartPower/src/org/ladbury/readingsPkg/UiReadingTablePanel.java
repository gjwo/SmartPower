package org.ladbury.readingsPkg;

import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.userInterfacePkg.UiDataTablePanel;


public class UiReadingTablePanel extends UiDataTablePanel {
	
	private static final long serialVersionUID = 1L;
	
	private UiReadingTableLink	model;
	private TableRowSorter<UiReadingTableLink> sorter;

	public UiReadingTablePanel(ActionListener al){
		super(al);
		model = new UiReadingTableLink();
		getTable().setModel(model);
		sorter = new TableRowSorter<UiReadingTableLink>(model);
		getTable().setRowSorter(sorter);
		//{"Timestamp", "Total Amps", "Count", "Delta (Amps)", "Watts", "On?", "Select"}
		getTable().getColumnModel().getColumn(0).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(2).setMaxWidth(40);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setMaxWidth(40);
		getTable().getColumnModel().getColumn(5).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(5).setMaxWidth(40);
		getTable().getColumnModel().getColumn(6).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(6).setMaxWidth(40);
		getTable().setCellSelectionEnabled(true);
	}
	
	public AbstractTableModel getModel() {
		return model;
	}

	@Override
	public EntityType type() {
		return EntityType.READINGS;
	}
}