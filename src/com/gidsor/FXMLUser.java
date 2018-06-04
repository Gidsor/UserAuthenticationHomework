package com.gidsor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLUser implements Initializable {

    public static User currentUser;

    @FXML
    private Label infoUser;

    @FXML
    public void handleLogoutUser() throws Exception {
        Main.setWindowScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"))), "Authentication");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoUser.setText(currentUser.toString());
    }
}
