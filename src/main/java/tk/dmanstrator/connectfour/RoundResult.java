package tk.dmanstrator.connectfour;

/**
 * POJO class representing the result of a round.
 * @author DManstrator
 *
 */
public class RoundResult {

    private final boolean valid;
    private final String message;
    private final String playerName;

    /**
     * Constructor for a round result taking a message and a flag if the round was valid.
     * 
     * @param message Message for the round
     * @param valid {@code true} if round was valid, else {@code false}
     */
    public RoundResult(final String message, final boolean valid)  {
        this(null, message, valid);
    }

    /**
     * Constructor for a round result taking a player, a message and a flag if the round was valid.
     * 
     * @param playerName Name of the player of the round
     * @param message Message for the round
     * @param valid {@code true} if round was valid, else {@code false}
     */
    public RoundResult(final String playerName, final String message, final boolean valid) {
        this.valid = valid;
        this.message = message;
        this.playerName = playerName;
    }

    /**
     * Returns the name of the player for this round.
     * @return Possibly null player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the message for this round.
     * @return Possibly null message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns if the round was valid or not.
     * @return {@code true} if round was valid, else {@code false}
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * toString overload returning {@code Result of Round: <Message>}.
     */
    @Override
    public String toString() {
        return String.format("Result of Round: %s", message);
    }

}
