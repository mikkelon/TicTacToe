package model;

import javafx.scene.control.Button;

public class Game {
    private final GameLog gameLog = new GameLog();
    private Player player, computer, startingPlayer;
    private final Button[][] gameGrid = new Button[3][3];
    private boolean gameOver, randomStartingPlayer;
    private int turns, drawCounts;
    //private String startingPlayer = ""; // Should either be 'computer', '<playername>' or 'random';

    public Game() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[i].length; j++) {
                gameGrid[i][j] = new Button("");
            }
        }
        this.player = (new Player("Player1", "X"));
        this.computer = (new Player("Computer", "O"));
        this.gameOver = false;
        this.turns = 0;
    }

    public int getDrawCounts() {
        return drawCounts;
    }

    public void setStartingPlayer(Player startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public void setRandomStartingPlayer(boolean randomStartingPlayer) {
        this.randomStartingPlayer = randomStartingPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setComputer(Player player) {
        this.computer = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Player getComputer() {
        return this.computer;
    }

    public Button[][] getGameGrid() {
        return this.gameGrid;
    }

    public void swapTurn() {
        player.setTurn(!player.hasTurn());
        computer.setTurn(!computer.hasTurn());
    }

    public Player playerHasTurn() {
        Player returnPlayer;
        if (player.hasTurn()) {
            returnPlayer = this.player;
        } else {
            returnPlayer = this.computer;
        }
        return returnPlayer;
    }

    public void takePlayerTurn(Button button) {
        if (button.getText().equalsIgnoreCase("")) {
            // Set mark
            button.setText(player.getMark());

            // Log
            gameLog.addLog(gameLog.LOG_TURN_SUCCESS, player);

            // Count turn
            turns++;

            // Check winning conditions
            checkWin();

            // Swap the turn
            swapTurn();

            // Log the next player's turn
            if (!gameOver) {
                gameLog.addLog(gameLog.LOG_PLAYER_TURN, playerHasTurn());
            }
        } else {
            gameLog.addLog(gameLog.LOG_TURN_OCCUPIED);
        }
    }

    public void takeComputerTurn() {
        // Priority 1 - Win the game
        computerCheckBlockOrWin(this.computer);

        // Priority 2 - Block the player from winning next round
        if (computer.hasTurn()) {
            computerCheckBlockOrWin(this.player);
        }

        // Priority 3 - Place mark in the middle cell
        if (computer.hasTurn()) {
            if (gameGrid[1][1].getText().equalsIgnoreCase("")) {
                gameGrid[1][1].setText(computer.getMark());
                gameLog.addLog(gameLog.LOG_TURN_SUCCESS, computer);
                swapTurn();
            }
        }

        // Priority 4 - Force player to block to avoid player winning
        if (computer.hasTurn()) {
            if (turns == 3 && startingPlayer == player) {
                if (gameGrid[0][0].getText().equalsIgnoreCase(player.getMark())
                        && gameGrid[2][2].getText().equalsIgnoreCase(player.getMark())) {
                    gameGrid[0][1].setText(computer.getMark());
                    swapTurn();
                } else if (gameGrid[2][0].getText().equalsIgnoreCase(player.getMark())
                        && gameGrid[0][2].getText().equalsIgnoreCase(player.getMark())) {
                    gameGrid[0][1].setText(computer.getMark());
                    swapTurn();
                }
            }
        }

        // Priority 4.5 - Block player from filling out corner next to two player filled side cells
        if (computer.hasTurn()) {
            if (turns == 3 && startingPlayer == player) {
                if (gameGrid[0][1].getText().equalsIgnoreCase(player.getMark())
                        && gameGrid[1][0].getText().equalsIgnoreCase(player.getMark())) {
                    gameGrid[0][0].setText(computer.getMark());
                    swapTurn();
                } else if (gameGrid[0][1].getText().equalsIgnoreCase(player.getMark())
                        && gameGrid[1][2].getText().equalsIgnoreCase(player.getMark())) {
                    gameGrid[0][2].setText(computer.getMark());
                    swapTurn();
                } else if (gameGrid[1][2].getText().equalsIgnoreCase(player.getMark())
                        && gameGrid[2][1].getText().equalsIgnoreCase(player.getMark())) {
                    gameGrid[2][2].setText(computer.getMark());
                    swapTurn();
                } else if (gameGrid[2][1].getText().equalsIgnoreCase(player.getMark())
                        && gameGrid[1][0].getText().equalsIgnoreCase(player.getMark())) {
                    gameGrid[2][0].setText(computer.getMark());
                    swapTurn();
                }
            }
        }

        // Priority 5 - Place mark in a corner cell
        if (computer.hasTurn()) {
            if (gameGrid[0][0].getText().equalsIgnoreCase("")) {
                gameGrid[0][0].setText(computer.getMark());
                swapTurn();
            } else if (gameGrid[0][2].getText().equalsIgnoreCase("")) {
                gameGrid[0][2].setText(computer.getMark());
                swapTurn();
            } else if (gameGrid[2][0].getText().equalsIgnoreCase("")) {
                gameGrid[2][0].setText(computer.getMark());
                swapTurn();
            } else if (gameGrid[2][2].getText().equalsIgnoreCase("")) {
                gameGrid[2][2].setText(computer.getMark());
                swapTurn();
            }
        }

        // Priority 6 - Place mark in a free cell
        if (computer.hasTurn()) {
            if (gameGrid[0][1].getText().equalsIgnoreCase("")) {
                gameGrid[0][1].setText(computer.getMark());
                swapTurn();
            } else if (gameGrid[1][2].getText().equalsIgnoreCase("")) {
                gameGrid[1][2].setText(computer.getMark());
                swapTurn();
            } else if (gameGrid[2][1].getText().equalsIgnoreCase("")) {
                gameGrid[2][1].setText(computer.getMark());
                swapTurn();
            } else if (gameGrid[1][0].getText().equalsIgnoreCase("")) {
                gameGrid[1][0].setText(computer.getMark());
                swapTurn();
            }
        }

        turns++;

        checkWin();

        // Log next player's turn (unless game is already over)
        if (!gameOver) {
            gameLog.addLog(gameLog.LOG_PLAYER_TURN, playerHasTurn());
        }
    }

    private void computerCheckBlockOrWin(Player player) {
        // Block if player is about to win in a row
        for (int i = 0; i < gameGrid.length; i++) {
            int count = 0;
            for (int j = 0; j < gameGrid[i].length; j++) {
                String btnMark = gameGrid[i][j].getText().trim();
                if (btnMark.equalsIgnoreCase(player.getMark())) {
                    count++;
                }
            }
            if (count == 2) {
                for (int j = 0; j < gameGrid[i].length; j++) {
                    String btnMark = gameGrid[i][j].getText().trim();
                    if (btnMark.equalsIgnoreCase("")) {
                        gameGrid[i][j].setText(computer.getMark());
                        gameLog.addLog(gameLog.LOG_TURN_SUCCESS, computer);
                        swapTurn();
                    }
                }
            }
        }

        // Block if player is about to win in a column
        if (computer.hasTurn()) { // Check to see whether or not the computer already has taken it's move
            for (int i = 0; i < gameGrid[0].length; i++) {
                int count = 0;
                for (int j = 0; j < gameGrid.length; j++) {
                    String btnMark = gameGrid[j][i].getText().trim();
                    if (btnMark.equalsIgnoreCase(player.getMark())) {
                        count++;
                    }
                }
                if (count == 2) {
                    for (int j = 0; j < gameGrid.length; j++) {
                        String btnMark = gameGrid[j][i].getText().trim();
                        if (btnMark.equalsIgnoreCase("")) {
                            gameGrid[j][i].setText(computer.getMark());
                            gameLog.addLog(gameLog.LOG_TURN_SUCCESS, computer);
                            swapTurn();
                        }
                    }
                }
            }
        }

        // Block if player is about to win diagonally
        if (computer.hasTurn()) { // Check to see whether or not the computer already has taken it's move
            int count = 0;
            for (int i = 0; i < gameGrid.length; i++) {
                if (gameGrid[i][i].getText().trim().equalsIgnoreCase(player.getMark())) {
                    count++;
                }
            }
            if (count == 2) {
                for (int i = 0; i < gameGrid.length; i++) {
                    if (gameGrid[i][i].getText().trim().equalsIgnoreCase("")) {
                        gameGrid[i][i].setText(computer.getMark());
                        gameLog.addLog(gameLog.LOG_TURN_SUCCESS, computer);
                        swapTurn();
                    }
                }
            }
        }

        // Block if player is about to win anti-diagonally
        if (computer.hasTurn()) { // Check to see whether or not the computer already has taken it's move
            int count = 0;
            int col = 2;
            for (int i = 0; i < gameGrid.length; i++) {
                if (gameGrid[i][col].getText().trim().equalsIgnoreCase(player.getMark())) {
                    count++;
                }
                col--;
            }
            if (count == 2) {
                col = 2;
                for (int i = 0; i < gameGrid.length; i++) {
                    if (gameGrid[i][col].getText().trim().equalsIgnoreCase("")) {
                        gameGrid[i][col].setText(computer.getMark());
                        gameLog.addLog(gameLog.LOG_TURN_SUCCESS, computer);
                        swapTurn();
                    }
                    col--;
                }
            }
        }

    }

    public GameLog getGameLog() {
        return this.gameLog;
    }

    public void checkWin() {
        Player winningPlayer = null;
        boolean foundWinner = false;

        String player1Mark = player.getMark();
        String player2Mark = computer.getMark();

        int playerPoints = 0;
        int computerPoints = 0;

        // Check win in rows
        for (Button[] row : gameGrid) {
            for (Button button : row) {
                String btnMark = button.getText().trim();

                if (btnMark.equalsIgnoreCase(player1Mark)) {
                    playerPoints++;
                } else if (btnMark.equalsIgnoreCase(player2Mark)) {
                    computerPoints++;
                }
            }
            if (playerPoints == 3) {
                winningPlayer = player;
                gameOver = true;

            } else if (computerPoints == 3) {
                winningPlayer = computer;
                gameOver = true;
            }
            playerPoints = 0;
            computerPoints = 0;
        }

        // Check win in columns
        if (!foundWinner) { // Check if winner has already been found
            for (int i = 0; i < gameGrid[0].length; i++) {
                for (int j = 0; j < gameGrid.length; j++) {
                    String btnMark = gameGrid[j][i].getText().trim();

                    if (btnMark.equalsIgnoreCase(player1Mark)) {
                        playerPoints++;
                    } else if (btnMark.equalsIgnoreCase(player2Mark)) {
                        computerPoints++;
                    }
                }
                if (playerPoints == 3) {
                    winningPlayer = player;
                    gameOver = true;
                } else if (computerPoints == 3) {
                    winningPlayer = computer;
                    gameOver = true;
                } else {
                    playerPoints = 0;
                    computerPoints = 0;
                }
            }
        }

        // Check diagonal win
        if (!foundWinner) {
            for (int i = 0; i < gameGrid.length; i++) {
                String btnMark = gameGrid[i][i].getText().trim();

                if (btnMark.equalsIgnoreCase(player1Mark)) {
                    playerPoints++;
                } else if (btnMark.equalsIgnoreCase(player2Mark)) {
                    computerPoints++;
                }
            }
            if (playerPoints == 3) {
                winningPlayer = player;
                gameOver = true;
            } else if (computerPoints == 3) {
                winningPlayer = computer;
                gameOver = true;
            } else {
                playerPoints = 0;
                computerPoints = 0;
            }
        }

        // Check anti-diagonal win
        if (!foundWinner) {
            int col = 2;
            for (int i = 0; i < gameGrid.length; i++) {
                String btnMark = gameGrid[i][col].getText().trim();

                if (btnMark.equalsIgnoreCase(player1Mark)) {
                    playerPoints++;
                } else if (btnMark.equalsIgnoreCase(player2Mark)) {
                    computerPoints++;
                }
                col--;
            }
            if (playerPoints == 3) {
                winningPlayer = player;
                gameOver = true;
            } else if (computerPoints == 3) {
                winningPlayer = computer;
                gameOver = true;
            }
        }

        if (turns == 9) {
            gameOver = true;
        }

        if (gameOver) {
            if (winningPlayer != null) {
                gameLog.addLog(gameLog.LOG_PLAYER_WON, winningPlayer);
                winningPlayer.addPoint();
            } else {
                this.drawCounts++;
                gameLog.addLog(gameLog.LOG_DRAW);
            }
        }
    }

    public void setStartingPlayer() {
        if (randomStartingPlayer) {
            double random = Math.random();
            if (random > 0.5) {
                player.setTurn(true);
                computer.setTurn(false);
            } else {
                player.setTurn(false);
                computer.setTurn(true);
            }
            startingPlayer = playerHasTurn();
        } else {
            if (startingPlayer == player) {
                player.setTurn(true);
                computer.setTurn(false);
            } else {
                player.setTurn(false);
                computer.setTurn(true);
            }
        }
        gameLog.addLog(gameLog.LOG_PLAYER_STARTS, startingPlayer);
    }

    public void newGame() {
        this.gameOver = false;
        this.turns = 0;
        gameLog.clear();
    }
}
