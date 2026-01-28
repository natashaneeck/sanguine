package sanguine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.strategies.Move;

/**
 * Tests for Move and its public methods.
 */
public class MoveTests {
  Move valid;

  /**
   * Sets up a valid Move to be tested on.
   */
  @Before
  public void setUp() {
    Card sanguine = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example.deck").getFirst();
    this.valid = new Move(sanguine, 2, 3);
  }

  @Test
  public void testGetCard() {
    Card sanguine = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example.deck").getFirst();
    assertEquals(sanguine, this.valid.getCard());
  }

  @Test
  public void testGetRow() {
    assertEquals(3, this.valid.getRow());
  }

  @Test
  public void testGetCol() {
    assertEquals(2, this.valid.getCol());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCard() {
    Move bad = new Move(null, 1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowRow() {
    Card sanguine = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example.deck").getFirst();
    Move bad = new Move(sanguine, -3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowCol() {
    Card sanguine = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example.deck").getFirst();
    Move bad = new Move(sanguine, 2, -2);
  }
}
