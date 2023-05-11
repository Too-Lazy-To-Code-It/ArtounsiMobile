/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIgrotmot;

import GUIARTISTE.*;

import controller.ModifierOffreController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Category;
import models.demandeTravail;
import models.grosMots;
import models.offreTravail;
import service.CategoryService;
import service.demandeTravailService;
import static service.demandeTravailService.verifdemande;
import service.grosMotsService;
import static service.grosMotsService.verifmot;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class modifiermotController implements Initializable {

    @FXML
    private TextField titreOffre;
   
    @FXML
    private Button modifier;
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }       


public void getid(int i,String titre){
             modifier.setId(Integer.toString(i));
             titreOffre.setText(titre);

}
  

    @FXML
    private void modifierOffre(ActionEvent event) {
          int id= Integer.parseInt(modifier.getId());
         
          grosMots m = new grosMots();
            
          
if(titreOffre.getText().matches("\\d+")) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entre un mot valide");
        alert.showAndWait();}
        
else {
              try {
           
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Dashboard.fxml"));
                  Parent root = loader.load();
                  m.setIdmot(id);
          m.setMot(titreOffre.getText());
    
  grosMotsService motserv = new grosMotsService();
       
       //fromtoken of.setIdStudio(2);
    motserv.modifierOffre(m);
       
            if(verifmot==true) { 
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                Scene scene = new Scene(root,1380,700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);}
                
              } catch (IOException ex) {
                  Logger.getLogger(ModifierOffreController.class.getName()).log(Level.SEVERE, null, ex);
              }
    

}
    }


    
}