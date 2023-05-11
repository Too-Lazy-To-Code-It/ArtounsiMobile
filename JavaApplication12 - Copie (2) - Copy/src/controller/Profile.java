package controller;

import GUIposts.AfficherpostController;
import GUIposts.AfficherpostdetailsController;
import GUIposts.ModifyPostController;
import GUIposts.VueCommentPostController;
import Service.PostService;
import java.io.File;
import models.AllUsers;
import models.Logged;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Post;
import models.PostLike;

public class Profile implements Initializable {

    @FXML
    private ImageView Avatar;

    @FXML
    private ImageView Background;

    @FXML
    private Label Bio;

    @FXML
    private Label Description;

    @FXML
    private Label Nickname;

    @FXML
    private Label Location;

    @FXML
    private VBox tfpostlist;
      @FXML
    private GridPane citiesGril;
    private PostService postService;

    public Profile() {
        postService = new PostService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert Avatar != null : "fx:id=\"Avatar\" was not injected: check your FXML file '/GUI/Profile.fxml'.";
        assert Background != null : "fx:id=\"Background\" was not injected: check your FXML file '/GUI/Profile.fxml'.";
        assert Bio != null : "fx:id=\"Bio\" was not injected: check your FXML file '/GUI/Profile.fxml'.";
        assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file '/GUI/Profile.fxml'.";
        assert Nickname != null : "fx:id=\"Nickname\" was not injected: check your FXML file '/GUI/Profile.fxml'.";
        assert Location != null : "fx:id=\"Location\" was not injected: check your FXML file '/GUI/Profile.fxml'.";
        AllUsers user = Logged.get_instance().getUser();

        Nickname.setText(user.getNickname());
        Description.setText(user.getDescription());
        Bio.setText(user.getBio());
        Location.setText(user.getNationality());
        if (user != null) {
            System.out.println(user.getNickname());
            System.out.println(user);
            System.out.println(user.getAvatar());
            System.out.println(user.getBackground());

            String imagePath = "C:/xampp2/htdocs/uploads/" + user.getAvatar();
            try (InputStream avatarStream = new FileInputStream(imagePath)) {
                Image avatarImage = new Image(avatarStream);
                Avatar.setImage(avatarImage);
                Avatar.setPreserveRatio(false);
                Avatar.setFitWidth(100);
                Avatar.setFitHeight(100);

            } catch (IOException e) {
                System.err.println("Error loading avatar image: " + e.getMessage());
            }

            String imagePath1 = "C:/xampp2/htdocs/uploads/" + user.getBackground();
            try (InputStream backgroundStream = new FileInputStream(imagePath1)) {
                Image backgroundImage = new Image(backgroundStream);
                Background.setImage(backgroundImage);
                Background.setPreserveRatio(false);
                Background.setFitWidth(1386.0);
                Background.setFitHeight(338.0);

            } catch (IOException e) {
                System.err.println("Error loading background image: " + e.getMessage());
            }
        } else {
            System.out.println("No user is currently logged in.");
        }

        List<Post> posts = postService.fetchPortfolioPostDetails();//change agter this to .fetchPortfolioPostDetails
       int column=0;
       int row = 1 ;
       for(Post post : posts){
           //post.setId_post(post.getId_post());
           String title = post.getTitle();
           int CreaterUserId =postService.getIdUserByTitle(title);
           
           System.out.println("aaaaaaaaaaaaaaaaaaa"+CreaterUserId);
            System.out.println("bbbbbbbbbbbbbbbbbb"+Logged.get_instance().getUser().getID_User());
           if(CreaterUserId==Logged.get_instance().getUser().getID_User()){
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUIposts/afficherpostdetails.fxml"));
               Pane pane = fxmlLoader.load();
               AfficherpostdetailsController ac = fxmlLoader.getController();
               //ac.loaddata(post);
               ac.loaddata(post);
               
               if(column==3){column=0; ++row ;}
               citiesGril.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
           } catch (IOException ex) {
               Logger.getLogger(Menu1Controller.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
                Logger.getLogger(AfficherpostController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       }
    }

    @FXML
    private void modifyUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UpdateUser.fxml"));
        Parent uuView = loader.load();
        Scene scene = new Scene(uuView, 1377, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void viewAddCategoryPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/AddCategory.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void viewCategory(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/affichercategory.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

}
