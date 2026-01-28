package sanguine.mocks;

import java.util.List;
import sanguine.Player;
import sanguine.model.ReadOnlyModelInterface;
import sanguine.strategies.Move;
import sanguine.strategies.Strategy;

/**
 * always returns an empty list of moves to represent a situation with no good moves possible.
 */
public class NoGoodMoveMock implements Strategy {

  @Override
  public List<Move> getBestMove(ReadOnlyModelInterface model, Player player) {
    return List.of();
  }
}
