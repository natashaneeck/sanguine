package sanguine.strategies;

import java.util.List;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.CellInterface;
import sanguine.model.ReadOnlyModelInterface;

/**
 * Maximizes the row score by choosing a card and location that will get the current player a
 * higher row score than its opponent.
 */
public class MaxRowScoreStrategy implements Strategy {

  @Override
  public List<Move> getBestMove(ReadOnlyModelInterface model, Player player) {
    CellInterface[][] board = model.getBoard();
    for (int rowIdx = 0; rowIdx < model.getNumRows(); rowIdx++) {
      int oppScore = model.getRowScore(this.getOpponent(player), rowIdx);
      int playerScore = model.getRowScore(player, rowIdx);
      if (playerScore <= oppScore) {
        for (Card card : model.getHand(player)) {
          for (int colIdx = 0; colIdx < model.getNumCols(); colIdx++) {
            if (model.isValidMove(card, rowIdx, colIdx)
                && playerScore + card.getValue() > oppScore) {
              Move move = new Move(card, colIdx, rowIdx);
              return List.of(move);
            }
          }
        }
      }
    }
    return List.of();
  }

  private Player getOpponent(Player player) {
    if (player == Player.RED) {
      return Player.BLUE;
    } else {
      return Player.RED;
    }
  }
}
