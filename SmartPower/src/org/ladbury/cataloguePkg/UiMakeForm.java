package org.ladbury.cataloguePkg;

import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import javax.swing.JTextField;

public class UiMakeForm extends UiDataFormPanel{

	private static final long serialVersionUID = -9025076752841686749L;
	private static final JLabel lblFill 	= new JLabel("  ");
	private static final JLabel	lblMake 	= new JLabel("Make:");

	private JTextField 	txtMake;
	private int			makeRow;
	private Make 		make;

	public UiMakeForm(ActionListener al) {
		super(al);
		
		make = new Make();
		makeRow =-1;
		setFieldsChanged(false);

		//setTitle("Catalogue");
		
		getFormPanel().add(lblFill, "1, 1");
		getFormPanel().add(lblMake, "2, 4");
		txtMake = new JTextField();
		getFormPanel().add(txtMake, "4, 4, 5, 1");
		txtMake.setColumns(10);		
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.MAKE;
	}
	@Override
	public void displayObject(int row){
		if (getMakes().rangeCheck(row)){
			makeRow = row;
			if (!haveFieldsChanged()) make = getMakes().get(row);
			txtMake.setText(make.name());
		}else{
			if (!haveFieldsChanged()) make = new Make();
			makeRow = -1;
			txtMake.setText("<Make>");
			setObjectTextFields();
		}
		getSelector().setSelectedIndex(makeRow); //calls fireActionEvent
	}
	@Override
	public void saveObject(int row) {
		setObjectTextFields();
		if (getMakes().rangeCheck(row)){
			getMakes().update(row,make);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getMakes().remove(row);
		makeRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		setObjectTextFields();
		if (getMakes().add(make)){
			this.repaint();
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<Make> getMakes(){
		return  SmartPower.getMain().getData().getMakes();
	}
	private void setObjectTextFields(){
		make.setName(txtMake.getText());
	}
}