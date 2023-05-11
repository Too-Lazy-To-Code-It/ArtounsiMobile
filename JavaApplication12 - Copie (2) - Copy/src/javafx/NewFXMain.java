package javafx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FilenameFilter;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author nour2
 */
public class NewFXMain extends Application {
     private MediaPlayer mediaPlayer;
 public static final String CURRENCY = "DT";
    
      @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
              music();
            root = FXMLLoader.load(getClass().getResource("/GUImenuprincipale/menuprincipale.fxml"));
//root = FXMLLoader.load(getClass().getResource("/GUIposts/PostControlPanel.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("ArTounsi");
            primaryStage.setScene(scene);
            primaryStage.show();
          
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void music() {

    File musicDir = new File("src/Music");
    File[] musicFiles = musicDir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".mp3");
        }
    });

    if (musicFiles != null && musicFiles.length > 0) {
        mediaPlayer = new MediaPlayer(new Media(musicFiles[0].toURI().toString()));
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                int currentIndex = -1;
                for (int i = 0; i < musicFiles.length; i++) {
                    if (mediaPlayer.getMedia().getSource().equals(musicFiles[i].toURI().toString())) {
                        currentIndex = i;
                        break;
                    }
                }
                if (currentIndex != -1 && currentIndex + 1 < musicFiles.length) {
                    Media nextMedia = new Media(musicFiles[currentIndex + 1].toURI().toString());
                    mediaPlayer = new MediaPlayer(nextMedia);
                    mediaPlayer.play();
                     mediaPlayer.setVolume(5);
                }
            }
        });
        mediaPlayer.play();
        mediaPlayer.setVolume(0.05);
}} 
    /**
     * @param args the command line arguments
     */
    
    

   
    public static void main(String[] args) {
         launch(args); 
      

            
        
    
      
    }}




    
