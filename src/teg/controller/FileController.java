/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import static teg.StartupConstants.PATH_DATA;
import teg.error.ErrorHandler;
import teg.model.EPModel;
import teg.view.EPortfolioGeneratorView;
import teg.file.TEGFileMannager;
/**
 *
 * @author HTC
 */
public class FileController {
    private boolean saved;
    private EPortfolioGeneratorView ui;
    private TEGFileMannager ePIO;
    
    
    public FileController(EPortfolioGeneratorView initUI, TEGFileMannager initEPIO) {
       ui = initUI;
       ePIO = initEPIO;
       saved = true;
    }
    
    public void setSaved(Boolean initSaved){
        saved  = initSaved;
    }
    
    public boolean handleSaveSlideShowRequest() {
        try {
	    // GET THE SLIDE SHOW TO SAVE
	    EPModel ePToSave = ui.getEPModel();
	    
            // SAVE IT TO A FILE
            ePIO.saveEP(ePToSave,PATH_DATA + "/" + ePToSave.getStudentName() + ".json");

            // MARK IT AS SAVED
            saved = true;
            
            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            ui.updateToolbarControls(saved);
	    return true;
        } catch (IOException ioe) {
    //        ErrorHandler eH = ui.getErrorHandler();
            // @todo
	    return false;
        }
    }
    
    public void handleNewEPRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            saved = ui.saveEPButton.isDisabled();

            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL

                continueToMakeNew = promptToSave();
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI

                EPModel ep = ui.getEPModel();

                ep.reset();
                
                TextInputDialog makeNewEP = new TextInputDialog("default");
                makeNewEP.setTitle("New eportfolio");
                makeNewEP.setHeaderText(null);
                makeNewEP.setGraphic(null);
                makeNewEP.setContentText("Please enter the title:");
                
                Optional<String> result = makeNewEP.showAndWait();
                if (result.isPresent()) {
                    ep.setStudentName(result.get());
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("New eportfolio has build successfully!");

                    alert.showAndWait();
                    
                    //ui.reloadWorkspace();
                    ui.addWorkspace();
                    saved = false;
                    // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                    // THE APPROPRIATE CONTROLS
                    ui.updateTitle();
                    ui.updateToolbarControls(saved);
                    ui.reloadListOfSitesPane(ep);
                    ui.initInnerWorkspace();
                }
                
                
                
                // TELL THE USER THE SLIDE SHOW HAS BEEN CREATED

                // @todo
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            // @todo provide error message
        }
    
    }

    public void handleLoadEPRequest() {
        saved = ui.saveEPButton.isDisabled();
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A POSE
            if (continueToOpen) {
                // GO AHEAD AND PROCEED MAKING A NEW POSE
                promptToOpen();
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            //@todo provide error message
            
        }
    }

    public boolean handleSaveEPRequest() {
        try {
	    // GET THE SLIDE SHOW TO SAVE
	    EPModel ePToSave = ui.getEPModel();
	    
            // SAVE IT TO A FILE
            ePIO.saveEP(ePToSave,PATH_DATA + "/" + ePToSave.getStudentName() + ".json");

            // MARK IT AS SAVED
            saved = true;
            ui.addWorkspace();
            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            ui.updateToolbarControls(saved);
	    return true;
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            // @todo
	    return false;
        }
    
    }

    public void handleSaveAsEPRequest() throws IOException {
        EPModel ep = ui.getEPModel();
        File file;
       
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As File");
        fileChooser.setInitialDirectory(new File(PATH_DATA));
        fileChooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Json File", "*.json"),
                        new ExtensionFilter("All Files", "*.*"));
        file = fileChooser.showSaveDialog(ui.getWindow());
        
        if(file!=null){
            ep.setStudentName(file.getName().substring(0, file.getName().length()-5));
                /*FileWriter writer = null;
                try {
                    writer = new FileWriter(file);
                    writer.flush();
                } catch (IOException ex) {
                   Logger.getLogger(EPortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    writer.close();
                }*/
                ePIO.saveEP(ep, ""+file.getPath().replaceAll("/", "\\"));
                ui.updateTitle();
                ui.updateToolbarControls(saved);
        }
                
            //}
        //}
    }

    public void handleExportEPRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void handleExitRequest() {
        saved = ui.saveEPButton.isDisabled();
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            //ErrorHandler eH = ui.getErrorHandler();
            // @todo
        }
    
    }

    private boolean promptToSave() throws IOException {
        boolean saveWork = false; // @todo change this to prompt
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ask to save work");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to save current work?");
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(ui.getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            saveWork = true;
        } 
        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (saveWork) {
            EPModel ep = ui.getEPModel();
            ePIO.saveEP(ep,PATH_DATA + "/" + ep.getStudentName() + ".json");
            saved = true;
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (!true) {
            return false;
        }
        
        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    
    }

    public void promptToOpen() {
        FileChooser epFileChooser = new FileChooser();
        epFileChooser.setInitialDirectory(new File(PATH_DATA));
        File selectedFile = epFileChooser.showOpenDialog(ui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
		EPModel ePToLoad = ui.getEPModel();
                ePIO.loadEP(ePToLoad, selectedFile.getPath().replaceAll("/", "\\"));
                ePToLoad.setSelectedSite(null);
                //ui.reloadListOfSitesPane(ePToLoad);
                saved = true;
                ui.addWorkspace();
                ui.updateTitle();
                ui.updateToolbarControls(true);
                ui.reloadListOfSitesPane(ePToLoad);
                
                //ui.reloadSiteworkSpace();
                ui.updateSiteToolbarControls();
                ui.initInnerWorkspace();
            } catch (Exception e) {
                ErrorHandler eH = ui.getErrorHandler();
                eH.processError("Failed to load file");
            }
        }
    }
    
}
