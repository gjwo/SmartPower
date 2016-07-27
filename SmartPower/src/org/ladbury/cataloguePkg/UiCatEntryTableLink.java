package org.ladbury.cataloguePkg;

import javax.swing.table.AbstractTableModel;

import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.BooleanSelector;

public class UiCatEntryTableLink extends AbstractTableModel {
	
	private static final long serialVersionUID = -8319401200901128800L;
	private static final int TABLE_CC = 6;
	private static final int MAX_SELECT= 200;
	private static final String [] eventTblTitles = {"ID", "Make", "Model",  "Version","Devices", "Select"};
	
	
	private BooleanSelector booleanSelector = new BooleanSelector(MAX_SELECT);
	
	@Override
	public int getColumnCount() {
		return TABLE_CC;
	}

	@Override
	public String getColumnName(int col) {
		if ((col >= TABLE_CC) | (col <0)){
			return "Bad Column";
		}
        return eventTblTitles[col];
    }

	@Override
	public int getRowCount() {
		if (SmartPower.getMain()==null) return 0;
		return SmartPower.getMain().getData().getCatalogue().size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class<?> getColumnClass(int col){
		Class c = Object.class;
		
		switch(col){
		case 0: c = Long.class;
				break;
		case 1: c = String.class;
				break;
		case 2: c = String.class;
				break;
		case 3: c = String.class;
				break;
		case 4: c = Integer.class;
				break;
		case 5: c = Boolean.class;
				break;
		}
		return c;
	}
	@Override
	public boolean isCellEditable(int row,
            int col) {
		if (boundsCheck(row)) return (col == 5); 
		return false;
	}
	@Override
	public Object getValueAt(int row, int col) {
		Object r = "Empty"; // new Object();
		
		if (boundsCheck(row)){
			switch(col){
				case 0: r =  new Long(getCatalogueEntry(row).id());
						break;
				case 1: r =  new String(getCatalogueEntry(row).name());
						break;
				case 2: r =  new String(getCatalogueEntry(row).getModel());
						break;
				case 3: r =  new String(getCatalogueEntry(row).getDescription());
						break;
				case 4: r =  new Long(getCatalogueEntry(row).deviceCount());
						break;
				case 5: r =  new Boolean(booleanSelector.isSelected(row));
						break;
			}
		}
		return r;
	}

	@Override
	public void setValueAt(Object aValue, int row, int col){
		
		if (boundsCheck(row)){
			switch(col){
				case 5: booleanSelector.setSelect(row,((boolean)aValue));
						break;
				default:; //do nothing update not allowed
			}
		}
	}
	public CatalogueEntry getCatalogueEntry(int row){
		return (CatalogueEntry)SmartPower.getMain().getData().getCatalogue().get(row);
	}
	public boolean boundsCheck(int row){
		return (row>=0)&&(row < SmartPower.getMain().getData().getCatalogue().size());
	}
}
