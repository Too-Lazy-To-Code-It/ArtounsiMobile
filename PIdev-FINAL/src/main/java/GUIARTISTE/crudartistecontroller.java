/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIARTISTE;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.NewFXMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class crudartistecontroller implements Initializable {

    @FXML
    private AnchorPane CRUD;
    @FXML
    private AnchorPane afficheroffre;
    @FXML
    private AnchorPane CRUD1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
            Pane pane = FXMLLoader.load(getClass().getResource("/GUIARTISTE/afficherdemandes.fxml"));
         afficheroffre.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

   

   
  
    private void ajout(ActionEvent event) {
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUIARTISTE/ajouterdemande.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

   
    private void afficherMesOffres(ActionEvent event) {
         try {
            Pane pane = FXMLLoader.load(getClass().getResource("/GUIARTISTE/mesdemandes.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void ajout(MouseDragEvent event) {
    }

  
   
    
    
}
