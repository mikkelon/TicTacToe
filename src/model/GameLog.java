package model;

import java.util.ArrayList;

public class GameLog {
    private ArrayList<String> log = new ArrayList<>();

    // LOG CODES
    public final int LOG_PLAYER_STARTS = 0;
    public final int LOG_PLAYER_TURN = 1;
    public final int LOG_TURN_SUCCESS = 2;
    public final int LOG_TURN_OCCUPIED = 3;
    public final int LOG_PLAYER_WON = 4;
    public final int LOG_DRAW = 5;

    public GameLog() {

    }

    public ArrayList<String> getLog() {
        return this.log;
    }

    public void addLog(int logCode) {
        String logOutput = "";
        switch (logCode) {
            case 3 -> logOutput = "ERROR: Space is already occupied.";
            case 5 -> logOutput = "Game is a draw.";
            default -> logOutput = "LOG ERROR!";
        }
        this.log.add(0, logOutput);
    }

    public void addLog(int logCode, Player player) {
        String logOutput = "";
        switch (logCode) {
            case 0 -> logOutput = player.getName() + " starts the game.";
            case 1 -> logOutput = player.getName() + " has the turn.";
            case 2 -> logOutput = player.getName() + " has set their '" + player.getMark() + "' on the board.";
            case 3 -> logOutput = "ERROR: Space is already occupied.";
            case 4 -> logOutput = player.getName() + " has won the game!";
            default -> logOutput = "LOG ERROR!";
        }
        this.log.add(0, logOutput);
    }

    public void clear() {
        log.clear();
    }
}
