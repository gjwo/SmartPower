package org.ladbury.periodPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.ladbury.periodPkg.WeekdayType.DayOfWeek;
import org.ladbury.periodPkg.WeekdayType.DayCategory;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;

public class UiWeekdayTypeForm 	extends UiDataFormPanel
							implements ActionListener{

	private static final long serialVersionUID = 4960670894284423499L;
	private static final JLabel lblFill 		= new JLabel("  ");
	private static final JLabel	lblDayofWeek 	= new JLabel("Day of Week:");
	private static final JLabel lblType 		= new JLabel("Type:");

	private int			weekdayTypeRow;
	private WeekdayType weekdayType;
	private JComboBox<DayCategory> 		comboType 		= new JComboBox<>();
	private JComboBox<DayOfWeek> 	comboDayOfWeek 	= new JComboBox<>();

	public UiWeekdayTypeForm(ActionListener al) {
		super(al);
		
		weekdayType = new WeekdayType();
		weekdayTypeRow =-1;
		setFieldsChanged(false);
		
		getFormPanel().add(lblFill, "1, 1");
		getFormPanel().add(lblDayofWeek, "2, 4");
		getFormPanel().add(lblType, "2, 4");
		comboDayOfWeek.setModel(new DefaultComboBoxModel<>(WeekdayType.DayOfWeek.values()));
		comboDayOfWeek.addActionListener(this);
		getFormPanel().add(comboDayOfWeek, "4, 4, 5, 1");
		
		getFormPanel().add(lblType, "2, 6");
		comboType.setModel(new DefaultComboBoxModel<>(WeekdayType.DayCategory.values()));
		comboType.addActionListener(this);
		getFormPanel().add(comboType, "4, 6, 5, 1");
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.WEEKDAYTYPE;
	}
	@Override
	public void displayObject(int row){
		if (getWeekdayTypes().rangeCheck(row)){
			weekdayTypeRow = row;
			if (!haveFieldsChanged()) weekdayType = getWeekdayTypes().get(row);
			//;
		}else{
			if (!haveFieldsChanged()) weekdayType = new WeekdayType();
			weekdayTypeRow = -1;
			//;
			//setObjectTextFields();
		}
		getSelector().setSelectedIndex(weekdayTypeRow); //calls fireActionEvent
	}
	@Override
	public void saveObject(int row) {
//		setObjectTextFields();
		if (getWeekdayTypes().rangeCheck(row)){
			getWeekdayTypes().update(row,weekdayType);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getWeekdayTypes().remove(row);
		weekdayTypeRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
//		setObjectTextFields();
		if (getWeekdayTypes().add(weekdayType)){
			this.repaint();
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<WeekdayType> getWeekdayTypes(){
		return  SmartPower.getMain().getData().getWeekdayTypes();
	}
//	private void setObjectTextFields(){
		//weekdayType.setName(txtWeekdayType.getText());
//	}
	//
	// Method to handle changes to type combo-boxes
	//
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==comboType){
			weekdayType.setType(DayCategory.values()[comboType.getSelectedIndex()]);
			setFieldsChanged(true);
		} else{
			weekdayType.setWeekday((DayOfWeek.values()[comboDayOfWeek.getSelectedIndex()]));
			setFieldsChanged(true);
		}
			
	}
}