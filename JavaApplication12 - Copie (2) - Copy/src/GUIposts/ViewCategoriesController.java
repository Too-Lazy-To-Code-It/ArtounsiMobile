/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIposts;

import models.Category;
import interfaces.CategoryInterface;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import service.CategoryService;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class ViewCategoriesController implements Initializable {

    @FXML
    private ListView<String> Categorieslabel;

    private CategoryInterface categoryService = new CategoryService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Category> categories = categoryService.fetchCategories();
        
        for (Category category : categories) {
            Categorieslabel.getItems().add(category.getName_category());
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
