/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.*;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Offre;
import com.mycompany.service.OffreService;

/**
 * @author khaledguedria
 */
public class AddForm extends BaseForm {

    //var
    OffreService ts = OffreService.getInstance();

    public AddForm(Resources res) {

        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Newsfeed");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

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

        Component.setSameSize(radioContainer, spacer1, spacer2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("Tous", barGroup);
        all.addActionListener(e -> {
            new Showoffres(res).show();
        });
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("mes offres", barGroup);
        featured.addActionListener(e -> {
            new Showmesoffres(res).show();
        });
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("Ajouter offre", barGroup);
        RadioButton populars = RadioButton.createToggle("         ", barGroup);
        popular.addActionListener(e -> {
            new AddForm(res).show();
        });
        popular.setUIID("SelectBar");

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, populars),
                FlowLayout.encloseBottom(arrow)
        ));

        all.setSelected(true);
        arrow.setVisible(false);

        //Widgets
        TextField titreTF = new TextField("", "titre", 40, TextField.ANY);
        titreTF.setUIID("NewsTopLine");


        TextField descTF = new TextField("", "description");
        descTF.setUIID("NewsTopLine");

        TextField categorieTF = new TextField("", "categorieTF");
        categorieTF.setUIID("NewsTopLine");


        // Create a list of Post Type
        // String[] categorie= {"1", "2","3"};
        String[] type = {"Contrat", "Freelance", "Permanante"};
        String[] loc = {"Tunis", "Algerie", "France"};

// Create a ComboBox to hold the Post Type
        // ComboBox<String> categorieTF = new ComboBox<>(categorie);

        Picker typeoffreTF = new Picker();
        typeoffreTF.setUIID("NewsTopLine");
        typeoffreTF.setStrings(type);
        typeoffreTF.setSelectedString(type[0]);
        typeoffreTF.addActionListener(e -> ToastBar.showMessage("You picked " + typeoffreTF.getSelectedString(), FontImage.MATERIAL_INFO));
        Picker localisationTF = new Picker();
        localisationTF.setUIID("NewsTopLine");
        localisationTF.setStrings(loc);
        localisationTF.setSelectedString(loc[0]);
        localisationTF.addActionListener(e -> ToastBar.showMessage("You picked " + localisationTF.getSelectedString(), FontImage.MATERIAL_INFO));

        Button submitBtn = new Button("Ajouter");

        //actions
        submitBtn.addActionListener((evt) -> {
            if (ts.addoffre(new Offre(titreTF.getText(), descTF.getText(), typeoffreTF.getText(), localisationTF.getText(), Integer.parseInt(categorieTF.getText())))) {
                Dialog.show("Success", "offre ajouter avec succes", "Got it", null);
                new Showmesoffres(res).show();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });

        //end
        this.addAll(titreTF, descTF, categorieTF, typeoffreTF, localisationTF, submitBtn);

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
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
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
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
