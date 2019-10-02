import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Fuqing Wang, Hang Xu
 * This is where the game logic sits
 */
public class Game {

    /** if it is no longer 21, we can change here */
    private static final int TWENTY_ONE = 21;
    /** init a new deck of Cards */
    private CardDeck deck = new CardDeck();
    private Scanner scan = new Scanner(System.in);

    /** Give player(s) first two cards to the Hand */
    public void initPlayer(Player player, int betAmount){
        player.cleanHands();
        player.subtractBalance(betAmount);
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        player.initHands();

        // check if player's cards are split-able
        // if the player has enough money, the player might be able to split the hand
        if (player.getBalance() - betAmount >= 0) {
            player.setHands(split(player, player.getHand(), betAmount).getHands());
        } else {
            player.initHands();
        }
        List<Integer> list = new ArrayList<>(player.getHands().size());
        while(list.size() < player.getHands().size()){
            list.add(0);
        }
        player.setWinMultiplier(list);
    }

    /** Give the dealer first two cards to the Hand */
    public void initDealer(Dealer dealer){
        dealer.cleanHands();
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        dealer.initHands();
    }

    /** Blackjack dummy dealer AI */
    public void blackJackDealersTurn_AI(Player player, Dealer dealer) {
        // dealer's turn
        int playerMaxPoints = 0;
        for (int index = 0; index < player.getHands().size(); index++) {
            if(player.calculateRank(index) <= 21){
                playerMaxPoints = player.calculateRank(index);
            }
        }
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                ", total points " + dealer.calculateRank(0) + ".");
        while (dealer.calculateRank(0) <= playerMaxPoints) {
            Card newCard = deck.drawCard();
            dealer.addCard(newCard);
            System.out.println(" ...... Dealer chooses to hit  ......");
            System.out.println("Dealer draws a new card " + newCard.toString());
            System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                    ", total points " + dealer.calculateRank(0) + ".");
        }
        System.out.println(" ...... Dealer chooses to stand  ......");
    }

    /** Blackjack - game logic for dealer's turn */
    public void blackJackDealersTurn_Human(Player player, Dealer dealer) {
        // dealer's turn
        int playerMaxPoints = 0;
        for (int index = 0; index < player.getHands().size(); index++) {
            if(player.calculateRank(index) <= TWENTY_ONE){
                playerMaxPoints = player.calculateRank(index);
            }
        }

        while (dealer.calculateRank(0) <= TWENTY_ONE) {
            List<Card> hand = dealer.getHands().get(0);
            dealer.setHand(hand);

            System.out.println("Player's largest hand has total points: " + playerMaxPoints);
            System.out.println("Dealer has: " + dealer.printHand(0) +
                    ", total points " + dealer.calculateRank(0) + ".");
            System.out.println("Do you want to 1 (hit) or  2 (stand)");
            int choice = scan.nextInt();
            // the player chooses "hit"
            if (choice == 1) {
                Card newCard = deck.drawCard();
                dealer.addCard(newCard);
                System.out.println("The new card is: " + newCard.toString() + ".");
            // the player chooses "stand"
            } else {
                System.out.println("Dealer has: " + dealer.printHand(0) +
                        ", total points " + dealer.calculateRank(0) + ".");
                break;
            }
        }
    }

    /** Blackjack - game logic for player's turn */
    public void blackJackPlayersTurn(Player player, int betAmount) {
        // player's turn
        for (int index = 0; index < player.getHands().size(); index++) {
            while (player.calculateRank(index) <= TWENTY_ONE) {
                List<Card> hand = player.getHands().get(index);
                // set the hand to the current hand of the player's
                player.setHand(hand);

                System.out.println("You have: [" + player.printHand(index) + "], total points " +
                        player.calculateRank(index) + ".");
                System.out.println("Do you want to 1(hit), 2 (stand), 3 (double up): ");
                int choice = scan.nextInt();
                if (choice == 1) {
                    Card newCard = deck.drawCard();
                    // at this point, the player can win at most 2 times the bet amount in this game
                    player.setWinMultiplierOf(index, 2);
                    // Add the new card to the current hand
                    player.addCard(newCard);
                    System.out.println("The new card is: " + newCard.toString() + ".");
                } else if (choice == 2) {
                    player.setWinMultiplierOf(index, 2);
                    break;
                } else if (choice == 3) {
                    // at this point, the player can win at most 4 times the best amount since "double-up"
                    player.setWinMultiplierOf(index, 4);
                    if (player.getBalance() - betAmount >= 0) {
                        player.subtractBalance(betAmount);
                        Card newCard = deck.drawCard();
                        player.addCard(newCard);
                        System.out.println("The new card is: " + newCard.toString() + ".");
                        System.out.println("Your cards are now: " + player.printHand(index) + ", total points " +
                                player.calculateRank(index) + ".");
                        break;
                    } else {
                        System.out.println("You don't have enough money, please try other options!");
                    }
                } else {
                    System.out.println("Invalid choice! Please 1 (hit), 2 (stand), 3 (double up).");
                }
            }
        }
    }

    /** Blackjack - compare player's each hand of cards with dealer's cards, and add/subtract balance accordingly*/
    public void blackJackCompare(Player player, Dealer dealer, int currBet) {
        for (int index = 0; index < player.getHands().size(); index++) {
            System.out.println("Player's cards are: " + player.printHand(index) + ", total points "
                    + player.calculateRank(index) + ".");
            System.out.println("Dealer's cards are: " + dealer.printHand(0) +
                    ", total points " + dealer.calculateRank(0) + ".");
            // Find the winner!
            int playerTotal = player.calculateRank(index);
            int dealerTotal = dealer.calculateRank(0);

            if (playerTotal <= TWENTY_ONE && dealerTotal <= TWENTY_ONE) {
                if (playerTotal < dealerTotal) {
                    System.out.println("Dealer wins!");
                } else if (playerTotal > dealerTotal) {
                    System.out.println("Player wins!");
                    player.addBalance(currBet * player.getWinMultiplierOf(index));
                } else {
                    // check natural blackjack
                    if (playerTotal == TWENTY_ONE) {
                        if (player.getHand().size() == 2 && dealer.getHand().size() > 2) {
                            player.addBalance(currBet * player.getWinMultiplierOf(index));
                            System.out.println("Player wins");
                        } else {
                            System.out.println("Dealer wins");
                        }
                    } else {
                        // if draw, the dealer wins
                        System.out.println("Dealer wins");
                    }
                }
            } else if (playerTotal <= TWENTY_ONE) {
                player.addBalance(currBet * player.getWinMultiplierOf(index));
                System.out.println("Player wins");
            } else if (dealerTotal <= TWENTY_ONE) {
                System.out.println("Dealer wins");
            } else {
                player.addBalance(currBet * player.getWinMultiplierOf(index)/2);
                System.out.println("The game tied");
            }
        }
    }

    /** Blackjack - play the game based on different modes */
    public void PlayMode(int mode) {
        // only two mode supported for now. Could comment this out if we need to support more.
        if (mode == 1) {
            System.out.println("Single Player Mode");
        } else {
            System.out.println("Double Player Mode");
        }
        Player player = new Player("Player", 100);
        Dealer dealer = new Dealer("Dealer", Integer.MAX_VALUE);

        boolean play = true;

        while (player.getBalance() >= 0 && play) {
            // set budget, shuffle card, etc
            deck.prepareDeck();
            System.out.println("Your current balance is: " + player.getBalance() +
                    ". Please choose a bet amount for this round: (for example, enter 5)");
            int betAmount = scan.nextInt();
            while (player.getBalance() - betAmount < 0) {
                System.out.println("You don't have enough balance for this bet. Choose a different amount.");
                betAmount = scan.nextInt();
            }

            // game starts!
            initDealer(dealer);
            System.out.println("Dealer shows: " + dealer.getHand().get(0));

            initPlayer(player, betAmount);
            System.out.println("Player's card is " + player.printHands());

            System.out.println("It is Player's Turn!");
            // game proceeds, player can choose to 1.hit, 2.stand or 3.double up for each hand of cards
            blackJackPlayersTurn(player, betAmount);

            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("It is now dealer's Turn!");
            if (mode == 1) {
                blackJackDealersTurn_AI(player, dealer);
            } else if(mode == 2) {
                blackJackDealersTurn_Human(player, dealer);
            }

            // game proceeds, compare cards and add/subtract balance
            blackJackCompare(player, dealer, betAmount);

            // game summary
            System.out.println(player.getName() + "'s current balance is: " + player.getBalance() + ".");
            if (player.getBalance() > 0) {
                System.out.println("Do you want to play again? 1 (Yes) or 2 (No)");
                int input = scan.nextInt();
                if (input != 1){
                    System.out.println("Player has cashed out.");
                    break;
                }
            } else {
                play = false;
                System.out.println("Player has lost all money. Game exits!");
            }
        }
    }

    /** Blackjack - run a recursion to get all possible split pairs */
    private Player split(Player player, List<Card> hand, int betPerGame) {
        Card card1 = hand.get(0);
        Card card2 = hand.get(1);

        if (card1.getFace().equals(card2.getFace()) && player.getBalance() - betPerGame >= 0) {
            System.out.println("Your hands are: " + player.printHands());
            System.out.println("Your current hand is: " + player.printHand());
            Scanner scan = new Scanner(System.in);
            System.out.println("Do you want to split? " + player.printHand() + " (type 1 for Yes or 2 for No) ");
            int isSplit = scan.nextInt();
            if (isSplit == 1) {
                // bet the same amount of money to the new hand
                player.subtractBalance(betPerGame);

                List<Card> hand1 = new ArrayList<>();
                hand1.add(card1);
                Card newCard = deck.drawCard();
                hand1.add(newCard);
                System.out.println("You drew a new card: " + newCard.toString());
                player.setHand(hand1);
                System.out.println("Current hand is: " + player.printHand());
                // recursively called split to check whether there is a chance to split
                List<List<Card>> newHands = new ArrayList<>(split(player, hand1, betPerGame).getHands());

                List<Card> hand2 = new ArrayList<>();
                hand2.add(card2);
                newCard = deck.drawCard();
                System.out.println("You drew a new card: " + newCard.toString());
                hand2.add(newCard);
                player.setHand(hand2);
                System.out.println("Current hand is: " + player.printHand());
                newHands.addAll(split(player, hand2, betPerGame).getHands());

                player.setHands(newHands);
                return player;
            }
        }

        List<List<Card>> newHands = new ArrayList<>();
        newHands.add(hand);
        player.setHand(hand);
        player.setHands(newHands);
        return player;
    }
}
