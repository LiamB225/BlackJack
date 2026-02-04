import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a standard 52-card Blackjack deck.
 * <p>
 * Cards are stored as integers 1–13, where:
 * <ul>
 * <li>1 = Ace</li>
 * <li>11 = Jack</li>
 * <li>12 = Queen</li>
 * <li>13 = King</li>
 * <li>2–10 = numeric cards</li>
 * </ul>
 * Each value appears four times (one for each suit). The class supports deck
 * resetting and drawing random cards without replacement.
 */
public class Deck {

    /**
     * Internal list storing all remaining cards in the deck.
     * Values range from 1–13, and each value appears four times.
     */
    final private ArrayList<Integer> deckArray = new ArrayList<>();

    /**
     * Constructs a new deck and automatically initializes it
     * to a full 52-card set.
     */
    public Deck() {
        deckArray.clear();
        for (int card = 1; card <= 13; card++) {
            for (int suite = 0; suite < 4; suite++) {
                deckArray.add(card);
            }
        }
    }

    /**
     * Resets the deck to a full 52-card set.
     * <p>
     * This method clears the deck and repopulates it with
     * all cards from Ace (1) through King (13), four of each.
     */
    public void resetDeck() {
        deckArray.clear();
        for (int card = 1; card <= 13; card++) {
            for (int suite = 0; suite < 4; suite++) {
                deckArray.add(card);
            }
        }
    }

    /**
     * Draws a random card from the deck and removes it from play.
     *
     * @return the integer card value drawn (1–13)
     */
    public int drawCard() {
        Random rnd = new Random();
        int randIndex = rnd.nextInt(deckArray.size());
        int cardVal = deckArray.remove(randIndex);
        return cardVal;
    }
}