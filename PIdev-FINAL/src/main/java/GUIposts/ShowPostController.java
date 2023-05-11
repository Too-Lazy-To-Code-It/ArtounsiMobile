package GUIposts;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import models.Post;
import models.PostLike;
import Service.PostService;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShowPostController implements Initializable {

    @FXML
    private VBox tfpostlist;
    private PostService postService;

    public ShowPostController() {
        postService = new PostService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Post> posts = postService.fetchPosts();
        for (Post post : posts) {
            VBox postBox = new VBox();
            postBox.setStyle("-fx-border-color: black; -fx-padding: 5px;");
            Label titleLabel = new Label("Title: " + post.getTitle());
            Label descriptionLabel = new Label("Description: " + post.getDescription_p());

            // Load the image from the file system based on the file path stored in the database
            String mediaPath = post.getMedia();
            if (mediaPath != null && !mediaPath.isEmpty()) {
                File mediaFile = new File(mediaPath);
                if (mediaFile.exists() && mediaFile.isFile()) {
                    ImageView mediaView = new ImageView(new Image(mediaFile.toURI().toString()));
                    mediaView.setFitWidth(400); // set the width of the image view as needed
                    mediaView.setFitHeight(400); // set the height of the image view as needed
                    postBox.getChildren().add(mediaView);
                } else {
                    Label mediaLabel = new Label("Media: " + mediaPath);
                    postBox.getChildren().add(mediaLabel);
                }
            }

            HBox titleBox = new HBox();
            titleBox.getChildren().addAll(titleLabel);
            postBox.getChildren().add(titleBox);

            HBox descriptionBox = new HBox();
            descriptionBox.getChildren().addAll(descriptionLabel);
            postBox.getChildren().add(descriptionBox);

            // Get the number of likes for this post
            List<PostLike> likes = postService.Number_Of_Likes_For_A_Post_Post(post.getId_post());
            int numberOfLikes = likes.size();
            Label likesLabel = new Label("Likes: " + numberOfLikes); // create a Label object to display the number of likes
            
            Button addCommentButton = new Button("Add Comment");
            addCommentButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/AddAComment.fxml"));
                Parent addCommentView = loader.load();
                AddACommentController addCommentController = loader.getController();
                addCommentController.setId_post(post.getId_post());
                addCommentController.setId_user(1);
                Scene scene = new Scene(addCommentView);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
           // addCommentButton.setOnAction(this::handleAddComment);
//            addCommentButton.setOnAction((ActionEvent e) -> {
//                // Open the post in a new window or do some other action
//            });

//            Button button1 = new Button("View Comments");
//            button1.setOnAction(event -> {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/VueCommentPost.fxml"));
//                Parent root = loader.load();
//                VueCommentPostController controller = loader.getController();
//                controller.setPostId(post.getId_post());
//
//                Scene scene = new Scene(root);
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                stage.setScene(scene);
//                stage.show();         
//            } catch (IOException e)  {
//                e.printStackTrace();
//            }
//        });

//            Button button1 = new Button("View Comments");
//            button1.setOnAction(event -> {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/VueCommentPost.fxml"));
//                    Parent root = loader.load();
//                    VueCommentPostController controller = loader.getController();
//                    controller.setId_post(post.getId_post()); // set the postId in the VueCommentPostController instance
//
//                    Scene scene = new Scene(root);
//                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                    stage.setScene(scene);
//                    stage.show();         
//                } catch (IOException e)  {
//                    e.printStackTrace();
//                }
//            });
        Button button1 = new Button("View Comments");
        button1.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/VueCommentPost.fxml"));
                Parent root = loader.load();
                VueCommentPostController controller = loader.getController();
                controller.setId_post(post.getId_post()); // set the postId in the VueCommentPostController instance

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();         
            } catch (IOException e)  {
                e.printStackTrace();
            }
        });






            Button button2 = new Button("like");
            button2.setOnAction((ActionEvent e) -> {
                Post p = new Post();
                p.setId_post(post.getId_post());
                p.setId_user(1); // set the user ID of the user who liked the post
                postService.addLike(p); // call the addLike method to add the like
                // update the label displaying the number of likes
                //List<PostLike> likes = postService.Number_Of_Likes_For_A_Post_Post(post.getId_post());
               // int numberOfLikes = likes.size();
                likesLabel.setText("Likes: " + numberOfLikes);
            });
            Button button3 = new Button("Unlike");
            button3.setOnAction((ActionEvent e) -> {
                Post p = new Post();
                p.setId_post(post.getId_post());
                p.setId_user(1); // set the user ID of the user who unliked the post
                postService.deleteLike(p.getId_post(), p.getId_user()); // call the deleteLike method to remove the like
                // update the label displaying the number of likes
                //List<PostLike> likes = postService.Number_Of_Likes_For_A_Post_Post(post.getId_post());
                //int numberOfLikes = likes.size();
                likesLabel.setText("Likes: " + numberOfLikes);
            });
            HBox buttonBox = new HBox();
            buttonBox.getChildren().addAll(addCommentButton, button1, button2, button3);

            buttonBox.setSpacing(10); // Set the spacing between the buttons
            

            VBox likesBox = new VBox();
            likesBox.getChildren().addAll(likesLabel, buttonBox);
            postBox.getChildren().add(likesBox);

            tfpostlist.getChildren().add(postBox);
        }
    }
    
    
       public void handleAddComment(ActionEvent event) {
    try {
        // Load the AddAComment.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/AddAComment.fxml"));
        Parent root = loader.load();

        // Create a new scene and display it in a new window
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        // Handle the exception here
        e.printStackTrace();
    }
}

}