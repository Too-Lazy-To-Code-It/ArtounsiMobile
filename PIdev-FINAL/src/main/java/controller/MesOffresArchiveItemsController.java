/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.offreTravail;
import models.offreTravailarchive;
import service.offreTravailArchiveService;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class MesOffresArchiveItemsController implements Initializable {

    @FXML
    private Label titredemande;
    @FXML
    private Label description;
    @FXML
    private Button recuperer;
    @FXML
    private Button supprime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 public void loaddata(offreTravailarchive f){
       titredemande.setText(f.getTitreOffre());
       
       description.setText(f.getDescriptionOffre());
     
       recuperer.setId(Integer.toString(f.getIdOffre()));   
      supprime.setId(Integer.toString(f.getIdOffre()));   }
 offreTravailArchiveService offrearchive=new offreTravailArchiveService ();
    @FXML
    private void recupereraction(ActionEvent event) {
       int id= Integer.parseInt(recuperer.getId());
         offreTravailarchive of = new offreTravailarchive();
      of=offrearchive.fetchOffrearchiveParId(id);
        offrearchive.recupererOffre(of);
      System.out.println();
    }
    
    
    

    @FXML
    private void supprimeraction(ActionEvent event) {
        int id= Integer.parseInt(supprime.getId());
         offreTravailarchive of = new offreTravailarchive();
      of=(offreTravailarchive) offrearchive.fetchOffrearchiveParId(id);
        offrearchive.SupprimerDefinitivement(of);
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Alert");
alert.setHeaderText(null);
alert.setContentText("cette offre est supprimer, veuiller refresh your page");

alert.show();
    }
    
}
