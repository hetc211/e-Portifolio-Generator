/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import static teg.StartupConstants.PATH_IMAGES;
import teg.model.Img;

/**
 *
 * @author HTC
 */
public class ImgEditStage extends Dialog<ButtonType>{
    EPortfolioGeneratorView ui;
    ImageView iv;
    String imagePath;
    File imageFile;
    TextField tfCaption;
    TextField tfHeight;
    TextField tfWidth;
    ChoiceBox cb;
    Img img;
    VBox workspace;
    private TextField tfID;
    
    public ImgEditStage(EPortfolioGeneratorView initUI) throws MalformedURLException{
        ui = initUI;
        this.setTitle("Edit Image");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        img = ui.getEPModel().selectedSite.getSelectedContent().component.image;
        
        //workspace = new VBox();
        //workspace.setAlignment(Pos.CENTER);
        //ImageView 
        //iv = new ImageView();
        
        initWindow();
        
        this.reloadImageView();
        /* 
        imagePath = PATH_IMAGES +img.imageName;
	File file = new File(imagePath);
        
        // GET AND SET THE IMAGE
        URL fileURL = file.toURI().toURL();
        Image slideImage = new Image(fileURL.toExternalForm());
        

        // AND RESIZE IT
        double scaledWidth = 200;
        double perc = scaledWidth / slideImage.getWidth();
        double scaledHeight = slideImage.getHeight() * perc;
        iv.setFitWidth(scaledWidth);
        iv.setFitHeight(scaledHeight);
        
        Image img1 = new Image(fileURL.toExternalForm());
        iv.setImage(img1);
        /*
        this.reloadImageView();
        iv.setOnMousePressed(e -> {
            handleSelectImageFile();
            try {
                reloadImageView();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ImgEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        /*
        Label lb = new Label("Enter the caption: ");
        tfCaption = new TextField("None");
        vb.getChildren().add(iv);
        
        GridPane gp= new GridPane();
        Label L1 = new Label("Enter the height: ");
        Label L2 = new Label("Enter the width: ");
        Label L3 = new Label("Float status");
        tfHeight = new TextField("300");
        tfWidth = new TextField("400");
        cb = new ChoiceBox(FXCollections.observableArrayList("none",
                "left",
                "right")
        );
        cb.getSelectionModel().selectFirst();
        
        gp.add(lb, 0, 0);
        gp.add(L1, 0, 1);
        gp.add(L2,0,2);
        gp.add(L3, 0, 3);
        gp.add(tfCaption, 1, 0);
        gp.add(tfHeight, 1, 1);
        gp.add(tfWidth, 1, 2);
        gp.add(cb, 1, 3);
        vb.getChildren().add(gp);
        
        this.getDialogPane().setContent(vb);
        */        
        iv.setOnMousePressed(e -> {
            handleSelectImageFile();
            try {
                reloadImageView();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ImgEditStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        Optional<ButtonType> result = this.showAndWait();
        if (result.isPresent()) {
            handleEditRequest();
        }
    }
    
    public void reloadImageView() throws MalformedURLException {
        /*if(!imageFile.getName().equals("DefaultStartSlide.png")){
            imageFile = new File(img.imagePath+img.imageName);
        }*/
        
        URL fileURL = imageFile.toURI().toURL();
        Image img1 = new Image(fileURL.toExternalForm());
        if(img1!=null)
            iv.setImage(img1);
    }
    
    public void handleSelectImageFile(){
        FileChooser imageFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File(PATH_IMAGES));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
            imageFile = file;
	}
    }

    public void handleEditRequest() {
        img.captions = tfCaption.getText();
        img.imageHeight = tfHeight.getText();
        img.imageWidth = tfWidth.getText();
        img.imageStatus = cb.getValue().toString();
        img.imageName = imageFile.getName();
        img.imagePath = imageFile.getPath().substring(0, imageFile.getPath().indexOf(img.imageName));
        img.imageID = tfID.getText();
        ui.updateToolbarControls(false);
    }

    public void initWindow() throws MalformedURLException {
        workspace = new VBox();
        workspace.setAlignment(Pos.CENTER);
        //ImageView 
        imageFile = new File(img.imagePath+img.imageName);
       
	//File file = new File(imagePath);
        iv = new ImageView();
        //File file = new File(imagePath);
        
        // GET AND SET THE IMAGE
        URL fileURL = imageFile.toURI().toURL();
        Image slideImage = new Image(fileURL.toExternalForm());
        
        // AND RESIZE IT
        double scaledWidth = 200;
        double perc = scaledWidth / slideImage.getWidth();
        double scaledHeight = slideImage.getHeight() * perc;
        iv.setFitWidth(scaledWidth);
        iv.setFitHeight(scaledHeight);
        
        Image img1 = new Image(fileURL.toExternalForm());
        iv.setImage(img1);
        
        Label lb = new Label("Enter the caption: ");
        
        workspace.getChildren().add(iv);
        
        GridPane gp= new GridPane();
        Label L1 = new Label("Enter the height: ");
        Label L2 = new Label("Enter the width: ");
        Label L3 = new Label("Float status");
        Label L4 = new Label("ID#: ");
        tfCaption = new TextField(img.captions);
        tfHeight = new TextField(img.imageHeight);
        tfWidth = new TextField(img.imageWidth);
        tfID = new TextField(img.imageID);        
        cb = new ChoiceBox(FXCollections.observableArrayList("none",
                "left",
                "right")
        );
        cb.getSelectionModel().select(img.imageStatus);
        
        gp.add(lb, 0, 0);
        gp.add(L1, 0, 1);
        gp.add(L2,0,2);
        gp.add(L3, 0, 3);
        gp.add(L4, 0, 4);
        gp.add(tfCaption, 1, 0);
        gp.add(tfHeight, 1, 1);
        gp.add(tfWidth, 1, 2);
        gp.add(cb, 1, 3);
        gp.add(tfID, 1, 4);
        workspace.getChildren().add(gp);
        
        this.getDialogPane().setContent(workspace);
        
    }
    
}
