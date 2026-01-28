package sanguine.model;

import java.util.List;

/**
 * Represents a card used to play Sanguine. Each card has a name, a cost in terms of pawns, a point
 * value, and a grid to represent its influence on positions on the game board. The influence of a
 * card allows a player to place more pawns on the board.
 */
public interface Card {

  /**
   * Returns the name of this card as a String.
   *
   * @return a string representing the name of this card.
   */
  String getName();

  /**
   * Returns the cost of this card in terms of pawns.
   *
   * @return an integer between 1 and 3 that represents the cost of this card.
   */
  int getCost();

  /**
   * Returns the score value of this card as a positive integer.
   *
   * @return the number of points this card is worth.
   */
  int getValue();

  /**
   * returns the influence grid of this class.
   *
   * @return a 2D array of the influence grid of this card.
   */
  List<List<InfluenceType>> getInfluence();
}
