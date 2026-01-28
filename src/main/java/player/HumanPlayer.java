package player;

import java.util.ArrayList;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.view.PlayerActions;

/**
 * A human player. They do not publish any actions, because the view takes on that role instead.
 */
public class HumanPlayer implements PlayerInterface {
  private Player color;
  private PlayerActions listener;

  /**
   * creates a human player represented by the given color.
   *
   * @param color the color representing this player. can be either red or blue.
   */
  public HumanPlayer(Player color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.color = color;
  }


  @Override
  public void decideTurn() {

  }

  @Override
  public void onMoveConfirmed() {

  }

  @Override
  public void onTurnPassed() {

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
