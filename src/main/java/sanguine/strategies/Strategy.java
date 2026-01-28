package sanguine.strategies;

import java.util.List;
import sanguine.Player;
import sanguine.model.ReadOnlyModelInterface;

/**
 * A way to play the game Sanguine, under specific rules of the algorithm for choosing moves.
 */
public interface Strategy {


  /**
   * Returns the best move possible in a game of sanguine, based on the strategy implemented.
   *
   * @param model the game being played.
   * @param player the player who is making the move.
   * @return the best moves the player should play so that they win.
   */
  List<Move> getBestMove(ReadOnlyModelInterface model, Player player);

}
