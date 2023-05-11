/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUImenuprincipale;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.NewFXMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
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
public class menuprincipale implements Initializable {

    private GridPane citiesGrid;
    offreTravailService of = new offreTravailService();
    private List<offreTravail> listeOffres;
    @FXML
    private Pane CRUD;
    offreTravailService off = new offreTravailService();
    @FXML
    private Button forstudio;
    @FXML
    private Circle profilephoto;
    @FXML
    private Button profile;
    @FXML
    private Button deconnexion;
    @FXML
    private Button galeriebutton;
    @FXML
    private Button challengebutton;
    @FXML
    private Button tutobutn;
    @FXML
    private Button shopbutton;
    @FXML
    private Button panierbutton;
    @FXML
    private Button blogbutton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Logged.get_instance().getUser() == null) {
            profile.setVisible(false);
             profilephoto.setVisible(false);
             deconnexion.setText("Connecter");
        } else {
            try {//get the css for popup 
                   profile.setVisible(true);
                    profilephoto.setVisible(true);
                AllUsers user = Logged.get_instance().getUser();
                String imagePath = "C:/xampp2/htdocs/uploads/" + user.getAvatar();
                try (InputStream avatarStream = new FileInputStream(imagePath)) {
                    Image avatarImage = new Image(avatarStream);
                    profilephoto.setFill(new ImagePattern(avatarImage));
                } catch (IOException e) {
                    System.err.println("Error loading avatar image: " + e.getMessage());
                }
//design my popup
                if (Logged.get_instance().getUser().getType().equals("Observer")) {
                    forstudio.setVisible(false);
                } else {
                    forstudio.setVisible(true);
                }

                AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUImenuprincipale/menuelements.fxml"));
                CRUD.getChildren().add(pane);

            } catch (IOException ex) {
                Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void gotomenu(ActionEvent event
    ) {

        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUImenuprincipale/menuelements.fxml"));
            CRUD.getChildren().add(pane);

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    AllUsersService as = new AllUsersService();

    @FXML
    private void forstudio(ActionEvent event
    ) {
        try {

            if (Logged.get_instance().getUser().getType().equals("Studio")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/menu1.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 1380, 700);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIARTISTE/menuartiste.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 1380, 700);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            }
        } catch (IOException ex) {
            Logger.getLogger(menuprincipale.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void galerie(ActionEvent event
    )throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/Explore.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

    @FXML
    private void challenge(ActionEvent event
    ) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/FetchChallenges.fxml"));
            CRUD.getChildren().add(pane);

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void tuto(ActionEvent event
    ) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/FXML_Fetch_Tutoriels.fxml"));
            CRUD.getChildren().add(pane);

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deconnexion(ActionEvent event
    ) {
        if (Logged.get_instance().getUser() != null) {
            Logged.get_instance().setUser(null);
            Parent root;
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
                root = loader.load();

                Scene scene = new Scene(root, 1380, 700);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Parent root;
            try {

                FXMLLoader loaders = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
                root = loaders.load();

                Scene scene = new Scene(root, 1380, 700);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

            } catch (IOException ex) {
                Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void afficherprofile(ActionEvent event
    ) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/profile.fxml"));
            CRUD.getChildren().add(pane);

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void blog(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/ViewBlog.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
