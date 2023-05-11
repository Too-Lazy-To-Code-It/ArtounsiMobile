package GUIposts;

import models.Category;

import service.CategoryService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class AffichercategorydetailsController implements Initializable {
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    private Label titrecategory;

    private Category category;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loaddata(Category c) throws SQLException{
        this.category = c;
        titrecategory.setText(c.getName_category());
        supprimer.setId(Integer.toString(c.getId_Category()));
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this category?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int categoryId = Integer.parseInt(supprimer.getId());
            CategoryService cs = new CategoryService();
            cs.deleteCategoryById(categoryId);
            System.out.println("Category deleted successfully!");
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Success");
            alert2.setHeaderText(null);
            alert2.setContentText("Category deleted successfully!");
            alert2.showAndWait();
        } else {
            System.out.println("nope");
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCategory.fxml"));
            Parent root = loader.load();
            UpdateCategoryController controller = loader.getController();
            controller.setOldCategoryName(category.getName_category());
            Scene scene = new Scene(root);
            Stage stage = (Stage) modifier.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}