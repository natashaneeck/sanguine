package sanguine.model;

import java.util.Objects;
import sanguine.Player;

/**
 * Represents the squares of the grids, which keep track of the player influencing them, the
 * number of pawns in it, and whether it has a card in it. Players can gain control of the cell
 * and add pawns to it with their cards.
 */
public class Cell implements CellInterface {
  private Player influencing;
  private PawnAmount amt;
  private Card card;

  /**
   * Represents the default state for most Cells in the game, where no Player has influenced it
   * or added pawns or Cards into it.
   */
  public Cell() {
    this.amt = PawnAmount.ZERO;
    this.card = null;
  }

  /**
   * constructor to create a copy of the given cell.
   *
   * @param cell the cell to be copied.
   */
  public Cell(CellInterface cell) throws IllegalArgumentException {
    if (cell == null) {
      throw new IllegalArgumentException("cell cannot be nul");
    }
    this.amt = cell.getPawnAmt();
    this.influencing = cell.getOwner();
    this.card = cell.getCard();
  }

  /**
   * Represents the default state for the Cells on the furthest left and right column, with one
   * pawn, no Cards, and a Player influencing it.
   *
   * @param influencing the Player who owns the pawns in this Cell
   * @throws IllegalArgumentException if null argument
   */
  public Cell(Player influencing) throws IllegalArgumentException {
    if (influencing == null) {
      throw new IllegalArgumentException("Cannot have null parameters");
    }
    this.influencing = influencing;
    this.amt = PawnAmount.ONE;
    this.card = null;
  }

  /**
   * Determines if there is a Card in this Cell, meaning it cannot change influence or get more
   * pawns added to it.
   *
   * @return if there is a Card in this Cell
   */
  public boolean hasCard() {
    return this.card != null;
  }

  /**
   * Represents the actions taken when a Player influences a Cell.
   * If there is not a Card there, the Cell is now owned by the given player.
   * If it is possible to increase the number of pawns by one, it does so.
   * If there is a Card in this Cell, this does nothing because the influence cannot be changed.
   *
   * @param adder the Player adding the pawns and/or changing the influence
   * @throws IllegalArgumentException if parameter is null
   */
  public void influence(Player adder) throws IllegalArgumentException {
    if (adder == null) {
      throw new IllegalArgumentException("Player may not be null");
    }
    if (this.hasCard()) {
      return;
    }
    this.influencing = adder;
    if (this.amt == PawnAmount.THREE) {
      return;
    }

    switch (this.amt) {
      case ZERO -> {
        this.amt = PawnAmount.ONE;
        return;
      }
      case ONE -> {
        this.amt = PawnAmount.TWO;
        return;
      }
      case TWO -> {
        this.amt = PawnAmount.THREE;
        return;
      }
      default -> {
        return;
      }
    }
  }


  /**
   * Gets the value (score) of this Cell, which is the value of the Card within it, or 0 if there
   * is no Card.
   *
   * @return the score this Cell adds
   */
  @Override
  public int getValue() {
    if (this.hasCard()) {
      return this.card.getValue();
    } else {
      return 0;
    }
  }

  /**
   * Adds the given Card to this Cell and sets pawn amount to zero.
   *
   * @param card the Card to be added
   * @throws IllegalArgumentException if Card is null
   */
  @Override
  public void placeCard(Card card) throws IllegalArgumentException {
    if (card == null) {
      throw new IllegalArgumentException("Card must not be null");
    }
    this.card = card;
    this.amt = PawnAmount.ZERO;
  }

  @Override
  public Player getOwner() {
    return this.influencing;
  }

  /**
   * Gets the number of pawns in this Cell.
   *
   * @return the PawnAmount the Cell has
   */
  @Override
  public PawnAmount getPawnAmt() {
    return this.amt;
  }

  @Override
  public Card getCard() {
    return this.card;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof CellInterface)) {
      return false;
    }
    CellInterface that = (CellInterface) other;
    return this.influencing == that.getOwner() && this.amt == that.getPawnAmt()
        && this.hashCode() == that.hashCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.influencing, this.amt, this.card);
  }
}