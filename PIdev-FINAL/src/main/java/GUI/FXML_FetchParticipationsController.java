/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Challenge;
import models.Participation;
import interfaces.ParticipationInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AllUsers;
import models.Logged;
import service.ParticipationService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_FetchParticipationsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Challenge c = new Challenge();
    Participation p = new Participation();
    ParticipationInterface pi = new ParticipationService();
    
    @FXML
    private TextArea new_desc;
    @FXML
    private Label challenge_desc;
    @FXML
    private ImageView challenge_img;
    
    private String src;
    private String dest;
    private List<Participation> participations;
    
    @FXML
    private Button imported_img;
    private boolean imported=false;
    private AllUsers u= new AllUsers();
    @FXML
    private GridPane Participation_Grid;
    @FXML
    private VBox vbox;
    @FXML
    private Button participate_id;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
        private void afficher_Participations() {
        Participation_Grid.getChildren().clear();
        participations = pi.fetchParticipantionsByChallenge(c.getID_Challenge());
        
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<participations.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXML_Item_p.fxml"));
            
            AnchorPane item = fxmlLoader.load();
           
            
            FXML_Item_pController participationItemController = fxmlLoader.getController();
            participationItemController.setData(participations.get(i));
            
            if(columns == 1){
                columns = 0 ;
                ++rows;
            }
            
            Participation_Grid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    void getChallenge(Challenge challenge) {
        this.c=challenge;
        challenge_desc.setText(c.getDescription());
        File file = new File("C:\\xampp\\htdocs\\img\\"+challenge.getPathIMG());
        Image img = new Image(file.toURI().toString());
        challenge_img.setImage(img);
        afficher_Participations();
    }
    
    @FXML
    private void participate(ActionEvent event) throws IOException {
        if(new_desc.getText().length()==0||!imported){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs");
            alert.show();
            return;
        }
        else{
            Files.copy(Paths.get(src), Paths.get(dest)); 
            p.setDescription(new_desc.getText());
            p.setParticipant(Logged.get_instance().getUser());
            p.setChallenge(c);
            pi.addParticipation(p);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("./FetchChallenges.fxml"));
            Parent view_2=loader.load();
            Scene scene = new Scene(view_2);
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        pi.addParticipation(p);
    }

    @FXML
    private void importImage(ActionEvent event) {
           FileChooser fc = new FileChooser();
           FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG Files","*.png");
           FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG Files","*.jpg");

           fc.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);
           
           File selectedFile = fc.showOpenDialog(null);
           
           if(selectedFile != null) {
               src = selectedFile.getPath();
               dest = "C:\\xampp\\htdocs\\img\\"+selectedFile.getName();
               imported_img.setText(selectedFile.getName());
               p.setIMG_Participation(selectedFile.getName());
               imported = true;
           } else {
               System.err.println("file is not valid");
           }
    }
    
}
