package controller;

import service.AllUsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import models.Logged;

  
public class Login {


    @FXML
    private TextField EmailOrUsername;

    @FXML
    private PasswordField PASSWORD;


    AllUsersService as = new AllUsersService();

    @FXML
    void LogIn(ActionEvent event) throws SQLException, IOException {
        as.login(EmailOrUsername.getText(), PASSWORD.getText());
        if (Logged.get_instance().getUser().getType().equals("Admin")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Dashboard.fxml"));
            Parent uuView = loader.load();
            Scene scene = new Scene(uuView, 1380, 700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUImenuprincipale/menuprincipale.fxml"));
            Parent uuView = loader.load();
            Scene scene = new Scene(uuView, 1380, 700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void SignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Register.fxml"));
        Parent uuView = loader.load();
        Scene scene = new Scene(uuView);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ForgetPw(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FPWEmail.fxml"));
        Parent uuView = loader.load();
        Scene scene = new Scene(uuView, 1380, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    void initialize() {
        assert EmailOrUsername != null : "fx:id=\"EmailOrUsername\" was not injected: check your FXML file 'Login.fxml'.";
        assert PASSWORD != null : "fx:id=\"PASSWORD\" was not injected: check your FXML file 'Login.fxml'.";

    }

}


