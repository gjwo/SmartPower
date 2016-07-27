package org.ladbury.cataloguePkg;

import java.awt.event.ActionListener;

import javax.swing.JLabel;

import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import javax.swing.JTextField;

public class UiCategoryForm extends UiDataFormPanel{
	private static final long serialVersionUID = -4133354607655741626L;
	private static final JLabel lblFill 	= new JLabel("  ");
	private static final JLabel	lblCategory 	= new JLabel("Category:");

	private JTextField 	txtCategory;
	private int			categoryRow;
	private Category 	category;

	public UiCategoryForm(ActionListener al) {
		super(al);
		
		category = new Category();
		categoryRow =-1;
		setFieldsChanged(false);

		//setTitle("Catalogue");
		
		getFormPanel().add(lblFill, "1, 1");
		getFormPanel().add(lblCategory, "2, 4");
		txtCategory = new JTextField();
		getFormPanel().add(txtCategory, "4, 4, 5, 1");
		txtCategory.setColumns(10);		
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.CATEGORY;
	}
	@Override
	public void displayObject(int row){
		if (getCategories().rangeCheck(row)){
			categoryRow = row;
			if (!haveFieldsChanged()) category = getCategories().get(row);
			txtCategory.setText(category.name());
		}else{
			if (!haveFieldsChanged()) category = new Category();
			categoryRow = -1;
			txtCategory.setText("<Category>");
			setObjectTextFields();
		}
		getSelector().setSelectedIndex(categoryRow); //calls fireActionEvent
	}
	@Override
	public void saveObject(int row) {
		setObjectTextFields();
		if (getCategories().rangeCheck(row)){
			getCategories().update(row,category);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getCategories().remove(row);
		categoryRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		setObjectTextFields();
		if (getCategories().add(category)){
			this.repaint();
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<Category> getCategories(){
		return  SmartPower.getMain().getData().getCategories();
	}
	private void setObjectTextFields(){
		category.setName(txtCategory.getText());
	}
}