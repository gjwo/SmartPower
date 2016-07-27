package org.ladbury.periodPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.periodPkg.TimePeriod.PeriodType;
import org.ladbury.periodPkg.WeekdayType.DayCategory;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntityList;
import org.ladbury.userInterfacePkg.UiStyle;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

public class UiTimePeriodForm extends UiDataFormPanel implements ActionListener{

	private static final long serialVersionUID = 4131072002256824747L;
	private static final JLabel lblName 	= new JLabel("Name:");
	private static final JLabel lbltype		= new JLabel("Type:");
	private static final JLabel	lblStart	= new JLabel("Start time:");
	private static final JLabel	lblEnd		= new JLabel("End time:");
	private static final JLabel lbldayCat	= new JLabel("Day Catagory:");
	private static final JLabel lblUsage 	= new JLabel("Usage when:");
	private static final JLabel	lblHabits	= new JLabel("Habits:");

	private JTextField 					txtName;
	
	private final JFormattedTextField 	fTxtStart 	= new JFormattedTextField(UiStyle.timeFormat);
	private final JFormattedTextField 	fTxtEnd 	= new JFormattedTextField(UiStyle.timeFormat);
	
	private JComboBox<DayCategory> 		comboDayCategory	= new JComboBox<>();
	private JComboBox<PeriodType> 	comboTimePeriodType = new JComboBox<>();
	
	private final JCheckBox 	chkBoxUsage = new JCheckBox();
	
	private UiEntityList		listHabits	= new UiEntityList(UiDialogueType.SINGLE_FORM,EntityType.HABIT); //display event table if selected
	private long[]				habitIds;
	
	private TimePeriod 			timePeriod;
	private int					timePeriodRow;

	
	public UiTimePeriodForm(ActionListener al) {
		super(al);
		timePeriodRow =-1;
		timePeriod = new TimePeriod();
		setFieldsChanged(false);
		
		getFormPanel().add(lblName, "2, 2");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 2, fill, default");
		txtName.setColumns(10);	
		
		getFormPanel().add(lbltype, "6, 2");
		comboTimePeriodType.setModel(new DefaultComboBoxModel<>(PeriodType.values()));
		comboTimePeriodType.addActionListener(this);
		getFormPanel().add(comboTimePeriodType, "8, 2, fill, default");
				
		getFormPanel().add(lblStart, "2, 4");
		fTxtStart.setValue(timePeriod.startTime());		
		getFormPanel().add(fTxtStart, "4, 4, fill, default");
		
		getFormPanel().add(lblEnd, "6, 4");	
		fTxtEnd.setValue(timePeriod.endTime());
		getFormPanel().add(fTxtEnd, "8, 4, fill, default");
		
		getFormPanel().add(lbldayCat, "2, 6");
		comboDayCategory.setModel(new DefaultComboBoxModel<>(WeekdayType.DayCategory.values()));
		comboDayCategory.addActionListener(this);
		getFormPanel().add(comboDayCategory, "4, 6, 5, 1");

		getFormPanel().add(lblUsage, "6, 6");	
		getFormPanel().add(chkBoxUsage, "7, 6");
		
		getFormPanel().add(lblHabits, "2, 8");	
		getFormPanel().add(listHabits.getListPane(), "4, 8, 5, 1");
		lblUsage.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private void copyUifields(){
		//combo fields have already been updated in action performed
		//list fields and ID are not updateable
		timePeriod.setName(txtName.getText());
		timePeriod.setStartTime(((Number) fTxtStart.getValue()).longValue());
		timePeriod.setEndTime(((Number) fTxtEnd.getValue()).longValue());
		timePeriod.setUsage(chkBoxUsage.isSelected());
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.TIMEPERIOD;
	}
	@Override
	public void displayObject(int row){
		int 	i;

		if ((row>=0) && (row<getTimePeriod().size())){ //load period from data
			timePeriodRow = row;
			if (!haveFieldsChanged()) timePeriod = getTimePeriod().get(row); // don't overwrite changed data
		}else{ //set up blank data
			timePeriodRow = -1;			
			timePeriod = new TimePeriod();	
		}
			//set up display from local period
			txtName.setText(timePeriod.name());
			fTxtStart.setText(timePeriod.startTime().toString());
			fTxtEnd.setText(timePeriod.endTime().toString());
			listHabits.populateList(null);
			habitIds = new long [timePeriod.getHabitCount()];
			for(i=0;i<timePeriod.getHabitCount();i++){
				habitIds[i] = timePeriod.getHabit(i).id();
			}
			listHabits.populateList(habitIds);
			comboDayCategory.setSelectedIndex(timePeriod.getDayCategory().ordinal());
			comboTimePeriodType.setSelectedIndex(timePeriod.getType().ordinal());
			chkBoxUsage.setSelected(timePeriod.used());
			
			getSelector().setSelectedIndex(timePeriodRow); //calls fireActionEvent
			repaint();
	}
	@Override
	public void saveObject(int row) {
		copyUifields();
		if (getTimePeriod().rangeCheck(row)){
			getTimePeriod().update(row,timePeriod);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getTimePeriod().remove(row);
		timePeriodRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		copyUifields();
		if (getTimePeriod().add(timePeriod)){
			this.repaint();
		}
	}
	//
	// Method to handle changes to type combo-boxes
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==comboDayCategory){
			timePeriod.setDayCategory(DayCategory.values()[comboDayCategory.getSelectedIndex()]);
			setFieldsChanged(true);
		} else{
			timePeriod.setType((PeriodType.values()[comboTimePeriodType.getSelectedIndex()]));
			setFieldsChanged(true);
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<TimePeriod> getTimePeriod(){
		return  SmartPower.getMain().getData().getTimePeriods();
	}
}