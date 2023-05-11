/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.Category;
import models.offreTravail;
import service.CategoryService;
import service.offreTravailService;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import models.Logged;
import static service.offreTravailService.verifajout;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class AjouterOffreController implements Initializable {
    offreTravailService offs= new   offreTravailService();
    offreTravail of = new offreTravail();
CategoryService c = new CategoryService();
    @FXML
    private ChoiceBox<Category> listeCategorie= new ChoiceBox<>();;
    @FXML
    private ChoiceBox<String> listetypes;
    @FXML
    private ChoiceBox<String> villeliste;
    @FXML
    private TextField titreOffre;
    @FXML
    private TextArea descriptionOffre;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loaddata();
    }    
     public void loaddata(){
       listeCategorie.getItems().addAll(c.fetchCategories());
       String[]type={"freelance","contract","other"};
String[] ville = { "tunisie","Oman","Koweït","Algérie","États-Unis", "Chine", "Maroc", "Russie", "Japon","Iran","Iraq", "Irlande", "Italie", "Arabie Saoudite","Argentine", "France", "Canada", "Autres"};
       listetypes.getItems().addAll(type);
      villeliste.getItems().addAll(ville);
       
    }

    @FXML
    private void ajouterOffre(ActionEvent event) {
     

if (titreOffre.getText().matches("\\d+")) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entre un titre valide");
        alert.showAndWait();}
        else if (descriptionOffre.getText().matches("\\d+"))
                { Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entrer une dscription valide");
        alert.showAndWait();}
else {
    // Le contenu du TextField n'est pas un entier

        of.setTitreOffre(titreOffre.getText());
       
        of.setDescriptionOffre(descriptionOffre.getText());
        of.setCategorieOffre(listeCategorie.getValue());
        of.setLocalisationOffre(villeliste.getValue());
        of.setTypeOffre(listetypes.getValue());
       /*getfrom token */ of.setIdStudio(Logged.get_instance().getUser().getID_User()
);
      
           offs.addOffre(of);
          if (verifajout==true){
String myVariable = "";
descriptionOffre.setText(myVariable);
	titreOffre.setText(myVariable);
   
   listeCategorie.setValue(null);
      listetypes.setValue(null);
    
     villeliste.setValue(null);}
	
	
	
    }
    }
   
}
