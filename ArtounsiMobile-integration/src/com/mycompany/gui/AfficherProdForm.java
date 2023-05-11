/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;

import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import com.mycompany.entites.Produits;
import com.mycompany.service.PanierService;
import com.mycompany.service.ProduitsService;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;


/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class AfficherProdForm extends BaseForm {
 ProduitsService ps = ProduitsService.getInstance();
 PanierService pn = PanierService.getInstance();
    public AfficherProdForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("SHOP");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("simon-tosovsky-magicpowder-02.jpg"), spacer1, " ", "85 ", "");
                
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
            new AfficherProdForm(res).show();
        });
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("Ajouter produit ", barGroup);
          featured.addActionListener(e->{
            new AjouterProduitForm(res).show();
        });
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Afficher détails", barGroup);
        featured.addActionListener(e->{
//            new AfficherDétailsProduit(res,41 ).show();
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
  
    ArrayList<Produits>List = ps.fetchProduits();
       for(Produits p: List){
           
           System.out.println("idp"+p.getIdproduit());
         Label imageTxt = new Label("Image Produit : "+p.getImage(),"NewsTopLine2" );
////    ImageViewer imgg = null;
////    EncodedImage enc = null;
////    Image imgs;
//    ImageViewer imgv;
//    String url = p.getImage();



EncodedImage enc = null;
        Image imgs;
        ImageViewer imgv;
        String url = "http://localhost/img/"+p.getImage();
    try {
      
            enc = EncodedImage.create("/load.png");
        System.out.println(p.getImage());
    } catch (IOException ex) {

    }
 System.out.println(url);
    imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
   ImageViewer img = null;
img = new ImageViewer(imgs);
    addButton(img.getImage(), p,res);
        }
        
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
    
       private void addButton(Image img,Produits p, Resources res) {
           
       Container C5 = new Container (new BoxLayout (BoxLayout.X_AXIS));
       Container C6 = new Container (new BoxLayout (BoxLayout.Y_AXIS));
           
           
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);

       TextArea Nom = new TextArea(p.getNom());
       Nom.setUIID("NewsTopLine");
       
       Nom.setEditable(false);
       TextArea Description = new TextArea(p.getDescription());
       Description.setUIID("NewsTopLine");
       Description.setEditable(false);
       Label prix = new Label(p.getPrix() + " DT  ", "NewsBottomLine");
       prix.setTextPosition(RIGHT);
       
      Button featured = new Button("Afficher détails");
      featured.addActionListener(e -> {
      // new AfficherDétailsProduit(res, p.getIdproduit()).show();
     
    });
      
      Button modif = new Button("Modifier");
           
        modif.addActionListener(e->{
            new ModifierProduitForm (p,res).show();
        });
      
      Button delete = new Button("Supprimer");
       FontImage.setMaterialIcon(delete, FontImage.MATERIAL_DELETE);
        delete.addActionListener(e->{
            if (ps.supprimerProduit(p)) {
                Dialog.show("Success", "Product deleted successfully", "Got it", null);
                new AfficherProdForm(res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });
         Button AjouterProdAuPanier = new Button("Ajouter au Panier");

        AjouterProdAuPanier.addActionListener(e->{
            if (pn.AjouterProdAuPanier(p)) {
                Dialog.show("Success", "Produit ajouté au panier avec succès", "Got it", null);
                new AfficherProdForm(res).show();
            } else {
                Dialog.show("Failed", "Produit existe déjà dans le panier", "Got it", null);
            }
        });
        
        
        
        
        
       C5.add(modif);
       C5.add(delete);
       C5.add(AjouterProdAuPanier);
       
//       String dateLimiteStr = p.getDateajout().toString();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                        Date dateLimite = dateFormat.parse(dateLimiteStr);
//                        p.setDateajout(dateLimite);
//       
       
     
//       Label dd = new Label(p.getDateajout() +  "NewsBottomLine");
          
//       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//       String dateAjout = p.getDateajout(); 
//       String dateString = dateAjout != null ? dateFormat.format(dateAjout) : "";
//       TextArea dd = new TextArea(p.getDateajout());
////       TextArea dd = new TextArea(p.getDateajout());
//       dd.setUIID("NewsTopLine");
//       dd.setEditable(false);

//SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//String inputDateStr = p.getDateajout();
//String outputDateStr = "";
//try {
//    Date date = inputFormat.parse(inputDateStr);
//    outputDateStr = outputFormat.format(date);
//} catch (ParseException e) {
//    e.printStackTrace();
//}
//TextArea dd = new TextArea(outputDateStr);







//         System.out.println("DDDDDAAAAATTTTEEE"+p.getDateajout() );
//          System.out.println("Nom   "+p.getNom());
//       // Parse dateajout
//       String dateajoutStr = p.getDateajout().toString();
//       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
//       TextArea Dateajout = new TextArea(dateajoutStr );
//      
//       Label categorie= new Label(p.getCategory().getName_category(), "NewsBottomLine");
    
    FontImage.setMaterialIcon(prix, FontImage.MATERIAL_CHAT);
       
 
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       Nom,Description,
//                       BoxLayout.encloseX(categorie,prix)
                       BoxLayout.encloseX(prix)
                       
               ));
      C6.add(cnt);
      C6.add(C5);
      
       add(C6);
 
   }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
    
    
}
