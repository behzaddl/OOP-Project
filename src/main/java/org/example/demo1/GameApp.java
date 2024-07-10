package org.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.SecureRandom;

public class GameApp extends Application {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Card Battle Game");

        Scene mainScene = createMainMenuScene(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private static Scene createMainMenuScene(Stage primaryStage) {
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(10);
        mainMenu.setPadding(new Insets(20, 20, 20, 20));

        Button signUpButton = new Button("Sign Up");
        Button loginButton = new Button("Login");
        Button exitButton = new Button("Exit");

        signUpButton.setOnAction(e -> displaySignUpMenu(primaryStage));
        loginButton.setOnAction(e -> displayLoginMenu(primaryStage));
        exitButton.setOnAction(e -> primaryStage.close());

        mainMenu.getChildren().addAll(signUpButton, loginButton, exitButton);

        return new Scene(mainMenu, 300, 200);
    }

    private static void displaySignUpMenu(Stage primaryStage) {
        GridPane signUpGrid = new GridPane();
        signUpGrid.setPadding(new Insets(10, 10, 10, 10));
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

            // Handle captcha (simplified for demonstration)
            String captcha = ASCIIDigits.generateCaptcha();
            TextInputDialog captchaDialog = new TextInputDialog();
            captchaDialog.setHeaderText("Please enter the number displayed:");
            captchaDialog.setContentText(captcha);
            String userInput = captchaDialog.showAndWait().orElse("");

            if (captcha.equals(userInput)) {
                showAlert("User created successfully!");
                primaryStage.setScene(createMainMenuScene(primaryStage));
            } else {
                showAlert("Incorrect captcha. Please try again.");
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

        Scene signUpScene = new Scene(signUpGrid, 400, 400);
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


    static void displayLoginMenu(Stage primaryStage) {
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10, 10, 10));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginSubmitButton = new Button("Login");
        GridPane.setConstraints(loginSubmitButton, 1, 2);

        loginSubmitButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            User user = User.checkUserPasswordForLogin(username);

            if (user != null && User.getPassword(user).equals(password)) {
                // Successful login
                showAlert("Logged in successfully!");
                primaryStage.setScene(createMainMenuScene(primaryStage));
                InerLoginMenu.Display(user, primaryStage);

            } else {
                if (user == null) {
                    showAlert("Username doesn’t exist!");
                } else {
                    showAlert("Password and Username don’t match!");
                }
            }
        });

        loginGrid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginSubmitButton);

        Scene loginScene = new Scene(loginGrid, 400, 200);
        primaryStage.setScene(loginScene);
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
