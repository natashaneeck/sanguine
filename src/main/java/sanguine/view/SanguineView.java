package sanguine.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import sanguine.Player;
import sanguine.model.ReadOnlyModelInterface;

/**
 * A view for the game Sanguine, containing a panel that displays the board and another to
 * display a player's hand. Listens for mouse clicks and keyboard presses.
 */
public class SanguineView extends JFrame implements GameView {
  private final SanguineBoardPanel boardPanel;
  private final SanguineHandPanel handPanel;
  private List<PlayerActions> listeners;
  private Player player;

  /**
   * Initializes the view with a model that cannot be modified by this view.
   *
   * @param model the Sanguine game
   */
  public SanguineView(ReadOnlyModelInterface model, Player color) {
    if (model == null || color == null) {
      throw new IllegalArgumentException("arguments cannot be null");
    }
    this.setSize(800, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.boardPanel = new SanguineBoardPanel(model, color);
    this.handPanel = new SanguineHandPanel(model, color);
    boardPanel.setPreferredSize(new Dimension(800, 400));
    handPanel.setPreferredSize(new Dimension(800, 200));
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(handPanel, BorderLayout.SOUTH);
    this.listeners = new ArrayList<>();
    this.player = color;
  }


  @Override
  public void addFeatureListener(ViewFeatures feature) {
    this.boardPanel.addFeatureListener(feature);
    this.handPanel.addFeatureListener(feature);
  }

  @Override
  public void addPlayerActionListener(PlayerActions listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener argument cannot be null");
    }
    this.listeners.add(listener);
    this.boardPanel.addPlayerActionListener(listener);
    this.handPanel.addPlayerActionListener(listener);
  }

  @Override
  public void display(boolean show) {
    this.setVisible(show);
  }
}
