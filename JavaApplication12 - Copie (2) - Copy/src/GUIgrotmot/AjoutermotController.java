/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIgrotmot;

import GUIARTISTE.*;
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
import models.grosMots;
import service.demandeTravailService;
import service.grosMotsService;


/**
 * FXML Controller class
 *
 * @author nour2
 */
public class AjoutermotController implements Initializable {
  
    @FXML
    private TextField titreOffre;
   
grosMots mot = new grosMots();
  grosMotsService ajoutmot= new grosMotsService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
     

    @FXML
    private void ajouterOffre(ActionEvent event) {
     

if (titreOffre.getText().matches("\\d+")) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entre un titre valide");
        alert.showAndWait();}
       else{
    // Le contenu du TextField n'est pas un entier

            mot.setMot(titreOffre.getText());
      
      
          ajoutmot.ajoutGrosMot(mot);
            
   
    
String myVariable = "";

	titreOffre.setText(myVariable);

           
    }
    }

  
}
