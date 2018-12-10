/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import static teg.StartupConstants.*;
import teg.controller.SlideShowEditController;
import teg.model.Slide;

/**
 *
 * @author HTC
 */
public class SlideShowEditStage extends Stage{
    HBox workspace;
    VBox slideEditToolbar;
    Button addSlideButton;
    Button removeSlideButton;
    Button moveUpButton;
    Button moveDownButton;
    public EPortfolioGeneratorView ui;
    VBox slidesEditorPane;
    ScrollPane slidesEditorScrollPane;
    public ObservableList<Slide> slides;
    public Button finishBT;
    public Button exitBT;
    public Slide selectedSlide;
    public SlideShowEditController editController;
    
    
    public SlideShowEditStage(EPortfolioGeneratorView initUI) throws CloneNotSupportedException{
        ui = initUI;
        this.setTitle("Edit SlideShow");
        this.slides = FXCollections.observableArrayList();
        this.selectedSlide = ui.getEPModel().selectedSite.getSelectedContent().getComponent().selectedSlide;
        loadData();
        workspace = new HBox();
	Scene scene = new Scene(workspace,500,800);
        scene.getStylesheets().add(STYLE_SHEET_UI);
        try {
            initWorkspace();
        } catch (MalformedURLException ex) {
            Logger.getLogger(SlideShowEditStage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initEventHandler();
        //slideEditToolbar.setStyle("-fx-background-color:red;");
	this.initModality(Modality.WINDOW_MODAL);
        this.setScene(scene);
        this.showAndWait();
    }

    public void initWorkspace() throws MalformedURLException {//throws MalformedURLException {
        workspace.getStyleClass().add(CSS_CLASS_WORKSPACE);
	// THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
        slideEditToolbar = new VBox();
        slideEditToolbar.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
        //slideEditToolbar.setStyle("-fx-background-radius: 5.0;-fx-background-color: red;-fx-border-color: rgb(50, 5, 5);-fx-spacing: 20px;");
        addSlideButton = ui.initChildButton(slideEditToolbar, ICON_ADD_PAGE, TOOLTIP_ADD_SLIDE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, false);
        removeSlideButton = ui.initChildButton(slideEditToolbar, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_SLIDE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        moveUpButton = ui.initChildButton(slideEditToolbar, ICON_MOVE_UP, TOOLTIP_MOVE_UP, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        moveDownButton = ui.initChildButton(slideEditToolbar, ICON_MOVE_DOWN, TOOLTIP_MOVE_DOWN, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        finishBT = ui.initChildButton(slideEditToolbar, ICON_SAVE_EP1, TOOLTIP_SAVE_EXIT, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        exitBT = ui.initChildButton(slideEditToolbar, ICON_EXIT1, TOOLTIP_SAVE_EXIT, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, false);
        this.updateToolbarControls(true);
        slideEditToolbar.setAlignment(Pos.CENTER);

        // AND THIS WILL GO IN THE CENTER
        slidesEditorPane = new VBox();
        slidesEditorScrollPane = new ScrollPane(slidesEditorPane);

        // NOW PUT THESE TWO IN THE WORKSPACE
        workspace.getChildren().add(slideEditToolbar);
        workspace.getChildren().add(slidesEditorScrollPane);
        
        loadSlideShow();
    }

    public void loadSlideShow() throws MalformedURLException {
        slidesEditorPane.getChildren().clear();
        for(Slide slide : slides){
            SlideEditView slideEditor = new SlideEditView(slide,this);
            if(slide.equals(this.selectedSlide)){
                slideEditor.setStyle("-fx-background-color:#99ccff;");
            }
            slideEditor.getStyleClass().add(CSS_CLASS_SLIDE_SHOW_EDIT_VBOX);
            slidesEditorPane.getChildren().add(slideEditor);
        }
    }

    public void loadData() throws CloneNotSupportedException {
        //this.slides.clear();
        this.selectedSlide = null;
        for(Slide slide: this.ui.getEPModel().selectedSite.getSelectedContent().component.slides){
            Slide s = (Slide) slide.clone();
            this.slides.add(s);
        }
    }

    public void initEventHandler() {
        editController = new SlideShowEditController(this);
	addSlideButton.setOnAction(e -> {
            try {
                editController.processAddSlideRequest();
            } catch (MalformedURLException ex) {
                Logger.getLogger(SlideShowEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        removeSlideButton.setOnAction(e -> {
            try {
                editController.processRemoveSlideRequest();
            } catch (MalformedURLException ex) {
                Logger.getLogger(SlideShowEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        moveUpButton.setOnAction(e -> {
            try {
                editController.processMoveUpRequest();
            } catch (MalformedURLException ex) {
                Logger.getLogger(SlideShowEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        moveDownButton.setOnAction(e ->{
            try { 
                editController.processMoveDownRequest();
            } catch (MalformedURLException ex) {
                Logger.getLogger(SlideShowEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        finishBT.setOnAction(e->{
           editController.processSaveRequest();
        });
        exitBT.setOnAction(e->{
            editController.processExit();
        });
    
    }

    public ObservableList<Slide> getSlides() {
        return this.slides;
    }
    
    public Slide getSelectedSlide(){
        return this.selectedSlide;
    }

    public void updateToolbarControls(boolean saved) {
        if(slides.size()==0){
            removeSlideButton.setDisable(true);
            moveUpButton.setDisable(true);
            moveDownButton.setDisable(true);
            //finishBT.setDisable(saved);
        }
        else{
            //removeSlideButton.setDisable(false);
            if(slides.size()==1){
                moveUpButton.setDisable(true);
                moveDownButton.setDisable(true);
                
            }
            else{
                //moveUpButton.setDisable(false);
                //moveDownButton.setDisable(false);
            
                if(slides.indexOf(this.selectedSlide)==slides.size()-1){
                    moveUpButton.setDisable(false);
                    moveDownButton.setDisable(true);
                    
                }
                else if(slides.indexOf(this.selectedSlide)==0){
                    moveUpButton.setDisable(true);
                    moveDownButton.setDisable(false);
                }
                else{
                    moveUpButton.setDisable(false);
                    moveDownButton.setDisable(false);
                }
                //finishBT.setDisable(saved);
            }
            if(selectedSlide==null){
                removeSlideButton.setDisable(true);
                moveUpButton.setDisable(true);
                moveDownButton.setDisable(true);
            }
            else{
                removeSlideButton.setDisable(false);
            }
        }
        finishBT.setDisable(saved);
    }

    public void reloadSlideShowPane(ObservableList<Slide> slides) throws MalformedURLException {
        slidesEditorPane.getChildren().clear();
	for (Slide slide : slides) {
	    SlideEditView slideEditor = new SlideEditView(slide, this);
            if(slide.equals(this.selectedSlide)){
                slideEditor.setStyle("-fx-background-color: #99ccff");
            }
            slidesEditorPane.getChildren().add(slideEditor);
	}
    
    }
}
