import java.util.Scanner;
import java.util.ArrayList;

public class MainGame {

    public ArrayList<Card> WAR(ArrayList<Card> pot, PlayerDeck p1, PlayerDeck p2) {
        // war occurs when the two cards play are the same value
        // each player then plays 3 cards and compares their values to each other.
        // the player who wins the most out of these 3 comparisons wins all of the cards in the pot
        // the 3 cards played from each player in the war are also included in the pot
        // a tie can occur, in which case we will repeat war until a victor is determined.

        int playerWin = 0;
        int cpuWin = 0;

        System.out.println("\tPlayer \t\t\t    Cpu");

        // we will use try-catch statements to detect if either player runs out of cards during the war phase
        // keep in mind a player can still win with just two cards remaining if both those cards win over opponent
        for(int i = 0; i < 3; i++) {
            try {
                Card playerCard = p1.drawTop();
                try {
                    Card cpuCard = p2.drawTop();

                    String result = playerCard.getName() + " vs " + cpuCard.getName();

                    int compare = playerCard.compareTo(cpuCard);
                    if (compare > 0) {
                        playerWin += 1;
                        result += " (WIN)";
                    } else if (compare < 0) {
                        cpuWin += 1;
                        result += " (LOSS)";
                    } else {
                        result += " (TIE)";
                        // nothing occurs if tie
                    }

                    System.out.println(result);

                    pot.add(playerCard);
                    pot.add(cpuCard);

                } catch (IndexOutOfBoundsException p) {
                    // when the cpu runs out of cards in deck
                    String result = playerCard.getName() + " vs Nothing (WIN)";
                    System.out.println(result);
                    playerWin += 1;
                    pot.add(playerCard);
                }

            } catch (IndexOutOfBoundsException e) {
                // when the player runs out of cards in deck
                Card cpuCard = p2.drawTop();
                String result = "Nothing vs " + cpuCard.getName() + " (LOSS)";
                System.out.println(result);
                cpuWin += 1;
                pot.add(cpuCard);
            }


        }

        if (playerWin > cpuWin) {
            p1.setWin();
            System.out.println("You won the war!");
        } else if (playerWin < cpuWin) {
            p2.setWin();
            System.out.println("Cpu won the war!");
        } else {
            // if tie, we run war again
            System.out.println("Still a tie, running war again...");
            pot = WAR(pot, p1, p2);

        }

        return pot;
    }

    public ArrayList<Card> mainRound(int currentPlay, ArrayList<Card> pot, PlayerDeck p1, PlayerDeck p2, Card lastCard) {
        // pot: represents all cards in current pot. This method will be recursive
        // currentPlay: if 1, player has won last
        // if -1, cpu has has won last
        // if 0, then it is first turn of round
        // this is important to determine if player/cpu gets another change to play
        // p1: represents player's deck
        // p2: represents cpu's deck

        if (currentPlay == 0) {
            // first turn of the round
            Card playerCard = p1.drawTop();
            System.out.print("YOU: ");
            playerCard.printName();

            Card cpuCard = p2.drawTop();
            System.out.print("Cpu: ");
            cpuCard.printName();

            int compare = playerCard.compareTo(cpuCard);
            if (compare > 0) {
                System.out.println("Cpu gets another chance!");
                pot.add(cpuCard);
                pot = (mainRound(1, pot, p1, p2, playerCard));

            } else if (compare < 0) {
                System.out.println("You get another chance!");
                pot.add(playerCard);
                pot = (mainRound(-1, pot, p1, p2, cpuCard));

            } else {
                pot = WAR(pot, p1, p2);
            }

        } else if (currentPlay == 1) {
            if (p2.getSize() == 0) {
                // checking if p2 has any cards left
                p1.setWin();
                pot.add(lastCard);
                return pot;
            }

            Card cpuCard = p2.drawTop();
            System.out.print("Cpu: ");
            cpuCard.printName();

            int compare = lastCard.compareTo(cpuCard);

            if (compare > 0) {
                // in this case, player has won twice, so player will win the round
                // and collect all cards from pot
                System.out.println("You win this round!");
                pot.add(lastCard);
                pot.add(cpuCard);

                p1.setWin();

                return pot;
            } else if (compare < 0) {
                // if cpu wins, then player will get another chance
                pot.add(lastCard);

                System.out.println("You get another chance!");

                // rewrite lastcard to cpu's card
                lastCard = cpuCard;
                pot = (mainRound(-1, pot, p1, p2, lastCard));

            } else {
                pot = WAR(pot, p1, p2);
            }
        } else {
            // if currentPlay == -1
            if (p1.getSize() == 0) {
                // checking if p1 has any cards left
                p2.setWin();
                pot.add(lastCard);
                return pot;
            }

            Card playerCard = p1.drawTop();
            System.out.print("YOU: ");
            playerCard.printName();

            int compare = playerCard.compareTo(lastCard);
            if (compare > 0) {
                // if player wins, cpu will get another chance
                System.out.println("Cpu gets another chance!");
                pot.add(lastCard);
                lastCard = playerCard;

                pot = (mainRound(1, pot, p1, p2, lastCard));

            } else if (compare < 0) {
                // cpu wins the whole round
                // cpu gets everything in returned pot
                System.out.println("Cpu wins this round!");

                pot.add(lastCard);
                pot.add(playerCard);

                p2.setWin();

                return pot;

            } else {
                pot = WAR(pot, p1, p2);
            }


        }

        return pot;

    }

    public static void main(String[] args) {
        MainGame main = new MainGame();

        Scanner scan = new Scanner(System.in);

        Deck deck = new Deck();

        int turncounter = 0;

        PlayerDeck player = new PlayerDeck();
        PlayerDeck cpu = new PlayerDeck();

        // randomly giving cards to player and cpu
        while (deck.getSize() > 0) {
            player.insert(deck.drawRandom());
            cpu.insert(deck.drawRandom());
        }

        System.out.println("Hello! Welcome to this game of Domination. \n" +
                "You have been dealt 26 cards from a standard deck.");

        // main game loop
        while (true) {

            // first, check end game conditions
            if (player.getSize() == 0) {
                cpu.setWin();
                break;
            }
            if (cpu.getSize() == 0) {
                player.setWin();
                break;
            }

            System.out.println("Type anything to play next round, or type \"quit\" to quit: ");
            String input = scan.nextLine();

            if(input.toLowerCase().equals("quit")) {
                System.out.println("quitting...");
                break;
            }

            if (input.toLowerCase().equals("deck")) {
                System.out.println("You have " + player.getSize() + " cards.");

            } else if (input.toLowerCase().equals("testwar")) {
                PlayerDeck test1 = new PlayerDeck();
                PlayerDeck test2 = new PlayerDeck();

                test1.insert(new Card(2, "test"));
                test1.insert(new Card(3, "test"));
                test1.insert(new Card(14, "test"));

                test1.insert(new Card(3, "test"));
                test1.insert(new Card(3, "test"));
                test1.insert(new Card(3, "test"));

                test2.insert(new Card(14, "test"));
                test2.insert(new Card(14, "test"));
                test2.insert(new Card( 14, "test"));

                test2.insert(new Card(14, "test"));
                test2.insert(new Card(14, "test"));

                ArrayList<Card> temp = new ArrayList<Card>();
                ArrayList<Card> pot = main.WAR(temp, test1, test2);


            }
            else {
                turncounter += 1;

                ArrayList<Card> temp = new ArrayList<Card>();
                ArrayList<Card> pot = main.mainRound(0, temp, player, cpu, null);

                // identify who wins the pot
                if (player.getWin() == 1) {
                    for (int i = 0; i < pot.size(); i++) {
                        player.insert(pot.get(i));
                    }
                    player.resetWin();

                } else {
                    for (int i = 0; i < pot.size(); i++) {
                        cpu.insert(pot.get(i));
                    }
                    cpu.resetWin();

                }
            }
            
        }

        if (player.getWin() == 1) {
            System.out.println("YOU WIN! You beat the computer in " + turncounter + " turns");
        } else {
            System.out.println("You Lost! The cpu beat you in " + turncounter + " turns");
        }

    }


}
