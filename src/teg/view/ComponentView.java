/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import static teg.StartupConstants.CSS_CLASS_TOOLTIP_SETTING;
import static teg.StartupConstants.PATH_ICONS;

/**
 *
 * @author HTC
 */
public class ComponentView extends HBox{
    public Label style;
    public Button editBT;
    EPortfolioGeneratorView ui;
    GridPane gp; 
    public ComponentView(String initStyle, EPortfolioGeneratorView initUI){
        ui = initUI;
        
        style = new Label("This is a "+initStyle+" component");
        editBT = new Button();
        String imagePath = "file:" + PATH_ICONS + "Edit.png";
	Image buttonImage = new Image(imagePath);
        Tooltip buttonTooltip = new Tooltip("Edit component");
        editBT.getStyleClass().add(CSS_CLASS_TOOLTIP_SETTING);
	editBT.setDisable(false);
	editBT.setGraphic(new ImageView(buttonImage));
        buttonTooltip.getStyleClass().add("/");
        editBT.setTooltip(buttonTooltip);
        
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        gp.add(style, 0, 0);
        gp.add(editBT, 0, 1);
        
    }
}
