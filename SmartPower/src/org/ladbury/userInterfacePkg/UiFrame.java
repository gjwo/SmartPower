package org.ladbury.userInterfacePkg;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.jfree.ui.RefineryUtilities;
import org.ladbury.chartingPkg.PieChart;
import org.ladbury.chartingPkg.ScatterChart;
import org.ladbury.chartingPkg.TimeHistogram;
import org.ladbury.persistentData.PersistentData.EntityType;
import org.ladbury.smartpowerPkg.SmartPower;
import org.ladbury.userInterfacePkg.UiStyle.UiDialogueType;

public class UiFrame
    extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    // variables
    private JPanel contentPane;
    private BorderLayout borderLayout1 = new BorderLayout();
    private JMenuBar jMenuBar1 = new JMenuBar();

    private JMenu jMenuFile = new JMenu("File");
    private JMenuItem jMenuFileOpen = new JMenuItem("Open");
    private JMenuItem jMenuFileSave = new JMenuItem("Save");
    private JMenuItem jMenuFileExit = new JMenuItem("Exit");

    private JMenu jMenuProcess = new JMenu("Process");
    private JMenuItem jMenuProcessRecords = new JMenuItem("Process Edges");
    private JMenuItem jMenuProcessDevices = new JMenuItem("Process Devices");
    
    private JMenu jMenuChart = new JMenu("Chart");
    private JMenuItem jMenuChartPie = new JMenuItem("Pie 3D");
    private JMenuItem jMenuChartHistogram = new JMenuItem("Histogram");
    private JMenuItem jMenuChartScatter = new JMenuItem("Scatter");
    

    private JMenu jMenuHelp = new JMenu("Help");
    private JMenuItem jMenuHelpAbout = new JMenuItem("About..");

    private JMenu jMenuData 					= new JMenu("Data");
    private JMenuItem jMenuDataTables 			= new JMenuItem("Tabbed Tables");
    private JMenuItem jMenuDataForms 			= new JMenuItem("Tabbed Forms");
    private JMenuItem jMenuDataEventTable;		//Entity Menus in constructor			
    private JMenuItem jMenuDataReadingTable;			
    private JMenuItem jMenuDataMetricTable;
    private JMenuItem jMenuDataDeviceForm;
    private JMenuItem jMenuDataActivityForm;
    private JMenuItem jMenuDataAbodeForm;
    private JMenuItem jMenuDataRoomForm;
    private JMenuItem jMenuDataPatternForm;
    private JMenuItem jMenuDataCatalogueForm;
    private JMenuItem jMenuDataMakeForm;
    private JMenuItem jMenuDataCategoryForm;
    private JMenuItem jMenuDataEventForm;
    private JMenuItem jMenuDataTimePeriodForm;
    private JMenuItem jMenuDataWeekdayTypeForm;
    private JMenuItem jMenuDataCalendarPeriodForm;
    private JMenuItem jMenuDataHabitForm;
    private JMenuItem jMenuDataClusterForm;
    private JMenuItem jMenuDataMeterForm;
    //private JMenuItem jMenuDataMetricForm;

    private TextArea textArea1 = new TextArea();

    private FileDialog fileDialogue = null;
	private String 	windowTitle = null;
	private UiDataTabbedTableDialogue 	tabbedTableDlg 	= null;
	private UiDataSingleTableDialogue 	singleTableDlg 	= null;
	private UiDataSingleFormDialogue 	singleFormDlg 	= null;
	private UiDataTabbedFormDialogue 	tabbedFormDlg 	= null;

    //
    // Construct the frame
    //
    public UiFrame(String str) {
        super(str);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            windowTitle = str;
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    // Component initialisation
    //
    private void jbInit() throws Exception {
        // Create a content pane
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(800, 600));
        this.setTitle(windowTitle);
        // add a menu bar
        this.setJMenuBar(jMenuBar1);
        
        //set up menu items for Entities
        jMenuDataEventTable		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_TABLE,EntityType.EVENTS ,"Event Table"));			
        jMenuDataReadingTable	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_TABLE,EntityType.READINGS ,"Reading Table"));			
        jMenuDataMetricTable	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_TABLE,EntityType.TIMEDRECORD ,"TimedRecords Table"));			

        jMenuDataDeviceForm 	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.DEVICE ,"Device Form"));
        jMenuDataActivityForm	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.ACTIVITY ,"Activity Form"));
        jMenuDataAbodeForm 		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.ABODE ,"Abode Form"));
        jMenuDataRoomForm 		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.ROOM ,"Room Form"));
        jMenuDataPatternForm 	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.PATTERN ,"Pattern Form"));
        jMenuDataCatalogueForm 	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.CATALOGUE ,"Catalogue Form"));
        jMenuDataEventForm 		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.EVENTS ,"Event Form"));
        jMenuDataMakeForm 		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.MAKE ,"Make Form"));
        jMenuDataCategoryForm 	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.CATEGORY ,"Category Form"));
        jMenuDataTimePeriodForm = new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.TIMEPERIOD ,"Time Period Form"));
        jMenuDataWeekdayTypeForm 	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.WEEKDAYTYPE ,"Weekday Type Form"));
        jMenuDataCalendarPeriodForm	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.CALENDARPERIOD ,"Calendar Period Form"));
        jMenuDataHabitForm		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.HABIT ,"Habit Form"));
        jMenuDataClusterForm	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.CLUSTER ,"Cluster Form"));
        jMenuDataMeterForm		= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.METER ,"Meter Form"));
        //jMenuDataMetricForm	= new JMenuItem(new UiEntityAction(this,UiDialogueType.SINGLE_FORM,EntityType.TIMEDRECORD ,"TimedRecords Form"));

        // initialise the menus
        createFileMenu();
        createDataMenu();
        createProcessMenu();
        createChartMenu();
        createHelpMenu();
 
        // add log text area
        textArea1.setBackground(Color.pink);
        textArea1.setColumns(80);
        textArea1.setCursor(null);
        textArea1.setEditable(false);
        textArea1.setFont(UiStyle.NORMAL_FONT);
        textArea1.setRows(20);
        textArea1.setText("Smart Power System Messages\n");

        contentPane.add(textArea1, BorderLayout.CENTER);
    }

    private void createFileMenu(){

        // add sub items and their actions 
        jMenuFile.add(jMenuFileOpen);
        jMenuFileOpen.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
                jMenuFileOpen_actionPerformed(e);
            }
        });
        jMenuFile.add(jMenuFileSave);
        jMenuFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuFileSave_actionPerformed(e);
            }
        });
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExit);
        jMenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuFileExit_actionPerformed(e);
            }
        });
        // add the menu to the menu bar
        jMenuBar1.add(jMenuFile);
    }

    private void createDataMenu(){
        // add sub items and their actions      
        jMenuDataTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuTabbedTableActionPerformed(e);
            }
        });
        jMenuData.add(jMenuDataTables);
        
        jMenuDataForms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuTabbedFormActionPerformed(e);
            }
        });
        jMenuData.add(jMenuDataForms);
        
        jMenuData.addSeparator();
        
        jMenuData.add(jMenuDataEventTable);
        jMenuData.add(jMenuDataReadingTable);
        jMenuData.add(jMenuDataMetricTable);
        
        jMenuData.addSeparator();

        jMenuData.add(jMenuDataDeviceForm);
        jMenuData.add(jMenuDataActivityForm);
        jMenuData.add(jMenuDataAbodeForm);
        jMenuData.add(jMenuDataRoomForm);
        jMenuData.add(jMenuDataPatternForm);
        jMenuData.add(jMenuDataCatalogueForm);
        jMenuData.add(jMenuDataEventForm);

        jMenuData.addSeparator();
        
        jMenuData.add(jMenuDataMakeForm);
        jMenuData.add(jMenuDataCategoryForm);
        jMenuData.add(jMenuDataTimePeriodForm);
        jMenuData.add(jMenuDataWeekdayTypeForm);
        jMenuData.add(jMenuDataCalendarPeriodForm);
        jMenuData.add(jMenuDataHabitForm);
        jMenuData.add(jMenuDataClusterForm);
        jMenuData.addSeparator();
        jMenuData.add(jMenuDataMeterForm);
                     
        // add the menu to the menu bar
        jMenuBar1.add(jMenuData);
    }
    private void createProcessMenu(){
        // add sub items and their actions      
        jMenuProcessRecords.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuProcessRecords_actionPerformed(e);
            }
        });
        jMenuProcess.add(jMenuProcessDevices);
        jMenuProcessDevices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuProcessDevices_actionPerformed(e);
            }
        });
        jMenuProcess.add(jMenuProcessRecords);
        jMenuProcess.add(jMenuProcessDevices);
        // add the menu to the menu bar
        jMenuBar1.add(jMenuProcess);
    }

    private void createChartMenu(){
        // add sub items and their actions      
        jMenuChartPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuChartPie_actionPerformed(e);
            }
        });
        jMenuChart.add(jMenuChartPie);
        jMenuChartHistogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuChartHistogram_actionPerformed(e);
            }
        });
        jMenuChart.add(jMenuChartHistogram);
        jMenuChartScatter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuChartScatter_actionPerformed(e);
            }
        });
        jMenuChart.add(jMenuChartScatter);
       // add the menu to the menu bar
        jMenuBar1.add(jMenuChart);
    }
    
    private void createHelpMenu(){
        // add sub items and their actions      
        jMenuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuHelpAbout_actionPerformed(e);
            }
        });
        jMenuHelp.add(jMenuHelpAbout);
        // add the menu to the menu bar
        jMenuBar1.add(jMenuHelp);
    }

    //Overridden so we can exit when window is closed
    public void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            SmartPower.getMain().stop();
        	//System.exit(0);
        }
    }

    //
    //File | Open action performed
    //
    public void jMenuFileOpen_actionPerformed(ActionEvent e) {
        //FilenameFilter m_filter = "*.csv";
        // create a file dialogue
        fileDialogue = new FileDialog(this, "Open measurement readings (.csv) file");
        //fileDialogue.setFilenameFilter((FilenameFilter)"*.csv");
        fileDialogue.setDirectory("c:/");
        fileDialogue.setVisible(true);
        // File Dialogue is modal so won't return unless file or cancel
        if(fileDialogue.getFile() != null ){
        	SmartPower.getMain().change_state(SmartPower.RunState.OPEN_FILE); //trigger processing in main loop
        }
        // else the user cancelled the dialog, do nothing
    }

    //
    //File | Save action performed
    //
    public void jMenuFileSave_actionPerformed(ActionEvent e) {
        //FilenameFilter m_filter = "*.csv";
        // create a file dialogue
        fileDialogue = new FileDialog(this, "Open measurement readings (.csv) file");
        //fileDialogue.setFilenameFilter((FilenameFilter)"*.csv");
        fileDialogue.setDirectory("c:/");
        fileDialogue.setVisible(true);
        // File Dialogue is modal so won't return unless file or cancel
        if(fileDialogue.getFile() != null ){
        	SmartPower.getMain().change_state(SmartPower.RunState.SAVE_FILE);  //trigger processing in main loop
        }
    }
    
    //
    //File | Exit action performed
    //
    public void jMenuFileExit_actionPerformed(ActionEvent e) {
        SmartPower.getMain().stop();
    }

    //
    //Help About action performed
    //
    public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
        UiAboutBox dlg = new UiAboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height -
                         dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.setVisible(true);
    }
    //
    //Process Records action performed
    //
    public void jMenuProcessRecords_actionPerformed(ActionEvent e) {
        SmartPower.getMain().change_state(SmartPower.RunState.PROCESS_EDGES);  //trigger processing in main loop
    }

    //
    //Process Devices action performed
    //
    public void jMenuProcessDevices_actionPerformed(ActionEvent e) {
        SmartPower.getMain().change_state(SmartPower.RunState.PROCESS_EVENTS);  //trigger processing in main loop
    }
    
    //
    //Chart Pie action performed
    //
    public void jMenuChartPie_actionPerformed(ActionEvent e) {
    	PieChart demo = new PieChart("Comparison", "Which operating system are you using?");
        demo.pack();
        demo.setVisible(true);
    }

    //
    //Chart Histogram action performed
    //
    public void jMenuChartHistogram_actionPerformed(ActionEvent e) {

        final TimeHistogram powerH = new TimeHistogram("Power Histogram");
        powerH.pack();
        RefineryUtilities.centerFrameOnScreen(powerH);
        powerH.setVisible(true);
    }
    
    //
    //Chart Scatter action performed
    //
    public void jMenuChartScatter_actionPerformed(ActionEvent e) {

        final ScatterChart powerH = new ScatterChart("Device Scatter Chart");
        powerH.pack();
        RefineryUtilities.centerFrameOnScreen(powerH);
        powerH.setVisible(true);
    }
    //
    //Tabbed table action performed
    //
    public void jMenuTabbedTableActionPerformed(ActionEvent e) {
    	displayTabbedTableDialogue(this,EntityType.DEVICE,0);
   }
    
    public void jMenuTabbedFormActionPerformed(ActionEvent e) {
    	displayTabbedFormDialogue(this,EntityType.DEVICE,0);
   }
/*    
    public void jMenuSingleTableActionPerformed(ActionEvent e) {
    	EntityType entity;
    	entity = getEntityType(e);
    	displayTableDialogue(this,entity,0);
   }
    //
    //Match Devices form action performed
    //
    public void jMenuDataForm_actionPerformed(ActionEvent e) {
    	EntityType entity;
    	entity = getEntityType(e);
    	displayFormDialogue(this,entity,0);
    }*/
    //
    // Display a form Dialogue
    //
    public void displayFormDialogue(UiFrame parent, EntityType entityType, int initialRow) {
    	Dimension dlgSize,frmSize;
        singleFormDlg = new UiDataSingleFormDialogue(parent,entityType,initialRow);
        dlgSize = singleFormDlg.getPreferredSize();
        frmSize = getSize();
        Point loc = getLocation();
        singleFormDlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height -
                         dlgSize.height) / 2 + loc.y);
        singleFormDlg.setModal(true);
        singleFormDlg.setVisible(true);
    }
/*    private EntityType getEntityType(ActionEvent e){
    	Object o = e.getSource();
    	
        if(o.equals(jMenuDataEventTable)) return EntityType.EVENTS;
        if(o.equals(jMenuDataReadingTable)) return EntityType.READINGS;
        if(o.equals(jMenuDataDeviceForm)) return EntityType.DEVICE;
        if(o.equals(jMenuDataActivityForm))  return EntityType.ACTIVITY;
        if(o.equals(jMenuDataAbodeForm)) return EntityType.ABODE;
        if(o.equals(jMenuDataRoomForm)) return EntityType.ROOM;
        if(o.equals(jMenuDataPatternForm)) return EntityType.PATTERN;
        if(o.equals(jMenuDataCatalogueForm)) return EntityType.CATALOGUE;
        if(o.equals(jMenuDataEventForm)) return EntityType.EVENTS;
        return EntityType.UNDEFINED;
    }*/
    //
    // Display a tabbed table Dialogue
    //
    public void displayTabbedTableDialogue(UiFrame parent, EntityType entityType, int initialRow) {
    	Dimension dlgSize,frmSize;
        tabbedTableDlg = new UiDataTabbedTableDialogue(parent/*,entityType,initialRow*/);
        dlgSize = tabbedTableDlg.getPreferredSize();
        frmSize = getSize();
        Point loc = getLocation();
        tabbedTableDlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height -
                         dlgSize.height) / 2 + loc.y);
        tabbedTableDlg.setModal(true);
        tabbedTableDlg.setVisible(true);
    }
    //
    // Display a tabbed form Dialogue
    //
    public void displayTabbedFormDialogue(UiFrame parent, EntityType entityType, int initialRow) {
    	Dimension dlgSize,frmSize;
        tabbedFormDlg = new UiDataTabbedFormDialogue(parent,entityType,initialRow);
        dlgSize = tabbedFormDlg.getPreferredSize();
        frmSize = getSize();
        Point loc = getLocation();
        tabbedFormDlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height -
                         dlgSize.height) / 2 + loc.y);
        tabbedFormDlg.setModal(true);
        tabbedFormDlg.setVisible(true);
    }
    //
    // Display a single table Dialogue
    //
    public void displayTableDialogue(UiFrame parent, EntityType entityType, int initialRow) {
    	Dimension dlgSize,frmSize;
        singleTableDlg = new UiDataSingleTableDialogue(parent,entityType,initialRow);
        dlgSize = singleTableDlg.getPreferredSize();
        frmSize = getSize();
        Point loc = getLocation();
        singleTableDlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height -
                         dlgSize.height) / 2 + loc.y);
        singleTableDlg.setModal(true);
        singleTableDlg.setVisible(true);
    }

    //
    // write a string to the log area
    //
    public void displayLog(String str) {
        textArea1.append(str);
        repaint();
    }

    //
    // write an integer to the log area
    //
    public void displayLog(int i) {
        Integer intWrapper = new Integer(i);
        textArea1.append(intWrapper.toString());
        repaint();
    }
    public FileDialog getFileDialog() {
		return fileDialogue;
	}

	public void setFileDialog(FileDialog m_filediag1) {
		this.fileDialogue = m_filediag1;
	}

	public UiDataTabbedTableDialogue getDataDialogue(){
		return this.tabbedTableDlg;
	}

	public UiDataSingleFormDialogue getFormDlg() {
		return singleFormDlg;
	}
	public  void setFormDlg(UiDataSingleFormDialogue dlg) {
		this.singleFormDlg=dlg;
	}
}