/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Category;
import models.offreTravail;
import service.CategoryService;
import service.offreTravailService;
import static service.offreTravailService.verif;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class ModifierOffreController implements Initializable {

    @FXML
    private TextField titreOffre;
    @FXML
    private TextField descriptionOffre;
    @FXML
    private ChoiceBox<Category> listeCategorie;
    @FXML
    private ChoiceBox<String> listetypes;
    @FXML
    private ChoiceBox<String> villeliste;
    @FXML
    private Button modifier;
   CategoryService c = new CategoryService();
   offreTravailService offs= new   offreTravailService();
    offreTravail of = new offreTravail();
    @FXML
    private AnchorPane anchormodifier;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      getdata();
    }       

public void getdata()
        
{
    listeCategorie.getItems().addAll(c.fetchCategories());
       String[]types={"freelance","contract","other"};
     
           
String[]villes= { "tunisie","Oman","Koweït","Algérie","États-Unis", "Chine", "Maroc", "Russie", "Japon","Iran","Iraq", "Irlande", "Italie", "Arabie Saoudite","Argentine", "France", "Canada", "Autres"};

       listetypes.getItems().addAll(types);
      villeliste.getItems().addAll(villes);
   
}
public void getid(int i,String titre,String desc,String type,String loc,String categ){
             modifier.setId(Integer.toString(i));
titreOffre.setText(titre);
descriptionOffre.setText(desc);
listetypes.setValue(type);
villeliste.setValue(loc);
listeCategorie.setValue(c.fetchCategoryByNom("2d art"));
}
  

    @FXML
    private void modifierOffre(ActionEvent event) {
          int id= Integer.parseInt(modifier.getId());
           Category c = new Category(); 
          if(titreOffre.getText().matches("\\d+")) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entre un titre valide");
        alert.showAndWait();}
        else if (descriptionOffre.getText().matches("\\d+"))
                { Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entrer une dscription valide");
        alert.showAndWait();}
else  if ("".equals(titreOffre.getText())||"".equals(descriptionOffre.getText()) ||descriptionOffre.getText().isEmpty()||titreOffre.getText().isEmpty())
{ Alert alert = new Alert(Alert.AlertType.ERROR, "Veuiller remplir tous les champs");
        alert.showAndWait();}
else{
              try {
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/menu1.fxml"));
                  Parent root = loader.load();
                  
                  of.setIdOffre(id);
                  of.setTitreOffre(titreOffre.getText());
                  
                  CategoryService cs = new CategoryService();
                  c= cs.fetchCategoryByNom(listeCategorie.getValue().getName_category());
                  of.setDescriptionOffre(descriptionOffre.getText());
                  of.setCategorieOffre( c);
                  of.setLocalisationOffre(villeliste.getValue());
                  of.setTypeOffre(listetypes.getValue());
                  of.setIdStudio(2);
                  
                  offs.modifierOffre(of);
               if(verif==true)
               {Scene scene = new Scene(root,1380,700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);}
                
              } catch (IOException ex) {
                  Logger.getLogger(ModifierOffreController.class.getName()).log(Level.SEVERE, null, ex);
              }
        }
          
    }

}
