/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIARTISTE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.AllUsers;
import models.Logged;
import models.demandeTravail;
import models.offreTravail;
import service.AllUsersService;
import service.demandeTravailService;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class DesignresulterRechercheoffreController implements Initializable {

  
    @FXML
    private Label nomartiste;
    @FXML
    private Label categorie;
    @FXML
    private Label titredemande;
    @FXML
    private Label description;
    @FXML
    private Button contacter;
    @FXML
    private Circle photo1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 public void loaddata(offreTravail f) throws SQLException{
       titredemande.setText(f.getTitreOffre());
      description.setText(f.getDescriptionOffre());
       nomartiste.setText(f.getNomStudio());
      categorie.setText(f.getCategorieOffre().getName_category());
      
      contacter.setId(Integer.toString(f.getIdOffre()));   
      
          AllUsersService u = new  AllUsersService();
       AllUsers user = u.fetchAUbyNickname(f.getNomStudio());
            String imagePath = "C:/xampp2/htdocs/uploads/"+user.getAvatar();
            try (InputStream avatarStream = new FileInputStream(imagePath)) {
                Image avatarImage = new Image(avatarStream);
                photo1.setFill(new ImagePattern(avatarImage));
               

            } catch (IOException e) {
                System.err.println("Error loading avatar image: " + e.getMessage());
            }
   }
    offreTravailService demandeserv=new offreTravailService ();
    @FXML
    private void contacteraction(ActionEvent event) {
        int id= Integer.parseInt(contacter.getId());
        offreTravail demande = new offreTravail();
     demande=demandeserv.fetchOffresParId(id);
       demandeserv.postuleViaMail(Logged.get_instance().getUser().getID_User(), demande);
       

    }
    
}
