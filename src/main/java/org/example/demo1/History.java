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

public class History implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String HISTORY_FILE = "history.dat";

    private LocalDateTime dateTime;
    private boolean win;
    private int xp;
    private int coins;
    private String opponentUsername;
    private int opponentLevel;
    private String loggedInUsername;

    public History(LocalDateTime dateTime, boolean win, int xp, int coins, String loggedInUsername, String opponentUsername, int opponentLevel) {
        this.dateTime = dateTime;
        this.win = win;
        this.xp = xp;
        this.coins = coins;
        this.loggedInUsername = loggedInUsername;
        this.opponentUsername = opponentUsername;
        this.opponentLevel = opponentLevel;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isWin() {
        return win;
    }

    public int getXp() {
        return xp;
    }

    public int getCoins() {
        return coins;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public int getOpponentLevel() {
        return opponentLevel;
    }

    @Override
    public String toString() {
        return "Date: " + dateTime + ", Win: " + win + ", XP: " + xp + ", Coins: " + coins +
                ", Opponent: " + opponentUsername + " (Level " + opponentLevel + ")";
    }

    public static void saveHistory(List<History> historyList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE))) {
            oos.writeObject(historyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<History> loadHistory() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HISTORY_FILE))) {
            return (List<History>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void addHistory(LocalDateTime dateTime, boolean win, int xp, int coins, String loggedInUsername, String opponentUsername, int opponentLevel) {
        List<History> historyList = loadHistory();
        historyList.add(new History(dateTime, win, xp, coins, loggedInUsername, opponentUsername, opponentLevel));
        saveHistory(historyList);
    }
}