package example.jbot.commands;

import example.jbot.core.GameCache;
import example.jbot.core.TicTacToe;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.stereotype.Component;

/**
 * Created by xxxxx on 6/17/17.
 */
@Component
public class GameStatus extends BaseCommand {
    /**
     * Check for an active game and print the Board and provide the state of board.
     * State: Win or Tied or current move
     * @param channelId
     * @param gameCache
     * @return
     */
    public RichMessage handleCommand(String channelId, GameCache gameCache) {
        if (!gameCache.isActiveGame(channelId)) {
            return buildMessage("No Active Game exists!. \n '/ttt challenge @<Teammember>' to start the game");
        }
        TicTacToe game = gameCache.getGame(channelId);
        return buildMessage(game.printStatus());
    }
}
