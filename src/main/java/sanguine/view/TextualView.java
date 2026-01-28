package sanguine.view;

/**
 * Contains the necessary methods for making the game visible to the Players all throughout the
 * gameplay.
 */
public interface TextualView {

  /**
   * Returns the current board state represented as a String.
   *
   * @return the visualization of the game board
   */
  public String renderBoard();
}
