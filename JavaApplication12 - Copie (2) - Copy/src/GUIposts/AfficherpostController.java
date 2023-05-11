    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package GUIposts;

    import models.Post;
    import GUIARTISTE.AfficherController;
    import GUIARTISTE.AfficherdemandesitemsController;
    import Service.PostService;
    import controller.Menu1Controller;
    import java.io.IOException;
    import java.net.URL;
    import java.sql.SQLException;
    import java.util.List;
    import java.util.ResourceBundle;
    import java.util.logging.Level;
    import java.util.logging.Logger;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.geometry.Insets;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.Pane;

    /**
     * FXML Controller class
     *
     * @author amine
     */
    public class AfficherpostController implements Initializable {

        @FXML
        private GridPane citiesGril;
        private PostService postService;
        /**
         * Initializes the controller class.
         */
        public AfficherpostController() {
            postService = new PostService();
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            List<Post> posts = postService.fetchPortfolioPostDetails();//change agter this to .fetchPortfolioPostDetails
           int column=0;
           int row = 1 ;
           for(Post post : posts){
               try {
                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getResource("/GUIposts/afficherpostdetails.fxml"));
                   Pane pane = fxmlLoader.load();
                   AfficherpostdetailsController ac = fxmlLoader.getController();
                   //ac.loaddata(post);
                   ac.loaddata(post);

                   if(column==3){column=0; ++row ;}
                   citiesGril.add(pane,column++,row);
                   GridPane.setMargin(pane, new Insets(20));
               } catch (IOException ex) {
                   Logger.getLogger(Menu1Controller.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                    Logger.getLogger(AfficherpostController.class.getName()).log(Level.SEVERE, null, ex);
                }
           }
        }    

    }