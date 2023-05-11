package GUIposts;

import Entity.Category;

import Entity.Category;
import interfaces.CategoryInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.CategoryService;

/**
 * FXML Controller class
 *
 */
public class DeleteCategoryController implements Initializable {
    //var
    CategoryInterface cat = new CategoryService();

    @FXML
    private TextField DeleteCategoryName;
    String DeletedCategoryName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void DeleteCategory(ActionEvent event) {
        String categoryName = DeleteCategoryName.getText();
        if (categoryName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Category name cannot be empty!");
            alert.showAndWait();
            return;
        }
        
        Category c = new Category();
        c.setName_category(categoryName);
        cat.deleteCategory(categoryName);
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