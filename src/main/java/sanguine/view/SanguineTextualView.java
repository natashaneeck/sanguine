package sanguine.view;

import sanguine.Player;
import sanguine.model.CellInterface;
import sanguine.model.PawnAmount;
import sanguine.model.ReadOnlyModelInterface;

/**
 * Contains the necessary methods for making the game visible to the Players all throughout the
 * gameplay of Sanguine. This implementation uses a textual representation for the game as a String.
 */
public class SanguineTextualView implements TextualView {
  ReadOnlyModelInterface model;

  /**
   * Stores the aliased model as a field so it is accessible throughout the game.
   *
   * @param model the Sanguine game being played
   * @throws IllegalArgumentException if model is null
   */
  public SanguineTextualView(ReadOnlyModelInterface model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  /**
   * Returns the current board state represented as a String.
   *
   * @return the visualization of the game board
   */
  @Override
  public String renderBoard() {
    CellInterface[][] board = this.model.getBoard();
    String rendered = "";

    for (int index = 0; index < board.length; index++) {
      rendered += this.model.getRowScore(Player.RED, index)  + " ||"
          + this.renderRow(board[index])
          + " || " + this.model.getRowScore(Player.BLUE, index)
          + System.lineSeparator();
    }
    return rendered;
  }

  private String renderRow(CellInterface[] row) {
    String rendered = "";

    for (CellInterface cell : row) {
      rendered += " " + this.renderCell(cell);
    }
    return rendered;
  }

  private String renderCell(CellInterface cell) {
    if (cell.hasCard()) {
      return cell.getOwner().toString().substring(0, 1);

    } else if (cell.getPawnAmt() != PawnAmount.ZERO) {
      return cell.getPawnAmt().toString();

    } else {
      return "_";
    }
  }
}
