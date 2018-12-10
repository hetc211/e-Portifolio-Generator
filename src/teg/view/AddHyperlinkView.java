/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import teg.model.HyperLink;

/**
 *
 * @author HTC
 */
public class AddHyperlinkView extends TextInputDialog{
    public HyperLink hy;
    ItemEditView ui;
    TextHeaderEditStage tui;
    public AddHyperlinkView(ItemEditView initUI){
        ui = initUI;
        //TextInputDialog dialog = new TextInputDialog(ui.hyperlinks.get(0).text);
        this.getEditor().setText(ui.hyperlinks.get(0).link);
        this.setTitle("Edit hyperlink");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.setContentText("Please enter the URL:");
        
        Optional<String> result = this.showAndWait();
        if (result.isPresent()) {
            this.hy = new HyperLink("0",Integer.toString(ui.tf.getText().length()-1),this.getEditor().getText(),ui.tf.getText());
        }
    }
    
    public AddHyperlinkView(TextHeaderEditStage initUI){
        tui = initUI;
        this.getEditor().setText(ui.hyperlinks.get(0).link);
        this.setTitle("Edit hyperlink");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.setContentText("Please enter the URL:");
        
        Optional<String> result = this.showAndWait();
        if (result.isPresent()) {
            this.hy = new HyperLink("0",Integer.toString(tui.tf.getText().length()-1),this.getEditor().getText(),tui.tf.getText());
        }
    }
    
    
    
    
    
}
