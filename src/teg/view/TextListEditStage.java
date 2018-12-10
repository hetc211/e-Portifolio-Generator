/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import static teg.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static teg.StartupConstants.ICON_ADD_PAGE;
import static teg.StartupConstants.ICON_REMOVE_PAGE;
import static teg.StartupConstants.TOOLTIP_ADD_COMPONENT;
import static teg.StartupConstants.TOOLTIP_ADD_LIST_ITEM;
import static teg.StartupConstants.TOOLTIP_REMOVE_LIST_ITEM;
import teg.controller.SiteEditController;
import teg.model.TList;

/**
 *
 * @author HTC
 */
public class TextListEditStage extends Stage{//Dialog<ButtonType>{
    EPortfolioGeneratorView ui;
    Button addBT;
    Button removeBT;
    ObservableList<TList> lists;
    SiteEditController siteEditController;
    BorderPane vb;
    VBox itemVB;
    FlowPane toolbar;
    TList selectedItem;
    public ScrollPane itemScrollPane;
    Button okBT;
    Button cancleBT;
    FlowPane controllBar;
    
    public TextListEditStage(EPortfolioGeneratorView initUI,SiteEditController initSiteEditController) throws CloneNotSupportedException {
        this.siteEditController = initSiteEditController;
        ui = initUI;
        //this.setHeight(800);
        //this.setWidth(1000);
        getData();
        //lists = ui.getEPModel().getSelectedSlide().getSelectedContent().component.lists;
        selectedItem = ui.getEPModel().getSelectedSlide().getSelectedContent().component.selectedItem;
        this.setTitle("Edit List");
        //this.setHeaderText(null);
        //this.setGraphic(null);
       // this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        vb = new BorderPane();
        vb.setStyle("-fx-padding:20px 20px 20px 20px");
        //vb.setAlignment(Pos.CENTER);
        itemVB = new VBox();
        //itemVB.setMaxHeight(500);
        itemScrollPane = new ScrollPane(itemVB);
        
        toolbar = new FlowPane();
        toolbar.setHgap(20);
        controllBar = new FlowPane();
        controllBar.setHgap(20);
        controllBar.setVgap(20);
        controllBar.setAlignment(Pos.CENTER);
        addBT = ui.initChildButton(toolbar,ICON_ADD_PAGE,TOOLTIP_ADD_LIST_ITEM,CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
        removeBT = ui.initChildButton(toolbar,ICON_REMOVE_PAGE,TOOLTIP_REMOVE_LIST_ITEM,CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
        okBT = new Button("OK");
        cancleBT = new Button("Cancle");
        controllBar.getChildren().addAll(okBT,cancleBT);
        
        initEventHandler();
        this.updateRemoveBT();
        toolbar.setPadding(new Insets(11, 12, 13, 14));
        toolbar.setHgap(20);
        toolbar.setAlignment(Pos.CENTER);
        
        
        //BorderPane bp = new BorderPane();
        //bp.setTop(fp);
        
        /*GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        for(int i=0; i<lists.size(); i++){
            Label lb = new Label("Item "+i+": ");
            TextField tf = new TextField(lists.get(i).text);
            gp.add(lb, 0, i);
            gp.add(tf, 1, i);
        }*/
        //vb.getChildren().addAll(toolbar,itemScrollPane,controllBar);
        vb.setTop(toolbar);
        vb.setCenter(itemScrollPane);
        vb.setBottom(controllBar);
        loadItem();
        Scene scene = new Scene(vb, 500, 500);
        this.initModality(Modality.WINDOW_MODAL);
        this.setScene(scene);
        this.showAndWait();
        //this.getDialogPane().setMaxHeight(800);
        //this.getDialogPane().setContent(vb);
        //Optional<ButtonType> result = this.showAndWait();
        //if (result.isPresent()) {
        //    handleChange();
        //}
        
    }
    
    public void loadItem() throws CloneNotSupportedException{
        itemVB.getChildren().clear();
        for(int i =0; i<lists.size(); i++){
            ItemEditView item = new ItemEditView(this,lists.get(i),i);
            itemVB.getChildren().add(item);
        }
    }

    public void reloadItem() throws CloneNotSupportedException {
        itemVB.getChildren().clear();
        for(int i =0; i<lists.size(); i++){
            ItemEditView item = new ItemEditView(this,lists.get(i),i);
            if(selectedItem!=null&&selectedItem.equals(item.item)){
                item.setStyle("-fx-background-color: blue");
            }
            itemVB.getChildren().add(item);
        }
    }

    public void handleChange() throws CloneNotSupportedException {
        ui.getEPModel().getSelectedSlide().getSelectedContent().component.lists = this.lists;
        ui.updateToolbarControls(false);
        this.close();
    }

    public void initEventHandler() {
        addBT.setOnAction(e->{
            try {
                processAdd();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TextListEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        removeBT.setOnAction(e->{
            try {
                processRemove();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TextListEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        okBT.setOnAction(e->{
            try {
                handleChange();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TextListEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cancleBT.setOnAction(e->{
            this.close();
        });
        
    }

    public void processAdd() throws CloneNotSupportedException {
        TList listToAdd = new TList("");
        lists.add(listToAdd);
        reloadItem();
        this.updateRemoveBT();
    }

    public void processRemove() throws CloneNotSupportedException {
        lists.remove(selectedItem);
        reloadItem();
        updateRemoveBT();
    }

    public void updateRemoveBT() {
        if(lists.size()!=0){
            removeBT.setDisable(false);
        }
        else 
            removeBT.setDisable(true);
    }
    public void getData() throws CloneNotSupportedException{
        lists = FXCollections.observableArrayList();
        for(TList a : ui.getEPModel().getSelectedSlide().getSelectedContent().component.lists){
            TList itemToAdd = new TList("");
            itemToAdd = (TList) a.clone();
            lists.add(itemToAdd);
        }
    }
}
