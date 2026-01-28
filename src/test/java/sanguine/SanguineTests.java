package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.CellInterface;
import sanguine.model.MutableModelInterface;
import sanguine.model.PawnAmount;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.model.SanguineModel;

/**
 * Tests for the public methods in SanguineModel.
 */
public class SanguineTests {
  MutableModelInterface startModel;
  MutableModelInterface inProgressModel;
  List<Card> exampleDeck;

  /**
   * Sets up some example models for use, both at start and in progress with cards played.
   */
  @Before
  public void setUp() {
    this.exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    this.startModel = new SanguineModel(2, 3,
        exampleDeck, exampleDeck, 3, false);
    this.startModel.startGame();

    this.inProgressModel = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    this.inProgressModel.startGame();
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.RED).get(4), 2, 0);
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(1), 0, 4);
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.RED).get(2), 0, 0);
  }

  @Test
  public void testGetNumRows() {
    assertEquals(3, this.inProgressModel.getNumRows());
  }

  @Test
  public void testGetNumCols() {
    assertEquals(5, this.inProgressModel.getNumCols());
  }

  @Test
  public void testGetPlayerRed() {
    assertEquals(Player.RED, this.startModel.getPlayer());
  }

  @Test
  public void testGetPlayerBlue() {
    this.startModel.pass();
    assertEquals(Player.BLUE, this.startModel.getPlayer());
  }

  @Test
  public void testGetHandCorrectProperties() {
    assertEquals(3, this.startModel.getHand(Player.RED).size());
    assertEquals(this.startModel.getHand(Player.RED), this.startModel.getHand(Player.BLUE));

    assertEquals(5, this.inProgressModel.getHand(Player.RED).size());
    assertNotEquals(this.inProgressModel.getHand(Player.RED),
        this.inProgressModel.getHand(Player.BLUE));
  }

  @Test
  public void testGetTotalScore() {
    assertEquals(0, this.startModel.getTotalScore(Player.RED));
    assertEquals(0, this.startModel.getTotalScore(Player.BLUE));
    assertEquals(1, this.inProgressModel.getTotalScore(Player.RED));
    assertEquals(0, this.inProgressModel.getTotalScore(Player.BLUE));
  }

  @Test
  public void testGetRowScore() {
    assertEquals(1, this.inProgressModel.getRowScore(Player.RED, 0));
    assertEquals(1, this.inProgressModel.getRowScore(Player.BLUE, 0));
    assertEquals(0, this.inProgressModel.getRowScore(Player.RED, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerTotalScore() {
    this.startModel.getTotalScore(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerRowScore() {
    this.startModel.getRowScore(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooLowRowGetRowScore() {
    this.startModel.getRowScore(Player.RED, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooHighRowGetRowScore() {
    this.startModel.getRowScore(Player.RED, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerGetHand() {
    this.startModel.getHand(null);
  }

  @Test
  public void testIsValidMoveOnInvalidRow() {
    assertFalse(this.inProgressModel.isValidMove(this.inProgressModel.getHand(Player.RED).get(4),
        -1, 0));
  }

  @Test
  public void testIsValidMoveOnInvalidColumn() {
    assertFalse(this.inProgressModel.isValidMove(this.inProgressModel.getHand(Player.RED).get(4),
        0, -1));
  }

  @Test
  public void testIsValidMoveOnNullCard() {
    assertFalse(this.inProgressModel.isValidMove(null, 0, 0));
  }

  @Test
  public void testIsValidMoveOnFullCell() {
    assertFalse(this.inProgressModel.isValidMove(this.inProgressModel.getHand(Player.BLUE).get(0),
        0, 4));
  }

  @Test
  public void testIsValidMoveOnOtherPlayerCell() {
    assertFalse(this.inProgressModel.isValidMove(this.startModel.getHand(Player.RED).get(0),
        1, 2));
  }

  @Test
  public void testIsValidMoveOnNotEnoughPawns() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator + "example.deck");
    assertFalse(this.inProgressModel.isValidMove(deck.get(14), 1, 4));
  }

  @Test
  public void testIsValidMoveOnValidMove() {
    assertTrue(this.startModel.isValidMove(this.startModel.getHand(Player.RED).getFirst(),
        0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardInvalidRow() {
    this.startModel.playCard(this.inProgressModel.getHand(Player.RED).get(4), -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardRowOutOfRange() {
    this.startModel.playCard(this.inProgressModel.getHand(Player.RED).get(4), 7, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardInvalidCol() {
    this.startModel.playCard(this.inProgressModel.getHand(Player.RED).get(4), 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardColOutOfRange() {
    this.startModel.playCard(this.inProgressModel.getHand(Player.RED).get(4), 0, 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayCardNullCard() {
    this.startModel.playCard(null, 0, 9);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardOnCellWithCard() {
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(0), 0, 4);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardOnOtherPlayerCell() {
    this.startModel.playCard(this.startModel.getHand(Player.RED).get(0), 1, 2);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardNotEnoughPawns() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator + "example.deck");
    this.inProgressModel.playCard(deck.get(14), 1, 4);
  }

  @Test
  public void testPlayCardValid() {
    List<Card> beforeHand = new ArrayList<>(startModel.getHand(Player.RED));
    this.startModel.playCard(this.startModel.getHand(Player.RED).get(0), 0, 0);
    CellInterface[][] grid = this.startModel.getBoard();
    CellInterface cell = grid[0][0];
    assertTrue(cell.hasCard());
    assertNotEquals(beforeHand, this.startModel.getHand(Player.RED));
  }

  @Test
  public void testPlayCardUpdatesInfluence() {
    CellInterface[][] grid = this.inProgressModel.getBoard();
    CellInterface cell = grid[2][1];
    assertEquals(1, cell.getPawnAmt().getNumPawns());
  }

  @Test
  public void testInfluenceAddsPawns() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator + "example.deck");
    this.inProgressModel.pass();
    CellInterface[][] gridBefore = this.inProgressModel.getBoard();
    CellInterface cellBefore = gridBefore[1][0];
    assertEquals(2, cellBefore.getPawnAmt().getNumPawns());
    this.inProgressModel.playCard(deck.get(6), 2, 1);
    CellInterface[][] grid = this.inProgressModel.getBoard();
    CellInterface cell = grid[1][0];
    assertEquals(3, cell.getPawnAmt().getNumPawns());
  }

  @Test
  public void testInfluenceWithThreePawnsInCell() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator + "example.deck");
    this.inProgressModel.pass();
    this.inProgressModel.playCard(deck.get(6), 2, 1);
    CellInterface[][] gridBefore = this.inProgressModel.getBoard();
    CellInterface cellBefore = gridBefore[1][0];
    assertEquals(3, cellBefore.getPawnAmt().getNumPawns());
    this.inProgressModel.pass();
    this.inProgressModel.playCard(deck.get(0), 1, 1);
    CellInterface[][] grid = this.inProgressModel.getBoard();
    CellInterface cell = grid[1][0];
    assertEquals(3, cell.getPawnAmt().getNumPawns());
  }

  @Test
  public void testInfluenceUpdatesOwnership() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator + "example.deck");
    this.inProgressModel.pass();
    CellInterface[][] grid1 = this.inProgressModel.getBoard();
    CellInterface cellBefore = grid1[0][3];
    assertEquals(Player.BLUE, cellBefore.getOwner());
    this.inProgressModel.playCard(deck.get(5), 2, 1);
    CellInterface[][] grid = this.inProgressModel.getBoard();
    CellInterface cell = grid[0][3];
    assertEquals(Player.RED, cell.getOwner());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameNotOver() {
    this.inProgressModel.getWinner();
  }

  @Test
  public void testGetWinnerRed() {
    this.inProgressModel.pass();
    this.inProgressModel.pass();
    assertEquals(Player.RED, this.inProgressModel.getWinner());
  }

  @Test
  public void testGetWinnerTie() {
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(0), 1, 4);
    this.inProgressModel.pass();
    this.inProgressModel.pass();
    assertNull(this.inProgressModel.getWinner());
  }

  @Test
  public void testGetWinnerBlue() {
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(0), 1, 4);
    this.inProgressModel.pass();
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(0), 2, 4);
    this.inProgressModel.pass();
    this.inProgressModel.pass();
    assertEquals(Player.BLUE, this.inProgressModel.getWinner());
  }

  @Test
  public void testGameNotOver() {
    assertFalse(this.startModel.isGameOver());
  }

  // also tests pass() as that must work to satisfy game condition
  @Test
  public void testGameOver() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.startGame();
    model.pass();
    model.pass();
    assertTrue(model.isGameOver());

  }

  @Test
  public void testGetBoard() {
    CellInterface[][] expected = new Cell[2][3];
    expected[0][0] = new Cell(Player.RED);
    expected[0][1] = new Cell();
    expected[0][2] = new Cell(Player.BLUE);
    expected[1][0] = new Cell(Player.RED);
    expected[1][1] = new Cell();
    expected[1][2] = new Cell(Player.BLUE);
    assertEquals(expected, this.startModel.getBoard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRedDeck() {
    ReadOnlyModelInterface model = new SanguineModel(3, 5,
        new ConfigFileParser().parseDeck("docs"
            + File.separator + "exampleInvalid.deck"),
        exampleDeck, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBlueDeck() {
    ReadOnlyModelInterface model = new SanguineModel(3, 5, exampleDeck,
        new ConfigFileParser().parseDeck("docs"
            + File.separator + "exampleInvalid4.deck"),
        2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRowCount() {
    ReadOnlyModelInterface model = new SanguineModel(0, 5, exampleDeck,
        exampleDeck, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooLowColumnCount() {
    ReadOnlyModelInterface model = new SanguineModel(3, 1, exampleDeck,
        exampleDeck, 2, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEvenColumnCount() {
    ReadOnlyModelInterface model = new SanguineModel(3, 6, exampleDeck,
        exampleDeck, 2, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTooHighHandSize() {
    ReadOnlyModelInterface model = new SanguineModel(3, 5, exampleDeck,
        exampleDeck, 20, true);
  }

  @Test
  public void testCorrectConstruction() {
    assertEquals(2, this.startModel.getBoard().length);
    assertEquals(3, this.startModel.getBoard()[0].length);
    assertEquals(3, this.startModel.getBoard()[1].length);
    assertEquals(Player.RED, this.startModel.getBoard()[1][0].getOwner());
    assertEquals(PawnAmount.ONE, this.startModel.getBoard()[1][0].getPawnAmt());
    assertEquals(Player.BLUE, this.startModel.getBoard()[0][2].getOwner());
    assertEquals(PawnAmount.ONE, this.startModel.getBoard()[0][2].getPawnAmt());
  }

  @Test
  public void testShuffleInConstruction() {
    ReadOnlyModelInterface model = new SanguineModel(3, 5, exampleDeck,
        exampleDeck, 5, true);
    ((SanguineModel) model).startGame();
    assertNotEquals(model.getHand(Player.BLUE), model.getHand(Player.RED));
  }

  @Test
  public void testMoveChangesPlayer() {
    assertEquals(Player.BLUE, this.inProgressModel.getPlayer());
    this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(0), 1, 4);
    assertEquals(Player.RED, this.inProgressModel.getPlayer());

  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameTwice() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.startGame();
    model.startGame();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetHandGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.getHand(model.getPlayer());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetTotalScoreGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.getTotalScore(model.getPlayer());
  }

  @Test(expected = IllegalStateException.class)
  public void testGetRowScoreGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.getRowScore(model.getPlayer(), 2);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayCardGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.playCard(exampleDeck.getFirst(), 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testIsValidMoveGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.isValidMove(exampleDeck.getFirst(), 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetPlayerGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.getPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testIsGameOverGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.isGameOver();
  }

  @Test(expected = IllegalStateException.class)
  public void testGetWinnerGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.getWinner();
  }

  @Test(expected = IllegalStateException.class)
  public void testPassGameNotStarted() {
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    model.pass();
  }

}
