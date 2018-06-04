package com.gidsor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLAdmin implements Initializable {

    ObservableList<User> users;

    @FXML
    private TableView<User> tableOfUsers;
    @FXML
    private TextField idInputUpdate;
    @FXML
    private TextField usernameInputAdd;
    @FXML
    private TextField usernameInputUpdate;
    @FXML
    private PasswordField passwordInputAdd;
    @FXML
    private PasswordField passwordInputUpdate;
    @FXML
    private TextField fullnameInputAdd;
    @FXML
    private TextField fullnameInputUpdate;
    @FXML
    private TextField emailInputAdd;
    @FXML
    private TextField emailInputUpdate;



    @FXML
    public void handleLogoutUser() throws Exception {
        Main.setWindowScene(new Scene(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"))), "Authentication");
    }

    @FXML
    public void handleUpdateUser() {
        try {
            DBHandler dbHandler = DBHandler.getInstance();
            User selectedUser = tableOfUsers.getSelectionModel().getSelectedItem();
            String hash = BCrypt.hashpw(passwordInputUpdate.getText(), BCrypt.gensalt());
            dbHandler.changeUser(usernameInputUpdate.getText(), hash, fullnameInputUpdate.getText(), emailInputUpdate.getText());
            users = FXCollections.observableArrayList(dbHandler.getAllUsers());
            tableOfUsers.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRemoveUser() {
        try {
            DBHandler dbHandler = DBHandler.getInstance();
            User selectedUser = tableOfUsers.getSelectionModel().getSelectedItem();
            dbHandler.removeUser(selectedUser.getUsername());
            users = FXCollections.observableArrayList(dbHandler.getAllUsers());
            tableOfUsers.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddUser() {
        try {
            String hash = BCrypt.hashpw(passwordInputAdd.getText(), BCrypt.gensalt());
            DBHandler dbHandler = DBHandler.getInstance();
            dbHandler.addUser(usernameInputAdd.getText(), hash, fullnameInputAdd.getText(), emailInputAdd.getText());
            users = FXCollections.observableArrayList(dbHandler.getAllUsers());
            tableOfUsers.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DBHandler dbHandler = DBHandler.getInstance();
            List<User> usersList = dbHandler.getAllUsers();
            users = FXCollections.observableArrayList(usersList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn idCol = new TableColumn("id");
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));


        TableColumn usernameCol = new TableColumn("username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));

        TableColumn passwordCol = new TableColumn("password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

        TableColumn fullnameCol = new TableColumn("fullname");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<User, String>("fullname"));

        TableColumn emailCol = new TableColumn("email");
        emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));

        tableOfUsers.getColumns().addAll(idCol, usernameCol, passwordCol, fullnameCol, emailCol);
        tableOfUsers.setItems(users);

        tableOfUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                User selectedUser = tableOfUsers.getSelectionModel().getSelectedItem();
                idInputUpdate.setText(Integer.toString(selectedUser.getId()));
                usernameInputUpdate.setText(selectedUser.getUsername());
                fullnameInputUpdate.setText(selectedUser.getFullname());
                emailInputUpdate.setText(selectedUser.getEmail());
            }
        });
    }
}
