package tk.dmanstrator.connectfour.log;

/**
 * Class representing an entry for the {@link GameLog}.
 * @author DManstrator
 *
 */
public class GameLogEntry {

    /**
     * Represents the title of a round.
     * @author DManstrator
     *
     */
    public enum RoundTitle  {
        /**
         * Default title with a round number.
         */
        DEFAULT("Round %02d", true),

        /**
         * Invalid entry.
         */
        INVALID("invalid!", true),

        /**
         * Game over entry.
         */
        GAME_OVER("GameOver", false),

        /**
         * Draw entry.
         */
        DRAW("  Draw  ", false),

        /**
         * Unknown entry title for maintainability.
         */
        UNKNOWN(" unknown", false);

        private final String title;
        private final boolean fromPlayer;

        /**
         * Constructor setting the title and if the entry is from a player.
         * 
         * @param title Title of the enum
         * @param fromPlayer {@code true} if the entry is from a player, else {@code false}
         */
        RoundTitle(final String title, final boolean fromPlayer)  {
            this.title = title;
            this.fromPlayer = fromPlayer;
        }

        /**
         * Returns the title.
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Returns if the entry is from a player.
         * @return {@code true} if the entry is from a player, else {@code false}
         */
        public boolean isFromPlayer() {
            return fromPlayer;
        }
    }

    private final String message;
    private final int roundNumber;
    private final int playerNumber;
    private final RoundTitle roundTitle;
    private final boolean validRound;

    /**
     * Constructor for the entry.
     * 
     * @param message Message of the entry
     * @param roundNumber Round number of the entry
     * @param playerNumber Player number of the entry
     * @param validRound Flag telling if it was a valid round
     * @param title {@link RoundTitle} of the entry
     */
    public GameLogEntry(final String message, final int roundNumber,
            final int playerNumber, final boolean validRound, final RoundTitle title) {
        this.message = message;
        this.roundTitle = title;
        this.validRound = validRound;
        this.roundNumber = roundNumber;
        this.playerNumber = playerNumber;
    }

    /**
     * Returns the message of the entry.
     * @return The message of the entry
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the round number of the entry. Will be {@code -1} if round was invalid.
     * @return The round number of the entry
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Returns the player number of the entry. Will be {@code 0} if no player was passed.
     * @return The player number of the entry
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Returns flag telling if it was a valid round.
     * @return {@code true} if round was valid, else {@code false}
     */
    public boolean isValidRound() {
        return validRound;
    }

    /**
     * Returns the {@link RoundTitle} of the entry.
     * @return The {@link RoundTitle} of the entry
     */
    public RoundTitle getRoundTitle() {
        return roundTitle;
    }

    /**
     * Gets the entry as a string.
     * Adds the round number into the {@link RoundTitle} when it's a valid round.
     * Converts the player number to {@code [Player X]} if a player number was set.
     * @return The entry as a string in format {@code <Title> [Player]: <Message>}
     */
    public String getAsString()  {   
        final String title = roundTitle.getTitle();

        final String roundInfo = roundNumber == -1
                ? title
                : String.format(title, roundNumber);

        final String playerInfo = !roundTitle.isFromPlayer()
                ? ""
                : "[Player " + playerNumber + "]: ";

        return String.format("[%s] %s%s", roundInfo, playerInfo, message);
    }

    /**
     * Returns the entry as a string.
     * @return The entry as a string
     * @see GameLogEntry#getAsString()
     */
    @Override
    public String toString() {
        return getAsString();
    }

}
