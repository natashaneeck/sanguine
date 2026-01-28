package sanguine.model;

/**
 * A representation of each position in the influence grid of a card. 'X' means there is no
 * influence,'I' means the card has influence at this position, and 'C' represents
 * the position of the card itself.
 */
public enum InfluenceType {
  X('X'),
  I('I'),
  C('C');
  private final char type;

  InfluenceType(char type) {
    this.type = type;
  }

  /**
   * Gets the character representation of this InfluenceType.
   *
   * @return the character
   */
  public char getType() {
    return type;
  }
}
