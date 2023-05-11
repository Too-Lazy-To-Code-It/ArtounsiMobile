/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.LignePanier;
import models.Panier;
import models.Produits;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.LignePanierService;
import service.PanierService;
import service.ProduitService;

/**
 * FXML Controller class
 *
 * @author aouad
 */
public class FXML_Prod2Controller implements Initializable {
ProduitService ps= new ProduitService();
PanierService pn= new PanierService();
LignePanierService lp= new LignePanierService();
 LignePanier lignePanier = new LignePanier();
Panier pan= new Panier ();
    Produits p = new Produits();
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button modif;
    @FXML
    private Button supp;
    @FXML
    private ImageView imgcard;
    @FXML
    private Label nomCard;
    @FXML
    private Label prixCard;
    @FXML
    private Label cat;
    @FXML
    private Label Descp;
    @FXML
    private Label date;
    @FXML
    private Button AjoutPan;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
      private Produits prod;
      private ImageView zoomImage;
      
  public FXML_Prod2Controller() {};

    public FXML_Prod2Controller(ImageView zoomImage) {
        this.zoomImage = zoomImage;
    }
    public void setZoomImage(ImageView zoomImage) {
    this.zoomImage = zoomImage;
}
    
    int idPanier=8;

    public void setData(Produits prod) throws MalformedURLException {
        this.prod = prod;
        nomCard.setText(prod.getNom());
        Descp.setText(prod.getDescription());
        cat.setText(prod.getCategorieProduit().getName_category());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateAsString = format.format(prod.getDateajout());
        date.setText(dateAsString);
        
        prixCard.setText(javafx.NewFXMain.CURRENCY + prod.getPrix());
        File file = new File("C:\\xampp\\htdocs\\img\\"+prod.getImage());
//        System.out.println("fprod"+file);
        Image img = new Image(file.toURI().toString());
//        System.out.println("nmgProd"+img);
        imgcard.setImage(img);
        
        //zoom image 
        
        // Add a click event handler to the product image to display it in the zoomImage view
    imgcard.setOnMouseClicked(event -> {
        // Set the zoomImage view to display the clicked product image
        zoomImage.setImage(img);
        
        // Set the size of the zoomImage view
        zoomImage.setFitWidth(390);
        zoomImage.setFitHeight(390);
    });
        
        
        
        //bouttons  
        
        supp.setId(Integer.toString(prod.getIdproduit()));
        modif.setId(Integer.toString(prod.getIdproduit()));
        AjoutPan.setId(Integer.toString(prod.getIdproduit()));
        
        
        
        
        
  }
    
    @FXML
    private void suppProd(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer ce produit ?");
      
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
        int id= Integer.parseInt(supp.getId());
        ps.spprimerProduit(id);
        
        System.out.println("Produit supprimé avec succés!");
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Success");
        alert2.setHeaderText(null);
        alert2.setContentText("Produit supprimé avec succés!");
        alert2.showAndWait();
       
      } else {
        System.out.println("Le produit n'a pas été supprimé");
    }
    }

    @FXML
    private void modifierProd(ActionEvent event) throws IOException {
        int id= Integer.parseInt(modif.getId());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXML_ModifierProduit.fxml"));
        Parent view_2=loader.load();
        FXML_ModifierProduitController ModifierController=loader.getController();
        ModifierController.getProd(prod);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();   
        
    }
 public void ajouterProduitAuPanier(int idpanier, Produits produit) {
     
    LignePanierService lignePanierService = new LignePanierService();
    PanierService panierService = new PanierService();
    Panier pan = panierService.afficherPanierParId(idpanier);

    if (pan == null) {
        System.out.println("Le panier n'existe pas");
        return;
    }

    lignePanier.setPanier(pan);
     lignePanier.getProduit().setNom(produit.getNom());
     lignePanier.getProduit().setNom(produit.getNom());
     lignePanier.getProduit().setPrix(produit.getPrix());
    lignePanierService.ajouterLignePanier(lignePanier);
    
}
    
    
    
    
    @FXML
    private void ajouterAuPanier(ActionEvent event) {
       ajouterProduitAuPanier(8,prod);   
    }

    
}
