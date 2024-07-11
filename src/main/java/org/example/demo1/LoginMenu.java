package org.example.demo1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;


import java.net.URL;
import java.util.Optional;

public class LoginMenu {

    private static int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 2;
    private static final int WAIT_TIME_SECONDS = 10;
    private static int remainingWaitTime = WAIT_TIME_SECONDS;
    private static String currentStyle = "default";
   // private static MediaPlayer backgroundMusicPlayer;

    public static void displayLoginMenu(Stage primaryStage) {
        primaryStage.setTitle("Login");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getStyleClass().add("root");

        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("label");
        TextField usernameField = new TextField();
        usernameField.getStyleClass().add("text-field");

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("label");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("password-field");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        Button recoverPasswordButton = new Button("Forgot Password");
        recoverPasswordButton.getStyleClass().add("button");
        Button settingsButton = new Button("Settings");
        settingsButton.getStyleClass().add("button");
        Label countdownLabel = new Label();
        countdownLabel.getStyleClass().add("countdown-label");

        vbox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, recoverPasswordButton, settingsButton, countdownLabel);

        Scene scene = new Scene(vbox, 770, 450);
        setStyleSheet(scene, currentStyle);

        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> handleLogin(primaryStage, usernameField.getText(), passwordField.getText(), loginButton, countdownLabel));
        recoverPasswordButton.setOnAction(e -> handlePasswordRecovery(primaryStage));

       // playBackgroundMusic();
    }

    private static void handleLogin(Stage primaryStage, String username, String password, Button loginButton, Label countdownLabel) {
        User user = User.searchByUsername(username);

        if (user != null && User.getPassword(user).equals(password)) {
            loginAttempts = 0; // Reset attempts on successful login
            remainingWaitTime = WAIT_TIME_SECONDS; // Reset remaining wait time
            showAlert("Login successful!");
            InerLoginMenu.Display(user, primaryStage);
        } else {
            loginAttempts++;
            if (loginAttempts > MAX_ATTEMPTS) {
                loginAttempts = 0; // Reset attempts after waiting
                remainingWaitTime = WAIT_TIME_SECONDS;
                loginButton.setDisable(true);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(1), event -> {
                            remainingWaitTime--;
                            countdownLabel.setText("Please wait " + remainingWaitTime + " seconds before trying again.");
                            if (remainingWaitTime <= 0) {
                                loginButton.setDisable(false);
                                countdownLabel.setText("");
                                ((Timeline) event.getSource()).stop();
                            }
                        })
                );
                timeline.setCycleCount(WAIT_TIME_SECONDS);
                timeline.play();
            } else {
                showAlert("Incorrect username or password. Attempts left: " + (MAX_ATTEMPTS - loginAttempts));
            }
        }
    }

    private static void handlePasswordRecovery(Stage primaryStage) {
        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setHeaderText("Enter your username:");
        String username = usernameDialog.showAndWait().orElse("");

        User user = User.searchByUsername(username);
        if (user == null) {
            showAlert("User not found.");
            return;
        }

        TextInputDialog recoveryDialog = new TextInputDialog();
        recoveryDialog.setHeaderText("Recovery Question: " + User.getPasswordRecoveryQuestion(user));
        recoveryDialog.setContentText("Answer:");
        String answer = recoveryDialog.showAndWait().orElse("");

        if (User.getRecoveryAnswer(user).equalsIgnoreCase(answer)) {
            TextInputDialog newPasswordDialog = new TextInputDialog();
            newPasswordDialog.setHeaderText("Enter new password:");
            String newPassword = newPasswordDialog.showAndWait().orElse("");

            User.setPassword(user, newPassword);
            showAlert("Password changed successfully!");
        } else {
            showAlert("Incorrect answer. Please try again.");
        }
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    static void setStyleSheet(Scene scene, String style) {
        scene.getStylesheets().clear();
        URL cssUrl = null;
        if ("style1".equals(style)) {
            cssUrl = LoginMenu.class.getResource("/style1.css");
        } else if ("style2".equals(style)) {
            cssUrl = LoginMenu.class.getResource("/style2.css");
        } else {
            cssUrl = LoginMenu.class.getResource("/styles.css"); // Default style
        }
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Could not find CSS file for style: " + style);
        }
    }

 /*   private static void playBackgroundMusic() {
        if (backgroundMusicPlayer == null) {
            Media sound = new Media(LoginMenu.class.getResource("/background.mp3").toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(sound);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        backgroundMusicPlayer.play();
    }*/

    public static void setCurrentStyle(String style) {
        currentStyle = style;
    }
    static String getCurrentStyle(){
        return currentStyle;
    }

   /* public static void setBackgroundMusicPlayer(MediaPlayer player) {
        backgroundMusicPlayer = player;
    }*/
}
