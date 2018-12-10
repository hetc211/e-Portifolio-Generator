/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import teg.controller.SiteEditController;
import teg.model.Component;
import teg.model.Content;
import teg.model.Font;
import teg.model.HyperLink;
import teg.model.Paragraph;

/**
 *
 * @author HTC
 */
public class TextParagraphEditStage extends Stage{
    private final TextArea paragraphTA;
    private final ChoiceBox fontCB;
    private final TextField fontSize;
    private final Button addHypBT;
    EPortfolioGeneratorView ui;
    SiteEditController siteEditController;
    Paragraph paragraph;
    Content content;
    public ObservableList<HyperLink> hyperlinks;
    public Component comp;
    public VBox vb;
    public ScrollPane hyperlinksPane;
    private final VBox hyDetailVB;
    private final Button removeHYBT;
    HyperLink currentHY;
    private final VBox vb1;
    private final Button okBT;
    private final Button cancleBT;
    private final VBox vb2;
    private final Button saveP;
   
    
    public TextParagraphEditStage(EPortfolioGeneratorView initUI,SiteEditController initSiteEditController) throws CloneNotSupportedException{
        this.siteEditController = initSiteEditController;
        ui = initUI;
        comp = ui.getEPModel().selectedSite.getSelectedContent().component;
        //paragraph = comp.paragraph;
        
        //content = ui.getEPModel().selectedSite.getSelectedContent();
        loadData();
        //hyperlinks = content.component.paragraph.hyperlinks;
        currentHY = null;
        this.setTitle("Edit Paragraph");
       
        BorderPane bp = new BorderPane();
        vb2 = new VBox();
        vb2.setSpacing(20);
        Label lb = new Label("Enter the paragraph: ");
        //TextArea 
        paragraphTA = new TextArea(paragraph.text);
        vb2.getChildren().addAll(lb,paragraphTA);
        vb = new VBox();
        //vb.getChildren().addAll(lb,paragraphTA);
        
        FlowPane hyControlbar = new FlowPane();
        hyControlbar.setHgap(20);
        hyControlbar.setAlignment(Pos.CENTER);
        
        addHypBT = new Button("Add hyperlink");
        addHypBT.setOnAction(e->{
            handleAddHyp(Integer.toString(paragraphTA.getSelection().getStart()),Integer.toString(paragraphTA.getSelection().getEnd()),paragraphTA.getText().substring(paragraphTA.getSelection().getStart(), paragraphTA.getSelection().getEnd()));
        });
        removeHYBT = new Button("Remove hyperlink");
        removeHYBT.setOnAction(e->{
            handleRemoveHy();
        });
        updateRBT();
        saveP = new Button("save paragraph");
        hyControlbar.getChildren().addAll(saveP, addHypBT, removeHYBT);
        saveP.setOnAction(e->{
            paragraph.text=paragraphTA.getText();
        });
        
        //vb.getChildren().add(addHypBT);
        hyDetailVB = new VBox();
        hyDetailVB.setSpacing(20);
        hyperlinksPane = new ScrollPane(hyDetailVB);
        hyperlinksPane.setPrefHeight(200);
        loadhy();
        vb.getChildren().addAll(hyControlbar,hyperlinksPane);
        //paragraphTA.setPromptText(content.component.paragraph.text);
        
        bp.setTop(vb2); 
        bp.setCenter(vb);
        //ChoiceBox
        Label L0 = new Label("Select a font: ");
        
        ObservableList<String> fontList = FXCollections.observableArrayList();
        for(Font name : ui.fonts){
            fontList.add(name.name);
        }
        fontCB = new ChoiceBox(FXCollections.observableArrayList(fontList));
        fontCB.getSelectionModel().select(paragraph.font.name);
        
        /*fontCB = new ChoiceBox(FXCollections.observableArrayList("Julius Sans One",
                "Lobster Two",
                "Orbitron",
                "Pacifico",
                "Poiret One")
        );
        fontCB.getSelectionModel().selectFirst();
        */
        
        Label L1 = new Label("Enter the size of font: ");
        //TextField 
        fontSize = new TextField(paragraph.font.size);
        //fontSize.setPromptText("12");
        
        
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        gp.add(L0, 0, 0);
        gp.add(L1, 0, 1);
        //gp.add(addHypBT, 0, 2);
        
        gp.add(fontCB,1,0);
        gp.add(fontSize,1,1);
        
        vb1 = new VBox();
        FlowPane controlPane = new FlowPane();
        okBT = new Button("OK"); 
        cancleBT = new Button("Cancle");
        
        okBT.setOnAction(e->{
            this.handleEditRequest();
        });
        cancleBT.setOnAction(e->{
            this.close();
        });
        
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setHgap(20);
        controlPane.getChildren().addAll(okBT,cancleBT);
        vb.setSpacing(20);
        vb.getChildren().addAll(gp,controlPane);
        bp.setBottom(vb1);
        
        Scene scene = new Scene (bp, 400, 600);
        this.setScene(scene);
        this.showAndWait();
        //this.getDialogPane().setContent(bp);
        
        //Optional<ButtonType> result = this.showAndWait();
        //if (result.isPresent()) {
        //    handleEditRequest();
        //}
        
    }

    private void handleEditRequest() {
        paragraph.text = paragraphTA.getText();
        Font fToAdd = siteEditController.findSelectedFont(fontCB.getValue().toString());
        fToAdd.size = fontSize.getText();
        paragraph.font = fToAdd;
        //content.setComponent(comp);
        //paragraph.hyperlinks = hyperlinks;*/
        ui.getEPModel().selectedSite.getSelectedContent().component.paragraph = paragraph;
        ui.updateToolbarControls(false);
        this.close();
    }

    public void handleAddHyp(String startIndex, String endIndex,String initText) {
        if(paragraph.text.equals(paragraphTA.getText())){
            if(startIndex.equals(endIndex)){
                ui.errorHandler.processError("Please select text phase first");
            }
            else{
                HyperLink h = new HyperLink(startIndex,endIndex,"", initText);
                hyperlinks.add(h);
                currentHY = h;
                reloadhy();
                updateRBT();
            }
        }
        else{
            hyperlinks.clear();
            if(startIndex.equals(endIndex)){
                ui.errorHandler.processError("Please select text phase first");
                reloadhy();
            }
            else{
                HyperLink h = new HyperLink(startIndex,endIndex,"", initText);
                hyperlinks.add(h);
                currentHY = h;
                reloadhy();
                updateRBT();
                reloadhy();
            }
        }
    }

    private void loadhy() {
        hyDetailVB.getChildren().clear();
        for(HyperLink h : hyperlinks){
            HyperlinkEditView view = new HyperlinkEditView(h,h.startIndex,h.endIndex,this);
            hyDetailVB.getChildren().add(view);
        }
    
    }
    
    public void reloadhy() {
        hyDetailVB.getChildren().clear();
        for(HyperLink h : hyperlinks){
            HyperlinkEditView view = new HyperlinkEditView(h,h.startIndex,h.endIndex,this);
            if(h.equals(currentHY)){
                view.setStyle("-fx-background-color: blue;");
            }
            hyDetailVB.getChildren().add(view);
        }
    }

    private void loadData() throws CloneNotSupportedException {
        paragraph = (Paragraph) ui.getEPModel().selectedSite.getSelectedContent().component.paragraph.clone();
        hyperlinks = paragraph.hyperlinks;
    }

    private void handleRemoveHy() {
        hyperlinks.remove(currentHY);
        currentHY = null;
        updateRBT();
        reloadhy();
    }
    
    public void updateRBT(){
        if(currentHY!=null){
            removeHYBT.setDisable(false);
        }
        else{
            removeHYBT.setDisable(true);
        }
    }
    
}
