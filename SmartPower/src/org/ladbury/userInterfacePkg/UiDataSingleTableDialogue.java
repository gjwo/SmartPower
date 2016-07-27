package org.ladbury.userInterfacePkg;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;

import org.ladbury.meterPkg.UiMetricTablePanel;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.readingEventPkg.UiEventTablePanel;
import org.ladbury.readingsPkg.UiReadingTablePanel;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;


public class UiDataSingleTableDialogue
    extends JDialog
    implements ActionListener {

	private UiDataTablePanel tablePanel;

	private static final long serialVersionUID = 1L;
	private EntityType tableType = EntityType.UNDEFINED;
	
    public UiDataSingleTableDialogue(Frame parent,EntityType entityType, int row) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
        	setResizable(true);
        	switch (entityType){
        	case EVENTS:{
        				tablePanel = new UiEventTablePanel(this); //create panel and add this as the action listener
        				this.getContentPane().add(tablePanel,null);
        				break;			
        				}
        	case READINGS:{
        				tablePanel = new UiReadingTablePanel(this);
        				this.getContentPane().add(tablePanel,null);
        				break;			
        				}
        	case TIMEDRECORD:{
				tablePanel = new UiMetricTablePanel(this);
				this.getContentPane().add(tablePanel,null);
				break;			
				}
			default:	return; // do nothing;
        	}
            setTableType(entityType);
        	this.setTitle(entityType.toString() +" Table");
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

    /**Close the dialog on a button event*/
    public void actionPerformed(ActionEvent e) {
    	//Device d;
    	//EntityType tt;
    	SPAction action;
    	action = tablePanel.actionFromEventSource(e.getSource());
    	switch (action){
    	case CANCEL: dispose(); //Close the Dialog
    		break;
		case CLEAR_FILTER: tablePanel.clearFilter();
			break;
		case DELETE:
			break;
    	case ADD_DEVICE: 
            //d = new Device(SmartPower.m_devices.nextTag(),0);
            //SmartPower.m_devices.add(d);
    		//tablePanel.getModel(EntityType.DEVICE).fireTableDataChanged();
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
    public UiDataTablePanel dataPanel(){
    	return this.tablePanel;
    }

	public EntityType getTableType() {
		return tableType;
	}

	public void setTableType(EntityType tableType) {
		this.tableType = tableType;
	}
}
