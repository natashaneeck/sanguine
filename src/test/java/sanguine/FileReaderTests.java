package sanguine;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sanguine.controller.ConfigFileParser;
import sanguine.model.Card;
import sanguine.model.InfluenceType;

/**
 * Tests for the creation of card decks from deck configuration files.
 * Also tests public observers for SanguineCard.
 */
public class FileReaderTests {

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFile() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck("docs" + File.separator
        + "path-to-test-file");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyFile() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "Empty.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardWithInvalidName() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid5.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardWithInvalidCost() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid6.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardWithTooHighCost() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid7.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCardWithInvalidValue() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid8.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileWithInvalidInfluenceGridSize() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileWithInvalidInfluenceType() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid2.deck");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFileWithInvalidCardPosition() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid3.deck");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testFileWithMultipleCardPositions() {
    //also tests case where invalid card is not first in config file.
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "exampleInvalid4.deck");
  }

  @Test
  public void testValidFile() {
    ConfigFileParser parser = new ConfigFileParser();
    List<Card> deck = parser.parseDeck(
        "docs" + File.separator + "example.deck");
    assertEquals(15, deck.size());
    assertEquals("Security", deck.getFirst().getName());
    assertEquals(1, deck.getFirst().getValue());
    assertEquals(1, deck.getFirst().getCost());
    List<List<InfluenceType>> influence = new ArrayList<>(List.of(
        new ArrayList<>(List.of(InfluenceType.X, InfluenceType.X, InfluenceType.X,
            InfluenceType.X, InfluenceType.X)),
        new ArrayList<>(List.of(InfluenceType.X, InfluenceType.X, InfluenceType.I,
            InfluenceType.X, InfluenceType.X)),
        new ArrayList<>(List.of(InfluenceType.X, InfluenceType.I, InfluenceType.C,
            InfluenceType.I, InfluenceType.X)),
        new ArrayList<>(List.of(InfluenceType.X, InfluenceType.X, InfluenceType.I,
            InfluenceType.X, InfluenceType.X)),
        new ArrayList<>(List.of(InfluenceType.X, InfluenceType.X, InfluenceType.X,
            InfluenceType.X, InfluenceType.X))
    ));
    assertEquals(influence, deck.getFirst().getInfluence());
  }

}
