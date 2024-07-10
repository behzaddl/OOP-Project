package org.example.demo1;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo1.OrdinaryCard;
import java.util.List;
import java.util.Optional;

import static org.example.demo1.InerLoginMenu.showAlert;

public class Store {

    public static void displayStoreMenu(User user, Stage primaryStage) {
        primaryStage.setTitle("Store Menu");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label storeLabel = new Label("Store Menu:");
        Button buyCardButton = new Button("Buy Card");
        Button sellCardButton = new Button("Sell Card");
        Button upgradeCardButton = new Button("Upgrade Card");
        Button returnButton = new Button("Return to Login Menu");

        buyCardButton.setOnAction(e -> buyCard(user, primaryStage));
        sellCardButton.setOnAction(e -> sellCard(user, primaryStage));
        upgradeCardButton.setOnAction(e -> upgradeCard(user, primaryStage));
        returnButton.setOnAction(e -> InerLoginMenu.Display(user, primaryStage));

        vbox.getChildren().addAll(storeLabel, buyCardButton, sellCardButton, upgradeCardButton, returnButton);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void buyCard(User user, Stage primaryStage) {
        Stage buyCardStage = new Stage();
        buyCardStage.setTitle("Buy Card");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label availableCardsLabel = new Label("Available Cards:");
        ListView<String> cardListView = new ListView<>();
        for (Card card : CardManager.getAllCards()) {
            cardListView.getItems().add(card.getName());
        }

        Label coinsLabel = new Label("Your current coins: " + user.getCoins());
        Label costLabel = new Label("Every card costs 60 coins");

        TextField cardNameInput = new TextField();
        cardNameInput.setPromptText("Enter the name of the card");

        Button buyButton = new Button("Buy Card");
        buyButton.setOnAction(e -> {
            String cardName = cardNameInput.getText();
            Card card = CardManager.getCardByName(cardName);

            if (card == null) {
                showAlert("Error", "Card not found.");
            } else if (user.hasCard(card)) {
                showAlert("Error", "You already own this card.");
            } else if (user.getCoins() < 60) {
                showAlert("Error", "You do not have enough coins to buy this card.");
            } else {
                user.addCard(card);
                user.setCoins(user.getCoins() - 60);
                showAlert("Success", "Card purchased successfully. Your new coins: " + user.getCoins());
                buyCardStage.close();
                displayStoreMenu(user, primaryStage);
            }
        });

        vbox.getChildren().addAll(availableCardsLabel, cardListView, coinsLabel, costLabel, cardNameInput, buyButton);

        Scene scene = new Scene(vbox, 400, 300);
        buyCardStage.setScene(scene);
        buyCardStage.show();
    }

    private static void sellCard(User user, Stage primaryStage) {
        Stage sellCardStage = new Stage();
        sellCardStage.setTitle("Sell Card");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label userCardsLabel = new Label("Your Cards:");
        ListView<String> cardListView = new ListView<>();
        for (Card card : user.getCardList()) {
            cardListView.getItems().add(card.getName());
        }

        Label coinsLabel = new Label("Your current coins: " + user.getCoins());
        Label earningLabel = new Label("You will earn 40 coins by selling each card");

        TextField cardNameInput = new TextField();
        cardNameInput.setPromptText("Enter the name of the card");

        Button sellButton = new Button("Sell Card");
        sellButton.setOnAction(e -> {
            String cardName = cardNameInput.getText();
            Card card = user.getCardByName(cardName);

            if (card == null) {
                showAlert("Error", "Card not found.");
            } else {
                user.removeCard(card);
                user.setCoins(user.getCoins() + 40);
                showAlert("Success", "Card sold successfully. Your new coins: " + user.getCoins());
                sellCardStage.close();
                displayStoreMenu(user, primaryStage);
            }
        });

        vbox.getChildren().addAll(userCardsLabel, cardListView, coinsLabel, earningLabel, cardNameInput, sellButton);

        Scene scene = new Scene(vbox, 400, 300);
        sellCardStage.setScene(scene);
        sellCardStage.show();
    }

    private static void upgradeCard(User user, Stage primaryStage) {
        Stage upgradeCardStage = new Stage();
        upgradeCardStage.setTitle("Upgrade Card");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label ordinaryCardsLabel = new Label("Your Ordinary Cards:");
        ListView<String> cardListView = new ListView<>();
        List<OrdinaryCard> ordinaryCards = user.getOrdinaryCards();
        for (Card card : ordinaryCards) {
            cardListView.getItems().add(card.getName());
        }

        TextField cardChoiceInput = new TextField();
        cardChoiceInput.setPromptText("Choose a card to upgrade (num)");

        Button upgradeButton = new Button("Upgrade Card");
        upgradeButton.setOnAction(e -> {
            int choice = Integer.parseInt(cardChoiceInput.getText());

            if (choice < 1 || choice > ordinaryCards.size()) {
                showAlert("Error", "Invalid choice.");
            } else {
                OrdinaryCard card = ordinaryCards.get(choice - 1);
                upgradeCardOptions(user, card);
            }
        });

        vbox.getChildren().addAll(ordinaryCardsLabel, cardListView, cardChoiceInput, upgradeButton);

        Scene scene = new Scene(vbox, 400, 300);
        upgradeCardStage.setScene(scene);
        upgradeCardStage.show();
    }

    private static void upgradeCardOptions(User user, OrdinaryCard card) {
        Stage upgradeOptionsStage = new Stage();
        upgradeOptionsStage.setTitle("Upgrade Options");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label selectedCardLabel = new Label("Selected Card: " + card.getName());

        Button upgradeDamageButton = new Button("Upgrade Damage");
        Button upgradePowerButton = new Button("Upgrade Power");

        upgradeDamageButton.setOnAction(e -> upgradeDamage(user, card));
        upgradePowerButton.setOnAction(e -> upgradePower(user, card));

        vbox.getChildren().addAll(selectedCardLabel, upgradeDamageButton, upgradePowerButton);

        Scene scene = new Scene(vbox, 300, 200);
        upgradeOptionsStage.setScene(scene);
        upgradeOptionsStage.show();
    }

    private static void upgradeDamage(User user, OrdinaryCard card) {
        if (card.getDamage() >= 50) {
            showAlert("Maximum Damage", "Card has reached maximum damage.");
            return;
        }

        showAlert("Coins", "Your current coins: " + user.getCoins());
        showAlert("Cost", "Every 1 damage upgrade costs 5 coins");

        int maxUpgrade = 50 - card.getDamage();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Upgrade Damage");
        dialog.setHeaderText("Enter the amount of damage to upgrade (max " + maxUpgrade + "):");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            int damageUpgrade;
            try {
                damageUpgrade = Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number.");
                return;
            }

            if (damageUpgrade < 0 || damageUpgrade > maxUpgrade) {
                showAlert("Invalid Damage Upgrade", "Invalid damage upgrade amount.");
                return;
            }

            int cost = damageUpgrade * 5;
            if (user.getCoins() < cost) {
                showAlert("Insufficient Coins", "You do not have enough coins to upgrade damage.");
                return;
            }

            card.setDamage(card.getDamage() + damageUpgrade);
            user.setCoins(user.getCoins() - cost);
            showAlert("Success", "Damage upgraded successfully. Your current coins: " + user.getCoins());
        }
    }

    private static void upgradePower(User user, OrdinaryCard card) {
        if (user.getLevel() < 5) {
            showAlert("Insufficient Level", "Your level must be greater than 5 to upgrade power.");
            return;
        }

        if (card.getPower() >= 100) {
            showAlert("Maximum Power", "Card has reached maximum power.");
            return;
        }

        showAlert("Coins", "Your current coins: " + user.getCoins());
        showAlert("Cost", "Every 1 power upgrade costs 100 coins");

        int maxUpgrade = 100 - card.getPower();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Upgrade Power");
        dialog.setHeaderText("Enter the amount of power to upgrade (max " + maxUpgrade + "):");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            int powerUpgrade;
            try {
                powerUpgrade = Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number.");
                return;
            }

            if (powerUpgrade < 0 || powerUpgrade > maxUpgrade) {
                showAlert("Invalid Power Upgrade", "Invalid power upgrade amount.");
                return;
            }

            int cost = powerUpgrade * 100;
            if (user.getCoins() < cost) {
                showAlert("Insufficient Coins", "You do not have enough coins to upgrade power.");
                return;
            }

            card.setPower(card.getPower() + powerUpgrade);
            user.setCoins(user.getCoins() - cost);
            showAlert("Success", "Power upgraded successfully. Your current coins: " + user.getCoins());
        }
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
