package org.ladbury.userInterfacePkg;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;


public class UiDataTabbedFormDialogue extends JDialog
    							implements ActionListener {

	private UiDataTabbedFormPanel tabbedPanel;

	private static final long serialVersionUID = 1L;
	
    public UiDataTabbedFormDialogue(Frame parent, EntityType type, int row) {
        super(parent);
        this.setTitle(type.toString()+" Form");
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
        	setResizable(true);
        	tabbedPanel = new UiDataTabbedFormPanel(this);
       		this.getContentPane().add(tabbedPanel,null);    		
       		tabbedPanel.selectTab(type);
       		this.tabbedPanel.getTab(type).displayObject(row);
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
    	action = tabbedPanel.currentTab().actionFromEventSource(e.getSource());
    	switch (action){
    	case CANCEL: dispose(); //Close the Dialog
    		break;
    	case SELECT: tabbedPanel.currentTab().select();
    		break;
		case DELETE: tabbedPanel.currentTab().delete();
					 dispose(); //Close the dialogue to reset selector etc
			break;
//     	case NEW:	tabbedPanel.currentTab().add();
 //   		break;
    	case SAVE:	tabbedPanel.currentTab().save();
    		break;
		case UNKNOWN:
			break;
		default:
			break;	
    	}
    }
    public UiDataFormPanel formPanel(){
    	return this.tabbedPanel.currentTab();
    }
    
	public EntityType getFormType() {
		return this.tabbedPanel.currentTab().type();
	}
}