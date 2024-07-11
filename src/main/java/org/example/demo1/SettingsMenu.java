package org.example.demo1;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingsMenu {

    public static void displaySettingsMenu(Stage primaryStage, Scene previousScene/*, MediaPlayer backgroundMusicPlayer*/) {
        primaryStage.setTitle("Settings");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label themeLabel = new Label("Select Theme:");
        ComboBox<String> themeComboBox = new ComboBox<>();
        themeComboBox.getItems().addAll("Default", "Theme 1", "Theme 2");
        themeComboBox.setValue("Default");

        Label volumeLabel = new Label("Volume:");
        // Slider volumeSlider = new Slider(0, 1, backgroundMusicPlayer.getVolume());

        Button play3MinutesAheadButton = new Button("Play 3 Minutes Ahead");
        Button play3MinutesBackButton = new Button("Play 3 Minutes Back");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        vbox.getChildren().addAll(themeLabel, themeComboBox, volumeLabel, /*volumeSlider,*/ play3MinutesAheadButton, play3MinutesBackButton, saveButton, cancelButton);

        Scene scene = new Scene(vbox, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();

        saveButton.setOnAction(e -> {
            String selectedTheme = themeComboBox.getValue();
            if ("Theme 1".equals(selectedTheme)) {
                LoginMenu.setCurrentStyle("style1");
            } else if ("Theme 2".equals(selectedTheme)) {
                LoginMenu.setCurrentStyle("style2");
            } else {
                LoginMenu.setCurrentStyle("default");
            }
            LoginMenu.setStyleSheet(previousScene, LoginMenu.getCurrentStyle());
            //  backgroundMusicPlayer.setVolume(volumeSlider.getValue());
            primaryStage.setScene(previousScene);
        });

        cancelButton.setOnAction(e -> primaryStage.setScene(previousScene));

     /*   play3MinutesAheadButton.setOnAction(e -> {
            double currentTime = backgroundMusicPlayer.getCurrentTime().toSeconds();
            backgroundMusicPlayer.seek(Duration.seconds(currentTime + 180));
        });

        play3MinutesBackButton.setOnAction(e -> {
            double currentTime = backgroundMusicPlayer.getCurrentTime().toSeconds();
            backgroundMusicPlayer.seek(Duration.seconds(Math.max(0, currentTime - 180)));
        });*/
    }
}

