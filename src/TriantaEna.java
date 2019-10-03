/*
 * Authors: Fuqing Wang (fuqing04 AT bu.edu), Hang Xu (xuh AT bu.edu)
 * Game: Console-based BlackJack with 2 different play modes
 * Date: Sept. 28, 2019
 */

import java.util.Scanner;

/**
 * @author Fuqing Wang, Hang Xu
 */
public class TriantaEna {
    /** player mode, currently, support two different modes */
    private static final int SINGLE = 1;
    private static final int DOUBLE = 2;

    public static void main(String[] args) {
        System.out.println("Welcome to Fuqing and Hang's BlackJack table!");

        // prepare game
        Game game = new Game();

        // choose play mode based on the user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose a play mode: (1 for single player, 2 for versus mode)");
        int mode = scan.nextInt();
        // For future game we could just comment this out. For now, since only two modes required, we keep the check
        while (mode != SINGLE && mode != DOUBLE) {
            System.out.println("Please enter either 1 or 2");
            mode = scan.nextInt();
        }
        game.PlayMode(mode);
    }
}
