# Console Blackjack Game

A simple, interactive console-based Blackjack game written in Java. Play against the dealer, place chips, and try to beat the house!

## Features

-   **Betting System**: Start with 100 chips and place bets each round.
-   **Game Actions**:
    -   **Hit**: Take another card.
    -   **Stand**: End your turn and let the dealer play.
    -   **Double Down**: Double your bet and receive exactly one more card.
    -   **Surrender**: Forfeit the hand and lose only half your bet.
-   **Insurance**: Option to buy insurance when the dealer shows an Ace (pays 2:1).
-   **Blackjack Payouts**: Natural Blackjack pays 3:2.
-   **Dealer Rules**:
    -   Dealer must hit on a "Soft 17".
    -   Dealer stands on "Hard 17" or higher.
-   **Game State**: Tracks your chip count across rounds until you run out or choose to quit.

## How to Run

### Prerequisites
-   Java Development Kit (JDK) 22 or higher (for direct source execution).

### Quick Start (Run from Source)
Run the game directly without compiling manually:

```bash
java src/Main.java
```

### Alternative: Compile and Run
If you are on an older Java version or prefer compiling:

1.  Compile the source files:
    ```bash
    javac -d bin src/*.java
    ```

2.  Run the compiled game:
    ```bash
    java -cp bin Main
    ```

## How to Play

1.  **Start**: Run the application. You begin with 100 chips.
2.  **Bet**: Enter a bet amount (must be within your chip balance).
3.  **Deal**: You and the dealer are dealt two cards. One of the dealer's cards is hidden.
4.  **Action**:
    -   If you have a natural Blackjack (21), you win automatically unless the dealer also has Blackjack (push).
    -   otherwise, choose to Hit, Stand, Double Down, or Surrender.
5.  **Dealer's Turn**: After you stand, the dealer reveals their card and hits/stands according to the rules.
6.  **Result**:
    -   Beat the dealer's score without busting (going over 21) to win.
    -   If you bust, you lose immediately.
    -   If the dealer busts, you win.
    -   Ties result in a "Push" (bet returned).

## Project Structure

-   `src/Main.java`: The main game loop and user interaction logic.
-   `src/Deck.java`: Manages the deck of 52 cards using an `ArrayList`.
-   `src/Hand.java`: Represents a player or dealer hand, handling scoring and Ace logic (1 vs 11).

## Author
Liam Bauer
