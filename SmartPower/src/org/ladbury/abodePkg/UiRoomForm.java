package org.ladbury.abodePkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.abodePkg.Room.RoomType;
import org.ladbury.behaviourPkg.Cluster;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntitySelector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UiRoomForm extends UiDataFormPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 965231311769985153L;
	private static final JLabel lblFill = new JLabel("  ");
	private static final JLabel lblType = new JLabel("Type:");
	private static final JLabel lblName = new JLabel("Name:");
	private static final JLabel lblAbode = new JLabel("Abode:");
	private static final JLabel lblCluster = new JLabel("Cluster");

	private JTextField 			txtName;
	private UiEntitySelector 	comboAbode 	= new UiEntitySelector(this,EntityType.ABODE);
	private UiEntitySelector	comboCluster= new UiEntitySelector(this,EntityType.CLUSTER);
	private JComboBox<RoomType> comboType = new JComboBox<>();
	private int					roomRow;
	private Room				room;
	
	//
	//Constructor
	//
	public UiRoomForm(ActionListener al)  {
		super(al); // listens for buttons
		room = new Room();
		roomRow =-1;
		setFieldsChanged(false);

		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblType, "2, 4");
		comboType.setModel(new DefaultComboBoxModel<>(RoomType.values()));
		comboType.addActionListener(this);
		
		getFormPanel().add(comboType, "4, 4, fill, center");
		
		getFormPanel().add(lblName, "2, 6");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 6, fill, default");
		txtName.setColumns(10);
		
		getFormPanel().add(lblAbode, "6, 6");
		getFormPanel().add(comboAbode.getCombo(), "8, 6, fill, default");
				
		getFormPanel().add(lblCluster, "2, 8");
		getFormPanel().add(comboCluster.getCombo(), "4, 8, fill, default");
	}
	private Abode selectedAbode(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getAbodes().size()>0)){
			return SmartPower.getMain().getData().getAbodes().get(row);
		}
		return null;
	}
	private Cluster selectedCluster(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getClusters().size()>0)){
			return SmartPower.getMain().getData().getClusters().get(row);
		}
		return null;
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.ROOM;
	}

	@Override
	public void displayObject(int row){
		if (getRooms().rangeCheck(row)){
			roomRow = row;
			if (!haveFieldsChanged()) room = getRooms().get(row);
		}else{
			roomRow = -1;
			if (!haveFieldsChanged()) room = new Room();
		}	
		comboType.setSelectedIndex(room.getType().ordinal());
		txtName.setText(room.name());
		getSelector().setSelectedIndex(roomRow); //calls fireActionEvent
		repaint();
	}
	@Override
	public void saveObject(int row) {
		room.setType((RoomType)comboType.getSelectedItem());
		room.setName(txtName.getText());
		if (getRooms().rangeCheck(row)){
			getRooms().update(row,room);
		}
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		room.setType((RoomType)comboType.getSelectedItem());
		room.setName(txtName.getText());
		if (getRooms().add(room)){
			this.repaint();
		}
	}
	//
	// method to deal which changes to combobox values (type handled separately)
	//
	@Override
	public void comboSelect(EntityType selectorType,int selectedIndex){
		switch(selectorType){
			case ABODE:{
				room.setAbode(selectedAbode(selectedIndex));
				setFieldsChanged(true); break;
			}
	   		case CLUSTER:{	
	   			room.setCluster(selectedCluster(selectedIndex));
	   			setFieldsChanged(true); break;
	   		}
	   		default:
		}
	}
	//
	// Method to handle changes to type combobox
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		room.setType(RoomType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}
	//
	// Supporting methods
	//
	private PersistentList<Room> getRooms(){
		return  SmartPower.getMain().getData().getRooms();
	}
}