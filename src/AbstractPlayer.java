import java.util.*;

/**
 * @author Fuqing Wang, Hang Xu
 */
public abstract class AbstractPlayer implements Comparable<AbstractPlayer> {
    /** protected so that only accessible within the application */
    protected int balance;
    private String name;
    private List<Card> hand;
    private List<List<Card>> hands;
    private boolean isDealer;
    private static final int TWENTY_ONE = 21;
    private static final int ThIRTY_ONE = 31;
    private static final String ACE = "A";
    private int bet;

    public AbstractPlayer(String name, int balance, boolean isDealer) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.hands = new ArrayList<>();
        this.balance = balance;
        this.isDealer = isDealer;
    }

    /** return player's current bet */
    public int getBet(){
        return this.bet;
    }

    /** set player's bet */
    public void setBet(int bet){
        this.bet = bet;
    }

    /** return if the player is a dealer */
    public boolean getIsDealer(){
        return this.isDealer;
    }

    /** set player as a dealer */
    public void setIsDealer(boolean isDealer){
        this.isDealer = isDealer;
    }

    /** return player's name */
    public String getName(){
        return this.name;
    }

    /** set player's balance */
    public void setName(String name){
        this.name = name;
    }

    /** return player's current hand */
    public List<Card> getHand() {
        return this.hand;
    }

    /** set player's current hand
     * for example, player has two hands [spade 4, heart 5] and [diamond 5, club 6].
     * we call setHand([diamond 5, club 6]) when player operates on second hand of card
     * */
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    /** return player's current hands */
    public List<List<Card>> getHands() {
        return this.hands;
    }

    /** set player's current hands */
    public void setHands(List<List<Card>> hands) {
        this.hands = hands;
    }

    /** return player's balance */
    public void initHands() {
        List<List<Card>> myHand = new ArrayList<>();
        myHand.add(this.hand);
        this.hands = myHand;
    }

    /** clean up all cards player has */
    public void cleanHands() {
        this.hand.clear();
        this.hands.clear();
    }

    /** return player's balance */
    public int getBalance() {
        return this.balance;
    }

    /** set player's balance */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /** add a card to current player object */
    public void addCard(Card card) {
        this.hand.add(card);
    };

    /** Compute the total value of a Hand */
    public int calculateRank() {
        int value = 0;
        // count how many "Ace" in the hand that we have used as a value of 11
        boolean count = false;
        List<Card> hand = this.hand;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            value += card.getRank();
            // Special case: since "Ace" could have value 11 or 1
            // Here we treat "Ace" as 11 if the total value is less than 21
            // Therefore, we add another 10 (the 1 has been added above) and increase the count counter
            if (card.getFace().equals(ACE)) {
                value += 10;
                count = true;
            }
            // When the total value is larger than 21 and we have used "Ace" as 11 not 1, we need to treat it as 1
            // Therefore, subtract 10 from the total value and decrease the count counter
            while (value > ThIRTY_ONE && count) {
                value -= 10;
                count = false;
            }
        }
        return value;
    }

    /** Compute the total value of a Hand */
    public int calculateRank(int index) {
        int value = 0;
        // count how many "Ace" in the hand that we have used as a value of 11
        int count = 0;
        List<Card> hand = this.hands.get(index);
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            value += card.getRank();
            // Special case: since "Ace" could have value 11 or 1
            // Here we treat "Ace" as 11 if the total value is less than 21
            // Therefore, we add another 10 (the 1 has been added above) and increase the count counter
            if (card.getFace().equals(ACE)) {
                value += 10;
                count += 1;
            }
            // When the total value is larger than 21 and we have used "Ace" as 11 not 1, we need to treat it as 1
            // Therefore, subtract 10 from the total value and decrease the count counter
            while (value > TWENTY_ONE && count > 0) {
                value -= 10;
                count -= 1;
            }
        }
        return value;
    }

    /** Print the Hand that the Player currently possesses, in the format of [spade 4, heart 5] */
    public String printHand() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(Card card: this.hand) {
            sb.append(card.getSuit()).append(" ").append(card.getFace()).append(", ");
        }
        sb.trimToSize();
        return sb.substring(0, sb.length() - 2) + "]";
    }

    /** Print a specific Hand amongst all Hands that the player has, in the format of [spade 4, heart 5]*/
    public String printHand(int index) {
        StringBuilder sb = new StringBuilder();
        List<Card> hand = this.hands.get(index);
        for (Card card: hand) {
            sb.append(card.getSuit()).append(" ").append(card.getFace()).append(", ");
        }
        sb.trimToSize();
        return sb.substring(0, sb.length() - 2) + "]";
    }

    /** Print All Hands that the Player might have, in the format of [[spade 4, heart 5], [spade Q, heart 2]] */
    public String printHands() {
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (int i = 0; i < this.hands.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            List<Card> hand = hands.get(i);
            for (Card card: hand) {
                sb.append(card.getSuit().toString()).append(" ").append(card.getFace()).append(", ");
            }
            sb.trimToSize();

            res.append(sb.substring(0, sb.length() - 2)).append("]").append("]");
        }
        return res.toString();
    }

    /** sort players based on balance */
    @Override
    public int compareTo(AbstractPlayer player1) {
        return Integer.compare(player1.getBalance(), this.getBalance());
    }
}