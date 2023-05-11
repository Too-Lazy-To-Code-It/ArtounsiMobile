/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIposts;

import models.Post;
import models.PostLike;
import Service.PostService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Logged;
import models.grosMots;

/**
 * FXML Controller class
 *
 * @author amine
 */
 

public class AfficherpostdetailsController implements Initializable {

    @FXML
    private ImageView imgProd;

    /**
     * Initializes the controller class.
     */
    
    private Post post;
    @FXML
    private Label TitrePost;
    @FXML
    private Label Description;
    @FXML
    private Label Likes;
    @FXML
    private Button Like;
    @FXML
    private Button Unlike;
    @FXML
    private Button deletePostButton;
    @FXML
    private Button ModifyPostButton;
    @FXML
    private Button ViewComments;
    @FXML
    private Button AddComment;
    
//    @FXML
//    private Button Like;
    private PostService postService;
     public void initData(Post post) {
        this.post = post;
        //PostTitle.setText(post.getTitle());
        
        //label
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      this.post = post;
      
      ;
        
         }
    public AfficherpostdetailsController(){
        postService = new PostService();
    }
       public void loaddata(Post g) throws SQLException {
           //BLOG CHANGE IT AFTER THIS TO EXPLORE TYPE 
        TitrePost.setText(g.getTitle());
        Description.setText(g.getDescription_p());
        //TESTING FOR LIKES
        List<PostLike> likes = postService.Number_Of_Likes_For_A_Post_Post(g.getId_post());
       // String  numberOfLikes = Integer.toString(likes.size());
        int numberOfLikes = likes.size();
        String  LikesNumber = Integer.toString(numberOfLikes);
       Likes.setText((LikesNumber));
        //Likes.setText((g.getPost_typ()));

  Like.setOnAction((ActionEvent e) -> {
      g.setId_post(g.getId_post());
      System.out.println("a"+g.getId_post());
      g.setId_user(Logged.get_instance().getUser().getID_User()); // set the user ID of the user who liked the post 
       postService.addLike(g);
       //updateLikesLabel(Likes, post.getId_post()); // update the label displaying the number of likes
       updateLikesLabel(Likes, g.getId_post());
    });
  
  Unlike.setOnAction((ActionEvent e) -> {
      g.setId_post(g.getId_post());
      System.out.println("a"+g.getId_post());
      String title = g.getTitle();
      int CreaterUserId =postService.getIdUserByTitle(title);
      int extracet = postService.getLikeIdsForPost( g.getId_post()).get(0);
      if ((postService.getUserIdsByPostId(extracet).get(0) == Logged.get_instance().getUser().getID_User())){
                System.out.println("user" + postService.getUserIdsByPostId(extracet).get(0));
                postService.deleteLike(g.getId_post(), postService.getUserIdsByPostId(extracet).get(0)); // call the deleteLike method to remove the like
                updateLikesLabel(Likes, g.getId_post());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Unlike Post");
                alert.setContentText("You can't Unlike post, If you didn't like it!");
                alert.showAndWait();
            }
    });
    deletePostButton.setOnAction((ActionEvent e) -> {
       // Post p = new Post();
                g.setTitle(g.getTitle());
                String title = g.getTitle();
                int CreaterUserId = postService.getIdUserByTitle(title);
                if (CreaterUserId == Logged.get_instance().getUser().getID_User()) {
                    postService.deletePost(g.getId_post()); // call the deletePost method to delete the post
                   // tfpostlist.getChildren().remove(postBox); // remove the VBox representing the deleted post from the main VBox
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Delete Post");
                    alert.setContentText("You can't delete the post, you're not the creator!");
                    alert.showAndWait();
                }
    });
    
    
    
    ModifyPostButton.setOnAction(event -> {

              g.setTitle(g.getTitle());
              g.setId_user( g.getId_user());
              
             String title = g.getTitle();
               
                System.out.println("hey" + g.getId_user());
                System.out.println("id" + postService.getIdUserByTitle(title));
                //System.out.println("title"+title);
                int CreaterUserId =postService.getIdUserByTitle(title);
                if ((CreaterUserId == Logged.get_instance().getUser().getID_User())){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/ModifyPost.fxml"));
                    Parent modifyPostView = loader.load();
                    Scene scene = new Scene(modifyPostView);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    ModifyPostController modifyPostController = loader.getController();

                    // Pass the selected post object to the ModifyPostController
                    modifyPostController.initData(g);
                    
                    
                } 
                
                catch (IOException e) {
                    e.printStackTrace();
                }
                 //ModifyPostButton.setVisible(true);
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Modify Post");
                    alert.setContentText("You can't Modify the post, you're not the creator!");
                    alert.showAndWait();
                }
            });

    ViewComments.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/VueCommentPost.fxml"));
                Parent root = loader.load();
                VueCommentPostController controller = loader.getController();
                controller.setId_post(g.getId_post()); // set the postId in the VueCommentPostController instance

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();         
            } catch (IOException e)  {
                e.printStackTrace();
            }
        });
    
    AddComment.setOnAction(event -> {
        System.out.println("id user"+Logged.get_instance().getUser().getID_User());
        System.out.println("id post"+g.getId_post());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/AddAComment.fxml"));
                    Parent addCommentView = loader.load();
                    AddACommentController addCommentController = loader.getController();
                    addCommentController.setId_post(g.getId_post());
                    addCommentController.setId_user(Logged.get_instance().getUser().getID_User());
                    Scene scene = new Scene(addCommentView);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        
        
        
        String mediaPath = g.getMedia();
        
        File mediaFile = new File(mediaPath);
        if (mediaFile.exists() && mediaFile.isFile()) {
            imgProd.setImage(new Image(mediaFile.toURI().toString()));
        }
    }
        private void updateLikesLabel(Label Likes, int postId) {
        List<PostLike> likes = postService.Number_Of_Likes_For_A_Post_Post(postId);
        int numberOfLikes = likes.size();
        String  LikesNumber = Integer.toString(numberOfLikes);
        Likes.setText((LikesNumber));
    }
      public void handleAddComment(ActionEvent event) {
    try {
        // Load the AddAComment.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/AddAComment.fxml"));
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