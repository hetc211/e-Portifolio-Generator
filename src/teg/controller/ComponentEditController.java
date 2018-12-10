/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;
import teg.model.Content;
import teg.model.EPModel;
import teg.view.EPortfolioGeneratorView;

/**
 *
 * @author HTC
 */
public class ComponentEditController {
    public EPortfolioGeneratorView ui;
    public EPModel ep;
    
    public ComponentEditController(EPortfolioGeneratorView initUI){
        this.ui = initUI;
        this.ep = initUI.getEPModel();
    }
    
    public void handleAddComponentRequest(){
        processAddComponent();
        //ep.selectedSite.getContents().add(null);
        
    }

    public void processAddComponent() {
        List<String> choices = new ArrayList<>();
        choices.add("paragraph");
        choices.add("lists");
        choices.add("header");
        choices.add("image");
        choices.add("video");
        choices.add("slideShow");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("paragraph", choices);
        
        dialog.setTitle("Add component");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Choose the tyle of component:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ep.selectedSite.addContent(result.get());
            ui.updateToolbarControls(false);
            ui.reLoadContents();
            ui.updateRCBT();
            ui.updateEditCompBT();
        }   
    
    }

    public void handleRemoveComponentRequest() {
        ep.selectedSite.getContents().remove(ep.selectedSite.getSelectedContent());
        ui.reLoadContents();
        ui.updateToolbarControls(false);
        ui.updateRCBT();
        ui.updateEditCompBT();
    }
    
    
    
}
