/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import models.Tutoriel;
import models.Video;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class TutorielItemController implements Initializable {

    @FXML
    private ImageView tutorial_image;
    @FXML
    private Label tutorial_title;
    
    Tutoriel tutoriel = new Tutoriel();
    @FXML
    private Label tutorial_creator;
    @FXML
    private Label categorie_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void showTutorial(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Fetch_Tutoriel.fxml"));
        Parent view_2=loader.load();
        FXML_Fetch_TutorielController Fetch_TutorielController=loader.getController();

        Fetch_TutorielController.setTutorial(tutoriel);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
    }
    
    void setData(Tutoriel tutoriel) {
        File file = new File("C:\\xampp\\htdocs\\img\\"+tutoriel.getPathImg());
        Image img = new Image(file.toURI().toString());
        tutorial_image.setImage(img);
        tutorial_title.setText(tutoriel.getTitle());
        tutorial_creator.setText(tutoriel.getCreator().getName());
        categorie_label.setText(tutoriel.getCategorie().getName_category());
        this.tutoriel = tutoriel;
    }
}
