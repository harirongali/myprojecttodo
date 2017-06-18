package example.jbot.config;

import example.jbot.commands.Challenge;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import static example.jbot.config.Constants.*;

/**
 * Created by harirongali on 6/16/17.
 */
public class GamePlayers {
    /**
     * Holds player for a game
     */
    private String player1;
    private String player2;

    public GamePlayers(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getPlayer1Name() {
        return player1;
    }

    public String getPlayer2Name() {
        return player2;
    }

    public String toString() {
        return player1 + " " + player2;
    }
}
