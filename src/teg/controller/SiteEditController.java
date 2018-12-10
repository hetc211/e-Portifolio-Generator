/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.controller;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import teg.model.EPModel;
import teg.model.Font;
import teg.model.Site;
import teg.view.EPortfolioGeneratorView;

/**
 *
 * @author HTC
 */
public class SiteEditController {
    private EPortfolioGeneratorView ui;
    
    public Dialog<ButtonType> dialog;
    public GridPane gp;
    public Label titleLB;
    public Label layouLB;
    public Label fontLB;
    public Label colorLB;
    public TextField titleTF;
    public ChoiceBox layoutCB;
    public ChoiceBox fontCB;
    public ChoiceBox colorCB;
    public TextField fontSizeTF;
    public ButtonType okBT;
    ObservableList<String> fontList;
    
    public SiteEditController(EPortfolioGeneratorView initUI){
        ui = initUI;
        fontList = FXCollections.observableArrayList();
        
    }
    
    public void handleAddSiteRequest() {
        EPModel ep = ui.getEPModel();
        initSiteSet();
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()){
            Font fontToSet = findSelectedFont(fontCB.getValue().toString());
            
            ep.addSiteWithFont(titleTF.getText(), layoutCB.getValue().toString(), fontToSet, colorCB.getValue().toString(),ui);
            ui.updateToolbarControls(false);
            ui.updateSiteToolbarControls();
            ui.initInnerWorkspace();
        }    
            
        
        
    }

    public void handleRemoveSiteRequest() {
        EPModel ep = ui.getEPModel();
        ep.getSites().remove(ep.getSelectedSlide());
        ui.reloadListOfSitesPane(ep);
        ui.updateToolbarControls(false);
        ui.updateSiteToolbarControls();
        ui.initInnerWorkspace();
    }

    public void initSiteSet() {
        
        dialog = new Dialog<>();
        dialog.setTitle("Add Site");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        titleTF = new TextField("default");
        titleLB = new Label("Enter the title: ");
        
        layoutCB = new ChoiceBox(FXCollections.observableArrayList("Layout_1","Layout_2","Layout_3","Layout_4","Layout_5"));
        layoutCB.getSelectionModel().selectFirst();
        Label L0 = new Label("Select a layout model: ");
        
        fontList.clear();
        for(Font name : ui.fonts){
            fontList.add(name.name);
        }
        fontCB = new ChoiceBox(FXCollections.observableArrayList(fontList));
        fontCB.getSelectionModel().selectFirst();
        Label L1 = new Label("Select a font: ");
        fontSizeTF = new TextField("12");
        Label L2 = new Label("Enter the font size: ");
        
        
        colorCB = new ChoiceBox(FXCollections.observableArrayList("red","yellow","blue","Green","white"));
        colorCB.getSelectionModel().selectFirst();
        Label L3 = new Label("Select a color theme: ");
        
        gp.add(titleLB, 0, 0);
        gp.add(L0, 0, 1);
        gp.add(L1, 0, 2);
        gp.add(L2, 0, 3);
        gp.add(L3, 0, 4);
        gp.add(titleTF, 1, 0);
        gp.add(layoutCB, 1, 1);
        gp.add(fontCB, 1, 2);
        gp.add(fontSizeTF, 1, 3);
        gp.add(colorCB, 1, 4);
        dialog.getDialogPane().setContent(gp);
    }

    public Font findSelectedFont(String s) {
        for(int i = 0; i <= ui.fonts.size(); i++){
            Font f = ui.fonts.get(i);
            if(f.name.equals(s)){
                return f;
            }
        }
        return (new Font());
    }
    
    
    
}
