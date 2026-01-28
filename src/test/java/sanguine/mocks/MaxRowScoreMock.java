package sanguine.mocks;

import java.io.File;
import java.util.List;
import sanguine.Player;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.model.CellInterface;
import sanguine.model.ModelStatusListener;
import sanguine.model.MutableModelInterface;

/**
 * Mocks the model so that the red player always has a lower score than blue only in the second row.
 */
public class MaxRowScoreMock implements MutableModelInterface {


  @Override
  public void startGame() throws IllegalStateException {

  }

  @Override
  public void playCard(Card card, int row, int col)
      throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void pass() {

  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) throws IllegalStateException {

  }

  @Override
  public List<Card> getHand(Player player) throws IllegalArgumentException {
    return new ConfigFileParser().parseDeck("docs"
        + File.separator + "example.deck").subList(12, 14);
  }

  @Override
  public int getTotalScore(Player player) throws IllegalArgumentException {
    return 0;
  }

  @Override
  public int getRowScore(Player player, int row) throws IllegalArgumentException {
    if (player == Player.RED && row == 2) {
      return 0;
    } else if (player == Player.BLUE) {
      return 1;
    } else {
      return 2;
    }
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
    return new CellInterface[0][];
  }

  @Override
  public int getNumRows() {
    return 3;
  }

  @Override
  public int getNumCols() {
    return 5;
  }

  @Override
  public boolean isValidMove(Card card, int row, int col) {
    return true;
  }

  @Override
  public Player getPlayer() {
    return null;
  }
}
