package org.ladbury.userInterfacePkg;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;

import org.ladbury.abodePkg.UiAbodeForm;
import org.ladbury.abodePkg.UiRoomForm;
import org.ladbury.behaviourPkg.UiClusterForm;
import org.ladbury.behaviourPkg.UiHabitForm;
import org.ladbury.cataloguePkg.UiCatEntryForm;
import org.ladbury.cataloguePkg.UiCategoryForm;
import org.ladbury.cataloguePkg.UiMakeForm;
import org.ladbury.consumptionPatternPkg.UiConPatternForm;
import org.ladbury.deviceActivityPkg.UiActivityForm;
import org.ladbury.devicePkg.UiDeviceForm;
import org.ladbury.meterPkg.UiMeterForm;
import org.ladbury.periodPkg.UiCalendarPeriodForm;
import org.ladbury.periodPkg.UiTimePeriodForm;
import org.ladbury.periodPkg.UiWeekdayTypeForm;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.readingEventPkg.UiEventForm;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;


public class UiDataSingleFormDialogue extends JDialog
    							implements ActionListener {

	private UiDataFormPanel formPanel;
	private EntityType	entityType;

	private static final long serialVersionUID = 1L;
	
    public UiDataSingleFormDialogue(Frame parent, EntityType type, int row) {
        super(parent);
        entityType = type;
        this.setTitle(entityType.toString()+" Form");
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
        	setResizable(true);
        	switch (type){ //create panel and add this as the action listener
			case ABODE: 		formPanel = new UiAbodeForm(this); break;
			case ACTIVITY:		formPanel = new UiActivityForm(this); break;
			case CATALOGUE:		formPanel = new UiCatEntryForm(this); break;
			case DEVICE:		formPanel = new UiDeviceForm(this);	break;
			case EVENTS:		formPanel = new UiEventForm(this);	break;
			case PATTERN: 		formPanel = new UiConPatternForm(this); break;
			case READINGS: 		cancel(); return; //	break;
			case ROOM:			formPanel = new UiRoomForm(this); break;
			case MAKE:			formPanel = new UiMakeForm(this); break;
			case CATEGORY:		formPanel = new UiCategoryForm(this); break;
			case WEEKDAYTYPE:	formPanel = new UiWeekdayTypeForm(this); break;
			case TIMEPERIOD:	formPanel = new UiTimePeriodForm(this); break;
			case CALENDARPERIOD:	formPanel = new UiCalendarPeriodForm(this); break;
			case HABIT:			formPanel = new UiHabitForm(this); break;
			case CLUSTER:		formPanel = new UiClusterForm(this); break;	
			case METER:			formPanel = new UiMeterForm(this); break;
			//case METRIC:		formPanel = new UiMetricForm(this);break
			default:		cancel();
							return; //bail out, no form created
        	}
        	if (formPanel!=null){
        		this.getContentPane().add(formPanel,null);
        		this.formPanel.displayObject(row);
        	}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        pack();
    }
	/**Overridden so we can exit when window is closed*/
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            cancel();
        }
        super.processWindowEvent(e);
    }

    /**Close the dialog*/
    protected void cancel() {
        dispose(); //Close the Dialog
    }

    /**action a user event dependent on source*/
    @Override
    public void actionPerformed(ActionEvent e) {
     	SPAction action;
    	action = formPanel.actionFromEventSource(e.getSource());
    	switch (action){
    	case CANCEL: dispose(); //Close the Dialog
    		break;
    	case SELECT: formPanel.select();
    		break;
		case DELETE: formPanel.delete();
					 dispose(); //Close the dialogue to reset selector etc
			break;
//     	case NEW:	formPanel.add();
//    		break;
    	case SAVE:	formPanel.save();
    		break;
		case UNKNOWN:
			break;
		default:
			break;	
    	}
    }
    public UiDataFormPanel formPanel(){
    	return this.formPanel;
    }
	public EntityType getTableType() {
		return entityType;
	}
}