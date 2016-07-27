package org.ladbury.consumptionPatternPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.NumberFormat;

import javax.swing.JLabel;

import org.ladbury.consumptionPatternPkg.ConsumptionPattern.ConsumptionType;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntityList;
import org.ladbury.userInterfacePkg.UiStyle;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class UiConPatternForm extends UiDataFormPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 965231311769985153L;
	private static final JLabel lblFill = new JLabel("  ");
	private static final JLabel lblType = new JLabel("Type:");
	private static final JLabel lblName = new JLabel("Name:");
	private static final JLabel lblDevices = new JLabel("Devices:");

	private JTextField 					txtName;
	private JComboBox<ConsumptionType> 	comboType = new JComboBox<>();
	private UiEntityList				listDevices;
	private int							patternRow;
	private final JLabel lblOnTime = new JLabel("On time");
	private final JLabel lblOnSpread = new JLabel("On spread");
	private final JLabel lblOffTime = new JLabel("Off time");
	private final JLabel lblOffSpread = new JLabel("Off Spread");
	private final JFormattedTextField fTxtOnTime = new JFormattedTextField(UiStyle.timestampFormat);
	private final JFormattedTextField fTxtOnSpread = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private final JFormattedTextField fTxtOffTime = new JFormattedTextField(UiStyle.timestampFormat);
	private final JFormattedTextField fTxtOffSpread = new JFormattedTextField(NumberFormat.getIntegerInstance());
	
	private long				onTime 		= 0L;
	private int 				onSpread 	= 0;
	private long 				offTime 	= 0L;
	private int 				offSpread 	= 0;
	private ConsumptionPattern 	cp;
	private long[]				deviceIds;
	
	public UiConPatternForm(ActionListener al) {
		super(al);
		patternRow =-1;
		cp = new ConsumptionPattern();

		setFieldsChanged(false);
		
		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblName, "2, 4");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 4, fill, default");
		txtName.setColumns(10);	
		getFormPanel().add(lblType, "6, 4");
		comboType.setModel(new DefaultComboBoxModel<>(ConsumptionType.values()));
		comboType.addActionListener(this);
		
		getFormPanel().add(comboType, "8, 4, fill, center");
		
		getFormPanel().add(lblOnTime, "2, 6");
		fTxtOnTime.setValue(new Timestamp(onTime));		
		getFormPanel().add(fTxtOnTime, "4, 6, fill, default");
		
		getFormPanel().add(lblOnSpread, "6, 6");
		fTxtOnSpread.setValue(new Integer(onSpread));
		getFormPanel().add(fTxtOnSpread, "8, 6, fill, default");
		
		getFormPanel().add(lblOffTime, "2, 8");
		fTxtOffTime.setValue(new Timestamp(offTime));
		getFormPanel().add(fTxtOffTime, "4, 8, fill, default");
		
		getFormPanel().add(lblOffSpread, "6, 8");		
		fTxtOffSpread.setValue(new Integer(offSpread));
		getFormPanel().add(fTxtOffSpread, "8, 8, fill, default");
		
		getFormPanel().add(lblDevices, "2, 10");
		listDevices = new UiEntityList(UiDialogueType.SINGLE_FORM, EntityType.DEVICE);
		getFormPanel().add(listDevices.getListPane(), "4, 10, fill, default");
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.PATTERN;
	}
	@Override
	public void displayObject(int row){
		int i;
		if (getPatterns().rangeCheck(row)){
			if (!haveFieldsChanged()) cp=getPatterns().get(row);
			patternRow = row;
			onTime = cp.getOnTime();
			onSpread = cp.getOnSpread();
			offTime = cp.getOffTime();
			offSpread = cp.getOffSpread();
			
			deviceIds = new long[cp.deviceCount()];
			for(i=0;i<cp.deviceCount();i++){
				deviceIds[i]= cp.getDevice(i).id();
			}
			listDevices.populateList(deviceIds);
			
			comboType.setSelectedIndex(cp.getType().ordinal());
			txtName.setText("<Name>");
			//txtName.setText(r.getName());
			
			getSelector().setSelectedIndex(row); //calls fireActionEvent
			repaint();
		}else{
			patternRow = -1;
			comboType.setSelectedIndex(0);
			txtName.setText("<Name>");
			onTime = 0L;
			onSpread = 0;
			offTime = 0L;
			offSpread = 0;
			listDevices.populateList(null);
			getSelector().setSelectedIndex(patternRow); //calls fireActionEvent
		}
	}
	@Override
	public void saveObject(int row) {
		//cp.setName(txtName.getText());
		cp.setOnTime(onTime);
		cp.setOnSpread(onSpread);
		cp.setOffTime(offTime);
		cp.setOffSpread(offSpread);

		if (getPatterns().rangeCheck(row)){
			getPatterns().update(row,cp);
		}
	}
	@Override
	public void deleteObject(int row) {
		getPatterns().remove(row);
		patternRow = -1;
	}
	@Override
	public void addObject() {
		cp.setOnTime(onTime);
		cp.setOnSpread(onSpread);
		cp.setOffTime(offTime);
		cp.setOffSpread(offSpread);
		//cp.setName(txtName.getText());
		if (getPatterns().add(cp)){
			this.repaint();
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<ConsumptionPattern> getPatterns(){
		return  SmartPower.getMain().getData().getConsumptionPatterns();
	}
	public Object getDeviceList(){
		return this.listDevices;
	}
	//
	// methods to deal with combo-box selection of single value linked entities & type
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		cp.setType(ConsumptionType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}
}