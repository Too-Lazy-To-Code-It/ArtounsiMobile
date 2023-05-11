package GUIposts;

import models.Category;
import interfaces.CategoryInterface;
import service.CategoryService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddCategoryController implements Initializable {
    //var
    CategoryInterface cat = new CategoryService();

    //widgets
    @FXML 
    private TextField AddCategoryName;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void AddCategory(ActionEvent event) {
        String categoryName = AddCategoryName.getText();
        if (categoryName.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Input");
            alert.setContentText("Please enter a category name.");
            alert.showAndWait();
        } else {
            Category c = new Category();
            c.setName_category(categoryName);
            cat.addCategory2(c);
        } 
    }
        @FXML
     public void handleReturn(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/CategoryControlPanel.fxml"));
     Scene scene = new Scene(root);
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     stage.setScene(scene);
     stage.show();
 }
}