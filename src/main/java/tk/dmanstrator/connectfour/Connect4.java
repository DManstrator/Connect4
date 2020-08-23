package tk.dmanstrator.connectfour;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import tk.dmanstrator.connectfour.log.GameLog;
import tk.dmanstrator.connectfour.log.GameLogEntry;
import tk.dmanstrator.connectfour.log.GameLogEntry.RoundTitle;
import tk.dmanstrator.connectfour.log.GameLogEntryBuilder;

/**
 * Main class containing the logic for the Connect4 game.
 * @author DManstrator
 *
 */
public abstract class Connect4 {

    protected static final int WIDTH = 7;
    protected static final int HEIGHT = 6;
    protected static final int MIN_ROUNDS = 6;
    protected static final int MAX_ROUNDS = WIDTH * HEIGHT;

    protected static final int WIN_AMOUNT = 4;

    protected static final int[][] ROW_INDICES = {{-3, 0}, {-2, 0}, {-1, 0},
                                                  { 0, 0},
                                                  { 1, 0}, { 2, 0}, { 3, 0}};

    protected static final int[][] COL_INDICES = {{0, -3}, {0, -2}, {0, -1},
                                                  {0,  0},
                                                  {0,  1}, {0,  2}, {0,  3}};

    protected static final int[][] DIA1_INDICES = {{-3, -3}, {-2, -2}, {-1, -1},
                                                   { 0,  0},
                                                   { 1,  1}, { 2,  2}, { 3,  3}};

    protected static final int[][] DIA2_INDICES = {{-3,  3}, {-2,  2}, {-1,  1},
                                                   { 0,  0},
                                                   { 1, -1}, { 2, -2}, { 3, -3}};

    protected static final int[][][] ALL_MATRICES = {ROW_INDICES, COL_INDICES,
                                                     DIA1_INDICES, DIA2_INDICES};

    protected static final char EMPTY_CHAR = ' ';
    protected static final char PLAYER_ONE_COLOR = 'r';
    protected static final char PLAYER_TWO_COLOR = 'y';

    protected final char[][] field = createNewField();

    private final String firstPlayerName;
    private final String secondPlayerName;
    private final GameLog gameLog = new GameLog();
    private final Map<Integer, Integer> entriesPerColumn = new HashMap<>();

    private String currentPlayerName;
    private int playedRounds = 0;
    private boolean draw = false;
    private boolean gameOver = false;

    /**
     * Constructor for creating a Connect4 game with two players.
     * 
     * @param firstPlayerName Name of the first player
     * @param secondPlayerName Name of the second player
     */
    public Connect4(final String firstPlayerName, final String secondPlayerName)  {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.currentPlayerName = firstPlayerName;
    }

    /**
     * Returns the name of the first player.
     * @return The name of the first player
     */
    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    /**
     * Returns the name of the second player.
     * @return The name of the second player
     */
    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    /**
     * Returns the name of the current player.
     * @return The name of the current player
     */
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    /**
     * Returns the amount of valid played rounds.
     * @return The amount of valid played rounds
     */
    public int getPlayedRounds() {
        return playedRounds;
    }

    /**
     * Tells if the game has ended and if nobody won.
     * @return {@code true} if the game ended in a draw, else {@code false}
     */
    public boolean isDraw()  {
        return draw;
    }

    /**
     * Tells if the game has ended. It doesn't matter if somebody has won or not.
     * @return {@code true} if the game ended, else {@code false}
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns the {@link GameLog} for this game.
     * @return The GameLog for this game
     */
    public GameLog getGameLog() {
        return gameLog;
    }

    /**
     * Used to play a round of a {@link DefaultConnect4} game.
     * 
     * @param x X coordinate which has to be between 1 and 7
     * @return A {@link RoundResult} containing a message and
     * a boolean telling if the round was valid
     * @throws UnsupportedOperationException if this is a {@link FreePositionedConnect4} game.
     */
    public RoundResult play(final int x) {
        return play(x, getNextAvailablePosition(x));
    }

    /**
     * Used to play a round of a {@link FreePositionedConnect4} game.
     * 
     * @param x X coordinate which has to be between 1 and 7
     * @param y Y coordinate which has to be between 1 and 6
     * @return A {@link RoundResult} containing a message and
     * a boolean telling if the round was valid
     * @throws UnsupportedOperationException if this is a {@link DefaultConnect4} game.
     */
    public RoundResult play(final int x, final int y)  {
        final boolean alreadyOver = isGameOver();
        if (alreadyOver)  {
            return new RoundResult("Game is already over!", false);
        }

        final String currentPlayer = this.currentPlayerName;

        final int internalX = x - 1;
        final int internalY = y - 1;

        final boolean inRange = isInRange(internalX, internalY);
        if (!inRange)  {
            final String message = String.format("Chosen position (%d, %d) is not in range!",
                    x, y);
            return returnAndLog(
                    new RoundResult(currentPlayer, message, false), RoundTitle.INVALID);
        }

        final char charAtPos = getCharAtPosition(internalX, internalY);
        if (charAtPos != EMPTY_CHAR) {
            final String message = String.format("Chosen position (%d, %d) is already taken!",
                    x, y);
            return returnAndLog(new RoundResult(currentPlayer, message, false), RoundTitle.INVALID);
        }

        setField(internalX, internalY);
        updateRoundsCount();

        final String defaultReturnMsg = String.format("Position (%d, %d) belongs now to player %s",
                x, y, currentPlayer);
        final RoundResult defaultResult = returnAndLog(
                new RoundResult(currentPlayer, defaultReturnMsg, true));

        if (playedRounds > MIN_ROUNDS)  {
            final boolean gameOver = checkGameOver(internalX, internalY);
            if (gameOver)  {
                final String message = String.format("Player %s has won the game!", currentPlayer);
                this.gameOver = true;
                return returnAndLog(new RoundResult(currentPlayer, message, true), RoundTitle.GAME_OVER);
            }
        }

        if (playedRounds == MAX_ROUNDS)  {
            this.draw = true;
            this.gameOver = true;
            return returnAndLog(new RoundResult("Draw, nobody won!", true), RoundTitle.DRAW);
        }

        updatePlayer();
        return defaultResult;
    }

    /**
     * Returns the current field as string with column and row numbers.<br><br>
     * 
     * <b>Example:</b>
     * <pre>{@code 
     *   1234567
     * 1[       ]
     * 2[       ]
     * 3[   y   ]
     * 4[  rry  ]
     * 5[yrrryyr]
     * 6[rryyyry]
     * }</pre>
     * @return The current field as string which can be used to illustrate the current state
     */
    public String getCurrentField()  {
        return getCurrentField(true);
    }

    /**
     * Returns the current field as string.
     * The boolean determines if column and row numbers should be displayed or not.<br><br>
     * 
     * <b>Example (without column and row numbers):</b>
     * <pre>{@code 
     * [       ]
     * [       ]
     * [   y   ]
     * [  rry  ]
     * [yrrryyr]
     * [rryyyry]
     * }</pre>
     * @param fullDetails {@code true} if column and row numbers should be displayed, else {@code false}
     * @return The current field as string which can be used to illustrate the current state
     * @see Connect4#getCurrentField()
     */
    public String getCurrentField(final boolean fullDetails)  {
        final StringBuilder builder = new StringBuilder();
        if (fullDetails) {
            builder.append("  ");  // spaces for first line
            for (int c = 1; c <= WIDTH; c++)  {
                builder.append(c);
            }
            builder.append(System.lineSeparator());
        }

        for (int c = 1; c <= HEIGHT; c++)  {
            char[] row = field[c - 1];
            if (fullDetails)  {
                builder
                    .append(c);
            }
            builder
                .append('[')
                .append(row)
                .append(']');

            if (c != HEIGHT)  {
                builder
                    .append(System.lineSeparator());
            }

        }
        return builder.toString();
    }

    /**
     * Returns the current field as string without column and row numbers.
     * 
     * @return the current field as string which can be used to illustrate the current state
     * @see Connect4#getCurrentField(boolean)
     */
    @Override
    public String toString() {
        return getCurrentField(false);
    }

    /**
     * Creates a new field for the game with spaces as the default value (default value would be null terminal).
     * @return A new field for the game
     */
    private char[][] createNewField()  {
        final char[][] field = new char[HEIGHT][WIDTH];
        for (final char[] arr : field)  {
            // Default value is null terminal (\0) which can break when copy paste
            Arrays.fill(arr, ' ');
         }
        return field;
    }

    /**
     * Calculates the next possible y coordinate for a given x coordinate.
     * This is used for the {@link DefaultConnect4} game.
     * @param x X coordinate to calculate the y coordinate from
     * @return The next possible y coordinate for a given x coordinate
     */
    private int getNextAvailablePosition(int x) {
        int entriesInColumn = entriesPerColumn.computeIfAbsent(x, (y) -> 0);
        entriesPerColumn.compute(x, (__, y) -> y + 1);  // update map
        return HEIGHT - entriesInColumn;
    }

    /**
     * Checks if the given point is in range of the field.
     * @param x X coordinate
     * @param y Y coordinate
     * @return {@code true} if in range, else {@code false}
     */
    private boolean isInRange(final int x, final int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    /**
     * Returns the character / color of a player at a given position.
     * @param x X coordinate
     * @param y Y coordinate
     * @return The character / color of a player at a given position
     */
    private char getCharAtPosition(int x, int y) {
        return field[y][x];  // not how it is supposed to be
    }

    /**
     * Assigns the given position to the current player.
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void setField(int x, int y) {
        field[y][x] = getCurrentPlayerColor();
    }

    /**
     * Returns the player number for the current player.
     * This is used for the {@link GameLog}.
     * @return {@code 1} for the first player or {@code 2} for the second player
     */
    private int getCurrentPlayerNumber()  {
        return currentPlayerName.equals(firstPlayerName)
                ? 1
                : 2;
    }

    /**
     * Returns the color of the current player.
     * @return {@code r} if the current player is player one, else {@code y}
     */
    private char getCurrentPlayerColor()  {
        return currentPlayerName.equals(firstPlayerName)
                ? PLAYER_ONE_COLOR
                : PLAYER_TWO_COLOR;
    }

    /**
     * Sets the current player to the other player.
     */
    private void updatePlayer()  {
        this.currentPlayerName = currentPlayerName.equals(firstPlayerName)
                ? secondPlayerName
                : firstPlayerName;
    }

    /**
     * Increases the amount of player rounds.
     */
    private void updateRoundsCount()  {
        playedRounds++;
    }

    /**
     * Logs the given {@link RoundResult} with {@link RoundTitle#DEFAULT} as the title
     * and sets the round number to the current one.
     * 
     * @param roundResult RoundResult containing the message and a flag if the round was valid, used for logging
     * @return The given {@link RoundResult} for convenience
     */
    private RoundResult returnAndLog(final RoundResult roundResult) {
        return returnAndLog(roundResult, RoundTitle.DEFAULT, playedRounds);
    }

    /**
     * Logs the given {@link RoundResult} with the given {@link RoundTitle}.
     * {@code -1} is set as the round number marking it as invalid.
     * 
     * @param roundResult RoundResult containing the message and a flag if the round was valid, used for logging
     * @return The given {@link RoundResult} for convenience
     */
    private RoundResult returnAndLog(final RoundResult roundResult, final RoundTitle title) {
        return returnAndLog(roundResult, title, -1);
    }

    /**
     * Logs the given {@link RoundResult} with the given {@link RoundTitle} and the given round number.
     * 
     * @param roundResult RoundResult containing the message and a flag if the round was valid, used for logging
     * @return The given {@link RoundResult} for convenience
     */
    private RoundResult returnAndLog(final RoundResult roundResult,
                                     final RoundTitle title,
                                     final int roundNr) {
        final int playerNumber = title.isFromPlayer()
                ? getCurrentPlayerNumber()
                : 0;

        final GameLogEntry entry = new GameLogEntryBuilder()
            .setTitle(title)
            .setRoundNumber(roundNr)
            .setMessage(roundResult.getMessage())
            .setPlayerNumber(playerNumber)
            .setValidRound(roundResult.isValid())
            .build();
        gameLog.addEntry(entry);
        return roundResult;
    }

    /**
     * Checks if the game is over. Takes the last position for an optimized algorithm.
     * Algorithm is calculating the maximum hits in order and checks if it is equal or greater than 4.
     * <br><br>
     * 
     * With the matrices, the following positions will be checked depending on the current position:
     * <pre>{@code 
     * \  |  /
     *  \ | / 
     *   \|/  
     * ---X---
     *   /|\  
     *  / | \ 
     * /  |  \
     * }</pre>
     * 
     * @param lastX The last X coordinate which was played
     * @param lastY The last Y coordinate which was played
     * @return {@code true} if the game is over, else {@code false}
     * @see Connect4#getHitsInOrder(int[][], int, int)
     */
    private boolean checkGameOver(final int lastX, final int lastY)  {
        for (final int[][] matrix : ALL_MATRICES)  {
            final int hits = getHitsInOrder(matrix, lastX, lastY);
            if (hits >= WIN_AMOUNT)  {
                return true;
            }
        }

        return false;
    }

    /**
     * Calculates the maximum hits for the given lookup matrix.
     * 
     * @param sourceMatrix Lookup matrix containing needed positions to check
     * @param srcX X coordinate which should be used as the starting point
     * @param srcY Y coordinate which should be used as the starting point
     * @return The maximum hits in order for the given lookup matrix 
     */
    private int getHitsInOrder(final int[][] sourceMatrix, final int srcX, final int srcY)  {
        int hitsInOrder = 0, maxHits = 0;
        final char currentPlayerColour = getCurrentPlayerColor();
        for (final int[] matrixElement : sourceMatrix)  {
            final char colorOfField = getColorOfField(matrixElement, srcX, srcY);
            if (colorOfField == currentPlayerColour)  {
                hitsInOrder++;
            }  else  {
                maxHits = Math.max(maxHits, hitsInOrder);
                hitsInOrder = 0;
            }
        }
        return Math.max(maxHits, hitsInOrder);
    }

    /**
     * Returns the color of a field depended on the matrix element.
     * Calculates the correct coordinates based on the given matrix element.<br>
     * Example. {@code srcX = 4, srcY = 2, offset = (-2, 2) => x = 2, y = 4} 
     * 
     * @param sourceMatrix Lookup matrix containing needed positions to check
     * @param srcX X coordinate which should be used as the starting point
     * @param srcY Y coordinate which should be used as the starting point
     * @return The character / color at the calculated position or a space if the field is not assigned yet
     * @see Connect4#getCharAtPosition(int, int)
     */
    private char getColorOfField(final int[] matrixElement, final int srcX, final int srcY)  {
        final int x = srcX + matrixElement[0];
        final int y = srcY + matrixElement[1];
        final boolean inRange = isInRange(x, y);
        return inRange
                ? getCharAtPosition(x, y)
                : EMPTY_CHAR;
    }

}
