package org.ladbury.userInterfacePkg;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;

import org.ladbury.persistentData.PersistentData.EntityType;


public class UiDataTabbedTableDialogue
    extends JDialog
    implements ActionListener {

	public static enum SPAction{UNKNOWN, ADD_DEVICE, CANCEL, CLEAR_FILTER, DELETE, NEW, SAVE, SELECT};
	private UiDataTabbedTablePanel dpanel;

	private static final long serialVersionUID = 1L;
	private static final String frame_title = "Tabbed Data Table Dialogue";
	
    public UiDataTabbedTableDialogue(Frame parent) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
        	this.setTitle(frame_title);
        	setResizable(true);
            dpanel = new UiDataTabbedTablePanel(this); //create panel and add this as the action listener
            this.getContentPane().add(dpanel,null);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        pack();
    }

    /**Overridden so we can exit when window is closed*/
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

    /**Close the dialog on a button event*/
    public void actionPerformed(ActionEvent e) {
    	//Device d;
    	//EntityType tt;
    	SPAction action;
    	action = dpanel.actionFromEventSource(e.getSource());
    	switch (action){
    	case CANCEL: dispose(); //Close the Dialog
    		break;
		case CLEAR_FILTER: dpanel.currentTab().clearFilter();
			break;
		case DELETE:
			break;
    	case ADD_DEVICE: 
            //d = new Device(SmartPower.m_devices.nextTag(),0);
            //SmartPower.m_devices.add(d);
    		dpanel.getModel(EntityType.DEVICE).fireTableDataChanged();
    		break;
    	case NEW:
    		break;
    	case SAVE:
    		break;
		case SELECT:
			//use the table type to determine what to do
			break;
		case UNKNOWN:
			break;
		default:
			break;	
    	}
    }
    public UiDataTabbedTablePanel dataPanel(){
    	return this.dpanel;
    }
}
