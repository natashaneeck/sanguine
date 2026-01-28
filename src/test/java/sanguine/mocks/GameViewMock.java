package sanguine.mocks;

import sanguine.view.GameView;
import sanguine.view.PlayerActions;
import sanguine.view.ViewFeatures;

/**
 * A mock for the GameView interface that does nothing.
 */
public class GameViewMock implements GameView {
  @Override
  public void addFeatureListener(ViewFeatures feature) {

  }

  @Override
  public void addPlayerActionListener(PlayerActions listener) {

  }

  @Override
  public void display(boolean show) {

  }

  @Override
  public void repaint() {

  }
}
