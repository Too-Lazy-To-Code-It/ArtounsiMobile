/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIposts;

import interfaces.CategoryInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import service.CategoryService;
import java.util.List;
import models.Category;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;


/**
 * FXML Controller class
 *
 * @author amine
 */
public class AffichercategoryController implements Initializable {
    
    private CategoryInterface categoryService = new CategoryService();
    private List<Category> category;
    @FXML
    private GridPane citiesGril;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //grosmots=new ArrayList<>(mot.fetchgrosmots());
        category=new ArrayList<>(categoryService.fetchCategories());
        int column=0;
       int row = 1 ;
       //for(grosMots g : grosmots)
       for(Category c :category ) {
           try {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("affichercategorydetails.fxml"));
               Pane pane = fxmlLoader.load();
               AffichercategorydetailsController ac = fxmlLoader.getController();
               ac.loaddata(c);
               if(column==3){column=0; ++row ;}
               citiesGril.add(pane,column++,row);
               GridPane.setMargin(pane, new Insets(20));
           } catch (IOException ex) {
               //Logger.getLogger(Menu1Controller.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SQLException ex) {
                Logger.getLogger(AffichercategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    }    
    
}
