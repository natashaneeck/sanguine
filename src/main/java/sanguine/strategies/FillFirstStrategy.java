package sanguine.strategies;

import java.util.List;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.ReadOnlyModelInterface;

/**
 * Fill first strategy: gives the first card and location that can be played on.
 */
public class FillFirstStrategy implements Strategy {

  /**
   * Returns a move of the first card and location that can be played on.
   *
   * @param model  the game being played.
   * @param player the player who is making the move.
   * @return the first card and location that can be played on.
   */
  @Override
  public List<Move> getBestMove(ReadOnlyModelInterface model, Player player) {
    for (Card card : model.getHand(player)) {
      for (int rowIdx = 0; rowIdx < model.getNumRows(); rowIdx++) {
        for (int colIdx = 0; colIdx < model.getNumCols(); colIdx++) {
          if (model.isValidMove(card, rowIdx, colIdx)) {
            Move move = new Move(card, colIdx, rowIdx);
            return List.of(move);

          }
        }
      }
    }
    return List.of();
  }

}
