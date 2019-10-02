/**
 * @author Fuqing Wang, Hang Xu
 */
public class Card {
    private Suit suit;
    private int rank;
    private String face;
    /** uses the index as its rank value */
    private static String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.rank = value > 9 ? 10 : value + 1;
        this.face = ranks[value];
    }

    public int getRank() {
        return this.rank;
    }

    public String getFace() {
        return face;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public static int getRankSize() {
        return ranks.length;
    }

    @Override
    public String toString() {
        return this.suit.toString() + " " + face;
    }
}
