package org.ladbury.meterPkg;

import java.awt.event.ActionListener;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.userInterfacePkg.UiDataTablePanel;


public class UiMetricTablePanel extends UiDataTablePanel {
	
	private static final long serialVersionUID = 1L;
	
	private UiMetricTableLink	model;
	private TableRowSorter<UiMetricTableLink> sorter;

	public UiMetricTablePanel(ActionListener al){
		super(al);
		model = new UiMetricTableLink();
		getTable().setModel(model);
		sorter = new TableRowSorter<UiMetricTableLink>(model);
		getTable().setRowSorter(sorter);
		//{"Timestamp", "Total watts", "Count", "Delta (watts)",  "On?"}
		getTable().getColumnModel().getColumn(0).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(2).setMaxWidth(40);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(30);
		getTable().getColumnModel().getColumn(4).setMaxWidth(30);
		getTable().setCellSelectionEnabled(true);
	}
	
	public AbstractTableModel getModel() {
		return model;
	}

	@Override
	public EntityType type() {
		return EntityType.TIMEDRECORD;
	}
}