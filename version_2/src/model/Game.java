package model;

import javafx.scene.control.Button;

public class Game {
    private final GameLog gameLog = new GameLog();
    private Player player1;
    private Player player2;
    private final Button[][] gameGrid = new Button[3][3];

    public Game() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                gameGrid[i][j] = new Button();
            }
        }
        player2 = (new Player("Computer", "O"));
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Button[][] getGameGrid() {
        return this.gameGrid;
    }

    public void swapTurn() {
        player1.setTurn(!player1.hasTurn());
        player2.setTurn(!player2.hasTurn());
        gameLog.addLog(gameLog.LOG_PLAYER_TURN, playersTurn());
    }

    public Player playersTurn() {
        Player returnPlayer;
        if (player1.hasTurn()) {
            returnPlayer = this.player1;
        } else {
            returnPlayer = this.player2;
        }
        return returnPlayer;
    }

    public void takeTurn(Button button) {
        if (button.getText().equalsIgnoreCase("")) {
            // Set mark
            button.setText(playersTurn().getMark());

            // Log
            gameLog.addLog(gameLog.LOG_TURN_SUCCESS, playersTurn());

            // Swap turn
            swapTurn();
        } else {
            gameLog.addLog(gameLog.LOG_TURN_OCCUPIED);
        }
    }

    public GameLog getGameLog() {
        return this.gameLog;
    }
}
