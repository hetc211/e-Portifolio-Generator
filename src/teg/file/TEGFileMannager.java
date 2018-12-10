/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.file;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import static teg.StartupConstants.PATH_DATA;
import teg.model.EPModel;
import javax.json.JsonWriter;
import teg.model.Component;
import teg.model.Content;
import teg.model.Font;
import teg.model.Hd;
import teg.model.HyperLink;
import teg.model.Img;
import teg.model.Paragraph;
import teg.model.Site;
import teg.model.Slide;
import teg.model.TList;
import teg.model.Vd;

/**
 *
 * @author HTC
 */
public class TEGFileMannager {
    public static String JSON_STUDENT_NAME = "student_name";
    public static String JSON_SITES = "sites";
    public static String JSON_TITLE = "title";
    public static String JSON_BANNER = "banner";
    public static String JSON_CONTENTS = "contents";
    public static String JSON_STYLE = "style";
    public static String JSON_COMPONENT = "component";    
    public static String JSON_PARAGRAPH = "paragraph";
    public static String JSON_TEXT = "text";
    public static String JSON_HYPERLINKS = "hyperlinks";
    public static String JSON_START_INDEX = "start_index";
    public static String JSON_END_INDEX = "end_index";
    public static String JSON_LINK = "link";
    public static String JSON_HEADER = "header";
    public static String JSON_LISTS = "lists";
    public static String JSON_LIST_TEXT = "list_text";
    public static String JSON_IMG = "img";
    public static String JSON_IMAGE_NAME = "image_name";
    public static String JSON_IMAGE_PATH = "image_path";
    public static String JSON_IMAGE_ID = "imageID";
    public static String JSON_HEIGHT = "height";
    public static String JSON_WIDTH = "width";
    public static String JSON_STATUS = "status";
    public static String JSON_CAPTIONS = "captions";
    public static String JSON_VIDEO = "video";
    public static String JSON_VIDEO_NAME = "video_name";
    public static String JSON_VIDEO_PATH = "video_path";
    public static String JSON_VIDEo_CAPTIONS = "captions";
    public static String JSON_SLIDES = "slides";
    public static String JSON_FOOTER = "footer";
    public static String JSON_FONT = "font";
    public static String JSON_FONT_NAME = "name";
    public static String JSON_FONT_PATH = "font_link";
    public static String JSON_FONT_SIZE = "font_size";
    public static String JSON_COLOR_THEME = "color_theme";
    public static String JSON_LAYOUT = "layout";
    public static String JSON_EXT = ".json";
    public static String SLASH = "/";
    
    public void saveEP(EPModel ePToSave, String path) throws IOException{
        //String ePTitle = ""+ ePToSave.getStudentName();
        //String jsonFilePath = PATH_DATA + SLASH + ePTitle + JSON_EXT; 
        String jsonFilePath = path;
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);
        //try{
        JsonArray sitesJsonArray = makeSitesJsonArray(ePToSave.getSites());
        JsonObject courseJsonObject = Json.createObjectBuilder()
                                        .add(JSON_STUDENT_NAME, ePToSave.getStudentName())
                                        .add(JSON_SITES, sitesJsonArray)
                                        .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(courseJsonObject);
        //}catch (NullPointerException e) {
        //    System.out.println("WTF");
        //}
        
    }
    
    public void loadEP(EPModel ePToLoad, String jsonFilePath) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // NOW LOAD THE SITES
	ePToLoad.reset();
        ePToLoad.setStudentName(json.getString(JSON_STUDENT_NAME));
        JsonArray jsonSitesArray = json.getJsonArray(JSON_SITES);
        for (int i = 0; i < jsonSitesArray.size(); i++) {
	    JsonObject siteJso = jsonSitesArray.getJsonObject(i);
	    ePToLoad.addSite(siteJso.getString(JSON_TITLE),
                             siteJso.getString(JSON_FOOTER),
                             siteJso.getString(JSON_LAYOUT),
                             siteJso.getString(JSON_COLOR_THEME));
            
            
            JsonObject bannerJso = siteJso.getJsonObject(JSON_BANNER);
            ePToLoad.getSites().get(i).setBanner(bannerJso.getString(JSON_IMAGE_NAME),
                                                 bannerJso.getString(JSON_IMAGE_PATH)//, 
                                                 //bannerJso.getString(JSON_TEXT)
                                                );
            
            JsonObject fontJso = siteJso.getJsonObject(JSON_FONT);
            ePToLoad.getSites().get(i).setFont( fontJso.getString(JSON_FONT_NAME),
                                                fontJso.getString(JSON_FONT_PATH),
                                                fontJso.getString(JSON_FONT_SIZE));
            
            JsonArray cJsA = siteJso.getJsonArray(JSON_CONTENTS);
            for(int j = 0 ; j< cJsA.size(); j++){
                JsonObject jsC = cJsA.getJsonObject(j);
                ePToLoad.getSites().get(i).addContent(jsC.getString(JSON_STYLE));
                
                //JsonObject cpJso = jsC.getJsonObject(JSON_COMPONENT);
                JsonObject jsCP =jsC.getJsonObject(JSON_COMPONENT);
                Component component = new Component();
                JsonObject jsH = jsCP.getJsonObject(JSON_HEADER);
                Hd hd = component.getHeader();
                hd.text = jsH.getString(JSON_TEXT);
                JsonArray hpA = jsH.getJsonArray(JSON_HYPERLINKS);
                for(int inx = 0; inx < hpA.size(); inx++){
                    JsonObject hhp = hpA.getJsonObject(inx);
                    hd.addHyperLink(hhp.getString(JSON_START_INDEX),
                                    hhp.getString(JSON_END_INDEX),
                                    hhp.getString(JSON_LINK),
                                    hhp.getString(JSON_TEXT));

                }
                JsonObject jsP = jsCP.getJsonObject(JSON_PARAGRAPH);
                //component.setParagraph(jsP.getString(JSON_TEXT));
                Paragraph paragraph = component.getParagraph();
                paragraph.text = jsP.getString(JSON_TEXT);

                JsonArray hpP = jsP.getJsonArray(JSON_HYPERLINKS);
                for(int inx = 0; inx < hpP.size(); inx++){
                    JsonObject hhp = hpP.getJsonObject(inx);
                    paragraph.addHyperLink(hhp.getString(JSON_START_INDEX),
                                    hhp.getString(JSON_END_INDEX),
                                    hhp.getString(JSON_LINK),
                                    hhp.getString(JSON_TEXT));

                }

                JsonArray jsL = jsCP.getJsonArray(JSON_LISTS);
                for(int inx = 0; inx < jsL.size(); inx++){
                    JsonObject listJs = jsL.getJsonObject(inx);

                    TList list = new TList(listJs.getString(JSON_TEXT));
                    JsonArray hpL = listJs.getJsonArray(JSON_HYPERLINKS);
                    for (int jnx = 0; jnx < hpL.size(); jnx++) {
                        JsonObject hhp = hpL.getJsonObject(jnx);
                        list.addHyperLink(hhp.getString(JSON_START_INDEX),
                                hhp.getString(JSON_END_INDEX),
                                hhp.getString(JSON_LINK),
                                hhp.getString(JSON_TEXT));
                    }
                    component.getLists().add(list);

                }

                JsonObject jsI = jsCP.getJsonObject(JSON_IMG);
                component.setImage( jsI.getString(JSON_IMAGE_NAME),
                                    jsI.getString(JSON_IMAGE_PATH),
                                    jsI.getString(JSON_STATUS),
                                    jsI.getString(JSON_HEIGHT),
                                    jsI.getString(JSON_WIDTH),
                                    jsI.getString(JSON_CAPTIONS),
                                    jsI.getString(JSON_IMAGE_ID));

                JsonObject jsV = jsCP.getJsonObject(JSON_VIDEO);
                component.setVideo( jsV.getString(JSON_VIDEO_NAME),
                                    jsV.getString(JSON_VIDEO_PATH),
                                    jsV.getString(JSON_CAPTIONS),
                                    jsV.getString(JSON_HEIGHT),
                                    jsV.getString(JSON_WIDTH));

                JsonArray jsSS = jsCP.getJsonArray(JSON_SLIDES);
                for(int jnx = 0; jnx < jsSS.size(); jnx++){
                    JsonObject jsS = jsSS.getJsonObject(jnx);
                    Slide slide = new Slide(jsS.getString(JSON_IMAGE_NAME),
                                            jsS.getString(JSON_IMAGE_PATH),
                                            jsS.getString(JSON_CAPTIONS));
                    component.slides.add(slide);
                }
                ePToLoad.getSites().get(i).getContents().get(j).setComponent(component);

            /*for(int k = 0; k< cpJsA.size(); k++){
                //content.addComponent();
                JsonObject jsCP = cpJsA.getJsonObject(k);
                Component component = new Component();
                JsonObject jsH = jsCP.getJsonObject(JSON_HEADER);
                Hd hd = component.getHeader();
                hd.text = jsH.getString(JSON_TEXT);
                JsonArray hpA = jsH.getJsonArray(JSON_HYPERLINKS);
                for(int inx = 0; inx < hpA.size(); inx++){
                    JsonObject hhp = hpA.getJsonObject(inx);
                    hd.addHyperLink(hhp.getString(JSON_START_INDEX),
                                    hhp.getString(JSON_END_INDEX),
                                    hhp.getString(JSON_LINK));

                }
                JsonObject jsP = jsCP.getJsonObject(JSON_PARAGRAPH);
                //component.setParagraph(jsP.getString(JSON_TEXT));
                Paragraph paragraph = component.getParagraph();
                paragraph.text = jsP.getString(JSON_TEXT);

                JsonArray hpP = jsP.getJsonArray(JSON_HYPERLINKS);
                for(int inx = 0; inx < hpP.size(); inx++){
                    JsonObject hhp = hpP.getJsonObject(inx);
                    paragraph.addHyperLink(hhp.getString(JSON_START_INDEX),
                                    hhp.getString(JSON_END_INDEX),
                                    hhp.getString(JSON_LINK));

                }

                JsonArray jsL = jsCP.getJsonArray(JSON_LISTS);
                for(int inx = 0; inx < hpP.size(); inx++){
                    JsonObject listJs = jsL.getJsonObject(inx);

                    TList list = new TList(listJs.getString(JSON_TEXT));
                    JsonArray hpL = listJs.getJsonArray(JSON_HYPERLINKS);
                    for (int jnx = 0; jnx < hpL.size(); jnx++) {
                        JsonObject hhp = hpP.getJsonObject(jnx);
                        list.addHyperLink(hhp.getString(JSON_START_INDEX),
                                hhp.getString(JSON_END_INDEX),
                                hhp.getString(JSON_LINK));
                    }
                    component.getLists().add(list);

                }

                JsonObject jsI = jsCP.getJsonObject(JSON_IMG);
                component.setImage( jsI.getString(JSON_IMAGE_NAME),
                                    jsI.getString(JSON_IMAGE_PATH),
                                    jsI.getString(JSON_STATUS),
                                    jsI.getString(JSON_HEIGHT),
                                    jsI.getString(JSON_WIDTH),
                                    jsI.getString(JSON_CAPTIONS));

                JsonObject jsV = jsCP.getJsonObject(JSON_VIDEO);
                component.setVideo( jsV.getString(JSON_VIDEO_NAME),
                                    jsV.getString(JSON_VIDEO_PATH),
                                    jsV.getString(JSON_CAPTIONS),
                                    jsV.getString(JSON_HEIGHT),
                                    jsV.getString(JSON_WIDTH));

                JsonArray jsSS = jsCP.getJsonArray(JSON_SLIDESHOW);
                for(int jnx = 0; jnx < jsSS.size(); jnx++){
                    JsonObject jsS = jsSS.getJsonObject(jnx);
                    Slide slide = new Slide(jsS.getString(JSON_IMAGE_NAME),
                                            jsS.getString(JSON_IMAGE_PATH),
                                            jsS.getString(JSON_CAPTIONS));
                    component.slides.add(slide);
                }
                ePToLoad.getSites().get(i).getContents().get(j).addComponent(component);
            }*/

            }
            
	}
    }

    private JsonObject loadJSONFile(String jsonFilePath) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    private JsonArray makeSitesJsonArray(ObservableList<Site> sites) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
            for (Site site : sites) {
                JsonObject jso = makeSiteJsonObject(site);
                jsb.add(jso);
            }
            JsonArray jA = jsb.build();
            return jA;
    }

    private JsonObject makeSiteJsonObject(Site site) {
        JsonObject jsb =  makeBannerJsonObject(site);
        JsonArray jsc = makeContentsJsonObject(site);
        JsonObject jsf = makeFontJsonObject(site.getFont());
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_TITLE, site.getTitle())
                .add(JSON_BANNER, jsb)
                .add(JSON_CONTENTS, jsc)
                .add(JSON_FOOTER, site.getFooter())
                .add(JSON_LAYOUT, site.getLayout())
                .add(JSON_FONT, jsf)
                .add(JSON_COLOR_THEME, site.getColorTheme())
                .build();
        return jso;
    }
    
    private JsonObject makeBannerJsonObject(Site site){
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_IMAGE_NAME, site.getBanner().getName())
                .add(JSON_IMAGE_PATH, site.getBanner().getPath())
                .add(JSON_TEXT, site.getBanner().getText())
                .build();
        return jso;
    }

    private JsonArray makeContentsJsonObject(Site site) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
            for (Content content : site.getContents()) {
                JsonObject jso = makeContentJsonObject(content);
                jsb.add(jso);
            }
            JsonArray jA = jsb.build();
            return jA;
    }

    private JsonObject makeContentJsonObject(Content content) {
        JsonObject jsb = makeComponentJsonObject(content.getComponent());
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_STYLE, content.getStyle())
                .add(JSON_COMPONENT, jsb)
                .build();
        return jso;
    }

    private JsonArray makeCompontentsJsonObject(ObservableList<Component> components) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Component component : components) {
            JsonObject jso = makeComponentJsonObject(component);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    
    
    }
    
    private JsonObject makeComponentJsonObject(Component component){
        JsonObject jsp = makeParagraphJsonObject(component.paragraph);
        JsonArray jsl = makeListsJsonObject(component.lists);
        JsonObject jsh = makeHeaderJsonObject(component.header);
        JsonObject jsi = makeImageJsonObject(component.image);
        JsonObject jsv = makeVideoJsonObject(component.video);
        JsonArray jsss = makeSlidesJsonArray(component.slides);
        
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_PARAGRAPH, jsp)
                .add(JSON_LISTS,  jsl)
                .add(JSON_HEADER, jsh)
                .add(JSON_IMG, jsi)
                .add(JSON_VIDEO, jsv)
                .add(JSON_SLIDES, jsss)
                .build();
        return jso;
    }

    
    private JsonObject makeParagraphJsonObject(Paragraph paragraph) {
        JsonArray jsa = makeHyperlinksJsonObject(paragraph.hyperlinks);
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_TEXT, paragraph.text)
                .add(JSON_HYPERLINKS, jsa)
                .build();
        return jso;
    }
    
    

    private JsonArray makeHyperlinksJsonObject(ObservableList<HyperLink> hyperlinks) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (HyperLink hyperlink : hyperlinks) {
            JsonObject jso = makeHyperlinkJsonObject(hyperlink);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    
    }

    private JsonObject makeHyperlinkJsonObject(HyperLink hyperlink) {
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_START_INDEX, hyperlink.startIndex)
                .add(JSON_END_INDEX, hyperlink.endIndex)
                .add(JSON_LINK, hyperlink.link)
                .add(JSON_TEXT, hyperlink.text)
                .build();
        return jso;
    }

    private JsonArray makeListsJsonObject(ObservableList<TList> lists) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for(TList list : lists){
            JsonObject jso = makeListJsonObject(list);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    
    }
    
    private JsonObject makeListJsonObject(TList list) {
        JsonArray jsa = makeHyperlinksJsonObject(list.hyperlinks);
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_TEXT, list.text)
                .add(JSON_HYPERLINKS, jsa)
                .build();
        return jso;
    }
    
    
    private JsonObject makeHeaderJsonObject(Hd header) {
        JsonArray jsa = makeHyperlinksJsonObject(header.hyperlinks);
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_TEXT, header.text)
                .add(JSON_HYPERLINKS, jsa)
                .build();
        return jso;
    }

    private JsonObject makeImageJsonObject(Img image) {
        JsonObject jso = Json.createObjectBuilder()
                 .add(JSON_IMAGE_NAME, image.imageName)
                .add(JSON_IMAGE_PATH, image.imagePath)
                .add(JSON_CAPTIONS, image.captions)
                .add(JSON_HEIGHT, image.imageHeight)
                .add(JSON_WIDTH, image.imageWidth)
                .add(JSON_STATUS, image.imageStatus)
                .add(JSON_IMAGE_ID,image.imageID)
                .build();
        return jso;
    
    }

    private JsonObject makeVideoJsonObject(Vd video) {
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_VIDEO_NAME, video.videoName)
                .add(JSON_VIDEO_PATH, video.videoPath)
                .add(JSON_CAPTIONS, video.videoCaptions)
                .add(JSON_HEIGHT, video.videoHeight)
                .add(JSON_WIDTH, video.videoWidth)
                .build();
        return jso;
    }

    /*private JsonObject makeSlideShowJsonObject(ObservableList<Slide> slides) {
        JsonArray slidesJsonArray = makeSlidesJsonArray(slides);
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_SLIDESHOW, slidesJsonArray)
                .build();
        return jso;
    }*/

    private JsonArray makeSlidesJsonArray(ObservableList<Slide> slides) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Slide slide : slides) {
	    JsonObject jso = makeSlideJsonObject(slide);
	    jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA; 
    
    }

    private JsonObject makeSlideJsonObject(Slide slide) {
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_IMAGE_NAME, slide.getImageFileName())
                .add(JSON_IMAGE_PATH, slide.getImagePath())
                .add(JSON_CAPTIONS, slide.getImageCaptions())
                .build();
        return jso;
    }

    private JsonObject makeFontJsonObject(Font font) {
        JsonObject jso= Json.createObjectBuilder()
                .add(JSON_FONT_NAME, font.name)
                .add(JSON_FONT_PATH, font.link)
                .add(JSON_FONT_SIZE, font.size)
                .build();
        return jso;
    }
    
    
    
}
