
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import models.Logged;
import models.demandeTravail;
import models.offreTravail;
import service.demandeTravailService;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class MesOffresController implements Initializable {
   offreTravailService of = new offreTravailService ();
    private List<offreTravail> mesoffres;
    @FXML
  private GridPane citiesGril;
    @FXML
    public static AnchorPane offresancho;
    @FXML
    private Button showall;
    @FXML
    private Button popup;
    @FXML
    private Pane CRUD;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mesoffres=new ArrayList<>(of.fetchOffresPerIdDate(Logged.get_instance().getUser().getID_User()));
       int column=0;
       int row = 1 ;
       for(offreTravail f : mesoffres)
       {
           try {
            
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUI/mesOffesitems.fxml"));
               Pane pane = fxmlLoader.load();
              MesOffesitemsController ac = fxmlLoader.getController();
               ac.loaddata(f);
               if(column==1){column=0; ++row ;}
               citiesGril.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
               
           } catch (IOException ex) {
               Logger.getLogger( MesOffresController.class.getName()).log(Level.SEVERE, null, ex);
           }

       }
    
    }    

    


}
