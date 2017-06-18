package example.jbot.core;

import org.springframework.stereotype.Component;
import static example.jbot.config.Constants.*;

/**
 * Created by xxxx on 6/17/17.
 */
public class TicTacToeBoard {

    /**
     * Maintains board and handles moves & Prints the board
     */
    private int board[]; // 0 - 8 cells
    private int trackCells[][] = {
            //Tracks all combinations for a result
            {0, 1, 2},    // Row 1
            {3, 4, 5},    // Row 2
            {6, 7, 8},    // Row 3
            {0, 3, 6},    // Column 1
            {1, 4, 7},    // Column 2
            {2, 5, 8},    // Column 3
            {0, 4, 8},    // Diagonal 1
            {2, 4, 6}     // Diagonal 2
    };

    public TicTacToeBoard()
    {
        this.reset();
    }

    public void reset()
    {
        board = new int[] {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
    }

    private int getCell(int index)
    {
        if (index < 0 | index > 8)
            throw new IllegalArgumentException("index must be 0-9");

        return board[index];
    }

    public int getCell(String cell)
    {
        int index = mapCellToIndex(cell);
        if (index == -1)
            throw new IllegalArgumentException("Invalid cell");
        switch (getCell(index))
        {
            case CROSS:
                return PLAYER1;
            case NOUGHT:
                return PLAYER2;
            default:
                return NONE;
        }
    }

    private int mapCellToIndex(String cell)
    {
        if (cell.equals("A1")) {
            return 0;
        } else if (cell.equals("A2")) {
            return 1;
        } else if (cell.equals("A3")) {
            return 2;
        } else if (cell.equals("B1")) {
            return 3;
        } else if (cell.equals("B2")) {
            return 4;
        } else if (cell.equals("B3")) {
            return 5;
        } else if (cell.equals("C1")) {
            return 6;
        } else if (cell.equals("C2")) {
            return 7;
        } else if (cell.equals("C3")) {
            return 8;
        } else {
            return -1;
        }
    }

    private String mapIndexToCell(int index)
    {
        switch (index)
        {
            case 0:
                return "A1";
            case 1:
                return "A2";
            case 2:
                return "A3";
            case 3:
                return "B1";
            case 4:
                return "B2";
            case 5:
                return "B3";
            case 6:
                return "C1";
            case 7:
                return "C2";
            case 8:
                return "C3";
            default:
                return "";
        }
    }

    public String playAt(String cell, int player)
    {
        int index = mapCellToIndex(cell);
        if (index == -1)
            return "Invalid cell";
        return this.playAt(index, player);
    }

    private String playAt(int index, int player)
    {
        /**
         * validates the move and places CROSS for first player and NOUGHT for second player.
         */
        if (index < 0 | index > 8)
            return "Invalid Move. Cell must be " + getRemainingMoves();
        if (player <1 | player > 2)
            return "Player must be 1 or 2";
        if (board[index] != 2)
            return "Cell is not empty. Try " + getRemainingMoves();
        if (player == 1)
            board[index] = CROSS;
        else
            board[index] = NOUGHT;
        return "";
    }

    public int getState()
    {
        /**
         * State of the Board ==> Win/Draw or continue
         * For Draw, considering possible wins as well.
         * Possible moves for a draw:
         *    1. Blanks zero ==> Draw
         *    2. Blanks one ==> check if every trackCells has atleast one CROSS & NOUGHT
         *    3. Blanks Two ==> A win is possible only if there is a combination of trackcells with two CROSS's or NOUGHT's
         */
        Boolean oneChance = false;
        Boolean twoChances = false;
        // check for win
        for (int i = 0; i < trackCells.length; i++)
        {
            int p = getTrackCellProduct(i);

            if (p == CROSSWIN)
                return PLAYER1;      // Player 1 has won
            if (p == NOUGHTWIN)
                return PLAYER2;      // Player 2 has won
            if (p % (CROSS * NOUGHT) != 0) {
                if (oneChance == true)
                    twoChances = true;
                oneChance = true;
            }
        }

        // check for draw

        int blankCount = 0;
        for (int i = 0; i < board.length; i++)
            if (board[i] == EMPTY)
                blankCount++;

        if (blankCount == 0 || (blankCount == 1 && !oneChance) ||
                (blankCount == 2 && !twoChances && !oneChance))
            return DRAW;          // Game is a draw

        return NONE;              // Game is not over
    }

    private int getTrackCellProduct(int index)
    {
        return board[trackCells[index][0]] *
                board[trackCells[index][1]] *
                board[trackCells[index][2]];
    }

    public String toString()
    {
        return "\n " +
                getMark(board[0]) + " | " +
                getMark(board[1]) + " | " +
                getMark(board[2]) +
                "\n-----------\n" +
                " " +
                getMark(board[3]) + " | " +
                getMark(board[4]) + " | " +
                getMark(board[5]) +
                "\n-----------\n" +
                " " +
                getMark(board[6]) + " | " +
                getMark(board[7]) + " | " +
                getMark(board[8]);
    }

    public String printBoard() {
        return "\n " +
                "A1" + " | " +
                "A2" + " | " +
                "A3" +
                "\n-------------\n" +
                " " +
                "B1" + " | " +
                "B2" + " | " +
                "B3" +
                "\n-------------\n" +
                " " +
                "C1" + " | " +
                "C2" + " | " +
                "C3";
    }


    private String getMark(int status)
    {
        if (status == CROSS)
            return "X";
        if (status == NOUGHT)
            return "O";
        return "  ";
    }

    public String getRemainingMoves() {
        String moves = "";
        for (int i = 0; i < board.length; i++)
            if (board[i] == EMPTY) {
                moves = moves + ", " + mapIndexToCell(i);
            }
        return moves;
    }
}
