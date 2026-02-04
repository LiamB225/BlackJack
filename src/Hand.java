import java.util.ArrayList;

/**
 * Represents a Blackjack hand. Stores both the numerical values of cards for
 * scoring and the string versions used for display. Handles Blackjack-specific
 * rules such as Ace evaluation (1 or 11), soft hands, and blackjack detection.
 */
public class Hand {

    /**
     * The list of card values used for scoring.
     * Aces are stored as 1, face cards (J, Q, K) count as 10.
     */
    final private ArrayList<Integer> handValues;

    /**
     * The list of display strings representing each card (e.g., "A", "10", "K").
     */
    final private ArrayList<String> displayHand;

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        handValues = new ArrayList<>();
        displayHand = new ArrayList<>();
    }

    /**
     * Adds a card to the hand.
     *
     * @param cardValue the raw card value from the deck (1–13),
     *                  where 1 = Ace, 11 = Jack, 12 = Queen, 13 = King.
     */
    public void addCard(int cardValue) {
        int value = (cardValue > 10) ? 10 : cardValue;
        handValues.add(value);

        switch (cardValue) {
            case 1 -> displayHand.add("A");
            case 11 -> displayHand.add("J");
            case 12 -> displayHand.add("Q");
            case 13 -> displayHand.add("K");
            default -> displayHand.add(Integer.toString(cardValue));
        }
    }

    /**
     * Calculates the total value of the hand, treating Aces as either 1 or 11
     * depending on whichever is most advantageous without busting.
     *
     * @return the total hand value
     */
    public int getTotal() {
        int total = 0;
        boolean hasAce = false;

        for (int val : handValues) {
            if (val == 1) {
                hasAce = true;
            }
            total += val;
        }

        // If hand contains an Ace and treating it as 11 doesn't bust,
        // count it as 11 instead of 1 (add 10 since Ace is already counted as 1).
        if (hasAce && total + 10 <= 21) {
            total += 10;
        }

        return total;
    }

    /**
     * Determines whether this hand is a natural blackjack (Ace + 10-valued card).
     *
     * @return true if the hand contains exactly two cards totaling 21
     */
    public boolean isBlackjack() {
        return getTotal() == 21 && handValues.size() == 2;
    }

    /**
     * Determines whether the hand is a "soft" hand, meaning it contains an Ace
     * counted as 11 without busting.
     *
     * @return true if the hand is soft, false otherwise
     */
    public boolean isSoft() {
        int total = 0;
        boolean hasAce = false;

        for (int val : handValues) {
            if (val == 1) {
                hasAce = true;
            }
            total += val;
        }

        return hasAce && (total + 10 <= 21);
    }

    /**
     * Removes all cards from the hand, resetting it to empty.
     */
    public void clear() {
        handValues.clear();
        displayHand.clear();
    }

    /**
     * Returns the list of card display strings.
     *
     * @return an ArrayList of display card strings
     */
    public ArrayList<String> getDisplayHand() {
        return displayHand;
    }

    /**
     * Returns the display string for the card at a given index.
     *
     * @param index the index of the card
     * @return the display string of the card, or an empty string if invalid index
     */
    public String getDisplayCard(int index) {
        if (index >= 0 && index < displayHand.size()) {
            return displayHand.get(index);
        }
        return "";
    }

    /**
     * Gets the number of cards currently in the hand.
     *
     * @return the size of the hand
     */
    public int getSize() {
        return handValues.size();
    }
}