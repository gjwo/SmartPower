package org.ladbury.abodePkg;

import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTablePanel;

public class UiAbodeTablePanel extends UiDataTablePanel{
	
	private static final long serialVersionUID = 201L;
	
	private UiAbodeTableLink	model; //this must change when replicating to match the object link table
	private TableRowSorter<UiAbodeTableLink> sorter;
	//private RowFilter<UiAbodeTableLink,Integer> RoomTypeFilter;

	public UiAbodeTablePanel(ActionListener al){
		super(al);
		// construct table type specific items
		model = new UiAbodeTableLink();
		sorter = new TableRowSorter<UiAbodeTableLink>(model);
		//sorter.setRowFilter(RoomTypeFilter);
		getTable().setRowSorter(sorter);
		getTable().setModel(model);
		getTable().getColumnModel().getColumn(0).setPreferredWidth(50);
		getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
		getTable().getColumnModel().getColumn(2).setMaxWidth(100);
		getTable().getColumnModel().getColumn(3).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(4).setMaxWidth(40);
		getTable().getColumnModel().getColumn(5).setPreferredWidth(40);
		getTable().getColumnModel().getColumn(5).setMaxWidth(40);
	}
	@Override
	public AbstractTableModel getModel() {
		return model;
	}
	@Override
	public void cellSelect(JTable target, int row, int col) {
		//called when the cell is double clicked
		if (col == 0){ //the ID column
			//SmartPower.getMain().getFrame().displayLog("mouse clicked on "+row+"\n\r");
			SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(),EntityType.ABODE,row); //fire up a form dialogue
			repaint();
		}
	}
	@Override
	public EntityType type() {
		return EntityType.ABODE;
	}
}
