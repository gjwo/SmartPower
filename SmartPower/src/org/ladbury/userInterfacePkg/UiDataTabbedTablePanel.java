package org.ladbury.userInterfacePkg;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.border.CompoundBorder;
import javax.swing.table.AbstractTableModel;

import org.ladbury.abodePkg.UiAbodeTablePanel;
import org.ladbury.abodePkg.UiRoomTablePanel;
import org.ladbury.cataloguePkg.UiCatEntryTablePanel;
import org.ladbury.consumptionPatternPkg.UiConPatternTablePanel;
import org.ladbury.deviceActivityPkg.UiActivityTablePanel;
import org.ladbury.devicePkg.UiDeviceTablePanel;
import org.ladbury.meterPkg.UiMetricTablePanel;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.readingEventPkg.UiEventTablePanel;
import org.ladbury.readingsPkg.UiReadingTablePanel;
import org.ladbury.userInterfacePkg.UiDataTabbedTableDialogue.SPAction;

import java.awt.event.ActionListener;

public class UiDataTabbedTablePanel extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private GridBagLayout gridBagLayout;
	private JLabel mainPanelLbl;
	private GridBagConstraints mainPanelLblGBG;
	private JTabbedPane tabbedPane;
	private GridBagConstraints tabbedPaneGBC;
	private UiReadingTablePanel readingsPanel;
	private UiDeviceTablePanel devicesPanel;
	private UiEventTablePanel eventPanel;
	private UiActivityTablePanel activityPanel;
	private UiAbodeTablePanel abodePanel;
	private UiRoomTablePanel roomPanel;
	private UiCatEntryTablePanel cataloguePanel;
	private UiConPatternTablePanel patternPanel;
	private UiMetricTablePanel metricPanel;

	//Constructor
	public UiDataTabbedTablePanel(ActionListener al) {
		
		// set up primary panel
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{73, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		mainPanelLbl = new JLabel("Data Tables");
		mainPanelLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		mainPanelLblGBG = new GridBagConstraints();
		mainPanelLblGBG.gridwidth = 3;
		mainPanelLblGBG.insets = new Insets(0, 0, 5, 0);
		mainPanelLblGBG.gridx = 0;
		mainPanelLblGBG.gridy = 0;
		add(mainPanelLbl, mainPanelLblGBG);
		
		// add the tabbed pane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 12));
		tabbedPane.setBorder(new CompoundBorder());
		tabbedPaneGBC = new GridBagConstraints();
		tabbedPaneGBC.gridheight = 2;
		tabbedPaneGBC.gridwidth = 3;
		tabbedPaneGBC.fill = GridBagConstraints.BOTH;
		tabbedPaneGBC.gridx = 0;
		tabbedPaneGBC.gridy = 1;
		add(tabbedPane, tabbedPaneGBC);
		
		// Add tabs to pane
		readingsPanel = new UiReadingTablePanel(al);
		tabbedPane.addTab("Readings", null, readingsPanel, null);
		devicesPanel = new UiDeviceTablePanel(al);
		tabbedPane.addTab("Devices", null, devicesPanel, null);
		eventPanel = new UiEventTablePanel(al);
		tabbedPane.addTab("Events", null, eventPanel, null);
		activityPanel = new UiActivityTablePanel(al);
		tabbedPane.addTab("Activity", null, activityPanel, null);
		abodePanel = new UiAbodeTablePanel(al);
		tabbedPane.addTab("Abode", null, abodePanel, null);
		roomPanel = new UiRoomTablePanel(al);
		tabbedPane.addTab("Room", null, roomPanel, null);
		cataloguePanel = new UiCatEntryTablePanel(al);
		tabbedPane.addTab("Catalogue", null, cataloguePanel, null);
		patternPanel = new UiConPatternTablePanel(al);
		tabbedPane.addTab("Pattern", null, patternPanel, null);
		metricPanel = new UiMetricTablePanel(al);
		tabbedPane.addTab("TimedRecords", null, metricPanel, null);

	}
	
	// identify action from the button that generated it
	public SPAction actionFromEventSource(Object o){
		SPAction action;
		
		action = roomPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = abodePanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = devicesPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = readingsPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = eventPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = activityPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = cataloguePanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = patternPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		action = metricPanel.actionFromEventSource(o);
		if (action != SPAction.UNKNOWN) return action;
		return SPAction.CANCEL;  
	}
	
	//return the relevant link table object
	public AbstractTableModel getModel(EntityType t) {
		switch(t){
		case DEVICE:	return devicesPanel.getModel();
		case READINGS:	return readingsPanel.getModel();
		case EVENTS:	return eventPanel.getModel();
		case ACTIVITY:	return activityPanel.getModel();
		case ABODE:		return abodePanel.getModel();
		case ROOM:		return roomPanel.getModel();
		case CATALOGUE:	return cataloguePanel.getModel();
		case PATTERN:	return patternPanel.getModel();
		case TIMEDRECORD:	return metricPanel.getModel();
		default: 		return null;
		}
	}
	public void selectTab(EntityType t){
		Component c;
		switch(t){
		case DEVICE:	c = devicesPanel; break;
		case READINGS:	c = readingsPanel; break;
		case EVENTS:	c = eventPanel;break;
		case ACTIVITY:	c = activityPanel; break;
		case ABODE:		c = abodePanel; break;
		case ROOM:		c = roomPanel; break;
		case CATALOGUE:	c = cataloguePanel;break;
		case PATTERN:	c = patternPanel; break;
		case TIMEDRECORD:	c = metricPanel; break;
		default: 		return;
		}	
		tabbedPane.setSelectedComponent(c);
	}
	public UiDataTablePanel getTab(EntityType t){
		UiDataTablePanel tp;
		switch(t){
		case DEVICE:	tp = devicesPanel; break;
		case READINGS:	tp = readingsPanel; break;
		case EVENTS:	tp = eventPanel;break;
		case ACTIVITY:	tp = activityPanel; break;
		case ABODE:		tp = abodePanel; break;
		case ROOM:		tp = roomPanel; break;
		case CATALOGUE:	tp = cataloguePanel;break;
		case PATTERN:	tp = patternPanel; break;
		case TIMEDRECORD:	tp = metricPanel; break;
		default: 		return null;
		}	
		return tp;
	}
	public UiDataTablePanel currentTab(){
		 return (UiDataTablePanel) tabbedPane.getSelectedComponent();
	}
}
