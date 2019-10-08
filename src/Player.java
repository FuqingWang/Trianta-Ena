import java.util.ArrayList;
import java.util.List;
/**
 * @author Fuqing Wang, Hang Xu
 */
public class Player extends AbstractPlayer {

    /** each element of this Arraylist is a winning multiplier for each hand
     If the game ties, the element will be 1 (gives back the bet money). If player wins, the element will be 2 for normal and 4 for double up, etc. */
    private List<Integer> winMultiplier;

    public Player(String name, int balance) {
        super(name, balance, false);
        this.winMultiplier = new ArrayList<>();
    }

    public Player(String name, int balance, boolean isDealer) {
        super(name, balance, isDealer);
    }

    /** add balance when player wins */
    public void addBalance(int balance) {
        this.balance += balance;
    }

    /** subtract balance when player starts a new round, doubles up and splits */
    public void subtractBalance(int balance) {
        this.balance -= balance;
    }

    /** set win multiplier for all rounds. */
    public void setWinMultiplier(List<Integer> list){
        this.winMultiplier = list;
    }

    /** set win multiplier for a specific round */
    public void setWinMultiplierOf(int index, int multi){
        this.winMultiplier.set(index, multi);
    }

    /** return win multiplier for a specific round */
    public int getWinMultiplierOf(int index){
        return this.winMultiplier.get(index);
    }
}
