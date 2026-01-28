package sanguine.mocks;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import sanguine.Player;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.model.CellInterface;
import sanguine.model.ModelStatusListener;
import sanguine.model.MutableModelInterface;

/**
 * Logs all the coordinates checked in isValidMove(). Pretends this is a 3x5 board.
 * Uses a hand of the first 3 cards from docs/example.deck so strategies can loop over them.
 */
public class CoordsCheckedLoggerMock implements MutableModelInterface {
  Appendable ap;

  /**
   * Initializes the mock with an Appendable.
   *
   * @param ap the appendable object
   */
  public CoordsCheckedLoggerMock(Appendable ap) {
    this.ap = Objects.requireNonNull(ap);
  }

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
        + File.separator + "example.deck").subList(0, 2);
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
    try {
      this.ap.append("Row: ").append(String.valueOf(row)).append(" Col: ")
          .append(String.valueOf(col)).append("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  @Override
  public Player getPlayer() {
    return null;
  }
}
