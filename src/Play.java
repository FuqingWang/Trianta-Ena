/*
 * Authors: Fuqing Wang (fuqing04 AT bu.edu), Hang Xu (xuh AT bu.edu)
 * Game: Console-based BlackJack with 2 different play modes
 * Date: Sept. 28, 2019
 */

import java.util.Scanner;

/**
 * @author Fuqing Wang, Hang Xu
 */
public class Play {
    private static final int SINGLE = 1;
    private static final int DOUBLE = 2;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Fuqing and Hang's Game Table, Please enter 1 for \"BlackJack\" or  2 for \"TriantaEna\"!");

        // prepare game
        int gameChoice = scanner.nextInt();
        if (gameChoice == 1) {
            BlackJack blackJack = new BlackJack();
            System.out.println("Please choose a play mode: (1 for single player, 2 for versus mode)");
            int mode = scanner.nextInt();
            while (mode != SINGLE && mode != DOUBLE) {
                System.out.println("Please enter either 1 or 2");
                mode = scanner.nextInt();
            }
            blackJack.PlayMode(mode);
        } else {
            TriantaEna triantaEna = new TriantaEna();
            triantaEna.playTriantaMode();
        }
    }
}
