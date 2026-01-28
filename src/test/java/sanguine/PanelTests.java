package sanguine;

import java.io.File;
import java.util.List;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.mocks.InProgressBoardMock;
import sanguine.mocks.StartingBoardMock;
import sanguine.model.Card;
import sanguine.model.MutableModelInterface;
import sanguine.model.SanguineModel;
import sanguine.view.GamePanel;
import sanguine.view.SanguineBoardPanel;
import sanguine.view.SanguineHandPanel;

/**
 * Tests for the panel classes that extend AbstractPanel.
 */
public class PanelTests {

  @Test(expected = NullPointerException.class)
  public void testNullModelBoardPanel() {
    GamePanel panel1 = new SanguineBoardPanel(null, Player.RED);
  }

  @Test(expected = NullPointerException.class)
  public void testNullModelHandPanel() {
    GamePanel panel1 = new SanguineHandPanel(null, Player.RED);
  }

  @Test(expected = NullPointerException.class)
  public void testNullPlayerBoardPanelIntegrated() {
    List<Card> exampleBigDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "exampleBig.deck");
    MutableModelInterface model = new SanguineModel(5, 7,
        exampleBigDeck, exampleBigDeck, 5, false);
    GamePanel panel1 = new SanguineBoardPanel(model, null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullPlayerHandPanelIntegrated() {
    List<Card> exampleBigDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "exampleBig.deck");
    MutableModelInterface model = new SanguineModel(5, 7,
        exampleBigDeck, exampleBigDeck, 5, false);
    GamePanel panel1 = new SanguineHandPanel(model, null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullPlayerBoardPanelUnit() {
    GamePanel panel1 = new SanguineBoardPanel(new StartingBoardMock(), null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullPlayerHandPanelUnit() {
    GamePanel panel1 = new SanguineHandPanel(new InProgressBoardMock(), null);
  }

  @Test(expected = NullPointerException.class)
  public void testNullFeatureListener() {
    GamePanel panel1 = new SanguineHandPanel(new InProgressBoardMock(), Player.RED);
    panel1.addFeatureListener(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPlayerActionListener() {
    GamePanel panel1 = new SanguineBoardPanel(new InProgressBoardMock(), Player.RED);
    panel1.addPlayerActionListener(null);
  }
}
