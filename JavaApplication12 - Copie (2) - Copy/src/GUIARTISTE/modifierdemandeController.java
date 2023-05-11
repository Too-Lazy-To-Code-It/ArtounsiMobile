/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIARTISTE;

import static GUIARTISTE.AjouterdemandeController.file;
import controller.ModifierOffreController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Category;
import models.demandeTravail;
import models.offreTravail;
import service.CategoryService;
import service.demandeTravailService;
import static service.demandeTravailService.verifdemande;
import service.offreTravailService;

/**
 * FXML Controller class
 *
 * @author nour2
 */
public class modifierdemandeController implements Initializable {

    @FXML
    private TextField titreOffre;
    @FXML
    private TextArea descriptionOffre;
    @FXML
    private ChoiceBox<Category> listeCategorie;
   
    @FXML
    private Button modifier;
    static FileChooser fileChooser = new FileChooser();
  

    static File file;
   CategoryService c = new CategoryService();
   demandeTravailService offs= new   demandeTravailService();
   demandeTravail of = new demandeTravail();
    @FXML
    private Button pdfs;
   

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
      
   
}
public void getid(int i,String titre,String desc,String categ,String pdf){
             modifier.setId(Integer.toString(i));
titreOffre.setText(titre);
descriptionOffre.setText(desc);
pdfs.setId(pdf);
listeCategorie.setValue(c.fetchCategoryByNom(categ));
}
  

    @FXML
    private void modifierOffre(ActionEvent event) {
          int id= Integer.parseInt(modifier.getId());
           Category c = new Category(); 
          
            
            if ( file!=null)
              {String fileName1 = file.getName();
                
                    try {
                          // Copy the file to the XAMPP htdocs directory
                          Path sourcePath = file.toPath();
                          Path targetPath = Paths.get("C:/xampp2/htdocs/uploads/" + fileName1);
                          Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                          
                          of.setPdf(fileName1);
                    } catch (IOException ex) {
                        Logger.getLogger(modifierdemandeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
}else  {of.setPdf(pdfs.getId());}
if(titreOffre.getText().matches("\\d+")) {
    Alert alert = new Alert(Alert.AlertType.ERROR, "veuiller entre un titre valide");
        alert.showAndWait();}
        else if (descriptionOffre.getText().matches("\\d+"))
                { Alert alert = new Alert(Alert.AlertType.ERROR, "Veuiller entrer une description valide");
        alert.showAndWait();}
else  if ("".equals(titreOffre.getText())||"".equals(descriptionOffre.getText()) ||descriptionOffre.getText().isEmpty()||titreOffre.getText().isEmpty())
{ Alert alert = new Alert(Alert.AlertType.ERROR, "Veuiller remplir tous les champs");
        alert.showAndWait();}
else{
              try {
           
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIARTISTE/menuartiste.fxml"));
                  Parent root = loader.load();
                  
           of.setIdDemande(id);
        of.setTitreDemande(titreOffre.getText());
      
        CategoryService cs = new CategoryService();
       c= cs.fetchCategoryByNom(listeCategorie.getValue().getName_category());
        of.setDescriptionDemande(descriptionOffre.getText());
        of.setCategorieDemande(c);
       
       //fromtoken of.setIdStudio(2);
    
           offs.modifierDemande(of);
            if(verifdemande==true) { Scene scene = new Scene(root,1380,700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);}
                
              } catch (IOException ex) {
                  Logger.getLogger(ModifierOffreController.class.getName()).log(Level.SEVERE, null, ex);
              }
    

}
    }

    @FXML
    private void modifiercv(ActionEvent event) {
           //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("pdf files (*.pdf)", "*.pdf");
       
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

    }
}