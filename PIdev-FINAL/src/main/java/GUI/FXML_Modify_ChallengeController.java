/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUImenuprincipale.menuprincipale;
import controller.MesOffesitemsController;
import models.Category;
import models.Challenge;
import interfaces.CategoryInterface;
import interfaces.CategoryInterface;
import interfaces.ChallengeInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AllUsers;
import service.CategoryService;
import service.ChallengeService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_Modify_ChallengeController implements Initializable {
    
    CategoryInterface ci = new CategoryService();
    ChallengeInterface chi= new ChallengeService();
    Challenge challenge = new Challenge();
    
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
    @FXML
    private Button galeriebutton;
    @FXML
    private Button forstudio;
    @FXML
    private Button challengebutton;
    @FXML
    private Button tutobutn;
    @FXML
    private Button shopbutton;
    @FXML
    private Button panierbutton;
    @FXML
    private Button deconnexion;
    @FXML
    private Button profile;
    @FXML
    private Circle profilephoto;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list.removeAll(list);
        ci.fetchCategories().stream().forEach(e->list.add(e.getName_category()));
        System.out.println(chi.fetchChallenges());
        categories.getItems().addAll(list);
    }    
    
    @FXML
    private void importImage(ActionEvent event) {
           FileChooser fc = new FileChooser();
           fc.getExtensionFilters().addAll(
           new FileChooser.ExtensionFilter("Image Files","*.png"));
           File selectedFile = fc.showOpenDialog(null);
           if(selectedFile != null) {
               image_name.setText(selectedFile.getName());
               challenge.setPathIMG(selectedFile.getAbsolutePath());
           } else {
               System.err.println("file is not valid");
           }
    }

    public void getChallenge(Challenge c){
        titre.setText(c.getTitle());
        descrption.setText(c.getDescription());
        categories.setValue(c.getCategorie().getName_category());
        niveau.setText(String.valueOf(c.getNiveau()));
        image_name.setText(c.getPathIMG());
        
        int year = Integer.parseInt(c.getDate_C().substring(0, 4));
        int mois = Integer.parseInt(c.getDate_C().substring(5, 7));
        int jour = Integer.parseInt(c.getDate_C().substring(7, 9));
        
        date_challenge.setValue(LocalDate.parse(c.getDate_C()));
        
        challenge.setID_Challenge(c.getID_Challenge());
        challenge.setPathIMG(c.getPathIMG());
        //challenge.setCategorie(c.getCategorie());
    }

    @FXML
    private void modifyChallenge(ActionEvent event) throws IOException {
        if(titre.getText().length()==0||descrption.getText().length()==0||date_challenge.getValue().toString().length()==0||niveau.getText().length()==0){
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
        }else{
        challenge.setTitle(titre.getText());
        challenge.setDescription(descrption.getText());
        challenge.setCategorie(ci.fetchCategoryByNom(categories.getValue().toString()));
        challenge.setDate_C(date_challenge.getValue().toString());
        challenge.setCreator(new AllUsers());
        challenge.setNiveau(Integer.parseInt(niveau.getText()));
        chi.modifyChallenge(challenge);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUImenuprincipale/menuprincipale.fxml"));
            Parent uuView = loader.load();
                        Scene scene = new Scene(uuView, 1380, 700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
           
//       
//          catch (IOException ex) {
//          Logger.getLogger(FXML1Controller.class.getName()).log(Level.SEVERE, null, ex);
//     }
    }

   

    
}
}