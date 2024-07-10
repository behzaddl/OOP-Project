package org.example.demo1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardDisplay {

    public static void displayCardDetails(Card card, int number) {
        System.out.println(number + ". " + (card instanceof OrdinaryCard ? "Ordinary Card:" : "Spell Card:"));
        if (card instanceof OrdinaryCard) {
            displayOrdinaryCardDetails((OrdinaryCard) card);
        } else if (card instanceof SpellCard) {
            displaySpellCardDetails((SpellCard) card);
        }
    }

    private static void displayOrdinaryCardDetails(OrdinaryCard card) {
        System.out.println("  Name: " + card.getName());
        System.out.println("  Damage: " + card.getDamage());
        System.out.println("  Duration: " + card.getDuration());
        System.out.println("  Power: " + card.getPower());
        System.out.println("  Level: " + card.getLevel());
    }

    private static void displaySpellCardDetails(SpellCard card) {
        System.out.println("  Name: " + card.getName());
        System.out.println("  Specific Action: " + card.getSpecificAction());
        System.out.println("  Duration: " + card.getDuration());
        System.out.println("  Level: " + card.getLevel());
    }

    public static void displayCards(List<Card> cards) {
        System.out.println("Spell Cards:");
        int number = 1;
        for (Card card : cards) {
            if (card instanceof SpellCard) {
                displayCardDetails(card, number++);
            }
        }

        System.out.println("Ordinary Cards:");
        number = 1;
        for (Card card : cards) {
            if (card instanceof OrdinaryCard) {
                displayCardDetails(card, number++);
            }
        }
    }

    public static void displayAvailableCards(List<Card> cards, User user) {
        System.out.println("Available Cards for Purchase:");
        int number = 1;

        Set<String> userCardNames = new HashSet<>();
        for (Card userCard : user.getCardList()) {
            userCardNames.add(userCard.getName());
        }

        for (Card card : cards) {
            if (!userCardNames.contains(card.getName())) {
                displayCardDetails(card, number++);
            }
        }
    }

    public static void displayCardsInGUI(List<Card> cards, Stage stage) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        int number = 1;
        for (Card card : cards) {
            String cardType = card instanceof OrdinaryCard ? "Ordinary Card" : "Spell Card";
            items.add(number++ + ". " + cardType + ": " + card.getName());
        }
        listView.setItems(items);

        vbox.getChildren().add(listView);
        Scene scene = new Scene(vbox, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void displayAvailableCardsInGUI(List<Card> cards, User user, Stage stage) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        int number = 1;

        Set<String> userCardNames = new HashSet<>();
        for (Card userCard : user.getCardList()) {
            userCardNames.add(userCard.getName());
        }

        for (Card card : cards) {
            if (!userCardNames.contains(card.getName())) {
                String cardType = card instanceof OrdinaryCard ? "Ordinary Card" : "Spell Card";
                items.add(number++ + ". " + cardType + ": " + card.getName());
            }
        }
        listView.setItems(items);

        vbox.getChildren().add(listView);
        Scene scene = new Scene(vbox, 400, 600);
        stage.setScene(scene);
        stage.show();
    }
}
