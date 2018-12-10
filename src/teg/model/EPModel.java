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
public class EPModel {
    EPortfolioGeneratorView ui;
    String studentName;// = SLIDE_SHOW_TITLE;
    ObservableList<Site> sites;
    public Site selectedSite;
    
    public EPModel(EPortfolioGeneratorView initUI) {
        ui = initUI;
        sites = FXCollections.observableArrayList();
        reset();
    }
    
    public boolean isSiteSelected() {
	return selectedSite != null;
    }
    
    public ObservableList<Site> getSites() {
	return sites;
    }
    
    public Site getSelectedSlide() {
	return selectedSite;
    }

    public String getStudentName() { 
	return this.studentName; 
    }
    
    // MUTATOR METHODS
    public void setSelectedSite(Site initSelectedSite) {
	selectedSite = initSelectedSite;
    }
    
    public void setStudentName(String initSN) { 
	this.studentName = initSN; 
    }
    
    public void reset() {
	sites.clear();
        this.studentName = "newePortfolio";
    }
    
    public void addSite(String initTitle, String initFooter, String initLayout, String initColor) {
	Site siteToAdd = new Site(initTitle, initFooter, initLayout, initColor,ui);
	sites.add(siteToAdd);
	selectedSite = siteToAdd;
    	ui.reloadListOfSitesPane(this);
	selectedSite = siteToAdd;
    }
    
    public void addSiteWithFont(String initTitle, String initLayout, Font fontToSet,  String initColor,EPortfolioGeneratorView initUI){
        ui = initUI;
        Site siteToAdd = new Site(initTitle, initLayout, fontToSet, initColor,ui);
        sites.add(siteToAdd);
	selectedSite = siteToAdd;
    	ui.reloadListOfSitesPane(this);
	selectedSite = siteToAdd;
    }
    
}
