package org.example.demo1;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class ChangeInfo {

    public static void changeUsername(User user) {
        TextInputDialog dialog = new TextInputDialog(user.getUsername());
        dialog.setTitle("Change Username");
        dialog.setHeaderText("Change your username");
        dialog.setContentText("Please enter your new username:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newUsername -> {
            user.setUsername(newUsername);
            showAlert("Success", "Username changed successfully.");
        });
    }

    public static void changeNickname(User user) {
        TextInputDialog dialog = new TextInputDialog(user.getNickname());
        dialog.setTitle("Change Nickname");
        dialog.setHeaderText("Change your nickname");
        dialog.setContentText("Please enter your new nickname:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newNickname -> {
            user.setNickname(newNickname);
            showAlert("Success", "Nickname changed successfully.");
        });
    }

    public static void changePassword(User user) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Change your password");
        dialog.setContentText("Please enter your new password:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            User.setPassword(user, newPassword);
            showAlert("Success", "Password changed successfully.");
        });
    }

    public static void changeEmail(User user) {
        TextInputDialog dialog = new TextInputDialog(user.getEmail());
        dialog.setTitle("Change Email");
        dialog.setHeaderText("Change your email");
        dialog.setContentText("Please enter your new email:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newEmail -> {
            user.setEmail(newEmail);
            showAlert("Success", "Email changed successfully.");
        });
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
