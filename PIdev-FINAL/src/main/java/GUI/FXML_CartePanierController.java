/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.LignePanier;
import models.Produits;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Stage;
import service.LignePanierService;

/**
 * FXML Controller class
 *
 * @author aouad
 */
public class FXML_CartePanierController implements Initializable {
    
    LignePanierService lp = new LignePanierService();
    LignePanier ligneP= new LignePanier();
    @FXML
    private ImageView imgProd;
    @FXML
    private Label nomProd;
    @FXML
    private Label prixProd;
    @FXML
    private Label dateAj;

    @FXML
    private Button suppP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        public void setIdPanier(int idPanier) {
       this.idPanier = idPanier;
     
    }
    int idPanier=8;
    private Produits prod;
    public void setDataPanier(LignePanier ligneP) throws MalformedURLException {
        this.ligneP = ligneP;
        nomProd.setText(ligneP.getProduit().getNom());
        prixProd.setText(Double.toString(ligneP.getProduit().getPrix()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateAjout = sdf.format(ligneP.getDateajout());
        dateAj.setText(dateAjout);
        File file = new File("C:\\xampp\\htdocs\\img\\"+ligneP.getProduit().getImage());
        System.out.println("fprod"+file);
        Image img = new Image(file.toURI().toString());
        System.out.println("nmgProd"+img);
        imgProd.setImage(img);
        suppP.setId(Integer.toString(ligneP.getIdlignepanier()));
        System.out.println("ligneP.getIdLignePanier(): " + ligneP);

    }


    @FXML
    private void suppProd(ActionEvent event) throws IOException {
        System.out.println();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer ce produit ?");
      
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
     
        int idLignePanier = Integer.parseInt(suppP.getId());
        lp.supprimerLignePanier(idLignePanier);
        
        System.out.println("Supp Prod Button Clicked");
        System.out.println("suppP.getId(): " + suppP.getId());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXML_AfficherPanier.fxml"));
        Parent view_2=loader.load();
        Scene scene = new Scene(view_2);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        System.out.println("Produit supprimé avec succés!");
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        alert2.setTitle("Success");
        alert2.setHeaderText(null);
        alert2.setContentText("Produit supprimé avec succés!");
        alert2.showAndWait();
        
      
      } else {
        System.out.println("Le produit n'a pas été supprimé");
    }   }
       

    
//lps.SupprimerProduit_de_LignePanier(lp.getPanier().getId_panier(), lp.getProduit().getId_prod());
//        //Rafraichir le contenu de l'interface
//           FXMLLoader loader= new FXMLLoader(getClass().getResource("./PanierInterface.fxml"));
//            Parent view_2=loader.load();
//           PanierInterfaceController i =loader.getController();
//            
//            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(view_2);
//            stage.setScene(scene);
//            stage.show();
//         Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Produit supprimé du panier avec succés");
//            alert.show();
//




}
