package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    // ATTRIBUTES
    private ArrayList<ArrayList<String>> gameBoard = new ArrayList<ArrayList<String>>();
    private ArrayList<String> row1 = new ArrayList<>();
    private ArrayList<String> row2 = new ArrayList<>();
    private ArrayList<String> row3 = new ArrayList<>();
    private Scanner scanner;
    private boolean player1Turn = true;

    // CONSTRUCTORS
    public Game() {
        // Set rows to gameBoard
        gameBoard.add(0, row1);
        gameBoard.add(1, row2);
        gameBoard.add(2, row3);

        // Fill rows with spaces
        row1.add(0, " ");
        row1.add(1, " ");
        row1.add(2, " ");

        row2.add(0, " ");
        row2.add(1, " ");
        row2.add(2, " ");

        row3.add(0, " ");
        row3.add(1, " ");
        row3.add(2, " ");

        // Construct Scanner
        scanner = new Scanner(System.in);
    }

    public void getBoard() {
        System.out.println("+---+---+---+");
        System.out.println("| " + row1.get(0) + " | " + row1.get(1) + " | " + row1.get(2) + " |");
        System.out.println("+---+---+---+");
        System.out.println("| " + row2.get(0) + " | " + row2.get(1) + " | " + row2.get(2) + " |");
        System.out.println("+---+---+---+");
        System.out.println("| " + row3.get(0) + " | " + row3.get(1) + " | " + row3.get(2) + " |");
        System.out.println("+---+---+---+");
    }

    public void setMark(ArrayList<String> row, int index, String mark) {
        row.set(index, mark);
    }

    public void checkOccupiedSpaced() {

    }

    public int getRowIndex(String mark) {
        String input;
        boolean chosenRow = false;
        int rowIndex = -1;
        while (!chosenRow) {
            System.out.println("På hvilken række, vil du sætte dit '" + mark + "'?");
            input = scanner.nextLine();
            switch (Integer.parseInt(input)) {
                case 1 -> {
                    rowIndex = 0;
                    chosenRow = true;
                }
                case 2 -> {
                    rowIndex = 1;
                    chosenRow = true;
                }
                case 3 -> {
                    rowIndex = 2;
                    chosenRow = true;
                }
                default -> System.out.println("Ikke gyldigt input. Vælg en række mellem 1 - 3.");
            }
        }
        return rowIndex;
    }

    public int getColumnIndex(String mark) {
        String input;
        boolean chosenColumn = false;
        int columnIndex = -1;
        while (!chosenColumn) {
            System.out.println("I hvilken kolonne, vil du sætte dit '" + mark + "'?");
            input = scanner.nextLine();
            switch (Integer.parseInt(input)) {
                case 1 -> {
                    columnIndex = 0;
                    chosenColumn = true;
                }
                case 2 -> {
                    columnIndex = 1;
                    chosenColumn = true;
                }
                case 3 -> {
                    columnIndex = 2;
                    chosenColumn = true;
                }
                default -> System.out.println("Ikke gyldigt input. Vælg en række mellem 1 - 3.");
            }
        }
        return columnIndex;
    }

    public void takeTurn() {
        // Set player turn
        String mark;
        if (player1Turn) {
            mark = "X";
        } else {
            mark = "O";
        }

        // Ask and set mark
        int rowIndex = getRowIndex(mark);
        int columnIndex = getColumnIndex(mark);
        gameBoard.get(rowIndex).set(columnIndex,mark); // TODO: Vælg kolonne index

        // Swap player turn
        player1Turn = !player1Turn;
        }

    public void startGame() {
        boolean finished = false;
        // Show empty gameBoard
        getBoard();

        // Start game loop
        while (!finished) {
            takeTurn();
            getBoard();
        }

    }

    // Temporary getters for testing
    public ArrayList<String> getRow1() {
        return row1;
    }

    public ArrayList<String> getRow2() {
        return row2;
    }

    public ArrayList<String> getRow3() {
        return row3;
    }

} // End of class
