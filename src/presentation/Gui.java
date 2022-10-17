package presentation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Game;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tic Tac Toe");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private final Game game = new Game();
    private final TextField txfPlayerName = new TextField("Player 1");
    private final TextField txfPlayerMark = new TextField("X");
    private final TextArea txaGameLog = new TextArea();
    private final Button btnStartGame = new Button("Start game");
    private final Button btnNewGame = new Button("New game");
    private final Button btnPlayerStarts = new Button("Player");
    private final Button btnComputerStarts = new Button("Computer");
    private final Button btnRandomStarts = new Button("Random");
    private final Label lblPlayerCounter = new Label("0");
    private final Label lblComputerCounter = new Label("0");
    private final Label lblDrawCounter = new Label("0");
    private void initContent(GridPane pane) {
        // Visible grid lines
        pane.setGridLinesVisible(false);

        // Set padding and gap between cells
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        // Player name and mark input
        Label lblPlayerName = new Label("Player name");
        Label lblPlayerMark = new Label("Mark");
        txfPlayerMark.setPrefWidth(0);
        txfPlayerName.setPrefWidth(0);
        pane.add(lblPlayerName, 0, 0, 2, 1);
        pane.add(lblPlayerMark, 2, 0);
        pane.add(txfPlayerName, 0, 1, 2, 1);
        pane.add(txfPlayerMark, 2, 1);
        pane.add(btnStartGame, 0, 2, 2, 1);
        btnStartGame.setDisable(true);
        btnStartGame.setOnAction(event -> startGameAction(btnStartGame));

        // New game button
        pane.add(btnNewGame, 1, 2, 2, 1);
        btnNewGame.setTranslateX(25);
        btnNewGame.setDisable(true);
        btnNewGame.setOnAction(event -> newGameAction());

        // Choose who starts the game
        Label lblChooseStartingPlayer = new Label("Choose starting player:");
        pane.add(lblChooseStartingPlayer, 0, 3, 3, 1);
        pane.add(btnPlayerStarts, 0, 4);
        pane.add(btnComputerStarts, 1, 4, 2, 1);
        pane.add(btnRandomStarts,2 , 4, 2, 1);
        btnRandomStarts.setTranslateX(22);
        btnPlayerStarts.setOnAction(event -> playerStartAction());
        btnComputerStarts.setOnAction(event -> computerStartAction());
        btnRandomStarts.setOnAction(event -> randomStartAction());


        // Buttons for Tic Tac Toe grid
        final int BTN_SIZE = 50;
        final int GRID_COL = 0;
        final int GRID_ROW = 5;
        for (int i = 0; i < game.getGameGrid().length; i++) {
            for (int j = 0; j < game.getGameGrid()[i].length; j++) {
                Button button = game.getGameGrid()[i][j];
                button.setPrefSize(BTN_SIZE, BTN_SIZE);
                pane.add(button, GRID_COL + j, GRID_ROW + i);
                button.setDisable(true);
                button.setOnAction(event -> takeTurnAction(button));
            }
        }

        // Add scoreboard
        Label lblScoreboard = new Label("#- SCOREBOARD -#");
        Label lblPlayer = new Label(game.getPlayer().getName() + ": ");
        Label lblComputer = new Label(game.getComputer().getName() + ": ");
        Label lblDraw = new Label("Draw: ");
        pane.add(lblScoreboard, 3, 0, 3, 1);
        pane.add(lblPlayer, 3, 1);
        pane.add(lblPlayerCounter, 4, 1);
        pane.add(lblDraw, 3, 2);
        pane.add(lblDrawCounter, 4, 2);
        pane.add(lblComputer, 3, 3);
        pane.add(lblComputerCounter, 4, 3);


        // Add game log
        pane.add(txaGameLog, 3, 5, 3, 3);
        txaGameLog.setPrefSize(200, 0);
        txaGameLog.setWrapText(true);
        txaGameLog.setEditable(false);
    }

    private void startGameAction(Button button) {
        for (int i = 0; i < game.getGameGrid().length; i++) {
            for (int j = 0; j < game.getGameGrid()[i].length; j++) {
                Button currentBtn = game.getGameGrid()[i][j];
                currentBtn.setDisable(false);
            }
        }
        String name = txfPlayerName.getText().trim();
        String mark = txfPlayerMark.getText().trim();
        game.getPlayer().setName(name);
        if (mark.equalsIgnoreCase("O")){
            game.getComputer().setMark("X");
        }
        game.getPlayer().setMark(mark);
        txfPlayerName.setEditable(false);
        txfPlayerMark.setEditable(false);
        button.setDisable(true);
        game.setStartingPlayer();
        if (game.playerHasTurn() == game.getComputer()) {
            game.takeComputerTurn();
        }
        updateLog();
    }

    private void takeTurnAction(Button btn) {
        // Take turn
        if (game.playerHasTurn() == game.getPlayer()) {
            game.takePlayerTurn(btn);
        }
        if (game.playerHasTurn() == game.getComputer() && !game.isGameOver()) {
            game.takeComputerTurn();
        }

        // Disable all buttons if game over
        if (game.isGameOver()) {
            for (Button[] row : game.getGameGrid()) {
                for (Button button : row) {
                    button.setDisable(true);
                }
            }
            btnNewGame.setDisable(false);
        }

        // Update the log and scoreboard
        updateLog();
        updateScoreboard();
    }

    private void updateLog() {
        String logLines = String.join("\n", game.getGameLog().getLog());
        txaGameLog.setText(logLines);
    }

    private void newGameAction() {
        game.newGame();
        for (Button[] row : game.getGameGrid()) {
            for (Button button : row) {
                button.setText("");
                button.setDisable(false);
            }
        }

        game.setStartingPlayer();

        btnNewGame.setDisable(true);
        if (game.playerHasTurn() == game.getComputer()) {
            game.takeComputerTurn();
        }
        updateLog();
    }

    private void playerStartAction() {
        btnPlayerStarts.setDisable(true);
        btnRandomStarts.setDisable(false);
        btnComputerStarts.setDisable(false);
        game.setStartingPlayer(game.getPlayer());
        game.setRandomStartingPlayer(false);
        btnStartGame.setDisable(false);
    }

    private void computerStartAction() {
        btnComputerStarts.setDisable(true);
        btnRandomStarts.setDisable(false);
        btnPlayerStarts.setDisable(false);
        game.setStartingPlayer(game.getComputer());
        game.setRandomStartingPlayer(false);
        btnStartGame.setDisable(false);
    }

    private void randomStartAction() {
        btnRandomStarts.setDisable(true);
        btnPlayerStarts.setDisable(false);
        btnComputerStarts.setDisable(false);
        game.setRandomStartingPlayer(true);
        btnStartGame.setDisable(false);
    }

    private void updateScoreboard() {
        lblComputerCounter.setText(Integer.toString(game.getComputer().getPoints()));
        lblPlayerCounter.setText(Integer.toString(game.getPlayer().getPoints()));
        lblDrawCounter.setText(Integer.toString(game.getDrawCounts()));
    }

}
