package controller;

import models.AllUsers;
import service.AllUsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FPWEmail {
    AllUsersService as = new AllUsersService();

    @FXML
    private TextField Email;

    @FXML
    void VerifyFPWE(ActionEvent event) throws SQLException, IOException {
        AllUsers user = null;
        user = as.fetchAUbyEmail(Email.getText().toString());
        System.out.println(user);
        if (user.getEmail() != null) {
            String verificationCode = as.generateVerificationCode();
            as.sendVerificationCode(user.getEmail(), verificationCode);
            // Create a new Stage object for the pop-up window
            Stage popupWindow = new Stage();

// Create a new Textfield object for the user to input the verification code
            TextField verificationCodeField = new TextField();

// Create a new Button object to submit the verification code
            Button submitButton = new Button("Submit");

// Add an action event handler to the submit button to retrieve the verification code from the Textfield
            submitButton.setOnAction(e -> {
                String verificationCodE = verificationCodeField.getText();
                // Do something with the verification code, such as validate it against the sent password
                popupWindow.close(); // Close the pop-up window
            });

// Create a new VBox object to hold the Textfield and submit button
            VBox vbox = new VBox(10, verificationCodeField, submitButton);
            vbox.setAlignment(Pos.CENTER);

// Create a new Scene object with the VBox as its root node
            Scene scene = new Scene(vbox, 300, 200);

// Set the title and modality of the pop-up window
            popupWindow.setTitle("Verification Code");
            popupWindow.initModality(Modality.APPLICATION_MODAL);

// Set the scene of the pop-up window and show it
            popupWindow.setScene(scene);
            popupWindow.showAndWait();
            if (verificationCode.equals(verificationCodeField.getText())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/FPWPassword.fxml"));
                Parent uuView = loader.load();
                Scene scene1 = new Scene(uuView, 1380, 700);

                FPWPassword fpwPassword = loader.getController();
                fpwPassword.setEmail(user.getEmail());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene1);
                stage.show();


            }

        }


    }

    @FXML
    void SignUp(ActionEvent event) {

    }
}