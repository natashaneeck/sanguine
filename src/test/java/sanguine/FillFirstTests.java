package sanguine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.mocks.CoordsCheckedLoggerMock;
import sanguine.model.Card;
import sanguine.model.MutableModelInterface;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.model.SanguineModel;
import sanguine.strategies.FillFirstStrategy;
import sanguine.strategies.Move;
import sanguine.strategies.Strategy;

/**
 * Tests for the Fill first strategy, unittests and integration tests.
 */
public class FillFirstTests {
  Strategy fillFirst = new FillFirstStrategy();

  @Test
  public void testFirstCheckPasses() {
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    ReadOnlyModelInterface model = new SanguineModel(2, 3,
        exampleDeck, exampleDeck, 3, false);
    ((SanguineModel) model).startGame();
    Move expected = new Move(exampleDeck.getFirst(), 0, 0);
    assertEquals(expected, this.fillFirst.getBestMove(model, model.getPlayer()).getFirst());
  }

  @Test
  public void testChecksMultCells() {
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.startGame();
    //play Queen (2) in 0,0
    model.playCard(exampleDeck.get(2), 0, 0);
    model.pass();

    //should be Security with 0, 1
    Move expected = new Move(exampleDeck.getFirst(), 0, 1);
    Move actual = this.fillFirst.getBestMove(model, Player.RED).getFirst();
    assertEquals(expected, actual);
  }

  @Test
  public void testChecksInOrder() {
    Appendable ap = new StringBuilder();
    CoordsCheckedLoggerMock model = new CoordsCheckedLoggerMock(ap);

    this.fillFirst.getBestMove(model, Player.RED);

    String expected = """
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
        Row: 2 Col: 0
        Row: 2 Col: 1
        Row: 2 Col: 2
        Row: 2 Col: 3
        Row: 2 Col: 4
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
        Row: 2 Col: 0
        Row: 2 Col: 1
        Row: 2 Col: 2
        Row: 2 Col: 3
        Row: 2 Col: 4
        """;
    assertEquals(expected, ap.toString());
  }
}
