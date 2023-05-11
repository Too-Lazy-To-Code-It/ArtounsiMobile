/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Category;
import models.Tutoriel;
import models.Video;
import interfaces.CategoryInterface;
import interfaces.TutorielInterface;
import interfaces.VideoInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.CategoryService;
import service.TutorielService;
import service.VideoService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_Modify_TutorielController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    CategoryInterface ci = new CategoryService();
    TutorielInterface ti = new TutorielService();
    VideoInterface vi = new VideoService();
    @FXML
    private ImageView tutoriel_img;
    @FXML
    private GridPane video_grid;
    @FXML
    private HBox addButton;
    @FXML
    private TextField tutoriel_title;
    @FXML
    private TextField tutoriel_level;
    @FXML
    private TextArea tutoriel_description;
    @FXML
    private Button image_name;
    
    Tutoriel tutoriel = new Tutoriel();
    @FXML
    private ComboBox<String> comboBox;
    private String src;
    private String dest;
    private boolean img_imported = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list.removeAll(list);
        ci.fetchCategories().stream().forEach(e->list.add(e.getName_category()));
        comboBox.getItems().addAll(list);
        
    }    

    @FXML
    private void addChallenge(MouseEvent event) {
    }

    @FXML
    private void importImage(ActionEvent event) {
           FileChooser fc = new FileChooser();
           FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG Files","*.png");
           FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG Files","*.jpg");

           fc.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);
           
           File selectedFile = fc.showOpenDialog(null);
           if(selectedFile != null) {
               src = selectedFile.getPath();
               dest = "C:\\xampp\\htdocs\\img\\"+selectedFile.getName();
               
               image_name.setText(selectedFile.getName());
               tutoriel.setPathImg(selectedFile.getName());
               img_imported = true;
           } else {
               System.err.println("file is not valid");
           }
    }

    void getTutoriel(Tutoriel t) {
        System.out.println(t);
        tutoriel_title.setText(t.getTitle());
        tutoriel_level.setText(String.valueOf(t.getNiveau()));
        tutoriel_description.setText(t.getDescription());
        comboBox.setValue(t.getCategorie().getName_category());
        image_name.setText(t.getPathImg());
        //date_challenge.setValue(new Date());
        File file = new File("C:\\xampp\\htdocs\\img\\"+tutoriel.getPathImg());
        Image img = new Image(file.toURI().toString());
        tutoriel_img.setImage(img);
        
        tutoriel.setID_Tutoriel(t.getID_Tutoriel());
        tutoriel.setPathImg(t.getPathImg());
        tutoriel.setCreator(t.getCreator());
        tutoriel.setCategorie(t.getCategorie());
        afficher_Tutoriel();
    }
    
    private void afficher_Tutoriel() {
        List<Video> videos = vi.fetchVideosByTutoriel(tutoriel.getID_Tutoriel());
            System.out.println(videos);
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<videos.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("VideoItem.fxml"));
            
            AnchorPane item = fxmlLoader.load();
           
            
            VideoItemController videoItemController = fxmlLoader.getController();
            videoItemController.setData(videos.get(i),tutoriel);
            
            if(columns == 2){
                columns = 0 ;
                ++rows;
            }
            
            video_grid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void modifyTutoriel(ActionEvent event) throws IOException {
        if(tutoriel_title.getText().length()==0||tutoriel_description.getText().length()==0||tutoriel_level.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs");
            alert.show();
            return;
        }
        else if(!tutoriel_level.getText().matches("[0-9]")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs"+ "");
            alert.show();
            return;
        }
            else {
        tutoriel.setTitle(tutoriel_title.getText());
        tutoriel.setCategorie(ci.fetchCategoryByNom(comboBox.getValue().toString()));
        tutoriel.setDescription(tutoriel_description.getText());
        tutoriel.setNiveau(Integer.parseInt(tutoriel_level.getText()));
        ti.ModifyTutoriel(tutoriel);
        Path tmp;
        if(img_imported)
            tmp = Files.copy(Paths.get(src), Paths.get(dest)); 
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Fetch_Tutoriel.fxml"));
        Parent view_2=loader.load();
        FXML_Fetch_TutorielController Fetch_TutorielController=loader.getController();
        Fetch_TutorielController.setTutorial(tutoriel);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    
    
        
    
    
}}

