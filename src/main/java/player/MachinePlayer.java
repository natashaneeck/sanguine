package player;

import java.util.List;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.strategies.Move;
import sanguine.strategies.Strategy;
import sanguine.view.PlayerActions;

/**
 * A automatic player for the game Sanguine that relies on a given strategy.
 */
public class MachinePlayer implements PlayerInterface {
  private Strategy strategy;
  private ReadOnlyModelInterface model;
  private Player color;
  private PlayerActions listener;


  /**
   * Creates this player.
   *
   * @param strategy the decision-making process for this player
   * @param model the Sanguine game
   * @param color the Player color this player is
   */
  public MachinePlayer(Strategy strategy, ReadOnlyModelInterface model, Player color) {
    if (strategy == null || model == null || color == null) {
      throw new IllegalArgumentException("player fields cannot be null");
    }
    this.strategy = strategy;
    this.model = model;
    this.color = color;
  }


  @Override
  public void decideTurn() {
    List<Move> moves = this.strategy.getBestMove(this.model, this.color);
    if (moves.isEmpty()) {
      this.onTurnPassed();
    } else {
      Move move = moves.getFirst();
      this.listener.onCardSelected(move.getCard());
      this.listener.onCellSelected(move.getRow(), move.getCol());
      this.onMoveConfirmed();
    }
  }

  @Override
  public void onMoveConfirmed() {
    this.listener.onMoveConfirmed();
  }

  @Override
  public void onTurnPassed() {
    listener.onTurnPassed();
  }

  @Override
  public void subscribe(PlayerActions listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener must not be null");
    }
    this.listener = listener;
  }

  @Override
  public Player getColor() {
    return this.color;
  }
}
