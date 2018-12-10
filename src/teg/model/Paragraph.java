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
public class Paragraph {
    public String text="";
    public ObservableList<HyperLink> hyperlinks;
    public Font font;
    
   
    public Paragraph(String initText){
        text = initText;
        this.hyperlinks = FXCollections.observableArrayList();
        this.font = new Font();
    }

    
    public void addHyperLink(String initStartIndex, String initEndIndex, String initLink, String initText){
        HyperLink hy = new HyperLink(initStartIndex, initEndIndex, initLink,initText);
        hyperlinks.add(hy);
    }
    
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        Paragraph h = new Paragraph("");
        h.text = this.text;
        for(HyperLink hy : this.hyperlinks){
            h.hyperlinks.add(hy);
        }
        h.font = (Font) this.font.clone();
        return h;
    }
    
}
