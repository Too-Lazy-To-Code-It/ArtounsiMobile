/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;

import com.codename1.ui.ComboBox;

import com.codename1.ui.TextField;


import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;

import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;

import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import com.mycompany.entites.demande;
import com.mycompany.service.demandeservice;
import java.io.IOException;
import java.util.List;

import com.mycompany.entites.Category;
import com.mycompany.entites.grosmot;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.grosmotservice;
import java.util.ArrayList;
/**
 *
 * @author khaledguedria
 */
public class Adddemande extends BaseForm {

    //var
   demandeservice ts = demandeservice.getInstance();
    grosmotservice grosmotser = grosmotservice.getInstance();
   List<grosmot> listBadWords =grosmotser.fetchmot();
    Boolean verif = false ;
      private ArrayList<Category> categories;
    public Adddemande(Resources res) {

        super("Ajouter demande", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter demande");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        tb.addSearchCommand(e -> {});
        
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        Label spacer2 = new Label();
        addTab(swipe, res.getImage("simon-tosovsky-magicpowder-02.jpg"), spacer1, " ", " ", "ajouter demande");
      
                
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
            new Showdemandes(res).show();
        });
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("mesdemandes", barGroup);
          featured.addActionListener(e->{
            new Showmesdemandes(res).show();
        });
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Ajouter demande", barGroup);
          RadioButton populars = RadioButton.createToggle("         ", barGroup);
          popular.addActionListener(e->{
           new Adddemande(res).show();
        });
        popular.setUIID("SelectBar");
       
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular,populars),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);

        //Widgets
        TextField titreTF = new TextField("", "titre",40, TextField.ANY);
            titreTF.setUIID("NewsTopLine");


        TextField descTF = new TextField("", "description");
           descTF .setUIID("NewsTopLine");
      
       
         
            // Get the list of categories from the ServiceCategory class
ServiceCategory sc = new ServiceCategory();
categories = (ArrayList<Category>) sc.fetchCategory();

// Create an array of category names
String[] categoryNames = new String[categories.size()];
for (int i = 0; i < categories.size(); i++) {
    categoryNames[i] = categories.get(i).getName_category();
}

// Create a ComboBox to hold the categories
ComboBox<String> categorieTF = new ComboBox<>(categoryNames);
            






          Button pdf = new Button("Select pdf");
pdf.addActionListener((evt) -> {
    Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
        if (ev != null && ev.getSource() != null) {
            String filePath = (String) ev.getSource();

            String mime = "pdf";
            String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
            String newFileName = System.currentTimeMillis() + "." + extension;

           demande f = new demande();
            f.setPdf(newFileName);

            InfiniteProgress prog = new InfiniteProgress();
            Dialog dlg = prog.showInifiniteBlocking();

            // Upload the file in a background thread
            Display.getInstance().callSerially(() -> {
                // Save the file in the device's storage directory
                String storageDir = FileSystemStorage.getInstance().getAppHomePath();
                String fileFullPath = storageDir + newFileName;
                try {
                    Util.copy(FileSystemStorage.getInstance().openInputStream(filePath), FileSystemStorage.getInstance().openOutputStream(fileFullPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Dialog.show("Success", "pdf uploaded", "OK", null);
                dlg.dispose(); // dismiss the loading dialog
            });
        }
    }, Display.GALLERY_ALL);
});

          
          
      
          pdf.setUIID("NewsTopLine");
         
         // Create a list of Post Type
        // String[] categorie= {"1", "2","3"};

        Button submitBtn = new Button("Ajouter");

        //actions
        submitBtn.addActionListener((evt) -> {
            if (titreTF.getText().isEmpty() || descTF.getText().isEmpty() ) {
    Dialog.show("Error", "Veuillez remplir tous les champs!", "OK", null);
            } else
if(grosmotser.grosMots(titreTF.getText())){ Dialog.show("Failed", "ATTENTION !! Vous avez écrit un gros mot dans ele titre.C'est un avertissement ! Priére d'avoir un peu de respect ! ", "Got it", null);}
else 
    if(grosmotser.grosMots(descTF.getText())){ Dialog.show("Failed", "ATTENTION !! Vous avez écrit un gros mot dans la description.C'est un avertissement ! Priére d'avoir un peu de respect ! ", "Got it", null);}

     else  
            if (ts.adddemande(new demande(titreTF.getText(),descTF.getText(),pdf.getText(), categorieTF.getSelectedIndex()))) {
                Dialog.show("Success", "demande ajouter avec succes", "Got it", null);
                 new Showmesdemandes(res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });

        //end
        this.addAll(titreTF, descTF,pdf,categorieTF, submitBtn);

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
}
