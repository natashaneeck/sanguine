package sanguine.view;

/**
 * Contains all methods the user needs to interact with the view and model through the controller.
 */
public interface ViewFeatures {

  /**
   * Quits the program.
   */
  void quit();

  /**
   * Prints out location when a user clicks on the game grid. (Not 0-indexed)
   *
   * @param posX the index where the mouse was clicked
   * @param posY the index of the click
   */
  void printCellCoordinates(int posX, int posY);

  /**
   * Prints out location and player when a user clicks on the current player's hand.
   * (Not 0-indexed)
   *
   * @param posX the index where the mouse was clicked
   */
  void printHandCoordinates(int posX);

  /**
   * Prints "player passed" if user pressed p on the keyboard.
   */
  void printPass();

  /**
   * Prints "move confirmed" if the user pressed enter on the keyboard.
   */
  void printConfirm();
}
