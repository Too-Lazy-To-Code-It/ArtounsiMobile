/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import models.Logged;
import models.offreTravail;
import models.offreTravailarchive;
import service.offreTravailArchiveService;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class MesOffresArchiveController implements Initializable {

    offreTravailArchiveService of = new offreTravailArchiveService ();
    private List<offreTravailarchive> mesoffres;
    @FXML
    private GridPane citiesGril;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mesoffres=new ArrayList<>(of.fetchOffresarchiverPerIdDate(Logged.get_instance().getUser().getID_User()));
       int column=0;
       int row = 1 ;
       for(offreTravailarchive f : mesoffres)
       {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUI/mesOffresArchiveItems.fxml"));
               Pane pane = fxmlLoader.load();
             MesOffresArchiveItemsController ac = fxmlLoader.getController();
               ac.loaddata(f);
               if(column==2){column=0; ++row ;}
               citiesGril.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
           } catch (IOException ex) {
               Logger.getLogger( MesOffresController.class.getName()).log(Level.SEVERE, null, ex);
           }

       }
     
    }    
     
    
}
