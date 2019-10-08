import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fuqing Wang, Hang Xu
 * This is where the Trianta Ena logic sits
 */
public class TriantaEna extends Game {
    private static final int WINNING_COND = 31;
    /** init a new deck of Cards */
    private CardDeck deck = new CardDeck();
    private Scanner scan = new Scanner(System.in);

    public TriantaEna() {
        super(WINNING_COND);
    }

    public void playTriantaMode() {
        // while ...
        boolean play = true;
        while(play){
            // init number of players
            System.out.println("Enter the number of players");
            int numPlayer = scan.nextInt();

            List<Player> players = new ArrayList<>();

            // Create all players and add them in a list
            for(int i = 0; i < numPlayer; i++){
                String name = "Player " + (i+1);
                Player newPlayer = new Player(name, 100);
                newPlayer.setIsDealer(false);
                players.add(newPlayer);
            }
            // Trianta Ena needs two decks of Cards
            deck.prepareDecks(2);

            boolean ongoing = true;
            while (ongoing) {
                // Choose dealer and players based on personal choices or randomly select.
                rotateTriantaPlayers(players);

                // Init dealer
                Player banker = triantaGetDealer(players);
                banker.cleanHands();

                // dealer draws a card
                Card newCard = deck.drawCard();
                banker.addCard(newCard);
                System.out.println(banker.getName() + "(banker) shows " + newCard.toString());

                // player each draws a card (without bet)
                List<Player> newPlayers = new ArrayList<>();
                for (int i = 0; i < numPlayer; i++) {
                    Player player = players.get(i);
                    if (!player.getIsDealer()) {
                        player.cleanHands();
                        newCard = deck.drawCard();
                        player.cleanHands();
                        player.addCard(newCard);
                        System.out.println(player.getName() + " draws " + newCard.toString());
                        System.out.println("Do you want to 1.fold 2.keep playing");
                        int choice = scan.nextInt();
                        if (choice == 2) {
                            newPlayers.add(player);
                        }
                    }
                }

                // Player choose to play (put in bet)
                for (int i = 0; i < newPlayers.size(); i++) {
                    Player player = newPlayers.get(i);
                    List<List<Card>> newHands = new ArrayList<>();
                    newHands.add(player.getHand());
                    player.setHands(newHands);
                    System.out.println(player.getName() + " Select a bet amount");
                    int bet = scan.nextInt();

                    //@todo bet must not exceed dealer's balance
                    player.subtractBalance(bet);
                    player.setBet(bet);

                    while (player.calculateRank(0) < WINNING_COND) {
                        System.out.println(player.getName() + " now has " + player.getHand());
                        System.out.println("Do you want to 1 (hit) or 2 (stand)");
                        int choice = scan.nextInt();
                        if (choice == 1) {
                            newCard = deck.drawCard();
                            System.out.println(player.getName() + " draws a new card " + newCard);
                            player.addCard(newCard);
                        } else {
                            break;
                        }
                    }
                }

                // Banker's turn
                List<List<Card>> newHands = new ArrayList<>();
                newHands.add(banker.getHand());
                banker.setHands(newHands);
                if (newPlayers.size() > 0) {
                    while (banker.calculateRank(0) < WINNING_COND) {
                        System.out.println(banker.getName() + "(banker) now has " + banker.getHand());
                        System.out.println("Do you want to 1.hit 2.stand");
                        int choice = scan.nextInt();
                        if (choice == 1) {
                            newCard = deck.drawCard();
                            System.out.println("Banker draws a new card " + newCard);
                            banker.addCard(newCard);
                        }else{
                            break;
                        }
                    }
                    // check win
                    System.out.println();
                    for (int i = 0; i < newPlayers.size(); i++) {
                        Player player = newPlayers.get(i);
                        triantaEnaCompare(player, banker);
                    }
                    System.out.println();
                } else {
                    System.out.println("All players have chosen to fold this round.");
                }

                // summary
                triantaEnaSummary(players);

                System.out.println("Do you want to start a new round? 1 (Yes) or 2 (No)");
                int choice = scan.nextInt();
                if (choice != 1) {
                    ongoing = false;
                }
            }

            System.out.println("Do you want to start a new game? 1.Yes 2.No");
            int choice = scan.nextInt();
            if (choice != 1) {
                play = false;
                System.out.println("Bye Bye!");
            }
        }
    }


    /** compare hands between players in the game */
    private void triantaEnaCompare(Player player, Player dealer) {

        int playerTotal = player.calculateRank(0);
        int dealerTotal = dealer.calculateRank(0);

        System.out.println(player.getName() + "'s cards are: " + player.printHands() + ", total points "
                + playerTotal + ".");
        System.out.println(dealer.getName() + "'s(Banker) cards are: " + dealer.printHands() +
                ", total points " + dealerTotal + ".");

        // Find the winner!
        System.out.println(player.getName() + "'s bet is " + player.getBet());
        if (playerTotal <= WINNING_COND && dealerTotal <= WINNING_COND) {
            if (playerTotal < dealerTotal) {
                dealer.addBalance(player.getBet());
                System.out.println("Banker wins!");
            } else if (playerTotal > dealerTotal) {
                dealer.subtractBalance(player.getBet());
                // gain own and one more bet amount back
                player.addBalance(player.getBet() * 2);
                System.out.println("Player wins!");
            } else {
                // check natural blackjack
                if (playerTotal == WINNING_COND) {
                    if (player.getHand().size() == 3 && dealer.getHand().size() > 3) {
                        dealer.subtractBalance(player.getBet());
                        player.addBalance(player.getBet() * 2);
                        System.out.println("Player wins");
                    } else {
                        dealer.addBalance(player.getBet());
                        System.out.println("Banker wins");
                    }
                } else {
                    // if draw, the dealer wins
                    dealer.addBalance(player.getBet());
                    System.out.println("Banker wins");
                }
            }
        } else if (playerTotal <= WINNING_COND) {
            dealer.subtractBalance(player.getBet());
            player.addBalance(player.getBet() * 2);
            System.out.println("Player wins");
        } else {
            dealer.addBalance(player.getBet());
            System.out.println("Banker wins");
        }
    }

    /** display the summary of current game status */
    private void triantaEnaSummary(List<Player> players) {
        System.out.println(players.size());
        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println(player.getName() + "'s current balance is " + player.getBalance());
        }
    }

    /** choose which player to be the Banker, willingly or randomly */
    public void rotateTriantaPlayers(List<Player> players) {
        // Sort the players at an increasing balance (money left) order.
        Collections.sort(players);
        boolean hasDealer = false;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println(player.getName() + ", do you want to be the dealer?  1 (Yes) or 2 (No)");
            int choice = scan.nextInt();
            if(choice == 1){
                player.setIsDealer(true);
                hasDealer = true;
                break;
            }
        }
        // if no one wants to be the dealer, randomly select player and set him as dealer
        if (!hasDealer) {
            int d = (int) Math.floor(Math.random() * players.size());
            players.get(d).setIsDealer(true);
            System.out.println("No one wants to be the banker, " + players.get(d).getName() + " is randomly chosen as banker.");
        }
    }

    /** find which player is the dealer */
    public Player triantaGetDealer(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getIsDealer()){
                return players.get(i);
            }
        }
        return null;
    }
}
