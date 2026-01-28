package sanguine.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import sanguine.Player;
import sanguine.model.Card;
import sanguine.model.CellInterface;
import sanguine.model.ReadOnlyModelInterface;

/**
 * A Panel containing the grid of the ongoing Sanguine game, displaying Cards and pawns.
 */
public class SanguineBoardPanel extends AbstractPanel {
  private final double logicalX;
  private final double logicalY;
  private int[] highlightedCellCoords;

  /**
   * Initializes this panel, determining the coordinates for the game based on model observers.
   *
   * @param model the game being represented by this view.
   */
  public SanguineBoardPanel(ReadOnlyModelInterface model, Player player) {
    super(model, player);
    this.logicalX = 10 * model.getNumCols();
    this.logicalY = 10 * model.getNumRows();
    this.highlightedCellCoords = new int[] {-1, -1};
  }

  @Override
  public void addFeatureListener(ViewFeatures feature) {
    super.addFeatureListener(feature);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          int row = (int) (e.getY() * (double) model.getNumRows() / getHeight());
          int col = (int) (e.getX() * (double) model.getNumCols() / getWidth());
          if (model.getPlayer() == player) {
            listener.printCellCoordinates(col + 1, row + 1);
            setHighlightedCell(col, row);
          }
          for (PlayerActions listener : playerListeners) {
            listener.onCellSelected(row, col);
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
          char c = e.getKeyChar();
          if (c == 'p') {
            setHighlightedCell(-1, -1);
            listener.printPass();
            for (PlayerActions listener : playerListeners) {
              listener.onTurnPassed();
            }
          } else if (c == KeyEvent.VK_ENTER) {
            setHighlightedCell(-1, -1);
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

  private void setHighlightedCell(int col, int row) {
    if (highlightedCellCoords[0] == col && highlightedCellCoords[1] == row) {
      highlightedCellCoords[0] = -1;
      highlightedCellCoords[1] = -1;
    } else {
      highlightedCellCoords = new int[] {(int) col, (int) row};
    }
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    setBackground(Color.GRAY);
    g2d.scale(getWidth() / logicalX, getHeight() / logicalY);

    g2d.setStroke(new BasicStroke(0.5f));

    CellInterface[][] board = this.model.getBoard();
    for (int rowIdx = 0; rowIdx < this.model.getNumRows(); rowIdx++) {
      for (int colIdx = 0; colIdx < this.model.getNumCols(); colIdx++) {
        this.drawCell(g2d, rowIdx, colIdx, board[rowIdx][colIdx]);
      }
    }
  }

  private void drawCell(Graphics2D g2d, int rowIdx, int colIdx, CellInterface cell) {
    int modelRowToLogicalY = (int) this.logicalY / model.getNumRows();
    int modelColToLogicalX = (int) this.logicalX / model.getNumCols();

    if (colIdx == this.highlightedCellCoords[0] && rowIdx == this.highlightedCellCoords[1]) {
      g2d.setColor(Color.CYAN);
    } else {
      g2d.setColor(Color.GRAY);
    }

    Rectangle2D rect = new Rectangle2D.Double(colIdx * modelColToLogicalX,
        rowIdx * modelRowToLogicalY,
        modelColToLogicalX, modelRowToLogicalY);

    if (cell.hasCard()) {
      this.getOwnerColor(g2d, cell.getOwner());
      g2d.fill(rect);
      this.drawCard(g2d, rowIdx, colIdx, cell.getCard());

    } else if (cell.getPawnAmt().getNumPawns() > 0) {
      g2d.fill(rect);
      this.getOwnerColor(g2d, cell.getOwner());
      this.drawPawns(g2d, rowIdx, colIdx, cell.getPawnAmt().getNumPawns());
    } else {
      g2d.fill(rect);
    }

    g2d.setColor(Color.BLACK);
    g2d.draw(rect);
  }

  private void drawPawns(Graphics2D g2d, int rowIdx, int colIdx, int numPawns) {
    int modelRowToLogicalY = (int) this.logicalY / model.getNumRows();
    int modelColToLogicalX = (int) this.logicalX / model.getNumCols();

    int halfCellWidth = (int) modelColToLogicalX / 2;
    int twoThirdsCellHeight = (int) (modelRowToLogicalY / 3) * 2;

    g2d.setFont(new Font("Arial", Font.BOLD, 3));
    g2d.drawString(Integer.toString(numPawns), colIdx * modelColToLogicalX + halfCellWidth,
        rowIdx * modelRowToLogicalY + twoThirdsCellHeight);
  }

  private void drawCard(Graphics2D g2d, int rowIdx, int colIdx, Card card) {
    int modelRowToLogicalY = (int) this.logicalY / model.getNumRows();
    int modelColToLogicalX = (int) this.logicalX / model.getNumCols();

    int paddingX = (int) (this.logicalX / model.getNumCols()) / 10;
    int paddingY = (int) (this.logicalY / model.getNumRows()) / 3;

    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 2));
    String name = card.getName();
    String value = "Value: " + Integer.toString(card.getValue());
    g2d.drawString(name, colIdx * modelColToLogicalX + paddingX,
        rowIdx * modelRowToLogicalY + paddingY);
    g2d.drawString(value, colIdx * modelColToLogicalX + paddingX,
        rowIdx * modelRowToLogicalY + paddingY * 2);
  }

  private void getOwnerColor(Graphics2D g2d, Player player) {
    Color red = new Color(255, 112, 112);
    Color blue = new Color(125, 160, 255);
    if (player == Player.RED) {
      g2d.setColor(red);
    } else {
      g2d.setColor(blue);
    }
  }

}
