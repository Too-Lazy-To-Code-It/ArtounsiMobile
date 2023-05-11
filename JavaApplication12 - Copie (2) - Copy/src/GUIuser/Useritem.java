/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIuser;

import controller.AddBan;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import javafx.stage.Stage;
import models.AllUsers;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class Useritem implements Initializable {

    @FXML
    private ImageView Background;
    @FXML
    private ImageView Avatar;
    @FXML
    private Label Nickname;
    @FXML
    private Label Bio;
    @FXML
    private Label Location;
    @FXML
    private Label Description;
    @FXML
    private Button banid;

    /**
     * Initializes the controller class.
     */
   void loaddata(AllUsers user)
{
    banid.setId(Integer.toString(user.getID_User()));
    Nickname.setText(user.getNickname());
    Description.setText(user.getDescription());
    Bio.setText(user.getBio());
    Location.setText(user.getNationality());
    String imagePath = "C:/xampp2/htdocs/uploads/"+user.getAvatar();
    try (InputStream avatarStream = new FileInputStream(imagePath)) {
        Image avatarImage = new Image(avatarStream);
        Avatar.setImage(avatarImage);
        Avatar.setPreserveRatio(false);
        Avatar.setFitWidth(100);
        Avatar.setFitHeight(100);

    } catch (IOException e) {
        System.err.println("Error loading avatar image: " + e.getMessage());
    }

    String imagePath1 = "C:/xampp2/htdocs/uploads/"+user.getBackground();
    try (InputStream backgroundStream = new FileInputStream(imagePath1)) {
        Image backgroundImage = new Image(backgroundStream);
        Background.setImage(backgroundImage);
        Background.setPreserveRatio(false);
        Background.setFitWidth(360.0);
        Background.setFitHeight(120.0);

    } catch (IOException e) {
        System.err.println("Error loading background image: " + e.getMessage());
    }
}


    void initialize() {
        assert Avatar != null : "fx:id=\"Avatar\" was not injected: check your FXML file 'Profile.fxml'.";
        assert Background != null : "fx:id=\"Background\" was not injected: check your FXML file 'Profile.fxml'.";
        assert Bio != null : "fx:id=\"Bio\" was not injected: check your FXML file 'Profile.fxml'.";
        assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file 'Profile.fxml'.";
        assert Nickname != null : "fx:id=\"Nickname\" was not injected: check your FXML file 'Profile.fxml'.";
        assert Location != null : "fx:id=\"Location\" was not injected: check your FXML file 'Profile.fxml'.";
    }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
    }

    @FXML
    private void Addban(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AddBan.fxml"));
        Parent uuView = loader.load();
        Scene scene = new Scene(uuView,1380,700);

        AddBan AddBan = loader.getController();
        AddBan.setUserId(Integer.parseInt(banid.getId()));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}
