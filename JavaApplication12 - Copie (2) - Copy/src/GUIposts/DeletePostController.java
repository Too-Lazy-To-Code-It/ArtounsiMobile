package GUIposts;

import Service.PostService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

public class DeletePostController implements Initializable {

    @FXML
    private SplitMenuButton postMenuButton;

    @FXML
    private Button deletePostButton;

    private PostService postService;
    private String selectedTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            postService = new PostService();
            List<String> postTitles = postService.getPostTitles();
            for (String title : postTitles) {
                MenuItem menuItem = new MenuItem(title);
                menuItem.setOnAction(e -> {
                    selectedTitle = title;
                });
                postMenuButton.getItems().add(menuItem);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // handle the error here
        }
    }

    @FXML
    private void handleDeletePostButtonAction(ActionEvent event) {
        int postId = postService.getPostIdByTitle(selectedTitle);
        postService.deletePost(postId);
    }
     @FXML
     public void handleReturn(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/PostControlPanel.fxml"));
     Scene scene = new Scene(root);
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     stage.setScene(scene);
     stage.show();
 }
     
}