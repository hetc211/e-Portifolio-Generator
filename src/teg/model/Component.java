/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teg.StartupConstants.*;
import static teg.StartupConstants.PATH_IMAGES;
import static teg.StartupConstants.PATH_VIDEOES;

/**
 *
 * @author HTC
 */
public class Component {
    public Paragraph paragraph;
    public ObservableList<TList> lists;
    public Hd header;
    public Img image;
    public Vd video;
    public ObservableList<Slide> slides;
    public TList selectedItem;
    public Slide selectedSlide;
    
    public Component(){
        paragraph = new Paragraph("");
        lists = FXCollections.observableArrayList();
        slides = FXCollections.observableArrayList();
        this.header = new Hd("");
        this.image = new Img();
        this.video = new Vd();
    }
    
    public Component(String initStyle){
        if(initStyle.equals("paragraph")){
            this.paragraph = new Paragraph("none");
            lists = FXCollections.observableArrayList();
            slides = FXCollections.observableArrayList();
            this.header = new Hd("");
            this.image = new Img();
            this.video = new Vd();
        }
        if(initStyle.equals("lists")){
            paragraph = new Paragraph("");
            lists = FXCollections.observableArrayList();
            slides = FXCollections.observableArrayList();
            this.header = new Hd("");
            this.image = new Img();
            this.video = new Vd();
        }
        if(initStyle.equals("slideShow")){
            paragraph = new Paragraph("");
            lists = FXCollections.observableArrayList();
            slides = FXCollections.observableArrayList();
            this.header = new Hd("");
            this.image = new Img();
            this.video = new Vd();
        }
        if(initStyle.equals("image")){
            this.image = new Img("DefaultStartSlide.png",PATH_IMAGES,"none","400","400","none","id");
            paragraph = new Paragraph("");
            lists = FXCollections.observableArrayList();
            slides = FXCollections.observableArrayList();
            this.header = new Hd("");
            //this.image = new Img();
            this.video = new Vd();
        }
        if(initStyle.equals("video")){
            this.video = new Vd("3.mp4",PATH_VIDEOES,"none","400","400");
            paragraph = new Paragraph("");
            lists = FXCollections.observableArrayList();
            slides = FXCollections.observableArrayList();
            this.header = new Hd("");
            this.image = new Img();
            //this.video = new Vd();
        }
        if(initStyle.equals("header")){
            this.header = new Hd("none");
            paragraph = new Paragraph("");
            lists = FXCollections.observableArrayList();
            slides = FXCollections.observableArrayList();
            //this.header = new Hd("");
            this.image = new Img();
            this.video = new Vd();
        }
        
    }
    
    
    public Paragraph getParagraph(){
        return this.paragraph;
    }
    
    public ObservableList<TList> getLists(){
        return this.lists;
    }
    
    public Hd getHeader(){
        return this.header;
    }
    
    
    public ObservableList<Slide> getSlides(){
        return this.slides;
    }
    
    public void setSelectedItem(TList list){
        this.selectedItem = list;
    }
    
    public void setSelectedSlide(Slide slide){
        this.selectedSlide = slide;
    }
    
    public void setParagraph(String initText){
        this.paragraph = new Paragraph(initText);
    }
    
    public void setHeader(String initText){
        this.header = new Hd(initText);
    }
    
    public void setImage(String initName, String initPath, String initStatus, String initHeight, String initWidth, String initCaption, String initID){
        image = new Img(initName, initPath, initStatus, initHeight, initWidth, initCaption, initID);
    } 
    
    public void setVideo(String initName, String initPath, String initCaption, String initHeight, String initWidth){
        video = new Vd(initName, initPath, initCaption, initHeight, initWidth);
    }
    
    public void addSlide(   String initImageFileName,
                            String initImagePath, String initCaptions) {
        Slide slideToAdd = new Slide(initImageFileName, initImagePath, initCaptions);
        this.slides.add(slideToAdd);
    }
    
    public void addList(String initItem){
        TList temp = new TList(initItem);
        this.lists.add(temp);
    }
    
}
