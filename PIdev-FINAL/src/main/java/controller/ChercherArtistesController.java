/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Category;
import models.Logged;
import models.demandeTravail;
import models.offreTravail;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import service.CategoryService;
import service.demandeTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class ChercherArtistesController implements Initializable {
    demandeTravailService dm = new  demandeTravailService();
    private List<demandeTravail> demandes=new ArrayList<>(dm.fetchDemandesPerDate());
         CategoryService categorie=new  CategoryService();
      private List<Category> categories=new ArrayList<>(categorie.fetchCategories());
    @FXML
    private TextField resultatsearch;
    @FXML
    private GridPane gridchercher;
    private CheckBox checkboxcateg;
    private AnchorPane anchorpa;
    @FXML
    private VBox vbox;
    
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private AnchorPane anchorpane;
  
Set<String>words = new HashSet<>(  );
private AutoCompletionBinding<String> autocomplete;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         resultatsearch.setStyle("-fx-text-fill: white;");
          resultatsearch.setText("");
          
  words.addAll(dm.fetchDemandesPerDate().stream().map(e -> e.getTitreDemande()).collect(Collectors.toList()));  
  autocomplete=TextFields.bindAutoCompletion(resultatsearch, words );
      resultatsearch.setOnKeyPressed((KeyEvent e)->{
          switch(e.getCode())
          {case ENTER:
              
              learnWord( resultatsearch.getText());
              break;
          default:break;  
          }
      });
       int column=0;
       int row = 1 ;
       for(demandeTravail d :  demandes)
       {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUI/designresulterrecherche.fxml"));
               Pane pane = fxmlLoader.load();
             DesignresulterRechercheController ac = fxmlLoader.getController();
               ac.loaddata(d);
               if(column==1){column=0; ++row ;}
             gridchercher.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
          

           } catch (IOException ex) {
               Logger.getLogger( ChercherArtistesController.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
               Logger.getLogger(ChercherArtistesController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }

         for(Category c: categories)
         {  
             checkboxcateg=new CheckBox();
             checkboxcateg.setText(c.getName_category());
             checkboxcateg.setId(Integer.toString(c.getId_Category()));
           
        vbox.getChildren().add(checkboxcateg);
            
   ;};

  
   checkboxcateg.setOnAction(event -> {
        demandes=dm.fetchDemandesPerCategorieDate(demandes, categorie.fetchCategoryByNom(checkboxcateg.getText()) );
        } );
        
    }; 
       
   


    @FXML

       
      
        
      private void chercher(ActionEvent event) throws SQLException {
    gridchercher.getChildren().clear();
    int column = 0;
    int row = 1;

    String searchTerm = resultatsearch.getText();
    if (searchTerm.isEmpty()) {
        // If the search term is empty, retrieve all data from the database
        demandes = dm.fetchDemandesPerDate();
    } else {
        // Otherwise, search the database using the search term
        demandes = dm.chercherDemande(resultatsearch.getText());
    }

    for (demandeTravail d : demandes) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/GUI/designresulterrecherche.fxml"));
            Pane pane = fxmlLoader.load();
            DesignresulterRechercheController ac = fxmlLoader.getController();
            ac.loaddata(d);
            if (column == 1) {
                column = 0;
                ++row;
            }
            gridchercher.add(pane, column++, row);
            GridPane.setMargin(pane, new Insets(20));
        } catch (IOException ex) {
            Logger.getLogger(ChercherArtistesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


      
    }
       private void learnWord(String text) {
       words.add(text);
       if( autocomplete!=null)
       {autocomplete.dispose();
       autocomplete.setPrefWidth(500);
       }
         autocomplete=TextFields.bindAutoCompletion(resultatsearch, words );
    }


    @FXML
    private void demandesimilaires(ActionEvent event) throws SQLException {
            gridchercher.getChildren().clear();
        int column=0;
       int row = 1 ;
       for(demandeTravail d :  demandes)
       {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUI/designresulterrecherche.fxml"));
               Pane pane = fxmlLoader.load();
            DesignresulterRechercheController ac = fxmlLoader.getController();
               ac.loaddata(d);
               if(column==1){column=0; ++row ;}
             gridchercher.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
               
            

           } catch (IOException ex) {
               Logger.getLogger(ChercherArtistesController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       demandes=dm.offresimilairaunedemande(Logged.get_instance().getUser().getID_User());
    }
    
    
    
}
