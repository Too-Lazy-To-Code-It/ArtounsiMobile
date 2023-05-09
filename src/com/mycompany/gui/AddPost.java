/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.capture.Capture;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.CharArrayReader;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import static com.codename1.io.rest.Rest.post;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
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
import com.mycompany.entites.Category;
import com.mycompany.entites.Post;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.ServicePost;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ext.filechooser.FileChooser;


/**
 * @author amine
 */
public class AddPost extends BaseForm{
    String storageDir = FileSystemStorage.getInstance().getAppHomePath();

    // Define a variable to hold the list of categories
    private ArrayList<Category> categories;
    private String mediaFilename;

    
     Form current;
     public AddPost(Resources res ) {
         super("Newsfeed",BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical
         
        Toolbar tb = new Toolbar(true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add Post/Blog");
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
        RadioButton mesListes = RadioButton.createToggle("My Posts", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Others", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Post", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
            refreshTheme();
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

        
        //
        
        
// TITLE
TextField title_p = new TextField("", "Enter Post Title!!!");
title_p.setUIID("TextFieldBlack");
addStringValue("Post Title",title_p);

// DESCRIPTION
TextField description_p = new TextField("", "Enter Post Description!!!");
description_p.setUIID("TextFieldBlack");
addStringValue("Post Description",description_p);

// Create a list of Post Type
String[] PostType = {"blog", "portfolio"};

// Create a ComboBox to hold the Post Type
ComboBox<String> post_type = new ComboBox<>(PostType);

// Add the ComboBox to the form
addStringValue("Post", post_type);

// Get the list of categories from the ServiceCategory class
ServiceCategory sc = new ServiceCategory();
categories = sc.displayCategory();

// Create an array of category names
String[] categoryNames = new String[categories.size()];
for (int i = 0; i < categories.size(); i++) {
    categoryNames[i] = categories.get(i).getName_category();
}

// Create a ComboBox to hold the categories
ComboBox<String> category_p = new ComboBox<>(categoryNames);

// Add the ComboBox to the form
addStringValue("Category", category_p);




//kinda fih tri9 s7i7
Button btnAdd = new Button("Add");
addStringValue("", btnAdd);

// Initialize the button to display an image
Button photoButton = new Button("Attach Photo");
photoButton.setTextPosition(Label.BOTTOM);
add(photoButton);

// Add a listener to the photo button that allows the user to select an image
photoButton.addActionListener((ActionEvent e) -> {
    if (FileChooser.isAvailable()) {
        FileChooser.setOpenFilesInPlace(true);
        FileChooser.showOpenDialog(".jpg,.jpeg,.png/plain", (ActionListener) (ActionEvent e2) -> {
            if (e2 == null || e2.getSource() == null) {
                add("No file was selected");
                revalidate();
                return;
            }
            String file = (String) e2.getSource();
            String extension = null;
            if (file.lastIndexOf(".") > 0) {
                extension = file.substring(file.lastIndexOf(".") + 1);
                StringBuilder hi = new StringBuilder(file);

                if (file.startsWith("file://")) {
                } else {
                    hi.delete(0, 7);
                }
                int lastIndexPeriod = hi.toString().lastIndexOf(".");
                String ext = hi.toString().substring(lastIndexPeriod);
                String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                try {
                    String namePic = saveFileToDevice(file, ext);
                    System.out.println(namePic);
                    photoButton.setBadgeText(namePic);
                    Image logo = Image.createImage(file).scaledSmallerRatio(256, 256);
                    photoButton.setIcon(logo);
                } catch (IOException ex) {
                }
            }
        });
    }
});

// Add a listener to the "Add" button to create a new Post object and add it to the database
btnAdd.addActionListener((e) -> {
    try {
        if (title_p.getText().equals("") || description_p.getText().equals("")) {
            Dialog.show("Please fill all fields!", "", "OK", null);
        } else {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog iDialog = ip.showInfiniteBlocking();

            Post p = new Post();
            p.setTitle_p(title_p.getText());
            p.setDescription_p(description_p.getText());
            p.setPost_type(post_type.getSelectedItem());
            p.setCategory_p(categories.get(category_p.getSelectedIndex()));

            // Set the path to the image file, if one was selected
            String mediaPath = photoButton.getBadgeText();
if (mediaPath != null && !mediaPath.isEmpty()) {
    p.setMedia(mediaPath);
}

            ServicePost.getInstance().addPost(p);

            iDialog.dispose();
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
});

     }
/**
 * Saves a file to the device's storage directory
 *
 * @param filePath the path to the file to be saved
 * @param extension the file extension
 * @return the name of the saved file
 * @throws IOException if an error occurs while saving the file
 */

     private String saveFileToDevice(String filePath, String extension) throws IOException {
    String newFileName = System.currentTimeMillis() + extension;
    String storageDir = FileSystemStorage.getInstance().getAppHomePath();
    String fileFullPath = storageDir + newFileName;
    Util.copy(FileSystemStorage.getInstance().openInputStream(filePath), FileSystemStorage.getInstance().openOutputStream(fileFullPath));
    return newFileName;
}

    

       private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeee));
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
}