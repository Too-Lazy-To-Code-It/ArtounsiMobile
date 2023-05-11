/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
// linear-gradient(from 25px 25px to 35px 70px, #c10c99,  #071330)
import interfaces.FavorisTutorielInterface;
import models.Tutoriel;
import models.Video;
import interfaces.TutorielInterface;
import interfaces.VideoInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Logged;
import service.FavorisTutorielService;
import service.TutorielService;
import service.VideoService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_Fetch_TutorielController implements Initializable {

    FavorisTutorielInterface ft = new FavorisTutorielService();
    TutorielInterface ti = new TutorielService();
    VideoInterface vi = new VideoService();
    @FXML
    private ImageView tutoriel_img;
    @FXML
    private Label tutoriel_description;
    @FXML
    private Label tutoriel_title;
    @FXML
    private GridPane video_grid;
    @FXML
    private Label tutoriel_level;
    @FXML
    private HBox addButton;
    
    Tutoriel t = new Tutoriel();
    @FXML
    private Label tutorial_categorie;
    @FXML
    private ImageView star_img;
    @FXML
    private HBox hbox;
    @FXML
    private VBox vbox;
    @FXML
    private Button modify_but;
    @FXML
    private Button delete_but;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
        
        private void afficher_Tutoriel() {
        List<Video> videos = vi.fetchVideosByTutoriel(t.getID_Tutoriel());
        System.out.println("v : "+videos);
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<videos.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("VideoItem.fxml"));
            AnchorPane item = fxmlLoader.load();
           
            System.out.println(videos.get(i));
            VideoItemController videoItemController = fxmlLoader.getController();
            videoItemController.setData(videos.get(i),t);
            
            if(columns == 2){
                columns = 0 ;
                ++rows;
            }
            
            video_grid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void addVideo(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_ADD_Video.fxml"));
        Parent view_2=loader.load();
        FXML_ADD_VideoController add_videoController=loader.getController();
        add_videoController.setTutoriel(t);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void modifyTutoriel(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Modify_Tutoriel.fxml"));
        Parent view_2=loader.load();
        FXML_Modify_TutorielController Modify_TutorielController=loader.getController();
        Modify_TutorielController.getTutoriel(t);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void removeTutoriel(ActionEvent event) throws IOException {
        ti.deleteTutoriel(t.getID_Tutoriel());
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Fetch_Tutoriels.fxml"));
        Parent view_2=loader.load();
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
        
    }

    void setTutorial(Tutoriel tutoriel) {
        Image emptystar = new Image(getClass().getResourceAsStream("/img/emptystar.png"));
        Image star = new Image(getClass().getResourceAsStream("/img/star.png"));
        if(!ft.favorated(tutoriel))
            star_img.setImage(emptystar);
        else star_img.setImage(star);
        tutorial_categorie.setText(tutoriel.getCategorie().getName_category());
        tutoriel_title.setText(tutoriel.getTitle());
        tutoriel_description.setText(tutoriel.getDescription());
        tutoriel_level.setText(String.valueOf(tutoriel.getNiveau()));
        File file = new File("C:\\xampp\\htdocs\\img\\"+tutoriel.getPathImg());
        Image img = new Image(file.toURI().toString());
        tutoriel_img.setImage(img);
        this.t = tutoriel;
        afficher_Tutoriel();
        
         if(!(Logged.get_instance().getUser().getType().equals("Admin") || Logged.get_instance().getUser().getID_User()==t.getCreator().getID_User())){
            vbox.getChildren().remove(delete_but);
            vbox.getChildren().remove(modify_but);
            hbox.getChildren().remove(addButton);
        }
    }

    @FXML
    private void favorate(MouseEvent event) {
        Image emptystar = new Image(getClass().getResourceAsStream("/img/emptystar.png"));
        Image star = new Image(getClass().getResourceAsStream("/img/star.png"));
        if(!ft.favorated(t)){
            star_img.setImage(star);
            ft.addFavoris(t);
        }
        else{
            star_img.setImage(emptystar);
            ft.removeFavoris(t);
        }
    }
    
}
