package sanguine.controller;

import java.awt.Component;
import javax.swing.JOptionPane;
import player.PlayerInterface;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.ModelStatusListener;
import sanguine.model.MutableModelInterface;
import sanguine.view.GameView;
import sanguine.view.PlayerActions;
import sanguine.view.ViewFeatures;

/**
 * Game controller for the game Sanguine, run in a GUI window.
 */
public class SanguineController implements ControllerInterface, ViewFeatures, PlayerActions,
    ModelStatusListener {
  private final GameView view;
  private final MutableModelInterface model;
  private final PlayerInterface player;
  private Card card;
  private int row;
  private int col;

  /**
   * Initializes the controller and saves the view and model, adding this as a listener to the view.
   *
   * @param view  the view of the game being played.
   * @param model the game being controlled.
   * @param player the player using this controller.
   */
  public SanguineController(GameView view, MutableModelInterface model, PlayerInterface player) {
    if (view == null || model == null || player == null) {
      throw new IllegalArgumentException("Cannot be null");
    }
    this.view = view;
    this.model = model;
    this.player = player;
    this.view.addFeatureListener(this);
    this.view.addPlayerActionListener(this);
    this.model.addModelStatusListener(this);
    this.player.subscribe(this);
  }

  @Override
  public void runGame() {
    this.view.display(true);
  }

  @Override
  public void quit() {
    System.exit(0);
  }

  @Override
  public void printCellCoordinates(int posX, int posY) {
    System.out.println(posX + ", " + posY);
  }

  @Override
  public void printHandCoordinates(int posX) {
    System.out.println(this.model.getPlayer().toString() + ": " + posX);
  }

  @Override
  public void printPass() {
    System.out.println("player passed.");
  }

  @Override
  public void printConfirm() {
    System.out.println("move confirmed.");
  }

  @Override
  public void onTurnChanged(Player color) {
    view.repaint();

    if (this.model.isGameOver()) {
      this.showGameOver();
    } else {
      if (this.player.getColor().equals(color)) {
        this.player.decideTurn();
      }
    }
  }

  @Override
  public void onCardSelected(Card card) {
    this.ensureTurnCorrect();
    if (card == null) {
      throw new IllegalArgumentException("cannot be null");
    }
    this.card = card;
  }

  @Override
  public void onCellSelected(int row, int col) {
    this.ensureTurnCorrect();
    this.row = row;
    this.col = col;
  }

  @Override
  public void onMoveConfirmed() {
    this.ensureTurnCorrect();
    this.model.playCard(this.card, this.row, this.col);

    this.card = null;
    this.row = -1;
    this.col = -1;
  }

  @Override
  public void onTurnPassed() {
    this.ensureTurnCorrect();
    this.model.pass();

    this.card = null;
    this.row = -1;
    this.col = -1;
  }

  private void ensureTurnCorrect() {
    if (this.model.getPlayer() != this.player.getColor()) {
      throw new IllegalStateException("Cannot play when not your turn");
    }
  }

  private void showGameOver() {
    Player winner = this.model.getWinner();
    String winString = "";
    if (winner == null) {
      winString = "Winner: " + "Tie" + "\nScore: " + this.model.getTotalScore(Player.RED);
    } else {
      winString = "Winner: " + winner.toString() + "\nScore: "
          + this.model.getTotalScore(winner);
    }
    JOptionPane.showMessageDialog((Component) this.view,
        winString, "Game Over", JOptionPane.PLAIN_MESSAGE);
  }
}
