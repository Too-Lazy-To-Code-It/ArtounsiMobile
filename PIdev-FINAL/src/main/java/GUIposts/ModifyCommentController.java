package GUIposts;

import models.Comment;
import Interfaces.CommentInterface;
import Service.CommentService;
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
public class ModifyCommentController implements Initializable {

    //var 
    CommentInterface cm = new CommentService();

    @FXML
    private TextField OldComment;
    @FXML
    private TextField NewComment;
    private Comment comment;
    
    public void initData(Comment comment) {
        this.comment = comment;
        OldComment.setText(comment.getComment());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleModifyComment(ActionEvent event) {
        if (OldComment.getText().isEmpty() || NewComment.getText().isEmpty()) {
            // Show an error message if any of the input fields are empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All input fields are required.");
            alert.showAndWait();
        } else {
            Comment c = new Comment();
            c.setComment(OldComment.getText());
            String newComment = NewComment.getText();
            // Call the modifyComment method of the CommentService
            cm.modifyComment(c, newComment);
            
        }
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