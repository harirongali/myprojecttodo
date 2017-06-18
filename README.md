# SlackTicTacToe

Slack tic tac toe game that allows you to play tic tac toe with 
someone in a slack channel

Project is based out of https://github.com/ramswaroop/jbot

# Rules

If your unfamiliar with the complex rules of tic tac toe, check them @ [this wikipedia
article](https://en.wikipedia.org/wiki/Tic-tac-toe) !

# Commands
- `/ttt challenge [@username]` play tictactoe !
- `/ttt accept [@username]` to accept a game and start the game
- `/ttt place [cell name]` place move on empty space A1, A2, A3, B1, B2, B3, C1, C2, C3 of the board
- `/ttt quit` quits the current game in the channel
- `/ttt help`


# Architecture

### TicTacToeBoard
TicTacToe Board maintains game board and makes moves & check for winners.

## TicTacToe
Then there is actual Game tracker which has the board and maintains players and moves.

### Commands
Dir has all commands that are supported and provides actions based on the usage.

Messaging the Slack channel all comes in the form of responses. The only time this services
messages the slack room is due to a response of the POST request.

### Game cache
Here instead of DB, we are maintining cache as local DB that has two objects 
1. allActiveGames Object maintains a game per channel. As per requirement only one game per channel is allowed. 
And object is hashmap that holds all TicTacToe objects as values.
2. challegedGames Object maintains channelId as key and ArrayList of players to start the game. Once a game is started,
then that key is removed and a game is added to allActiveGames map.

Multiple people can broadcast multiple challenges on the same channel. If any one of the challenges
gets accepted, all other challenges on that channel get cleared.

# Installation

- clone the repo
```sh
git clone https://github.com/harirongali/slackTicTacToe
cd slackTicTacToe
```
- Run on IDE or on command line
```sh
mvn spring-boot:run
```
