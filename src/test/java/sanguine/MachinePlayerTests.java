package sanguine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import org.junit.Test;
import player.MachinePlayer;
import sanguine.controller.ConfigFileParser;
import sanguine.controller.ControllerInterface;
import sanguine.controller.SanguineController;
import sanguine.mocks.NoGoodMoveMock;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.CellInterface;
import sanguine.model.MutableModelInterface;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.model.SanguineModel;
import sanguine.strategies.FillFirstStrategy;
import sanguine.strategies.Strategy;
import sanguine.view.GameView;
import sanguine.view.SanguineView;

/**
 * tests for machine player.
 */
public class MachinePlayerTests {

  @Test
  public void testConstructor() {
    Strategy strat = new FillFirstStrategy();
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    MachinePlayer machinePlayer = new MachinePlayer(strat, model, Player.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullStrategy() {
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    MachinePlayer machinePlayer = new MachinePlayer(null, model, Player.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    Strategy strat = new FillFirstStrategy();
    MachinePlayer machinePlayer = new MachinePlayer(strat, null, Player.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPlayer() {
    Strategy strat = new FillFirstStrategy();
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    MachinePlayer machinePlayer = new MachinePlayer(strat, model, null);
  }

  @Test
  public void testPlayerPassesWhenNoGoodMove() {
    Strategy strat = new NoGoodMoveMock();
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    MachinePlayer machinePlayer = new MachinePlayer(strat, model, Player.RED);
    GameView view = new SanguineView((ReadOnlyModelInterface) model, machinePlayer.getColor());
    ControllerInterface controller = new SanguineController(view, model, machinePlayer);
    CellInterface[][] board = model.getBoard();
    model.startGame();
    assertEquals(Player.BLUE, model.getPlayer());
    assertEquals(6, model.getHand(machinePlayer.getColor()).size());
  }

  @Test
  public void testPlaysMoveRecommendedByStrategy() {
    Strategy strat = new FillFirstStrategy();
    List<Card> exampleDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "example" + ".deck");
    MutableModelInterface model = new SanguineModel(3, 5,
        exampleDeck, exampleDeck, 5, false);
    MachinePlayer machinePlayer = new MachinePlayer(strat, model, Player.RED);
    GameView view = new SanguineView((ReadOnlyModelInterface) model, machinePlayer.getColor());
    ControllerInterface controller = new SanguineController(view, model, machinePlayer);
    CellInterface[][] board = model.getBoard();
    model.startGame();
    assertEquals(Player.BLUE, model.getPlayer());
    assertEquals(5, model.getHand(machinePlayer.getColor()).size());
    assertEquals(exampleDeck.getFirst(), model.getBoard()[0][0].getCard());
  }


}
