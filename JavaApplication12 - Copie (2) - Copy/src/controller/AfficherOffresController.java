/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.offreTravail;

import service.offreTravailService;
public class AfficherOffresController implements Initializable {
    offreTravailService of = new offreTravailService ();
    @FXML
    private ListView<offreTravail> listeOffres;
    @FXML
    private AnchorPane affichescene;
   
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      loaddata();
    }    
    public void loaddata(){
        listeOffres.getItems().addAll(of.fetchOffresPerDate());
    }


    private void ajouteroffre(ActionEvent event) {
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/chercherOffre.fxml"));
         affichescene.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
   
}
