/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import teg.controller.SiteEditController;
import teg.model.HyperLink;
import teg.model.TList;

/**
 *
 * @author HTC
 */
public class ItemEditView extends FlowPane{
    TList item;
    Label lb;
    TextField tf;
    public ObservableList<HyperLink> hyperlinks;
    Button hyBT;
    Button okBT;
    TextListEditStage ui;
    

    public ItemEditView(TextListEditStage initUI, TList list, int i) throws CloneNotSupportedException {
        ui = initUI;
        item = list;
        loadHY();
        
        //hyperlinks = item.hyperlinks;
        
        this.setPadding(new Insets(11, 12, 13, 14));
        this.setHgap(20);
        this.setAlignment(Pos.CENTER);
        
        lb = new Label("Item "+ i +": ");
        tf = new TextField(item.text);
        hyBT = new Button("Edit hyperlink");
        okBT = new Button("OK");
        
        this.getChildren().addAll(lb,tf,hyBT,okBT);
        initEventhandler();
        this.setStyle("-fx-border-color: red");
        //item.text = tf.getText();
        
        
    }

    public void initEventhandler() {
        this.setOnMousePressed(e -> {
	    this.processHighLight();
        });
        hyBT.setOnAction(e->{
            processAddHyp();
        });
        okBT.setOnAction(e->{
            processSave();
        });
        
    }

    private void processHighLight() {
        //ui.getEPModel().selectedSite.getSelectedContent().getComponent().setSelectedItem(this.item);
        ui.selectedItem = this.item;
    }

    private void processAddHyp() {
        //HyperLink h = new HyperLink();
        //hyperlinks.clear();
        //hyperlinks.add(h);
        if(!tf.getText().equals(item.text)){
            hyperlinks.clear();
            HyperLink hy = new HyperLink();
            hyperlinks.add(hy);
        }
        AddHyperlinkView hyView = new AddHyperlinkView(this);
        hyperlinks.clear();
        hyperlinks.add(hyView.hy);
        
        
    }

    public void processSave() {
        item.text = tf.getText();
        item.hyperlinks = this.hyperlinks;
        //ui.updateToolbarControls(false);
    }

    public void loadHY() throws CloneNotSupportedException {
        hyperlinks = FXCollections.observableArrayList();
        for(HyperLink h : item.hyperlinks){
                HyperLink hy = new HyperLink();
                hy = (HyperLink) h.clone();
                hyperlinks.add(hy);
        }
        if(hyperlinks.size()==0){
            HyperLink hy = new HyperLink();
            hyperlinks.add(hy);
        }
    
    }
    
}
