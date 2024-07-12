package org.example.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class HistoryMenu /*extends Application*/ {
    private static String loggedInUsername;

    public HistoryMenu() {}

    public HistoryMenu(String loggedInUsername) {
        HistoryMenu.loggedInUsername = loggedInUsername;
    }

    /*@Override
    public void start(User user, Stage primaryStage) {
        displayMenu(user, primaryStage);
    }*/

    public static void displayMenu(User user, Stage primaryStage) {
        primaryStage.setTitle("History Menu");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Button showHistoryButton = new Button("Show Match History");
        Button sortHistoryButton = new Button("Sort Match History");
        Button pageSortHistoryButton = new Button("Page Sort Match History");
        Button returnButton = new Button("Return to Login Menu");

        showHistoryButton.setOnAction(e -> showMatchHistory());
        sortHistoryButton.setOnAction(e -> sortMatchHistory());
        pageSortHistoryButton.setOnAction(e -> pageSortMatchHistory(primaryStage));
        returnButton.setOnAction(e -> {
            User userlog = User.checkUserPasswordForLogin(loggedInUsername);
            InerLoginMenu.Display(user, primaryStage);
        });

        vbox.getChildren().addAll(showHistoryButton, sortHistoryButton, pageSortHistoryButton, returnButton);

        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(HistoryMenu.class.getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void showMatchHistory() {
        List<History> historyList = History.loadHistory();
        historyList = filterByUsername(historyList, loggedInUsername);
        if (historyList.isEmpty()) {
            showAlert("No match history found.");
            return;
        }
        historyList.forEach(history -> showAlert(history.toString()));
    }

    private static void sortMatchHistory() {
        List<History> historyList = History.loadHistory();
        historyList = filterByUsername(historyList, loggedInUsername);
        if (historyList.isEmpty()) {
            showAlert("No match history found.");
            return;
        }

        List<History> sortedList = new ArrayList<>(historyList);
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Date and Time (Newest)", "Date and Time (Oldest)", "Win or Loss", "Opponent Username (A-Z)", "Opponent Username (Z-A)", "Opponent Level (Higher to Lower)", "Opponent Level (Lower to Higher)");
        dialog.setTitle("Sort Options");
        dialog.setHeaderText("Choose a sorting option");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            switch (result.get()) {
                case "Date and Time (Newest)":
                    sortedList.sort(Comparator.comparing(History::getDateTime).reversed());
                    break;
                case "Date and Time (Oldest)":
                    sortedList.sort(Comparator.comparing(History::getDateTime));
                    break;
                case "Win or Loss":
                    sortedList.sort(Comparator.comparing(History::isWin));
                    break;
                case "Opponent Username (A-Z)":
                    sortedList.sort(Comparator.comparing(History::getOpponentUsername));
                    break;
                case "Opponent Username (Z-A)":
                    sortedList.sort(Comparator.comparing(History::getOpponentUsername).reversed());
                    break;
                case "Opponent Level (Higher to Lower)":
                    sortedList.sort(Comparator.comparing(History::getOpponentLevel).reversed());
                    break;
                case "Opponent Level (Lower to Higher)":
                    sortedList.sort(Comparator.comparing(History::getOpponentLevel));
                    break;
            }
            History.saveHistory(sortedList);
            showAlert("History sorted.");
        }
    }

    private static void pageSortMatchHistory(Stage primaryStage) {
        List<History> historyList = History.loadHistory();
        historyList = filterByUsername(historyList, loggedInUsername);
        if (historyList.isEmpty()) {
            showAlert("No match history found.");
            return;
        }

        historyList.sort(Comparator.comparing(History::getDateTime).reversed());

        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Records Per Page");
        dialog.setHeaderText("Enter number of records per page:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            try {
                int recordsPerPage = Integer.parseInt(result.get());
                displayPagedHistory(primaryStage, historyList, recordsPerPage);
            } catch (NumberFormatException e) {
                showAlert("Invalid number.");
            }
        }
    }

    private static void displayPagedHistory(Stage primaryStage, List<History> historyList, int recordsPerPage) {
        int totalPages = (int) Math.ceil((double) historyList.size() / recordsPerPage);
        int[] currentPage = {1};

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label pageLabel = new Label();
        Button nextPageButton = new Button("Next Page");
        Button previousPageButton = new Button("Previous Page");

        nextPageButton.setOnAction(e -> {
            if (currentPage[0] < totalPages) {
                currentPage[0]++;
                updatePage(vbox, historyList, recordsPerPage, currentPage[0], totalPages, pageLabel);
            }
        });

        previousPageButton.setOnAction(e -> {
            if (currentPage[0] > 1) {
                currentPage[0]--;
                updatePage(vbox, historyList, recordsPerPage, currentPage[0], totalPages, pageLabel);
            }
        });

        vbox.getChildren().addAll(pageLabel, nextPageButton, previousPageButton);
        updatePage(vbox, historyList, recordsPerPage, currentPage[0], totalPages, pageLabel);

        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(HistoryMenu.class.getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private static void updatePage(VBox vbox, List<History> historyList, int recordsPerPage, int currentPage, int totalPages, Label pageLabel) {
        vbox.getChildren().clear();

        int start = (currentPage - 1) * recordsPerPage;
        int end = Math.min(start + recordsPerPage, historyList.size());

        for (int i = start; i < end; i++) {
            vbox.getChildren().add(new Label(historyList.get(i).toString()));
        }

        pageLabel.setText("Page " + currentPage + " of " + totalPages);
        vbox.getChildren().addAll(pageLabel, new Button("Next Page"), new Button("Previous Page"));
    }

    private static List<History> filterByUsername(List<History> historyList, String username) {
        List<History> filteredList = new ArrayList<>();
        for (History history : historyList) {
            if (history.getLoggedInUsername().equals(username)) {
                filteredList.add(history);
            }
        }
        return filteredList;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}