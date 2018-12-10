/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import teg.view.EPortfolioGeneratorView;

/**
 *
 * @author HTC
 */
public class Site{
    String title = "";
    Banner banner;
    ObservableList<Content> contents;
    Content selectedContent;
    String footer = "";
    Font font;
    String layout = "";
    String colorTheme= "";
    EPortfolioGeneratorView ui;

    public Site(String initTitle,EPortfolioGeneratorView initUI) {
         ui = initUI;
        title = initTitle;
        this.contents = FXCollections.observableArrayList();
        this.banner = new Banner("","",ui.getEPModel().getStudentName());
        this.font = new Font();
       
    }

    public Site(String initTitle, String initFooter, String initLayout, String initColor,EPortfolioGeneratorView initUI) {
        ui = initUI;
        title = initTitle;
        this.layout = initLayout;
        this.colorTheme = initColor;
        this.footer = initFooter;
        this.banner = new Banner("","",ui.getEPModel().getStudentName());
        this.contents = FXCollections.observableArrayList();
        this.font = new Font();
        
    }
    
    public Site(String initTitle, String initLayout, Font fontToSet,  String initColor,EPortfolioGeneratorView initUI ){
        ui = initUI;
        title = initTitle;
        this.layout = initLayout;
        this.colorTheme = initColor;
        this.banner = new Banner("","",ui.getEPModel().getStudentName());
        this.contents = FXCollections.observableArrayList();
        this.font = fontToSet;
        
    }
    
    public String getTitle(){
        return title;
    }
    
    public Banner getBanner(){
        return this.banner;
    }
    
    public Content getSelectedContent(){
        return this.selectedContent;
    }
    //public String getBannerImagePath(){
      //  return this.bannerImagePath;
    //}
    
    
    public String getFooter(){
        return this.footer;
    }
    
    public ObservableList<Content> getContents(){
        return this.contents;
    }
    
    public String getLayout(){
        return this.layout;
    }
    
    public String getColorTheme(){
        return this.colorTheme;
    }
    
    public Font getFont(){
        return this.font;
    }
    
    public void setTitle(String initTitle){
        this.title = initTitle;
    }
    
    public void setSelectedContent(Content c){
        this.selectedContent = c;
    }
    
    
    public void setBanner(String initName, String initPath){
        //initText = this.title + "'s eportfolio ";
        banner = new Banner(initName, initPath, ui.getEPModel().getStudentName());
    }
    
    
    public void addContent(String initStyle){
        Content contentToAdd = new Content(initStyle);
        this.contents.add(contentToAdd);
        this.setSelectedContent(contentToAdd);
    }
    
    public void setFont(String initName, String initLink, String initSize){
        font = new Font(initName, initLink, initSize);
        
    }
    
    public void setFooter(String initFooter){
        this.footer = initFooter;
    }
    
    public void setLayout(String initLayout){
        this.layout = initLayout;
    }

    public void setColor(String initColor) {
        this.colorTheme = initColor;
    
    }
    
    
}
