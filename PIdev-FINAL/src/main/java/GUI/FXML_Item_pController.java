/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Challenge;
import models.Participation;
import models.Rate;
import interfaces.ChallengeInterface;
import interfaces.RatingInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Logged;
import org.controlsfx.control.Rating;
import service.ChallengeService;
import service.RatingService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_Item_pController implements Initializable {

    RatingInterface ri = new RatingService();
    ChallengeInterface ci = new ChallengeService();
    @FXML
    private ImageView participation_img;
    @FXML
    private Label participation_desc;
    
    private Participation p;
    private Rate rate = new Rate();
    @FXML
    private Rating rating;
    @FXML
    private Label avg_rating;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setData(Participation p) {
        this.p=p;
        participation_desc.setText(p.getDescription());
        File file = new File("C:\\xampp\\htdocs\\img\\"+p.getIMG_Participation());
        Image img = new Image(file.toURI().toString());
        participation_img.setImage(img);

        rate.setRater(Logged.get_instance().getUser());

        rate.setChallenge(p.getChallenge());
        
        rate.setParticipator(p.getParticipant());
        
        rate.setRating(rating.getRating());
        
        rating.setRating(ri.fetchRating(rate));
        
        avg_rating.setText(String.valueOf(ri.fetchRatingAVG(rate)));
        
        
    }

    @FXML
    private void setRating(ActionEvent event) throws IOException {
        rate.setRating(rating.getRating());
        ri.updateRating(rate);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Welcome");
            alert.setHeaderText("");
            alert.setContentText("Thanks for you voting!");
            alert.show();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FetchChallenges.fxml"));
        FXML_FetchParticipationsController itemController = loader.getController();
        Parent view_2=loader.load();
        Scene scene = new Scene(view_2);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
}