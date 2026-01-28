package sanguine.view;

import sanguine.model.Card;

/**
 * An interface defining the events that can be published when a player makes a move.
 * On their turn, players can choose a card to play, choose a cell to play on, confirm their move,
 * or pass.
 */
public interface PlayerActions {

  /**
   * Called when a player selects the given card.
   *
   * @param card the card selected by the player.
   */
  void onCardSelected(Card card);

  /**
   * Called when a player selects a cell on the board.
   *
   * @param row the 0-based index of the row on the board of the selected cell.
   * @param col he 0-based index of the column on the board of the selected cell.
   */
  void onCellSelected(int row, int col);

  /**
   * Called when a player confirms their move.
   */
  void onMoveConfirmed();

  /**
   * Called when a player passes their turn.
   */
  void onTurnPassed();

}
