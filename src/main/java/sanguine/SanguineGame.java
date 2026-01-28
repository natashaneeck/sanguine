package sanguine;

import java.util.List;
import player.HumanPlayer;
import player.MachinePlayer;
import player.PlayerInterface;
import sanguine.controller.ConfigFileParser;
import sanguine.controller.ControllerInterface;
import sanguine.controller.SanguineController;
import sanguine.model.Card;
import sanguine.model.MutableModelInterface;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.model.SanguineModel;
import sanguine.strategies.FillFirstStrategy;
import sanguine.strategies.MaxRowScoreStrategy;
import sanguine.view.GameView;
import sanguine.view.SanguineView;

/**
 * Main method for a GUI version of Sanguine.
 */
public final class SanguineGame {

  /**
   * Runs the game Sanguine in a GUI view.
   *
   * @param args specifications of the game, num rows, num columns, red filepath, blue filepath,
   *             red player type, blue player type
   */
  public static void main(String[] args) {
    String redFilePath = args[2];
    String blueFilePath = args[3];
    List<Card> redDeck = new ConfigFileParser().parseDeck(redFilePath);
    List<Card> blueDeck = new ConfigFileParser().parseDeck(blueFilePath);
    MutableModelInterface model = new SanguineModel(Integer.parseInt(args[0]),
        Integer.parseInt(args[1]), redDeck, blueDeck, 5, false);

    PlayerInterface red = SanguineGame.getPlayerType(args[4].toLowerCase(), model,
        Player.RED);
    PlayerInterface blue = SanguineGame.getPlayerType(args[5].toLowerCase(),
        model, Player.BLUE);

    GameView viewRed = new SanguineView((ReadOnlyModelInterface) model, red.getColor());
    GameView viewBlue = new SanguineView((ReadOnlyModelInterface) model, blue.getColor());
    ControllerInterface controllerRed = new SanguineController(viewRed, model, red);
    ControllerInterface controllerBlue = new SanguineController(viewBlue, model, blue);
    model.startGame();
    controllerRed.runGame();
    controllerBlue.runGame();
  }

  private static PlayerInterface getPlayerType(String arg, MutableModelInterface model,
                                               Player player) {
    if (arg.equals("human")) {
      return new HumanPlayer(player);
    } else if (arg.equals("fill-first")) {
      return new MachinePlayer(new FillFirstStrategy(), model, player);
    } else {
      return new MachinePlayer(new MaxRowScoreStrategy(), model, player);
    }
  }
}