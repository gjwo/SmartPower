package org.ladbury.readingEventPkg;

import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.NumberFormat;

import javax.swing.JLabel;

import org.ladbury.abodePkg.Abode;
import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntitySelector;
import org.ladbury.userInterfacePkg.UiStyle;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

public class UiEventForm extends UiDataFormPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8416291603512575704L;
	/**
	 * 
	 */
	private static final JLabel lblTimestamp 	= new JLabel("Timestamp:");
	private final JLabel lblConsumption 		= new JLabel("Consumption:");
	private final JLabel lblAbode 				= new JLabel("Abode:");
	private final JLabel lblEvent 				= new JLabel("Event:");

	private final JTextField txtName 			= new JTextField();
	
	private final JFormattedTextField fTxtConsumption	= new JFormattedTextField(NumberFormat.getIntegerInstance());	
	private final JFormattedTextField fTxtTimestamp = new JFormattedTextField(UiStyle.timestampFormat);

	private final JCheckBox chckbxOnEdge 		= new JCheckBox("On");
	
	private	UiEntitySelector 	comboAbode 			= new UiEntitySelector(this, EntityType.ABODE);
	

	private int				eventRow;
	private ReadingEvent 	event;

	//
	//Constructor
	//
	public UiEventForm(ActionListener al) {
		super(al);
		event = new ReadingEvent();
		eventRow =-1;
		setFieldsChanged(false);
		
		getFormPanel().add(lblTimestamp, "2, 2");
		fTxtTimestamp.setValue(new Timestamp(0L));		
		getFormPanel().add(fTxtTimestamp, "4, 2, fill, default");

		getFormPanel().add(lblAbode, "2, 4");
		getFormPanel().add(comboAbode.getCombo(), "4, 4, fill, default");

		getFormPanel().add(lblConsumption, "2, 6");
		fTxtConsumption.setValue(0);
		getFormPanel().add(fTxtConsumption, "4, 6, fill, default");		
		getFormPanel().add(lblEvent,"6, 6");
		getFormPanel().add(chckbxOnEdge, "8, 6");
	}
	
	private Abode selectedAbode(int row){
		if ((row>=0)&& (SmartPower.getMain().getData().getAbodes().size()>=0)){
			return SmartPower.getMain().getData().getAbodes().get(row);
		}
		return null;
	}

	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.EVENTS;
	}
	@Override
	public void displayObject(int row){
		if ((row>=0) && (row<getEvents().size())){
			eventRow = row;
			if (!haveFieldsChanged())event = getEvents().get(row); // don't overwrite changed data
		}else{
			event = new ReadingEvent();
			eventRow = -1;
		}
		displayEventFields();
		getSelector().setSelectedIndex(eventRow); //calls fireActionEvent
		repaint();

	}
	public void displayEventFields(){
		//set up simple values
		if (event.name()!=null){ txtName.setText(event.name());	}
		else txtName.setText("<Name>");		
		fTxtConsumption.setValue(event.consumption());
		chckbxOnEdge.setSelected(event.isOn());
		
		// set up combo box lists + initial value selection
		if (event.getAbode() == null ) comboAbode.setSelectedIndex(-1);
		else comboAbode.setSelectedIndex(SmartPower.getMain().getData().getReadingEvents().findID(event.getAbode().id()));
				
	}
	@Override
	public void saveObject(int row) {
		//combo field have already been updated in action performed
		//list fields and ID are not updateable
		event.setTimestamp(new Timestamp(((Number)fTxtTimestamp.getValue()).longValue()));
		event.setConsumption(((Number)fTxtConsumption.getValue()).intValue());
		event.setEdge(chckbxOnEdge.isSelected());
		if (row >0){
			getEvents().update(row,event);
		}
	}
	@Override
	public void deleteObject(int row) {
		getEvents().remove(row);
		eventRow = -1;
		event = new ReadingEvent();
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		//combo field have already been updated in action performed
		//list fields and ID are not updateable
		event.setTimestamp(new Timestamp(((Number)fTxtTimestamp.getValue()).longValue()));
		event.setConsumption(((Number) fTxtConsumption.getValue()).intValue());
		event.setEdge(chckbxOnEdge.isSelected());
		if (getEvents().add(event)){
			this.repaint();
		}
	}
	//
	// method to deal which changes to combobox values (type handled separately)
	//
	@Override
	public void comboSelect(EntityType selectorType,int selectedIndex){
		switch (selectorType){
    		case ABODE: 	{event.setAbode(selectedAbode(selectedIndex));setFieldsChanged(true); break;}
			default: return;
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<ReadingEvent> getEvents(){
		return  SmartPower.getMain().getData().getReadingEvents();
	}
}
