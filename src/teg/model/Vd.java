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
public class Vd {
    public String videoName = "";
    public String videoPath="";
    public String videoCaptions="";
    public String videoHeight="";
    public String videoWidth="";
    
    public Vd(){}
    
    public Vd(String initName, String initPath, String initCaption,String initHeight, String initWidth){
        this.videoName = initName;
        this.videoPath = initPath;
        this.videoCaptions = initCaption;
        this.videoHeight = initHeight;
        this.videoWidth = initWidth;
    }
    
    
}
