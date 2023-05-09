/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;


import com.codename1.components.ImageViewer;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;

import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;

import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
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

import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;

import com.codename1.ui.util.Resources;

import com.mycompany.entites.Category;
import com.mycompany.entites.Produits;
import com.mycompany.service.ProduitsService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;


/**
 *
 * @author aouad
 */
public class ModifierProduitForm extends Form{

    //var
    ProduitsService ps = ProduitsService.getInstance();
    
    public ModifierProduitForm(Produits p, Resources res) {
   
        
           
        //CUSTOM
    super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        
     
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("simon-tosovsky-magicpowder-02.jpg"), spacer1, " ", "85 ", "Mes offres d'emplois");
      
                
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
        
         RadioButton popular = RadioButton.createToggle("Ajouter produit", barGroup);
          RadioButton populars = RadioButton.createToggle("         ", barGroup);
          popular.addActionListener(e->{
           new AjouterProduitForm(res).show();
        });
        popular.setUIID("SelectBar");
       
       
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, popular,populars),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);

        //Widgets
        TextField nomTF = new TextField(p.getNom() + "", "Nom du produit", 20, TextField.ANY);
             nomTF .setUIID("NewsTopLine");
      
        TextField descriptionTF= new TextField(p.getDescription() + "", "Description du produit", 20, TextField.ANY);
        descriptionTF.setUIID("NewsTopLine");
          TextField prixTF= new TextField(p.getPrix() + "", "Description du produit", 20, TextField.ANY);
        prixTF.setUIID("NewsTopLine");
//       TextField categorieTF = new TextField(String.valueOf(p.getCategory()), "categorieTF", 20, TextField.ANY);
//       categorieTF.setUIID("NewsTopLine");

       ///
         System.out.println("idp"+p.getIdproduit());
         Label imageTxt = new Label("Image Produit : "+p.getImage(),"NewsTopLine2" );
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
  img.getImage();
///
      Category[] categories = new Category[] {
            new Category(1,"2D"),
            new Category(2,"3D")
        };  


  ComboBox<Category> categoryCombo = new ComboBox<>("Catégorie du produit");
      
        for (Category category : categories) {
            categoryCombo.addItem(category);
        }
        
           TextField imageTF = new TextField(p.getImage(), "categorieTF", 20, TextField.ANY);
           imageTF .setUIID("NewsTopLine");

          Button selectImageBtn = new Button("Choisir une image");
         selectImageBtn.setUIID("NewsTopLine");
     
        prixTF.setUIID("NewsTopLine");
        Button submitBtn = new Button("modifier");
         //Actions
        selectImageBtn.addActionListener(e -> {
            Display.getInstance().openGallery((ActionListener) evt -> {
                String filePath = (String) evt.getSource();
                if (filePath != null) {
                    imageTF.setText(filePath);
                }
            }, Display.GALLERY_IMAGE);
        });

         //actions
        submitBtn.addActionListener(e -> {
    Category selectedCategory = categoryCombo.getSelectedItem();
   
    String imagePath = imageTF.getText();
    String imageName = new File(imagePath).getName();
    Produits produit = new Produits(nomTF.getText(), descriptionTF.getText(), imageName, selectedCategory, Double.parseDouble(prixTF.getText()), new Date());
    if (ps.modifierProduit(produit)){
        Dialog.show("Success", "Product Inserted successfully", "Got it", null);
    } else {
        Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
    }
});

        //end
        this.addAll(nomTF, descriptionTF, imageTF, selectImageBtn, categoryCombo, prixTF, submitBtn);


    }

  private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        com.codename1.ui.plaf.Style heartStyle = new com.codename1.ui.plaf.Style(likes.getUnselectedStyle());
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
        image.setBackgroundType(com.codename1.ui.plaf.Style.BACKGROUND_IMAGE_SCALED_FILL);
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
    
    
}
