/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Video;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class VideoViewController implements Initializable {

    @FXML
    private MediaView mediaView;
    @FXML
    private Button playButton;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private String path;
    @FXML
    private Slider sliderTime;
    @FXML
    private HBox hBoxControls;
    @FXML
    private Slider slider_volume;
    @FXML
    private Label total_time;
    @FXML
    private Label speed;
    @FXML
    private Label full_screen;

    private boolean atEndOfVideo = false;
    private boolean isPlaying = true;
    private boolean isMuted = true;

    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivRestart;
    private ImageView ivVolume;
    private ImageView ivFullScreen;
    private ImageView ivMute;
    private ImageView ivExit;
    final int iv_SIZE = 25;
    @FXML
    private Label volume;
    @FXML
    private Label time_current;
    @FXML
    private HBox hboxVolume;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void playMedia(ActionEvent event) {
        mediaPlayer.play();
    }

    private void pauseMedia(ActionEvent event) {
        mediaPlayer.pause();
    }

    private void resetMedia(ActionEvent event) {
        if (mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
            mediaPlayer.seek(Duration.seconds(0.0));
        }
    }

    void setVideo(String path) {
        file = new File("C:\\xampp\\htdocs\\videos\\" + path);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        Image imagePlay = new Image(new File("src/img/play.png").toURI().toString());
        ivPlay = new ImageView(imagePlay);
        ivPlay.setFitHeight(iv_SIZE);
        ivPlay.setFitWidth(iv_SIZE);

        Image imageStop = new Image(new File("src/img/pause.png").toURI().toString());
        ivPause = new ImageView(imageStop);
        ivPause.setFitHeight(iv_SIZE);
        ivPause.setFitWidth(iv_SIZE);

        Image imageRestart = new Image(new File("src/img/reload.png").toURI().toString());
        ivRestart = new ImageView(imageRestart);
        ivRestart.setFitHeight(iv_SIZE);
        ivRestart.setFitWidth(iv_SIZE);

        Image imageVol = new Image(new File("src/img/vlm.png").toURI().toString());
        ivVolume = new ImageView(imageVol);
        ivVolume.setFitHeight(iv_SIZE);
        ivVolume.setFitWidth(iv_SIZE);

        Image imageFull = new Image(new File("src/img/maximize.png").toURI().toString());
        ivFullScreen = new ImageView(imageFull);
        ivFullScreen.setFitHeight(iv_SIZE);
        ivFullScreen.setFitWidth(iv_SIZE);

        Image imageExit = new Image(new File("src/ressources/play-button.png").toURI().toString());
        ivExit = new ImageView(imageExit);
        ivExit.setFitHeight(iv_SIZE);
        ivExit.setFitWidth(iv_SIZE);

        Image imageMute = new Image(new File("src/img/mute.png").toURI().toString());
        ivMute = new ImageView(imageMute);
        ivMute.setFitHeight(iv_SIZE);
        ivMute.setFitWidth(iv_SIZE);
        
        playButton.setGraphic(ivPause);
        volume.setGraphic(ivMute);
        speed.setText("1X");
        full_screen.setGraphic(ivFullScreen);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button buttonPlay = (Button) actionEvent.getSource();
                if (atEndOfVideo) {
                    sliderTime.setValue(0);
                    atEndOfVideo = false;
                    isPlaying = false;
                }
                if (isPlaying) {
                    buttonPlay.setGraphic(ivPlay);
                    mediaPlayer.pause();
                    isPlaying = false;
                } else {
                    buttonPlay.setGraphic(ivPause);
                    mediaPlayer.play();
                    isPlaying = true;
                }
            }
        });

        hboxVolume.getChildren().remove(slider_volume);
        mediaPlayer.volumeProperty().bindBidirectional(slider_volume.valueProperty());
        bindCurrentTimeLabel();

        slider_volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                mediaPlayer.setVolume(slider_volume.getValue());
                if (mediaPlayer.getVolume() != 0.0) {
                    volume.setGraphic(ivVolume);
                    isMuted = false;
                } else {
                    volume.setGraphic(ivMute);
                    isMuted = true;
                }
            }
        });
        
        speed.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(speed.getText().equals("1X")){
                    speed.setText("1.5X");
                    mediaPlayer.setRate(1.5);
                }else{
                    speed.setText("1X");
                    mediaPlayer.setRate(1.0);
                }
            }
            
        });
        
        volume.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){
                if(isMuted) {
                    volume.setGraphic(ivVolume);
                    slider_volume.setValue(0.2);
                    isMuted = false;
                }
                
                else{
                    volume.setGraphic(ivMute);
                    slider_volume.setValue(0);  
                    isMuted = true;
                }
            }
        });
        
        volume.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(hboxVolume.lookup("#slider_volume") == null){
                    hboxVolume.getChildren().add(slider_volume);
                    slider_volume.setValue(mediaPlayer.getVolume());
            }
            }      
        });
        
        hboxVolume.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                    hboxVolume.getChildren().remove(slider_volume);
            }
            
         
            //vBoxParent.sceneProperty()
        });
    }

    public void bindCurrentTimeLabel() {
        time_current.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mediaPlayer.getCurrentTime()) + " / ";
            }
        }, mediaPlayer.currentTimeProperty()));
    }

    public String getTime(Duration time) {
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) {
            seconds = seconds % 60;
        }
        if (minutes > 59) {
            seconds = minutes % 60;
        }
        if (hours > 59) {
            seconds = hours % 60;
        }

        if (hours > 0) {
            return String.format("%d:02d:%02d",
                    hours,
                    minutes,
                    seconds);
        } else {
            return String.format("%02d:%02d",
                    minutes,
                    seconds);
        }
    }

    @FXML
    private void returne(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./FXML_Fetch_Tutoriels.fxml"));
        Parent view_2 = loader.load();
        Scene scene = new Scene(view_2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
