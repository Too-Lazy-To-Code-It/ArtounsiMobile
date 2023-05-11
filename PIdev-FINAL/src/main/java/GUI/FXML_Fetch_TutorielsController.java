/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Tutoriel;
import interfaces.CategoryInterface;
import interfaces.FavorisTutorielInterface;
import interfaces.TutorielInterface;
import interfaces.ViewInterface;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.NewFXMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Logged;
import models.Video;
import org.controlsfx.control.CheckComboBox;
import service.CategoryService;
import service.FavorisTutorielService;
import service.TutorielService;
import service.ViewService;
// linear-gradient(from 25px 25px to 35px 70px, #c10c99,  #071330)
/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_Fetch_TutorielsController implements Initializable {

    FavorisTutorielInterface ft = new FavorisTutorielService();
    TutorielInterface ti = new TutorielService();
    ViewInterface vvi = new ViewService();
    @FXML
    private HBox addButton1;
    private TextField search;
    @FXML
    private GridPane tutorielGrid;
    @FXML
    private HBox addButton;
    
    private List<Tutoriel> tutoriels;
    @FXML
    private TextField searching;
    private ComboBox<String> categorie;
    
    ObservableList list = FXCollections.observableArrayList();
    
    CategoryInterface ci = new CategoryService();
    @FXML
    private VBox vBox;
    @FXML
    private HBox showMyTutorials_id;
    @FXML
    private CheckComboBox<String> cat;
    @FXML
    private HBox showFavoris_id;
    @FXML
    private HBox showHistory_id;
    @FXML
    private AnchorPane tutorielleanchorpane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(Logged.get_instance().getUser().getType().equals("Observer")){
            vBox.getChildren().remove(addButton);
            vBox.getChildren().remove(showMyTutorials_id);
        }
        
        list.removeAll(list);
        ci.fetchCategories().stream().forEach(e -> list.add(e.getName_category()));
        cat.getItems().addAll(list);
        
        if(searching.getText().length()!=0)
            search();
        else
            afficher_Tutoriels();
    }    
    
    private void afficher_Tutoriels() {
        showFavoris_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showMyTutorials_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showHistory_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        tutorielGrid.getChildren().clear();
        tutoriels = ti.fetchTutoriels();
        
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<tutoriels.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("TutorielItem.fxml"));
            
            AnchorPane item = fxmlLoader.load();
           
            
            TutorielItemController tutorielItemController = fxmlLoader.getController();
            tutorielItemController.setData(tutoriels.get(i));
            
            if(columns == 2){
                columns = 0 ;
                ++rows;
            }
            
            tutorielGrid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void search() {
        
        tutorielGrid.getChildren().clear();
        tutoriels = ti.fetchTutorielsByTitle(searching.getText());
        showFavoris_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showMyTutorials_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showHistory_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");

        String checkedCategories = cat.getCheckModel().getCheckedItems().stream().reduce("(", (a, b) -> a + "'" + b + "',");
        checkedCategories = checkedCategories.substring(0, checkedCategories.length() - 1) + ")";
        if (searching.getText() == "" && checkedCategories.equals(")")) {
            tutoriels = ti.fetchTutorielsByTitle(searching.getText());
        } else if (!checkedCategories.equals(")")) {
            tutoriels = ti.fetchTutorielsByCategorie(checkedCategories);
        } else {
            tutoriels = ti.fetchTutorielsByTitle(searching.getText());
        }
        
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<tutoriels.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("TutorielItem.fxml"));
            
            AnchorPane item = fxmlLoader.load();
           
            
            TutorielItemController tutorielItemController = fxmlLoader.getController();
            tutorielItemController.setData(tutoriels.get(i));
            
            if(columns == 2){
                columns = 0 ;
                ++rows;
            }
            
            tutorielGrid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void addTutoriel(MouseEvent event) throws IOException {
        /*FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_ADD_Tutoriel.fxml"));
        Parent view_2=loader.load();
        Scene scene = new Scene(view_2);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();*/
         try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("./FXML_ADD_Tutoriel.fxml"));
           tutorielleanchorpane.getChildren().add(pane);

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @FXML
    private void showFavoris(MouseEvent event) {
        tutorielGrid.getChildren().clear();
        tutoriels = ft.fetchFavorisTutorials();
        // linear-gradient(from 25px 25px to 35px 70px, #c10c99,  #071330)
        showMyTutorials_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showHistory_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showFavoris_id.setStyle("-fx-background-color: linear-gradient(from 25px 25px to 35px 70px, #c10c99,  #071330); -fx-cursor:hand");
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<tutoriels.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("TutorielItem.fxml"));
            
            AnchorPane item = fxmlLoader.load();
           
            
            TutorielItemController tutorielItemController = fxmlLoader.getController();
            tutorielItemController.setData(tutoriels.get(i));
            
            if(columns == 2){
                columns = 0 ;
                ++rows;
            }
            
            tutorielGrid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void showMyTutorials(MouseEvent event) {
        tutorielGrid.getChildren().clear();
        tutoriels = ti.fetchTutorielsByArtist(Logged.get_instance().getUser().getName());
        
        showFavoris_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showHistory_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showMyTutorials_id.setStyle("-fx-background-color: linear-gradient(from 25px 25px to 35px 70px, #c10c99,  #071330); -fx-cursor:hand");
        int columns=0;
        int rows=0;
        try {
        for(int i=0;i<tutoriels.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("TutorielItem.fxml"));
            
            AnchorPane item = fxmlLoader.load();
           
            
            TutorielItemController tutorielItemController = fxmlLoader.getController();
            tutorielItemController.setData(tutoriels.get(i));
            
            if(columns == 2){
                columns = 0 ;
                ++rows;
            }
            
            tutorielGrid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void showHistory(MouseEvent event) {
        showFavoris_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        showHistory_id.setStyle("-fx-background-color: linear-gradient(from 25px 25px to 35px 70px, #c10c99,  #071330); -fx-cursor:hand");
        showMyTutorials_id.setStyle("-fx-background-color: #071330; -fx-cursor:hand");
        tutorielGrid.getChildren().clear();
        List<Video> videos = vvi.fetchHistory();
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
            videoItemController.setData(videos.get(i),new Tutoriel());
            
            if(columns == 1){
                columns = 0 ;
                ++rows;
            }
            
            tutorielGrid.add(item, columns++, rows);
        }}
              catch (IOException ex) {
                Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
