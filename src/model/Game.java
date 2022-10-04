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

    public boolean isOccupied(int row, int column) {
        boolean occupied = false;
        if (row == 0) {
            if (!row1.get(column).equalsIgnoreCase(" ")) {
                occupied = true;
            }
        } else if (row == 1) {
            if (!row2.get(column).equalsIgnoreCase(" ")) {
                occupied = true;
            }
        } else if (row == 2) {
            if (!row3.get(column).equalsIgnoreCase(" ")) {
                occupied = true;
            }
        }
        return occupied;
    }

    public int getRowIndex(String mark) {
        String input;
        boolean chosenRow = false;
        int rowIndex = -1;
        while (!chosenRow) {
            System.out.println("Choose row to set your '" + mark + "'?");
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
                default -> System.out.println("Not a valid input. Please choose a number between 1 - 3.");
            }
        }
        return rowIndex;
    }

    public int getColumnIndex(String mark) {
        String input;
        boolean chosenColumn = false;
        int columnIndex = -1;
        while (!chosenColumn) {
            System.out.println("Choose column to set your '" + mark + "'?");
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
                default -> System.out.println("Not a valid input. Please choose a number between 1 - 3.");
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
        boolean validTurn = false;
        int rowIndex = -1;
        int columnIndex = -1;
        while (!validTurn) {
            rowIndex = getRowIndex(mark);
            columnIndex = getColumnIndex(mark);
            if (!isOccupied(rowIndex, columnIndex)) {
                validTurn = true;
            } else {
                System.out.println("ERROR! Space is occupied.");
                getBoard();
            }
        }


        gameBoard.get(rowIndex).set(columnIndex,mark);

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
