/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Challenge;
import models.Participation;
import interfaces.ChallengeInterface;
import interfaces.ParticipationInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Logged;
import service.ChallengeService;
import service.ParticipationService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class ItemController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private ImageView image;
    
    private int id;
    private Challenge challenge;
    
    ChallengeInterface chi= new ChallengeService();
    @FXML
    private Label level;
    @FXML
    private Label level1;
    @FXML
    private Label categorie;
    @FXML
    private Label date;
    
    ParticipationInterface pi = new ParticipationService();
    Participation p = new Participation();
    @FXML
    private Button mod_button;
    @FXML
    private Button del_button;
    @FXML
    private VBox vbox;

    public void setData(Challenge challenge){
        File file = new File("C:\\xampp\\htdocs\\img\\"+challenge.getPathIMG());
        Image img = new Image(file.toURI().toString());
        image.setImage(img);
        title.setText(challenge.getTitle());
        description.setText(challenge.getDescription());
        level.setText(String.valueOf(challenge.getNiveau()));
        categorie.setText(challenge.getCategorie().getName_category());
        date.setText(challenge.getDate_C()); 
        
        this.challenge = challenge;
        if (!(challenge.getCreator().getID_User() == Logged.get_instance().getUser().getID_User() || Logged.get_instance().getUser().getType().equals("admin")))
        {
            vbox.getChildren().remove(mod_button);
            vbox.getChildren().remove(del_button);
        }
    }
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void removeChallenge(ActionEvent event) throws IOException {
        chi.deleteChallenge(challenge.getID_Challenge());
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FetchChallenges.fxml"));
        Parent view_2=loader.load();
        Scene scene = new Scene(view_2);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyChallenge(ActionEvent event) {
               try {
        //Article selectedGarage=listaf.getSelectionModel().getSelectedItem();
        
      
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Modify_Challenge.fxml"));
        Parent view_2=loader.load();
        FXML_Modify_ChallengeController modify_ChallengeController=loader.getController();
        modify_ChallengeController.getChallenge(challenge);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace();
        //Logger.getLogger(FXMLafficherController.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    @FXML
    private void participate(ActionEvent event) throws IOException {
        if(pi.Participated(challenge.getID_Challenge(), Logged.get_instance().getUser().getID_User()))
            {
             FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_FetchParticipation2.fxml"));
            Parent view_2=loader.load();
            FXML_FetchParticipations2Controller fetchParticipations2Controller=loader.getController();
            fetchParticipations2Controller.getChallenge(challenge);
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(view_2);
            stage.setScene(scene);
            stage.show();
                }
        else {
           FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_FetchParticipations.fxml"));
            Parent view_2=loader.load();
            FXML_FetchParticipationsController fetchParticipationsController=loader.getController();
            fetchParticipationsController.getChallenge(challenge);
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(view_2);
            stage.setScene(scene);
            stage.show();
        }
    }
    
}
