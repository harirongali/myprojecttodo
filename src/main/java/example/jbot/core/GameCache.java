package example.jbot.core;

import example.jbot.config.GamePlayers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;

import static example.jbot.config.Constants.*;

/**
 * Created by xxxxx on 6/16/17.
 */
@Component
public class GameCache {
    /**
     * Cache to hold games for all channels.
     */
    private HashMap<String, TicTacToe> allActiveGames; // Only one Game Per Channel
    private HashMap<String, ArrayList<GamePlayers>> challegedGames; // Holds all challenges per channel

    @PostConstruct
    public void init() {
        allActiveGames = new HashMap<String, TicTacToe>();
        challegedGames = new HashMap<String, ArrayList<GamePlayers>>();
    }

    public void updateChallengedGames(String channelId, GamePlayers challenge) {
        /**
         * Create or update Challenges in the Map.
         */
        if (challegedGames.containsKey(channelId)) {
            challegedGames.get(channelId).add(challenge);
        } else {
            ArrayList<GamePlayers> tempConf = new ArrayList<GamePlayers>();
            tempConf.add(challenge);
            challegedGames.put(channelId, tempConf);
        }
    }

    public GamePlayers findChallenge(String channelId, String player1, String player2) {
        /**
         * Find the challenge for a combination of players in a channel.
         */
        if (challegedGames.containsKey(channelId)) {
            ArrayList<GamePlayers> tempConf = challegedGames.get(channelId);
            for (GamePlayers gamePlayers : tempConf) {
                if (gamePlayers.getPlayer1Name().equals(player1) &&
                        gamePlayers.getPlayer2Name().equals(player2)) {
                    return gamePlayers;
                }
            }
        }
        return null;
    }

    public void removeGame(String channelId) {
        allActiveGames.remove(channelId);
    }

    public boolean isActiveGame(String channelId) {
        return allActiveGames.containsKey(channelId);
    }

    public void startGame(String channelId, GamePlayers gamePlayers) {
        /**
         * When a user accepts a challenge, then TicTacToe is created and added to activeGames object
         * All challenges for a channel are cleared.
         */
        allActiveGames.put(channelId, new TicTacToe(gamePlayers));
        challegedGames.remove(channelId);
    }

    public String printBoard(String channelId) {
        return allActiveGames.get(channelId).printBoard();
    }

    public Boolean isGameOver(String channelId) {
        /**
         * Check to see if there a result for game.
         * True => Win or Draw
         */
        Integer status = getGame(channelId).getBoard().getState();
        if (status == PLAYER1 || status == PLAYER2 || status == DRAW) {
            return true;
        }
        return false;
    }

    public TicTacToe getGame(String channelId) {
        return allActiveGames.get(channelId);
    }

    public boolean isPlaying(String channelId, String player) {
        /**
         * Check to see if the player is actually part of the game in a channel
         */
        if (getGame(channelId).getGamePlayers().getPlayer1Name().equals(player) ||
                getGame(channelId).getGamePlayers().getPlayer2Name().equals(player)) {
            return true;
        }
        return false;
    }

    public String getMoves(String channelId) {
        /**
         * Provide all possible moves
         */
        if (isActiveGame(channelId)) {
            return getGame(channelId).getMoves();
        } else {
            return MOVES;
        }

    }
}
