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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class CRUDController implements Initializable {

    @FXML
    private AnchorPane CRUD;
   
    @FXML
    private Button mesoffres;
    @FXML
    private Button ajouter;
    @FXML
    private Button archive;
    @FXML
    private AnchorPane afficheroffre1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       try {
            Pane pane = FXMLLoader.load(getClass().getResource("/GUI/afficher.fxml"));
         afficheroffre1.getChildren().setAll(pane);
      /*  Image img = new Image("/img/plus.PNG") ;
      circle.setFill(new ImagePattern(img));
        Image img2 = new Image("/img/archive.PNG") ;
      circle1.setFill(new ImagePattern(img2));
        Image img3 = new Image("/img/work.PNG") ;
      circle2.setFill(new ImagePattern(img3));*/
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

   

   
    @FXML
    private void ajout(ActionEvent event) {
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/ajouterOffre.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @FXML
    private void afficherMesOffres(ActionEvent event) {
         try {
          /*  Pane pane = FXMLLoader.load(getClass().getResource("/GUI/mesOffres.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);*/
      
          Pane pane = FXMLLoader.load(getClass().getResource("/GUI/mesOffres.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void archiveaction(ActionEvent event) {
         try {
            Pane pane = FXMLLoader.load(getClass().getResource("/GUI/mesOffresArchive.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajout(MouseDragEvent event) {
    }

    
    
}
