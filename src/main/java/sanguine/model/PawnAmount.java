package sanguine.model;

/**
 * Represents the number of pawns in each Cell. Empty cells have zero, and cells on the far left
 * and right column are initialized at one.
 */
public enum PawnAmount {
  ZERO(0), ONE(1), TWO(2), THREE(3);

  private final int num;

  PawnAmount(int value) {
    this.num = value;
  }

  /**
   * Returns the numerical representation of the number of pawns as an integer.
   *
   * @return the number of pawns
   */
  public int getNumPawns() {
    return this.num;
  }

  @Override
  public String toString() {
    return Integer.toString(this.num);
  }
}
