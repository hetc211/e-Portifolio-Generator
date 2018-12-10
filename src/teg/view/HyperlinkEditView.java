/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import teg.model.HyperLink;

/**
 *
 * @author HTC
 */
public class HyperlinkEditView extends FlowPane{
    GridPane gp;
    Label Lb;
    Label L0;
    Label text;
    TextField link;
    Button okBT;
    TextParagraphEditStage ui;
    HyperLink hy;
    
    public HyperlinkEditView(HyperLink hyperlink, String bIndex, String eIndex,TextParagraphEditStage ui){
        
            hy = hyperlink;
            this.ui = ui;
            gp = new GridPane();
            gp.setAlignment(Pos.CENTER);
            gp.setHgap(5.5);
            gp.setVgap(5.5);

            Lb = new Label("Text: ");
            L0 = new Label("URL: ");
            text = new Label(hyperlink.text);
            link = new TextField(hyperlink.link);
            okBT = new Button("OK");


            gp.add(Lb, 0, 0);
            gp.add(L0, 0, 1);
            gp.add(text, 1, 0);
            gp.add(link, 1, 1);
            gp.add(okBT, 2, 0);

            this.getChildren().add(gp);
            
            okBT.setOnAction(e->{
                hy.startIndex = bIndex;
                hy.endIndex = eIndex;
                //hyperlink.text = text.getText();
                hy.link = link.getText();
            });

            this.setOnMousePressed(e->{
                this.ui.currentHY = hy;
                ui.reloadhy();
                ui.updateRBT();
            });
        
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color: black;");
        
        
    }
    
}
