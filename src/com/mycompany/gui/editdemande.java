/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Category;
import com.mycompany.entites.demande;
import com.mycompany.entites.grosmot;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.demandeservice;
import com.mycompany.service.grosmotservice;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author nour2
 */
public class editdemande  extends BaseForm {
    
      private ArrayList<Category> categories;
    demandeservice ts = demandeservice.getInstance();
     grosmotservice grosmotser = grosmotservice.getInstance();
   List<grosmot> listBadWords =grosmotser.fetchmot();
    public editdemande(demande t,Resources res) {
      
      
        //CUSTOM
    super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
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
           new AddForm(res).show();
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
        TextField titreTF = new TextField(t.getTitreDemande() + "", "titre demande", 20, TextField.ANY);
            titreTF.setUIID("NewsTopLine");
       TextField pdf = new TextField(t.getPdf() + "", "pdf", 20, TextField.ANY);
            titreTF.setUIID("NewsTopLine");
        //getStyle().setBgColor(0xa469f0);
        //getStyle().setBgTransparency(255);
        TextField descriptionTF = new TextField(t.getDescriptionDemande() + "", "description demande", 20, TextField.ANY);
        descriptionTF.setUIID("NewsTopLine");
          
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
            


       

        Button submitBtn = new Button("modifier");
        

        //actions
        submitBtn.addActionListener((evt) -> {
             if (titreTF.getText().isEmpty() || descriptionTF.getText().isEmpty() ) {
    Dialog.show("Error", "Veuillez remplir tous les champs!", "OK", null);
            } else
if(grosmotser.grosMots(titreTF.getText())){ Dialog.show("Failed", "ATTENTION !! Vous avez écrit un gros mot dans ele titre.C'est un avertissement ! Priére d'avoir un peu de respect ! ", "Got it", null);}
else 
    if(grosmotser.grosMots(descriptionTF.getText())){ Dialog.show("Failed", "ATTENTION !! Vous avez écrit un gros mot dans la description.C'est un avertissement ! Priére d'avoir un peu de respect ! ", "Got it", null);}

     else  
             if (ts.Editdemande(new demande(t.getIdDemande(),titreTF.getText(),descriptionTF.getText(),pdf.getText() ,categorieTF.getSelectedIndex()))) {
                Dialog.show("Success", "offre modifier avec succes", "Got it", null);
                 new Showmesoffres(res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });

        //end
        this.addAll(titreTF, descriptionTF,categorieTF, submitBtn);

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