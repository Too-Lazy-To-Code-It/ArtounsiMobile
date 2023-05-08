/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Category;
import com.mycompany.entites.Post;
import com.mycompany.service.ServiceCategory;
import com.mycompany.service.ServicePost;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author amine
 */
public class AddPost extends BaseForm {
    String storageDir = FileSystemStorage.getInstance().getAppHomePath();

    // Define a variable to hold the list of categories
    private ArrayList<Category> categories;
    private String mediaFilename;


    Form current;

    public AddPost(Resources res) {
        super("Newsfeed", BoxLayout.y()); //herigate men Newsfeed w l formulaire vertical

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add Post/Blog");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("simon-tosovsky-magicpowder-02.jpg"), "", "", res);

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
        addStringValue("Post Title", title_p);

// DESCRIPTION
        TextField description_p = new TextField("", "Enter Post Description!!!");
        description_p.setUIID("TextFieldBlack");
        addStringValue("Post Description", description_p);

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

// Select Image button
        Button btnSelectImage = new Button("Select Image");
        btnSelectImage.addActionListener((evt) -> {
            Display.getInstance().openGallery((ActionListener) (ActionEvent ev) -> {
                if (ev != null && ev.getSource() != null) {
                    String filePath = (String) ev.getSource();

                    String mime = "image/jpeg";
                    String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
                    String newFileName = System.currentTimeMillis() + "." + extension;

                    Post post = new Post();
                    post.setMedia(newFileName);

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

                        Dialog.show("Success", "Image uploaded", "OK", null);
                        dlg.dispose(); // dismiss the loading dialog
                    });
                }
            }, Display.GALLERY_IMAGE);
        });


        add(btnSelectImage);

// Add button
        Button btnAdd = new Button("Add");
        addStringValue("", btnAdd);

// on click event of the Add button 
        btnAdd.addActionListener((e) -> {
            try {
                if (title_p.getText().equals("") || description_p.getText().equals("")) {
                    Dialog.show("Please fill all fields!", "", "OK", null);
                } else {
                    InfiniteProgress ip = new InfiniteProgress(); //Loading after insert data
                    final Dialog iDialog = ip.showInfiniteBlocking();

                    Post p = new Post();
                    p.setTitle_p(title_p.getText());
                    p.setDescription_p(description_p.getText());
                    p.setPost_type(post_type.getSelectedItem());
                    p.setCategory_p(categories.get(category_p.getSelectedIndex()));

                    String mediaPath = storageDir + p.getMedia();
                    if (mediaPath != null && !mediaPath.isEmpty()) {
                        p.setPath(mediaPath);
                    }

                    ServicePost.getInstance().addPost(p);

                    iDialog.dispose();//To Cancel Loading After The Adding

//                new PostListForm(res).show();
//
//                refreshTheme();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }


    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }


        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");


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

        swipe.addTab("", res.getImage("simon-tosovsky-magicpowder-02.jpg"), page1);


    }


    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }
}
