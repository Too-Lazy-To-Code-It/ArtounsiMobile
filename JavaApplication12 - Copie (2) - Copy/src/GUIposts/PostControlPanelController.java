/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIposts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class PostControlPanelController implements Initializable {
 /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void viewAddPostPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Post.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
//      public void viewModifyPostPage(ActionEvent event) throws IOException {
//        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("ModifyPost.fxml"));
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
       public void viewDeletePostPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("DeletePost.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        public void viewBlogPostPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ViewBlog.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
        public void viewPortfolioPostPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ViewPortfolio.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
            public void viewExplorePostPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Explore.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
       
    
}
