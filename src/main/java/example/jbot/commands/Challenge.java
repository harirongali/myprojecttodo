package example.jbot.commands;

import example.jbot.config.GamePlayers;
import example.jbot.core.GameCache;
import me.ramswaroop.jbot.core.slack.SlackDao;
import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.stereotype.Component;

/**
 * Created by xxxxx on 6/16/17.
 */
@Component
public class Challenge extends  BaseCommand {
    /**
     * Validates existence of an active Game and checks for self play.
     * Update the challenges for a channel.
     * @param channelId
     * @param player1
     * @param player2
     * @param gameCache
     * @return
     */
    public RichMessage handleCommand(String channelId, String player1, String player2, GameCache gameCache) {
        if (gameCache.isActiveGame(channelId)) {
            return buildMessage("An active Game exists in the channel");
        }
        if (player1.equals(player2)) {
            return buildMessage("Cant Play against Self!");
        }
        gameCache.updateChallengedGames(channelId, new GamePlayers(player1, player2));

        return buildMessage("@" + player2 + "! you have been challenged! " +"\n" +
                "'/ttt accept @"+player1+"' to accept!");
    }


}
