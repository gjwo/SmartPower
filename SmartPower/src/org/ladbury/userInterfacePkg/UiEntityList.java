package org.ladbury.userInterfacePkg;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

public class UiEntityList implements ListSelectionListener {
	private DefaultListModel<String>	lModel = new DefaultListModel<>();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JList<String> listEvents = new JList<>(lModel);
	private EntityType 		listType;
	private UiDialogueType	uiDialogueType;
	private long[]			ids;
	private int				idCount = -1;
	
	public UiEntityList(UiDialogueType dialoguetype, EntityType listType){
		
		this.listType = listType;
		this.uiDialogueType = dialoguetype;
		listEvents.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEvents.getSelectionModel().addListSelectionListener(this);
		lModel.addElement(UiStyle.NONE);
		listEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		scrollPane.setViewportView(listEvents);	
	}
	
	public void populateList(long[] ids){
		int i;
		int row;
		lModel.removeAllElements();
		if (ids == null) idCount = 0;
		else idCount = ids.length;
		if (idCount == 0){
			lModel.addElement(UiStyle.NONE);
		}
		else{
			this.ids=ids;
			for (i = 0; i<idCount; i++){
				row = SmartPower.getMain().getData().getDataList(listType).findID(ids[i]);
				lModel.addElement(SmartPower.getMain().getData().getDataList(listType).get(row).name());
			}
		}
	}

	public JComponent getListPane(){
		return scrollPane;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selection = 0;
		int row = 0;
	    if (!e.getValueIsAdjusting()) {
	    	selection =listEvents.getSelectedIndex();
	        if ((selection >= 0) && (idCount>0)){ //no valid selection or selected empty
	        	// we have a valid selection
				row = SmartPower.getMain().getData().getDataList(listType).findID(ids[selection]);
				if (uiDialogueType == UiDialogueType.SINGLE_TABLE){
					SmartPower.getMain().getFrame().displayTableDialogue(SmartPower.getMain().getFrame(), listType, row);
				}
				if (uiDialogueType == UiDialogueType.SINGLE_FORM){
					SmartPower.getMain().getFrame().displayFormDialogue(SmartPower.getMain().getFrame(), listType, row);
				}
	        }
	    }
	}	
}
