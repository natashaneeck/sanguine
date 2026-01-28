package sanguine;

import java.util.ArrayList;
import java.util.List;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.model.MutableModelInterface;
import sanguine.model.SanguineModel;
import sanguine.view.SanguineTextualView;
import sanguine.view.TextualView;

/**
 * Main method for a textual version of Sanguine, runs the game in the console.
 */
public class SanguineStorm {

  /**
   * The main method that runs the game. Automatically has both players placing cards.
   *
   * @param args the specifications passed in, with args[0] as the deck filepath
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Needs arguments for filepath");
    }

    List<Card> deck = new ArrayList<>();
    try {
      ConfigFileParser parser = new ConfigFileParser();
      deck = parser.parseDeck(args[0]);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }

    MutableModelInterface model = new SanguineModel(3, 5, deck,
        deck, 5, false);
    TextualView view = new SanguineTextualView(model);

    Player player = Player.RED;
    System.out.println(view.renderBoard());

    while (!model.isGameOver()) {

      SanguineStorm.playTurn(model, player);

      if (player == Player.RED) {
        player = Player.BLUE;
      } else {
        player = Player.RED;
      }

      System.out.println(view.renderBoard());
    }
  }

  /**
   * An automatic user that iteratively does the first possible move it finds on the board.
   *
   * @param model         the Sanguine game
   * @param currentPlayer whether this user is playing as red or blue
   */
  public static void playTurn(MutableModelInterface model, Player currentPlayer) {
    int rows = model.getBoard().length;
    int cols = model.getBoard()[0].length;

    List<Card> hand = model.getHand(currentPlayer);
    for (Card card : hand) {
      for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
        for (int colIndex = 0; colIndex < cols; colIndex++) {
          try {
            model.playCard(card, rowIndex, colIndex);
            return;
          } catch (IllegalArgumentException | IllegalStateException e) {
            continue;
          }
        }
      }
    }
    model.pass();
  }
}