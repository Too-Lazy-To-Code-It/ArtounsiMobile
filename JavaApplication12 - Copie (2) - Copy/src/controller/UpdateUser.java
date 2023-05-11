package controller;

import models.AllUsers;
import service.AllUsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import models.Logged;

public class UpdateUser implements Initializable {

    @FXML
    private DatePicker BirthdayTF;

    @FXML
    private PasswordField CPasswordTF;

    @FXML
    private TextField EmailTF;

    @FXML
    private TextField LastNameTF;

    @FXML
    private TextField NameTF;

    @FXML
    private TextField NationalityTF;

    @FXML
    private TextField NicknameTF;

    @FXML
    private PasswordField PasswordTF;

    @FXML
    private TextField TypeTF;
    
   AllUsers user= Logged.get_instance().getUser();

   

    AllUsersService as = new AllUsersService();

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(user);
        NameTF.setText(user.getName());
        LastNameTF.setText(user.getLast_Name());
        EmailTF.setText(user.getEmail());
        NicknameTF.setText(user.getNickname());
        NationalityTF.setText(user.getNationality());
        PasswordTF.setText(user.getPassword());
        CPasswordTF.setText(user.getPassword());
        BirthdayTF.setValue(user.getBirthday());
        TypeTF.setText(user.getType());

    }

    @FXML
    void Update(ActionEvent event) throws SQLException {

        user.setName(NameTF.getText());
        user.setLast_Name(LastNameTF.getText());
        user.setEmail(EmailTF.getText());
        user.setBirthday(BirthdayTF.getValue());
        user.setPassword(PasswordTF.getText());
        user.setNationality(NationalityTF.getText());
        user.setType(TypeTF.getText());
        user.setNickname(NicknameTF.getText());
        as.ModifyAu(user,user.getID_User());

    }

}
