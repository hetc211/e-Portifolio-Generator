/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// CONSTANTS
var IMG_PATH;
var ICON_PATH;
var IMG_WIDTH;
var SCALED_IMAGE_HEIGHT;
var FADE_TIME;
var SLIDESHOW_SLEEP_TIME;

// DATA FOR CURRENT SLIDE
var slides;
var currentSlide;

// TIMER FOR PLAYING SLIDESHOW
var timer;
//data for current page
var site_name;
var title;
var banner;
var contents;
var footer;
var currentPage;

var pages;

function Page(initTitle, initBanner, initContent, initFooter){
    this.title = initTitle;
    this.banner = new Banner(initBanner);
    this.contents = new Array();
    for(var i = 0; i<initContent.length; i++){
        var lawC = initContent[i];
        var temp = new Content(lawC.style, lawC.components);
        this.contents[i] = temp;
    }
    this.footer = initFooter;
}

function Content(initStyle, initComponent){
    this.style = initStyle;
    if(this.style==="t_p"||this.style==="t_h")
        this.component = initComponent;
    if(this.style ==="t_l")
        this.component = new TextList(initComponent);
    if(this.style ==="image")
        this.component = new Image(initComponent);
    if(this.style ==="t_link")
        this.component = new Hlink(initComponent);
    if(this.style ==="slideShow")
        this.component = new SlideShow(initComponent);
    if(this.style ==="video")
        this.component = new Video_C(initComponent);
}

function TextList(initComponent){
    for(var i=0; i<initComponent.length;i++){
        this[i] = initComponent[i];
    }
}

function Image(initComponent){
    this.imageFile = initComponent.imageName;
}

function Hlink(initComponent){
    this.textNode = initComponent.textNode;
    this.hyperlink = initComponent.Hyperlink;
}

function SlideShow(initComponent){
    slides = new Array();
    for (var i = 0; i < initComponent.slides.length; i++) {
	var rawSlide = initComponent.slides[i];
	var slide = new Slide(rawSlide.image_file_name, rawSlide.image_captions);
	slides[i] = slide;
    }
    if (slides.length > 0)
	currentSlide = 0;
    else
	currentSlide = -1;
}

function Video_C(initComponent){
    this.video_name = initComponent.video_name;
}

function Slide(initImgFile, initCaption) {
    this.imgFile = initImgFile;
    this.caption = initCaption;
}


function Banner(initImgFile){
    this.imgFile = initImgFile;
}

function initSlideShow(){
    IMG_PATH = "./image/";
    ICON_PATH = "./icons/";
    IMG_WIDTH = 1000;
    SCALED_IMAGE_HEIGHT = 500.0;
    FADE_TIME = 1000;
    SLIDESHOW_SLEEP_TIME = 3000;
    //slides = new Array();
    timer = null;
}
function initContent() {
    pages = new Array();
    var siteDataFile = "./data/json.json";
    loadData(siteDataFile);
}

function initNavi(){
    for (var i = 0; i < pages.length; i++) {
        if(i===currentPage){
            var li = document.createElement("LI");
            li.setAttribute("class","active");
            var a =  document.createElement('a');
            a.setAttribute("href","/HTML5Application/"+pages[i].title +"/"+ pages[i].title.toLowerCase() + ".html");
            a.innerHTML = pages[i].title;
            li.appendChild(a);
            $("#naviBar").append(li);
            
        }
        else{
        $('<a />', {
            href: "/HTML5Application/"+ pages[i].title +"/"+ pages[i].title.toLowerCase() + ".html",
            text: pages[i].title
        }).wrap('<li />').parent().appendTo('#naviBar');
        }
    }
}

function initSiteName(){
    $("#site_name").html(site_name);
}
function initPage(){
    initNavi();
    initBanner();
    initSiteName();
    fillingContent();
    initFooter();
}

function initFooter(){
    var f = document.getElementById("ft");
    var p = document.createElement("p");
    var text = document.createTextNode("footer");
    p.appendChild(text);
    f.appendChild(p);
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function(json) {
	loadPageData(json);
	initPage();
    });
}
function loadPageData(siteData){
    site_name = siteData.Sites_Name;
    pages = new Array();
    
    for(var i=0; i<siteData.Pages.length; i++){
        var tempPage = new Page(siteData.Pages[i].title, siteData.Pages[i].banner.imageName, siteData.Pages[i].contents, siteData.Pages[i].footer);
         
        pages[i] = tempPage; 
        //console.log(pages[i].title);
        if(pages[i].title ===
                "Cse219"
              ){
            currentPage = i;
            //console.log(currentPage);
                }
    
    
    }
}

function initBanner(){
    var bannerImage = document.getElementById("banner_img");
    bannerImage.setAttribute("src","./image/banner/"+ pages[currentPage].banner.imgFile);
    bannerImage.setAttribute("width","200");
    bannerImage.setAttribute("height","400");
    //console.log(pages[currentPage].banner.imgFile);
}

function fillingContent(){
    for(var i=0; i<pages[currentPage].contents.length; i++){
        //console.log(pages[currentPage].contents[i].style);
        if(pages[currentPage].contents[i].style =='t_p')
            addText_P(pages[currentPage].contents[i].component);
        if(pages[currentPage].contents[i].style =='t_h')
            addText_H(pages[currentPage].contents[i].component);
        if(pages[currentPage].contents[i].style =='t_l')
            addText_L(pages[currentPage].contents[i].component);
        if(pages[currentPage].contents[i].style =='t_link')
            addHLink(pages[currentPage].contents[i].component);
        if(pages[currentPage].contents[i].style =='image')
            addImage(pages[currentPage].contents[i].component.imageFile);
        if(pages[currentPage].contents[i].style =='video')
            addVideo(pages[currentPage].contents[i].component);
        if(pages[currentPage].contents[i].style =='slideShow')
            addSlideShow(pages[currentPage].contents[i].component);
        
    }
}
function addText_P(p){
    var mainContent = document.getElementById("content");
    var temp = document.createElement("p");
    temp.innerHTML = p;
    mainContent.appendChild(temp);
    
}

function addText_H(header){
    var mainContent = document.getElementById("content");
    var temp = document.createElement("h3");
    var bold = document.createElement('strong');
    bold.innerHTML = header;
    temp.appendChild(bold);
    mainContent.appendChild(temp);
}
function addText_L(list){
    var mainContent = document.getElementById("content");
    var div = document.createElement('div');
    var ul = document.createElement("ul");
    for(var i=0; i<list.length; i++){
        var temp = documnet.createElement("li");
        temp.innerHTML = list[i];
        ul.appendChild(temp);
    }
    div.appendChild(ul);
    mainContent.appendChild(div);
}
function addImage(image){
    var mainContent = document.getElementById("content");
    var div = document.createElement('div');
    var temp = document.createElement('img');
    temp.setAttribute("src","./image/"+image);
    temp.setAttribute("height","800");
    temp.setAttribute("width","900");
   
    div.appendChild(temp);
    mainContent.appendChild(div);
}
function addHLink(hlink){
    var mainContent = document.getElementById("content");
    var div = document.createElement('div');
    var temp = document.createElement('a');
    temp.setAttribute("href","http://"+link.hyperlink);
    var text = document.createTextNode(hlink.textNode);
    temp.appendChild(text);
    div.appendChild(temp);
    mainContent.appendChild(div);
}

function addVideo(video){
    var mainContent = document.getElementById("content");
    var div = document.createElement('div');
    var temp = document.createElement('video');
    temp.setAttribute("width","800");
    temp.setAttribute("height","900");
    temp.setAttribute("controls","");
    source = document.createElement('source');
    $(source).attr('type', 'video/mp4');
    $(source).attr('src','./video/'+video.video_name);
    $(mainContent).append(temp);
    $(temp).append(source);
    div.appendChild(temp);
    mainContent.appendChild(div);
}
function addSlideShow(){
    initSlideShow();
    var mainContent = document.getElementById("content");
    var temp = document.createElement('div');
    var image = document.createElement('img');
    if (slides.length > 0)
	currentSlide = 0;
    else
	currentSlide = -1;
    image.setAttribute('id','slide_img');
    var slideCaption = document.createElement('div');
    slideCaption.setAttribute("id","slide_caption");
    var slideControl = document.createElement('div');
    slideControl.setAttribute("id","slideshow_controls");
    var preBT = document.createElement('input');
    preBT.setAttribute("id","previous_button");
    preBT.setAttribute('type','image');
    preBT.setAttribute('src','./icons/Previous.png');
    preBT.setAttribute('onClick','processPreviousRequest()');
    
    var playBT = document.createElement('input');
    playBT.setAttribute("id","play_pause_button");
    playBT.setAttribute('type','image');
    playBT.setAttribute('src','./icons/Play.png');
    playBT.setAttribute('onClick','processPlayPauseRequest()');
    
    var nextBT = document.createElement('input');
    nextBT.setAttribute("id","next_button");
    nextBT.setAttribute('type','image');
    nextBT.setAttribute('src','./icons/Next.png');
    
    nextBT.setAttribute('onClick','processNextRequest()');
    
    slideControl.appendChild(preBT);
    slideControl.appendChild(playBT);
    slideControl.appendChild(nextBT);
    
    temp.appendChild(image);
    temp.appendChild(slideCaption);
    temp.appendChild(slideControl);
    mainContent.appendChild(temp);
    if (currentSlide >= 0) {
	$("#slide_caption").html(slides[currentSlide].caption);
	$("#slide_img").attr("src", IMG_PATH + slides[currentSlide].imgFile);
	$("#slide_img").one("load", function() {
	    autoScaleImage();
	});
    }
   //var newHTML = document.createElemant('html');
}

function fadeInCurrentSlide() {
    var filePath = IMG_PATH + slides[currentSlide].img_File;
    $("#slide_img").fadeOut(FADE_TIME, function(){
	$(this).attr("src", filePath).bind('onreadystatechange load', function(){
	    if (this.complete) {
		$(this).fadeIn(FADE_TIME);
		$("#slide_caption").html(slides[currentSlide].caption);
		autoScaleImage();
	    }
	});
    });     
}

function autoScaleImage() {
    var origHeight = $("#slide_img").height();
    var scaleFactor = SCALED_IMAGE_HEIGHT/origHeight;
    var origWidth = $("#slide_img").width();
    var scaledWidth = origWidth * scaleFactor;
    $("#slide_img").height(SCALED_IMAGE_HEIGHT);
    $("#slide_img").width(scaledWidth);
    var left = (IMG_WIDTH-scaledWidth)/2;
    $("#slide_img").css("left", left);
}



function processPreviousRequest() {
    currentSlide--;
    if (currentSlide < 0)
	currentSlide = slides.length-1;
    fadeInCurrentSlide();
}

function processPlayPauseRequest() {
    if (timer === null) {
	timer = setInterval(processNextRequest, SLIDESHOW_SLEEP_TIME);
	$("#play_pause_button").attr("src", ICON_PATH + "Pause.png");
    }
    else {
	clearInterval(timer);
	timer = null;
	$("#play_pause_button").attr("src", ICON_PATH + "Play.png");
    }	
}

function processNextRequest() {
    currentSlide++;
    if (currentSlide >= slides.length)
	currentSlide = 0;
    fadeInCurrentSlide();
}

