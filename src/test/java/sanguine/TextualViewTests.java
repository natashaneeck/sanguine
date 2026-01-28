package sanguine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.mocks.InProgressBoardMock;
import sanguine.mocks.StartingBoardMock;
import sanguine.model.Card;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.model.SanguineModel;
import sanguine.view.SanguineTextualView;
import sanguine.view.TextualView;

/**
 * Tests for the public methods in TextualView using SanguineTextualView.
 */
public class TextualViewTests {
  TextualView startView;
  TextualView progressView;
  TextualView realView;

  /**
   * Sets up some examples for use in tests using model mocks and a real model implementation.
   */
  @Before
  public void setUpConstruction() {
    ReadOnlyModelInterface startBoardModel = new StartingBoardMock();
    this.startView = new SanguineTextualView(startBoardModel);

    ReadOnlyModelInterface inProgressModel = new InProgressBoardMock();
    this.progressView = new SanguineTextualView(inProgressModel);

    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");

    ReadOnlyModelInterface realModel = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    ((SanguineModel) realModel).startGame();
    this.realView = new SanguineTextualView(realModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullArg() {
    TextualView badView = new SanguineTextualView(null);
  }

  @Test
  public void testRenderStartingBoard() {
    String expectedBoard = "0 || 1 _ _ _ 1 || 0" + System.lineSeparator()
        + "0 || 1 _ _ _ 1 || 0" + System.lineSeparator()
        + "0 || 1 _ _ _ 1 || 0" + System.lineSeparator();
    assertEquals(expectedBoard, this.startView.renderBoard());
  }

  @Test
  public void testRenderMockGame() {
    String expectedBoard = "3 || 1 _ _ _ 1 || 5" + System.lineSeparator()
        + "3 || 1 _ 1 1 1 || 5" + System.lineSeparator()
        + "3 || 1 B _ _ 1 || 5" + System.lineSeparator();
    assertEquals(expectedBoard, this.progressView.renderBoard());
  }

  @Test
  public void testRenderRealGame() {
    String expectedBoard = "0 || 1 _ _ _ 1 || 0" + System.lineSeparator()
        + "0 || 1 _ _ _ 1 || 0" + System.lineSeparator()
        + "0 || 1 _ _ _ 1 || 0" + System.lineSeparator();
    assertEquals(expectedBoard, this.realView.renderBoard());
  }
}
