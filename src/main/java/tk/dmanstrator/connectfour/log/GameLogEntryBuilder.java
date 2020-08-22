package tk.dmanstrator.connectfour.log;

import tk.dmanstrator.connectfour.log.GameLogEntry.RoundTitle;

/**
 * Builder class for the {@link GameLogEntry}.
 * @author DManstrator
 *
 */
public class GameLogEntryBuilder {

    private String message;
    private int roundNumber;
    private int playerNumber;
    private RoundTitle title;
    private boolean validRound;

    /**
     * Sets the message for the entry.
     * 
     * @param message Message to set
     * @return The builder instance, useful for chaining
     */
    public GameLogEntryBuilder setMessage(final String message) {
        this.message = message;
        return this;
    }

    /**
     * Sets the {@link RoundTitle} for the entry.
     * 
     * @param title {@link RoundTitle} to set
     * @return The builder instance, useful for chaining
     */
    public GameLogEntryBuilder setTitle(final RoundTitle title)  {
        this.title = title;
        return this;
    }

    /**
     * Sets the validity of the round.
     * 
     * @param valid Flag telling if valid of not.
     * @return The builder instance, useful for chaining
     */
    public GameLogEntryBuilder setValidRound(final boolean valid) {
        this.validRound = valid;
        return this;
    }

    /**
     * Sets the round number for the entry.
     * 
     * @param roundNumber Round number to set
     * @return The builder instance, useful for chaining
     */
    public GameLogEntryBuilder setRoundNumber(final int roundNumber) {
        this.validRound = true;
        this.roundNumber = roundNumber;
        return this;
    }

    /**
     * Sets the message for the entry.
     * 
     * @param message Message to set
     * @return The builder instance, useful for chaining
     */
    public GameLogEntryBuilder setPlayerNumber(final int playerNumber) {
        this.playerNumber = playerNumber;
        return this;
    }

    /**
     * Builds the {@link GameLogEntry}.
     * @return The built {@link GameLogEntry}
     */
    public GameLogEntry build()  {
        return new GameLogEntry(message, roundNumber, playerNumber, validRound, title);
    }

}
