package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
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
import com.mycompany.service.FavoriService;
import com.mycompany.service.TutorielService;
import java.io.IOException;
import java.util.List;

public class showBestTutorielsForm extends BaseForm {

    public showBestTutorielsForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Tutoriels");
        getContentPane().setScrollVisible(false);
            
        TutorielService ts = TutorielService.getInstance();
        List<Tutoriel> tutoriels = ts.fetchBestTutoriels();
        
        super.addSideMenu(res);
        
        //tb.addSearchCommand(e -> {});
        tb.addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD_CIRCLE , (e) -> new AddTutorielForm(res).show());

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("first.jpg"), spacer1, "Digital art tutorials are an excellent resource for anyone who wants to improve their digital art skills, whether for personal or professional purposes. ");
        addTab(swipe, res.getImage("second.jpg"), spacer2, "With the right guidance and practice, individuals can create stunning digital artwork and unleash their creativity in the digital realm.");
                
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
        RadioButton all = RadioButton.createToggle("All", barGroup);
        all.addActionListener((e)->{
            tutoriels.removeAll(tutoriels);
            tutoriels.addAll(ts.fetchFavorisTutoriels());
            this.refreshTheme();
        });
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Favoris", barGroup);
        featured.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        RadioButton best = RadioButton.createToggle("Best", barGroup);
        best.setUIID("SelectBar");
                
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, all, featured, best),
                FlowLayout.encloseBottom(arrow)
        ));
        
        best.setSelected(true);
        arrow.setVisible(false);
        
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        /*Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        for (int i = 0; i < tutoriels.size(); i++) {
            addItem(tutoriels.get(i), res);
        }*/
        
        
        for (int i = 0; i < tutoriels.size(); i++) {
            addButton(tutoriels.get(i),res, "http://localhost/img/"+tutoriels.get(i).getPathimg(), tutoriels.get(i).getTitle(), false, 26, 32);
        }
        
        all.addActionListener((e)->{
            new ShowTutorielsForm(res).showBack();
        });
        
        featured.addActionListener((e)->{
            new showfavorisTutorielsForm(res).showBack();
        });
        
        
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }


        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
    
   private void addButton(Tutoriel t,Resources res,String url, String title, boolean liked, int likeCount, int commentCount) {
       
        ImageViewer imgg = null;
        EncodedImage enc = null;
        Image img;
        ImageViewer imgv;
        
        try {
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

       System.out.println(t.getAvg_rating());
       Label likes = new Label(t.getAvg_rating()+ " Stars  ", "NewsBottomLine");
       likes.setTextPosition(RIGHT);
       if(!liked) {
           FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);
       } else {
           Style s = new Style(likes.getUnselectedStyle());
           s.setFgColor(0xff2d55);
           FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
           likes.setIcon(heartImage);
       }
       
       FavoriService fs = FavoriService.getInstance();
       Label comments = new Label(fs.countFavoris(t).get(0).getId_favoris() + " Favoris", "NewsBottomLine");
       FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
       
       
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,
                       BoxLayout.encloseX(likes, comments)
               ));
       add(cnt);
       image.addActionListener(e -> new ShowTutorielForm(t,res).show());
   }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
    private void addItem(Tutoriel t, Resources res) {
        TutorielService ts = TutorielService.getInstance();
        Container c1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        ImageViewer img = null;
        EncodedImage enc = null;
        Image imgs;
        ImageViewer imgv;
        String url = "http://localhost/img/"+t.getPathimg();
        
        
        try {
            //Image image = Image.createImage("http://localhost/img/" + t.getPathimg());
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {

        }
        
        imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

        Container c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label title = new Label(t.getTitle());
        TextArea description  = new TextArea(t.getDescription());
        try{
             description = new TextArea(t.getDescription().substring(0,20)+"...");
        }catch(Exception e){};
        try{
             description = new TextArea(t.getDescription().substring(0,30)+"...");
        }catch(Exception e){};
        description.setEditable(false);
        description.setFocusable(false);
        description.setUIID("Label");
        
        Button affiche = new Button("Afficher");
        affiche.addActionListener(e->{
            new ShowTutorielForm(t,res).show();
        });
        
         Container c3 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        Button modif = new Button();
        FontImage.setMaterialIcon(modif, FontImage.MATERIAL_MODE_EDIT);
        modif.addActionListener(e->{
            new ModifyTutorielForm(t,res).show();
        });
        Button delete = new Button();
        FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
        delete.addActionListener(e->{
            if (ts.deleteTutoriel(t)) {
                Dialog.show("Success", "Tutoriel deleted successfully", "Got it", null);
                new ShowTutorielsForm(res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });
        
        img = new ImageViewer(imgs);
        
        c3.add(modif);
        c3.add(delete);
        c3.getAllStyles().setMarginRight(10);;
        c2.add(title);
        c2.add(description);
        c2.add(affiche);
        c2.add(c3);
        c1.add(img);
        c1.add(c2);
        this.add(c1);
        
        this.refreshTheme();
    }
    
}
