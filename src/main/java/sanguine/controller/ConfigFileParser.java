package sanguine.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import sanguine.model.Card;
import sanguine.model.InfluenceType;
import sanguine.model.SanguineCard;

/**
 * A utility class that creates a deck of Cards used to play Sanguine from the given configuration
 * pile.
 */
public class ConfigFileParser {

  /**
   * Creates a deck of cards from the given configuration file.
   *
   * @param filePath the path of the given file with the card configurations.
   * @return a deck of the required cards represented as a List of Card
   * @throws IllegalArgumentException if card configuration is invalid in file, if the given
   *                                  file is empty, or if given file is not found.
   */
  public List<Card> parseDeck(String filePath) throws IllegalArgumentException {
    File file = new File(filePath);
    List<Card> cards = new ArrayList<>();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String headerLine = scanner.nextLine();
        String[] parts = headerLine.split(" ");
        if (parts.length != 3) {
          throw new IllegalArgumentException("Invalid Card configuration in file");
        }
        if (parts[0].equals(" ")) {
          throw new IllegalArgumentException("Invalid Card configuration in file");
        }
        String name = parts[0];
        int cost = Integer.parseInt(parts[1]);
        int value = Integer.parseInt(parts[2]);

        List<String> influenceLines = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
          influenceLines.add(scanner.nextLine());
        }
        try {
          cards.add(new SanguineCard(name, cost, value, createGrid(influenceLines)));
        } catch (IllegalArgumentException | NoSuchElementException e) {
          throw new IllegalArgumentException("Invalid Card configuration in file");
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("file not found");
    }
    if (cards.isEmpty()) {
      throw new IllegalArgumentException("File is empty");
    }
    return cards;
  }

  // throws IllegalArgumentException if influence contains characters other than X, I, C.
  private List<List<InfluenceType>> createGrid(List<String> influenceLines) throws
      IllegalArgumentException {
    List<List<InfluenceType>> grid = new ArrayList<>();
    for (String line : influenceLines) {  // For each line
      List<InfluenceType> row = new ArrayList<>();
      for (char c : line.toCharArray()) {  // For each character in that line
        switch (c) {
          case 'X' -> row.add(InfluenceType.X);
          case 'C' -> row.add(InfluenceType.C);
          case 'I' -> row.add(InfluenceType.I);
          default -> throw new IllegalArgumentException("Invalid Card configuration in file");
        }
      }
      grid.add(row);
    }
    return grid;
  }
}