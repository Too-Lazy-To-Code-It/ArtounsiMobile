
package GUIuser;

import GUIgrotmot.*;
import GUIARTISTE.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
public class MesdemandesController implements Initializable {
   demandeTravailService of = new demandeTravailService ();
    private List<demandeTravail> mesoffres;
    @FXML
    private GridPane citiesGril;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //id connecter
        mesoffres=new ArrayList<>(of.fetchDemandesPerIdDate(Logged.get_instance().getUser().getID_User()
));
       int column=0;
       int row = 1 ;
       for(demandeTravail f : mesoffres)
       {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/GUIARTISTE/mesdemandesitems.fxml"));
               Pane pane = fxmlLoader.load();
              MesdemandesitemsController ac = fxmlLoader.getController();
               ac.loaddata(f);
               if(column==1){column=0; ++row ;}
               citiesGril.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
           } catch (IOException ex) {
               Logger.getLogger(MesdemandesController.class.getName()).log(Level.SEVERE, null, ex);
           }

       }
     
    }    
     
    
}
