/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.mycompany.service.TutorielService;
import com.mycompany.entites.Tutoriel;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Category;
import com.mycompany.service.ServiceCategory;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 *
 * @author achref
 */
public class ModifyTutorielForm extends BaseForm {

    public ModifyTutorielForm(Tutoriel t, Resources res) {
                super(new BorderLayout());
        TutorielService ts = TutorielService.getInstance();

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

        TextField titleTF = new TextField(t.getTitle() + "", "Tutoriel title", 20, TextField.ANY);
        TextField descriptionTF = new TextField(t.getDescription() + "", "Tutoriel description", 20, TextField.ANY);
        TextField niveauTF = new TextField(t.getNiveau() + "", "Tutoriel niveau", 20, TextField.ANY);
        TextField pathimgTF = new TextField(t.getPathimg() + "", "Tutoriel pathimg", 20, TextField.ANY);

        Label sen = new Label("Selectionner un niveau");
        Label sei = new Label("Selectionner un image");
        Label sec = new Label("Selectionner un categorie");
        
        Slider stars = createStarRankSlider();
        stars.setProgress(t.getNiveau());
                ArrayList<Category> categories;
        ServiceCategory sc = new ServiceCategory();
        categories = (ArrayList<Category>) sc.fetchCategory();

// Create an array of category names
        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
        categoryNames[i] = categories.get(i).getName_category();
}
        Picker categorie = new Picker();
        categorie.setStrings(categoryNames);
        categorie.setSelectedString(categoryNames[0]);
        categorie.addActionListener(e -> ToastBar.showMessage("You picked " + categorie.getSelectedString() + "category", FontImage.MATERIAL_INFO));

        titleTF.setSingleLineTextArea(false);
        descriptionTF.setSingleLineTextArea(false);
        niveauTF.setSingleLineTextArea(false);
        pathimgTF.setSingleLineTextArea(false);

        Button selectImage = new Button(t.getPathimg()+"");
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
            if (ts.updatetutoriel(new Tutoriel(t.getId_tutoriel(), titleTF.getText(), descriptionTF.getText(), selectImage.getText(), stars.getProgress(), 1, categorie.getText()))) {
                Dialog.show("Success", "Tutoriel Modified successfully", "Got it", null);
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
        
        /*TutorielService ts = TutorielService.getInstance();
        setUIID("LoginForm");
        //CUSTOM
        this.setLayout(BoxLayout.y());
        this.setTitle("Modify Tutoriel");
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            new ShowTutorielsForm(res).show();
        });

        //Widgets
        TextField titleTF = new TextField(t.getTitle() + "", "Tutoriel title", 20, TextField.ANY);
        TextField descriptionTF = new TextField(t.getDescription() + "", "Tutoriel description", 20, TextField.ANY);
        TextField niveauTF = new TextField(t.getNiveau() + "", "Tutoriel niveau", 20, TextField.ANY);
        TextField pathimgTF = new TextField(t.getPathimg() + "", "Tutoriel pathimg", 20, TextField.ANY);

        Button selectImageBtn = new Button("Choisir une image");
        
        CheckBox multiSelect = new CheckBox("Multi-select");
        Button photoButton = new Button("Ajouter une Image");
        //Actions
        Button selectImage = new Button("select Image");
        selectImage.setUIID("WalkthruTab1");
        selectImage.addActionListener((e) -> {
            Display.getInstance().openGallery((ActionListener) evt -> {
                String filePath = (String) evt.getSource();
                if (filePath != null) {
                    pathimgTF.setText(filePath);
                }
            }, Display.GALLERY_IMAGE);
        });
        
        selectImageBtn.addActionListener(e -> {
            /*Display.getInstance().openGallery((ActionListener) evt -> {
                System.out.println(evt.getSource());
                String filePath = (String) evt.getSource();
                if (filePath != null) {
                    pathimgTF.setText(filePath);
                }
            }, Display.GALLERY_IMAGE);*/

/*            if (FileChooser.isAvailable()) {

                FileChooser.setOpenFilesInPlace(true);

                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg,.jpeg,.png/plain", (ActionEvent e2) -> {
                    if (e2 == null || e2.getSource() == null) {
                        add("No file was selected");
                        revalidate();
                        return;
                    }
                    if (multiSelect.isSelected()) {
                        String[] paths = (String[]) e2.getSource();
                        for (String path : paths) {
                            CN.execute(path);
                        }
                        return;
                    }
                    //menna 7atta el photoButton.setBadgeText(namePic); 9a3din ntal3ou fi esm taswira el 7a9ani 
                    String file = (String) e2.getSource();

                    System.out.println(file);
                    String extension = null;
                    if (file.lastIndexOf(".") > 0) {
                        extension = file.substring(file.lastIndexOf(".") + 1);
                        StringBuilder hi = new StringBuilder(file);

                        if (file.startsWith("file://")) {
                        } else {
                            hi.delete(0, 7);
                        }
                        int lastIndexPeriod = hi.toString().lastIndexOf(".");
                        Log.p(hi.toString());
                        String ext = hi.toString().substring(lastIndexPeriod);
                        String hmore = hi.toString().substring(0, lastIndexPeriod - 1);
                        try {
                            String namePic = saveFileToDevice(file, ext);
                            System.out.println(namePic);
                            //cr.setFilename("file",namePic);//any unique name you want
                            // photoButton.getIcon().setImageName(namePic);
                            photoButton.setBadgeText(namePic); //7atit l'esm fl badge ta3 button bch najjam nesta3mlou el berra ml cha9lalla hadhi
                        } catch (IOException ex) {
                        } catch (URISyntaxException ex) {
                        }
                    }
                    if (file == null) {
                        add("No file was selected");
                        revalidate();
                    } else {
                        Image logo;
                        try {
                            logo = Image.createImage(file).scaledSmallerRatio(256, 256);
                            photoButton.setIcon(logo); //lenna bch tatla3lk taswira 9bal ma ta3ml submit
                            //lenna nbdaw fl enregistrement ta3 taswira
                            String imageFile = FileSystemStorage.getInstance().getAppHomePath() + photoButton.getBadgeText();
                            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                                //System.out.println(imageFile);
                                ImageIO.getImageIO().save(logo, os, ImageIO.FORMAT_PNG, 1);//3mlna save lel image fi wost file system storage
                                System.out.println();
                            } catch (IOException err) {
                            }
                        } catch (IOException ex) {
                        }
                        revalidate();

                    }
                });
            }
        });


        String[] characters = {"2d", "3d" /* cropped */};
     /*   Picker categorie = new Picker();
        categorie.setStrings(characters);
        categorie.setSelectedString(characters[0]);
        categorie.addActionListener(e -> ToastBar.showMessage("You picked " + categorie.getSelectedString(), FontImage.MATERIAL_INFO));

        Button submitBtn = new Button("Modify");
        submitBtn.setUIID("LoginButton");

        //actions
        submitBtn.addActionListener((evt) -> {
            if (ts.updatetutoriel(new Tutoriel(t.getId_tutoriel(), titleTF.getText(), descriptionTF.getText(), pathimgTF.getText(), Integer.parseInt(niveauTF.getText()), 1, categorie.getText()))) {
                Dialog.show("Success", "Tutoriel" + t.getId_tutoriel() + "Modified successfully", "Got it", null);
                new ShowTutorielsForm(res).showBack();
            } else {
                Dialog.show("Failed", "Something Wrong! Try again", "Got it", null);
            }
        });

        //end
        this.addAll(titleTF, descriptionTF, niveauTF, pathimgTF, selectImage, categorie, submitBtn);

    }

    protected String saveFileToDevice(String hi, String ext) throws IOException, URISyntaxException {
        ConnectionRequest connectionRequest;
        connectionRequest = new ConnectionRequest();
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            //connectionRequest.setUrl("http://localhost/testUploader/insert.php?path=" + path);
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
        }
        return "null";
    }*/
    
    

