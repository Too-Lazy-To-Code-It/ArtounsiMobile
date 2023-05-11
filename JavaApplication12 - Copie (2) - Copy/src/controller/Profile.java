package controller;

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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

        //List<Post> posts = postService.fetchPortfolioPostDetails();
        List<Post> posts = postService.fetchPortfolioPostDetailsOfThePortfolioCreater();
        for (Post post : posts) {
            VBox postBox = new VBox();
            postBox.setStyle("-fx-border-color: black; -fx-padding: 5px;");
            postBox.setPrefWidth(200);
            Label titleLabel = new Label("Title: " + post.getTitle());

            Label descriptionLabel = new Label("Description: " + post.getDescription_p());
            System.out.println("tiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii" + post.getTitle());
            // Load the image from the file system based on the file path stored in the database
            String mediaPath = post.getMedia();
            if (mediaPath != null && !mediaPath.isEmpty()) {
                File mediaFile = new File(mediaPath);
                if (mediaFile.exists() && mediaFile.isFile()) {
                    ImageView mediaView = new ImageView(new Image(mediaFile.toURI().toString()));
                    mediaView.setFitWidth(100); // set the width of the image view as needed
                    mediaView.setFitHeight(100); // set the height of the image view as needed
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
            Label likesLabel = new Label("j'aime: " + numberOfLikes); // create a Label object to display the number of likes
            postBox.getChildren().add(likesLabel);

//            Button addCommentButton = new Button("Add Comment");
//            addCommentButton.setOnAction(event -> {
//                try {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/AddAComment.fxml"));
//                    Parent addCommentView = loader.load();
//                    AddACommentController addCommentController = loader.getController();
//                    addCommentController.setId_post(post.getId_post());
//                    addCommentController.setId_user(1);
//                    Scene scene = new Scene(addCommentView);
//                    Stage stage = new Stage();
//                    stage.setScene(scene);
//                    stage.show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            postBox.getChildren().add(addCommentButton);
            Button button1 = new Button("Voir Commentaires");

            button1.setLayoutX(1039.0);
            button1.setLayoutY(612.0);
            button1.setPrefHeight(46.0);
            button1.setPrefWidth(100.0);
            button1.setStyle("-fx-background-color: C10C99;");
            button1.setTextFill(Color.WHITE);

            button1.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/VueCommentPost.fxml"));
                    Parent root = loader.load();
                    VueCommentPostController controller = loader.getController();
                    controller.setId_post(post.getId_post()); // set the postId in the VueCommentPostController instance

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Button deletePostButton = new Button("Supprimer Post");

            deletePostButton.setLayoutX(1039.0);
            deletePostButton.setLayoutY(612.0);
            deletePostButton.setPrefHeight(46.0);
            deletePostButton.setPrefWidth(100.0);
            deletePostButton.setStyle("-fx-background-color: C10C99;");
            deletePostButton.setTextFill(Color.WHITE);

            deletePostButton.setOnAction(event -> {
                Post p = new Post();
                p.setTitle(post.getTitle());
                String title = post.getTitle();
                int CreaterUserId = postService.getIdUserByTitle(title);
                if (CreaterUserId == Logged.get_instance().getUser().getID_User()) {
                    postService.deletePost(post.getId_post()); // call the deletePost method to delete the post
                    tfpostlist.getChildren().remove(postBox); // remove the VBox representing the deleted post from the main VBox
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Supprimer Post");
                    alert.setContentText("You can't delete the post, you're not the creator!");
                    alert.showAndWait();
                }
            });

            postBox.getChildren().add(deletePostButton);

            Button ModifyPostButton = new Button("Modifier Post");
            ModifyPostButton.setLayoutX(1039.0);
            ModifyPostButton.setLayoutY(612.0);
            ModifyPostButton.setPrefHeight(46.0);
            ModifyPostButton.setPrefWidth(100.0);
            ModifyPostButton.setStyle("-fx-background-color: C10C99;");
            ModifyPostButton.setTextFill(Color.WHITE);

            ModifyPostButton.setOnAction(event -> {
                Post p = new Post();
                // p.setId_post(post.getId_post()
                // p.setId_user(currentUserId);
                //p.setId_post(post.getId_post())
                p.setTitle(post.getTitle());
                p.setId_user(post.getId_user());

                String title = post.getTitle();

                System.out.println("hey" + post.getId_user());
                System.out.println("id " + postService.getIdUserByTitle(title));
                int CreaterUserId = postService.getIdUserByTitle(title);
                if ((CreaterUserId == Logged.get_instance().getUser().getID_User())) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/ModifyPost.fxml"));
                        Parent modifyPostView = loader.load();
                        Scene scene = new Scene(modifyPostView);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        ModifyPostController modifyPostController = loader.getController();

                        // Pass the selected post object to the ModifyPostController
                        modifyPostController.initData(post);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //ModifyPostButton.setVisible(true);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Modify Post");
                    alert.setContentText("You can't Modify the post, you're not the creator!");
                    alert.showAndWait();
                }
            });
            // Add the ModifyPostButton button to the postBox

            postBox.getChildren().add(ModifyPostButton);

            HBox buttonBox = new HBox();
            buttonBox.getChildren().addAll(button1, ModifyPostButton, deletePostButton);

            buttonBox.setSpacing(10); // Set the spacing between the buttons

            VBox likesBox = new VBox();
            likesBox.getChildren().addAll(likesLabel, buttonBox);
            postBox.getChildren().add(likesBox);

            tfpostlist.getChildren().add(postBox);
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

}
