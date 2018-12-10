/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author HTC
 */
public class Hd {
    public String text="";
    public ObservableList<HyperLink> hyperlinks;
    
    public Hd(){
        this.hyperlinks = FXCollections.observableArrayList();
    }
    
    public Hd(String initText){
        this.text = initText;
        this.hyperlinks = FXCollections.observableArrayList();
    }
    
    public void addHyperLink(String initStartIndex, String initEndIndex, String initLink, String initText){
        hyperlinks.clear();
        HyperLink hy = new HyperLink(initStartIndex, initEndIndex, initLink, initText);
        hyperlinks.add(hy);
    }
}
