/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Challenge;
import interfaces.CategoryInterface;
import interfaces.CategoryInterface;
import interfaces.ChallengeInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.NewFXMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Logged;
import org.controlsfx.control.CheckComboBox;
import service.CategoryService;
import service.ChallengeService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FetchChallengesController implements Initializable {

    //ChallengeInterface ci = new ChallengeService();
    //List<Challenge> Challenges = ci.fetchChallenges();
    ChallengeInterface chi = new ChallengeService();

    ObservableList list = FXCollections.observableArrayList();

    private ListView<Challenge> listView;
    @FXML
    private ScrollPane challengesPane;
    @FXML
    private GridPane challengeGrid;
    private List<Challenge> challenges;
    @FXML
    private HBox addButton;
    @FXML
    private TextField search;

    CategoryInterface ci = new CategoryService();
    @FXML
    private CheckComboBox<String> cat;
    @FXML
    private HBox hbox;
    @FXML
   private AnchorPane anchorfetchchallenge;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(Logged.get_instance().getUser().getType().equals("Observer")){
            hbox.getChildren().remove(addButton);
        }
        
        afficher_Challenge();
        list.removeAll(list);
        ci.fetchCategories().stream().forEach(e -> list.add(e.getName_category()));
        cat.getItems().addAll(list);

    }

    @FXML
    private void afficher_Challenge() {

        String checkedCategories = cat.getCheckModel().getCheckedItems().stream().reduce("(", (a, b) -> a + "'" + b + "',");
        checkedCategories = checkedCategories.substring(0, checkedCategories.length() - 1) + ")";
        challengeGrid.getChildren().clear();
        if (search.getText() == "" && checkedCategories.equals(")")) {
            challenges = chi.fetchChallenges();
        } else if (!checkedCategories.equals(")")) {
            challenges = chi.fetchChallengeByCategorie(checkedCategories);
        } else {
            challenges = chi.fetchChallengeByName(search.getText());
        }
        int columns = 0;
        int rows = 0;
        try {
            for (int i = 0; i < challenges.size(); i++) {
                AnchorPane item;
                
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("item.fxml"));

                    item = fxmlLoader.load();

                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(challenges.get(i));

                if (columns == 1) {
                    columns = 0;
                    ++rows;
                }

                challengeGrid.add(item, columns++, rows);
            }
        } catch (IOException ex) {
            Logger.getLogger(FetchChallengesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addChallenge(MouseEvent event) throws IOException {
           try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("./FXML_ADD_Challenge.fxml"));
            anchorfetchchallenge.getChildren().add(pane);

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
