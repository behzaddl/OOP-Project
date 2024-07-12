package org.example.demo1;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class InerLoginMenu {

    public static void Display(User user, Stage primaryStage) {
        primaryStage.setTitle("Welcome, " + user.getUsername());

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label welcomeLabel = new Label("Welcome, " + user.getUsername());
        Button startGameButton = new Button("Start Game");
        Button seeCardsButton = new Button("See Cards");
        Button storeButton = new Button("Store");
        Button profileButton = new Button("Profile");
        Button adminLoginButton = new Button("Admin Login");
        Button historyButton = new Button("History");
        Button logoutButton = new Button("Log Out");

        startGameButton.setOnAction(e -> startGame(user));
        seeCardsButton.setOnAction(e -> displayUserCards(user, primaryStage));
        storeButton.setOnAction(e -> Store.displayStoreMenu(user, primaryStage));
        profileButton.setOnAction(e -> ProfileMenu.displayProfileMenu(user, primaryStage));
        adminLoginButton.setOnAction(e -> showAlert("Admin", "Admin functionality not implemented yet."));
        historyButton.setOnAction(e -> HistoryMenu.displayMenu(user, primaryStage));
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            LoginMenu.displayLoginMenu(primaryStage);
        });

        vbox.getChildren().addAll(
                welcomeLabel,
                startGameButton,
                seeCardsButton,
                storeButton,
                profileButton,
                adminLoginButton,
                historyButton,
                logoutButton
        );

        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(InerLoginMenu.class.getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void startGame(User user) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("1v1", "1v1", "bet 1v1");
        dialog.setTitle("Game Mode");
        dialog.setHeaderText("Choose the game mode");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(mode -> {
            if (mode.equals("1v1")) {
                // Implement the logic for 1v1 mode
                start1v1Game(user, false, 0);
            } else if (mode.equals("bet 1v1")) {
                // Implement the logic for bet 1v1 mode
                start1v1Game(user, true, 100); // Example bet amount
            }
        });
    }

    private static void start1v1Game(User user, boolean isBet, int betAmount) {
        // Simplified implementation for starting the game
        showAlert("Start Game", "Game logic not implemented yet.");
    }

    private static void displayUserCards(User user, Stage primaryStage) {
        List<Card> cards = user.getCardList();
        CardDisplay.displayCardsInGUI(cards, primaryStage, user, primaryStage);
    }

    static void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
