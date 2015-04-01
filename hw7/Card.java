// Represents a card from a 52-card deck.
public class Card {

    // Declare some constants. Your code may or may not find this helpful.
    public static final int SPADES = 0;
    public static final int HEARTS = 1;
    public static final int DIAMONDS = 2;
    public static final int CLUBS = 3;

    // These should be declared private, but declare them public for ease of
    // testing (name, for compatibility with the provided Junit test)
    public int suit;
    public int number;

    public Card(int suit, int number) {
        if (suit < 1 || suit > 4) {
            throw new IllegalArgumentException("Invalid card suit input");
        }
        if (number < 1 || number > 13) {
            throw new IllegalArgumentException("Invalid card number input");
        }
        this.suit = suit;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Card) {
            Card other = (Card) o;
            return (suit == other.suit) && (number == other.number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number+(suit * 13);
    }
}