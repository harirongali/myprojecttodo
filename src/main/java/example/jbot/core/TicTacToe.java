package example.jbot.core;

import example.jbot.config.GamePlayers;
import org.springframework.stereotype.Component;

import static example.jbot.config.Constants.*;

/**
 * Created by harirongali on 6/16/17.
 */
public class TicTacToe {
    /**
     * Holds the Board and players
     * Maintains state and flips for each move.
     */
    private TicTacToeBoard board = new TicTacToeBoard();
    private GamePlayers gamePlayers;
    private boolean state; //False Player1 & True Player2

    public String move(String cell, Integer player) {
        return board.playAt(cell, player);
    }

    public TicTacToe(GamePlayers gamePlayers) {
        this.gamePlayers = gamePlayers;
        state = false; //current state starts with Player1
    }

    public String printBoard() {
        return board.printBoard();
    }

    public String printStatus() {
        /**
         * Get the status of the Game and provide the corresponding message.
         */
        Integer status = board.getState();
        String message = "";
        if (status == PLAYER1) {
            message = "@"+ gamePlayers.getPlayer1Name() + " won the game!";
        } else if (status == PLAYER2) {
            message = "@"+ gamePlayers.getPlayer1Name() + " won the game!";
        } else if (status == DRAW) {
            message = "@"+ gamePlayers.getPlayer1Name() + "and @"+ gamePlayers.getPlayer2Name() +" tied the game!";
        } else {
            message = "Its @" + currentPlayer() + " turn!";
        }
        return message + board.toString();
    }

    public String currentPlayer() {
        return !state ? gamePlayers.getPlayer1Name(): gamePlayers.getPlayer2Name();
    }

    public Integer currentPlayerId() {
        return !state ? PLAYER1: PLAYER2;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    // Flip state after every move
    public void flipState() {
        this.state = !state;
    }

    public GamePlayers getGamePlayers() {
        return gamePlayers;
    }

    public TicTacToeBoard getBoard() { return board; }

    public String getMoves() {
        return board.getRemainingMoves();
    }
}
