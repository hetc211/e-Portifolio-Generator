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
public class Font {
    public String name= "";
    public String link= "";
    public String size= "";
    
    public Font(){
        this.name = "'Julius Sans One', sans-serif";
        this.link = "https://fonts.googleapis.com/css?family=Julius+Sans+One";
        this.size = "12";
    }
    public Font(String initName, String initLink){
        name = initName;
        link = initLink;
        size = "12";
    }
    
    public Font(String initName, String initLink, String initSize){
        name = initName;
        link = initLink;
        size = initSize;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        Font f = new Font();
        f.link = this.link;
        f.name = this.name;
        f.size = this.size;
        return f;
    }
}
