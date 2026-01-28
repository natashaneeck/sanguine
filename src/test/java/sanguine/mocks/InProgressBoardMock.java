package sanguine.mocks;

import java.io.File;
import java.util.List;
import sanguine.Player;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.model.Cell;
import sanguine.model.CellInterface;
import sanguine.model.ModelStatusListener;
import sanguine.model.MutableModelInterface;


/**
 * A mock game model of Sanguine in progress, with scores, pawns, and a Card placed.
 */
public class InProgressBoardMock implements MutableModelInterface {
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
    if (player == Player.RED) {
      return 3;
    } else {
      return 5;
    }
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

    board[1][2].influence(Player.BLUE);
    board[1][3].influence(Player.RED);

    Card example =
        new ConfigFileParser().parseDeck("docs" + File.separator + "example.deck").get(5);
    board[2][1].influence(Player.BLUE);
    board[2][1].placeCard(example);

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
