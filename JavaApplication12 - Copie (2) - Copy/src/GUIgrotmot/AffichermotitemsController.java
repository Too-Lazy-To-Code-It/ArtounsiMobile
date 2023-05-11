/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIgrotmot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import models.AllUsers;
import models.Logged;
import models.demandeTravail;

import models.grosMots;
import service.grosMotsService;



/**
 * FXML Controller class
 *
 * @author nour2
 */
public class AffichermotitemsController implements Initializable {

    @FXML
    private Label titreOffre;
    @FXML
    private Button modifier;
   
    @FXML
    private Button supprimer;
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void loaddata(grosMots g) throws SQLException{
       titreOffre.setText(g.getMot());
        supprimer.setId(Integer.toString(g.getIdmot()));   
 modifier.setId(Integer.toString(g.getIdmot()));  
   }

    @FXML
    private void modifier(ActionEvent event) {
           
         int id= Integer.parseInt( modifier.getId());
         String  titre=titreOffre.getText();
     
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIgrotmot/modifiermot.fxml"));
        Parent root = loader.load();
        
                  //get modifier controller to set data a mmodifier
            GUIgrotmot.modifiermotController  modifier = loader.getController();
            //envoyer donner
      modifier.getid(id,titre);
      
      
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
grosMotsService motparid = new grosMotsService();

    @FXML
    private void supprimer(ActionEvent event) {
        int id= Integer.parseInt( supprimer.getId());
        grosMots mot = new grosMots();
       
      mot=motparid.fetchmotById(id);
        motparid.Supprimer(mot);
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Alert");
alert.setHeaderText(null);
alert.setContentText("cette mot est supprimer, veuiller refresh your page");

alert.show();
    }
    


   

           
    

   
}
