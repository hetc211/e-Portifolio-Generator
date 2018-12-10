/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.model;

/**
 *
 * @author HTC
 */
public class Banner {
    String name ="";
    String path="";
    String text="";
    
    public Banner(String initText){
        this.text = initText;
    }
    public Banner(String initName, String initPath, String initText){
        this.name = initName;
        this.path = initPath;
        this.text = initText+"'s ePortfolio";
    }

    public Banner() {
        this.name = "";
        this.path = "";
        this.text = "";
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getPath(){
        return this.path;
    }
    
    public String getText(){
        return this.text;
    }
    
}
