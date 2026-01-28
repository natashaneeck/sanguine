package sanguine.model;

import sanguine.Player;

/**
 * Represents the Cell in the board's grid, which can contain nothing, a Card, or 1-3 pawns.
 * One Player owns the influence in this Cell, or nothing owns it if no pawns or Cards.
 */
public interface CellInterface {

  /**
   * Determines if there is a Card in this Cell, meaning it cannot change influence or get more
   * pawns added to it.
   *
   * @return if there is a Card in this Cell
   */
  public boolean hasCard();

  /**
   * Represents the actions taken when a Player influences a Cell.
   * If there is not a Card there, the Cell is now owned by the given player.
   * If it is possible to increase the number of pawns by one, it does so.
   * If there is a Card in this Cell, this does nothing because the influence cannot be changed.
   *
   * @param adder the Player adding the pawns and/or changing the influence
   * @throws IllegalArgumentException if parameter is null
   */
  public void influence(Player adder) throws IllegalArgumentException;

  /**
   * Gets the value (score) of this Cell, which is the value of the Card within it, or 0 if there
   * is no Card.
   *
   * @return the score this Cell adds
   */
  public int getValue();

  /**
   * Adds the given Card to this Cell.
   *
   * @param card the Card to be added
   * @throws IllegalArgumentException if Card is null
   */
  public void placeCard(Card card) throws IllegalArgumentException;

  /**
   * Returns the Player influencing this Cell, whether owning the Card inside or the pawns.
   *
   * @return the Player influencing this Cell, or null if none.
   */
  public Player getOwner();

  /**
   * Gets the number of pawns in this Cell.
   *
   * @return the PawnAmount the Cell has
   */
  public PawnAmount getPawnAmt();

  /**
   * Gets the card at this cell.
   *
   * @return the card at this cell.
   */
  public Card getCard();
}
