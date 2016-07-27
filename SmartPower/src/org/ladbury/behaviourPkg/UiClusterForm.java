package org.ladbury.behaviourPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.behaviourPkg.Cluster.ClusterType;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntityList;
import org.ladbury.userInterfacePkg.UiEntitySelector;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UiClusterForm extends UiDataFormPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 965231311769985153L;
	private static final JLabel lblFill 	= new JLabel("  ");
	private static final JLabel lblType 	= new JLabel("Type:");
	private static final JLabel lblName 	= new JLabel("Name:");
	private static final JLabel lblDevice 	= new JLabel("Device:");
	private static final JLabel lblCluster 	= new JLabel("Cluster:");
	private static final JLabel lblHabits	= new JLabel("Habits");

	private JTextField 				txtName;
	private UiEntitySelector 		comboDevice = new UiEntitySelector(this,EntityType.TIMEPERIOD);
	private UiEntitySelector 		comboHabit 	= new UiEntitySelector(this,EntityType.HABIT);
	private UiEntityList			listDevices;
	private UiEntityList			listHabits;
	private JComboBox<ClusterType> 	comboType = new JComboBox<>();
	
	private long[]		deviceIds;
	private long[]		habitIds;
	private int			clusterRow;
	private Cluster		cluster;
	
	//
	//Constructor
	//
	public UiClusterForm(ActionListener al)  {
		super(al); // listens for buttons
		cluster = new Cluster();
		clusterRow =-1;
		setFieldsChanged(false);

		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblName, "2, 4");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 4, fill, default");
		txtName.setColumns(10);
		
		getFormPanel().add(lblType, "6, 4");
		comboType.setModel(new DefaultComboBoxModel<>(ClusterType.values()));
		comboType.addActionListener(this);	
		getFormPanel().add(comboType, "8, 4, fill, center");
		
		getFormPanel().add(lblDevice, "2, 6");
		getFormPanel().add(comboDevice.getCombo(), "4, 6, fill, default");
				
		getFormPanel().add(lblCluster, "6, 6");
		getFormPanel().add(comboHabit.getCombo(), "8, 6, fill, default");
		
		listDevices = new UiEntityList(UiDialogueType.SINGLE_FORM, EntityType.DEVICE);
		getFormPanel().add(listDevices.getListPane(), "4, 7, 5, 1");
		
		getFormPanel().add(lblHabits, "2, 8");
		listHabits = new UiEntityList(UiDialogueType.SINGLE_FORM, EntityType.HABIT);
		getFormPanel().add(listHabits.getListPane(), "4, 8, 2, 1");

	}
	private org.ladbury.devicePkg.Device selectedDevice(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getDevices().size()>0)){
			return SmartPower.getMain().getData().getDevices().get(row);
		}
		return null;
	}
	private Habit selectedHabit(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getHabits().size()>0)){
			return SmartPower.getMain().getData().getHabits().get(row);
		}
		return null;
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.HABIT;
	}

	@Override
	public void displayObject(int row){
		int i;
		if (getClusters().rangeCheck(row)){
			clusterRow = row;
			if (!haveFieldsChanged()) cluster = getClusters().get(row);
		}else{
			clusterRow = -1;
			if (!haveFieldsChanged()) cluster = new Cluster();
		}
		comboType.setSelectedIndex(cluster.getType().ordinal());
		txtName.setText(cluster.name());
		
		if(cluster.deviceCount() >0){
			deviceIds = new long[cluster.deviceCount()];
			for(i=0;i<cluster.deviceCount();i++){
				deviceIds[i]= cluster.getDevice(i).id();
			}
		}else deviceIds = null; 
		listDevices.populateList(deviceIds);

		if(cluster.habitCount() >0){
			habitIds = new long[cluster.habitCount()];
			for(i=0;i<cluster.habitCount();i++){
				habitIds[i]= cluster.getDevice(i).id();
			}
		}else habitIds = null; 
		listDevices.populateList(habitIds);
		
		getSelector().setSelectedIndex(clusterRow); //calls fireActionEvent
		repaint();
	}
	@Override
	public void saveObject(int row) {
		cluster.setType((ClusterType)comboType.getSelectedItem());
		cluster.setName(txtName.getText());
		if (getClusters().rangeCheck(row)){
			getClusters().update(row,cluster);
		}
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		cluster.setType((ClusterType)comboType.getSelectedItem());
		cluster.setName(txtName.getText());
		if (getClusters().add(cluster)){
			this.repaint();
		}
	}
	//
	// method to deal which changes to combobox values (type handled separately)
	//
	@Override
	public void comboSelect(EntityType selectorType,int selectedIndex){
		switch(selectorType){
		case HABIT: { // TODO: add & remove code based on selection
				cluster.addHabit(selectedHabit(selectedIndex));
				setFieldsChanged(true);
				break;
		}
		case DEVICE:{
				cluster.addDevice(selectedDevice(selectedIndex));
				setFieldsChanged(true);
				break;
		}
		default:
		}
	}
	//
	// Method to handle changes to type combobox
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		cluster.setType(ClusterType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}
	//
	// Supporting methods
	//
	private PersistentList<Cluster> getClusters(){
		return  SmartPower.getMain().getData().getClusters();
	}
}