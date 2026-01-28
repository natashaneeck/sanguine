package sanguine.view;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sanguine.Player;
import sanguine.model.ReadOnlyModelInterface;

/**
 * A GamePanel for the Sanguine game that can draw itself and has a listener for features of the
 * view.
 */
public abstract class AbstractPanel extends JPanel implements GamePanel {
  protected final ReadOnlyModelInterface model;
  protected ViewFeatures listener;
  protected List<PlayerActions> playerListeners;
  protected Player player;

  /**
   * Stores the model in this panel, cannot be null.
   *
   * @param model the current game.
   */
  protected AbstractPanel(ReadOnlyModelInterface model, Player player) {
    this.model = Objects.requireNonNull(model);
    this.playerListeners = new ArrayList<>();
    this.player = Objects.requireNonNull(player);
  }

  @Override
  public void addFeatureListener(ViewFeatures feature) {
    this.listener = Objects.requireNonNull(feature);

    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  @Override
  public void addPlayerActionListener(PlayerActions listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener argument cannot be null");
    }
    this.playerListeners.add(listener);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  /**
   * Shows a message dialogue to the user explaining the error that happened.
   *
   * @param ex the exception being caught
   */
  protected void showErrorDialogue(Exception ex) {
    JOptionPane.showMessageDialog(this,
        ex, "Error", JOptionPane.WARNING_MESSAGE);
  }
}
