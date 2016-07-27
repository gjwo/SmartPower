package org.ladbury.behaviourPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.behaviourPkg.Habit.HabitType;
import org.ladbury.periodPkg.TimePeriod;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntitySelector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UiHabitForm extends UiDataFormPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 965231311769985153L;
	private static final JLabel lblFill = new JLabel("  ");
	private static final JLabel lblType = new JLabel("Type:");
	private static final JLabel lblName = new JLabel("Name:");
	private static final JLabel lblTimePeriod = new JLabel("TimePeriod:");
	private static final JLabel lblCluster = new JLabel("Cluster:");

	private JTextField 				txtName;
	private UiEntitySelector 		comboTimePeriod = new UiEntitySelector(this,EntityType.TIMEPERIOD);
	private UiEntitySelector 		comboCluster 	= new UiEntitySelector(this,EntityType.CLUSTER);
	private JComboBox<HabitType> 	comboType = new JComboBox<>();
	
	private int					habitRow;
	private Habit				habit;
	
	//
	//Constructor
	//
	public UiHabitForm(ActionListener al)  {
		super(al); // listens for buttons
		habit = new Habit();
		habitRow =-1;
		setFieldsChanged(false);

		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblName, "2, 4");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 4, fill, default");
		txtName.setColumns(10);
		
		getFormPanel().add(lblType, "6, 4");
		comboType.setModel(new DefaultComboBoxModel<>(HabitType.values()));
		comboType.addActionListener(this);	
		getFormPanel().add(comboType, "8, 4, fill, center");
		
		getFormPanel().add(lblTimePeriod, "2, 6");
		getFormPanel().add(comboTimePeriod.getCombo(), "4, 6, fill, default");
				
		getFormPanel().add(lblCluster, "6, 6");
		getFormPanel().add(comboCluster.getCombo(), "8, 6, fill, default");
	}
	private TimePeriod selectedTimePeriod(int row){
		if ((row>= 0) && (SmartPower.getMain().getData().getTimePeriods().size()>0)){
			return SmartPower.getMain().getData().getTimePeriods().get(row);
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
		return EntityType.HABIT;
	}

	@Override
	public void displayObject(int row){
		if (getHabits().rangeCheck(row)){
			habitRow = row;
			if (!haveFieldsChanged()) habit = getHabits().get(row);
		}else{
			habitRow = -1;
			if (!haveFieldsChanged()) habit = new Habit();
		}
		comboType.setSelectedIndex(habit.getType().ordinal());
		txtName.setText(habit.name());
		getSelector().setSelectedIndex(habitRow); //calls fireActionEvent
		repaint();
	}
	@Override
	public void saveObject(int row) {
		habit.setType((HabitType)comboType.getSelectedItem());
		habit.setName(txtName.getText());
		if (getHabits().rangeCheck(row)){
			getHabits().update(row,habit);
		}
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		habit.setType((HabitType)comboType.getSelectedItem());
		habit.setName(txtName.getText());
		if (getHabits().add(habit)){
			this.repaint();
		}
	}
	//
	// method to deal which changes to combobox values (type handled separately)
	//
	@Override
	public void comboSelect(EntityType selectorType,int selectedIndex){
		switch(selectorType){
		case HABIT: {
						habit.setTimePeriod(selectedTimePeriod(selectedIndex));
						setFieldsChanged(true);
					}
		case TIMEPERIOD:{
					habit.setCluster(selectedCluster(selectedIndex));
					setFieldsChanged(true);
					}
		default:
		}
	}
	//
	// Method to handle changes to type combobox
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		habit.setType(HabitType.values()[comboType.getSelectedIndex()]);
    	setFieldsChanged(true);
	}
	//
	// Supporting methods
	//
	private PersistentList<Habit> getHabits(){
		return  SmartPower.getMain().getData().getHabits();
	}
}