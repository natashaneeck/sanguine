package sanguine.model;

import sanguine.Player;

/**
 * An interface defining actions listeners must take when notified about changes to the game state
 * in the model.
 */
public interface ModelStatusListener {

  /**
   * called when a player finishes their turn, and it is the next player's turn.
   *
   * @param player the player whose turn has just begun.
   */
  void onTurnChanged(Player player);
}
