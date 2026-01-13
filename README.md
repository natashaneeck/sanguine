# Assignment 7

Changes for part 3 from part 2:
- model now takes a listener to notify upon changes to game state with corresponding addListener methods. This allows
  us to subscribe the controller to the model to enable turn validation, etc. 
- added a startGame method to the model which must be called for game play. 
- view takes an additional playerActionsListener to notify when a human player interacts with the view. This allows us
  to subscribe the controller to the view so that player actions are registered and accordingly carried out in the 
  model.
- in the view's panels, we removed the keylistener from the abstract class to the concrete classes, as the key event
  now toggles the highlighting functionality in different ways.

Keys for passing and confirming:
Press enter to confirm move, and p to pass.

Note that Card is immutable because all fields are final 
and no public methods can change anything about it, so the
ReadOnlyModelInterface does not return anything that can mutate
the game state.

Our model uses a 0-indexed coordinate system.

Changes for part 2 from part 1:
Added observers for board size into ReadOnlyModelInterface. 
Even though other MVC components could determine the size based on calling .size() or .length from
model.getBoard(), we decided that they should not be responsible for determining that, and the
model should be the one to know how to find big the board is.
Also added a player observer, and made isValidMove public.


# ReadMe

## **Overview**

This codebase implements Sanguine, a 2-player card game played on a rectangular board. Players place cards with unique
influence patterns and gain territory on the board via pawns to score points. This implementation provides a functional
model with rule enforcement, file-based deck configuration, GUI view, and a basic textual rendering of the game board.
The system follows MVC architecture and is designed to support future UI controller developments and possible 
integration of AI players.

## **Quick Start**

        //For TextualView:
        // initialize model with 3x5 board 
        List<Card> exampleBigDeck = new ConfigFileParser().parseDeck("docs"
            + File.separator + "exampleBig.deck");
        MutableModelInterface model  = new SanguineModel(3, 5, exampleBigDeck, exampleBigDeck, 5, false);
        // play cards 
        this.inProgressModel.playCard(this.inProgressModel.getHand(Player.RED).get(4), 2, 0);
        this.inProgressModel.playCard(this.inProgressModel.getHand(Player.BLUE).get(1), 0, 4);
        this.inProgressModel.playCard(this.inProgressModel.getHand(Player.RED).get(2), 0, 0);
        // get the visual rendering
        TextualView view = new SanguineTextualView(model);
        System.out.println(view.toString());

        //or for GUI window:
        List<Card> exampleBigDeck = new ConfigFileParser().parseDeck("docs"
        + File.separator + "exampleBig.deck");

        MutableModelInterface model = new SanguineModel(5, 7,
            exampleBigDeck, exampleBigDeck, 5, false);
        GameView view = new SanguineView((ReadOnlyModelInterface) model);
        ControllerInterface controller = new SanguineController(view, model);

        view.display(true);
        model.playCard(model.getHand(Player.RED).getFirst(), 1, 0);
        view.repaint();
        model.playCard(model.getHand(Player.BLUE).getFirst(), 1, 6);
        view.repaint();

## **Key Components**
1. Model: The class SanguineModel is an implementation of the MutableModelInterface with orchestrates all game logic 
   and rule enforcement. It drives the game flow by validating moves, applying cards' influence patterns and managing
   the game state.
2. Textual View: The class SanguineTextualView is an implementation of the TextualView interface. It is a simple string
   representation of the game. It responds to the model state by rendering the current game board. Our
   TextualView representation is mostly the same as the given representation, but we added some spaces
   and separators (||) for the edges of the board like in the example below:

            given:
            2 RR__3 0
            0 2__1B 1
            0 11_1B 3 
            ours:
            2 || R R _ _ 3 || 0
            0 || 2 _ _ 1 B || 1
            0 || 1 1 _ 1 B || 3

3. GUI View: The class SanguineView is an implementation of the GameView interface. It displays the board state and 
   player hand in windows with panels and allows players to highlight cells and cards, and use the keyboard to 
   confirm moves or pass.
4. Deck Configuration System: The ConfigFileParser class creates the card decks required for gameplay from config.
   files when requested during game initialization.


## **Key Subcomponents**
1. SanguineModel: Represents the model of the Sanguine game. Players play cards on a rectangular game board to
   gain more territory, and earn points. The game ends when both players pass, and the player with
   more points in each row wins.
2. ConfigFileParser: A utility class that creates a deck of Cards used to play Sanguine from the given configuration
   pile.
3. SanguineCard: A card used to play Sanguine. Cards are created from a configuration file.
   Cards have a name, cost, point-value and unique influence grid.
4. Cell: Represents the squares of the grids, which keep track of the player influencing them, the
   number of pawns in it, and whether it has a card in it. Players can gain control of the cell
   and add pawns to it with their cards.
5. InfluenceType: An enum representating each position in the influence grid of a card. 'X' means there is no
   influence,'I' means the card has influence at this position, and 'C' represents the position of the card itself.
6. PawnAmount: An enum representing the number of pawns possible in a cell- 'ONE', 'TWO', 'THREE'
7. Player: An enum representing the players of the game- 'RED', 'BLUE'.
8. SanguineStorm: Main method for textual Sanguine, runs the game.
9. SanguineTextualView: Contains the necessary methods for making the game visible to the Players all throughout the
   gameplay of Sanguine. This implementation uses a textual representation for the game as a String.
10. SanguineView: Contains the necessary methods for making the game visible to the players throughout the gameplay. 
    Also allows the player to interact with the game and updates the visuals accordingly
11. PlayerInterface: An interface describing the actions a player-entity could take when playing Sanguine. Used to 
    represent different player implementations like human players or machine players. 
12. SanguineController: Contains the necessary methods for linking the view to the model. takes user interactions with 
    the view and makes according changes to the model's game state. 

## **Source Organization**

    src/
    ├── main/
    │   └── java/
    │       ├── player/
    │       │   ├── HumanPlayer.java
    │       │   ├── MachinePlayer.java
    │       │   └── PlayerInterface.java
    │       ├── sanguine/
    │       │   ├── controller/
    │       │   │   ├── ConfigFileParser.java
    │       │   │   ├── ControllerInterface.java
    │       │   │   └── SanguineController.java
    │       │   ├── model/
    │       │   │   ├── Card.java
    │       │   │   ├── Cell.java
    │       │   │   ├── CellInterface.java
    │       │   │   ├── Cost.java (enum)
    │       │   │   ├── InfluenceType.java (enum)
    │       │   │   ├── ModelStatusListener.java
    │       │   │   ├── MutableModelInterface.java
    │       │   │   ├── PawnAmount.java (enum)
    │       │   │   ├── ReadOnlyModelInterface.java
    │       │   │   ├── SanguineCard.java
    │       │   │   └── SanguineModel.java
    │       │   ├── strategies/
    │       │   │   ├── FillFirstStrategy.java
    │       │   │   ├── MaxRowScoreStrategy.java
    │       │   │   ├── Move.java
    │       │   │   └── Strategy.java
    │       │   └── view/
    │       │       ├── AbstractPanel.java
    │       │       ├── GamePanel.java
    │       │       ├── GameView.java
    │       │       ├── PlayerActions.java
    │       │       ├── SanguineBoardPanel.java
    │       │       ├── SanguineHandPanel.java
    │       │       ├── SanguineTextualView.java
    │       │       ├── SanguineView.java
    │       │       ├── TextualView.java
    │       │       └── ViewFeatures.java
    │       ├── Player.java (enum)
    │       ├── SanguineGame.java
    │       └── SanguineStorm.java
    docs/                       # Configuration files
    └── *.deck                      # Card deck configuration files 
    images/                     # Screenshots to substitute for GUI view tests
    test/java/sanguine          # Unit tests and mocks
    ├── mocks/                     # Mock implementations for unit testing
    └── *Tests.java                # Test suites for model, file reader, Cell, view, strategies, moves, controller

**Package Overview**
1. player/ - Player implementations and interfaces for human and AI players 
2. sanguine/controller/ - Game controller logic and configuration parsing 
3. sanguine/model/ - Core game state, cards, board cells, and business logic 
4. sanguine/strategies/ - AI strategy implementations for machine players 
5. sanguine/view/ - GUI components and textual view implementations 
6. Root classes - Main game launcher and entry points
