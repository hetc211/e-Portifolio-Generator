/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.model;

import static teg.StartupConstants.PATH_IMAGES;

/**
 *
 * @author HTC
 */
public class Img {
    public String imageName="";
    public String imagePath="";
    public String captions="";
    public String imageHeight="";
    public String imageWidth="";
    public String imageStatus="";
    public String imageID = "";
    
    public Img(){
        
    }
    
    public Img(String initName, String initPath, String initStatus, String initHeight, String initWidth, String initCaption, String initID){
        this.imageName = initName;
        this.imagePath = initPath;
        this.imageStatus = initStatus;
        this.imageHeight = initHeight;
        this.imageWidth = initWidth;    
        this.captions = initCaption;
        this.imageID = initID;
    }
    
}
