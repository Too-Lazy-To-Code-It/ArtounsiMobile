/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIARTISTE;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Category;
import models.offreTravail;
import service.CategoryService;
import service.offreTravailService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import models.Logged;
import models.demandeTravail;
import service.demandeTravailService;
import static service.demandeTravailService.verifajoutdemande;


/**
 * FXML Controller class
 *
 * @author nour2
 */
public class AjouterdemandeController implements Initializable {
    demandeTravailService offs= new   demandeTravailService();
    demandeTravail of = new demandeTravail();
CategoryService c = new CategoryService();
    @FXML
    private ChoiceBox<Category> listeCategorie= new ChoiceBox<>();;
   
    @FXML
    private TextField titreOffre;
    @FXML
    private TextArea descriptionOffre;
 static FileChooser fileChooser = new FileChooser();
  

    static File file;
    @FXML
    private Button cv;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loaddata();
    }    
     public void loaddata(){
       listeCategorie.getItems().addAll(c.fetchCategories());
      
    }

    @FXML
    private void ajouterOffre(ActionEvent event) {
     

if (titreOffre.getText().matches("\\d+")) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entre un titre valide");
        alert.showAndWait();}
        else if (descriptionOffre.getText().matches("\\d+"))
                { Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entrer une dscription valide");
        alert.showAndWait();}
else  if (file == null) {
           
            Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller choisir un pdf");
        alert.showAndWait();
        }else{
    // Le contenu du TextField n'est pas un entier
String fileName1 = file.getName();
            try {
                // Copy the file to the XAMPP htdocs directory
                Path sourcePath = file.toPath();
                Path targetPath = Paths.get("C:/xampp2/htdocs/uploads/" + fileName1);
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
            of.setPdf(fileName1);
        of.setTitreDemande(titreOffre.getText());
       
        of.setDescriptionDemande(descriptionOffre.getText());
        of.setCategorieDemande(listeCategorie.getValue());
       
       /*getfrom token */ of.setIdArtiste(Logged.get_instance().getUser().getID_User());
      
           offs.addDemande(of);
            
   
    if(   verifajoutdemande==true){
String myVariable = "";
descriptionOffre.setText(myVariable);
	titreOffre.setText(myVariable);
   listeCategorie.setValue(null);
   fileName1="";
    }
  
           
    }
    }

    @FXML
    private void ajoutcv(ActionEvent event) {
       
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("pdf files (*.pdf)", "*.pdf");
       
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);


    
    }
}
