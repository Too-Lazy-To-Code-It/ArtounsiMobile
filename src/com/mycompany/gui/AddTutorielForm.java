/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.MultipartRequest;
import com.mycompany.service.TutorielService;
import com.mycompany.entites.Tutoriel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;

/**
 *
 * @author achref
 */
public class AddTutorielForm extends BaseForm {

    public AddTutorielForm(Resources res) {
        super(new BorderLayout());
        TutorielService ts = TutorielService.getInstance();

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");

        TextField titleTF = new TextField("", "Tutoriel titte", 20, TextField.ANY);
        TextField descriptionTF = new TextField("", "Tutoriel description", 20, TextField.ANY);
        TextField niveauTF = new TextField("", "Tutoriel niveau", 20, TextField.ANY);
        TextField pathimgTF = new TextField("", "Tutoriel path d'imgage", 20, TextField.ANY);
        Label sen = new Label("Selectionner un niveau");
        Label sei = new Label("Selectionner un image");
        Label sec = new Label("Selectionner un categorie");

        Slider stars = createStarRankSlider();

        String[] characters = {"2d", "3d" /* cropped */};
        Picker categorie = new Picker();
        categorie.setStrings(characters);
        categorie.setSelectedString(characters[0]);
        categorie.addActionListener(e -> ToastBar.showMessage("tu as selectionner " + categorie.getSelectedString() + "category", FontImage.MATERIAL_INFO));

        titleTF.setSingleLineTextArea(false);
        descriptionTF.setSingleLineTextArea(false);
        niveauTF.setSingleLineTextArea(false);
        pathimgTF.setSingleLineTextArea(false);

        Button selectImage = new Button("selectionner Un Image");
        selectImage.addActionListener((e) -> {
            Display.getInstance().openGallery((ActionListener) evt -> {
                String filePath = (String) evt.getSource();
                if (filePath != null) {
                    selectImage.setText(filePath);
                }
            }, Display.GALLERY_IMAGE);
        });

        Button submit = new Button("Submit");
        submit.addActionListener((evt) -> {
            if (ts.addTutoriel(new Tutoriel(titleTF.getText(), descriptionTF.getText(), selectImage.getText(), stars.getProgress(), 1, categorie.getText()))) {
                Dialog.show("Success", "Tutoriel ajouté avec succesé", "Got it", null);
                new ShowTutorielsForm(res).showBack();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });
        Container content = BoxLayout.encloseY(
                new Label("Add Tutoriel", "LogoLabel"),
                new FloatingHint(titleTF),
                createLineSeparator(),
                new FloatingHint(descriptionTF),
                createLineSeparator(),
                sei,
                selectImage,
                createLineSeparator(),
                sen,
                createLineSeparator(),
                FlowLayout.encloseCenter(stars),
                sec,
                categorie
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                submit
        ));
    }

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    private Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(5);
        Font fnt = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL).
        derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }
};
