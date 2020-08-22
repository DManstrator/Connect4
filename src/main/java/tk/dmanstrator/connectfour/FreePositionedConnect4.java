package tk.dmanstrator.connectfour;

/**
 * Implementation for a free positioned Connect4 game.
 * This exists since when I started developing, I implemented it so a player
 * can choose both x and y. Later when I started testing it I recognized my mistake.
 * 
 * @author DManstrator
 *
 */
public class FreePositionedConnect4 extends Connect4 {

    /**
     * Constructor for a 2 player game free positioned Connect4 game.
     * @param firstPlayerName Name of the first player
     * @param secondPlayerName Name of the second player
     */
    public FreePositionedConnect4(final String firstPlayerName, final String secondPlayerName)  {
        super(firstPlayerName, secondPlayerName);
    }
    
    /**
     * Overrides the method to throw an {@link UnsupportedOperationException}
     * since it's not allowed to be called on a free positioned Connect4 game.
     */
    @Override
    public RoundResult play(final int x)  {
        throw new UnsupportedOperationException();
    }

}
