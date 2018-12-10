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
public class Slide {
    String imageFileName;
    String imagePath;
    String imageCaptions;
    /**
     * Constructor, it initializes all slide data.
     * @param initImageFileName File name of the image.
     * 
     * @param initImagePath File path for the image.
     * 
     */
    public Slide(String initImageFileName, String initImagePath, String iniImageCaptions) {
	imageFileName = initImageFileName;
	imagePath = initImagePath;
        imageCaptions = iniImageCaptions;
    }
    
    // ACCESSOR METHODS
    public String getImageFileName() { return imageFileName; }
    public String getImagePath() { return imagePath; }
    public String getImageCaptions() {return imageCaptions;}
    
    public void setImageFileName(String initImageFileName) {
	imageFileName = initImageFileName;
    }
    
    public void setImagePath(String initImagePath) {
	imagePath = initImagePath;
    }
    
    public void setImageCaptions(String initImageCaptions) {
	imageCaptions = initImageCaptions;
    }
    public void setImage(String initPath, String initFileName, String initCaptions) {
	imagePath = initPath;
	imageFileName = initFileName;
        imageCaptions = initCaptions;
    }
    public void setImage(String initPath, String initFileName) {
	imagePath = initPath;
	imageFileName = initFileName;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        Slide clonedS = new Slide(null,null,null);
        clonedS.imageCaptions = this.imageCaptions;
        clonedS.imageFileName = this.imageFileName;
        clonedS.imagePath = this.imagePath;
        return clonedS;
    }
    
}
