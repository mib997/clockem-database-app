package com.example.clockemapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * The LoginController class is responsible for handling the login functionality of the application.
 * It validates user input, establishes a database connection, and manages the scene transition upon successful login.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessageLabel;


    @FXML
    public void handleLogin() {

        String userName = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (userName.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Login credential are inaccurate.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/id_management_lookup",
                userName,
                password
        )) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Person Database App");

        } catch (Exception e) {
            loginMessageLabel.setText("Invalid credentials.");
            e.printStackTrace();
        }
    }
}