/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import models.Video;
import interfaces.VideoInterface;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.VideoService;

/**
 * FXML Controller class
 *
 * @author achref
 */
public class FXML_Modify_VideoController implements Initializable {

        VideoInterface vi = new VideoService();

    @FXML
    private TextField titre;
    @FXML
    private TextArea descrption;
    @FXML
    private Label image_name;
    @FXML
    private Label video_name;

    Video video = new Video();
    
    private String src,dest;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void importImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            image_name.setText(selectedFile.getName());
            video.setPathImage(selectedFile.getAbsolutePath());
        } else {
            System.err.println("file is not valid");
        }
    }

    @FXML
    private void importVideo(ActionEvent event) throws IOException {
           FileChooser fc = new FileChooser();
           fc.getExtensionFilters().addAll(
           new FileChooser.ExtensionFilter("Video Files","*.mp4"));
           File selectedFile = fc.showOpenDialog(null);
           if(selectedFile != null) {
            src = selectedFile.getPath();
            dest = "C:\\xampp\\htdocs\\videos\\"+selectedFile.getName();
               
            video_name.setText(selectedFile.getName());
            video.setPathVideo(selectedFile.getName());
           } else {
               System.err.println("file is not valid");
           }
    }

    @FXML
    private void returne(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Fetch_Tutoriels.fxml"));
        Parent view_2=loader.load();
        Scene scene = new Scene(view_2);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    void setVideo(Video video) {
        titre.setText(video.getTitle());
        descrption.setText(video.getDescrption());
        image_name.setText(video.getPathImage());
        video_name.setText(video.getPathVideo());

        this.video = video;
    }

    @FXML
    private void modifyVideo(ActionEvent event) throws IOException {
        if(titre.getText().length()==0||descrption.getText().length()==0||image_name.getText().equals("No Image Selected")||video_name.getText().equals("No Video Selected")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie !");
            alert.setContentText("Veuillez  remplir tous les champs");
            alert.show();
            return;
        }
            else {
        video.setTitle(titre.getText());
        video.setDescrption(descrption.getText());
        Path tmp = Files.copy(Paths.get(src), Paths.get(dest)); 
        vi.modifyVideo(video);
        FXMLLoader loader= new FXMLLoader(getClass().getResource("./FXML_Fetch_Tutoriels.fxml"));
        Parent view_2=loader.load();
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view_2);
        stage.setScene(scene);
        stage.show();
        
    }

}}
