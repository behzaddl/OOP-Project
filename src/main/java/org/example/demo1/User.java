package org.example.demo1;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.*;
class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String passwordRecoveryQuestion;
    private String passwordRecoveryAnswer;
    private int level;
    private int maxHP;
    private int XP;
    private int coins;
    private List<Card> cardList;
    private static ArrayList<User> users = new ArrayList<>();

    public User(String username, String password, String email, String nickname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.level = 1;
        this.maxHP = this.level * 100;
        this.XP = 0;
        this.coins = 100;
        this.cardList = new ArrayList<>();
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static boolean checkUserName(String userName) {
        if (users != null) {
            for (User user : users) {
                if (user.username.equals(userName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static User checkUserPasswordForLogin(String userName) {
        for (User user : users) {
            if (user.username.equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public static String getUserName(User user) {
        return user.username;
    }

    public static User searchByUsername(String username) {
        for (User user : users) {
            if (username.equals(getUserName(user))) {
                return user;
            }
        }
        return null;
    }

    public static String getPassword(User user) {
        return user.password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void setPassword(User user, String newPassword) {
        user.password = newPassword;
    }

    public static String getRecoveryAnswer(User user) {
        return user.passwordRecoveryAnswer;
    }

    public void setSecurityQuestion(String question, String answer) {
        this.passwordRecoveryQuestion = question;
        this.passwordRecoveryAnswer = answer;
    }

    public static boolean validateUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9]+$", username);
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*");
    }

    public static boolean validateEmail(String email) {
        return Pattern.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getXP() {
        return XP;
    }

    public void addXP(int amount) {
        this.XP += amount;
        if (this.XP >= 1000) {
            this.levelUp();
        }
    }

    private void levelUp() {
        this.level++;
        this.maxHP = this.level * 100;
        this.XP = 0;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public boolean checkLevelUp() {
        int xpForNextLevel = calculateXPForNextLevel();
        if (this.XP >= xpForNextLevel) {
            this.level++;
            this.XP -= xpForNextLevel;
            System.out.println("Congratulations! You've reached level " + this.level + ".");
            return true;
        }
        return false;
    }

    private int calculateXPForNextLevel() {
        return 100 * (int) Math.pow(2, this.level - 1); // Example: 100, 200, 400, 800, ...
    }

    public void deductCoins(int amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
        } else {
            System.out.println("Not enough coins.");
        }
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void addCard(Card card) {
        this.cardList.add(card);
    }

    public void removeCard(Card card) {
        this.cardList.remove(card);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public boolean hasCard(Card card) {
        return cardList.contains(card);
    }

    public Card getCardByName(String cardName) {
        for (Card card : cardList) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public List<OrdinaryCard> getOrdinaryCards() {
        List<OrdinaryCard> ordinaryCards = new ArrayList<>();
        for (Card card : cardList) {
            if (card instanceof OrdinaryCard) {
                ordinaryCards.add((OrdinaryCard) card);
            }
        }
        return ordinaryCards;
    }
    public static void setUsers(List<User> loadedUsers) {
        users = new ArrayList<>(loadedUsers);
    }
}
