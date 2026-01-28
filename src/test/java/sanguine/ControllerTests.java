package sanguine;

import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.HumanPlayer;
import player.MachinePlayer;
import sanguine.controller.ConfigFileParser;
import sanguine.controller.ControllerInterface;
import sanguine.controller.SanguineController;
import sanguine.mocks.GameViewMock;
import sanguine.mocks.StartingBoardMock;
import sanguine.model.Card;
import sanguine.model.CellInterface;
import sanguine.model.ModelStatusListener;
import sanguine.model.MutableModelInterface;
import sanguine.model.SanguineModel;
import sanguine.strategies.FillFirstStrategy;
import sanguine.view.PlayerActions;

/**
 * Tests for the SanguineController.
 */
public class ControllerTests {
  ControllerInterface controller;

  /**
   * Sets up for future tests and checks valid construction.
   */
  @Before
  public void setUp() {
    this.controller = new SanguineController(new GameViewMock(),
        new StartingBoardMock(), new HumanPlayer(Player.BLUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionNullModel() {
    ControllerInterface control = new SanguineController(new GameViewMock(), null,
        new HumanPlayer(Player.RED));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionNullView() {
    ControllerInterface control = new SanguineController(null, new StartingBoardMock(),
        new HumanPlayer(Player.RED));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructionNullPlayer() {
    ControllerInterface control = new SanguineController(null, new StartingBoardMock(),
        null);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullOnCardSelected() {
    PlayerActions actionController = new SanguineController(new GameViewMock(),
        new StartingBoardMock(), new HumanPlayer(Player.RED));
    actionController.onCardSelected(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotTurnCardSelected() {
    PlayerActions actionController = (PlayerActions) controller;
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator + "example.deck");
    actionController.onCardSelected(deck.get(3));
  }

  @Test(expected = IllegalStateException.class)
  public void testNotTurnCellSelected() {
    PlayerActions actionController = (PlayerActions) controller;
    actionController.onCellSelected(2, 2);
  }

  @Test(expected = IllegalStateException.class)
  public void testNotTurnMoveConfirmed() {
    PlayerActions actionController = (PlayerActions) controller;
    actionController.onMoveConfirmed();
  }

  @Test
  public void testOnTurnChangedNotifiesPlayer() {
    List<Card> exampleBigDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "exampleBig.deck");
    MutableModelInterface model = new SanguineModel(5, 7,
        exampleBigDeck, exampleBigDeck, 5, false);
    model.startGame();
    this.controller = new SanguineController(new GameViewMock(), model,
        new MachinePlayer(new FillFirstStrategy(), model, Player.RED));
    ModelStatusListener actionController = (ModelStatusListener) controller;

    actionController.onTurnChanged(Player.RED);
    assertNotEquals(Player.RED, model.getPlayer());
  }

  @Test
  public void testOnMoveConfirmedPlaysCard() {
    List<Card> exampleBigDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "exampleBig.deck");
    MutableModelInterface model = new SanguineModel(5, 7,
        exampleBigDeck, exampleBigDeck, 5, false);
    model.startGame();
    this.controller = new SanguineController(new GameViewMock(), model,
        new MachinePlayer(new FillFirstStrategy(), model, Player.RED));
    PlayerActions actionController = (PlayerActions) controller;
    actionController.onCardSelected(exampleBigDeck.getFirst());
    actionController.onCellSelected(0, 0);

    CellInterface[][] beforeBoard = model.getBoard();
    actionController.onMoveConfirmed();
    assertNotEquals(beforeBoard, model.getBoard());
  }
}
