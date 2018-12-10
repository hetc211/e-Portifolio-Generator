package teg.view;

import teg.controller.ImageSelectionController;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static teg.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static teg.StartupConstants.CSS_CLASS_SLIDE_SHOW_EDIT_VBOX;
import static teg.StartupConstants.PATH_IMAGES;
import teg.model.Slide;

/**
 * This UI component has the controls for editing a single slide
 * in a slide show, including controls for selected the slide image
 * and changing its caption.
 * 
 * @author McKilla Gorilla & Tianci He
 */
public class SlideEditView extends HBox {
    // SLIDE THIS COMPONENT EDITS
    Slide slide;
    
    //SlideShowModel model;
    public SlideShowEditStage ui;
    // DISPLAYS THE IMAGE FOR THIS SLIDE
    ImageView imageSelectionView;
    
    // CONTROLS FOR EDITING THE CAPTION
    VBox captionVBox;
    Label captionLabel;
    public TextField captionTextField;
    Button captionOkBT;
    // PROVIDES RESPONSES FOR IMAGE SELECTION
    ImageSelectionController imageController;
    
    /**
     * THis constructor initializes the full UI for this component, using
     * the initSlide data for initializing values./
     * 
     * @param initSlide The slide to be edited by this component.
     */
    public SlideEditView(Slide initSlide, SlideShowEditStage initUI) throws MalformedURLException {
	// FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
	/*
        this.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
	// KEEP THE SLIDE FOR LATER
	
	// MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE
	ui = initUI;
	updateSlideImage();
        
	// SETUP THE CAPTION CONTROLS
	captionVBox = new VBox();
        captionOkBT = new Button("OK");
	captionLabel = new Label("Caption: ");
	captionTextField = new TextField();
        
        captionVBox.getStyleClass().add("-fx-background-color: yellow;");
        captionVBox.getChildren().add(captionLabel);
	captionVBox.getChildren().add(captionTextField);
        captionVBox.getChildren().add(captionOkBT);
        
	// LAY EVERYTHING OUT INSIDE THIS COMPONENT
	this.getChildren().add(imageSelectionView);
	this.getChildren().add(captionVBox);*/
        // FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
        this.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
        // KEEP THE SLIDE FOR LATER
        slide = initSlide;
        ui = initUI;
        // MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE
        imageSelectionView = new ImageView();
        updateSlideImage();

        // SETUP THE CAPTION CONTROLS
        captionVBox = new VBox();
        captionOkBT = new Button("OK");
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
        captionLabel = new Label("Captions: ");
        captionTextField = new TextField(slide.getImageCaptions());
        //captionTextField.setText(slide.getImageCaptions());
        captionVBox.getChildren().add(captionLabel);
        captionVBox.getChildren().add(captionTextField);
        captionVBox.getChildren().add(captionOkBT);

        captionOkBT.setOnAction(e -> {
            this.processUpdateCoptionRequest();

        });
        // LAY EVERYTHING OUT INSIDE THIS COMPONENT
        getChildren().add(imageSelectionView);
        getChildren().add(captionVBox);

        // SETUP THE EVENT HANDLERS
        imageController = new ImageSelectionController();
        imageSelectionView.setOnMousePressed(e -> {
            try {
                //Image img = new Image(slide.getImagePath()+"/"+slide.getImageFileName());
                imageController.processSelectImage(slide, this);
            } catch (MalformedURLException ex) {
                Logger.getLogger(SlideEditView.class.getName()).log(Level.SEVERE, null, ex);
            }
            //ui.fileController.markFileAsNotSaved();
            ui.updateToolbarControls(false);
            //ui.updateToolbarControls(false);
        });

        this.setOnMousePressed(e -> {
            try {
                this.processHighLight();
            } catch (MalformedURLException ex) {
                Logger.getLogger(SlideEditView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    /**
     * This function gets the image for the slide and uses it to
     * update the image displayed.
     */
    public void updateSlideImage() throws MalformedURLException {
        
	/*imageSelectionView = new ImageView();
        String imagePath = PATH_IMAGES +"DefaultStartSlide.png";
	File file = new File(imagePath);

        // GET AND SET THE IMAGE
        URL fileURL = file.toURI().toURL();
        Image slideImage = new Image(fileURL.toExternalForm());
        
        // AND RESIZE IT
        double scaledWidth = 200;
        double perc = scaledWidth / slideImage.getWidth();
        double scaledHeight = slideImage.getHeight() * perc;
        imageSelectionView.setFitWidth(scaledWidth);
        imageSelectionView.setFitHeight(scaledHeight);
        
        Image img = new Image(fileURL.toExternalForm());
        imageSelectionView.setImage(img);*/
        String imagePath = slide.getImagePath() + "/" + slide.getImageFileName();
	File file = new File(imagePath);
	try {
	    // GET AND SET THE IMAGE
	    URL fileURL = file.toURI().toURL();
	    Image slideImage = new Image(fileURL.toExternalForm());
	    imageSelectionView.setImage(slideImage);
	    
	    // AND RESIZE IT
	    double scaledWidth = 200;
	    double perc = scaledWidth / slideImage.getWidth();
	    double scaledHeight = slideImage.getHeight() * perc;
	    imageSelectionView.setFitWidth(scaledWidth);
	    imageSelectionView.setFitHeight(scaledHeight);
	} catch (Exception e) {
	    // @todo - use Error handler to respond to missing image
            ui.ui.errorHandler.processError("Failed to load image!");
	}
        
    }

    public void processUpdateCoptionRequest() {
        slide.setImageCaptions(captionTextField.getText());
        ui.updateToolbarControls(false);
    }

    public void processHighLight() throws MalformedURLException {
        ui.selectedSlide = slide;
        ui.updateToolbarControls(ui.editController.saved);
        ui.reloadSlideShowPane(ui.slides);
    }
    
}