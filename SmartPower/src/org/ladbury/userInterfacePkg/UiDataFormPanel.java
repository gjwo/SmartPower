package org.ladbury.userInterfacePkg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
//import javax.swing.JScrollPane;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public abstract class UiDataFormPanel extends JPanel{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2884952647806550503L;
	private static final JLabel	lblId = new JLabel("ID:");
	private static final JLabel lblFill = new JLabel("  ");
	private static final JLabel lblFill1 = new JLabel("  ");
	private static final JLabel lblFill2 = new JLabel("  ");
	private static final JLabel lblFill3 = new JLabel("  ");
	private static final JLabel lblFill4 = new JLabel("  ");
	private JButton 		btnCancel;
	private JButton 		btnDelete;
//	private JButton 		btnNew;
	private JButton			btnSave;
	private JPanel			titlePanel;				
	private JPanel 			buttonPanel;
	private JPanel			formPanel;
	private JLabel 			lblTitle;
	private UiEntitySelector	formSelector;
	private int					selectedIndex;
	private boolean 		fieldsChanged;

	public UiDataFormPanel(ActionListener al){
		setLayout(new BorderLayout(0, 0));
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setMinimumSize(new Dimension(this.getWidth(),30));
		lblTitle = new JLabel(" "); //spacer
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(UiStyle.TITLE_FONT);
		titlePanel.add(lblTitle,BorderLayout.CENTER);
		this.add(titlePanel,BorderLayout.NORTH);
		formPanel = new JPanel();
		formPanel.setFont(UiStyle.NORMAL_FONT);
		this.add(formPanel, BorderLayout.CENTER);
		//scrollPane.setColumnHeaderView(formPanel);
		formPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(30dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(30dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(30dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(30dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(20dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(20dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(20dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(20dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(20dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,}));

		getFormPanel().add(lblFill, "1, 1");
		getFormPanel().add(lblFill1, "3, 3");
		getFormPanel().add(lblFill2, "5, 5");
		getFormPanel().add(lblFill3, "7, 7");
		getFormPanel().add(lblFill4, "9, 9");

		// create selector
		getFormPanel().add(lblId, "2, 1");
		formSelector = new UiEntitySelector(this,this.type());
		getFormPanel().add(formSelector.getCombo(), "4, 1, 5, 1");
		
		// create button panel
		buttonPanel = new JPanel();
		buttonPanel.setMinimumSize(new Dimension(100, 100));
		buttonPanel.setPreferredSize(new Dimension(100, 150));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.add(buttonPanel, BorderLayout.EAST); // on right of panel 

		// add buttons to panel
//		btnNew = new JButton("New");
//		btnNew.addActionListener(al);
//		buttonPanel.add(btnNew);
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(al);
		buttonPanel.add(btnDelete);
		btnSave = new JButton("Save");
		btnSave.addActionListener(al);
		buttonPanel.add(btnSave);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(al);
		buttonPanel.add(btnCancel);
	}
	
	public boolean haveFieldsChanged() {
		return fieldsChanged;
	}
	public void setFieldsChanged(boolean fieldsChanged) {
		this.fieldsChanged = fieldsChanged;
	}
//	public void addButton(JButton b){
//		buttonPanel.add(b);
//	}
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	public JButton getBtnCancel() {
		return btnCancel;
	}
//	public JButton getBtnNew() {
//		return btnNew;
//	}
	public JButton getBtnSave() {
		return btnSave;
	}
	public UiEntitySelector getSelector(){
		return formSelector;
	}

	//return action to be taken of one of this objects buttons were clicked
	//Override if extra buttons are added
	public SPAction actionFromEventSource(Object o){
		if(o==getSelector())return SPAction.SELECT;
		if(o==getBtnCancel())return SPAction.CANCEL;
		if(o==getBtnSave())return SPAction.SAVE;
		if(o==getBtnDelete()) return SPAction.DELETE;
//		if(o==getBtnNew()) return SPAction.NEW;
		return SPAction.UNKNOWN;  
	}
	public void setTitle(String t){
		lblTitle.setText(t);
	}
	public JPanel getFormPanel() {
		return formPanel;
	}
	public void setFormPanel(JPanel formPanel) {
		this.formPanel = formPanel;
	}
	//Standard actions for buttons & selector
	public void select(){
		selectedIndex = getSelector().getSelectedIndex();
		setFieldsChanged(false); //force re-fetch of data
		displayObject(selectedIndex);
		repaint();
	}
	public void save(){
		setFieldsChanged(true); //prevent re-fetch of data
		selectedIndex = getSelector().getSelectedIndex();
		if (selectedIndex == -1) addObject();
		else saveObject(selectedIndex);
		getBtnCancel().doClick(); //close the dialogue
	}
	public void delete(){
		selectedIndex = getSelector().getSelectedIndex();
		deleteObject(selectedIndex);
		getBtnCancel().doClick(); //close the dialogue
	}
/*	public void add(){
		addObject();
		getBtnCancel().doClick(); //close the dialogue
	}*/
	public int getComboEntryCount(){
		return SmartPower.getMain().getData().getDataList(this.type()).size();
	}
	public String[] getComboStrings(){ //String of key + values
		int i;
		String[] selections = new String[getComboEntryCount()+1];
		for(i=0; i<getComboEntryCount();i++){
			selections[i] = SmartPower.getMain().getData().getDataList(this.type()).get(i).idString();
		}
		selections[i] = "Create a new entry";
		return selections;

	}
	//panel Specific methods
	public void comboSelect(EntityType selectorType, int selectedIndex){} //optional, override if needed
	public abstract EntityType type(); 			//returns the specific panel type
	public abstract void displayObject(int row);//Form display action
	public abstract void saveObject(int row);	//Save the object
	//Delete the object
	public void deleteObject(int row){
		SmartPower.getMain().getData().getDataList(this.type()).remove(row);
	}
	public abstract void addObject();	//Add a new object
}