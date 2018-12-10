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
public class HyperLink {
    public String startIndex="";
    public String endIndex="";
    public String link="";
    public String text="";
    
    public HyperLink(){}
    
    public HyperLink(String initStartIndex, String initEndIndex, String initLink, String initText) {
        this.startIndex = initStartIndex;
        this.endIndex = initEndIndex;
        this.link = initLink;
        this.text = initText;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        HyperLink hy = new HyperLink();
        hy.endIndex = this.endIndex;
        hy.link = this.link;
        hy.startIndex = this.startIndex;
        hy.text = this.text;
        return hy;
                
    }
    
    
    
}
