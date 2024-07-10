package org.example.demo1;

import java.util.Scanner;
import java.util.List;
public class InerLoginMenu {
    public static void Display(User user) {
        boolean loggedIn = true;
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = user;
        System.out.println("User logged in successfully!");
        while (loggedIn) {
            System.out.println("Menu:");
            System.out.println("1. Start Game");
            System.out.println("2. See Cards");
            System.out.println("4. Store");
            System.out.println("5. Profile");
            System.out.println("6. Admin login");
            System.out.println("7. History");
            System.out.println("8. Log Out");
            int choice = scanner.nextInt();
            scanner.nextLine();

       /*     switch (choice) {
                case 1:
                    System.out.println("Choose the game mode:");
                    System.out.println("1: 1v1 ");
                    System.out.println("2: bet 1v1");
                    int mode = scanner.nextInt();
                    scanner.nextLine();
                    if (mode == 1) {

                        System.out.println("Please type Player2 username:");
                        String player2Username = scanner.nextLine();
                        System.out.println("Please type Player2 password:");
                        String player2Password=scanner.nextLine();
                        User user2 = User.checkUserPasswordForLogin(player2Username);
                        if (User.checkUserName(player2Username) && User.getPassword(user2).equals(player2Password)) {
                            GamePlay.start(loggedInUser.getUsername(), player2Username,false,0);}
                        else{
                            System.out.println("Player 2 password is incorrect");
                        }
                    } else if (mode == 2) {
                            System.out.println("Please type Player2 username:");
                            String player2Username = scanner.nextLine();
                            System.out.println("Please type Player2 password:");
                            String player2Password = scanner.nextLine();
                            User user2 = User.checkUserPasswordForLogin(player2Username);

                        if (User.checkUserName(player2Username) && User.getPassword(user2).equals(player2Password)) {
                            System.out.println("HOW MUCH DO YOU WANT TO BET?");
                            int bet = scanner.nextInt();

                            if((user.getCoins()>=bet)&&(user2.getCoins()>=bet))
                            {
                                GamePlay.start(loggedInUser.getUsername(), player2Username,true,bet);}
                            else if (user.getCoins()<bet){
                                System.out.println("Player 1 has not enough coins");
                            }
                            else if (user2.getCoins()<bet){
                                System.out.println("Player 2 has not enough coins");
                            }
                            else {
                                System.out.println("Both palyer dont have enough coins");
                            }

                        }
                        else{
                            System.out.println("Player 2 password is incorrect");
                        }
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                case 2:
                    displayUserCards(loggedInUser);
                    break;
                case 5:
                    ProfileMenu.displayProfileMenu(loggedInUser);
                    break;
                case 4:
                    Store.displayStoreMenu(loggedInUser);
                    break;
                case 6:
                    System.out.println("Please type Admin password:");
                    String pass= scanner.nextLine();
                    if (pass.equals("AdminAdmin"))
                    {
                        System.out.println("Welcome Boss");
                        Admin.DisplayAdmin(user);

                    }
                    else{
                        System.out.println("Incorrect Password");
                    }
                    break;
                case 8:
                    loggedIn = false;
                    System.out.println("Logged out successfully.");
                    UserMenu.displayMenu();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private static void displayUserCards(User user) {
        System.out.println("\nYour Cards:");
        List<Card> cards = user.getCardList();
        CardDisplay.displayCards(cards);
    }*/
        }
    }
}

