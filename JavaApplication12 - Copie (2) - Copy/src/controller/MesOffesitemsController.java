/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.NewFXMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.offreTravail;
import service.offreTravailService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.AllUsers;
import models.Logged;
/**
 * FXML Controller class
 *
 * @author nour2
 */
public class MesOffesitemsController implements Initializable {

    @FXML
    private Label titreOffre;
    @FXML
    private Label nomStudio;
    @FXML
    private Label typeOffre;
    @FXML
    private Label locOffre;
    @FXML
    private Label dateAjout;
    @FXML
    private Button editoffre;
    @FXML
    private Button supprimerOffre;
    @FXML
    private Pane modifierpage;
    @FXML
    private Label decription;
    @FXML
    private Label categorie;
    @FXML
    private Circle photo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
 public void loaddata(offreTravail f){
       titreOffre.setText(f.getTitreOffre());
       nomStudio.setText(f.getNomStudio());
       typeOffre.setText(f.getTypeOffre());
       locOffre.setText(f.getLocalisationOffre());
       dateAjout.setText(f.getDateAjoutOffre().toString());
       decription.setText(f.getDescriptionOffre());
       categorie.setText(f.getCategorieOffre().getName_category());
       supprimerOffre.setId(Integer.toString(f.getIdOffre()));   
 editoffre.setId(Integer.toString(f.getIdOffre())); 
    AllUsers user = Logged.get_instance().getUser();
            String imagePath = "C:/xampp2/htdocs/uploads/"+user.getAvatar();
           try (InputStream avatarStream = new FileInputStream(imagePath)) {
                Image avatarImage = new Image(avatarStream);
                photo.setFill(new ImagePattern(avatarImage));
               

            } catch (IOException e) {
                System.err.println("Error loading avatar image: " + e.getMessage());
            }
 }
 
 
 
 /*****************************************************************************/
    @FXML
  private void modifier(ActionEvent event) {
    int id = Integer.parseInt(editoffre.getId());
    String titre = titreOffre.getText();
    String desc = decription.getText();
    String type = typeOffre.getText();
    String loc = locOffre.getText();
    String categ = categorie.getText();
    
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/modifierOffre.fxml"));
        Parent root = loader.load();
        
        ModifierOffreController modifier = loader.getController();
        modifier.getid(id, titre, desc, type, loc, categ);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    } catch (IOException ex) {
        ex.printStackTrace();
        // Display an error message to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Unable to open the modification window. Please try again.");
        alert.showAndWait();
    }
}

     /*****************************************************************************/

offreTravailService off=new offreTravailService ();
 offreTravailService of = new offreTravailService ();
    private List<offreTravail> mesoffres;
    @FXML
    
    
    private void supprimer(ActionEvent event) {
      
        try {
            int id= Integer.parseInt(supprimerOffre.getId());
            offreTravail of = new offreTravail();
            of=off.fetchOffresParId(id);
            off.Supprimeretajouterarchive(of);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/menu1.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root,1380,700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MesOffesitemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        

      
       
   
    }
    
}
