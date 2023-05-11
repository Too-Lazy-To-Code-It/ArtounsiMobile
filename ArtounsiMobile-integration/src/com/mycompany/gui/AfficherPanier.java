/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
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
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Lignepanier;
import com.mycompany.entites.Panier;
import com.mycompany.entites.Produits;
import com.mycompany.service.PanierService;
import com.mycompany.service.ProduitsService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aouad
 */
public class AfficherPanier extends BaseForm {

  public  static double Montant_total;
//     public static float getMontant_total() {
//        return Montant_total;
//    }
    
 PanierService pn = PanierService.getInstance();
ProduitsService ps = ProduitsService.getInstance();
    public AfficherPanier(Resources res) {
        
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Panier");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("simon-tosovsky-magicpowder-02.jpg"), spacer1, " ", " ", "Panier");
                
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
        RadioButton all = RadioButton.createToggle("Tous", barGroup);
          all.addActionListener(e->{
            new NewsfeedForm(res).show();
        });
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Ajouter produit ", barGroup);
          featured.addActionListener(e->{
            new AjouterProduitForm(res).show();
        });
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Afficher détails", barGroup);
        featured.addActionListener(e->{
          //  new AfficherDétailsProduit(res,41 ).show();
        });
        popular.setUIID("SelectBar");
        
        RadioButton myFavorite = RadioButton.createToggle("My Favorites", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        //TRYING TO DISPLAY THE POST'S
        //CALLLING DISPLAY METHOD 
        
         double Total = 0;
        
        
ArrayList<Produits> cc =  pn.getPanierElementsProducts();
        ArrayList<Lignepanier> pe =  pn.getPanierElements();
    List<Lignepanier> list = pn.getPanierElements() ;
     int i = 0;
    for (Lignepanier lp : pe) {
               Produits e = cc.get(i);
               
          Container c1 = new Container(BoxLayout.y());
           
           
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       
        Label imageTxt = new Label("Image Produit : "+e.getImage(),"NewsTopLine2" );
////    ImageViewer imgg = null;
////    EncodedImage enc = null;
////    Image imgs;
//    ImageViewer imgv;
//    String url = p.getImage();



EncodedImage enc = null;
        Image imgs;
        ImageViewer imgv;
        String url = "http://localhost/img/"+e.getImage();
    try {
        //Image image = Image.createImage("http://localhost/img/" + t.getPathimg());
            enc = EncodedImage.create("/load.png");
        System.out.println(e.getImage());
    } catch (IOException ex) {

    }
     System.out.println(url);
     imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
     ImageViewer img = null;
     img = new ImageViewer(imgs);
     System.out.println("noooooooo"+e.getNom());
       
       TextArea ta = new TextArea("Nom du produit "+e.getNom());
       ta.setUIID("NewsTopLine1");
       ta.setEditable(false);
       System.out.println("immmm"+e.getImage());
        

         TextArea p = new TextArea("Prix du produit "+String.valueOf(e.getPrix())+" DT");
         p.setUIID("NewsSecond");
         p.setEditable(false);
         System.out.println("pppppprrr"+String.valueOf(e.getPrix()));
         double tauxTVA = 0.19;
         double valTVA= (e.getPrix())*0.19;
         double prixAvecTva=(e.getPrix())+valTVA;
         Total += prixAvecTva ;
         
         System.out.println("toooottt   "+ Total);
       
         Button Delete = new Button("Delete");
          FontImage.setMaterialIcon(Delete, FontImage.MATERIAL_DELETE);
         c1.add(img);
         c1.add(ta);
         c1.add(p);
         c1.add(Delete);
         i++;
         add(createLineSeparator());
        add(LayeredLayout.encloseIn(c1));
     
          Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog.show("Confirmation", "Voulez vous Supprimé ce produit de panier !!! "
                               , "ok","Annuler");
                
               boolean test = pn.supprimerProduitduPanier((lp));
               if(test == true){
                   Dialog.show("Succés", "Produit est Supprimé avec succés !!! "
                               , "ok","");
               }else{
                   Dialog.show("Echec", "Echec de Suppression !!! "
                               , "ok","");
               }
               new  AfficherPanier (res).show();
             }
        });
        
     
        
       //end mta3 el for 
          }
    
    
         Container c2 = new Container(BoxLayout.y());
         Label Totale = new Label("Montant total  : " + Total + " DT" ,"largeLabel");
          Montant_total= Total;
           System.out.println("hhhhhhhhhhhhhhhhhh"+Montant_total);
           
         Totale.getAllStyles().setFgColor(0x7453fc);
         Button Payer = new Button("Payer");
         Payer.addActionListener(e->{
            new PaymentForm (res).show();
             // prendre le montant total à payer
           
        });
         
         
         
         
         c2.add(Totale);
         c2.add(Payer);
        add(LayeredLayout.encloseIn(c2));
    
    
    
    
    
    
    }
    
    private void updateArrowPosition(Button b, Label arrow) {
        arrow.getUnselectedStyle().setMargin(LEFT, b.getX() + b.getWidth() / 2 - arrow.getWidth() / 2);
        arrow.getParent().repaint();
        
        
    }
    
    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
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

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
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
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
  
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
}
