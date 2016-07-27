package org.ladbury.deviceActivityPkg;

import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.JLabel;

import org.ladbury.devicePkg.Device;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntityList;
import org.ladbury.userInterfacePkg.UiEntitySelector;
import org.ladbury.userInterfacePkg.UiStyle;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;


public class UiActivityForm extends UiDataFormPanel{

	private static final long serialVersionUID = 4131072002256824747L;
	
	private static final JLabel 				lblFill = new JLabel("  ");
	private static final JLabel 				lblName = new JLabel("Name:");
	private static final JLabel 				lblDevice = new JLabel("Device:");
	private static final JLabel 				lblReadings = new JLabel("Events:");
	private static final JLabel 				lblStart = new JLabel("Start time");
	private static final JLabel 				lblStop = new JLabel("Stop time");
	private static final JFormattedTextField 	fTxtStart = new JFormattedTextField(UiStyle.timestampFormat);
	private static final JFormattedTextField 	fTxtStop = new JFormattedTextField(UiStyle.timestampFormat);

	private JTextField 			txtName;
	private UiEntitySelector 	comboDevice 	= new UiEntitySelector(this,EntityType.DEVICE);
	
	private UiEntityList		listEvents = new UiEntityList(UiDialogueType.SINGLE_TABLE,EntityType.EVENTS); //display event table if selected
	
	private long				onTime = 0L;
	private long 				offTime =0L;
	
	private int					activityRow;
	private final JLabel 		lblConsumption = new JLabel("Power Consumption");
	private final JTextField 	txtConsumption = new JTextField();
//	private long[]				eventIds;
	private DeviceActivity 		activity;

	
	public UiActivityForm(ActionListener al) {
		super(al);
		activityRow =-1;
		activity = new DeviceActivity();
		setFieldsChanged(false);
		
		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblName, "2, 2");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 2, fill, default");
		txtName.setColumns(10);	
		
		getFormPanel().add(lblDevice, "6, 2");
		getFormPanel().add(comboDevice.getCombo(), "8, 2, fill, default");
		
		getFormPanel().add(lblStart, "2, 4");
		fTxtStart.setValue(new Timestamp(onTime));		
		getFormPanel().add(fTxtStart, "4, 4, fill, default");
		
		getFormPanel().add(lblStop, "6, 4");
		
		fTxtStop.setValue(new Timestamp(offTime));
		getFormPanel().add(fTxtStop, "8, 4, fill, default");
		
		getFormPanel().add(lblReadings, "2, 6");	
		getFormPanel().add(listEvents.getListPane(), "4, 6, 3, 6, fill, fill");
		lblConsumption.setHorizontalAlignment(SwingConstants.CENTER);
		
		getFormPanel().add(lblConsumption, "8, 6");
		txtConsumption.setHorizontalAlignment(SwingConstants.CENTER);
		txtConsumption.setColumns(10);
		txtConsumption.setFocusable(false);
		
		getFormPanel().add(txtConsumption, "8, 7");
	}
	private Device selectedDevice(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getDevices().size()>0)){
			return SmartPower.getMain().getData().getDevices().get(row);
		}
		return null;
	}

	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.ACTIVITY;
	}
	@Override
	public void displayObject(int row){

		if ((row>=0) && (row<getActivity().size())){
			activityRow = row;
			if (!haveFieldsChanged()) activity = getActivity().get(row); // don't overwrite changed data
			onTime = activity.start().getTime();
			offTime = activity.end().getTime();
			
			if ((SmartPower.getMain().getData().getDevices().size()>0) && activity.getDevice()!=null ){
				comboDevice.setSelectedIndex(SmartPower.getMain().getData().getDevices().findID(activity.getDevice().id()));
			}
			else{comboDevice.setSelectedIndex(-1);}
			txtName.setText("<Name>");
			txtName.setText(activity.name());

			listEvents.populateList(null);
			txtConsumption.setText(((Integer)SmartPower.getMain().getData().getActivity().get(row).getConsumption()).toString());	
			getSelector().setSelectedIndex(row); //calls fireActionEvent
			repaint();
		}else{
			activityRow = -1;
			comboDevice.setSelectedIndex(-1);
			txtName.setText("<Name>");
			onTime = 0L;
			offTime = 0L;
			txtConsumption.setText("0");
			listEvents.populateList(null);
			getSelector().setSelectedIndex(activityRow); //calls fireActionEvent
		}
	}
	@Override
	public void saveObject(int row) {
		//combo field have already been updated in action performed
		//list fields, name  and ID are not updateable
		activity.setStart(new Timestamp(onTime));
		activity.setEnd(new Timestamp(offTime));
		if (getActivity().rangeCheck(row)){
			getActivity().update(row,activity);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getActivity().remove(row);
		activityRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		activity.setStart(new Timestamp(onTime));
		activity.setEnd(new Timestamp(offTime));
		// name field is automatically generated therefore read only;
		if (getActivity().add(activity)){
			this.repaint();
		}
	}
	//
	// method to deal which changes to combobox values (type handled separately)
	//
	@Override
	public void comboSelect(EntityType selectorType,int selectedIndex){
		activity.setDevice(selectedDevice(selectedIndex));
   		setFieldsChanged(true);
	}
	//
	// Supporting methods
	//
	private PersistentList<DeviceActivity> getActivity(){
		return  SmartPower.getMain().getData().getActivity();
	}
}
