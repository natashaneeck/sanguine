package sanguine.view;

/**
 * A Panel in the game that contains feature objects and displays content.
 */
public interface GamePanel {

  /**
   * adds the given feature object to this panel, binding the feature as the observer of this panel
   * subject.
   *
   * @param feature the observer to bind to the listeners and this panel.
   */
  void addFeatureListener(ViewFeatures feature);

  /**
   * subscribes the given listener to this panel so that it
   * is notified when the player takes certain actions in this panel.
   *
   * @param listener the listener being subscribed to the view.
   */
  void addPlayerActionListener(PlayerActions listener);
}
