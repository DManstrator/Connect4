package tk.dmanstrator.connectfour;

import org.junit.Test;

import tk.dmanstrator.connectfour.log.GameLogEntry;

import org.junit.Assert;
import org.junit.Before;

public class DefaultConnect4Test extends Connect4Test  {
    private Connect4 connect4;

    @Override
    public Connect4 getConnect4Game() {
        return connect4;
    }

    @Before
    public void setup()  {
        connect4 = new DefaultConnect4(PLAYER1, PLAYER2);
    }

    @Override
    public void testPlayers() {
        super.testPlayers();
        Assert.assertEquals(PLAYER1, connect4.getCurrentPlayerName());
        connect4.play(3);
        Assert.assertEquals(PLAYER2, connect4.getCurrentPlayerName());
    }

    @Override
    public void testNormalGame()  {
        connect4.play(7);
        connect4.play(3);
        connect4.play(7);
        connect4.play(1);
        connect4.play(6);
        connect4.play(3);
        connect4.play(6);
        connect4.play(4);
        connect4.play(3);
        connect4.play(2);

        Assert.assertEquals("Game should be over", true, connect4.isGameOver());
        GameLogEntry lastEntry = connect4.getGameLog().getLastEntry();
        Assert.assertEquals("[GameOver] Player Test2 has won the game!", lastEntry.getAsString());
    }

    @Override
    public void testLast4OfMatrixAreValid()  {
        connect4.play(5);
        connect4.play(1);
        connect4.play(3);
        connect4.play(3);
        connect4.play(2);
        connect4.play(7);
        connect4.play(7);
        connect4.play(7);
        connect4.play(6);
        connect4.play(1);
        connect4.play(2);
        connect4.play(7);
        connect4.play(2);
        connect4.play(6);
        connect4.play(7);
        connect4.play(1);
        connect4.play(1);
        connect4.play(4);
        connect4.play(2);
        Assert.assertEquals("Game should be over", true, connect4.isGameOver());
    }

    @Override
    public void testFiveInARow()  {
        connect4.play(1);
        connect4.play(1);
        connect4.play(2);
        connect4.play(2);
        connect4.play(3);
        connect4.play(3);
        connect4.play(5);
        connect4.play(5);
        connect4.play(4);
        connect4.play(4);
        Assert.assertEquals("Game should be over", true, connect4.isGameOver());
    }

    @Override
    public void testInRange()  {
        connect4.play(-1);
        connect4.play(0);
        connect4.play(Connect4.WIDTH + 1);
        Assert.assertEquals("Game should have no valid round yet",
                0, connect4.getPlayedRounds());
        for (int c = 0; c < Connect4.HEIGHT; c++)  {
            connect4.play(3);
        }
        final RoundResult round = connect4.play(3);
        Assert.assertEquals("Last move should not be possible in that row",
                false, round.isValid());
    }

    @Override
    public void testDraw()  {
        connect4.play(3);
        connect4.play(2);
        connect4.play(6);
        connect4.play(4);
        connect4.play(5);
        connect4.play(7);
        connect4.play(3);
        connect4.play(2);
        connect4.play(6);
        connect4.play(4);
        connect4.play(3);
        connect4.play(2);
        connect4.play(2);
        connect4.play(2);
        connect4.play(5);
        connect4.play(7);
        connect4.play(7);
        connect4.play(6);
        connect4.play(6);
        connect4.play(5);
        connect4.play(2);
        connect4.play(6);
        connect4.play(4);
        connect4.play(7);
        connect4.play(7);
        connect4.play(3);
        connect4.play(1);
        connect4.play(1);
        connect4.play(3);
        connect4.play(7);
        connect4.play(1);
        connect4.play(1);
        connect4.play(5);
        connect4.play(4);
        connect4.play(4);
        connect4.play(3);
        connect4.play(5);
        connect4.play(5);
        connect4.play(1);
        connect4.play(4);
        connect4.play(6);
        connect4.play(1);
        Assert.assertEquals(true, connect4.isDraw());
        Assert.assertEquals(true, connect4.isGameOver());
    }

    @Override
    public void testFieldPrints()  {
        connect4.play(1);
        connect4.play(3);
        connect4.play(2);
        connect4.play(5);
        connect4.play(6);
        connect4.play(1);
        connect4.play(2);
        connect4.play(7);
        connect4.play(3);
        connect4.play(4);
        connect4.play(4);
        connect4.play(5);
        connect4.play(7);
        connect4.play(6);
        connect4.play(3);
        connect4.play(5);
        connect4.play(4);
        connect4.play(4);
        super.testFieldPrints();
    }

    @Override
    public void testGameLog() {
        connect4.play(0);
        connect4.play(1);
        connect4.play(2);
        connect4.play(3);
        connect4.play(4);
        connect4.play(5);
        connect4.play(6);
        connect4.play(7);
        final RoundResult lastRound = connect4.play(8);
        Assert.assertEquals("Result of Round: Chosen position (8, 6) is not in range!",lastRound.toString());

        super.testGameLog();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testTwoInputParams()  {
        connect4.play(4, 2);
    }

}
