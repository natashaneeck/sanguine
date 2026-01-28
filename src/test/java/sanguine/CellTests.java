package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Cell;
import sanguine.model.CellInterface;
import sanguine.model.PawnAmount;

/**
 * Tests for Cell and its public methods.
 */
public class CellTests {
  CellInterface empty;
  CellInterface redStart;
  CellInterface blueWithCard;

  /**
   * Sets up basic Cells for testing on, using both constructors. Includes three states of empty,
   * one pawn, and one with a card.
   */
  @Before
  public void setUp() {
    this.empty = new Cell();
    this.redStart = new Cell(Player.RED);

    this.blueWithCard = new Cell(Player.BLUE);
    this.blueWithCard.placeCard(new ConfigFileParser().parseDeck("docs" + File.separator
        + "example.deck").getFirst());
  }

  @Test
  public void testHasCardTrue() {
    assertFalse(this.empty.hasCard());
  }

  @Test
  public void testHasCardFalse() {
    assertTrue(this.blueWithCard.hasCard());
  }

  @Test
  public void testGetValueEmpty() {
    assertEquals(0, this.empty.getValue());
  }

  @Test
  public void testGetValueWithPawn() {
    assertEquals(0, this.redStart.getValue());
  }

  @Test
  public void testGetValueWithCard() {
    assertEquals(1, this.blueWithCard.getValue());
  }

  @Test
  public void testGetOwnerRed() {
    assertEquals(Player.RED, this.redStart.getOwner());
  }

  @Test
  public void testGetOwnerBlue() {
    assertEquals(Player.BLUE, this.blueWithCard.getOwner());
  }

  @Test
  public void testGetOwnerNobody() {
    assertNull(this.empty.getOwner());
  }

  @Test
  public void testGetPawnAmtNone() {
    assertEquals(PawnAmount.ZERO, this.empty.getPawnAmt());
  }

  @Test
  public void testGetPawnAmtStart() {
    assertEquals(PawnAmount.ONE, this.redStart.getPawnAmt());
  }

  @Test
  public void testGetPawnAmtWithCard() {
    assertEquals(PawnAmount.ZERO, this.blueWithCard.getPawnAmt());
  }


  @Test
  public void testInfluenceOnCellWithCard() {
    this.blueWithCard.influence(Player.RED);
    assertEquals(Player.BLUE, this.blueWithCard.getOwner());
    assertEquals(PawnAmount.ZERO, this.blueWithCard.getPawnAmt());
  }

  @Test
  public void testInfluenceOnEmptyCell() {
    this.empty.influence(Player.RED);
    assertEquals(Player.RED, this.empty.getOwner());
    assertEquals(PawnAmount.ONE, this.empty.getPawnAmt());

    this.empty.influence(Player.RED);
    assertEquals(PawnAmount.TWO, this.empty.getPawnAmt());

    this.empty.influence(Player.RED);
    assertEquals(PawnAmount.THREE, this.empty.getPawnAmt());

    this.empty.influence(Player.RED);
    assertEquals(PawnAmount.THREE, this.empty.getPawnAmt());
  }

  @Test
  public void testInfluenceChangingOwner() {
    assertEquals(Player.RED, this.redStart.getOwner());
    assertEquals(PawnAmount.ONE, this.redStart.getPawnAmt());

    this.redStart.influence(Player.BLUE);
    assertEquals(Player.BLUE, this.redStart.getOwner());
    assertEquals(PawnAmount.TWO, this.redStart.getPawnAmt());
  }

  @Test
  public void testPlaceCard() {
    assertEquals(Player.RED, this.redStart.getOwner());
    assertEquals(PawnAmount.ONE, this.redStart.getPawnAmt());
    assertFalse(this.redStart.hasCard());

    this.redStart.placeCard(new ConfigFileParser().parseDeck("docs" + File.separator
        + "example.deck").getFirst());
    assertEquals(Player.RED, this.redStart.getOwner());
    assertEquals(PawnAmount.ZERO, this.redStart.getPawnAmt());
    assertTrue(this.redStart.hasCard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInfluenceNullArg() {
    this.redStart.influence(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardNullArg() {
    this.redStart.placeCard(null);
  }
}
