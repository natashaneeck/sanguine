package sanguine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sanguine.Player;

/**
 * Represents the model of the Sanguine game, and rule-checking features.
 * The board is represented as a 2D array of CellInterface's, and the hand of a player is
 * represented as a list of cards.
 * There are two players, and their total scores are based on comparing their row scores.
 * Each player has their own deck, with hand sizes that increase as they pass. If both players
 * pass consecutively, the game ends.
 */
public class SanguineModel implements MutableModelInterface {
  CellInterface[][] grid;
  //INVARIANT: <the grid always has a positive number of rows and an odd number of columns.>
  Player turn; //INVARIANT: <the player whose turn it is, is never null.>
  List<Card> redDeck;
  List<Card> blueDeck;
  int redHandSize;
  int blueHandSize;
  boolean redPass;
  boolean bluePass;
  List<ModelStatusListener> listeners;
  boolean hasStarted;

  /**
   * Initializes the game with the Red Player starting first. Ensures both decks have enough
   * Cards to fill every cell on the board. Also validates the board size, ensuring rows are
   * greater than 0, and columns are odd integers greater than 1.
   *
   * @param rows             the number of rows
   * @param cols             the number of columns
   * @param redDeck          the red team's deck
   * @param blueDeck         the blue team's deck
   * @param startingHandSize the maximum number of Cards a Player can see
   * @param shuffle          whether the decks should be shuffled
   */
  public SanguineModel(int rows, int cols, List<Card> redDeck, List<Card> blueDeck,
                       int startingHandSize, boolean shuffle) {
    this.turn = Player.RED;
    this.hasStarted = false;

    this.validDeck(redDeck);
    this.redDeck = new ArrayList<>(redDeck);
    this.validDeck(blueDeck);
    this.blueDeck = new ArrayList<>(blueDeck);

    if (shuffle) {
      Collections.shuffle(this.redDeck);
      Collections.shuffle(this.blueDeck);
    }

    this.validateDeckSize(this.redDeck, rows, cols);
    this.validateDeckSize(this.blueDeck, rows, cols);
    this.validateBoardSize(rows, cols);

    if (startingHandSize > (this.redDeck.size() / 3)
        || startingHandSize > (this.blueDeck.size() / 3)) {
      throw new IllegalArgumentException("starting hand size is too large");
    } else {
      this.redHandSize = startingHandSize;
      this.blueHandSize = startingHandSize;
    }
    this.grid = this.setUpBoard(rows, cols);
    this.listeners = new ArrayList<>();
  }

  @Override
  public void startGame() throws IllegalStateException {
    if (hasStarted) {
      throw new IllegalStateException("Game has started already");
    }
    this.hasStarted = true;
    for (ModelStatusListener listener : this.listeners) {
      listener.onTurnChanged(this.turn);
    }
  }

  private void validDeck(List<Card> deck) {
    for (int currCard = 0; currCard < deck.size(); currCard++) {
      Card currentCard = deck.get(currCard);
      int count = 0;
      for (Card card : deck) {
        if (card.equals(currentCard)) {
          count++;
        }
      }
      if (count > 2) {
        throw new IllegalArgumentException("Invalid deck");
      }
    }
  }

  /**
   * Sets up the board array to contain Cells, with the first and last columns of the board
   * initialized with the correct Player influencing. Red is on the left, Blue is on the right.
   *
   * @param rows the number of rows of the board
   * @param cols the number of columns of the board
   * @return the board with the correct state to start the game in
   */
  private CellInterface[][] setUpBoard(int rows, int cols) {
    CellInterface[][] board = new Cell[rows][cols];
    for (CellInterface[] row : board) {
      row[0] = new Cell(Player.RED);
      for (int index = 1; index < cols - 1; index++) {
        row[index] = new Cell();
      }
      row[cols - 1] = new Cell(Player.BLUE);
    }

    return board;
  }

  /**
   * Gets a list of the Cards playable by the given Player. If list is empty, returns an empty list.
   *
   * @param player the user to get the hand of
   * @return the list of accessible Cards, may be empty
   * @throws IllegalArgumentException if Player is null
   */
  @Override
  public List<Card> getHand(Player player) throws IllegalArgumentException {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    return switch (player) {
      case RED -> redDeck.subList(0, Math.min(this.redHandSize, this.redDeck.size()));
      case BLUE -> blueDeck.subList(0, Math.min(this.blueHandSize, this.blueDeck.size()));
      case null -> throw new IllegalArgumentException("Player must not be null");
    };
  }

  /**
   * Gets the total score of the given Player when the game is over.
   * The player with the higher row-score adds their row-score to their total score.
   * The player with the lower row-score gains zero points for their total score.
   * If the row-scores are the same for both players, neither player gains points for that row.
   *
   * @param player the Player whose score to count
   * @return the player's score
   * @throws IllegalArgumentException if Player is null
   */
  @Override
  public int getTotalScore(Player player) throws IllegalArgumentException {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    int playerTotal = 0;
    if (player == null) {
      throw new IllegalArgumentException("Player must not be null");
    }
    Player other;

    if (player == Player.RED) {
      other = Player.BLUE;
    } else {
      other = Player.RED;
    }

    for (int index = 0; index < this.grid.length; index++) {
      int playerRowScore = this.getRowScore(player, index);
      int otherRowScore = this.getRowScore(other, index);

      if (playerRowScore > otherRowScore) {
        playerTotal += playerRowScore;
      }
    }

    return playerTotal;
  }

  /**
   * Gets the Player's score in a specified row. Score is the sum of the
   * Player's Card values.
   *
   * @param player the Player whose score to count
   * @param row    the row to count the score in
   * @return the player's score
   * @throws IllegalArgumentException if Player is null or row is invalid
   */
  @Override
  public int getRowScore(Player player, int row) throws IllegalArgumentException {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    int score = 0;
    if (player == null) {
      throw new IllegalArgumentException("Player must not be null");
    } else if (row < 0 || row >= this.grid.length) {
      throw new IllegalArgumentException("Invalid row number");
    }

    for (CellInterface cell : this.grid[row]) {
      if (cell.hasCard() && cell.getOwner() == player) {
        score += cell.getValue();
      }
    }
    return score;
  }

  /**
   * Plays the given Card at the specified 0-indexed Cell. And changes the turn to the other player
   * at the end of the play, and removes the card played from their deck.
   *
   * @param card the Card to play
   * @param row  the row to play the Card at
   * @param col  the column to play the Card at
   * @throws IllegalArgumentException if the given row or column is invalid or the given card is
   *                                  null
   * @throws IllegalStateException    if there is already a card in the given cell, if the given
   *                                  cell belongs to the other player, if there are not enough
   *                                  pawns in the cell.
   */
  @Override
  public void playCard(Card card, int row, int col) throws IllegalArgumentException,
      IllegalStateException {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (row < 0 || row >= this.grid.length || col < 0 || col >= this.grid[0].length) {
      throw new IllegalArgumentException("Invalid row or column");
    }
    if (card == null) {
      throw new IllegalArgumentException("card cannot be null");
    }
    if (this.isValidMove(card, row, col)) {
      CellInterface cell = this.grid[row][col];
      cell.placeCard(card);
      for (int rowNum = 0; rowNum < this.grid.length; rowNum++) {
        for (int colNum = 0; colNum < this.grid[0].length; colNum++) {
          this.influence(card, rowNum, colNum, row, col);
        }
      }
      if (this.turn == Player.RED) {
        redPass = false;
        this.redDeck.remove(card);
      } else {
        bluePass = false;
        this.blueDeck.remove(card);
      }
      this.changeTurn();
    } else {
      throw new IllegalStateException("Invalid move");
    }
  }


  private void influence(Card card, int boardRow, int boardCol, int cardRow, int cardCol) {
    List<List<InfluenceType>> influence = card.getInfluence();
    int influenceRow = boardRow - cardRow + 2;
    int influenceCol = boardCol - cardCol + 2;
    if (influenceRow >= 0 && influenceRow < 5 && influenceCol >= 0 && influenceCol < 5) {
      if (this.turn == Player.BLUE) {
        influenceCol = 4 - influenceCol;
      }
      if (influence.get(influenceRow).get(influenceCol) == InfluenceType.I) {
        CellInterface cell = grid[boardRow][boardCol];
        cell.influence(this.turn);
      }
    }
  }

  @Override
  public boolean isValidMove(Card card, int row, int col) {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    CellInterface cell = null;
    try {
      cell = this.grid[row][col];
    } catch (ArrayIndexOutOfBoundsException e) {
      return false;
    }
    return (!cell.hasCard()) && (cell.getOwner() == this.turn)
        && (card.getCost() <= cell.getPawnAmt().getNumPawns());
  }

  @Override
  public Player getPlayer() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    return this.turn;
  }

  /**
   * Determines if the game has been completed, which means both Players pass.
   *
   * @return true if game over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    return (redPass && bluePass);
  }

  /**
   * Changes the Player whose turn it is.
   */
  private void changeTurn() {
    if (this.turn == Player.RED) {
      this.turn = Player.BLUE;
    } else {
      this.turn = Player.RED;
    }
    for (ModelStatusListener listener : this.listeners) {
      listener.onTurnChanged(this.turn);
    }
  }

  /**
   * Determines the winner of the game.
   *
   * @return a Player if there is a winner, or null if tied.
   * @throws IllegalStateException if game is not over.
   */
  @Override
  public Player getWinner() throws IllegalStateException {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (!this.isGameOver()) {
      throw new IllegalStateException("Game not over");
    }
    if (this.getTotalScore(Player.RED) > this.getTotalScore(Player.BLUE)) {
      return Player.RED;
    } else if (this.getTotalScore(Player.BLUE) > this.getTotalScore(Player.RED)) {
      return Player.BLUE;
    } else {
      return null;
    }
  }

  /**
   * Gets the current state of the game as a deep copy.
   *
   * @return the board game
   */
  @Override
  public CellInterface[][] getBoard() {
    CellInterface[][] copy = new CellInterface[this.grid.length][this.grid[0].length];
    for (int row = 0; row < this.grid.length; row++) {
      for (int col = 0; col < this.grid[0].length; col++) {
        copy[row][col] = new Cell(this.grid[row][col]);
      }
    }

    return copy;
  }

  @Override
  public int getNumRows() {
    return this.grid.length;
  }

  @Override
  public int getNumCols() {
    return this.grid[0].length;
  }

  @Override
  public void pass() {
    if (!hasStarted) {
      throw new IllegalStateException("Game has not started");
    }

    if (this.turn == Player.RED) {
      this.redPass = true;
      this.redHandSize += 1;
      this.changeTurn();
    } else {
      this.bluePass = true;
      this.blueHandSize += 1;
      this.changeTurn();
    }
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    this.listeners.add(listener);
  }

  /**
   * Ensures both decks have enough Cards to fill every cell on the board.
   *
   * @param deck the list of cards in the deck
   * @param rows the number of rows
   * @param cols the number of columns
   * @throws IllegalArgumentException if validation fails
   */
  private void validateDeckSize(List<Card> deck, int rows, int cols)
      throws IllegalArgumentException {
    if (deck.size() < rows * cols) {
      throw new IllegalArgumentException("Deck is too small, cannot fill all cells on board");
    }
  }

  /**
   * Validates the size of the board, checking the number of rows is greater than 0, and columns
   * are an odd integer greater than 1.
   *
   * @param rows the number of rows on the board
   * @param cols the number of columns on the board
   * @throws IllegalArgumentException if validation fails
   */
  private void validateBoardSize(int rows, int cols) throws IllegalArgumentException {
    if (rows < 1) {
      throw new IllegalArgumentException("Number of rows must be greater than 0");
    } else if (cols <= 1 || cols % 2 == 0) {
      throw new IllegalArgumentException("Number of columns must be an odd integer greater than 1");
    }
  }
}