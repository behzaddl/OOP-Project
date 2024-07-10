package org.example.demo1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CardManager {
    private static final List<Card> allCards = new ArrayList<>();
    private static final Random random = new Random();

    static {
        // Add ordinary cards
        allCards.add(new OrdinaryCard("OrdinaryCard1", 50, 5, 30));
        allCards.add(new OrdinaryCard("OrdinaryCard2", 36, 4, 40));
        allCards.add(new OrdinaryCard("OrdinaryCard3", 40, 4, 20));
        allCards.add(new OrdinaryCard("OrdinaryCard4", 30, 3, 70));
        allCards.add(new OrdinaryCard("OrdinaryCard5", 36, 3, 60));
        allCards.add(new OrdinaryCard("OrdinaryCard6", 32, 2, 10));
        allCards.add(new OrdinaryCard("OrdinaryCard7", 28, 1, 30));
        allCards.add(new OrdinaryCard("OrdinaryCard8", 30, 3, 20));
        allCards.add(new OrdinaryCard("OrdinaryCard9", 36, 4, 40));
        allCards.add(new OrdinaryCard("OrdinaryCard10", 28, 2, 80));
        allCards.add(new OrdinaryCard("OrdinaryCard11", 45, 5, 90));
        allCards.add(new OrdinaryCard("OrdinaryCard12", 50, 1, 100));
        allCards.add(new OrdinaryCard("OrdinaryCard13", 33, 3, 70));
        allCards.add(new OrdinaryCard("OrdinaryCard14", 48, 3, 30));
        allCards.add(new OrdinaryCard("OrdinaryCard15", 44, 2, 20));
        allCards.add(new OrdinaryCard("OrdinaryCard16", 28, 2, 10));
        allCards.add(new OrdinaryCard("OrdinaryCard17", 45, 5, 50));
        allCards.add(new OrdinaryCard("OrdinaryCard18", 50, 1, 20));
        allCards.add(new OrdinaryCard("OrdinaryCard19", 33, 3, 80));
        allCards.add(new OrdinaryCard("OrdinaryCard20", 48, 3, 50));
        allCards.add(new OrdinaryCard("OrdinaryCard21", 44, 2, 10));

        // Add spell cards
        allCards.add(new SpellCard("Shield", "Shield", 2));
        allCards.add(new SpellCard("Heal", "Heal", 0));
        allCards.add(new SpellCard("PowerUp", "PowerUp", 0));
        allCards.add(new SpellCard("HoleChanger", "HoleChanger", 0));
        allCards.add(new SpellCard("RepairHole", "RepairHole", 0));
        allCards.add(new SpellCard("RoundLower", "RoundLower", 0));
        allCards.add(new SpellCard("CardStealer", "CardStealer", 0));
        allCards.add(new SpellCard("CardWeaker", "CardWeaker", 0));
        allCards.add(new SpellCard("Copy", "Copy", 0));
        allCards.add(new SpellCard("Hider", "Hider", 0));
        allCards.add(new SpellCard("LineChanger", "LineChanger", 0));
        allCards.add(new SpellCard("PlaceHole", "Placehole", 0));
        Collections.shuffle(allCards);
    }

    public static List<Card> getRandomCards(int numberOfOrdinary, int numberOfSpells) {
        List<Card> ordinaryCards = new ArrayList<>();
        List<Card> spellCards = new ArrayList<>();

        for (Card card : allCards) {
            if (card instanceof SpellCard) {
                spellCards.add(card);
            } else if (card instanceof OrdinaryCard) {
                ordinaryCards.add(card);
            }
        }

        List<Card> selectedCards = new ArrayList<>();
        Collections.shuffle(ordinaryCards);
        Collections.shuffle(spellCards);

        for (int i = 0; i < numberOfOrdinary; i++) {
            selectedCards.add(cloneCard(ordinaryCards.get(i)));
        }

        for (int i = 0; i < numberOfSpells; i++) {
            selectedCards.add(cloneCard(spellCards.get(i)));
        }

        Collections.shuffle(selectedCards);
        return selectedCards;
    }

    public static void distributeCards(String username) {
        User user = User.searchByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        int numberOfSpells = 5;
        int numberOfOrdinary = 15;
        List<Card> spellCards = new ArrayList<>();
        List<Card> ordinaryCards = new ArrayList<>();

        for (Card card : allCards) {
            if (card instanceof SpellCard) {
                spellCards.add(card);
            } else if (card instanceof OrdinaryCard) {
                ordinaryCards.add(card);
            }
        }

        if (spellCards.isEmpty() || ordinaryCards.isEmpty()) {
            System.out.println("Not enough cards available.");
            return;
        }

        while (user.getCardList().size() < numberOfSpells && !spellCards.isEmpty()) {
            Card card = spellCards.get(random.nextInt(spellCards.size()));
            if (!userHasCardByName(user, card.getName())) {
                user.addCard(cloneCard(card));
            }
        }

        while (user.getCardList().size() < numberOfOrdinary + numberOfSpells && !ordinaryCards.isEmpty()) {
            Card card = ordinaryCards.get(random.nextInt(ordinaryCards.size()));
            if (!userHasCardByName(user, card.getName())) {
                user.addCard(cloneCard(card));
            }
        }

        if (user.getCardList().size() < numberOfOrdinary + numberOfSpells) {
            System.out.println("Not enough unique cards available to distribute.");
        }
    }

    private static boolean userHasCardByName(User user, String cardName) {
        for (Card userCard : user.getCardList()) {
            if (userCard.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    private static Card cloneCard(Card card) {
        if (card instanceof OrdinaryCard) {
            OrdinaryCard ordinaryCard = (OrdinaryCard) card;
            return new OrdinaryCard(ordinaryCard.getName(), ordinaryCard.getDamage(), ordinaryCard.getDuration(), ordinaryCard.getPower());
        } else if (card instanceof SpellCard) {
            SpellCard spellCard = (SpellCard) card;
            return new SpellCard(spellCard.getName(), spellCard.getSpecificAction(), spellCard.getDuration());
        }
        return null;
    }

    public static List<Card> getAllCards() {
        return new ArrayList<>(allCards);
    }

    public static Card getCardByName(String cardName) {
        for (Card card : allCards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    // New methods to support Admin functionalities

    public static boolean isCardNameUnique(String cardName) {
        return getCardByName(cardName) == null;
    }

    public static void addCard(Card card) {
        allCards.add(card);
    }

    public static void displayAllCards() {
        System.out.println("Ordinary Cards:");
        for (Card card : allCards) {
            if (card instanceof OrdinaryCard) {
                System.out.println(card);
            }
        }

        System.out.println("\nSpell Cards:");
        for (Card card : allCards) {
            if (card instanceof SpellCard) {
                System.out.println(card);
            }
        }
    }
    public static void displayOrdinCards() {
        System.out.println("Ordinary Cards:");
        for (Card card : allCards) {
            if (card instanceof OrdinaryCard) {
                System.out.println(card);
            }
        }


    }

    public static OrdinaryCard getOrdinaryCardByName(String cardName) {
        Card card = getCardByName(cardName);
        if (card instanceof OrdinaryCard) {
            return (OrdinaryCard) card;
        }
        return null;
    }

    public static SpellCard getSpellCardByName(String cardName) {
        Card card = getCardByName(cardName);
        if (card instanceof SpellCard) {
            return (SpellCard) card;
        }
        return null;
    }

    public static void removeCard(Card card) {
        allCards.remove(card);
    }
}
