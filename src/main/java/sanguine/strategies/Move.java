package sanguine.strategies;

import java.util.Objects;
import sanguine.model.Card;

/**
 * represents a possible move that can be played in a game of Sanguine.
 * Moves consist of the card to be played and the position on the board where the card should be
 * played.
 */
public class Move {
  private final Card card;
  private final int row;
  private final int col;

  /**
   * constructor for the move, assigns the given values to the fields.
   *
   * @param card the card to be played.
   * @param col  the 0-based index of the column on the grid where the card is to be played
   * @param row  the 0-based index of the row on the grid where the card is to be played
   */
  public Move(Card card, int col, int row) {
    if (card == null || row < 0 || col < 0) {
      throw new IllegalArgumentException("Invalid inputs");
    }
    this.card = card;
    this.row = row;
    this.col = col;
  }

  /**
   * Observer for the Card this move says to play.
   *
   * @return the card to be played
   */
  public Card getCard() {
    return this.card;
  }

  /**
   * Observer for the row this move says to play in.
   *
   * @return the row location to be played on
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Observer for the column this move says to play in.
   *
   * @return the column location to be played on
   */
  public int getCol() {
    return this.col;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Move)) {
      return false;
    }
    Move that = (Move) other;
    return this.card.equals(that.getCard()) && this.row == that.getRow()
        && this.col == that.getCol();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.card, this.row, this.col);
  }
}
