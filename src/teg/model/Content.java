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
public class Content {
    public String style="";
    public Component component;
    
    public Content(){
        this.component = new Component();
    }
    
    public Content(String initStyle){
        this.style = initStyle;
        this.component = new Component(initStyle);
    }
    
    public String getStyle(){
        return this.style;
    }
    
    public Component getComponent(){
        return this.component;
    }
    
    
    public void setStyle(String initStyle){
        this.style = initStyle;
    }
    
    public void setComponent(Component comp) {
        this.component = comp;
    }
    
}
