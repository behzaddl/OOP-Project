package org.example.demo1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GamePlayGUI extends JFrame {
    private static final int TIMELINE_LENGTH = 21;
    private JPanel player1Field, player2Field;
    private JLabel player1Info, player2Info, roundsInfo;
    private JLabel player1Character, player2Character;
    private JButton[] player1BattleDeckButtons, player2BattleDeckButtons;
    private char[] player1Timeline, player2Timeline;
    private Card[] player1TimelineCards, player2TimelineCards;
    private List<Card> player1BattleDeck, player2BattleDeck;
    private int player1Health, player2Health, rounds;
    private boolean battleDeckVisibility;
    private int h;
    private String player1Username, player2Username;

    public GamePlayGUI(String player1Username, String player2Username) {
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        initializeComponents();
        initializeGame();
    }

    private void initializeComponents() {
        setTitle("Card Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for rounds info
        roundsInfo = new JLabel("Rounds Left: 4");
        add(roundsInfo, BorderLayout.NORTH);

        // Center panel for playing field
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        player1Field = new JPanel(new GridLayout(1, TIMELINE_LENGTH));
        player2Field = new JPanel(new GridLayout(1, TIMELINE_LENGTH));
        centerPanel.add(player1Field);
        centerPanel.add(player2Field);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for player info and battle decks
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel player1Panel = new JPanel(new BorderLayout());
        player1Info = new JLabel("Player 1 HP: 100");
        player1Character = new JLabel(new ImageIcon("path_to_player1_character_image"));
        player1Panel.add(player1Character, BorderLayout.WEST);
        player1Panel.add(player1Info, BorderLayout.CENTER);

        JPanel player1DeckPanel = new JPanel(new GridLayout(1, 5));
        player1BattleDeckButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            player1BattleDeckButtons[i] = new JButton();
            player1BattleDeckButtons[i].addActionListener(new CardButtonListener(1, i));
            player1DeckPanel.add(player1BattleDeckButtons[i]);
        }
        player1Panel.add(player1DeckPanel, BorderLayout.SOUTH);

        bottomPanel.add(player1Panel);

        JPanel player2Panel = new JPanel(new BorderLayout());
        player2Info = new JLabel("Player 2 HP: 100");
        player2Character = new JLabel(new ImageIcon("path_to_player2_character_image"));
        player2Panel.add(player2Character, BorderLayout.WEST);
        player2Panel.add(player2Info, BorderLayout.CENTER);

        JPanel player2DeckPanel = new JPanel(new GridLayout(1, 5));
        player2BattleDeckButtons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            player2BattleDeckButtons[i] = new JButton();
            player2BattleDeckButtons[i].addActionListener(new CardButtonListener(2, i));
            player2DeckPanel.add(player2BattleDeckButtons[i]);
        }
        player2Panel.add(player2DeckPanel, BorderLayout.SOUTH);

        bottomPanel.add(player2Panel);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeGame() {
        player1Timeline = new char[TIMELINE_LENGTH];
        player2Timeline = new char[TIMELINE_LENGTH];
        player1TimelineCards = new Card[TIMELINE_LENGTH];
        player2TimelineCards = new Card[TIMELINE_LENGTH];
        battleDeckVisibility = true;
        h = 0;

        // Initialize game data (health, decks, timelines, etc.)
        // initializeTimelines();
        // initializeDecks(player1Username, player2Username);
        // initializeBattleDecks();
        // initializeHealth(player1Username, player2Username);
        rounds = 4;
    }

    private class CardButtonListener implements ActionListener {
        private int player;
        private int index;

        public CardButtonListener(int player, int index) {
            this.player = player;
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle card selection and placement logic
            List<Card> battleDeck = (player == 1) ? player1BattleDeck : player2BattleDeck;
            Card selectedCard = battleDeck.get(index);
            if (selectedCard != null) {
                // Show card information or handle placement
                // placeCard(player, selectedCard);
            }
        }
    }

    // Method to update the UI based on game state
    private void updateUI() {
        player1Info.setText("Player 1 HP: " + player1Health);
        player2Info.setText("Player 2 HP: " + player2Health);
        roundsInfo.setText("Rounds Left: " + rounds);

        // Update playing field and battle decks
        player1Field.removeAll();
        player2Field.removeAll();
        for (int i = 0; i < TIMELINE_LENGTH; i++) {
            JLabel player1Block = new JLabel(String.valueOf(player1Timeline[i]));
            JLabel player2Block = new JLabel(String.valueOf(player2Timeline[i]));
            player1Field.add(player1Block);
            player2Field.add(player2Block);
        }

        // Update battle deck buttons
        for (int i = 0; i < 5; i++) {
            player1BattleDeckButtons[i].setText(player1BattleDeck.get(i) != null ? player1BattleDeck.get(i).toString() : "");
            player2BattleDeckButtons[i].setText(player2BattleDeck.get(i) != null ? player2BattleDeck.get(i).toString() : "");
        }

        player1Field.revalidate();
        player2Field.revalidate();
        player1Field.repaint();
        player2Field.repaint();
    }

    // Method to start the timeline animation and update game state
    private void startTimeline() {
        Timer timer = new Timer(1000, new ActionListener() {
            private int position = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (position < TIMELINE_LENGTH) {
                    // Move timeline marker and update damage/HP
                    updateUI();
                    position++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new GamePlayGUI("player1", "player2");
    }
}
