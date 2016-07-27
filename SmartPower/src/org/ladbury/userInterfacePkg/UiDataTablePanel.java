package org.ladbury.userInterfacePkg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.AbstractTableModel;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;

public abstract class UiDataTablePanel extends JPanel{
	
	private static final long serialVersionUID = 201L;
	
	private JButton 			btnSelect;
	private JButton 			btnCancel;
	private JButton				btnClearFilter;
	private JScrollPane 		scrollPane;
	private JTable 				table;
	private JPanel 				buttonPanel;

	public UiDataTablePanel(ActionListener al){
		
		this.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		//table.setAutoCreateRowSorter(true); tried but problems later on
		table.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row,col;
				JTable target;
				if (e.getClickCount() == 2) {
					target = (JTable)e.getSource();
					row = target.convertRowIndexToModel(target.getSelectedRow()); //correct for sorting
					col = target.getSelectedColumn();
					cellSelect(target,row,col);
				}
			}
		} ) ;
		scrollPane.setRowHeaderView(table);
		scrollPane.setViewportView(table);

		// create button panel for Device tab
		buttonPanel = new JPanel();
		buttonPanel.setMinimumSize(new Dimension(100, 10));
		buttonPanel.setPreferredSize(new Dimension(100, 10));
		this.add(buttonPanel, BorderLayout.EAST); // on right of panel 
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// add buttons to panel
		btnSelect = new JButton("Select");
		btnSelect.addActionListener(al);
		buttonPanel.add(btnSelect);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(al);
		buttonPanel.add(btnCancel);
		btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.addActionListener(al);
		buttonPanel.add(btnClearFilter);
	}
	public JButton getBtnSelect() {
		return btnSelect;
	}
	public JButton getBtnCancel() {
		return btnCancel;
	}
	public JButton getBtnClearFilter() {
		return btnClearFilter;
	}
	public void addButton(JButton b){
		buttonPanel.add(b);
	}
	//return action to be taken of one of this objects buttons were clicked
	//override if extra buttons are added
	public SPAction actionFromEventSource(Object o){
		if(o==getBtnSelect())return SPAction.SELECT;
		if(o==getBtnCancel())return SPAction.CANCEL;
		if(o==getBtnClearFilter()) return SPAction.CLEAR_FILTER;
		return SPAction.UNKNOWN;  
	}
	public JTable getTable() {
		return table;
	}
	//table specific actions when a cell is selected
	public  void cellSelect(JTable target,int row, int col){
		//override if needed, otherwise no action
	}
	//clear all filters active on the table
	public void clearFilter(){
		//override if needed, otherwise no action

	}
	//return the type of the table this panel supports
	public abstract EntityType type(); //must be implemented for reach specific panel
	public abstract AbstractTableModel getModel(); //must be implemented for reach specific panel
}