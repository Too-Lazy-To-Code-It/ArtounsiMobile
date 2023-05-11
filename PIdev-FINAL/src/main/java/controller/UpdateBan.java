package controller;

import models.Ban;
import service.BanService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateBan {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDUSERTF;

    @FXML
    private TextField ReasonTF;

    private int idBan;

    BanService bs=new BanService();

    public void setIdBan(int idBan) throws SQLException {

        this.idBan = idBan;
        Ban ban=new Ban();
        ban=bs.fetchBanbyID(idBan);
        IDUSERTF.setEditable(false);
        IDUSERTF.setText(String.valueOf(ban.getID_User()));
        ReasonTF.setText(ban.getReason());
    }

    @FXML
    void UpdateBan(ActionEvent event) throws SQLException {
        Ban b=new Ban();
        b.setID_User(Integer.parseInt(IDUSERTF.getText()));
        b.setReason(ReasonTF.getText());
        bs.ModifyBan(b,idBan);

    }

    @FXML
    void initialize() {
        assert IDUSERTF != null : "fx:id=\"IDUSERTF\" was not injected: check your FXML file '/GUI/UpdateBan.fxml'.";
        assert ReasonTF != null : "fx:id=\"ReasonTF\" was not injected: check your FXML file '/GUI/UpdateBan.fxml'.";

    }

}
