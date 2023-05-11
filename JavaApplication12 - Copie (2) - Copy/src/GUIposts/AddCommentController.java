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
    private ComboBox<Post> postsComboBox;

    @FXML
    private TextField commentTextField;

    @FXML
    private TextField userIdTextField;

    private ObservableList<Post> postsObservableList;
    private PostInterface postService;
    private CommentInterface commentService;
    private Comment comment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the services and the observable list of posts
        postService = new PostService();
        commentService = new CommentService();
        postsObservableList = FXCollections.observableArrayList();

        // Fetch the posts and add their titles to the combo box
        List<Post> posts = postService.fetchPosts();
        if (posts != null) {
            postsObservableList.addAll(posts);
            postsComboBox.setItems(postsObservableList);
            // check if the comboBox is not null
            if (postsComboBox != null) {
                postsComboBox.setConverter(new PostConverter());
            }
        }
    }

    @FXML
    void addComment(ActionEvent event) throws IOException {
        // Check if comment text field and user ID text field are not empty
        if (commentTextField.getText().isEmpty() || userIdTextField.getText().isEmpty()) {
            // Show an alert if either field is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing fields");
            alert.setContentText("Please enter a comment and a user ID.");
            alert.showAndWait();
            return;
        }

        // Create a new comment with the data from the form
        comment = new Comment();
        comment.setComment(commentTextField.getText());
        //comment.setId_user(Integer.parseInt(userIdTextField.getText()));
         comment.setId_user(Logged.get_instance().getUser().getID_User());

        // Get the selected post from the combo box
        Post selectedPost = postsComboBox.getValue();
        String postTitle = selectedPost.getDescription_p();

        // Search for the corresponding post in the database
        postService = new PostService();
        Post post = postService.fetchPostByTitle(postTitle);
        comment.setPost_c(post);

      /*  // Check spelling of comment text
       SpellChecker spellChecker = new SpellChecker();
        SpellDictionaryHashMap dictionary = new SpellDictionaryHashMap(new File("C:\\Users\\amine\\Documents\\NetBeansProjects\\Pidev_3eme\\src\\Util\\words_alpha"));
        spellChecker.setUserDictionary(dictionary);
        StringWordTokenizer tokenizer = new StringWordTokenizer(commentTextField.getText());
        while (tokenizer.hasMoreWords()) {
            String word = tokenizer.nextWord();
            if (!spellChecker.isCorrect(word)) {
                // Show an alert if the word is misspelled
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Misspelled word");
                alert.setContentText("The word '" + word + "' is misspelled.");
                alert.showAndWait();
                return;
            }
        }*/

        // Add the comment to the database
        commentService.addComment(comment);
    }
    /**
     * A custom converter that displays the title of a post in the combo box.
     */
    private class PostConverter extends javafx.util.StringConverter<Post> {

        @Override
        public String toString(Post post) {
            return (post != null) ? post.getTitle() : "";
        }

        @Override
        public Post fromString(String string) {
            return null; // not used
        }
    }
    
    
}