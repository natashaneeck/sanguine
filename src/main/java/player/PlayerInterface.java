package player;

import sanguine.Player;
import sanguine.model.Card;
import sanguine.view.PlayerActions;

/**
 * an interface defining actions that can be taken by a player entity for Sanguine.
 */
public interface PlayerInterface {

  /**
   * The move the player wishes to make.
   */
  void decideTurn();

  /**
   * Called when a player confirms their move.
   */
  void onMoveConfirmed();

  /**
   * Called when a player passes their turn.
   */
  void onTurnPassed();

  /**
   * subscribes the listener to the notifications of this object.
   *
   * @param listener the listener object to be notified.
   */
  void subscribe(PlayerActions listener);

  /**
   * What color of this player as a Player enum value.
   *
   * @return Player.RED if the player is red, Player.BLUE if the player is blue.
   */
  Player getColor();
}
