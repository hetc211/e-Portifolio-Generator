/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teg.model.Hd;
import teg.model.HyperLink;

/**
 *
 * @author HTC
 */
public class TextHeaderEditStage extends Dialog<ButtonType>{
    public TextField tf;
    public Label L0;
    public Button addHY;
    public HyperLink hyperlink;
    public EPortfolioGeneratorView ui;
    public Hd hd;
    TextField tfURL;
    
    public TextHeaderEditStage(EPortfolioGeneratorView initUI){
        ui = initUI;
        hd = ui.getEPModel().selectedSite.getSelectedContent().component.header;
        this.setTitle("Edit Header");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        if(hd.hyperlinks.size()==0){
            hyperlink = new HyperLink();
        }
        else{
            hyperlink = hd.hyperlinks.get(0);
        }
        L0 = new Label("Please enter the header: ");
        tf = new TextField(hd.text);
        //addHY = new Button("Edit hyperlink");
        Label L1 = new Label("URL: ");
        tfURL = new TextField(hyperlink.link);
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        gp.add(L0, 0, 0);
        gp.add(tf, 1, 0);
        gp.add(L1, 0, 1);
        gp.add(tfURL, 1, 1);
        
        
        this.getDialogPane().setContent(gp);
       
        Optional<ButtonType> result = this.showAndWait();
        if (result.isPresent()) {
            handleEditRequest();
        }
    }

    public void handleEditRequest() {
        hd.text = tf.getText();
        hyperlink = new HyperLink("0",Integer.toString(tf.getText().length()-1),tfURL.getText(),hd.text);
        hd.hyperlinks = FXCollections.observableArrayList();
        hd.hyperlinks.add(hyperlink);
        ui.updateToolbarControls(false);
    }

}
