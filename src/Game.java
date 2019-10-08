/**
 * @author Fuqing Wang, Hang Xu
 * This is where the overall Game logic sits
 */
public abstract class Game {
    /** every game should have a winning condition */
    private static int WINNING_COND = 0;

    public Game(int win) {
        WINNING_COND = win;
    }
}