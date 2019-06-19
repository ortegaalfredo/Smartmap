/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mapa;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import org.netbeans.microedition.lcdui.SplashScreen;

/**
 * @author alfred
 */
public class SmartMap extends MIDlet implements CommandListener {

// HINT - Uncomment for accessing new MIDlet Started/Resumed logic.
//    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private SplashScreen splashScreen1;
    private Form frmSearch;
    private TextField textField1;
    private TextField textField2;
    private Gauge gauge1;
    private List listResults;
    private Form frmOptions;
    private ChoiceGroup choiceGroup1;
    private TextBox frmHelp;
    private TextBox frmFailData;
    private TextBox frmFail;
    private Command okCommand1;
    private Command exitCommand;
    private Command okSearchCommand;
    private Command backCommand1;
    private Command endSearchCommand;
    private Command gotoScreenCommand;
    private Command SearchIntersectCommand;
    private Command SearchSingleCommand;
    private Command SearchColectivoCommand;
    private Command HelpCommand;
    private Command optionsCommand;
    private Command ZoomInCommand;
    private Command ZoomOutCommand;
    private Ticker ticker1;
    private Image image1;
    private Ticker ticker2;
    //</editor-fold>//GEN-END:|fields|0|


// HINT - Uncomment for accessing new MIDlet Started/Resumed logic.
// NOTE - Be aware of resolving conflicts of the constructor.
//    /**
//     * The ConvertedSmartMap2 constructor.
//     */
//    public ConvertedSmartMap2() {
//    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        
        switchDisplayable(null, getSplashScreen1());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
    // Insert global pre-action code here
        if (displayable == frmHelp) {//GEN-BEGIN:|7-commandAction|1|50-preAction
            if (command == gotoScreenCommand) {//GEN-END:|7-commandAction|1|50-preAction
                // Insert pre-action code here
//GEN-LINE:|7-commandAction|2|50-postAction
                // Insert post-action code here
            }//GEN-BEGIN:|7-commandAction|3|52-preAction
        } else if (displayable == frmOptions) {
            if (command == gotoScreenCommand) {//GEN-END:|7-commandAction|3|52-preAction
                screen.mRT.softScroll= getChoiceGroup1().isSelected(0); // Opcion de scroll suave
                ((POLYLINEcell)(screen.mPOLYLINEs)).fastMode=getChoiceGroup1().isSelected(1); // Opcion fast mode
//GEN-LINE:|7-commandAction|4|52-postAction

                // Insert post-action code here
            }//GEN-BEGIN:|7-commandAction|5|36-preAction
        } else if (displayable == frmSearch) {
            if (command == gotoScreenCommand) {//GEN-END:|7-commandAction|5|36-preAction
                
//GEN-LINE:|7-commandAction|6|36-postAction
            } else if (command == okSearchCommand) {//GEN-LINE:|7-commandAction|7|24-preAction
                doSearch();
                switchDisplayable(null, getListResults());//GEN-LINE:|7-commandAction|8|24-postAction
                // Insert post-action code here
            }//GEN-BEGIN:|7-commandAction|9|28-preAction
        } else if (displayable == listResults) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|9|28-preAction
                // write pre-action user code here
                listResultsAction();//GEN-LINE:|7-commandAction|10|28-postAction
                // write post-action user code here
            } else if (command == backCommand1) {//GEN-LINE:|7-commandAction|11|30-preAction
                switchDisplayable(null, getFrmSearch());//GEN-LINE:|7-commandAction|12|30-postAction
            } else if (command == endSearchCommand) {//GEN-LINE:|7-commandAction|13|33-preAction
                 //Maquina de estados de la busqueda, para busquedas comunes o dos intersecciones, para los colectivos
                    switch (mSearchMode) {
                      case cSEARCHMODE_COMMON:  doGotoIntersection();
                                                break;
                      case cSEARCHMODE_BUSFIRST: mSearchMode=cSEARCHMODE_BUSLAST;
                                                 getTicker2().setString("Input the destination street:");
                                                 mFirstIntersection = (Intersection) intersections.elementAt(getListResults().getSelectedIndex());
                                                 getFrmSearch().insert(0,(new StringItem("From: ", getListResults().getString(getListResults().getSelectedIndex()),Item.PLAIN)));
                                                 getDisplay().setCurrent(getFrmSearch());
                                                 break;
                        case cSEARCHMODE_BUSLAST: getFrmSearch().delete(0);
                                                 mLastIntersection = (Intersection) intersections.elementAt(getListResults().getSelectedIndex());
                                                 doSearchBus();
                                                 break;
                    }
//GEN-LINE:|7-commandAction|14|33-postAction
            }//GEN-BEGIN:|7-commandAction|15|15-preAction
        } else if (displayable == splashScreen1) {
            if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|15|15-preAction
                // Insert pre-action code here
//GEN-LINE:|7-commandAction|16|15-postAction
                // Insert post-action code here
            } else if (command == okCommand1) {//GEN-LINE:|7-commandAction|17|17-preAction
                if (screen!=null) {
                    Display.getDisplay(this).setCurrent(screen);
                    screen.Start();
                    screen.mDirty=true;
                    }
//GEN-LINE:|7-commandAction|18|17-postAction
            }//GEN-BEGIN:|7-commandAction|19|7-postCommandAction
        }//GEN-END:|7-commandAction|19|7-postCommandAction
        // Estos comandos hacen lo mismo no importa desde donde se llamen
    if  (command == exitCommand)  {// exit sale en cualquier lado
                screen.saveConfiguration();
                exitMIDlet();
                }
        
    if  (command == getOptionsCommand()) { // muestra el form de opciones
               getDisplay().setCurrent(this.getFrmOptions());
               }

    if  (command == getZoomInCommand()) { // Zoom in
               screen.ZoomIn();
               }

    if  (command == getZoomOutCommand()) { // Zoom out
               screen.ZoomOut();
               }

    if (command == SearchSingleCommand) {// Mostramos el form de buscar
                mSearchMode=cSEARCHMODE_COMMON;
                getTextField2().setString("");
                getTicker2().setString("Type the partial name of the street or place:");
                if (getFrmSearch().size()==3)
                    getFrmSearch().delete(1);
                getDisplay().setCurrent(getFrmSearch());
                }
        
     if (command == SearchIntersectCommand) {// Mostramos el form de buscar
                mSearchMode=cSEARCHMODE_COMMON;
                getTicker2().setString("Type the intersection to search:");
                if (getFrmSearch().size()==2)
                    getFrmSearch().insert(1,getTextField2());
                getDisplay().setCurrent(getFrmSearch());
                }
        
     if (command == SearchColectivoCommand) {// Mostramos el form de buscar 
                mSearchMode=cSEARCHMODE_BUSFIRST;
                getTicker2().setString("Type the intersection to part from:");
                if (getFrmSearch().size()==2)
                    getFrmSearch().insert(1,getTextField2());
                getDisplay().setCurrent(getFrmSearch());
                }
     
     if (command == getHelpCommand()) {
            if (getFrmHelp().getString().equals("")) 
                getFrmHelp().setString("Control:\n3: Zoom out, 1: Zoom in\n5: Switch cursor mode / scroll mode\n*: Create bookmark, 0: Delete bookmark\n7: Full screen (Available in some models)\nUse the keys (8,2,4,6) for moving.\nSearch street or place:\nType any part of the name and press \'Ok\'\nSearch intersection:\nType the name of the intersecting streets and press \'Ok\'.\n\n\n\nSmartmap 3 \nCopyright(c) 2006-2008 Alfredo A. Ortega\nForbidden all comercial use, in agreement with license UML 1.0 of project mapear.");
            getDisplay().setCurrent(getFrmHelp());
     }
        
     if (command == gotoScreenCommand) { // Mostramos el screen del mapa
               getDisplay().setCurrent(screen);
               screen.mDirty=true;
               }
    }//GEN-BEGIN:|7-commandAction|20|
    //</editor-fold>//GEN-END:|7-commandAction|20|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: splashScreen1 ">//GEN-BEGIN:|13-getter|0|13-preInit
    /**
     * Returns an initiliazed instance of splashScreen1 component.
     * @return the initialized component instance
     */
    public SplashScreen getSplashScreen1() {
        if (splashScreen1 == null) {//GEN-END:|13-getter|0|13-preInit
            // Insert pre-init code here
            splashScreen1 = new SplashScreen(getDisplay());//GEN-BEGIN:|13-getter|1|13-postInit
            splashScreen1.setTitle("Version 3.05 (only for personal use)");
            splashScreen1.setTicker(getTicker1());
            splashScreen1.addCommand(getOkCommand1());
            splashScreen1.setCommandListener(this);
            splashScreen1.setImage(getImage1());
            splashScreen1.setText("");
            splashScreen1.setTimeout(2000);
            splashScreen1.setAllowTimeoutInterrupt(false);//GEN-END:|13-getter|1|13-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|13-getter|2|
        return splashScreen1;
    }
    //</editor-fold>//GEN-END:|13-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmSearch ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initiliazed instance of frmSearch component.
     * @return the initialized component instance
     */
    public Form getFrmSearch() {
        if (frmSearch == null) {//GEN-END:|22-getter|0|22-preInit
            // Insert pre-init code here
            frmSearch = new Form("Street search", new Item[] { getTextField1(), getTextField2(), getGauge1() });//GEN-BEGIN:|22-getter|1|22-postInit
            frmSearch.setTicker(getTicker2());
            frmSearch.addCommand(getOkSearchCommand());
            frmSearch.addCommand(getGotoScreenCommand());
            frmSearch.setCommandListener(this);//GEN-END:|22-getter|1|22-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|22-getter|2|
        return frmSearch;
    }
    //</editor-fold>//GEN-END:|22-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">//GEN-BEGIN:|38-getter|0|38-preInit
    /**
     * Returns an initiliazed instance of textField1 component.
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {//GEN-END:|38-getter|0|38-preInit
            // Insert pre-init code here
            textField1 = new TextField("Street 1", null, 32, TextField.ANY);//GEN-LINE:|38-getter|1|38-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|38-getter|2|
        return textField1;
    }
    //</editor-fold>//GEN-END:|38-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField2 ">//GEN-BEGIN:|39-getter|0|39-preInit
    /**
     * Returns an initiliazed instance of textField2 component.
     * @return the initialized component instance
     */
    public TextField getTextField2() {
        if (textField2 == null) {//GEN-END:|39-getter|0|39-preInit
            // Insert pre-init code here
            textField2 = new TextField("Street 2", null, 32, TextField.ANY);//GEN-BEGIN:|39-getter|1|39-postInit
            textField2.setInitialInputMode("UCB_BASIC_LATIN");//GEN-END:|39-getter|1|39-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|39-getter|2|
        return textField2;
    }
    //</editor-fold>//GEN-END:|39-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: gauge1 ">//GEN-BEGIN:|40-getter|0|40-preInit
    /**
     * Returns an initiliazed instance of gauge1 component.
     * @return the initialized component instance
     */
    public Gauge getGauge1() {
        if (gauge1 == null) {//GEN-END:|40-getter|0|40-preInit
            // Insert pre-init code here
            gauge1 = new Gauge("Search", false, 100, 0);//GEN-LINE:|40-getter|1|40-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|40-getter|2|
        return gauge1;
    }
    //</editor-fold>//GEN-END:|40-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listResults ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of listResults component.
     * @return the initialized component instance
     */
    public List getListResults() {
        if (listResults == null) {//GEN-END:|26-getter|0|26-preInit
            // Insert pre-init code here
            listResults = new List("Results", Choice.IMPLICIT);//GEN-BEGIN:|26-getter|1|26-postInit
            listResults.addCommand(getBackCommand1());
            listResults.addCommand(getEndSearchCommand());
            listResults.setCommandListener(this);
            listResults.setFitPolicy(Choice.TEXT_WRAP_DEFAULT);
            listResults.setSelectCommand(getEndSearchCommand());//GEN-END:|26-getter|1|26-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|26-getter|2|
        return listResults;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listResultsAction ">//GEN-BEGIN:|26-action|0|26-preAction
    /**
     * Performs an action assigned to the selected list element in the listResults component.
     */
    public void listResultsAction() {//GEN-END:|26-action|0|26-preAction
        // enter pre-action user code here
//GEN-LINE:|26-action|1|26-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|26-action|2|
    //</editor-fold>//GEN-END:|26-action|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmHelp ">//GEN-BEGIN:|49-getter|0|49-preInit
    /**
     * Returns an initiliazed instance of frmHelp component.
     * @return the initialized component instance
     */
    public TextBox getFrmHelp() {
        if (frmHelp == null) {//GEN-END:|49-getter|0|49-preInit
            // Insert pre-init code here
            frmHelp = new TextBox("Ayuda", "", 2222, TextField.ANY | TextField.UNEDITABLE);//GEN-BEGIN:|49-getter|1|49-postInit
            frmHelp.addCommand(getGotoScreenCommand());
            frmHelp.setCommandListener(this);
            frmHelp.setInitialInputMode("any");//GEN-END:|49-getter|1|49-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|49-getter|2|
        return frmHelp;
    }
    //</editor-fold>//GEN-END:|49-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmOptions ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initiliazed instance of frmOptions component.
     * @return the initialized component instance
     */
    public Form getFrmOptions() {
        if (frmOptions == null) {//GEN-END:|51-getter|0|51-preInit
            // Insert pre-init code here
            frmOptions = new Form("form", new Item[] { getChoiceGroup1() });//GEN-BEGIN:|51-getter|1|51-postInit
            frmOptions.addCommand(getGotoScreenCommand());
            frmOptions.setCommandListener(this);//GEN-END:|51-getter|1|51-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|51-getter|2|
        return frmOptions;
    }
    //</editor-fold>//GEN-END:|51-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroup1 ">//GEN-BEGIN:|53-getter|0|53-preInit
    /**
     * Returns an initiliazed instance of choiceGroup1 component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroup1() {
        if (choiceGroup1 == null) {//GEN-END:|53-getter|0|53-preInit
            // Insert pre-init code here
            choiceGroup1 = new ChoiceGroup("Options", Choice.MULTIPLE);//GEN-BEGIN:|53-getter|1|53-postInit
            choiceGroup1.append("Scroll Animation", null);
            choiceGroup1.append("Fast Graphics", null);
            choiceGroup1.setSelectedFlags(new boolean[] { false, false });
            choiceGroup1.setFont(0, null);
            choiceGroup1.setFont(1, null);//GEN-END:|53-getter|1|53-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|53-getter|2|
        return choiceGroup1;
    }
    //</editor-fold>//GEN-END:|53-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmFail ">//GEN-BEGIN:|56-getter|0|56-preInit
    /**
     * Returns an initiliazed instance of frmFail component.
     * @return the initialized component instance
     */
    public TextBox getFrmFail() {
        if (frmFail == null) {//GEN-END:|56-getter|0|56-preInit
            // Insert pre-init code here
            frmFail = new TextBox("Advertencia", "El celular no cuenta con suficiente memoria (heap) libre.", 120, TextField.ANY);//GEN-LINE:|56-getter|1|56-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|56-getter|2|
        return frmFail;
    }
    //</editor-fold>//GEN-END:|56-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand1 ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of okCommand1 component.
     * @return the initialized component instance
     */
    public Command getOkCommand1() {
        if (okCommand1 == null) {//GEN-END:|18-getter|0|18-preInit
            // Insert pre-init code here
            okCommand1 = new Command("Ok", Command.OK, 1);//GEN-LINE:|18-getter|1|18-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|18-getter|2|
        return okCommand1;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|21-getter|0|21-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|21-getter|0|21-preInit
            // Insert pre-init code here
            exitCommand = new Command("Exit", Command.EXIT, 1);//GEN-LINE:|21-getter|1|21-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|21-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|21-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okSearchCommand ">//GEN-BEGIN:|25-getter|0|25-preInit
    /**
     * Returns an initiliazed instance of okSearchCommand component.
     * @return the initialized component instance
     */
    public Command getOkSearchCommand() {
        if (okSearchCommand == null) {//GEN-END:|25-getter|0|25-preInit
            // Insert pre-init code here
            okSearchCommand = new Command("Ok", Command.OK, 1);//GEN-LINE:|25-getter|1|25-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|25-getter|2|
        return okSearchCommand;
    }
    //</editor-fold>//GEN-END:|25-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|31-getter|0|31-preInit
    /**
     * Returns an initiliazed instance of backCommand1 component.
     * @return the initialized component instance
     */
    public Command getBackCommand1() {
        if (backCommand1 == null) {//GEN-END:|31-getter|0|31-preInit
            // Insert pre-init code here
            backCommand1 = new Command("Back", Command.BACK, 1);//GEN-LINE:|31-getter|1|31-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|31-getter|2|
        return backCommand1;
    }
    //</editor-fold>//GEN-END:|31-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: endSearchCommand ">//GEN-BEGIN:|34-getter|0|34-preInit
    /**
     * Returns an initiliazed instance of endSearchCommand component.
     * @return the initialized component instance
     */
    public Command getEndSearchCommand() {
        if (endSearchCommand == null) {//GEN-END:|34-getter|0|34-preInit
            // Insert pre-init code here
            endSearchCommand = new Command("Ok", Command.OK, 1);//GEN-LINE:|34-getter|1|34-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|34-getter|2|
        return endSearchCommand;
    }
    //</editor-fold>//GEN-END:|34-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: gotoScreenCommand ">//GEN-BEGIN:|37-getter|0|37-preInit
    /**
     * Returns an initiliazed instance of gotoScreenCommand component.
     * @return the initialized component instance
     */
    public Command getGotoScreenCommand() {
        if (gotoScreenCommand == null) {//GEN-END:|37-getter|0|37-preInit
            // Insert pre-init code here
            gotoScreenCommand = new Command("Back", Command.BACK, 1);//GEN-LINE:|37-getter|1|37-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|37-getter|2|
        return gotoScreenCommand;
    }
    //</editor-fold>//GEN-END:|37-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: SearchIntersectCommand ">//GEN-BEGIN:|41-getter|0|41-preInit
    /**
     * Returns an initiliazed instance of SearchIntersectCommand component.
     * @return the initialized component instance
     */
    public Command getSearchIntersectCommand() {
        if (SearchIntersectCommand == null) {//GEN-END:|41-getter|0|41-preInit
            // Insert pre-init code here
            SearchIntersectCommand = new Command("Intersection", "Busqueda de intersecci\u00F3n entre dos calles", Command.OK, 1);//GEN-LINE:|41-getter|1|41-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|41-getter|2|
        return SearchIntersectCommand;
    }
    //</editor-fold>//GEN-END:|41-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: SearchSingleCommand ">//GEN-BEGIN:|46-getter|0|46-preInit
    /**
     * Returns an initiliazed instance of SearchSingleCommand component.
     * @return the initialized component instance
     */
    public Command getSearchSingleCommand() {
        if (SearchSingleCommand == null) {//GEN-END:|46-getter|0|46-preInit
            // Insert pre-init code here
            SearchSingleCommand = new Command("Single Street or Place", Command.OK, 1);//GEN-LINE:|46-getter|1|46-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|46-getter|2|
        return SearchSingleCommand;
    }
    //</editor-fold>//GEN-END:|46-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: SearchColectivoCommand ">//GEN-BEGIN:|47-getter|0|47-preInit
    /**
     * Returns an initiliazed instance of SearchColectivoCommand component.
     * @return the initialized component instance
     */
    public Command getSearchColectivoCommand() {
        if (SearchColectivoCommand == null) {//GEN-END:|47-getter|0|47-preInit
            // Insert pre-init code here
            SearchColectivoCommand = new Command("Camino en colectivo", "Busca una ruta en colectivo entre dos intersecciones de calle", Command.OK, 1);//GEN-LINE:|47-getter|1|47-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|47-getter|2|
        return SearchColectivoCommand;
    }
    //</editor-fold>//GEN-END:|47-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: HelpCommand ">//GEN-BEGIN:|48-getter|0|48-preInit
    /**
     * Returns an initiliazed instance of HelpCommand component.
     * @return the initialized component instance
     */
    public Command getHelpCommand() {
        if (HelpCommand == null) {//GEN-END:|48-getter|0|48-preInit
            // Insert pre-init code here
            HelpCommand = new Command("Help", Command.OK, 1);//GEN-LINE:|48-getter|1|48-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|48-getter|2|
        return HelpCommand;
    }
    //</editor-fold>//GEN-END:|48-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: optionsCommand ">//GEN-BEGIN:|55-getter|0|55-preInit
    /**
     * Returns an initiliazed instance of optionsCommand component.
     * @return the initialized component instance
     */
    public Command getOptionsCommand() {
        if (optionsCommand == null) {//GEN-END:|55-getter|0|55-preInit
            // Insert pre-init code here
            optionsCommand = new Command("Options", Command.OK, 1);//GEN-LINE:|55-getter|1|55-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|55-getter|2|
        return optionsCommand;
    }
    //</editor-fold>//GEN-END:|55-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: ticker1 ">//GEN-BEGIN:|16-getter|0|16-preInit
    /**
     * Returns an initiliazed instance of ticker1 component.
     * @return the initialized component instance
     */
    public Ticker getTicker1() {
        if (ticker1 == null) {//GEN-END:|16-getter|0|16-preInit
            // Insert pre-init code here
            ticker1 = new Ticker("Smartmap (c) 2008 Alfredo A. Ortega  -  ortegaalfredo@gmail.com");//GEN-LINE:|16-getter|1|16-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|16-getter|2|
        return ticker1;
    }
    //</editor-fold>//GEN-END:|16-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: image1 ">//GEN-BEGIN:|19-getter|0|19-preInit
    /**
     * Returns an initiliazed instance of image1 component.
     * @return the initialized component instance
     */
    public Image getImage1() {
        if (image1 == null) {//GEN-END:|19-getter|0|19-preInit
            // Insert pre-init code here
            try {//GEN-BEGIN:|19-getter|1|19-@java.io.IOException
                image1 = Image.createImage("/logoMiniMap.png");
            } catch (java.io.IOException e) {//GEN-END:|19-getter|1|19-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|19-getter|2|19-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|19-getter|3|
        return image1;
    }
    //</editor-fold>//GEN-END:|19-getter|3|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: ticker2 ">//GEN-BEGIN:|23-getter|0|23-preInit
    /**
     * Returns an initiliazed instance of ticker2 component.
     * @return the initialized component instance
     */
    public Ticker getTicker2() {
        if (ticker2 == null) {//GEN-END:|23-getter|0|23-preInit
            // Insert pre-init code here
            ticker2 = new Ticker("Type intersecting streets and select \"Ok\"");//GEN-LINE:|23-getter|1|23-postInit
            // Insert post-init code here
        }//GEN-BEGIN:|23-getter|2|
        return ticker2;
    }
    //</editor-fold>//GEN-END:|23-getter|2|







    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: frmFailData ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initiliazed instance of frmFailData component.
     * @return the initialized component instance
     */
    public TextBox getFrmFailData() {
        if (frmFailData == null) {//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
            frmFailData = new TextBox("Fail", "Couldn\'t load the map from storage.\n", 100, TextField.ANY);//GEN-LINE:|57-getter|1|57-postInit
            // write post-init user code here
        }//GEN-BEGIN:|57-getter|2|
        return frmFailData;
    }
    //</editor-fold>//GEN-END:|57-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: ZoomInCommand ">//GEN-BEGIN:|100-getter|0|100-preInit
    /**
     * Returns an initiliazed instance of ZoomInCommand component.
     * @return the initialized component instance
     */
    public Command getZoomInCommand() {
        if (ZoomInCommand == null) {//GEN-END:|100-getter|0|100-preInit
            // write pre-init user code here
            ZoomInCommand = new Command("Zoom In", Command.OK, 0);//GEN-LINE:|100-getter|1|100-postInit
            // write post-init user code here
        }//GEN-BEGIN:|100-getter|2|
        return ZoomInCommand;
    }
    //</editor-fold>//GEN-END:|100-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: ZoomOutCommand ">//GEN-BEGIN:|102-getter|0|102-preInit
    /**
     * Returns an initiliazed instance of ZoomOutCommand component.
     * @return the initialized component instance
     */
    public Command getZoomOutCommand() {
        if (ZoomOutCommand == null) {//GEN-END:|102-getter|0|102-preInit
            // write pre-init user code here
            ZoomOutCommand = new Command("Zoom Out", Command.OK, 1);//GEN-LINE:|102-getter|1|102-postInit
            // write post-init user code here
        }//GEN-BEGIN:|102-getter|2|
        return ZoomOutCommand;
    }
    //</editor-fold>//GEN-END:|102-getter|2|



    /**
     * This method should return an instance of the display.
     */

    /**
     * Returns a display instance.
     * @returnsplashScreen1 the display instance.
     */
public Display getDisplay () {

        return Display.getDisplay (this);
        // return Display.getDisplay (this);
}


    /**
     * This method should exit the midlet.
     */

    /**
     * Exits MIDlet.
     */
public void exitMIDlet() {

        getDisplay ().setCurrent (null);
        destroyApp (true);
        notifyDestroyed ();
        // switchDisplayable (null, null);
        // destroyApp(true);
        // notifyDestroyed();
}


    
    Screen screen;
    //Screen screen2;
    /**
     * Creates a new instance of ConvertedSmartMap2
     */
    
    java.util.Vector intersections ;
    Intersection mFirstIntersection,mLastIntersection;

    // Modos de busqueda para diferenciar las tandas para el colectivo, etc.
    final static int cSEARCHMODE_COMMON = 0;
    final static int cSEARCHMODE_BUSFIRST = 1;
    final static int cSEARCHMODE_BUSLAST = 2;
       
    int mSearchMode = cSEARCHMODE_COMMON;
    
    public SmartMap() {
    }
    
   


  private RefreshClipThread mRefreshThread;
  boolean  midletPaused = false;

  public void startApp() {
        getTicker1().setString("Smartmap (c) 2006-2008 Alfredo A. Ortega (ortegaalfredo@gmail.com) -  Map '"+this.getAppProperty("Mapa")+"' created by "+this.getAppProperty("Author"));
        if (midletPaused) {
            resumeMIDlet ();
            return;
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
       
        try {
        screen = new Screen("ba.fcmlite");
        } catch (Exception e) {
                switchDisplayable(null, this.getFrmFailData());
                exitMIDlet();
                };
        // Carga checks de opciones
        getChoiceGroup1().setSelectedIndex(0,screen.mRT.softScroll);
        getChoiceGroup1().setSelectedIndex(1,((POLYLINEcell)(screen.mPOLYLINEs)).fastMode);
        
        screen.addCommand(getZoomInCommand());
        screen.addCommand(getZoomOutCommand());
        screen.addCommand(getExitCommand());
        screen.addCommand(getSearchIntersectCommand());
        screen.addCommand(getSearchSingleCommand());
        screen.addCommand(getOptionsCommand());
        screen.addCommand(getHelpCommand());
        screen.setCommandListener(this);

        mRefreshThread = new RefreshClipThread(screen);
        mRefreshThread.start();
       // switchDisplayable(null, screen);
       
    }
  
    private void doSubSearchSingle (String aElement,Cell aCell) {
        int lTotalLength = aCell.mPoints.length;
        gauge1.setMaxValue(lTotalLength);
        int lCont=0,indexStreet1 = 0,indexStreet2 = 0;
        String lStreet1FullName="";
        
        for (short i=0;i<lTotalLength;i++) {
                if ((i % 100) == 0)
                    gauge1.setValue(i);
                String lStreet3 = aCell.getCaption(i).toLowerCase();
                if (lStreet3.indexOf(aElement)>=0) {
                                    Intersection lInt = new Intersection();
                                    lInt.streetA=i;
                                    lInt.streetB=-1;
                                    // sacamos promedio
                                    lInt.point= new Point2D();
                                    int lVertexIndex=0;
                                    for (int t=0;t<i;t++) 
                                        lVertexIndex+=aCell.mPoints[t];
                                    lInt.point.x=aCell.Px[lVertexIndex];
                                    lInt.point.y=aCell.Py[lVertexIndex];
                                    intersections.addElement(lInt);
                                    getListResults().append((++lCont)+" - "+aCell.getCaption(i),null);
                                    }
                }
    }
    
    private void doSearchSingle(String aElement) {
        getListResults().deleteAll();
        aElement=aElement.toLowerCase();
        intersections = new java.util.Vector();
        doSubSearchSingle(aElement,screen.mPOLYLINEs);
        doSubSearchSingle(aElement,screen.mPois);
        doSubSearchSingle(aElement,screen.mPOLYGONs);
        gauge1.setValue(0);
    }

    private void doSearchIntersection(String aStreet1,String aStreet2) {
        getListResults().deleteAll();
        aStreet1=aStreet1.toLowerCase();
        aStreet2=aStreet2.toLowerCase();
        int lTotalLength = screen.mPOLYLINEs.mPoints.length;
        gauge1.setMaxValue(lTotalLength);
        int lCont=0,indexStreet1 = 0,indexStreet2 = 0;
        intersections = new java.util.Vector();
        String lStreet1FullName="",lStreet2FullName="";
        java.util.Vector lFoundX = new java.util.Vector();
        
        java.util.Vector lFoundY = new java.util.Vector();
        // Como existen calles con nombres repetidos, armamos la lista X 
        // con las que se parecen a la primera, y la lista Y con las que 
        // se parecen a la segunda, y calculamos todas las intersecciones.
        for (short i=0;i<lTotalLength;i++) {
                if ((i % 100) == 0)
                    gauge1.setValue(i);
                String lStreet3 = screen.mPOLYLINEs.getCaption(i).toLowerCase();
                if (lStreet3.indexOf(aStreet1)>=0)
                         lFoundX.addElement(new Integer(i));
                if (lStreet3.indexOf(aStreet2)>=0)
                         lFoundY.addElement(new Integer(i));
                }
        
        if ((lFoundX.size()>0) && (lFoundY.size()>0)) { 
                 Point2D I = new Point2D();
                 for (int y=0;y<lFoundY.size();y++) {
                     indexStreet1=((Integer)lFoundY.elementAt(y)).intValue();
                     for (int x=0;x<lFoundX.size();x++) {
                        indexStreet2=((Integer)lFoundX.elementAt(x)).intValue();
                        if (((POLYLINEcell)screen.mPOLYLINEs).polyLineIntersection(indexStreet1,indexStreet2,I)) {
                            Intersection lInt = new Intersection();
                            lInt.streetA=(short)indexStreet1;
                            lInt.streetB=(short)indexStreet2;
                            lInt.point= new Point2D();
                            lInt.point.x=I.x;
                            lInt.point.y=I.y;
                            intersections.addElement(lInt);
                            lStreet1FullName=screen.mPOLYLINEs.getCaption((short)indexStreet1);
                            lStreet2FullName=screen.mPOLYLINEs.getCaption((short)indexStreet2);
                            getListResults().append(lStreet1FullName+" y "+lStreet2FullName,null);
                            }
                        }
                    }
                }
            gauge1.setValue(0);
            try { java.lang.Thread.sleep(20); } catch (InterruptedException ex) { ex.printStackTrace(); }
    }
    
    private void doSearch() {
        String lStreet1=textField1.getString();
        String lStreet2=textField2.getString();
        if ((lStreet1.length()>0) && (lStreet2.length()>0)) 
            doSearchIntersection(lStreet1,lStreet2);
        if ((lStreet1.length()>0) && (lStreet2.length()==0)) 
            doSearchSingle(lStreet1);
        if ((lStreet1.length()==0) && (lStreet2.length()>0)) 
            doSearchSingle(lStreet2);
    }
    
  /* Setea el mapa para que se visualize la interseccion dada */
  public void doGotoIntersection() {
  int index = getListResults().getSelectedIndex();
  Intersection lInt = (Intersection) intersections.elementAt(index);
  screen.mScale=5000;
  screen.mTranslateX=-(lInt.point.x-(screen.getWidth()/2)*screen.mScale);
  screen.mTranslateY=-(lInt.point.y-(screen.getHeight()/2)*screen.mScale);
  screen.calculateMatrix();
  screen.mCursor.x=screen.mMatrix.transformXF(lInt.point.x);
  screen.mCursor.y=screen.mMatrix.transformYF(lInt.point.y);
  screen.mCursorMode=screen.cMODE_CURSOR;
  screen.mPOLYLINEs.mActiveIntersection=lInt;
  Display.getDisplay(this).setCurrent(screen);
  screen.mRT.scaleChange=1;
  //screen.mDirty=true;
  //screen.mRepainting=false;
  //screen.mTranslateX=1;
  //screen.repaint();
  }
    
   public void pauseApp() {
         midletPaused = true;
 }
    
    public void destroyApp(boolean unconditional) {
    }
    /* Busca el colectivo que pase por ambas intersecciones */
  public void doSearchBus() {
      
  }
    


// HINT - Uncomment for accessing new MIDlet Started/Resumed logic.
// NOTE - Be aware of resolving conflicts of following methods.
//    /**
//     * Called when MIDlet is started.
//     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
//     */
//    public void startApp() {
//        if (midletPaused) {
//            resumeMIDlet ();
//        } else {
//            initialize ();
//            startMIDlet ();
//        }
//        midletPaused = false;
//    }
//
//    /**
//     * Called when MIDlet is paused.
//     */
//    public void pauseApp() {
//        midletPaused = true;
//    }
//
//    /**
//     * Called to signal the MIDlet to terminate.
//     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
//     */
//    public void destroyApp(boolean unconditional) {
//    }

}
