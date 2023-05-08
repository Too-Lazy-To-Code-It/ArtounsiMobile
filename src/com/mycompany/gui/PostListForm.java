/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Category;
import com.mycompany.entites.Comment;
import com.mycompany.entites.Post;
import com.mycompany.entites.PostLike;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.ServicePost;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author amine
 */
public class PostListForm extends BaseForm{
    
    Form current;
    public PostListForm(Resources res){
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
    
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Explore");
        getContentPane().setScrollVisible(false);
        
        
        tb.addSearchCommand(e ->  {
            
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
        addTab(swipe,s1, res.getImage("simon-tosovsky-magicpowder-02.jpg"),"","",res);
        
        //
        
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
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("NewsfeedForm", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Blog", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Explore", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
            //refreshTheme();
            new NewsfeedForm(res).show(); 
        });
        
        liste.addActionListener((e) -> {
             new BlogListForm(res).show();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        
        //CALLLING DISPLAY METHOD 
        ArrayList<Post>List = ServicePost.getInstance().displayPost();
        for(Post post: List){
            Comment cat = new Comment();
//                if(post.getPost_type()=="blog"){
                PostLike postlike = new PostLike();
                String urlImage = "simon-tosovsky-magicpowder-02.jpg";
                Image placeHolder = Image.createImage(120,90);
                EncodedImage enc =  EncodedImage.createFromImage(placeHolder, false);
                URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
                addButton(urlim,post,res, post.getId_post(),cat);

                ScaleImageLabel image = new ScaleImageLabel(urlim);

                Container containerImg = new Container();
                //System.out.println("mmmmmm"+post.getPost_type());
                image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
//            }

        }

        
        //
        
        
        
        
    }
    private void addTab(Tabs swipe, Label spacer , Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size) {
            image = image.scaledHeight(size);
        }
        
        
        
        if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2 ) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        
        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        Label overLay = new Label("","ImageOverlay");
        
        
        Container page1 = 
                LayeredLayout.encloseIn(
                imageScale,
                        overLay,
                        BorderLayout.south(
                        BoxLayout.encloseY(
                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                        )
                    )
                );
        
        swipe.addTab("",res.getImage("simon-tosovsky-magicpowder-02.jpg"), page1);
        
        
        
        
    }
    public void bindButtonSelection(Button btn , Label l ) {
        
        btn.addActionListener(e-> {
        if(btn.isSelected()) {
            updateArrowPosition(btn,l);
        }
    });
    }

    private void updateArrowPosition(Button btn, Label l) {
        
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth()  / 2  - l.getWidth() / 2 );
        l.getParent().repaint();
    }
    
    private void addButton(Image img, Post post, Resources res, int id_post,Comment cat) {    
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.west(image);
        TextArea ta = new TextArea(post.getTitle_p());
        TextArea desc = new TextArea(post.getDescription_p());
        
       // PostLike postlike = new PostLike();
        
        ta.setUIID("NewsTopLine");
        ta.setEditable(false);
        Label categorynameTxt = new Label("Post Title : "+post.getTitle_p(),"NewsTopLine2" );
        Label descriptionTxt = new Label("Post Description : "+post.getDescription_p(),"NewsTopLine2" );
        //Label IntlikeTxt = new Label("Postlike  : "+postlike.getId_like(),"NewsTopLine2" );
       // cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(categorynameTxt)));
       Label mediaTxt = new Label("Post media : "+post.getMedia(),"NewsTopLine2" );
       
       ImageViewer imgg = null;
        EncodedImage enc = null;
        Image imgs;
        ImageViewer imgv;
        String url = post.getMedia();
        
        
        try {
            //Image image = Image.createImage("http://localhost/img/" + t.getPathimg());
            enc = EncodedImage.create("/load.png");
        } catch (IOException ex) {

        }
        
        imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgg = new ImageViewer(imgs);
        
         image.addActionListener(e -> {
            new CommentListForm(res, id_post).show();
            System.out.println("hello");
        });

        
        //DELETE BUTTON
        Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
//        cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(categorynameTxt,lSupprimer)));

        //CLICK DELETE BUTTON 
        
        lSupprimer.addPointerPressedListener(l -> {
            
             Dialog dig = new Dialog("Delete");
            if(dig.show("Delete","Do You Want To Delete This Post","Cancel","Yes")){
                dig.dispose();
            }else {
                dig.dispose();
                
                //CALLING THE DELETE METHOD FROM SERVICECATEGRY
                if(ServicePost.getInstance().deletePost(post.getId_post())) {
                    new PostListForm(res).show();
                }
            }
           
        });
        
        //UPDATE ICON
        Label lModifer = new Label(" ");
        lModifer.setUIID("NewsTopLine");
        Style modiferStyle = new Style(lModifer.getUnselectedStyle());
        modiferStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modiferStyle);
        lModifer.setIcon(mFontImage);
        lModifer.setTextPosition(LEFT);
        

         lModifer.addPointerPressedListener(l -> {
      
             //System.out.println("Hello Update");
             //new UpdateCategoryForm(res,post).show();
             new UpdatePostForm(res, post).show();
        });

         Label likes = new Label( " Add Comment  ", "NewsBottomLine");
          likes.setTextPosition(RIGHT);
          FontImage.setMaterialIcon(likes, FontImage.MATERIAL_CHAT);
          
          likes.addPointerPressedListener(l -> {
      
             //System.out.println("Hello Update");
             //new UpdateCategoryForm(res,post).show();
             //new AddComment(res, post).show();
             System.out.println("id_post"+id_post);
             new AddComment(res, id_post).show();
            
        });
          
              Label like = new Label( " Show Comment  ", "NewsBottomLine");
          like.setTextPosition(RIGHT);
          FontImage.setMaterialIcon(like, FontImage.MATERIAL_CHAT);
          
          like.addPointerPressedListener(l -> {
      
             //System.out.println("Hello Update");
             //new UpdateCategoryForm(res,post).show();
             //new MapForm();
              System.out.println("byeeeeeeeee");
              new CommentListForm(res, id_post).show();
        });
         
         
        cnt.add(BorderLayout.WEST,BoxLayout.encloseY(
                BoxLayout.encloseY(categorynameTxt,descriptionTxt,imgg,lModifer,lSupprimer,likes,like)));
        add(cnt);
        

    }
    
    
    
 }
    

