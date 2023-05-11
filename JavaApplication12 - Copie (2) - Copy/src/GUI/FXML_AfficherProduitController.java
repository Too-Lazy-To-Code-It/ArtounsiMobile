/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import models.Category;
import models.Produits;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import service.ProduitService;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.CategoryService;
import service.LignePanierService;
/**
 * FXML Controller class
 *
 * @author aouad
 */
public class FXML_AfficherProduitController implements Initializable {
      ProduitService ps= new ProduitService();
       CategoryService cs= new CategoryService();
     private ListView<List<Produits>> myListView;
     ObservableList<Produits> list = FXCollections.observableArrayList();
    
  
    @FXML
    private Button ajouter;
    private Button Retour;
    @FXML
    private TextField textchercher;

   
    private Label imgPrix;
    private ImageView imgView;
    @FXML
    private GridPane grid;
    private Label imgNom;

    private Button modif;
   
    Produits p;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ComboBox<?> categBox;
      ObservableList listCat = FXCollections.observableArrayList();
    @FXML
    private ImageView zoomImage;
    @FXML
    private VBox VboxImg;
 

    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
          VboxImg.setPrefHeight(390); // Remplacez la valeur par celle que vous souhaitez
          VboxImg.setMaxHeight(390);
        
          List<Category> categories = cs .fetchCategories();
        
        if (categories != null) {

            
         cs.fetchCategories().stream().forEach(e->listCat.add(e.getName_category()));
         
         
        categBox.getItems().addAll(listCat);
       
        
//     ListView <Produits> myListVie=(ListView <Produits>) ps.fetchProduits();

//        ps.fetchProduits().stream().forEach(a->list.add(a));
//        list2.setItems(list);
        list.addAll(ps.fetchProduits());
        Produits prod = new Produits();
        
        int column = 0;
        int row = 1;
          try {
            for (int i = 0; i < list.size(); i++) {
//                 FXMLLoader fxmlLoader = new FXMLLoader();
//               fxmlLoader.setLocation(getClass().getResource("/GUI/mesOffesitems.fxml"));
//               Pane pane = fxmlLoader.load();
//               MesOffesitemsController ac = fxmlLoader.getController();
//               ac.loaddata(f);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("FXML_Prod2.fxml"));
                anchorPane = fxmlLoader.load();

                FXML_Prod2Controller ProdController = fxmlLoader.getController();
                Produits prod3 = list.get(i);
                ProdController.setData(prod3);
                ProdController.setZoomImage(zoomImage);
          if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
        }

}           catch (IOException ex) {
                Logger.getLogger(FXML_AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }}

    }
    @FXML
    private void GotoAjouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_AjouterProduit.fxml"));
        Parent root = loader.load();
        ajouter.getScene().setRoot(root);
    }

    private void GotoAccueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Acceuil.fxml"));
        Parent root = loader.load();
        Retour.getScene().setRoot(root);
    }

      @FXML
    private void chercher() {
        grid.getChildren().clear();
        String nomProduit = textchercher.getText();
  
        list = ps.chercherProduitParNom(nomProduit);
        int column=0;
       int row = 1 ;
       for (int i = 0; i < list.size(); i++)
       {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("FXML_Prod2.fxml"));
               anchorPane = fxmlLoader.load();
               FXML_Prod2Controller ProdController = fxmlLoader.getController();
                Produits prod3 = list.get(i);
                ProdController.setData(prod3);
             if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
     
           } catch (IOException ex) {
               Logger.getLogger( FXML_AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
 
    
    }





    @FXML
    private void filtrer(ActionEvent event) {
         grid.getChildren().clear(); 
         String nomCategorie = (String) categBox.getValue(); // récupérer la catégorie sélectionnée
    
    list = ps.chercherProduitParCateg(nomCategorie); // filtrer les produits
    int column=0;
    int row = 1;
    for (int i = 0; i < list.size(); i++) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXML_Prod2.fxml"));
            anchorPane = fxmlLoader.load();
            FXML_Prod2Controller ProdController = fxmlLoader.getController();
            Produits prod3 = list.get(i);
            ProdController.setData(prod3);
            if (column == 3) {
                column = 0;
                row++;
            }
            grid.add(anchorPane, column++, row);
        } catch (IOException ex) {
            Logger.getLogger( FXML_AfficherProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    }



    }

  
