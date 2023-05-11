package GUIposts;

import models.Post;
import Interfaces.PostInterface;
import Service.PostService;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class ModifyPostController implements Initializable {

    //var 
    PostInterface st = new PostService();

    @FXML
    private TextField PostTitle;
    @FXML
    private TextField NewPostTitle;
    @FXML
    private TextField NewDescription;
    private Post post;
 
    
    public void initData(Post post) {
    this.post = post;
    PostTitle.setText(post.getTitle());
    //NewDescription.setText(post.getDescription_p());
}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void UpdatePost(ActionEvent event) {
        if (PostTitle.getText().isEmpty() || NewPostTitle.getText().isEmpty() || NewDescription.getText().isEmpty()) {
            // Show an error message if any of the input fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All input fields are required.");
            alert.showAndWait();
        } else {
            Post p = new Post();
            p.setTitle(PostTitle.getText());
            String newTitle = NewPostTitle.getText();
            String newDescription = NewDescription.getText();
            // Call the modifyPost method of the PostService
            st.modifyPost(p, newTitle, newDescription);
        }
    }
    @FXML
     public void handleReturn(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("PostControlPanel.fxml"));
     Scene scene = new Scene(root);
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     stage.setScene(scene);
     stage.show();
 }
     
}