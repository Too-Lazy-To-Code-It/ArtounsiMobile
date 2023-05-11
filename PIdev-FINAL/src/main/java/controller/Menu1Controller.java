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
import javafx.NewFXMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.demandeTravail;
import models.offreTravail;
import service.demandeTravailService;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class Menu1Controller implements Initializable {

    private GridPane citiesGrid;
 offreTravailService of = new offreTravailService ();
 private List<offreTravail> listeOffres; 
    @FXML
public Pane CRUD;
    @FXML
    private Button showall;
    @FXML
    private Button popup;
 offreTravailService off=new offreTravailService();
    @FXML
    private Button artistes;
    @FXML
    private Button mesoffres;
    @FXML
    private Button ajouter;
    @FXML
    private Button archive;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      try {//get the css for popup 
          
//design my popup
          Popup popups = new Popup();
             Label plabel = new Label();
          
             popups.setX(150);
             popups.setY(90);
            
             //insert elements in popup 
              List<String> f = new ArrayList<>(off.affichernotifications(2));
              //getting listview style
               ListView<String> listView = new ListView<>();
               listView.getStylesheets().add("/css/menustyle.css");
listView.setPrefWidth(400);
               listView.setCellFactory(param -> new ListCell<String>() {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item);
            setGraphic(null); // Clear any previously set graphic
            setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color:#1C233D;   -fx-text-fill: white;"); // Customize the style
        }
    }
});
             ObservableList<String> items = FXCollections.observableArrayList(f);
             listView.setItems(items);
               popups.getContent().add(listView);
                popups.getContent().add(   plabel);
                    popup.setOnAction(event -> {
                        if(!popups.isShowing()){
        popups.show(popup.getScene().getWindow());}
                        else{popups.hide();}
    });
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/CRUD.fxml"));
            CRUD.getChildren().add(pane);
            
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }    


    @FXML
    private void showall(ActionEvent event) {
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/CRUD.fxml"));
            CRUD.getChildren().add(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gotomenu(ActionEvent event) {
        
        
         try {
          
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUImenuprincipale/menuprincipale.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root,1380,700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void afficherrecherche(ActionEvent event) {
           List<demandeTravail> demandes;
           try {
               demandeTravailService dm = new  demandeTravailService();
         demandes=new ArrayList<>(dm.fetchDemandesPerDate());
         
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/ChercherArtistes.fxml"));
            CRUD.getChildren().add(pane);
            
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
    private void archiveaction(ActionEvent event) {
         try {
            Pane pane = FXMLLoader.load(getClass().getResource("/GUI/mesOffresArchive.fxml"));
            CRUD.getChildren().clear();
           CRUD.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
