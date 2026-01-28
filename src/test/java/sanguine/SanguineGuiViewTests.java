package sanguine;

import org.junit.Test;
import sanguine.mocks.InProgressBoardMock;
import sanguine.view.GameView;
import sanguine.view.SanguineView;

/**
 * Tests for SanguineView.
 */
public class SanguineGuiViewTests {

  @Test
  public void testValidConstruction() {
    GameView view = new SanguineView(new InProgressBoardMock(), Player.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelConstruction() {
    GameView view = new SanguineView(null, Player.BLUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerConstruction() {
    GameView view = new SanguineView(new InProgressBoardMock(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAddPlayerListener() {
    GameView view = new SanguineView(new InProgressBoardMock(), Player.BLUE);
    view.addPlayerActionListener(null);
  }
}
