package org.example.demo1;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.security.SecureRandom;

import static org.example.demo1.GameApp.createMainMenuScene;

public class SignUpMenu {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    static void displaySignUpMenu(Stage primaryStage) {
        GridPane signUpGrid = new GridPane();
        signUpGrid.setPadding(new Insets(20, 20, 20, 20));
        signUpGrid.setVgap(8);
        signUpGrid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 2);
        PasswordField confirmPasswordInput = new PasswordField();
        GridPane.setConstraints(confirmPasswordInput, 1, 2);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 3);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 3);

        Label nicknameLabel = new Label("Nickname:");
        GridPane.setConstraints(nicknameLabel, 0, 4);
        TextField nicknameInput = new TextField();
        GridPane.setConstraints(nicknameInput, 1, 4);

        Label securityQuestionLabel = new Label("Security Question:");
        GridPane.setConstraints(securityQuestionLabel, 0, 5);
        ChoiceBox<String> securityQuestionChoice = new ChoiceBox<>();
        securityQuestionChoice.getItems().addAll("What is your father’s name?", "What is your favorite color?", "What was the name of your first pet?");
        GridPane.setConstraints(securityQuestionChoice, 1, 5);

        Label securityAnswerLabel = new Label("Answer:");
        GridPane.setConstraints(securityAnswerLabel, 0, 6);
        TextField securityAnswerInput = new TextField();
        GridPane.setConstraints(securityAnswerInput, 1, 6);

        Button randomPasswordButton = new Button("Generate Random Password");
        GridPane.setConstraints(randomPasswordButton, 0, 7);
        randomPasswordButton.setOnAction(e -> {
            String randomPassword = generateRandomPassword();
            passwordInput.setText(randomPassword);
            confirmPasswordInput.setText(randomPassword);
            showAlert("Generated Random Password: " + randomPassword);
        });

        Button signUpSubmitButton = new Button("Sign Up");
        GridPane.setConstraints(signUpSubmitButton, 1, 7);

        signUpSubmitButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String confirmPassword = confirmPasswordInput.getText();
            String email = emailInput.getText();
            String nickname = nicknameInput.getText();
            String securityQuestion = securityQuestionChoice.getValue();
            String securityAnswer = securityAnswerInput.getText();

            // Perform validation and create the user
            if (!User.validateUsername(username)) {
                showAlert("Invalid username. Only letters and numbers are allowed.");
                return;
            }

            if (User.checkUserName(username)) {
                showAlert("Repetitious Username!");
                return;
            }

            if (!User.validatePassword(password)) {
                showAlert("Invalid password. Must be at least 8 characters long and include uppercase, lowercase, and a number.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Passwords do not match. Try again.");
                return;
            }

            if (!User.validateEmail(email)) {
                showAlert("Invalid email format.");
                return;
            }

            User user = new User(username, password, email, nickname);
            user.setSecurityQuestion(securityQuestion, securityAnswer);
            User.addUser(user);
            CardManager.distributeCards(username);

            BufferedImage captchaImage = CAPTCHAGenerator.generateCaptcha();
            CAPTCHADialog captchaDialog = new CAPTCHADialog(captchaImage);
            String userInput = captchaDialog.showAndWait().orElse("");

            // Assuming you store the generated CAPTCHA text
            String generatedCaptchaText = extractCaptchaText(captchaImage);

            if (generatedCaptchaText.equals(userInput)) {
                showAlert("User created successfully!");
                primaryStage.setScene(createMainMenuScene(primaryStage));
            } else {
                showAlert("Incorrect CAPTCHA. Please try again.");
            }

        });

        signUpGrid.getChildren().addAll(
                usernameLabel, usernameInput,
                passwordLabel, passwordInput,
                confirmPasswordLabel, confirmPasswordInput,
                emailLabel, emailInput,
                nicknameLabel, nicknameInput,
                securityQuestionLabel, securityQuestionChoice,
                securityAnswerLabel, securityAnswerInput,
                randomPasswordButton, signUpSubmitButton
        );

        Scene signUpScene = new Scene(signUpGrid, 770, 450);
        URL cssUrl = LoginMenu.class.getResource("/styles.css");
        if (cssUrl != null) {
            signUpScene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Could not find styles.css");
        }
        primaryStage.setScene(signUpScene);
    }

    private static String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
    private static String extractCaptchaText(BufferedImage captchaImage) {
        return CAPTCHAGenerator.getLastGeneratedText();
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
