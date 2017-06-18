package example.jbot.commands;

import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;

/**
 * Created by harirongali on 6/17/17.
 */
public class BaseCommand {

    public RichMessage buildMessage(String message) {
        RichMessage richMessage = new RichMessage("TicTacToe Game !");
        richMessage.setResponseType("in_channel");
        // set attachments
        Attachment[] attachments = new Attachment[1];
        attachments[0] = new Attachment();
        attachments[0].setText(message);
        richMessage.setAttachments(attachments);
        return richMessage;
    }

    public static RichMessage defaultMessage() {
        RichMessage richMessage = new RichMessage("TicTacToe Game !");
        richMessage.setResponseType("in_channel");
        // set attachments
        Attachment[] attachments = new Attachment[1];
        attachments[0] = new Attachment();
        attachments[0].setText("Invalid command. Please try '/ttt help' to know more about the game");
        richMessage.setAttachments(attachments);
        return richMessage;
    }
}
