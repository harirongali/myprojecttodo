package example.jbot.commands;

import example.jbot.core.GameCache;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.stereotype.Component;

/**
 * Created by harirongali on 6/17/17.
 */
@Component
public class QuitGame extends BaseCommand{
    public RichMessage handleCommand(String channelId, String player, GameCache gameCache) {
        if (!gameCache.isActiveGame(channelId)) {
            return buildMessage("No Active Game exists!. \n '/ttt challenge @<Teammember>' to start the game");
        }
        if (!gameCache.isPlaying(channelId, player)) {
            return buildMessage("@" + player + " You cant quit the game as you are not playing !");
        }
        gameCache.removeGame(channelId);

        return buildMessage("@" + player + " quit the Game!");
    }
}
