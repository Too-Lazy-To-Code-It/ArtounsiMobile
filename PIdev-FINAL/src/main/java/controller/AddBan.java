package controller;

import java.io.IOException;
import models.Ban;
import service.BanService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddBan {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDUSERTF;

    @FXML
    private TextField ReasonTF;
    private int userId;

    public void setUserId(int userId) throws SQLException {

        this.userId = userId;
        IDUSERTF.setText(String.valueOf(userId));


    }

    BanService bs=new BanService();

    @FXML
   void addBan(ActionEvent event) throws SQLException, IOException {
        Ban b=new Ban();
        b.setID_User(Integer.parseInt(IDUSERTF.getText()));
        b.setReason(ReasonTF.getText());
        bs.AddBan(b);
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Dashboard.fxml"));
            Parent uuView = loader.load();
            Scene scene = new Scene(uuView, 1380, 700);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

    }

    @FXML
    void initialize() {
        assert IDUSERTF != null : "fx:id=\"IDUSERTF\" was not injected: check your FXML file 'AddBan.fxml'.";
        assert ReasonTF != null : "fx:id=\"ReasonTF\" was not injected: check your FXML file 'AddBan.fxml'.";

    }

}
