package org.ladbury.meterPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.abodePkg.Abode;
import org.ladbury.meterPkg.Meter.MeterType;
import org.ladbury.meterPkg.Metric.MetricType;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntitySelector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UiMeterForm extends UiDataFormPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 965231311769985153L;
	private static final JLabel lblFill = new JLabel("  ");
	private static final JLabel lblType = new JLabel("Type:");
	private static final JLabel lblName = new JLabel("Name:");
	private static final JLabel lblAbode = new JLabel("Abode:");
	private static final JLabel lblMetric = new JLabel("Metric");

	private JTextField 			txtName;
	private UiEntitySelector 	comboAbode 	= new UiEntitySelector(this,EntityType.ABODE);
	private JComboBox<MetricType> comboMetric= new JComboBox<>();
	private JComboBox<MeterType> comboType = new JComboBox<>();
	private int					meterRow;
	private Meter				meter;
	
	//
	//Constructor
	//
	public UiMeterForm(ActionListener al)  {
		super(al); // listens for buttons
		meter = new Meter();
		meterRow =-1;
		setFieldsChanged(false);

		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblType, "2, 4");
		comboType.setModel(new DefaultComboBoxModel<>(MeterType.values()));
		comboType.addActionListener(this);
		
		getFormPanel().add(comboType, "4, 4, fill, center");
		
		getFormPanel().add(lblName, "2, 6");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 6, fill, default");
		txtName.setColumns(10);
		
		getFormPanel().add(lblAbode, "6, 6");
		getFormPanel().add(comboAbode.getCombo(), "8, 6, fill, default");
				
		getFormPanel().add(lblMetric, "2, 8");
		comboMetric.setModel(new DefaultComboBoxModel<>(MetricType.values()));
		comboMetric.addActionListener(this);
	}
	private Abode selectedAbode(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getAbodes().size()>0)){
			return SmartPower.getMain().getData().getAbodes().get(row);
		}
		return null;
	}
	private MetricType selectedMetric(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getMeters().size()>0)){
			return SmartPower.getMain().getData().getMeters().get(row).getMetric(comboMetric.getItemAt(row)).getType();
		}
		return null;
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.METER;
	}

	@Override
	public void displayObject(int row){
		if (getMeters().rangeCheck(row)){
			meterRow = row;
			if (!haveFieldsChanged()) meter = getMeters().get(row);
		}else{
			meterRow = -1;
			if (!haveFieldsChanged()) meter = new Meter();
		}	
		comboType.setSelectedIndex(meter.getType().ordinal());
		txtName.setText(meter.name());
		getSelector().setSelectedIndex(meterRow); //calls fireActionEvent
		repaint();
	}
	@Override
	public void saveObject(int row) {
		meter.setType((MeterType)comboType.getSelectedItem());
		meter.setName(txtName.getText());
		if (getMeters().rangeCheck(row)){
			getMeters().update(row,meter);
		}
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		meter.setType((MeterType)comboType.getSelectedItem());
		meter.setName(txtName.getText());
		if (getMeters().add(meter)){
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
				meter.setAbode(selectedAbode(selectedIndex));
				setFieldsChanged(true); break;
			}
	   		case METRIC:{	
	   			//meter.setMetric(selectedMetric(selectedIndex),);
	   			//setFieldsChanged(true); break;
	   		}
	   		default:
		}
	}
	//
	// Method to handle changes to type combobox
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		meter.setType(MeterType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}
	//
	// Supporting methods
	//
	private PersistentList<Meter> getMeters(){
		return  SmartPower.getMain().getData().getMeters();
	}
}