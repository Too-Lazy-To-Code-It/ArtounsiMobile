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
public class FXML_FetchParticipations2Controller implements Initializable {

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
            
        if(Logged.get_instance().getUser()==null){
                           vbox.getChildren().remove(imported_img);
                vbox.getChildren().remove(new_desc);
                vbox.getChildren().remove(participate_id);
            }
        

        if(Logged.get_instance().getUser()!=null)
            if(Logged.get_instance().getUser().getType().equals("Observator")){
                vbox.getChildren().remove(imported_img);
                vbox.getChildren().remove(new_desc);
                vbox.getChildren().remove(participate_id);
            }
            
        Participation_Grid.getChildren().clear();
        participations = pi.fetchParticipantionsByChallenge(c.getID_Challenge());
        Participation p = new Participation();
        
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

    void getChallenge(Challenge challenge,Participation p) {
        this.c=challenge;
        challenge_desc.setText(c.getDescription());
        File file = new File("C:\\xampp\\htdocs\\img\\"+challenge.getPathIMG());
        Image img = new Image(file.toURI().toString());
        challenge_img.setImage(img);
        afficher_Participations();
        
        this.p=p;
        imported_img.setText(p.getIMG_Participation());
        new_desc.setText(p.getDescription());
    }
    
    @FXML
    private void participate(ActionEvent event) throws IOException {
        if(new_desc.getText().length()==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs");
            alert.show();
            return;
        }
        else{
            if(imported){
            Files.copy(Paths.get(src), Paths.get(dest));}
            System.out.println(p);
                        this.p.setParticipant(Logged.get_instance().getUser());

            this.p.setDescription(new_desc.getText());
            this.p.setChallenge(c);
            pi.modifyParticipation(this.p);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_FetchParticipation2.fxml"));
            Parent view_2=loader.load();
            FXML_FetchParticipations2Controller fetchParticipations2Controller=loader.getController();
            fetchParticipations2Controller.getChallenge(c,p);
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(view_2);
            stage.setScene(scene);
            stage.show();
        }
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