package org.ladbury.consumptionPatternPkg;

import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTablePanel;

public class UiConPatternTablePanel extends UiDataTablePanel{
	
	private static final long serialVersionUID = 201L;
	
	private UiConPatternTableLink	model; //this must change when replicating to match the object link table
	TableRowSorter<UiConPatternTableLink> sorter;

	public UiConPatternTablePanel(ActionListener al){
		super(al);
		model = new UiConPatternTableLink();
		getTable().setModel(model);
		sorter = new TableRowSorter<UiConPatternTableLink>(model);
		getTable().setRowSorter(sorter);

		getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(2).setMaxWidth(100);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(4).setMaxWidth(50);
		getTable().getColumnModel().getColumn(5).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(5).setMaxWidth(50);
		getTable().getColumnModel().getColumn(5).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(5).setMaxWidth(40);
		getTable().setCellSelectionEnabled(true);
	}
	public AbstractTableModel getModel() {
		return model;
	}
	@Override
	public EntityType type() {
		return EntityType.PATTERN;
	}
	@Override
	public void cellSelect(JTable target, int row, int col) {
		//called when the cell is double clicked
		if (col == 0){ //the ID column
			SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(),EntityType.PATTERN,row); //fire up a form dialogue
			repaint();
		}
	}
}
