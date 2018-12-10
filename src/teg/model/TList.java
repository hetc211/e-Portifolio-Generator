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
public class TList {
    public String text="";
    public ObservableList<HyperLink> hyperlinks;
   
    
    public TList(String initText){
        this.text = initText;
        hyperlinks = FXCollections.observableArrayList();
    }
    
    public String getText(){
        return this.text;
    } 
    
    public void setText(String initText){
        this.text = initText;
    }
    
    public void addHyperLink(String initStartIndex, String initEndIndex, String initLink, String initText){
        HyperLink hy = new HyperLink(initStartIndex, initEndIndex, initLink, initText);
        hyperlinks.add(hy);
    }
    
    
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        //ItemInfo itemClone = new ItemInfo();
        TList itemClone =  new TList("");
        itemClone.text = this.text;
        for(HyperLink hy : this.hyperlinks){
            HyperLink h = new HyperLink();
            h = (HyperLink) hy.clone();
            itemClone.hyperlinks.add(h);
        }
        return itemClone;
    }
    
}
