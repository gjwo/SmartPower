package org.ladbury.periodPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.periodPkg.TimePeriod.PeriodType;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiStyle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;

public class UiCalendarPeriodForm extends UiDataFormPanel implements ActionListener{

	private static final long serialVersionUID = 4131072002256824747L;
	private static final JLabel lblName 	= new JLabel("Name:");
	private static final JLabel lbltype		= new JLabel("Type:");
	private static final JLabel	lblStart	= new JLabel("Start date:");
	private static final JLabel	lblEnd		= new JLabel("End date:");

	private JTextField 					txtName;
	
	private final JFormattedTextField 	fTxtStart 	= new JFormattedTextField(UiStyle.dateFormat);
	private final JFormattedTextField 	fTxtEnd 	= new JFormattedTextField(UiStyle.dateFormat);
	
	private JComboBox<PeriodType> 	comboCalendarPeriodType = new JComboBox<>();
	
	private CalendarPeriod 	calendarPeriod;
	private int				calendarPeriodRow;

	
	public UiCalendarPeriodForm(ActionListener al) {
		super(al);
		calendarPeriod = new CalendarPeriod();
		setFieldsChanged(false);
		
		getFormPanel().add(lblName, "2, 2");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 2, fill, default");
		txtName.setColumns(10);	
		
		getFormPanel().add(lbltype, "6, 2");
		comboCalendarPeriodType.setModel(new DefaultComboBoxModel<>(PeriodType.values()));
		comboCalendarPeriodType.addActionListener(this);
		getFormPanel().add(comboCalendarPeriodType, "8, 2, fill, default");
				
		getFormPanel().add(lblStart, "2, 4");
		fTxtStart.setValue(calendarPeriod.start());		
		getFormPanel().add(fTxtStart, "4, 4, fill, default");
		
		getFormPanel().add(lblEnd, "6, 4");	
		fTxtEnd.setValue(calendarPeriod.end());
		getFormPanel().add(fTxtEnd, "8, 4, fill, default");
		
	}

	private void copyUifields(){
		//combo fields have already been updated in action performed
		//list fields and ID are not updateable
		calendarPeriod.setName(txtName.getText());
		calendarPeriod.setStart(((Number) fTxtStart.getValue()).longValue());
		calendarPeriod.setEnd(((Number) fTxtEnd.getValue()).longValue());
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.CALENDARPERIOD;
	}
	@Override
	public void displayObject(int row){

		if ((row>=0) && (row<getCalendarPeriod().size())){ //load period from data
			calendarPeriodRow = row;
			if (!haveFieldsChanged()) calendarPeriod = getCalendarPeriod().get(row); // don't overwrite changed data
		}else{ //set up blank data
			calendarPeriodRow = -1;			
			calendarPeriod = new CalendarPeriod();	
		}
			//set up display from local period
			txtName.setText(calendarPeriod.name());
			fTxtStart.setText(calendarPeriod.start().toString());
			fTxtEnd.setText(calendarPeriod.end().toString());
			comboCalendarPeriodType.setSelectedIndex(calendarPeriod.getType().ordinal());
			
			getSelector().setSelectedIndex(calendarPeriodRow); //calls fireActionEvent
			repaint();
	}
	@Override
	public void saveObject(int row) {
		copyUifields();
		if (getCalendarPeriod().rangeCheck(row)){
			getCalendarPeriod().update(row,calendarPeriod);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getCalendarPeriod().remove(row);
		calendarPeriodRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		copyUifields();
		if (getCalendarPeriod().add(calendarPeriod)){
			this.repaint();
		}
	}
	//
	// Method to handle changes to type combo-boxes
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		calendarPeriod.setType((PeriodType.values()[comboCalendarPeriodType.getSelectedIndex()]));
		setFieldsChanged(true);
	}
	//
	// Supporting methods
	//
	private PersistentList<CalendarPeriod> getCalendarPeriod(){
		return  SmartPower.getMain().getData().getCalendarPeriods();
	}
}