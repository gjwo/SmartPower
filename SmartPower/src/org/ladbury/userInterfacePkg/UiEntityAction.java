package org.ladbury.userInterfacePkg;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

public class UiEntityAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1846525799290838736L;
	private UiFrame			dialogueHolder;
	private UiDialogueType 	uiDialogueType;
	private EntityType		entityType;

	UiEntityAction(	UiFrame 		parent,
					UiDialogueType 	dlgType,
					EntityType 		eType,
					String			name){
		super();
		this.uiDialogueType = dlgType;
		this.entityType = eType;
		this.dialogueHolder = parent;
		putValue(Action.NAME, name);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (dialogueHolder==null)return;
		if (uiDialogueType == UiDialogueType.SINGLE_FORM){
			dialogueHolder.displayFormDialogue(dialogueHolder, entityType, 0);
		}else{
			if(uiDialogueType == UiDialogueType.SINGLE_TABLE){
				dialogueHolder.displayTableDialogue(dialogueHolder, entityType, 0);
			}
		}
	}
}