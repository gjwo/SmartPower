package org.ladbury.userInterfacePkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;

//<L extends PersistentList<?>, E extends Persistable<?>>
public class UiEntitySelector  implements ActionListener {
	private JComboBox<String> 	combo;
	private int					entityCount;
	private String[]			selections;
	private EntityType			selectorType;
	private UiDataFormPanel 	form;
	private	int					selectedIndex;
	private	boolean				isFormSelector;
	
	public UiEntitySelector(UiDataFormPanel form, EntityType type){
		int i;
		this.selectorType = type;
		this.form = form;
		isFormSelector = (selectorType == form.type());
		entityCount = SmartPower.getMain().getData().getDataList(this.selectorType).size();
		selections = new String[entityCount+1];
		if (isFormSelector){
			for(i=0; i<entityCount;i++){
				selections[i+1] = SmartPower.getMain().getData().getDataList(this.selectorType).get(i).idString();
			}
			selections[0] = UiStyle.CREATE_NEW_ENTRY;
		}	
		else{
			for(i=0; i<entityCount;i++){
				selections[i+1] = SmartPower.getMain().getData().getDataList(this.selectorType).get(i).name();
			}
			selections[0] = UiStyle.NONE;
		}
		combo = new JComboBox<>(selections);
		combo.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int index;
		index = combo.getSelectedIndex();
		selectedIndex = index-1;//-1 is new or none, 0 to n = row
		if (isFormSelector){
			form.select();
		}else{
			form.comboSelect(selectorType,selectedIndex);
		}
		
		
	}
	public JComboBox<String> getCombo() {
		return combo;
	}

	public EntityType type() {
		return selectorType;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(int index) {
		selectedIndex = index;//-1 is new or none, 0 to n = row
		combo.setSelectedIndex(index+1);//0 is new or none, 1 to n = row
	}

}
