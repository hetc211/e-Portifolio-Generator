/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import teg.error.ErrorHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import static teg.StartupConstants.*;
import static teg.StartupConstants.STYLE_SHEET_UI;
import teg.controller.ComponentEditController;
import teg.controller.FileController;
import teg.controller.SiteEditController;
import teg.file.TEGFileMannager;
import teg.model.EPModel;
import teg.model.Site;
//for test
import teg.model.Component;
import teg.model.Content;
import teg.model.Font;
import teg.model.HyperLink;
import teg.view.TextParagraphEditStage;


/**
 *
 * @author HTC
 */
public class EPortfolioGeneratorView {
    Stage primaryStage;
    String appTitle;
    ErrorHandler errorHandler;
    BorderPane tegPane;
    Scene primaryScene;
    FlowPane fileToolbarPane;
    Button newEPButton;
    Button loadEPButton;
    public Button saveEPButton;
    Button saveAsEPButton;
    Button exitButton;
    Button exportEPButton;
    
    HBox workspace;
    VBox siteToolbar;
    
    Button addPageButton;
    Button removePageButton;
    VBox listSitePane;
    ScrollPane listSiteScrollPane;
    
    VBox innerWorkspace;
    FlowPane modePane;
    Button viewModeButton;
    Button editModeButton;
    BorderPane siteWorkspace;
    
    HBox siteEditorToolBar;
    
    VBox siteEditWorkspace;
    ScrollPane siteEditorScrollPane;
    VBox siteComponentPane;
    
    FileController fileController;
    
    BorderPane showDetailPane;
    FlowPane editSiteToolbar;
    Button editSiteTitleBT;
    Button editStudentNameBT;
    Button editBannerImageBT;
    Button enterFooterBT;
    Button layoutBT;
    Button colorModeBT;
    Button siteFontBT;
    VBox componentToolPane;
    Button addComponentButton;
    Button removeComponentButton;
    Button editComponentButton;
    
    //for test only
    String currentType;
    VBox contentPane;
    
    EPModel ep;
    TEGFileMannager ePIO;
    SiteEditController siteEditController;
    
    public ObservableList<Font> fonts;
    public VBox outerVBox;
    
    public EPortfolioGeneratorView(){
        // MAKE THE DATA MANAGING MODEL
	ep = new EPModel(this);
        ePIO = new TEGFileMannager();
        fonts = FXCollections.observableArrayList();
	// WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
	errorHandler = new ErrorHandler(this);
    }
    public Stage getWindow() {
	return primaryStage;
    }
    
    public EPModel getEPModel(){
        return ep;
    }

    public ErrorHandler getErrorHandler() {
	return errorHandler;
    }
    
    public String getAppTitle(){
        return appTitle;
    }
    
    public void startUI(Stage initPrimaryStage, String windowTitle) {
        initFileToolbar();
        
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
	// TO THE WINDOW YET
	initWorkspace();

	// NOW SETUP THE EVENT HANDLERS
	initEventHandlers();

        initFont();
	// AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
	// KEEP THE WINDOW FOR LATER
	primaryStage = initPrimaryStage;
	initWindow(windowTitle);
        appTitle = windowTitle;
        
        viewModeButton.setOnAction(e -> {
	    handleViewRequest();
	});
        editModeButton.setOnAction(e -> {
	    handleEditRequest();
	});
        
    }

    private void initFileToolbar() {
        fileToolbarPane = new FlowPane();
        fileToolbarPane.getStyleClass().add(CSS_CLASS_FILE_CONTROL_FLOWPANE);
        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
	// START AS ENABLED (false), WHILE OTHERS DISABLED (true)
	
	newEPButton = initChildButton(fileToolbarPane, ICON_NEW_EP,	TOOLTIP_NEW_EP,	    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	loadEPButton = initChildButton(fileToolbarPane, ICON_LOAD_EP,	TOOLTIP_LOAD_EP,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	saveEPButton = initChildButton(fileToolbarPane, ICON_SAVE_EP,	TOOLTIP_SAVE_EP,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	saveAsEPButton = initChildButton(fileToolbarPane, ICON_SAVEAS_EP,	TOOLTIP_SAVEAS_EP,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        exportEPButton = initChildButton(fileToolbarPane, ICON_EXPORT_EP,	TOOLTIP_EXPORT_EP,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    
    }

    public void initWorkspace() {
        // FIRST THE WORKSPACE ITSELF, WHICH WILL CONTAIN TWO REGIONS
        workspace = new HBox();  
	workspace.getStyleClass().add(CSS_CLASS_WORKSPACE);
        // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
        siteToolbar = new VBox();
        siteToolbar.getStyleClass().add(CSS_CLASS_SITE_CONTROL_VBOX);
	addPageButton = this.initChildButton(siteToolbar,	ICON_ADD_PAGE,	   TOOLTIP_ADD_PAGE,	  CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
	removePageButton = this.initChildButton(siteToolbar,	ICON_REMOVE_PAGE,  TOOLTIP_REMOVE_PAGE,   CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	siteToolbar.setAlignment(Pos.CENTER);
        
        listSitePane = new VBox();
        listSitePane.getStyleClass().add(CSS_CLASS_LIST_SITE_VBOX);
        listSiteScrollPane = new ScrollPane(listSitePane);
        
        siteWorkspace = new BorderPane();
        siteWorkspace.setPrefSize(800, 800);
        siteWorkspace.getStyleClass().add(CSS_CLASS_SITE_WORKSPACE);
        
        
        modePane = new FlowPane();
        modePane.getStyleClass().add(CSS_CLASS_MODE_HBOX);
        siteWorkspace.setTop(modePane);
       
        showDetailPane = new BorderPane();
        siteWorkspace.setCenter(showDetailPane);
        
        editModeButton = this.initChildButton(modePane,	ICON_EDIT_MODE,	   TOOLTIP_EDIT_MODE,	  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        viewModeButton = this.initChildButton(modePane,	ICON_VIEW_MODE,	   TOOLTIP_VIEW_MODE,	  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
	
        innerWorkspace = new VBox();
        workspace.getChildren().add(siteToolbar);
        workspace.getChildren().add(listSiteScrollPane);
        workspace.getChildren().add(innerWorkspace);
        
        
    }

    private void initEventHandlers() {
        
        fileController = new FileController(this, ePIO);
        newEPButton.setOnAction(e -> {
	    fileController.handleNewEPRequest();
	});
        loadEPButton.setOnAction(e -> {
	    fileController.handleLoadEPRequest();
	});
        saveEPButton.setOnAction(e -> {
	    fileController.handleSaveEPRequest();
	});
        saveAsEPButton.setOnAction(e -> {
            try {
                fileController.handleSaveAsEPRequest();
            } catch (IOException ex) {
                Logger.getLogger(EPortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
            }
	});
        exportEPButton.setOnAction(e -> {
	    fileController.handleExportEPRequest();
	});
        exitButton.setOnAction(e -> {
	    fileController.handleExitRequest();
	});
        
        siteEditController = new SiteEditController(this);
        addPageButton.setOnAction(e->{
            siteEditController.handleAddSiteRequest();
        });
        removePageButton.setOnAction(e->{
            siteEditController.handleRemoveSiteRequest();
        });
        
        
    }

    private void initWindow(String windowTitle) {
        primaryStage.setTitle(windowTitle);

	// GET THE SIZE OF THE SCREEN
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	primaryStage.setX(bounds.getMinX());
	primaryStage.setY(bounds.getMinY());
	primaryStage.setWidth(bounds.getWidth());
	primaryStage.setHeight(bounds.getHeight());
        
        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
	tegPane = new BorderPane();
        tegPane.setPrefSize(500,400);
	tegPane.setTop(fileToolbarPane);	
	primaryScene = new Scene(tegPane);
	
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
	// WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
	primaryScene.getStylesheets().add(STYLE_SHEET_UI);
	primaryStage.setScene(primaryScene);
        
        String iconPath = "file:" + PATH_ICONS + "ApplicationIcon.png";
	primaryStage.getIcons().add(new Image(iconPath));
        
	primaryStage.show();
    }
    
    public Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    String tooltip, 
	    String cssClass,
	    boolean disabled) {
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	Button button = new Button();
        button.getStyleClass().add(cssClass);
	button.getStyleClass().add(CSS_CLASS_TOOLTIP_SETTING);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(tooltip);
	buttonTooltip.getStyleClass().add("/");
        button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
    
    
    public void reloadListOfSitesPane(EPModel EPToLoad) {
        listSitePane.getChildren().clear();
        for (Site site : EPToLoad.getSites()) {
            Button bt = new Button(site.getTitle());
            bt.setMaxWidth(800);
            bt.setAlignment(Pos.CENTER);
            if(site.equals(EPToLoad.selectedSite)){
                bt.setStyle("-fx-background-color: #0099cc;");
            }
            listSitePane.getChildren().add(bt);
            bt.setOnAction(e->{
                handleLoadSiteWorkspace(site);
            });
        }
        
    }
    

    //for test
    public void handleViewRequest() {
        showDetailPane.getChildren().clear();
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(browser);
        webEngine.load("http://www3.cs.stonybrook.edu/~cse219/schedule.html");
        showDetailPane.setCenter(scrollPane);
        
    }

    public void initEditSiteToolBar(){
        editSiteToolbar = new FlowPane();
        editSiteToolbar.setStyle("-fx-padding:10px;");
        editSiteToolbar.setPrefWidth(1600);
        editSiteToolbar.getStyleClass().add(CSS_CLASS_FILE_CONTROL_FLOWPANE);
        editSiteTitleBT = this.initChildButton(editSiteToolbar,     ICON_EDIT_SITE_TITLE,       TOOLTIP_EDIT_SITE_TITLE,  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        editStudentNameBT = this.initChildButton(editSiteToolbar,   ICON_EDIT_STUDENT_NAME,     TOOLTIP_EDIT_STUDENT_NAME,CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        
        editBannerImageBT = this.initChildButton(editSiteToolbar,   ICON_EDIT_BI,               TOOLTIP_EDIT_BI,	  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        updateEditBIBT();
        enterFooterBT = this.initChildButton(editSiteToolbar,       ICON_EDIT_FOOTER,           TOOLTIP_EDIT_FOOTER,	  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        layoutBT = this.initChildButton(editSiteToolbar,            ICON_EDIT_LAYOUT,           TOOLTIP_EDIT_LAYOUT,	  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        colorModeBT = this.initChildButton(editSiteToolbar,         ICON_EDIT_COLOR_MODE,	TOOLTIP_EDIT_COLOR_MODE,  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        siteFontBT =this.initChildButton(editSiteToolbar,           ICON_EDIT_SITE_FONT,        TOOLTIP_EDIT_SITE_FONT,	  CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        ESTBEventHandler();
    }
    
    public void ESTBEventHandler(){
        editSiteTitleBT.setOnAction(e->{
            this.handleEditSiteTitleRequest();
        });
        editStudentNameBT.setOnAction(e->{
            this.handleEditStudentNameRequest();
        });
        editBannerImageBT.setOnAction(e->{
            this.handleEditBannerImageRequest();
        });
        enterFooterBT.setOnAction(e->{
            this.handleEditFooterRequest();
        });
        layoutBT.setOnAction(e->{
            this.handleSelectLayoutRequest();
        });
        colorModeBT.setOnAction(e->{
            this.handleSelectColorThemeRequest();
        });
        siteFontBT.setOnAction(e->{
            this.handleSelectSiteFontRequest();
        });
        
        
    }
    
    
    public void handleEditRequest() {
        showDetailPane.getChildren().clear();
        initEditSiteToolBar();
        showDetailPane.setTop(editSiteToolbar);
        initComponentToolbar();
        showDetailPane.setLeft(componentToolPane);
        contentPane = new VBox();
        loadContents();
        showDetailPane.setCenter(contentPane);
    }
    
    public void initComponentToolbar(){
        componentToolPane = new VBox();
        componentToolPane.getStyleClass().add(CSS_CLASS_COMPONENT_CONTROL_VBOX);
        componentToolPane.setAlignment(Pos.CENTER);
        addComponentButton = this.initChildButton(componentToolPane,	ICON_ADD_PAGE,      TOOLTIP_ADD_COMPONENT,      CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
	removeComponentButton = this.initChildButton(componentToolPane,	ICON_REMOVE_PAGE,   TOOLTIP_REMOVE_COMPONENT,   CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
        updateRCBT();
        editComponentButton = this.initChildButton(componentToolPane,   ICON_EDIT_MODE,     TOOLTIP_EDIT_COMPONENT,     CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        
        initCTEventhandler();
    }

    public void handleEditSiteTitleRequest() {
        TextInputDialog dialog = new TextInputDialog(ep.selectedSite.getTitle());
        dialog.setTitle("Edit site title");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Please enter the title:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            ep.selectedSite.setTitle(result.get());
            this.reloadListOfSitesPane(ep);
            this.updateToolbarControls(false);
        }
        
        
    }

    public void handleEditStudentNameRequest() {
        TextInputDialog dialog = new TextInputDialog(ep.getStudentName());
        dialog.setTitle("Edit Student name");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Please enter the student name:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            ep.setStudentName(result.get());
            this.updateTitle();
            this.updateToolbarControls(false);
        }
    
    }

    public void handleEditBannerImageRequest() {
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
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
            ep.selectedSite.setBanner(fileName, path);//, ep.getStudentName());
            this.updateToolbarControls(false);
	}
        
    }

    public void handleEditFooterRequest() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Footer");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        VBox vbox = new VBox();
        vbox.setSpacing(15);
        Label label = new Label("Enter footer:");
        TextArea ta = new TextArea(ep.selectedSite.getFooter());
        ta.setPrefSize(150, 50);
        vbox.getChildren().add(label);
        vbox.getChildren().add(ta);
        dialog.getDialogPane().setContent(vbox);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()){
            ep.selectedSite.setFooter(ta.getText());
            this.updateToolbarControls(false);
        }
       
    }

    public void handleSelectLayoutRequest() {
        List<String> choices = new ArrayList<>();
        choices.add("Layout_1");
        choices.add("Layout_2");
        choices.add("Layout_3");
        choices.add("Layout_4");
        choices.add("Layout_5");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(ep.selectedSite.getLayout(), choices);
        dialog.setTitle("Selecte Layout");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Choose the layout:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ep.selectedSite.setLayout(result.get());
            //initEditSiteToolBar();
            this.updateEditBIBT();
            this.updateToolbarControls(false);
        }
    }

    public void handleSelectColorThemeRequest() {
        List<String> choices = new ArrayList<>();
        choices.add("Red");
        choices.add("Yellow");
        choices.add("Blue");
        choices.add("Green");
        choices.add("White");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(ep.selectedSite.getColorTheme(), choices);
        dialog.setTitle("Selecte Color Theme");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Choose the color theme:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ep.selectedSite.setColor(result.get());
            this.updateToolbarControls(false);
        }
    
    }

    public void handleSelectSiteFontRequest() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Font");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane gp = new GridPane();
        gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5.5);
        gp.setVgap(5.5);
        
        ObservableList<String> fontList = FXCollections.observableArrayList();
        for(Font name : this.fonts){
            fontList.add(name.name);
        }
        ChoiceBox fontCB = new ChoiceBox(FXCollections.observableArrayList(fontList));
        fontCB.getSelectionModel().select(ep.selectedSite.getFont().name);
        Label L1 = new Label("Select a font: ");
        TextField fontSizeTF = new TextField(ep.selectedSite.getFont().size);
        Label L2 = new Label("Enter the font size: ");
        
        gp.add(L1, 0, 0);
        gp.add(L2, 0, 1);
        gp.add(fontCB, 1, 0);
        gp.add(fontSizeTF, 1, 1);
        dialog.getDialogPane().setContent(gp);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()){
            Font fToAdd = siteEditController.findSelectedFont(fontCB.getValue().toString());
            ep.selectedSite.setFont(fToAdd.name, fToAdd.link, fontSizeTF.getText());
            this.updateToolbarControls(false);
        }
        
    }
    
    public void reLoadContents(){
        contentPane.getChildren().clear();
        
        for(Content content :ep.selectedSite.getContents()){
            Button bt = new Button("This is a "+content.style);//paragraph");
            if(content.equals(ep.selectedSite.getSelectedContent())){
                bt.setStyle("-fx-background-color:#0099cc");
            }
            bt.setMaxWidth(800);
            bt.setAlignment(Pos.CENTER);
            bt.setOnAction(e->{
                ep.selectedSite.setSelectedContent(content);
                reLoadContents();
            });
            contentPane.getChildren().add(bt);
        }
        
        contentPane.setSpacing(10);
        //contentPane.getChildren().addAll();
        
    }
    
    public void updateEditCompBT(){
        if (ep.getSelectedSlide().getContents().size()==0){
            editComponentButton.setDisable(true);
        }
        else{
            editComponentButton.setDisable(false);
        }
        if(this.getEPModel().selectedSite.getSelectedContent()!=null){
            editComponentButton.setDisable(false);
        }
        else{
            editComponentButton.setDisable(true);
        }
        
    }
    
    public void handlEditComponentRequest() throws MalformedURLException, CloneNotSupportedException {
        if(ep.selectedSite.getSelectedContent().style.equals("paragraph")){
            //TextParagraphEditStage stage = 
            new TextParagraphEditStage(this,siteEditController);
        }
        if(ep.selectedSite.getSelectedContent().style.equals("lists")){
            //TextListEditStage stage= 
            new TextListEditStage(this,siteEditController);
        }
        if(ep.selectedSite.getSelectedContent().style.equals("header")){
            //TextHeaderEditStage stage = 
            new TextHeaderEditStage(this);
        }
        if(ep.selectedSite.getSelectedContent().style.equals("image")){
            ImgEditStage stage = new ImgEditStage(this);
            //stage.reloadImageView();
            //stage.showAndWait();
        }
        if(ep.selectedSite.getSelectedContent().style.equals("video")){
            VideoEditStage stage = new VideoEditStage(this);
            //stage.show();
        }
        if(ep.selectedSite.getSelectedContent().style.equals("slideShow")){
            SlideShowEditStage stage = new SlideShowEditStage(this);
            //stage.showAndWait();
        }    
    }

   public void updateToolbarControls(boolean saved) {
	// FIRST MAKE SURE THE WORKSPACE IS THERE
	tegPane.setCenter(workspace);
	//tegPane.setCenter(outerVBox);
	saveEPButton.setDisable(saved);
        saveAsEPButton.setDisable(false);
        for(Site site :ep.getSites()){
            if( !saved && site.getLayout()==null)
                exportEPButton.setDisable(true);
        }
	
    }
   
    public void updateSiteToolbarControls(){
        if(ep.getSites().size()!=0){
            removePageButton.setDisable(false);
        }
        else
            removePageButton.setDisable(true);
    }
   
    public void handleLoadSiteWorkspace(Site site) {
        initInnerWorkspace();
        ep.setSelectedSite(site);
        this.reloadListOfSitesPane(ep);
        innerWorkspace.getChildren().add(siteWorkspace);
        showDetailPane.getChildren().clear();
    }
    
    public void initInnerWorkspace(){
        innerWorkspace.getChildren().clear();
    }

    public void addWorkspace() {
        tegPane.setCenter(workspace);
    }

    public void updateTitle() {
        this.getWindow().setTitle(ep.getStudentName()+" -"+appTitle);
    }

    public void initFont() {
        fonts.clear();
        Font f0 = new Font("'Julius Sans One', sans-serif","https://fonts.googleapis.com/css?family=Julius+Sans+One");
        Font f1 = new Font("'Orbitron', sans-serif", "https://fonts.googleapis.com/css?family=Orbitron");
        Font f2 = new Font("Pacifico", "http://fonts.googleapis.com/css?family=Pacifico");
        Font f3 = new Font("'Lobster Two', cursive", "https://fonts.googleapis.com/css?family=Lobster+Two");
        Font f4 = new Font("'Poiret One', cursive", "https://fonts.googleapis.com/css?family=Poiret+One");
        fonts.addAll(f0,f1,f2,f3,f4);
    }

    public void updateEditBIBT() {
        if(ep.selectedSite.getLayout().equals("Layout_3")||ep.selectedSite.getLayout().equals("Layout_4")){
            editBannerImageBT.setDisable(true);
        }
        else
            editBannerImageBT.setDisable(false);
    }

    private void initCTEventhandler() {
        ComponentEditController componentEditController = new ComponentEditController(this);
        addComponentButton.setOnAction(e->{
            componentEditController.handleAddComponentRequest();
        });
        removeComponentButton.setOnAction(e->{
            componentEditController.handleRemoveComponentRequest();
        });
        editComponentButton.setOnAction(e->{
            try {
                try {
                    this.handlEditComponentRequest();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(EPortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(EPortfolioGeneratorView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    
    }

    public void updateRCBT() {
        if(ep.selectedSite.getContents().size()!=0){
            removeComponentButton.setDisable(false);
        }
        else
            removeComponentButton.setDisable(true);
    
    }

    public void reloadWorkspace() {
        workspace.getChildren().clear();
    }

    private void loadContents() {
        contentPane.getChildren().clear();
        
        for(Content content :ep.selectedSite.getContents()){
            Button bt = new Button("This is a "+content.style);//paragraph");
            
            bt.setMaxWidth(800);
            bt.setAlignment(Pos.CENTER);
            bt.setOnAction(e->{
                ep.selectedSite.setSelectedContent(content);
                this.updateEditCompBT();
                reLoadContents();
            });
            contentPane.getChildren().add(bt);
        }
        this.ep.selectedSite.setSelectedContent(null);
        this.updateEditCompBT();
        contentPane.setSpacing(10);
        //contentPane.getChildren().addAll();
        
    }

    

}
