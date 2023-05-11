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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Video;
import com.mycompany.service.VideoService;

/**
 *
 * @author achref
 */
public class AddVideoForm extends BaseForm {

    public AddVideoForm(Resources res,Tutoriel t) {           
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
              /*  
        TextField username = new TextField("", "Username", 20, TextField.ANY);
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "Confirm Password", 20, TextField.PASSWORD);*/
        VideoService vs = VideoService.getInstance();
        TextField titleTF = new TextField("", "Video title", 20, TextField.ANY);
        TextField descriptionTF = new TextField("", "Tutoriel description", 20, TextField.ANY);
        TextField pathimgTF = new TextField("", "Tutoriel pathimg", 20, TextField.ANY);
        TextField pathvidTF = new TextField("", "Tutoriel pathvid", 20, TextField.ANY);
                
        String[] characters = {"2d", "3d" /* cropped */};
        Picker categorie = new Picker();
        categorie.setStrings(characters);
        categorie.setSelectedString(characters[0]);
        categorie.addActionListener(e -> ToastBar.showMessage("You picked " + categorie.getSelectedString()+ "category", FontImage.MATERIAL_INFO));
        
        
        titleTF.setSingleLineTextArea(false);
        descriptionTF.setSingleLineTextArea(false);
        pathimgTF.setSingleLineTextArea(false);
        pathimgTF.setSingleLineTextArea(false);
        
        Button selectImage = new Button("select Image");
        selectImage.addActionListener((e) -> {
            Display.getInstance().openGallery((ActionListener) evt -> {
                String filePath = (String) evt.getSource();
                if (filePath != null) {
                    pathimgTF.setText(filePath);
                }
            }, Display.GALLERY_IMAGE);
        });
        
        Button selectVideo = new Button("select Video");
        selectVideo.addActionListener((e) -> {
            Display.getInstance().openGallery((ActionListener) evt -> {
                String filePath = (String) evt.getSource();
                if (filePath != null) {
                    pathvidTF.setText(filePath);
                }
            }, Display.GALLERY_IMAGE);
        });
        
        Button submit = new Button("Submit");
        submit.addActionListener((evt) -> {            
            if (vs.addVideo(new Video(titleTF.getText(), descriptionTF.getText(), pathimgTF.getText(), pathvidTF.getText()), t.getId_tutoriel())) {
                Dialog.show("Success", "Video Inserted successfully", "Got it", null);
                new ShowTutorielForm(t,res).showBack();
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
                selectImage,
                new FloatingHint(pathimgTF),
                createLineSeparator(),
                selectVideo,
                new FloatingHint(pathvidTF),
                createLineSeparator(),
                categorie
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                submit
        ));
    }
}
