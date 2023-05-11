/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Category;
import models.Challenge;
import interfaces.CategoryInterface;
import interfaces.CategoryInterface;
import interfaces.ChallengeInterface;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import models.AllUsers;
import service.CategoryService;
import service.ChallengeService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_ADD_ChallengeController implements Initializable {
    
    CategoryInterface ci = new CategoryService();
    ChallengeInterface chi= new ChallengeService();
    Challenge challenge = new Challenge();
    String src;
    String dest;
    
    
    @FXML
    private TextField titre;
    @FXML
    private DatePicker date_challenge;
    @FXML
    private TextArea descrption;
    @FXML
    private Label image_name;
    @FXML
    private ComboBox<String> categories;
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private TextField niveau;
    
    private int num;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list.removeAll(list);
        ci.fetchCategories().stream().forEach(e->list.add(e.getName_category()));
        categories.getItems().addAll(list);
    }    
    
    @FXML
    private void importImage(ActionEvent event) throws IOException {
           FileChooser fc = new FileChooser();
           FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG Files","*.png");
           FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG Files","*.jpg");

           fc.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);
           
           File selectedFile = fc.showOpenDialog(null);
           
           if(selectedFile != null) {
               src = selectedFile.getPath();
               dest = "C:\\xampp\\htdocs\\img\\"+selectedFile.getName();
               
               image_name.setText(selectedFile.getName());
               challenge.setPathIMG(selectedFile.getName());
           } else {
               System.err.println("file is not valid");
           }
    }

    @FXML
    private void addChallenge(ActionEvent event) throws IOException {
        if(titre.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs");
            alert.show();
            return;
        }
        else if(!niveau.getText().matches("[0-9]")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs"+ "");
            alert.show();
            return;
        }
        else{
            
            challenge.setTitle(titre.getText());
            challenge.setDescription(descrption.getText());
            challenge.setCategorie(ci.fetchCategoryByNom(categories.getValue()));
            challenge.setDate_C(date_challenge.getValue().toString());
            challenge.setCreator(new AllUsers());
            challenge.setNiveau(Integer.parseInt(niveau.getText()));
            chi.addChallenge(challenge);
            Path tmp = Files.move(Paths.get(src), Paths.get(dest)); 
            
            }
    }

    void setNum(int size) {
        this.num=size;
    }
    

}
/*if (name.getText().length()==0||
                 date.getText().length()==0||
                 start_hour.getText().length()==0||
                 finish_hour.getText().length()==0||
                 capacity.getText().length()==0||
                 description.getText().length()==0||
                 capacity.getText().length()==0||
                 image.getText().length()==0||
                 location.getText().length()==0){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs"+ "");
            alert.show();
            return;
    }*/