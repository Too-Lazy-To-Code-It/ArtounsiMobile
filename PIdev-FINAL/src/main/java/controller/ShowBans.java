package controller;


import models.Ban;
import service.BanService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowBans {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    BanService bs = new BanService();

    @FXML
    private ListView<Ban> BansLV;


    ObservableList<Ban> bul = FXCollections.observableArrayList(bs.fetchBan());



    @FXML
    void initialize() throws SQLException {
        bul = FXCollections.observableArrayList(bs.fetchBan());
        BansLV.setItems(bul);
    }


    @FXML
    void ShowBans(ActionEvent event) throws SQLException {
        bul = FXCollections.observableArrayList(bs.fetchBan());
        BansLV.setItems(bul);
    }

    @FXML
    void DeleteBan(ActionEvent event) throws SQLException {
        int selectedIndex = BansLV.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Ban ban = BansLV.getItems().get(selectedIndex);
            bs.DeleteBan(ban.getID_Ban());
            BansLV.getItems().remove(selectedIndex);
            showAlert("Ban deleted successfully.");
        } else {
            showAlert("Please select a Ban to delete.");
        }

    }


    @FXML
    void UpdateBan(ActionEvent event) throws IOException, SQLException {
        Ban selectedBan = BansLV.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/UpdateBan.fxml"));
        Parent uuView = loader.load();
        Scene scene = new Scene(uuView);


        UpdateBan updateban = loader.getController();
        updateban.setIdBan(selectedBan.getID_Ban());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
