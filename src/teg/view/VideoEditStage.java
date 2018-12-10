/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.io.File;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import static teg.StartupConstants.PATH_IMAGES;
import static teg.StartupConstants.PATH_VIDEOES;
import teg.model.Vd;

/**
 *
 * @author HTC
 */
public class VideoEditStage extends Dialog<ButtonType>{
    Button bt;// = new Button("Select a video");
    Label lb;
    TextField captionTF;
    TextField heightTF;
    TextField widthTF;
    EPortfolioGeneratorView ui;
    File videoFile;
    Vd video;
    GridPane gp;
    private Label L3;
    private Label L2;
    private Label L1;
    
    public VideoEditStage(EPortfolioGeneratorView initUI){
        ui = initUI;
        
        initWindow();
        /*this.setTitle("Edit Video");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        bt = new Button("Select a video");*/
        
        bt.setOnAction(e->{
            handleSelectVideoRequest();
        });
        /*
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        Label L1 =new Label("Enter the caption: ");
        Label L2 = new Label("Enter the height: ");
        Label L3 = new Label("Enter the width: ");
        
        captionTF = new TextField();
        heightTF = new TextField();
        widthTF = new TextField();
        
        lb = new Label("3.mp4");
        gp.add(bt, 0, 0);
        gp.add(L1, 0, 1);
        gp.add(L2, 0, 2);
        gp.add(L3, 0, 3);
        gp.add(lb, 1, 0);
        gp.add(captionTF, 1, 1);
        gp.add(heightTF, 1, 2);
        gp.add(widthTF, 1, 3);
        
        this.getDialogPane().setContent(gp);
        */        
        Optional<ButtonType> result = this.showAndWait();
        if (result.isPresent()) {
            handleEditRequest();
        }
    }

    private void handleSelectVideoRequest() {
        FileChooser videoFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
	videoFileChooser.setInitialDirectory(new File(PATH_VIDEOES));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter mp4Filter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");
	videoFileChooser.getExtensionFilters().addAll(mp4Filter);
	
	// LET'S OPEN THE FILE CHOOSER
	videoFile = videoFileChooser.showOpenDialog(null);
        lb = new Label(videoFile.getName());
        gp.getChildren().clear();
        gp.add(bt, 0, 0);
        gp.add(L1, 0, 1);
        gp.add(L2, 0, 2);
        gp.add(L3, 0, 3);
        gp.add(lb, 1, 0);
        gp.add(captionTF, 1, 1);
        gp.add(heightTF, 1, 2);
        gp.add(widthTF, 1, 3);
        
    }

    public void initWindow() {
        this.setTitle("Edit Video");
        this.setHeaderText(null);
        this.setGraphic(null);
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        bt = new Button("Select a video");
        video = ui.getEPModel().selectedSite.getSelectedContent().component.video;
        videoFile = new File(video.videoPath + video.videoName);
       
        captionTF = new TextField(video.videoCaptions);
        heightTF = new TextField(video.videoHeight);
        widthTF = new TextField(video.videoWidth);
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        L1 =new Label("Enter the caption: ");
        L2 = new Label("Enter the height: ");
        L3 = new Label("Enter the width: ");
        
        captionTF = new TextField(video.videoCaptions);
        heightTF = new TextField(video.videoHeight);
        widthTF = new TextField(video.videoWidth);
        
        lb = new Label(videoFile.getName());
        gp.add(bt, 0, 0);
        gp.add(L1, 0, 1);
        gp.add(L2, 0, 2);
        gp.add(L3, 0, 3);
        gp.add(lb, 1, 0);
        gp.add(captionTF, 1, 1);
        gp.add(heightTF, 1, 2);
        gp.add(widthTF, 1, 3);
        
        this.getDialogPane().setContent(gp);
        
    }

    public void handleEditRequest() {
        video.videoCaptions = captionTF.getText();
        video.videoHeight = heightTF.getText();
        video.videoWidth = widthTF.getText();
        video.videoName = videoFile.getName();
        video.videoPath = videoFile.getPath().substring(0, videoFile.getPath().indexOf(video.videoName));
    }
}
