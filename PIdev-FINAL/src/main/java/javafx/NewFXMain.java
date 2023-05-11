package javafx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nour2
 */
public class NewFXMain extends Application {
    public static final String CURRENCY = "DT";

    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("menuprincipale.fxml"));

            Scene scene = new Scene(root, 1380, 700);
            primaryStage.setTitle("ArTounsi");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */


    public static void main(String[] args) {
        launch(args);


    }
}




    
