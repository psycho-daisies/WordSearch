// Class: CS 145
// Assignment: Word Search
// Authors: Troy Brunette
//
// Word Search program generates a 2D grid of Words from a list of words.
// The user can add the words to a list or use a supplied list of words.
// Each word is checked if it can fit on the grid in the specified
// row and column, and checked in all directions to loop for overlaps with
// other words on the grid. If there is an overlap,
// and it's within the bounds it can be placed.

import java.io.IOException;
import java.util.*;

public class WordSearch {
  private char[][] grid;
  private List<String> placedWords;
  private int rows;
  private int columns;

  // N, NE, E, SE, S, SW, W, NW
  private static final int[][] directions =
          {{0, 1}, {1, 1}, {1, 0}, {1, -1},
          {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};


  // Constructor
  public WordSearch(int rows, int cols) throws IOException {
    grid = new char[rows][cols];
    placedWords = new ArrayList<>();
    this.rows = rows;
    this.columns = cols;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j] = '-';
      }
    }
  }
  // prints the word search
  public void print() {
    System.out.println("Grid:");
    for (char[] chars : grid) {
      for (char aChar : chars) {
        System.out.print(" " + aChar + " ");
      }
      System.out.println();
    }
  }

  // fills the rest of the grid with random characters
  void fillBlanks() {
    Random ran = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (grid[i][j] == '-') {
          char c = (char) ran.nextInt('A', 'Z');
          grid[i][j] = c;
        }
      }
    }
  }
  // places each letter of a word along a path
  // starting row / column
  // length of word
  // dx and dy represent direction and move to place each letter
  private void placeWord(String word, int row, int col, int dx, int dy) {
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      grid[row + dx * i][col + dy * i] = c;
    }
  }

  // places the longest word in the middle and diagonal
  void placeFirstWord(List<String> list) {
    String largestWord = largestWord(list);
    int row = grid.length - ((grid.length - largestWord.length()));
    int col = (grid.length - largestWord.length()) / 2;
    placeWord(largestWord, row + 1, col, -1, 1);
    placedWords.add(largestWord);
  }

  // searches for the longest word and returns it
  private String largestWord(List<String> list) {
    String largestWord = "";
    for (String word : list) {
      if (word.length() > largestWord.length()) {
        largestWord = word;
      }
    }
    return largestWord;
  }

  // check if the current word goes out of bounds for its position and direction
  private boolean checkBounds(String word, int row, int col, int dx, int dy) {
    return (row + dx * word.length() <= grid.length && col + dy * word.length() <= grid[row].length &&
            row + dx * word.length() >= 0 && col + dy * word.length() >= 0);
  }
  // check for character matches for the current word, position, and direction
  private boolean checkForLetters(String word, int row, int col, int dx, int dy) {
    // check if the grid location along the path is empty or if it contains the same letter
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (grid[row + dx * i][col + dy * i] != '-' && grid[row + dx * i][col + dy * i] != c) {
        return false;
      }
    }
    // check if characters match
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      if (grid[row + dx * i][col + dy * i] == c) {
        return true;
      }
    }
    return false;
  }

  // check placements for each word in all directions
  // place word if in bounds and has an overlap
  void placeCross(String word, List<String> list) {
    int count = 0; // number of tries
    int overlap = 0; // number of overlaps between words
    boolean placed = false;
    // try to place the word up to 20 times
    while (count <= 20 && overlap == 0 && !placed) {
      count++;
      // randomly select a grid position
      int row = (int) ((Math.random()) * (grid.length - 1));
      int col = (int) ((Math.random()) * (grid.length - 1));
      // check each direction
      for (int move = 0; move < directions.length; move++) {
        int dx = directions[move][0];
        int dy = directions[move][1];
        // check bounds and letter match
        if (checkBounds(word, row, col, dx, dy) && checkForLetters(word, row, col, dx, dy)) {
          overlap++;
          if (overlap >= 1 || placedWords.size() <= 10) {
            placeWord(word, row, col, dx, dy);
            placedWords.add(word);
            placed = true;
            break;
          }
        }
      }
    }
    // use recursive method to place the next word
    if (list.size() > 0) {
      String nextWord = list.get(0);
      list.remove(0);
      placeCross(nextWord, list);
    }
  }

  // display the words in the current puzzle
  public void displayWordList() {
    for (int j = 0, i = 1; j < (placedWords.size() / 2) ; j++, i++) {
      int size = ((placedWords.size() / 2 ) + j);
      String word = i + ". " + placedWords.get(j);
      String word2 = size + 1 + ". " +  placedWords.get((placedWords.size() / 2) + j);
      String str = String.format("%1$-20s %2$s", word, word2);
      System.out.println(str);
    }
  }

}
