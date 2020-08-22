package tk.dmanstrator.connectfour;

/**
 * Implementation for a default Connect4 game.
 * @author DManstrator
 *
 */
public class DefaultConnect4 extends Connect4 {

    private boolean valid = false;

    /**
     * Constructor for a two player default Connect4 game.
     * 
     * @param firstPlayerName Name of the first player
     * @param secondPlayerName Name of the second player
     */
    public DefaultConnect4(final String firstPlayerName, final String secondPlayerName)  {
        super(firstPlayerName, secondPlayerName);
    }

    /**
     * Overrides the method to set a flag.
     * Makes it possible to determine if it is okay to call {@link DefaultConnect4#play(int, int)}.
     */
    @Override
    public RoundResult play(final int x)  {
        valid = true;  // if this is called first, then it's okay
        return super.play(x);
    }

    /**
     * Overrides the method to check for a flag.
     * If the flag is not set, then this method was directly called
     * which is not allowed for a default Connect4 game
     * and a {@link UnsupportedOperationException} is thrown.
     */
    @Override
    public RoundResult play(final int x, final int y)  {
        if (!valid)  {  // play with only x has to be called first
            throw new UnsupportedOperationException();
        }
        valid = false;
        return super.play(x, y);
    }

}
