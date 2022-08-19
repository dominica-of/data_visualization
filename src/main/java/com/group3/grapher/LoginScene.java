package com.group3.grapher;

import com.group3.grapher.database.models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginScene {
    public static String email;

    public LoginScene(){}

    public static Scene getScene(Stage stage){
        HBox hBox = new HBox();

        VBox loginFormBox = new VBox();
        VBox registerFormBox = new VBox();
        Button loginButton = new Button("LOGIN");
        Button registerButton = new Button("REGISTER");

        TextField usernameField = new TextField();
        usernameField.setMinWidth(300);
        TextField emailField = new TextField();
        TextField logEmailField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField logPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();

        Label usernameLabel = new Label("Username");
        Label warningText = new Label();
        Label logWarningText = new Label();
        warningText.setVisible(false);
        logWarningText.setVisible(false);
        Label emailLabel = new Label("Email");
        Label logEmailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
        Label logPasswordLabel = new Label("Password");
        Label confirmPasswordLabel = new Label("Confirm Password");

        Label[] labels = {usernameLabel,emailLabel,logEmailLabel,logPasswordLabel,passwordLabel,confirmPasswordLabel};
        for (int i = 0; i < labels.length; i++) {
            labels[i].setId("formLabel");
        }

        usernameField.setId("textFields");
        emailField.setId("textFields");
        passwordField.setId("textFields");
        confirmPasswordField.setId("textFields");
        logEmailField.setId("textFields");
        logPasswordField.setId("textFields");

        registerButton.prefWidth(100);
        loginButton.prefWidth(100);
        logEmailField.setPrefWidth(200);

        warningText.setStyle("-fx-text-fill: red");
        logWarningText.setStyle("-fx-text-fill: red");

        Label signUpText = new Label("Create an account");
        signUpText.setId("link");
        Label loginText = new Label("Already have an account? Login here");
        loginText.setId("link");
        loginText.setOnMouseClicked(e->{
            hBox.getChildren().clear();
            hBox.getChildren().add(loginFormBox);
        });
        signUpText.setOnMouseClicked(e->{
            hBox.getChildren().clear();
            hBox.getChildren().add(registerFormBox);
            System.out.println("Clicked");
        });
        //login click event
        loginButton.setOnAction(e->{
            System.out.println("Log");
            if(logEmailField.getText() == null || logEmailField.getText().trim().isEmpty()){
                logWarningText.setText("Email field cannot be empty");
                logWarningText.setVisible(true);
            }else if(logPasswordField.getText() == null || logPasswordField.getText().trim().isEmpty()){
                logWarningText.setText("Please enter a password");
                logWarningText.setVisible(true);
            }else{
                logWarningText.setVisible(false);
                User user = new User();
                if(user.login(logEmailField.getText().trim(), logPasswordField.getText().trim())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "LOGIN SUCCESSFUL");
                    alert.setHeaderText("WELCOME");
                    alert.showAndWait();
                    stage.setScene(InnerScene.getScene(stage));
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "UNSUCCESSFUL LOGIN");
                    alert.setHeaderText("PLEASE VERIFY YOUR DETAILS AND TRY AGAIN");
                    alert.showAndWait();
                }
            }
        });
        //register click event
        registerButton.setOnAction(e->{
            if( (usernameField.getText() == null || usernameField.getText().trim().isEmpty())) {
                warningText.setText("Username field cannot be empty");
                warningText.setVisible(true);
            }else if(emailField.getText() == null || emailField.getText().trim().isEmpty()){
                warningText.setText("Email field cannot be empty");
                warningText.setVisible(true);
            }else if(passwordField.getText() == null || passwordField.getText().trim().isEmpty()){
                warningText.setText("Please enter a password");
                warningText.setVisible(true);
            }else if(confirmPasswordField.getText() == null || confirmPasswordField.getText().trim().isEmpty()){
                warningText.setText("Please confirm your a password");
                warningText.setVisible(true);
            }else if(!confirmPasswordField.getText().equals(passwordField.getText())){
                warningText.setText("Passwords do not match");
                warningText.setVisible(true);
            }else{
                warningText.setVisible(false);
                User user = new User();
                if(user.createUser(usernameField.getText().trim(), emailField.getText().trim(),passwordField.getText().trim())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "SUCCESSFUL REGISTRATION");
                    alert.setHeaderText("WELCOME "+usernameField.getText().trim());
                    alert.showAndWait();
                    stage.setScene(InnerScene.getScene(stage));
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "REGISTRATION WAS NOT SUCCESSFUL");
                    alert.setHeaderText("EMAIL ALREADY REGISTERED");
                    alert.showAndWait();
                }
            }
        });
        //////////////////////
        loginFormBox.setSpacing(8);
        loginFormBox.setPadding(new Insets(100,100,100,200));
        loginFormBox.getChildren().addAll(
                logEmailLabel,
                logEmailField,
                logPasswordLabel,
                logPasswordField,
                loginButton,
                signUpText, logWarningText
                );

        registerFormBox.setSpacing(8);
        registerFormBox.setPadding(new Insets(100,100,100,200));
        registerFormBox.getChildren().addAll(
                usernameLabel,
                usernameField,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                confirmPasswordLabel,
                confirmPasswordField,
                registerButton,
                loginText, warningText
        );
        hBox.getChildren().add(registerFormBox);
        Scene scene = new Scene(hBox,1000,600);
        scene.getStylesheets().add(Objects.requireNonNull(LoginScene.class.getResource("pre.css")).toExternalForm());

        return scene;
    }
}
