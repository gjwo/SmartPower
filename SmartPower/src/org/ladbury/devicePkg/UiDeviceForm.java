package org.ladbury.devicePkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JLabel;

import org.ladbury.cataloguePkg.CatalogueEntry;
import org.ladbury.consumptionPatternPkg.ConsumptionPattern;
import org.ladbury.devicePkg.Device.DeviceType;
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
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

public class UiDeviceForm extends UiDataFormPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 965231311769985153L;
	private static final JLabel lblType 		= new JLabel("Type:");
	private static final JLabel lblName 		= new JLabel("Name:");
	private static final JLabel lblCatalogue 	= new JLabel("Catalogue");
	private final JLabel lblConsumption 		= new JLabel("Consumption:");
	private final JLabel lblComponentOf 		= new JLabel("Part of:");
	private final JLabel lblComponents 			= new JLabel("Parts:");
	private final JLabel lblRoom 				= new JLabel("Room:");
	private final JLabel lblPattern 			= new JLabel("Pattern:");
	private final JLabel lblActivity 			= new JLabel("Activity:");

	private final JTextField txtName 			= new JTextField();
	
	private final JComboBox<DeviceType> 	comboType 	= new JComboBox<>();

	private final JFormattedTextField fTxtConsumption	= new JFormattedTextField(NumberFormat.getIntegerInstance());	
	
	private final JCheckBox chckbxPortable 		= new JCheckBox("Portable");
	
	private UiEntitySelector	comboPattern 		= new UiEntitySelector(this, EntityType.PATTERN);
	private UiEntitySelector 	comboItemCatalogue	= new UiEntitySelector(this, EntityType.CATALOGUE);
	private	UiEntitySelector 	comboRoom 			= new UiEntitySelector(this, EntityType.ROOM);
	private UiEntitySelector 	comboParentDevice 	= new UiEntitySelector(this, EntityType.PARENT_DEVICE);
	
	private UiEntityList 		listComponents;
	private	long[]				componentIds;
	private UiEntityList 		listActivity;
	private	long[]				activityIds;

	private int		deviceRow;
	private Device 	device;

	//
	//Constructor
	//
	public UiDeviceForm(ActionListener al) {
		super(al);
		device = new Device();
		deviceRow =-1;
		setFieldsChanged(false);
		
		getFormPanel().add(lblName, "2, 2");
		getFormPanel().add(txtName, "4, 2, fill, default");
		getFormPanel().add(lblType, "6, 2");
		comboType.setModel(new DefaultComboBoxModel<>(Device.DeviceType.values()));
		comboType.addActionListener(this);
		getFormPanel().add(comboType, "8, 2, fill, center");
		
		getFormPanel().add(lblRoom, "2, 4");
		getFormPanel().add(comboRoom.getCombo(), "4, 4, fill, default");
		getFormPanel().add(chckbxPortable, "6, 4");

		getFormPanel().add(lblConsumption, "2, 6");
		fTxtConsumption.setValue(0);
		getFormPanel().add(fTxtConsumption, "4, 6, fill, default");
		
		getFormPanel().add(lblPattern, "6, 6");
		getFormPanel().add(comboPattern.getCombo(), "8, 6, fill, default");
		
		getFormPanel().add(lblComponentOf, "2, 8");
		getFormPanel().add(comboParentDevice.getCombo(), "4, 8, fill, default");

		getFormPanel().add(lblComponents, "6, 8");
		listComponents 	= new UiEntityList(UiDialogueType.SINGLE_FORM,EntityType.DEVICE);//display device form if selected
		getFormPanel().add(listComponents.getListPane(), "8, 8, fill, default");
		
		getFormPanel().add(comboItemCatalogue.getCombo(), "4, 10, fill, default");
		getFormPanel().add(lblCatalogue, "2, 10");
		
		getFormPanel().add(lblActivity, "6, 10");
		listActivity 	= new UiEntityList(UiDialogueType.SINGLE_FORM,EntityType.ACTIVITY);//display activity form if selected
		getFormPanel().add(listActivity.getListPane(), "8, 10, fill, default");		
	}
	
	//
	// listener for changes to type
	//
	@Override
	public void actionPerformed(ActionEvent e) {
    	device.setType(DeviceType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}

	private Device selectedParent(int row){
		if ((row>=0)&& (SmartPower.getMain().getData().getDevices().size()>=0)){
			return SmartPower.getMain().getData().getDevices().get(row);
		}
		return null;
	}
	
	private ConsumptionPattern selectedPattern(int row){
		if ((row>=0)&& (SmartPower.getMain().getData().getConsumptionPatterns().size()>=0)){
			return SmartPower.getMain().getData().getConsumptionPatterns().get(row);
		}
		return null;
	}
	
	private CatalogueEntry selectedCatalogueItem(int row){
		if ((row>=0)&& (SmartPower.getMain().getData().getCatalogue().size()>=0)){
			return SmartPower.getMain().getData().getCatalogue().get(row);
		}
		return null;
	}

	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.DEVICE;
	}
	@Override
	public void displayObject(int row){
		if ((row>=0) && (row<getDevices().size())){
			deviceRow = row;
			if (!haveFieldsChanged())device = getDevices().get(row); // don't overwrite changed data
			displayDeviceFields();
			getSelector().setSelectedIndex(deviceRow); //calls fireActionEvent
			repaint();
		}else{
			device = new Device();
			deviceRow = -1;
			displayDeviceFields();
			getSelector().setSelectedIndex(deviceRow); //calls fireActionEvent
		}
	}
	public void displayDeviceFields(){
		int i;
		
		//set up simple values
		if (device.name()!=null){ txtName.setText(device.name());	}
		else txtName.setText("<Name>");		
		fTxtConsumption.setValue(device.getConsumption());
		chckbxPortable.setSelected(device.isPortable());
		
		// set up combo box lists + initial value selection
		comboType.setSelectedIndex(device.getType().ordinal());
		if (device.getComponentOf() == null ) comboParentDevice.setSelectedIndex(-1);
		else comboParentDevice.setSelectedIndex(SmartPower.getMain().getData().getDevices().findID(device.getComponentOf().id()));
		if (device.getCatalogueEntry() == null )comboItemCatalogue.setSelectedIndex(-1);
		else comboItemCatalogue.setSelectedIndex(SmartPower.getMain().getData().getCatalogue().findID(device.getCatalogueEntry().id()));
		if (device.getPattern() == null ) comboPattern.setSelectedIndex(-1);
		else comboPattern.setSelectedIndex(SmartPower.getMain().getData().getConsumptionPatterns().findID(device.getPattern().id()));
		
		// set up lists
		if (device.componentCount() > 0){
			componentIds = new long[device.componentCount()]; //might have changed whilst this dialogue open
			for(i=0;i<device.componentCount();i++){
				componentIds[i]=device.getComponent(i).id();
			}
		} else componentIds = null;
		listComponents.populateList(componentIds);

		if (device.activityCount() > 0){
			activityIds = new long[device.activityCount()]; //might have changed whilst this dialogue open
			for(i=0;i<device.activityCount();i++){
				activityIds[i]=device.getComponent(i).id();
			}
		} else activityIds = null;
		listComponents.populateList(activityIds);
	}
	@Override
	public void saveObject(int row) {
		//combo field have already been updated in action performed
		//list fields and ID are not updateable
		device.setName(txtName.getText());
		device.setConsumption(((Number)fTxtConsumption.getValue()).intValue());
		device.setPortable(chckbxPortable.isSelected());
		if (row >0){
			getDevices().update(row,device);
		}
	}
	@Override
	public void deleteObject(int row) {
		getDevices().remove(row);
		deviceRow = -1;
		device = new Device();
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		//combo field have already been updated in action performed
		//list fields and ID are not updateable
		device.setName(txtName.getText());
		device.setConsumption(((Number) fTxtConsumption.getValue()).intValue());
		device.setPortable(chckbxPortable.isSelected());
		if (getDevices().add(device)){
			this.repaint();
		}
	}
	//
	// method to deal which changes to combobox values (type handled separately)
	//
	@Override
	public void comboSelect(EntityType selectorType,int selectedIndex){
		switch (selectorType){
    		case PATTERN:{device.setPattern(selectedPattern(selectedIndex));setFieldsChanged(true); break;}
    		case PARENT_DEVICE:{device.setComponentOf(selectedParent(selectedIndex));setFieldsChanged(true); break;}
    		case CATALOGUE:{device.setCatalogueEntry(selectedCatalogueItem(selectedIndex));setFieldsChanged(true); break;}
			default: return;
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<Device> getDevices(){
		return  SmartPower.getMain().getData().getDevices();
	}
}
