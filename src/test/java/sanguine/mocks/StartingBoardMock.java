package sanguine.mocks;

import java.util.List;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.CellInterface;
import sanguine.model.ModelStatusListener;
import sanguine.model.MutableModelInterface;

/**
 * A mock model that only has the board state at the very start of the game.
 */
public class StartingBoardMock implements MutableModelInterface {
  @Override
  public List<Card> getHand(Player player) throws IllegalArgumentException {
    return List.of();
  }

  @Override
  public int getTotalScore(Player player) throws IllegalArgumentException {
    return 0;
  }

  @Override
  public int getRowScore(Player player, int row) throws IllegalArgumentException {
    return 0;
  }

  @Override
  public void startGame() throws IllegalStateException {

  }

  @Override
  public void playCard(Card card, int row, int col)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public CellInterface[][] getBoard() {
    CellInterface[][] board = new Cell[3][5];
    for (CellInterface[] row : board) {
      row[0] = new Cell(Player.RED);
      for (int index = 1; index < 4; index++) {
        row[index] = new Cell();
      }
      row[4] = new Cell(Player.BLUE);
    }

    return board;
  }

  @Override
  public int getNumRows() {
    return 0;
  }

  @Override
  public int getNumCols() {
    return 0;
  }

  @Override
  public boolean isValidMove(Card card, int row, int col) {
    return false;
  }

  @Override
  public Player getPlayer() {
    return null;
  }

  @Override
  public void pass() {

  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) throws IllegalStateException {

  }
}
