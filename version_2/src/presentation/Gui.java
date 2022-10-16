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
    private final Button btnNewGame = new Button("New game");

    private void initContent(GridPane pane) {
        // Visible grid lines
        pane.setGridLinesVisible(false);

        // Set padding and gap between cells
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        // Player name and mark input
        Button btnStartGame = new Button("Start game");
        Label lblPlayerName = new Label("Player name");
        Label lblPlayerMark = new Label("Mark");
        txfPlayerMark.setPrefWidth(0);
        txfPlayerName.setPrefWidth(0);
        pane.add(lblPlayerName, 0, 0, 2, 1);
        pane.add(lblPlayerMark, 2, 0);
        pane.add(txfPlayerName, 0, 1, 2, 1);
        pane.add(txfPlayerMark, 2, 1);
        pane.add(btnStartGame, 0, 2, 2, 1);
        btnStartGame.setOnAction(event -> startGameAction(btnStartGame));

        // New game button
        pane.add(btnNewGame, 1, 2, 2, 1);
        btnNewGame.setTranslateX(20);
        btnNewGame.setDisable(true);
        btnNewGame.setOnAction(event -> newGameAction());


        // Buttons for Tic Tac Toe grid
        final int BTN_SIZE = 50;
        final int GRID_COL = 0;
        final int GRID_ROW = 3;
        for (int i = 0; i < game.getGameGrid().length; i++) {
            for (int j = 0; j < game.getGameGrid()[i].length; j++) {
                Button button = game.getGameGrid()[i][j];
                button.setPrefSize(BTN_SIZE, BTN_SIZE);
                pane.add(button, GRID_COL + j, GRID_ROW + i);
                button.setOnAction(event -> takeTurnAction(button));
            }
        }

        // Add game log
        pane.add(txaGameLog, 3, 3, 1, 3);
        txaGameLog.setPrefSize(200, 0);
        txaGameLog.setWrapText(true);
        txaGameLog.setEditable(false);
    }

    private void startGameAction(Button button) {
        String name = txfPlayerName.getText().trim();
        String mark = txfPlayerMark.getText().trim();
        game.getPlayer().setName(name);
        game.getPlayer().setMark(mark);
        txfPlayerName.clear();
        txfPlayerMark.clear();
        button.setDisable(true);
        game.chooseStartingPlayer();
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

        // Update the log
        updateLog();
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
        btnNewGame.setDisable(true);
        if (game.playerHasTurn() == game.getComputer()) {
            game.takeComputerTurn();
        }
        updateLog();
    }

}
