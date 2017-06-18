package example.jbot.commands;

import example.jbot.config.GamePlayers;
import example.jbot.core.GameCache;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.stereotype.Component;

/**
 * Created by xxxxx on 6/16/17.
 */
@Component
public class Accept extends BaseCommand {
    /**
     * Validate the existence of game and find the challenge for a given channel.
     * If there is a challenge, then create a TicTacToe object and start the game.
     * Clear all challenges for a channel.
     * @param channelId
     * @param player1
     * @param player2
     * @param gameCache
     * @return
     */
    public RichMessage handleCommand(String channelId, String player1, String player2, GameCache gameCache) {
        if (gameCache.isActiveGame(channelId)) {
            return buildMessage("An active Game exists in the channel! Only one game allowed per channel");
        }
        if (player1.equals(player2)) {
            return buildMessage("Cant Play against Self!");
        }
        GamePlayers gamePlayers = gameCache.findChallenge(channelId, player1, player2);
        if (gamePlayers == null) {
            return buildMessage("There is no challege exist for @"+player2 + "! \n"
            +"'/ttt challenge @"+player1 +"' to challenge");
        }
        gameCache.startGame(channelId, gamePlayers);

        String message = "Game started with symbol X for @" + player1 + " and O for @" + player2 + "! It's @" + player1 + " turn! \n";
        return buildMessage(message + gameCache.printBoard(channelId));
    }
}
