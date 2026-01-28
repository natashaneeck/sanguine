package sanguine.view;

/**
 * The visuals for a game, displays itself and its panels.
 */
public interface GameView {

  /**
   * sets the callback object for the panels to the given feature object.
   *
   * @param feature the callback object for all components in the view.
   */
  void addFeatureListener(ViewFeatures feature);

  /**
   * subscribes the given listener to this view object's panels so that it
   * is notified when the player takes certain actions in that panel.
   *
   * @param listener the listener being subscribed to the view.
   */
  void addPlayerActionListener(PlayerActions listener);

  /**
   * makes the view visible if show is true, invisible otherwise.
   *
   * @param show whether the view should be visible?
   */
  void display(boolean show);

  /**
   * Updates the view to match the current game state.
   */
  void repaint();
}
