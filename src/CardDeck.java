import java.util.ArrayList;
import java.util.List;

/**
 * @author Fuqing Wang, Hang Xu
 */
public class CardDeck {
    private List<Card> deck;

    public CardDeck() {
        this.deck = new ArrayList<>();
    }

    public void prepareDeck() {
        // In a normal poker game, there are 4 suits
        this.deck.clear();
        int suitSize = Suit.values().length;
        int rankSize = Card.getRankSize();
        for (int suit = 0; suit < suitSize; suit++) {
            // ToDo: Rank should be further abstracted, currently, using a static method in Card class to return the size
            for (int rank = 0; rank < rankSize; rank++) {
                this.deck.add(new Card(Suit.values()[suit], rank));
            }
        }
    }

    public void prepare2Deck() {
        // In a normal poker game, there are 4 suits
        this.deck.clear();
        int suitSize = Suit.values().length;
        int rankSize = Card.getRankSize();
        for(int j = 0; j < 2; j++){
            for (int suit = 0; suit < suitSize; suit++) {
                // ToDo: Rank should be further abstracted, currently, using a static method in Card class to return the size
                for (int rank = 0; rank < rankSize; rank++) {
                    this.deck.add(new Card(Suit.values()[suit], rank));
                }
            }
        }
    }

    public Card drawCard() {
        int random = (int) Math.floor(Math.random() * this.deck.size());
        Card card = this.deck.get(random);
        this.deck.remove(random);
        return card;
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public int size() {
        return this.deck.size();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < deck.size(); i++){
            sb.append(deck.get(i).toString()).append(',');

        }
        return sb.substring(0, sb.length() - 1)+ "]";

    }
}
