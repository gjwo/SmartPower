package org.ladbury.cataloguePkg;

import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JLabel;

import org.ladbury.persistentData.PersistentList;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiDataFormPanel;
import org.ladbury.userInterfacePkg.UiEntityList;
import org.ladbury.userInterfacePkg.UiEntitySelector;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;
import javax.swing.JTextField;

public class UiCatEntryForm extends UiDataFormPanel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4931528306375315755L;
	private static final JLabel lblFill 	= new JLabel("  ");
	private static final JLabel	lblName 	= new JLabel("Name:");
	private static final JLabel	lblMake 	= new JLabel("Make:");
	private static final JLabel lblModel 	= new JLabel("Model:");
	private static final JLabel lblCategory 	= new JLabel("Category:");
	private static final JLabel lblDescription 	= new JLabel("Description:");
	private static final JLabel lblDevices 	= new JLabel("Devices:");

	private UiEntitySelector comboMake 	= new UiEntitySelector(this,EntityType.MAKE);
	private UiEntitySelector comboCategory 	= new UiEntitySelector(this,EntityType.CATEGORY);
	private JTextField 					txtName;
	private JTextField 					txtModel;
	private JTextField 					txtDescription;
	private UiEntityList				listDevices;
	private int							catalogueRow;
	private long[]						deviceIds;
	private CatalogueEntry 				catalogueEntry;

	public UiCatEntryForm(ActionListener al) {
		super(al);
		
		catalogueEntry = new CatalogueEntry();
		catalogueRow =-1;
		setFieldsChanged(false);

		//setTitle("Catalogue");
		
		getFormPanel().add(lblFill, "1, 1");
		
		getFormPanel().add(lblMake, "2, 4");
		getFormPanel().add(comboMake.getCombo(), "4, 4, default, fill");
		
		getFormPanel().add(lblModel, "6, 4");
		txtModel = new JTextField();
		getFormPanel().add(txtModel, "8, 4, default, fill");
		txtModel.setColumns(10);
		
		getFormPanel().add(lblName, "2, 6");
		txtName = new JTextField();
		getFormPanel().add(txtName, "4, 6, default, fill");
		txtName.setColumns(10);
		
		getFormPanel().add(lblCategory, "6, 6");
		getFormPanel().add(comboCategory.getCombo(), "8, 6, default, fill");
		
		getFormPanel().add(lblDescription, "2, 8");
		txtDescription = new JTextField();
		getFormPanel().add(txtDescription, "4, 8, 5, 1");
		txtDescription.setColumns(10);
		
		getFormPanel().add(lblDevices, "2, 10");
		listDevices = new UiEntityList(UiDialogueType.SINGLE_FORM, EntityType.DEVICE);
		getFormPanel().add(listDevices.getListPane(), "4, 10, 5, 1");
		
	}
	//
	// Methods required by form panel template
	//
	@Override
	public EntityType type() {
		return EntityType.CATALOGUE;
	}
	@Override
	public void displayObject(int row){
		int i;
		if (getCatalogue().rangeCheck(row)){
			catalogueRow = row;
			if (!haveFieldsChanged()) catalogueEntry = getCatalogue().get(row);
		}else{
			catalogueRow = -1;
			if (!haveFieldsChanged()) catalogueEntry = new CatalogueEntry();
		}
			txtName.setText(catalogueEntry.name());
			txtModel.setText(catalogueEntry.getModel());
			txtDescription.setText(catalogueEntry.getDescription()); 
			
			if ((SmartPower.getMain().getData().getMakes().size()>0) && catalogueEntry.getMake()!=null ){
				comboMake.setSelectedIndex(SmartPower.getMain().getData().getMakes().findID(catalogueEntry.getMake().id()));
			}
			else comboMake.setSelectedIndex(0);

			if ((SmartPower.getMain().getData().getCategories().size()>0) && catalogueEntry.getCategory()!=null ){
				comboCategory.setSelectedIndex(SmartPower.getMain().getData().getMakes().findID(catalogueEntry.getCategory().id()));
			}
			else comboCategory.setSelectedIndex(0);
			
			if(catalogueEntry.deviceCount() >0){
				deviceIds = new long[catalogueEntry.deviceCount()];
				for(i=0;i<catalogueEntry.deviceCount();i++){
					deviceIds[i]= catalogueEntry.getDevice(i).id();
				}
			}else deviceIds = null; 
			listDevices.populateList(deviceIds);
			
			setObjectTextFields();
		
		getSelector().setSelectedIndex(catalogueRow); //calls fireActionEvent
	}
	@Override
	public void saveObject(int row) {
		setObjectTextFields();
		if (getCatalogue().rangeCheck(row)){
			getCatalogue().update(row,catalogueEntry);
		}
		setFieldsChanged(false);
	}
	@Override
	public void deleteObject(int row) {
		getCatalogue().remove(row);
		catalogueRow = -1;
		setFieldsChanged(false);
	}
	@Override
	public void addObject() {
		setObjectTextFields();
		if (getCatalogue().add(catalogueEntry)){
			this.repaint();
		}
	}
	//
	// Supporting methods
	//
	private PersistentList<CatalogueEntry> getCatalogue(){
		return  SmartPower.getMain().getData().getCatalogue();
	}
	private void setObjectTextFields(){
		catalogueEntry.setName(txtName.getText());
		catalogueEntry.setModel(txtModel.getText());
		catalogueEntry.setDescription(txtDescription.getText());
	}
}