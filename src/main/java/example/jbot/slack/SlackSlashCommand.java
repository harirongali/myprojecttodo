package example.jbot.slack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.jbot.commands.*;
import example.jbot.core.GameCache;
import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample Slash Command Handler.
 *
 * @author ramswaroop
 * @version 1.0.0, 20/06/2016
 */
@RestController
public class SlackSlashCommand {

    private static final Logger logger = LoggerFactory.getLogger(SlackSlashCommand.class);

    /**
     * The token you get while creating a new Slash Command. You
     * should paste the token in application.properties file.
     */
    @Value("${slashCommandToken}")
    private String slackToken;

    @Autowired
    private GameCache gameCache;

    @Autowired
    private Accept accept;

    @Autowired
    private Challenge challenge;

    @Autowired
    private Move move;

    @Autowired
    private GameStatus gameStatus;

    @Autowired
    private QuitGame quit;

    @Autowired
    private GameHelp help;
    /**
     * Slash Command handler. When a user types for example "/app help"
     * then slack sends a POST request to this endpoint. So, this endpoint
     * should match the url you set while creating the Slack Slash Command.
     *
     * @param token
     * @param teamId
     * @param teamDomain
     * @param channelId
     * @param channelName
     * @param userId
     * @param userName
     * @param command
     * @param text
     * @param responseUrl
     * @return
     */
    @RequestMapping(value = "/ttt",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage onReceiveSlashCommand(@RequestParam("token") String token,
                                             @RequestParam("team_id") String teamId,
                                             @RequestParam("team_domain") String teamDomain,
                                             @RequestParam("channel_id") String channelId,
                                             @RequestParam("channel_name") String channelName,
                                             @RequestParam("user_id") String userId,
                                             @RequestParam("user_name") String userName,
                                             @RequestParam("command") String command,
                                             @RequestParam("text") String text,
                                             @RequestParam("response_url") String responseUrl) {
        // validate token
        if (!token.equals(slackToken)) {
            return new RichMessage("Sorry! You're not lucky enough to use our slack command.");
        }

        /** build response */
        RichMessage richMessage = BaseCommand.defaultMessage();
        String[] paramArgs = text.split(" ");

        /** Process and handle Commands **/
        try {
            richMessage = processCommands(paramArgs, channelId, userName);
        } catch (Exception e) {
            logger.error(e.toString());
        }

        // For debugging purpose only
        if (logger.isDebugEnabled()) {
            try {
                logger.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
            } catch (JsonProcessingException e) {
                logger.debug("Error parsing RichMessage: ", e);
            }
        }
        
        return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
    }

    private RichMessage processCommands(String[] paramArgs, String channelId, String userName) {
        /**
         * Process each of command and call the handler for each.
         * For accept and challenge commands, check for @username as well.
         */
        if (paramArgs[0].equals("accept") && paramArgs[1].startsWith("@")) {
            return accept.handleCommand(channelId, paramArgs[1].substring(1), userName, gameCache);
        } else if(paramArgs[0].equals("challenge")&& paramArgs[1].startsWith("@")) {
            return challenge.handleCommand(channelId, userName, paramArgs[1].substring(1), gameCache);
        }  else if(paramArgs[0].equals("status")) {
            return gameStatus.handleCommand(channelId, gameCache);
        }  else if(paramArgs[0].equals("place")) {
            return move.handleCommand(channelId, userName, paramArgs[1], gameCache);
        } else if(paramArgs[0].equals("quit")) {
            return quit.handleCommand(channelId, userName, gameCache);
        } else if(paramArgs[0].equals("help")) {
            return help.handleCommand(channelId, gameCache);
        }
        return BaseCommand.defaultMessage();
    }
}
