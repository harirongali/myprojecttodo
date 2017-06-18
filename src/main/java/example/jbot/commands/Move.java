package example.jbot.commands;

import example.jbot.core.GameCache;
import example.jbot.core.TicTacToe;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.stereotype.Component;

/**
 * Created by harirongali on 6/17/17.
 */
@Component
public class Move extends BaseCommand {
    /**
     * Validate the active game and current Player for a game.
     * If there is a result, then remove the game from active Games.
     * @param channelId
     * @param player
     * @param cell
     * @param gameCache
     * @return
     */
    public RichMessage handleCommand(String channelId, String player, String cell, GameCache gameCache) {
        if (!gameCache.isActiveGame(channelId)) {
            return buildMessage("No Active Game exists!. \n '/ttt challenge @<Teammember>' to start the game");
        }
        if (!gameCache.isPlaying(channelId, player)) {
            return buildMessage("@" + player + " cant make move! you are not playing!");
        }
        TicTacToe game = gameCache.getGame(channelId);
        if (!game.currentPlayer().equals(player)) {
            return buildMessage("Its not your turn !");
        }

        String errorMessage = game.move(cell, game.currentPlayerId());

        if (!errorMessage.isEmpty()) {
            return buildMessage(errorMessage);
        }

        game.flipState();

        String message = game.printStatus();

        if (gameCache.isGameOver(channelId)) {
            gameCache.removeGame(channelId);
        }
        return buildMessage(message);
    }
}
