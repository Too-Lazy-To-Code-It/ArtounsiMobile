/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUIposts;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author amine
 */
public class AmountController implements Initializable {

    @FXML
    private TextField Amount;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
     public void handleReturn(ActionEvent event) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("/GUIposts/CategoryControlPanel.fxml"));
     Scene scene = new Scene(root);
     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     stage.setScene(scene);
     stage.show();
 }
    public void handleDonate(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUIposts/fxmlpayement.fxml"));
    Parent root = loader.load();
    FxmlpayementController paymentController = loader.getController();
    double amount = Double.parseDouble(Amount.getText());
    
        System.out.println("amount"+amount);
    paymentController.setAmount(amount);
    
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}
    
}
