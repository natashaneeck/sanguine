package sanguine.model;


/**
 * A Sanguine game model that also allows actions to be taken by the players.
 * Players can play cards in a specified coordinate or pass their turn.
 */
public interface MutableModelInterface extends ReadOnlyModelInterface {

  /**
   * Signals the game is ready to be played. No moves should be able to be made prior to this
   * method call. Also tells the first Player that it is their turn.
   *
   * @throws IllegalStateException if game already started
   */
  void startGame() throws IllegalStateException;

  /**
   * Plays the given Card at the specified 0-indexed Cell.
   *
   * @param card the Card to play
   * @param row  the row to play the Card at
   * @param col  the column to play the Card at
   * @throws IllegalArgumentException if the given row or column is invalid or the given card is
   *                                  null
   * @throws IllegalStateException    if there is already a card in the given cell, if the given
   *                                  cell belongs to the other player, if there are not enough
   *                                  pawns in the cell.
   *                                  or if game has not started
   */
  public void playCard(Card card, int row, int col) throws IllegalArgumentException,
      IllegalStateException;

  /**
   * Passes the turn of the current player and changes the turn to the next Player.
   */
  public void pass();

  /**
   * subscribes the given listener to this model, so that it is notified of any changes to the game
   * state.
   *
   * @param listener the listener being subscribed to the model.
   * @throws IllegalStateException if game not started
   */
  public void addModelStatusListener(ModelStatusListener listener) throws IllegalStateException;

}
