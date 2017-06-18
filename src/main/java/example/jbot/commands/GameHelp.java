package example.jbot.commands;

import example.jbot.core.GameCache;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.stereotype.Component;

/**
 * Created by xxxxx on 6/17/17.
 */
@Component
public class GameHelp extends BaseCommand {
    /**
     *
     * @param channelId
     * @param gameCache
     * @return
     */
    public RichMessage handleCommand(String channelId, GameCache gameCache) {
        String message = "`/ttt challenge [@username]` play tictactoe !" +
                "\n`/ttt accept [@username]` a challenged game" +
                "\n`/ttt place [cell name]` place move on empty space " +
                gameCache.getMoves(channelId) + " of the board" +
                "\n`/ttt quit` quits the current game in the channel" +
                "\n`/ttt help`";
        return buildMessage(message);
    }
}
