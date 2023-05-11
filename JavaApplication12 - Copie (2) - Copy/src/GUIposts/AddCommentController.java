package GUIposts;


import models.Comment;
import models.Post;
import Interfaces.CommentInterface;
import Interfaces.PostInterface;
import Service.CommentService;
import Service.PostService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;/*
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;*/
import models.Logged;

public class AddCommentController implements Initializable {

    @FXML
    private TextField commentTextField;

    private CommentService commentService;
    private PostService postService;

    private int id_post;
    private int id_user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        commentService = new CommentService();
        postService = new PostService();
        commentService = new CommentService();
    }

    @FXML
    void addComment(ActionEvent event) {
        // Check if comment text field is not empty
        System.out.println("id user"+Logged.get_instance().getUser().getID_User());
        System.out.println("id_post"+id_post);
        if (commentTextField.getText().isEmpty()) {
            // Show an alert if the comment field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing comment");
            alert.setContentText("Please enter a comment.");
            alert.showAndWait();
            return;
        }

        Comment comment = new Comment();
        comment.setComment(commentTextField.getText());
        comment.setId_user(Logged.get_instance().getUser().getID_User());

        Post c = postService.getPostById(id_post);
        comment.setPost_c(c);
        System.out.println("comment"+ comment.getComment());
       commentService.addComment(comment);

        // Show a confirmation alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Comment added");
        alert.setContentText("The comment was successfully added.");
        alert.showAndWait();
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}