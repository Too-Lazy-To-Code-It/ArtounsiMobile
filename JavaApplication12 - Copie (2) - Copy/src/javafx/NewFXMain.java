package javafx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author nour2
 */
public class NewFXMain extends Application {
 public static final String CURRENCY = "DT";
    
      @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
              
            root = FXMLLoader.load(getClass().getResource("/GUImenuprincipale/menuprincipale.fxml"));

            Scene scene = new Scene(root,1380,700);
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
      

            
        
    
      
    }}




    
