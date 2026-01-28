package sanguine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import player.HumanPlayer;
import player.PlayerInterface;

/**
 * Tests for the HumanPlayer.
 */
public class HumanPlayerTests {
  @Test
  public void testValidConstructor() {
    PlayerInterface player = new HumanPlayer(Player.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstruction() {
    PlayerInterface player = new HumanPlayer(null);
  }

  @Test
  public void testColorGetter() {
    PlayerInterface player = new HumanPlayer(Player.RED);
    assertEquals(Player.RED, player.getColor());

    player = new HumanPlayer(Player.BLUE);
    assertEquals(Player.BLUE, player.getColor());
  }
}
