/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Interfaces.ProduitInterface;
import models.Category;
import models.Produits;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.CategoryService;
import service.ProduitService;

/**
 * FXML Controller class
 *
 * @author aouad
 */
public class FXML_ModifierProduitController implements Initializable {
       ProduitInterface ps= new ProduitService();
     CategoryService cs= new CategoryService() {};
     Produits p= new Produits();
  
 

    @FXML
    private Button modifier;
    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField prixText;
    private TextField image;
    @FXML
    private Button retour;
    @FXML
    private ChoiceBox<Category> listeCateg = new ChoiceBox<>();
     ObservableList listCat = FXCollections.observableArrayList();
    @FXML
    private ImageView imgV;
    @FXML
    private Label nomimage;
    @FXML
    private AnchorPane anchore;
    @FXML
    private Button Annuler;
    @FXML
    private TextField RecNom;
    @FXML
    private TextField RecCat;
    @FXML
    private TextField Recdescription;
    @FXML
    private TextField RcPrix;
    
    Produits produit=new Produits();
    @FXML
    private ImageView imgVNew;
    @FXML
    private Label nomimageNew;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      // TODO

        loaddata();
    }
    public void loaddata(){
        listeCateg.getItems().addAll(cs.fetchCategories());

    }
     

    
       
       
       
     public void getProd(Produits p) {
        this.produit = p;
        p = ps.readById(p.getIdproduit());
        RecNom.setText(p.getNom());
        Recdescription.setText(p.getDescription());
        RcPrix.setText(Double.toString(p.getPrix()));
       
        // sélectionne la catégorie associée à ce produit
        RecCat.setText(p.getCategorieProduit().getName_category());
        nomimage.setText(p.getImage());
        File file = new File("C:\\xampp\\htdocs\\img\\"+p.getImage());
        Image img = new Image(file.toURI().toString());
        imgV.setImage(img);
    }
    
    
     
//     
//    @FXML
//    private void modifier(ActionEvent event) {
//        int id= Integer.parseInt(modifier.getId());
//     if (!nom.getText().isEmpty() && !description.getText().isEmpty() && listeCateg.getValue() != null && !image.getText().isEmpty() && !qtDispo.getText().isEmpty() && !prixText.getText().isEmpty()) {
//        p.setIdProduit(idProduit);
//        p.setNom(nom.getText());
//        
//        if (listeCateg.getValue() != null) {
//            p.getCategorieProduit().setNomCategorie(listeCateg.getValue());
//        } else {
//            // Handle null value
//        }
//        p.setIdProduit(id);
//        p.setDescription(description.getText());
//        p.setImage(image.getText());
//        p.setQuantiteDispo(Integer.parseInt(qtDispo.getText()));
//        p.setPrix(Double.parseDouble(prixText.getText()));
//        ps.modifierProduit(p);
//    } else {
//        System.out.println("Veuillez remplir tous les champs.");
//    }
//}

     @FXML
    private void modifier(ActionEvent event) throws IOException {
     try{
       if (nom.getText().isEmpty() || description.getText().isEmpty() || listeCateg.getValue() == null || prixText.getText().isEmpty()|| nomimageNew.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur de saisie !");
        alert.setContentText("Veuillez remplir tous les champs");
        alert.show();
        return;
       } else{
      
        produit.setNom(nom.getText());
        produit.setDescription(description.getText());
        produit.setCategorieProduit(listeCateg.getValue());
        produit.setPrix(Double.parseDouble(prixText.getText()));
        produit.setImage(nomimageNew.getText());
  
        File file = new File("C:\\xampp\\htdocs\\img\\"+p.getImage());
        Image img = new Image(file.toURI().toString());
        imgVNew.setImage(img);
        ps.modifierProduit(produit);
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXML_AfficherProduit.fxml"));
        Parent view_2=loader.load();
        Scene scene = new Scene(view_2);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();}}
 
          catch (IOException ex) {
          Logger.getLogger(FXML_ModifierProduitController.class.getName()).log(Level.SEVERE, null, ex);
     }
    }





    @FXML
    private void retourAccueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML_Acceuil.fxml"));
        Parent root = loader.load();
        retour.getScene().setRoot(root);
    }

    
    
    
//    
//  void setProd(Produits p) {
//    nom.setText(p.getNom());
//    description.setText(p.getDescription());
//    prixText.setText(String.valueOf(p.getPrix()));
//    qtDispo.setText(String.valueOf(p.getQuantiteDispo()));
//    image.setText(p.getImage());
//
////     Trouver l'objet Categorie correspondant au nom de la catégorie de Produits
//    Categories selectedCateg = null;
//    for (Categories c : cs.fetchCategories()) {
//        if (c.getNomCategorie().equals(p.getCategorieProduit().getNomCategorie())) {
//            selectedCateg = c;
//            break;
//        }
//    }
//////
//////     Définir la valeur de listeCateg en utilisant l'objet Categorie correspondant
////    if (selectedCateg != null) {
////        listeCateg.setValue(selectedCateg);
////    }
//
//    this.p = p;
//
//}

     @FXML
    private void ImporterImage(ActionEvent event) {
        FileChooser open=new FileChooser();
        Stage stage=(Stage) anchore.getScene().getWindow();
        File file=open.showOpenDialog(stage);
        if(file!=null){
            String filename=file.getName();
            nomimageNew.setText(filename);
            Image img=new Image(file.toURI().toString());
            imgVNew.setImage(img);
    }
    }

    @FXML
    private void Annuler(ActionEvent event) {
         Stage stage = (Stage) Annuler.getScene().getWindow();
         stage.close();
    }

   
    
   

}

