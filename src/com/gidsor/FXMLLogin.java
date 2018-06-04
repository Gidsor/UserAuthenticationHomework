package com.gidsor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.security.crypto.bcrypt.*;

public class FXMLLogin implements Initializable {


    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label infoLabel;

    @FXML
    public void handleLoginUser() throws Exception {
        String username = usernameInput.getText().toLowerCase();
        String password = passwordInput.getText();
        DBHandler dbHandler = DBHandler.getInstance();
        User user = dbHandler.getUser(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            if (user.getUsername().equals("admin")) {
                Main.setWindowScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLAdmin.fxml"))), "Admin");
            } else {
                FXMLUser.currentUser = user;
                Main.setWindowScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLUser.fxml"))), "User");
            }
        } else {
            infoLabel.setText("Password incorrect");
            passwordInput.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
