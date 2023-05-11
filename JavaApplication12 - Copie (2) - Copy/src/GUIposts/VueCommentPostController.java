package GUIposts;

import models.Comment;
import Interfaces.CommentInterface;
import Service.CommentService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Logged;



public class VueCommentPostController implements Initializable {

    
   
    @FXML
    private VBox commentVBox;
    @FXML
    private ScrollPane commentScrollPane;
    @FXML
    private TextField OldComment;
    private CommentService commentService;

    private int postID;
    CommentInterface com = new CommentService();
    private Comment comment;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setId_post(int id) {
        postID = id;
        System.out.println("Setting post ID to: " + postID);
       //System.out.println("list of user " + commentService.getUserIdsByPostId(postID));
        List<Comment> comments = com.fetchCommentByPostId(postID);
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                CommentLabel commentLabel = new CommentLabel(comment);
                commentVBox.getChildren().add(commentLabel);
            }
        }
    }
    public VueCommentPostController(){
    commentService = new CommentService();
}
    
     public void initData(Comment comment) {
        this.comment = comment;
        //PostTitle.setText(post.getTitle());
        OldComment.setText(comment.getComment());
        
        
    }
   

    public class CommentLabel extends Label {
        private Comment comment;
        private Button modifyButton;
        private Button deleteButton;
        private Button TranslateToArabic;

        public CommentLabel(Comment comment) {
            this.comment = comment;
            setText(comment.getComment());

            modifyButton = new Button("Modify");
            modifyButton.setPrefHeight(26.0);
            modifyButton.setPrefWidth(80.0);
            modifyButton.setStyle("-fx-background-color: C10C99;");
            modifyButton.setTextFill(Color.WHITE);

            deleteButton = new Button("Delete");
            deleteButton.setPrefHeight(26.0);
            deleteButton.setPrefWidth(80.0);
            deleteButton.setStyle("-fx-background-color: C10C99;");
            deleteButton.setTextFill(Color.WHITE);

    modifyButton.setOnAction(event -> {
        Comment c = new Comment();
        c.setComment(comment.getComment());
        String TheComment = comment.getComment();
        String comm = comment.getComment();
        int id_commet = commentService.getIdCommentByComment(TheComment);
        c.setId_comment(id_commet);
        int id_commented_user = commentService.getIdUserByCommentId(id_commet);
        //int id_commented_user =1;
        System.out.println("id of the user who commeted " + id_commented_user);
        if ((id_commented_user == Logged.get_instance().getUser().getID_User())){
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/ModifyComment.fxml"));
            Parent modifyCommentView = loader.load();
            Scene scene = new Scene(modifyCommentView);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            ModifyCommentController modifyCommentController = loader.getController();

            // Pass the selected comment object to the ModifyCommentController
            modifyCommentController.initData(comment);

        } catch (IOException e) {
            e.printStackTrace();
        }
        }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Modify Comment");
                    alert.setContentText("You can't Modify the Comment, you're not  not the one who wrote it!");
                    alert.showAndWait();
                }
    });
        
        
        
            deleteButton.setOnAction(event -> {
                
            Comment c = new Comment();
            c.setComment(comment.getComment());
            String TheComment = comment.getComment();
            String comm = comment.getComment();
            int id_commet = commentService.getIdCommentByComment(TheComment);
            c.setId_comment(id_commet);
            int id_commented_user = commentService.getIdUserByCommentId(id_commet);
            //int id_commented_user =1;
                
                System.out.println("the comment baby" +comment.getComment());
                //String TheComment = comment.getComment();
                System.out.println("the id  baby" +commentService.getIdCommentByComment(TheComment));
                int TheIdOfComment=commentService.getIdCommentByComment(TheComment);
               if ((id_commented_user == Logged.get_instance().getUser().getID_User())){
                commentService.deleteComment(TheIdOfComment);
               }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Delete Comment");
                    alert.setContentText("You can't Delete the Comment, you're not the one who wrote it!");
                    alert.showAndWait();
                }
            });
            

            HBox buttons = new HBox(10.0, modifyButton, deleteButton);
            setGraphic(buttons);
            setWrapText(true);
        }
    }
//
//    @FXML
//    private void postComment() {
//        // TODO: add the comment to the database and display it in the commentVBox
//        String commentText = commentTextField.getText();
//        Label commentLabel = new Label(commentText);
//        commentVBox.getChildren().add(commentLabel);
//        commentTextField.clear();
//    }
    
      @FXML
     public void handleReturn(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/PostControlPanel.fxml"));
     Scene scene = new Scene(root);
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     stage.setScene(scene);
     stage.show();
 }
}