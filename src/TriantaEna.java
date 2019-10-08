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
        System.out.println("Welcome to Fuqing and Hang's Trianta Ena table!");

        // prepare game
        Game game = new Game();

        game.PlayTriantaMode();
    }
}
