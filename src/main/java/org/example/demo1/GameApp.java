package org.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.example.demo1.LoginMenu.backgroundMusicPlayer;

public class GameApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Card Battle Game");

        Scene mainScene = createMainMenuScene(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    static Scene createMainMenuScene(Stage primaryStage) {
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(10);
        mainMenu.setPadding(new Insets(270, 20, 20, 370));

        Button signUpButton = new Button("Sign Up");
        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");
        Button settingButton = new Button("Setting");

        signUpButton.setOnAction(e -> SignUpMenu.displaySignUpMenu(primaryStage));
        loginButton.setOnAction(e -> LoginMenu.displayLoginMenu(primaryStage));
        exitButton.setOnAction(e -> primaryStage.close());
        settingButton.setOnAction(e -> SettingsMenu.displaySettingsMenu(primaryStage, mainMenu.getScene(), backgroundMusicPlayer));

        mainMenu.getChildren().addAll(signUpButton, loginButton, exitButton, settingButton);

        Scene scene = new Scene(mainMenu, 770, 450);
        String cssFile = GameApp.class.getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(cssFile);

        return scene;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
