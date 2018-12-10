package teg.controller;

//import static ssm.LanguagePropertyType.DEFAULT_IMAGE_CAPTION;
import java.net.MalformedURLException;
import javafx.collections.ObservableList;
import static teg.StartupConstants.PATH_IMAGES;
import static teg.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import teg.model.HyperLink;
import teg.model.Slide;
import teg.view.SlideEditView;
import teg.view.SlideShowEditStage;

/**
 * This controller provides responses for the slideshow edit toolbar,
 * which allows the user to add, remove, and reorder slides.
 * 
 * @author McKilla Gorilla & Tianci He
 */
public class SlideShowEditController {
    // APP UI
    private SlideShowEditStage ui;
    public boolean saved;
    
    /**
     * This constructor keeps the UI for later.
     */
    public SlideShowEditController(SlideShowEditStage initUI) {
	ui = initUI;
        saved = true;
    }
    
    /**
     * Provides a response for when the user wishes to add a new
     * slide to the slide show.
     */
    public void processAddSlideRequest() throws MalformedURLException {
	ObservableList<Slide> slides = ui.getSlides();
	//PropertiesManager props = PropertiesManager.getPropertiesManager();
        Slide s = new Slide("DefaultStartSlide.png",PATH_IMAGES,"nono");
	slides.add(s);
        ui.selectedSlide = s;
        saved = false;
        ui.reloadSlideShowPane(slides);
        ui.updateToolbarControls(saved);
    }

    public void processRemoveSlideRequest() throws MalformedURLException {
        ObservableList<Slide> slides = ui.getSlides();
        slides.remove(ui.getSelectedSlide());
        ui.reloadSlideShowPane(slides);
        ui.selectedSlide = null;
        saved = false;
        //ui.fileController.markFileAsNotSaved();
        //ui.updateToolbarControls(ui.fileController.isSaved());
        //ui.updateToolbarControls(false);
        ui.reloadSlideShowPane(slides);
        ui.updateToolbarControls(saved);
    }

    public void processMoveUpRequest() throws MalformedURLException {
        ObservableList<Slide> slides = ui.getSlides();// slideShow = ui.getSlides();
        Slide slideToMoveUp = ui.getSelectedSlide();
        int position = slides.indexOf(slideToMoveUp);
        if(position!=0){
            slides.remove(slideToMoveUp);
            slides.add(position-1, slideToMoveUp);
            ui.reloadSlideShowPane(slides);
            //ui.fileController.markFileAsNotSaved();
            //ui.updateToolbarControls(ui.fileController.isSaved());
            saved = false;
            ui.updateToolbarControls(saved);
        }
    }
    public void processUpdateConptionRequest() throws MalformedURLException{
        ObservableList<Slide> slides = ui.getSlides();//SlideShowModel slideShow = ui.getSlideShow();
        SlideEditView slideE = new SlideEditView(ui.getSelectedSlide(),ui);
        ui.getSelectedSlide().setImageCaptions(slideE.captionTextField.getText());
        //ui.fileController.markFileAsNotSaved();
        saved = false;
        ui.updateToolbarControls(saved);
        //ui.updateToolbarControls(false);
    }
    public void processMoveDownRequest() throws MalformedURLException {
        ObservableList<Slide> slides = ui.getSlides();//SlideShowModel slideShow = ui.getSlideShow();
        Slide slideToMoveDown = ui.getSelectedSlide();
        int position = slides.indexOf(slideToMoveDown);
        if(position!=slides.size()-1){
            slides.remove(slideToMoveDown);
            slides.add(position+1, slideToMoveDown);
            ui.reloadSlideShowPane(slides);
            saved  = false;
            //ui.fileController.markFileAsNotSaved();
            //ui.updateToolbarControls(ui.fileController.isSaved());
            ui.updateToolbarControls(saved);
        }
    }
    
    public void processSaveRequest(){
        ui.ui.getEPModel().selectedSite.getSelectedContent().component.slides = ui.getSlides();
        saved = true;
        ui.updateToolbarControls(saved);
    }
    
    public void processExit(){
        if(saved = false){
            processSaveRequest();
        }
        else
            ui.close();
    }
}
