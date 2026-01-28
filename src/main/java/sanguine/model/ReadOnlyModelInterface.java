package sanguine.model;

import java.util.List;
import sanguine.Player;

/**
 * Represents the model of the Sanguine game, and rule-checking features.
 * Does not contain any methods that change the state of the game, and any fields returned like a
 * board grid should be returned as copies.
 * The board is represented as a 2D array of CellInterface's, and the hand of a player is
 * represented as a list of cards.
 * There are two players, and their total scores are based on comparing their row scores.
 */
public interface ReadOnlyModelInterface {

  /**
   * Gets a list of the Cards playable by the given Player.
   *
   * @param player the user to get the hand of
   * @return the list of accessible Cards
   * @throws IllegalArgumentException if Player is null
   * @throws IllegalStateException if game not started
   */
  public List<Card> getHand(Player player) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the total score of the given Player when the game is over.
   * The player with the higher row-score adds their row-score to their total score.
   * The player with the lower row-score gains zero points for their total score.
   * If the row-scores are the same for both players, neither player gains points for that row.
   *
   * @param player the Player whose score to count
   * @return the player's score
   * @throws IllegalArgumentException if Player is null
   * @throws IllegalStateException if game not started
   */
  public int getTotalScore(Player player) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the Player's score in a specified row. Score is the sum of the
   * Player's Card values.
   *
   * @param player the Player whose score to count
   * @param row    the row to count the score in
   * @return the player's score
   * @throws IllegalArgumentException if Player is null or row index is invalid
   * @throws IllegalStateException if game not started
   */
  public int getRowScore(Player player, int row) throws IllegalArgumentException,
      IllegalStateException;

  /**
   * Determines if the game has been completed, which means both Players pass.
   *
   * @return true if game over, false otherwise
   * @throws IllegalStateException if game not started
   */
  public boolean isGameOver() throws IllegalStateException;

  /**
   * Determines the winner of the game.
   *
   * @return a Player if there is a winner, or null if tied.
   * @throws IllegalStateException if game is not over or hasn't started
   */
  public Player getWinner();

  /**
   * Gets the current state of the game as a shallow copy.
   *
   * @return the board game
   */
  public CellInterface[][] getBoard();

  /**
   * Determines the number of rows on the game board.
   *
   * @return the number of rows
   */
  public int getNumRows();

  /**
   * Determines the number of columns on the game board.
   *
   * @return the number of columns
   */
  public int getNumCols();

  /**
   * Determines if the given card can be placed in the specified cell.
   *
   * @param card the card to be placed.
   * @param row  the 0-based index of the row at which to place the card.
   * @param col  the 0-based index of the column at which to place the card.
   * @return true if the move is possible, false otherwise
   * @throws IllegalStateException if game not started
   */
  public boolean isValidMove(Card card, int row, int col) throws IllegalStateException;

  /**
   * returns the player whose turn it is.
   *
   * @return the player whose turn it is.
   * @throws IllegalStateException if game not started
   */
  public Player getPlayer() throws IllegalStateException;
}
