/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIARTISTE;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.offreTravail;
import service.offreTravailService;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.AllUsers;
import models.Logged;
import models.demandeTravail;
import service.demandeTravailService;
/**
 * FXML Controller class
 *
 * @author nour2
 */
public class MesdemandesitemsController implements Initializable {

    @FXML
    private Label titreOffre;
    @FXML
    private Label nomStudio;
   
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
    @FXML
    private Button pdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
     String mypdf="";
 public void loaddata(demandeTravail f){
       titreOffre.setText(f.getTitreDemande());
       nomStudio.setText(f.getNomArtiste());
       pdf.setId(f.getPdf());
       dateAjout.setText(f.getDateAjoutDemande().toString());
       decription.setText(f.getDescriptionDemande());
       categorie.setText(f.getCategorieDemande().getName_category()); mypdf=f.getPdf();
       supprimerOffre.setId(Integer.toString(f.getIdDemande()));   
 editoffre.setId(Integer.toString(f.getIdDemande()));  
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
        
         int id= Integer.parseInt(editoffre.getId());
         String  titre=titreOffre.getText();
       String desc = decription.getText();
      String pdf= mypdf;
     String categ = categorie.getText();
      AllUsers user = Logged.get_instance().getUser();
            String imagePath = "C:/xampp2/htdocs/uploads/"+user.getAvatar();
           try (InputStream avatarStream = new FileInputStream(imagePath)) {
                Image avatarImage = new Image(avatarStream);
                photo.setFill(new ImagePattern(avatarImage));
               

            } catch (IOException e) {
                System.err.println("Error loading avatar image: " + e.getMessage());
            }
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIARTISTE/modifierdemande.fxml"));
        Parent root = loader.load();
        
                  //get modifier controller to set data a mmodifier
            modifierdemandeController  modifier = loader.getController();
            //envoyer donner
      modifier.getid(id,titre,desc,categ,pdf);
      
      
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
     /*****************************************************************************/

demandeTravailService off=new demandeTravailService ();
    @FXML
    
    
    private void supprimer(ActionEvent event) {
        try {
            int id= Integer.parseInt(supprimerOffre.getId());
            demandeTravail of = new demandeTravail();
            of=off.fetchdemandeParId(id);
            off.SupprimerDemande(of);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("cette offre est supprimer, veuiller refresh your page");
            
            alert.show();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIartiste/menuartiste.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root,1380,700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(MesdemandesitemsController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }

   
    @FXML
    private void getpdf(ActionEvent event) {
       String pdfPath = "C:/xampp2/htdocs/uploads/"+mypdf;
         System.out.print(mypdf+"nour");
             File file = new File(pdfPath);

               if ( file .exists()) {
        // do something with the file
        if(Desktop.isDesktopSupported()){try {
            Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                Logger.getLogger(AfficherdemandesitemsController.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    } else {
        System.err.println("File not found: " + pdfPath);
    }
    
}
}