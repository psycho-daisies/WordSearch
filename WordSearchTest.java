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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WordSearchTest {
  static final int SIZE = 14;
  public static void main(String[] args) throws IOException {
    intro();
    menu();
  }

  // user can choose to create a new word search
  // from their list of words or the supplied list
  public static void menu() throws IOException {
    List<String> words = new ArrayList<String>();
    Scanner input = new Scanner(System.in);
    String choice;
    menuSelection();
    System.out.print("Enter a choice:  ");
    do { // START OF MENU DO WHILE LOOP
      choice = input.next().toUpperCase();
      switch (choice) {
        case "1" -> words = addToList(input); // Add words to your word list
        case "2" -> {
          if (words.size() > 5) {
            createWordSearch(words);
          } else {
            createWordSearch(myList());
          }
        } // Generate a new Word Search
        case "3" -> createWordSearch(myList()); // Word Search with included List of words
        case "4" -> System.out.println(words); // View your list of words
        case "Q" -> { // Quit the program
          System.out.println("Good-Bye");
        }
        default -> System.out.println("Try that again");
      }
      System.out.println("(1.) Add a list of words, (2.) Generate new WordSearch, (3.) View your list of words, (Q)uit");
    } while (!choice.equals("Q")); // END OF LOOP
  }

  // Creates a new puzzle object from WordSearch class
  // uses the various methods of WordSearch class to place words
  // and display the puzzle
  public static void createWordSearch(List<String> list) throws IOException {
    WordSearch puzzle = new WordSearch(SIZE, SIZE);
    puzzle.placeFirstWord(list);
    String nextWord = list.get(0);
    puzzle.placeCross(nextWord, list);
    puzzle.print();
    System.out.println("With remaining blanks filled:");
    puzzle.fillBlanks();
    puzzle.print();
    puzzle.displayWordList();
  }

  // User adds words to an ArrayList and returns it
  public static List<String> addToList(Scanner input) {
    List<String> words = new ArrayList<String>();
    System.out.print("Word (1 to quit)? ");
    String name = input.next();
    while (!name.equals("1")) {
      words.add(name);
      System.out.print("Word (1 to quit)? ");
      name = input.next();
    }
    System.out.println("List of words:");
    System.out.println(words);
    return words;
  }

  // adds a list of words from a text file and returns the list
  public static List<String> myList() throws IOException {
    List<String> list = new ArrayList<>();
    String path = "src/words.txt";
    Scanner input = new Scanner(new File(path));
    while (input.hasNextLine()) {
      String line = input.nextLine();
      list.add(line);
    }
    list.sort(Comparator.comparingInt(String::length));
    return list;
  }
  // instructions and menu
  public static void intro() {
    String message = """
            | W | O | R | D |   |   |   |   |   |
            |   | S | E | A | R | C | H |   |   |
            |   |   | P | R | O | G | R | A | M |""";
    System.out.println(message);
    System.out.println("Welcome to the Word Search Program");
    System.out.println("A new Word Search can be created with");
    System.out.println("The list of words included with the program");
    System.out.println("Or you can enter your own list of words.");
  }
  public static void menuSelection() {
    System.out.println("""
            (1.) Add a list of words
            (2.) Generate new WordSearch
            (3.) Generate WordSearch with included list of words
            (4.) View your list of words
            (Q)uit""");
  }


}
