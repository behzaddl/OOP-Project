package org.example.demo1;

import java.util.List;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileMenu {

    public static void displayProfileMenu(User user, Stage primaryStage) {
        primaryStage.setTitle("Profile Menu");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label profileLabel = new Label("Profile Menu:");
        Button showInfoButton = new Button("Show Information");
        Button changeUsernameButton = new Button("Change Username");
        Button changeNicknameButton = new Button("Change Nickname");
        Button changePasswordButton = new Button("Change Password");
        Button changeEmailButton = new Button("Change Email");
        Button returnButton = new Button("Return to Login Menu");

        showInfoButton.setOnAction(e -> viewProfile(user));
        changeUsernameButton.setOnAction(e -> ChangeInfo.changeUsername(user));
        changeNicknameButton.setOnAction(e -> ChangeInfo.changeNickname(user));
        changePasswordButton.setOnAction(e -> ChangeInfo.changePassword(user));
        changeEmailButton.setOnAction(e -> ChangeInfo.changeEmail(user));
        returnButton.setOnAction(e -> InerLoginMenu.Display(user, primaryStage));

        vbox.getChildren().addAll(profileLabel, showInfoButton, changeUsernameButton, changeNicknameButton, changePasswordButton, changeEmailButton, returnButton);

        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void viewProfile(User user) {
        Stage profileStage = new Stage();
        profileStage.setTitle("User Profile");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label usernameLabel = new Label("Username: " + user.getUsername());
        Label nicknameLabel = new Label("Nickname: " + user.getNickname());
        Label emailLabel = new Label("Email: " + user.getEmail());
        Label levelLabel = new Label("Level: " + user.getLevel());
        Label maxHPLabel = new Label("Max HP: " + user.getMaxHP());
        Label xpLabel = new Label("XP: " + user.getXP());
        Label coinsLabel = new Label("Coins: " + user.getCoins());
        List<Card> cards = user.getCardList();
        Label cardsLabel = new Label("Cards number: " + cards.size());

        vbox.getChildren().addAll(usernameLabel, nicknameLabel, emailLabel, levelLabel, maxHPLabel, xpLabel, coinsLabel, cardsLabel);

        Scene scene = new Scene(vbox, 300, 300);
        profileStage.setScene(scene);
        profileStage.show();
    }
}
