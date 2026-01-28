package sanguine.model;

/**
 * The cost of a card in terms of pawns. A card can cost either one, two, or three pawns.
 */
public enum Cost {
  ONE(1), TWO(2), THREE(3);

  private final int cost;

  Cost(int cost) {
    this.cost = cost;
  }

  /**
   * returns the integer value of this cost.
   *
   * @return the value of this cost.
   */
  public int getValue() {
    return cost;
  }
}
