package sanguine.model;

import java.util.List;
import java.util.Objects;

/**
 * A card used to play Sanguine. Cards are created from a configuration file.
 * Each card has a name for its move, a cost that is the number of pawns it requires in order to
 * be played, a value that is the score a player gains with it, and a 2D list representing its
 * influence grid.
 */
public class SanguineCard implements Card {
  private final String name;
  private final Cost cost;
  private final int value;
  private final List<List<InfluenceType>> influence;

  /**
   * Constructs a Card for use in the game Sanguine, checking the validity of it based on the
   * rules of that game.
   *
   * @param name      the card's name
   * @param cost      the number of pawns needed to play it
   * @param value     the number of points it adds to the player's row score
   * @param influence the grid of changes it affects on the board
   * @throws IllegalArgumentException when value is negative, if influence is null or invalid, if
   *                                  cost is invalid
   */
  public SanguineCard(String name, int cost, int value, List<List<InfluenceType>> influence)
      throws IllegalArgumentException {
    if (value < 0) {
      throw new IllegalArgumentException("card cannot have negative value.");
    }
    if (influence == null) {
      throw new IllegalArgumentException("card cannot have null influence.");
    }
    if (influence.get(2).get(2) != InfluenceType.C) {
      throw new IllegalArgumentException("card must have C in the middle influence position");
    }
    int count = 0;
    for (List<InfluenceType> influenceTypes : influence) {
      for (InfluenceType type : influenceTypes) {
        if (type == InfluenceType.C) {
          count += 1;
        }
      }
    }
    if (count != 1) {
      throw new IllegalArgumentException("influence grid can only have one C");
    }
    switch (cost) {
      case 1 -> this.cost = Cost.ONE;
      case 2 -> this.cost = Cost.TWO;
      case 3 -> this.cost = Cost.THREE;
      default -> throw new IllegalArgumentException("invalid cost");
    }
    this.name = name;
    this.value = value;
    this.influence = influence;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getCost() {
    return this.cost.getValue();
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public List<List<InfluenceType>> getInfluence() {
    return this.influence;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Card)) {
      return false;
    }
    Card that = (Card) other;
    return this.name.equals(((SanguineCard) that).name)
        && this.value == ((SanguineCard) that).value
        && this.cost == ((SanguineCard) that).cost
        && this.influence.equals(((SanguineCard) that).influence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.value, this.cost, this.influence);
  }
}
