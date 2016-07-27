package org.ladbury.abodePkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.abodePkg.Abode.AbodeType;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntityList;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UiAbodeForm extends UiDataFormPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203705496038902621L;
	/**
	 * 
	 */
	private static final JLabel lblFill = new JLabel("  ");
	private static final JLabel	lblMonitor = new JLabel("Monitor:");
	private static final JLabel lblType = new JLabel("Type:");
	private static final JLabel lblAddress = new JLabel("Address:");
	private static final JLabel lblRooms = new JLabel("Rooms:");

	private JTextField 				txtMonitor;
	private JTextField 				txtAddress;
	private JComboBox<AbodeType> 	comboType = new JComboBox<>();
	private UiEntityList			listRooms;
	private int 					abodeRow = 0;
	private Abode 					abode;
	private long[]					roomIds;
	
	public UiAbodeForm(ActionListener al) {
		super(al);
		abodeRow = -1;
		abode = new Abode();
		setFieldsChanged(false);
		
		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblType, "2, 4");
		comboType.setModel(new DefaultComboBoxModel<>(AbodeType.values()));
		comboType.addActionListener(this);
		
		getFormPanel().add(comboType, "4, 4, fill, center");
		
		getFormPanel().add(lblMonitor, "2, 6");
		txtMonitor = new JTextField();
		getFormPanel().add(txtMonitor, "4, 6, fill, default");
		txtMonitor.setColumns(10);
		
		getFormPanel().add(lblAddress, "2, 8");
		txtAddress = new JTextField();
		getFormPanel().add(txtAddress, "4, 8, 5, 1, fill, default");
		txtAddress.setColumns(10);
		
		getFormPanel().add(lblRooms, "2, 10");
		listRooms = new UiEntityList(UiDialogueType.SINGLE_FORM, EntityType.ROOM);
		getFormPanel().add(listRooms.getListPane(), "4, 10, fill, center");
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.ABODE;
	}
	@Override
	public void displayObject(int row){
		int i;
		abodeRow = row;
		if (getAbodes().rangeCheck(row)){
			if (!haveFieldsChanged()) abode = getAbodes().get(row);
			
			roomIds = new long[abode.roomCount()];
			for(i=0;i<abode.roomCount();i++){
				roomIds[i]= abode.getRoom(i).id();
			}
			listRooms.populateList(roomIds);
			comboType.setSelectedIndex(abode.getType().ordinal());
			txtAddress.setText(abode.getAddress()); 
			getSelector().setSelectedIndex(abodeRow); //calls fireActionEvent
		}else{
			abodeRow = -1;
			comboType.setSelectedIndex(0);
			txtMonitor.setText("<Monitor>");
			txtAddress.setText("<Address>");
			listRooms.populateList(null);
			getSelector().setSelectedIndex(abodeRow); //calls fireActionEvent
		}
	}
	@Override
	public void saveObject(int row) {
		/*int i; //commented out code to add all rooms to this abode
		for(i=0;i<getRooms().size();i++){
		abode.addRoom(getRooms().get(i));
		}*/
		abode.setType((AbodeType)comboType.getSelectedItem());
		abode.setAddress(txtAddress.getText());
		if (getAbodes().rangeCheck(row)){
			getAbodes().update(row,abode);
		} 
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		if (getAbodes().rangeCheck(row)){
			getAbodes().remove(row);
			abodeRow = -1;
			abode = new Abode();
			setFieldsChanged(false);
		}
	}
	@Override
	public void addObject() {
		abode.setType((AbodeType)comboType.getSelectedItem());
		abode.setAddress(txtAddress.getText());
		if (getAbodes().add(abode)){
			this.repaint();
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<Abode> getAbodes(){
		return  SmartPower.getMain().getData().getAbodes();
	}
	//
	// Method to handle changes to type combobox
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		abode.setType(AbodeType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}
}