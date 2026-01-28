package sanguine.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.List;
import javax.swing.JOptionPane;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.InfluenceType;
import sanguine.model.ReadOnlyModelInterface;

/**
 * A Panel containing the Card hand of the player whose turn it is, displaying the Card info.
 */
public class SanguineHandPanel extends AbstractPanel {
  private int highlightedCard;

  /**
   * Initializes this panel, determining the coordinates for the game based on model observers.
   *
   * @param model the game being represented by this view.
   * @param color the player's color
   */
  public SanguineHandPanel(ReadOnlyModelInterface model, Player color) {
    super(model, color);
    this.highlightedCard = -1;
    this.player = color;
  }

  @Override
  public void addFeatureListener(ViewFeatures feature) {
    super.addFeatureListener(feature);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          int col = (int) (e.getX() * (double) model.getHand(player).size() / getWidth());
          if (model.getPlayer() == player) {
            listener.printHandCoordinates(col + 1);
            setHighlightedCard(col);
          }
          for (PlayerActions listener : playerListeners) {
            listener.onCardSelected(model.getHand(player).get(col));
          }
        } catch (IllegalArgumentException | IllegalStateException ex) {
          showErrorDialogue(ex);
        }
      }
    });

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        try {
          requestFocusInWindow();
          char c = e.getKeyChar();
          if (c == 'p') {
            setHighlightedCard(-1); // this is the line being skipped
            listener.printPass(); //the rest of these lines work, confirmable by this print/visuals
            for (PlayerActions listener : playerListeners) {
              listener.onTurnPassed();
            }
          } else if (c == KeyEvent.VK_ENTER) {
            setHighlightedCard(-1);
            listener.printConfirm();
            for (PlayerActions listener : playerListeners) {
              listener.onMoveConfirmed();
            }
          }
        } catch (IllegalArgumentException | IllegalStateException ex) {
          showErrorDialogue(ex);
        }
      }
    });
  }

  private void setHighlightedCard(int col) {
    if (highlightedCard == col) {
      highlightedCard = -1;
    } else {
      highlightedCard = col;
    }
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    Color red = new Color(255, 112, 112);
    Color blue = new Color(125, 160, 255);
    if (this.player == Player.RED) {
      setBackground(red);
    } else {
      setBackground(blue);
    }

    int numCols = model.getHand(this.player).size();
    int panelWidth = getWidth();
    int panelHeight = getHeight();
    double cellWidth = (panelWidth / (double) numCols);

    for (int idx = 0; idx < numCols; idx++) {
      double x = idx * cellWidth;
      g2d.setStroke(new BasicStroke(2.0f));
      Rectangle2D rect = new Rectangle2D.Double(x, 0, cellWidth, panelHeight);
      if (idx == highlightedCard) {
        g2d.setColor(Color.CYAN);
        g2d.fill(rect);
      }

      g2d.setColor(Color.BLACK);
      g2d.draw(rect);
      this.paintCard(g2d, model.getHand(this.player).get(idx), idx, 10);
    }
  }

  private void paintCard(Graphics2D g2d, Card card, int idx, int padding) {
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 14));
    String name =  card.getName();
    String cost = "Cost: " + Integer.toString(card.getCost());
    String value = "Value: " + Integer.toString(card.getValue());

    int numCols = model.getHand(this.player).size();
    int gap = getHeight() / 8;
    int cellWidth = getWidth() / numCols;
    g2d.drawString(name, idx * cellWidth + padding, gap);
    g2d.drawString(cost, idx * cellWidth + padding, 2 * gap);
    g2d.drawString(value, idx * cellWidth + padding, 3 * gap);

    for (int rowIdx = 0; rowIdx < card.getInfluence().size(); rowIdx++) {
      String rowInfluence = "";
      List<InfluenceType> row = card.getInfluence().get(rowIdx);
      if (this.player == Player.BLUE) {
        for (int colIdx = row.size() - 1; colIdx >= 0; colIdx--) {
          rowInfluence += (row.get(colIdx));
        }
      } else {
        for (InfluenceType influence : card.getInfluence().get(rowIdx)) {
          rowInfluence += (influence);
        }
      }
      g2d.drawString(rowInfluence, idx * cellWidth + padding, 4 * gap + rowIdx * gap);
    }
  }
}
