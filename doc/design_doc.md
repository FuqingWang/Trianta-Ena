# Triana Ena Design Doc

Author: Fuqing Wang (fuqing04@bu.edu), Hang Xu (xuh@bu.edu)

## Class Card

* The purpose of this class is to provide an abstraction of Card. A Card has three attributes: Rank, Suit, and number.
* The benefit of this class is its encapsulation and abstraction provides a simpler way for us to develop the game. Since a Card is the fundamental element of a BlackJack game and a single variable cannot represent a Card well.
* The benefit of this class to the future design is given the abstraction, we can easily change the components of a Card. For example, if “J” no longer has a value of 10 but 21, we just need to change only in the Card class to make it effective.

## Class CardDeck

* The purpose of this class is to provide an abstraction of having a deck of cards. It contains the initialization of Cards and Card Shuffling.
* The benefit of this class is that it provides a way to initialize a deck of cards and shuffle the deck using a card shuffling algorithm. Another benefit is that since it groups the related methods together into one single class, it makes it easier to maintain the entire code base.
* It benefits the future design since if we change to another shuffling algorithm, we can just change the method within this class and everywhere else would be effective.


## Class Player

* The purpose of this class is to extend the abstract class Player so that we can instantiate a Player instance through the Human Player implementation
* The benefit of this class is that it can inherit all methods defined in the abstract class and we can make the HumanPlayer’s own methods in this class
* It benefits to any future game in the sense that if the rules or constraints changed on the dealer, we can only modify this class. Or we could just add methods in the abstract class so that we don’t have to repeat ourselves.

## Abstract Class GameParticipant

* The purpose of this class is to provide an abstraction of what a Player could do in our game. It contains the most commonly used methods by different kinds of players in the game.
* The benefit of this class is that even though we have different players, we don’t have to write the same code for every single type of player. We can put the common methods in this class. And the other classes can inherit from this class.
* It benefits the future game is that we only need to change this class to add/delete users’ common action. It uses the DRY (do not repeat yourself) principle in OOD.

## Abstract Class Game

* The purpose of this class is to provide a simple blueprint for all different Games that we might need to support in the future. It abstracts the common characteristics of a game in our application.
* The benefit of this class is that we don’t have to rewrite the common features of different games in our applications. Since the root of our application is to support card games. We don’t have to repeat ourselves in writing the common classes for every card game.
* It benefits the future games in a way that we can reuse the common methods and save some time on implementing the game logic.

## Class Suit

* The purpose of this class is to provide an abstraction of Suits in a Card game.
* The benefit of this class is that since we only have a certain number of suits in one type of Card, It can be used by a Card class and it is easier to modify in the future.
* It benefits the future game since if we use a different type of Card, and it has more than 4 suits, we can just change the Suit class and don’t have to change other code. It provides simplicity to future modification.

## Class TrianaEna

* The purpose of this class is to contain the Trianta Ena game logic and related methods in one place
* The benefit of this class to our existing design is to have a place to contain the game logic. Also, from this class, we can instantiate  other classes
* The benefit of this to a future game is, given the abstraction, we only need to change the game logic if there is a change.

## Class BlackJack

* The purpose of this class is to contain the BlackJack game logic and related methods in one place
* The benefit of this class to our existing design is to have a place to contain the game logic. Also, from this class, we can instantiate  other classes
* The benefit of this to a future game is, given the abstraction, we only need to change the game logic if there is a change.

## Class Play
* The purpose of this class is to provide an entry point for the game. From this class, a user can play 2 different types of games: BlackJack and Trianta Ena
* The benefit of this class is that it is easy to see which other class called and can be modified easily.
* If we want to add more features to the game, we can change the entry point.

## Questions

* If there was a change to the number of players playing the game, could your program scale with minimal changes?
  * Let’s say there are three players, in our BlackJack.java, it sends the number of players to the Game class which has different game logic depends on how many players in the game. Therefore, the change required to support more than 2 players is very minimal in our design.
* How much of your existing object design will you be able to use in a different card game?
  * In a different card game, there might be a different number of Suits, so we could just change the enum values in the Suit class and everything else would remain the same. For the rank of the Card, it can just be changed in the Card class either add or delete. Therefore, we are able to reuse of all of Card related classes for the change.
* Could you use your existing classes without requiring major (or minor) changes?
  * In our design, we abstract the common characteristics about the BlackJack game. With that being said, we can use all the existing classes.
* Would ‘applications’ that use your class to play the game of Blackjack be impacted if you made changes to your implementation?
  * No, because of encapsulation. The fields of the classes are private and only contains necessary public classes that allows applications to do some GET. That is saying, the application that use our classes the game would affect the state and implementation of the entire game.

