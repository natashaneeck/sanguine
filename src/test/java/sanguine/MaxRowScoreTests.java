package sanguine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.mocks.CoordsCheckedLoggerMock;
import sanguine.mocks.MaxRowScoreMock;
import sanguine.model.Card;
import sanguine.model.MutableModelInterface;
import sanguine.model.SanguineModel;
import sanguine.strategies.MaxRowScoreStrategy;
import sanguine.strategies.Move;
import sanguine.strategies.Strategy;

/**
 * tests for the max row score strategy.
 */
public class MaxRowScoreTests {
  Strategy strat;


  /**
   * Sets up some example models for use, both at start and in progress with cards played.
   */
  @Before
  public void setUp() {
    this.strat = new MaxRowScoreStrategy();
  }

  @Test
  public void testStrategyChoosesExpectedCell() {
    MutableModelInterface model = new MaxRowScoreMock();
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    Move move = this.strat.getBestMove(model, Player.RED).getFirst();
    Move expected = new Move(exampleDeck.get(12), 0, 2);
    assertEquals(expected, move);
  }

  @Test
  public void testStrategyChoosesCorrectCell() {
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.startGame();
    // red plays crab on 2,0
    model.playCard(model.getHand(Player.RED).get(4), 2, 0);
    // blue plays security on 0, 4
    model.playCard(model.getHand(Player.BLUE).get(1), 0, 4);
    // red plays security on 0,0 --> red and blue tied on row 0
    model.playCard(model.getHand(Player.RED).get(0), 0, 0);
    Move move = this.strat.getBestMove(model, Player.RED).getFirst();
    // should be security on 0,3
    Move expected = new Move(exampleDeck.getFirst(), 3, 0);
    assertEquals(expected, move);
  }

  @Test
  public void testChecksInOrder() {
    Appendable ap = new StringBuilder();
    CoordsCheckedLoggerMock model = new CoordsCheckedLoggerMock(ap);

    this.strat.getBestMove(model, Player.RED);

    String expected = """
        Row: 0 Col: 0
        Row: 0 Col: 1
        Row: 0 Col: 2
        Row: 0 Col: 3
        Row: 0 Col: 4
        Row: 0 Col: 0
        Row: 0 Col: 1
        Row: 0 Col: 2
        Row: 0 Col: 3
        Row: 0 Col: 4
        Row: 1 Col: 0
        Row: 1 Col: 1
        Row: 1 Col: 2
        Row: 1 Col: 3
        Row: 1 Col: 4
        Row: 1 Col: 0
        Row: 1 Col: 1
        Row: 1 Col: 2
        Row: 1 Col: 3
        Row: 1 Col: 4
        Row: 2 Col: 0
        Row: 2 Col: 1
        Row: 2 Col: 2
        Row: 2 Col: 3
        Row: 2 Col: 4
        Row: 2 Col: 0
        Row: 2 Col: 1
        Row: 2 Col: 2
        Row: 2 Col: 3
        Row: 2 Col: 4
        """;
    assertEquals(expected, ap.toString());
  }

}


