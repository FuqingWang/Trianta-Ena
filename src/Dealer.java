/**
 * @author Fuqing Wang, Hang Xu
 */
public class Dealer extends AbstractPlayer {

    public Dealer(String name, int balance) {
        super(name, balance, true);
    }

    public Dealer(String name, int balance, boolean isDealer) {
        super(name, balance, isDealer);
    }

}
