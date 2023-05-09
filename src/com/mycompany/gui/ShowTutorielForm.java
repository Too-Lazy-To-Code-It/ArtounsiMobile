package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Tutoriel;
import com.mycompany.entites.Video;
import com.mycompany.service.FavoriService;
import com.mycompany.service.RatingService;
import com.mycompany.service.TutorielService;
import com.mycompany.service.VideoService;
import java.io.IOException;
import java.util.List;

public class ShowTutorielForm extends BaseForm {

    public ShowTutorielForm(Tutoriel t,Resources res) {
        super("Newsfeed", BoxLayout.y());
        
        RatingWidget.bindRatingListener(180000, getAppstoreURL(), "your-support-email-address@here.com");
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle(t.getTitle());
        getContentPane().setScrollVisible(false);
        TutorielService ts = TutorielService.getInstance();
        VideoService vs = VideoService.getInstance();
        List<Video> videos = ts.fetchVideos(t.getId_tutoriel());
    
        super.addSideMenu(res);        
        //tb.addSearchCommand(e -> {});
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Image imgs;
        EncodedImage enc = null;
        ImageViewer imgv;
        String url = "http://localhost/img/" + t.getPathimg();

        try {
            //Image image = Image.createImage("http://localhost/img/" + t.getPathimg());
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {

        }

        imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_REMOVE , (e) -> {
            if (ts.deleteTutoriel(t)) {
                Dialog.show("Success", "Tutoriel deleted successfully", "Got it", null);
                new ShowTutorielsForm(res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }    
        });
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_EDIT , (e) -> new ModifyTutorielForm(t,res).show());
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD_CIRCLE , (e) -> new AddVideoForm(res,t).show());

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        FavoriService fs = FavoriService.getInstance();
        addTab(t,swipe, imgs, spacer1, fs.countFavoris(t).get(0).getId_favoris()+" Favoris  ", "85 Comments", t.getDescription());
                
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        RatingService rs = RatingService.getInstance();
        Button submit = new Button("Rate");
        
       Slider stars = createStarRankSlider();
       if(rs.fetchMyRateTutoriel(t).size()>0)
        stars.setProgress(rs.fetchMyRateTutoriel(t).get(0).getRating());
       Container content = BoxLayout.encloseY(
                FlowLayout.encloseCenter(stars),
                createLineSeparator(),
                submit
        );
        content.setScrollableY(true);
        add(content);

        for (int i = 0; i < videos.size(); i++) {
            addItem(videos.get(i), t, res);
        }
        
        submit.addActionListener((evt) -> {
            if (rs.rateTutoriel(t, stars.getProgress())) {
                Dialog.show("Success", "Thanks For Your Rating", "Got it", null);
                new ShowTutorielsForm(res).showBack();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });
        
        /*
        for (int i = 0; i < videos.size(); i++) {
            addButton(videos.get(i),res, "http://localhost/img/"+videos.get(i).getPathimage(), videos.get(i).getTitle(), false, 26, 32);
        }*/
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tutoriel t,Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);
        Label comments = new Label(t.getAvg_rating());
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_STAR);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        FavoriService fs = FavoriService.getInstance();
        
        Button favori = new Button();
        
        if(fs.checkFavoris(t).size()>0)
            FontImage.setMaterialIcon(favori, FontImage.MATERIAL_FAVORITE);
        else
            FontImage.setMaterialIcon(favori, FontImage.MATERIAL_FAVORITE_BORDER);
        
        favori.addActionListener(e->{
            if(fs.checkFavoris(t).size()>0){
                fs.deleteFavori(t);
                FontImage.setMaterialIcon(favori, FontImage.MATERIAL_FAVORITE_BORDER);
            }   
            else{
                fs.addFavori(t);
                FontImage.setMaterialIcon(favori, FontImage.MATERIAL_FAVORITE);
            }
                
        });
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            favori,
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
   private void addButton(Tutoriel t,Video v,Resources res,String url, String title, boolean liked, int likeCount, int commentCount) {
       
        ImageViewer imgg = null;
        EncodedImage enc = null;
        Image img;
        ImageViewer imgv;
        
        
        try {
            //Image image = Image.createImage("http://localhost/img/" + t.getPathimg());
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {

        }
        
        img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgg = new ImageViewer(img);
        
       
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);
       TextArea ta = new TextArea(title);
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);

       FavoriService fs = FavoriService.getInstance();
       Label likes = new Label(fs.countFavoris(t).get(0).getId_favoris() + " Favoris", "NewsBottomLine");
       Label comments = new Label(t.getAvg_rating()+ " Stars  ", "NewsBottomLine");
       if(!liked) {
           FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
       } else {
           Style s = new Style(likes.getUnselectedStyle());
           s.setFgColor(0xff2d55);
           FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
           likes.setIcon(heartImage);
       }
              likes.setTextPosition(RIGHT);

       FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
       
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,
                       BoxLayout.encloseX(likes, comments)
               ));
       add(cnt);
       
       //image.addActionListener(e -> new ShowTutorielForm(t,res).show());
   }
   
   private void addItem(Video v, Tutoriel t, Resources res) {
        VideoService vs = VideoService.getInstance();
        ImageViewer img = null;
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Image imgs;
        EncodedImage enc = null;
        ImageViewer imgv;
        String url = "http://localhost/img/" + v.getPathimage();
        
        try {
            //Image image = Image.createImage("http://localhost/img/" + t.getPathimg());
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {

        }

        imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label title = new Label(v.getTitle());
        TextArea description = description = new TextArea(v.getDescription());
        try {
            description = new TextArea(v.getDescription().substring(0, 20) + "...");
        } catch (Exception e) {
        };
        try {
            description = new TextArea(v.getDescription().substring(0, 30) + "...");
        } catch (Exception e) {
        };
        description.setEditable(false);
        description.setFocusable(false);
        description.setUIID("Label");
        


        Button play = new Button("Play");
        play.addActionListener(e -> {
            new PlayVideoForm();
        });
                 Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));

        Button modif = new Button();
        FontImage.setMaterialIcon(modif, FontImage.MATERIAL_MODE_EDIT);
        modif.addActionListener(e->{
            new ModifyVideoForm(res,v,t).show();
        });
        Button delete = new Button();
        FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
        delete.addActionListener(e->{
            if(vs.deleteVideo(v)) {
                Dialog.show("Success", "Video deleted successfully", "Got it", null);
                new ShowTutorielForm(t,res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });

        img = new ImageViewer(imgs);
        c3.add(modif);
        c3.add(delete);
        c2.add(title);
        c2.add(description);
        c2.add(play);
        c2.add(c3);
        c1.add(img);
        //c1.add(new RatingWidget());
        c1.add(c2);
        this.add(c1);

        this.refreshTheme();
    }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
    public  String getAppstoreURL() {
    if(Display.getInstance().getPlatformName().equals("ios")) {
        return "https://itunes.apple.com/us/app/kitchen-sink-codename-one/id635048865";
    }
    if(Display.getInstance().getPlatformName().equals("and")) {
        return "https://play.google.com/store/apps/details?id=com.codename1.demos.kitchen";
    }
    return null;
    }
    
    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL).
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }
}