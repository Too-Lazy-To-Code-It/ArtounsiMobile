package controller;


import service.AllUsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class FPWPassword {


    @FXML
    private PasswordField newpassword;

    static private String Email;

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    @FXML
    void UpdatePw(ActionEvent event) throws IOException {
        AllUsersService as = new AllUsersService();
        as.changePassword(newpassword.getText(),getEmail() );
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
        Parent uuView = loader.load();
        Scene scene1 = new Scene(uuView, 1380, 700);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene1);
        stage.show();


    }

}
