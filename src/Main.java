
/**
 * Main.java
 * 
 * A simple console-based Blackjack game where a player can play against the dealer.
 * The player starts with 100 chips, can place bets, and the game supports hits, stands,
 * double down, surrender, insurance(must be half the bet), and blackjack payouts.
 * 
 * @author Liam Bauer
 * @version 2.0
 * @date 2025-12-05
 */

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class for a console-based Blackjack game.
 */
public class Main {
    static Deck deck = new Deck();
    static Hand userHand = new Hand();
    static Hand houseHand = new Hand();
    static Scanner scnr = new Scanner(System.in);

    /**
     * Clears both the player's and dealer's hands.
     */
    public static void resetHands() {
        userHand.clear();
        houseHand.clear();
    }

    /**
     * Deals initial cards to the player and dealer (2 each).
     */
    public static void startGame() {
        for (int i = 0; i < 2; i++) {
            userHand.addCard(deck.drawCard());
            houseHand.addCard(deck.drawCard());
        }
    }

    /**
     * Displays the dealer's and player's hands.
     * 
     * @param gameEnd True if the game has ended, in which case the dealer's full
     *                hand is shown.
     */
    public static void displayHands(boolean gameEnd) {
        System.out.println("Dealer's Hand:");
        if (gameEnd) {
            for (String card : houseHand.getDisplayHand()) {
                System.out.print(card + " ");
            }
        } else {
            System.out.print("# " + houseHand.getDisplayCard(1));
        }
        System.out.println("\n");

        System.out.println("Your Hand:");
        for (String card : userHand.getDisplayHand()) {
            System.out.print(card + " ");
        }
        System.out.println("\n");
    }

    /**
     * Prompts the user for a valid bet amount.
     * 
     * @param playerChips The player's current chip count.
     * @return The bet amount.
     */
    public static int getBetAmount(int playerChips) {
        int bet;
        while (true) {
            try {
                System.out.println("Place your bet:");
                bet = scnr.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scnr.next(); // Clear invalid input
                continue;
            }
            if (bet > 0 && bet <= playerChips) {
                return bet;
            }
            System.out.println("Invalid bet amount.");
        }
    }

    /**
     * Prompts the user for their action.
     * 
     * @param canSurrender True if surrender is allowed.
     * @param canDouble    True if double down is allowed.
     * @return "h" (hit), "s" (stand), "d" (double), "sur" (surrender).
     */
    public static String getPlayerAction(boolean canSurrender, boolean canDouble) {
        while (true) {
            System.out.print("Would you like to hit (h) or stand (s)");
            if (canDouble) {
                System.out.print(", double down (d)");
            }
            if (canSurrender) {
                System.out.print(", surrender (sur)");
            }
            System.out.println("?");

            String input = scnr.next();
            if (input.equals("h") || input.equals("s")) {
                return input;
            } else if (canDouble && input.equals("d")) {
                return input;
            } else if (canSurrender && input.equals("sur")) {
                return input;
            }
            System.out.println("Not a valid argument. Enter Again.");
        }
    }

    /**
     * Prompts the user for insurance.
     * 
     * @return true if user wants insurance, false otherwise.
     */
    public static boolean getInsuranceInput() {
        while (true) {
            System.out.println("Dealer shows an Ace! Buy insurance? (y/n)");
            String input = scnr.next();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            System.out.println("Not a valid argument. Enter Again.");
        }
    }

    /**
     * Prompts the user to play again.
     * 
     * @return true if the user wants to play again, false otherwise.
     */
    public static boolean getPlayAgainInput() {
        while (true) {
            System.out.println("Would you like to play again? (y/n)");
            String input = scnr.next();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            System.out.println("Not a valid argument. Enter Again.");
        }
    }

    /**
     * Plays the dealer's turn and determines the outcome.
     * 
     * @param userBust True if the player has busted.
     * @return 0 if player lost, 1 if player won, 2 if tie.
     */
    public static int playDealerTurn(boolean userBust) {
        if (userBust) {
            System.out.println("You busted! Dealer wins.\n");
            return 0;
        }

        // If dealer has blackjack, return result
        if (houseHand.isBlackjack()) {
            return userHand.isBlackjack() ? 2 : 0;
        } else if (userHand.isBlackjack()) {
            return 1;
        }

        System.out.println("Dealer reveals hand:");

        // Dealer hits on soft 17
        while (houseHand.getTotal() < 17 || (houseHand.getTotal() == 17 && houseHand.isSoft())) {
            System.out.println("Dealer hits.");
            houseHand.addCard(deck.drawCard());
            System.out.println("Dealer drew: " + houseHand.getDisplayCard(houseHand.getSize() - 1));
        }

        int houseTotal = houseHand.getTotal();
        int userTotal = userHand.getTotal();

        if (houseTotal < 21) {
            System.out.println("Dealer stands with " + houseTotal + ".");
        }

        // Determine winner
        if (houseTotal > 21) {
            System.out.println("Dealer busted! You win.\n");
            return 1;
        } else if (houseTotal == userTotal) {
            System.out.println("It's a tie.\n");
            return 2;
        } else if (houseTotal < userTotal) {
            System.out.println("You have " + userTotal + ", Dealer has " + houseTotal + ". You win!\n");
            return 1;
        } else {
            System.out.println("You have " + userTotal + ", Dealer has " + houseTotal + ". Dealer wins.\n");
            return 0;
        }
    }

    /**
     * Clears the console for a fresh display.
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Plays a single round of Blackjack.
     * 
     * @param bet         The bet amount for this round.
     * @param playerChips The player's current total chips (for validation).
     * @return The net change in chips.
     */
    public static int playRound(int bet, int playerChips) {
        // Setup
        deck.resetDeck();
        resetHands();
        System.out.println("Dealing Cards...\n");
        startGame();

        boolean gameOver = false;
        boolean userBust = false;
        boolean playerBlackjack = false;
        boolean surrendered = false;
        int currentBet = bet;
        int insuranceBet = 0;

        // Insurance Check
        // Dealer's visible card is at index 1
        if (houseHand.getDisplayCard(1).equals("A") && bet > 1) {
            // Insurance is half the bet
            int potentialInsurance = (int) (currentBet * 0.5);
            if (playerChips - currentBet >= potentialInsurance) {
                if (getInsuranceInput()) {
                    insuranceBet = potentialInsurance;
                    System.out.println("Insurance bet placed: " + insuranceBet);
                }
            } else {
                System.out.println("Dealer shows Ace, but you don't have enough chips for insurance.");
            }
        }

        // Check for dealer natural blackjack
        if (houseHand.isBlackjack()) {
            displayHands(true);
            int chipChange = 0;

            // Handle Insurance
            if (insuranceBet > 0) {
                System.out.println("Dealer has Blackjack! Insurance pays 2:1.");
                chipChange += insuranceBet * 2; // Win 2x insurance bet
            } else if (insuranceBet == 0 && houseHand.getDisplayCard(1).equals("A")) {
                System.out.println("Dealer has Blackjack! Insurance lost (if any).");
            }

            if (userHand.isBlackjack()) {
                System.out.println("You also have Blackjack! It's a Push on the main bet.\n");
                // Main bet returned (change 0)
            } else {
                System.out.println("You lose the main bet.\n");
                chipChange -= currentBet;
            }

            return chipChange;
        } else {
            // Dealer does NOT have blackjack
            if (insuranceBet > 0) {
                System.out.println("Dealer does not have Blackjack. Insurance bet lost.");
            }
        }

        // Player turn
        boolean firstTurn = true;

        // Check for initial player blackjack
        if (userHand.isBlackjack()) {
            playerBlackjack = true;
            gameOver = true;
            System.out.println("You got blackjack!\n");
        }

        while (!gameOver && !houseHand.isBlackjack()) {
            int userTotal = userHand.getTotal();

            if (userTotal == 21) {
                // Player reached 21 but it's not a natural blackjack if we are in this loop
                // (unless it was initial, handled above)
                gameOver = true;
            } else {
                if (!firstTurn) {
                    clearConsole();
                }
                displayHands(false);

                // Determine available actions
                boolean canSurrender = firstTurn;
                boolean canDouble = firstTurn && (playerChips - currentBet >= currentBet);

                String input = getPlayerAction(canSurrender, canDouble);
                System.out.println();

                switch (input) {
                    case "s" -> gameOver = true;
                    case "sur" -> {
                        System.out.println("You surrendered.");
                        surrendered = true;
                        gameOver = true;
                    }
                    case "d" -> {
                        System.out.println("Doubling down!");
                        currentBet *= 2;
                        userHand.addCard(deck.drawCard());
                        System.out.println("You drew: " + userHand.getDisplayCard(userHand.getSize() - 1));

                        if (userHand.getTotal() > 21) {
                            userBust = true;
                        }
                        gameOver = true; // Double down ends turn after 1 card
                    }
                    default -> {
                        userHand.addCard(deck.drawCard());
                        if (userHand.getTotal() > 21) {
                            userBust = true;
                            gameOver = true;
                        }
                    }
                }
                firstTurn = false;
            }
        }

        // Dealer turn and determine winner
        // Use userBust or surrendered to control logic if needed, but playDealerTurn
        // relies on userBust mainly for message
        // If surrendered, we still want to show dealer playing out, so we run
        // playDealerTurn
        int result = playDealerTurn(userBust);
        displayHands(true);

        // Calculate chip change
        int chipChange = 0;

        // Deduct insurance if lost (dealer didn't have blackjack)
        if (insuranceBet > 0) {
            chipChange -= insuranceBet;
        }

        if (surrendered) {
            chipChange -= (bet / 2);
            System.out.println("You surrendered. Lost " + (bet / 2) + " chips.");
        } else if (result == 0) {
            chipChange -= currentBet;
            System.out.println("You lost " + currentBet + " chips.");
        } else if (result == 1) {
            chipChange += currentBet;
            System.out.println("You won " + currentBet + " chips!");
        } else {
            System.out.println("It's a tie. Bet returned.");
        }

        // Blackjack pays 3:2
        if (playerBlackjack && result == 1) {
            int blackjackBonus = (int) (bet * 0.5); // Bonus is on original bet
            chipChange += blackjackBonus;
            System.out.println("Blackjack bonus! You won an additional " + blackjackBonus + " chips!");
        }

        return chipChange;
    }

    /**
     * Main method to start the Blackjack game.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        int playerChips = 100;

        System.out.println("Welcome to BlackJack! Let's begin.");

        while (playerChips > 0) {
            System.out.println("You have " + playerChips + " chips.");

            int bet = getBetAmount(playerChips);
            int chipChange = playRound(bet, playerChips);
            playerChips += chipChange;

            if (playerChips <= 0) {
                System.out.println("\nYou have lost all your chips!");
                System.out.println("GAME OVER! Better luck next time!");
                break;
            }

            if (!getPlayAgainInput()) {
                break;
            }
            clearConsole();
        }

        System.out.println("Goodbye!");
        scnr.close();
    }
}