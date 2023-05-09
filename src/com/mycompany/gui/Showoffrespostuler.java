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

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.entites.Offre;
import com.mycompany.service.OffreService;
import java.io.OutputStream;
import java.util.List;


/**
 * The newsfeed form
 *
 * @author Shai Almog
 */
public class Showoffrespostuler extends BaseForm {
    OffreService ts = OffreService.getInstance();

    public Showoffrespostuler(Resources res) {
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
        addTab(swipe, res.getImage("simon-tosovsky-magicpowder-02.jpg"), spacer1, " ", "85 ", "Les offres d'emplois");
      
                
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
        RadioButton featured = RadioButton.createToggle("mes demandes", barGroup);
          featured.addActionListener(e->{
           new Showmesdemandes(res).show();
        });
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Ajouter demande", barGroup);
         RadioButton populars = RadioButton.createToggle("offres ", barGroup);
          popular.addActionListener(e->{
       //new Adddemande(res).show();
        });
        popular.setUIID("SelectBar");
         populars.setUIID("SelectBar");
       populars.addActionListener(e->{
          new Showoffrespostuler(res).show();
        });
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular,populars),
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

        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
          List<Offre> off=ts.fetchoffres();
          for (int i = 0; i < off.size(); i++) {
         
                        addButton(res.getImage("offre.png"),off.get(i),res);
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
    
   private void addButton(Image img, Offre f,Resources res) {
        Container C6 = new Container (new BoxLayout (BoxLayout.Y_AXIS));
         Container C5 = new Container (new BoxLayout (BoxLayout.X_AXIS));
       int height = Display.getInstance().convertToPixels(30.5f);
       int width = Display.getInstance().convertToPixels(18f);
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       Container cnt = BorderLayout.west(image);
       cnt.setLeadComponent(image);
       TextArea ta = new TextArea(f.getTitreoffre());
         TextArea desc = new TextArea(f.getDescriptionoffre());
       ta.setUIID("NewsTopLine");
       ta.setEditable(false);
Label categorie= new Label(f.getCategorieoffre(), "NewsBottomLine");
Label type = new Label (f.getTypeoffre(), "NewsBottomLine");
Label localisation = new Label (f.getLocalisationoffre(), "NewsBottomLine");
       Button postuler = new Button("Postuler");
       postuler.addActionListener(e->{
           String verif=(String)ts.postuler(f.getIdoffre(), 1);
             if (verif.equals("true")) {
               try {
                   Dialog.show("success","votre demande est envoyer","got it ",null);
                   
                   downloadPDF(f.getTitreoffre(),f.getDescriptionoffre(),f.getNickname(),f.getTypeoffre(),f.getLocalisationoffre(),res);
               } catch (Exception ex) {
                   
               }
           } else {  Dialog.show("error","vous avez deja postuler a cette offre" ,"got it ",null);
           
           }});
       categorie.setTextPosition(RIGHT);
      
           FontImage.setMaterialIcon(categorie, FontImage.MATERIAL_FAVORITE);
      
     
       FontImage.setMaterialIcon(categorie, FontImage.MATERIAL_CHAT);
       
       C5.add(postuler);
       cnt.add(BorderLayout.CENTER, 
               BoxLayout.encloseY(
                       ta,desc,
                       BoxLayout.encloseX(categorie,type,localisation)
               ));
        C6.add(cnt);
      C6.add(C5);
      
       add(C6);
       image.addActionListener(e -> ToastBar.showMessage(f.getTitreoffre(), FontImage.MATERIAL_INFO));
   }
    
    private void bindButtonSelection(Button b, Label arrow) {
        b.addActionListener(e -> {
            if(b.isSelected()) {
                updateArrowPosition(b, arrow);
            }
        });
    }
    
    
    private void downloadPDF(String titre,String desc,String nickname,String type,String loc,Resources res) throws Exception {
    // Set the file path
    String filePath = FileSystemStorage.getInstance().getAppHomePath() + "offre.pdf";
    // Create an output stream to write the PDF file
    OutputStream outputStream = FileSystemStorage.getInstance().openOutputStream(filePath);

    // Create a new document
    Document document = new Document();
    // Set the PDF writer to write to the output stream
    PdfWriter.getInstance(document, outputStream);

    // Add metadata to the document
    document.addAuthor("Your Name");
    document.addCreator("Artounsi");
    document.addSubject("Fichier de demande d'emplois");
    document.addTitle("Fichier de demande d'emplois");

    // Set the document margins
    document.setMargins(36, 36, 36, 36);

    // Open the document
    document.open();

    
Paragraph nickparagraph = new Paragraph("Vou avez postuler a l'offre de la societé : " + nickname,
        FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.NORMAL));
nickparagraph.setAlignment(Element.ALIGN_CENTER);

// Create a new cell to hold the paragraph
PdfPCell cell = new PdfPCell(nickparagraph);

// Set the background color of the cell
cell.setBackgroundColor(new BaseColor(153, 50, 204)); // Violet color

// Add the cell to a new table with one column and one row
PdfPTable table = new PdfPTable(1);
table.setWidthPercentage(100);

table.addCell(cell);

// Add the table to the document
document.add(table);

      
    
             

// Add header with company logo and job title
Image logo = res.getImage("offre.PNG");
Paragraph header = new Paragraph();

header.add(new Phrase("Titre de l'offre: \n"+titre, FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.NORMAL)));
document.add(header);
Paragraph description = new Paragraph("Description de l'offre: \n", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.NORMAL));
description.add(new Chunk(desc, FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL)));
document.add(description);
Paragraph qualifications = new Paragraph("Type:\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.NORMAL));
qualifications.add(new Chunk(type, FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL)));
document.add(qualifications);
Paragraph locali = new Paragraph("localisation:\n", FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.NORMAL));
locali.add(new Chunk(loc, FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL)));
document.add(locali);

      
        // Set the font size and style for the response
      

        // Add some space between the questions
        document.add(new Paragraph(" "));
    

    // Close the document
    document.close();

    // Flush and close the output stream
    outputStream.flush();
    outputStream.close();

    if (Dialog.show("PDF Downloaded", "PDF file was saved to " + filePath + ". Do you want to open it?", "Open", "Cancel")) {
        // Open the PDF file
        Display.getInstance().execute(filePath);
    }
}




 
}
