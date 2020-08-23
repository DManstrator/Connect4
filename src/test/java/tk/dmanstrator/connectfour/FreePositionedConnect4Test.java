package tk.dmanstrator.connectfour;

import org.junit.Test;

import tk.dmanstrator.connectfour.log.GameLogEntry;

import org.junit.Assert;
import org.junit.Before;

public class FreePositionedConnect4Test extends Connect4Test {
    private Connect4 connect4;

    @Override
    public Connect4 getConnect4Game() {
        return connect4;
    }

    @Before
    public void setup()  {
        connect4 = new FreePositionedConnect4(PLAYER1, PLAYER2);
    }

    @Override
    public void testPlayers() {
        Assert.assertEquals(PLAYER1, connect4.getCurrentPlayerName());
        connect4.play(3, 3);
        Assert.assertEquals(PLAYER2, connect4.getCurrentPlayerName());
    }

    @Override
    public void testNormalGame()  {
        connect4.play(7, 6);
        connect4.play(3, 6);
        connect4.play(7, 5);
        connect4.play(1, 6);
        connect4.play(6, 6);
        connect4.play(3, 5);
        connect4.play(6, 5);
        connect4.play(4, 6);
        connect4.play(3, 4);
        connect4.play(2, 6);
        Assert.assertEquals("Game should be over", true, connect4.isGameOver());
        final GameLogEntry lastEntry = connect4.getGameLog().getLastEntry();
        Assert.assertEquals("[GameOver] Player Test2 has won the game!", lastEntry.getAsString());
    }
            @Override
    public void testLast4OfMatrixAreValid()  {
        connect4.play(5, 6);
        connect4.play(1, 6);
        connect4.play(3, 6);
        connect4.play(3, 5);
        connect4.play(2, 6);
        connect4.play(7, 6);
        connect4.play(7, 5);
        connect4.play(7, 4);
        connect4.play(6, 6);
        connect4.play(1, 5);
        connect4.play(2, 5);
        connect4.play(7, 3);
        connect4.play(2, 4);
        connect4.play(6, 5);
        connect4.play(7, 2);
        connect4.play(1, 4);
        connect4.play(1, 3);
        connect4.play(4, 6);
        connect4.play(2, 3);
        Assert.assertEquals("Game should be over", true, connect4.isGameOver());
    }

    @Override
    public void testFiveInARow()  {
        connect4.play(1, 6);
        connect4.play(1, 5);
        connect4.play(2, 6);
        connect4.play(2, 5);
        connect4.play(3, 6);
        connect4.play(3, 5);
        connect4.play(5, 6);
        connect4.play(5, 5);
        connect4.play(4, 6);
        connect4.play(4, 5);
        Assert.assertEquals("Game should be over", true, connect4.isGameOver());
    }

    @Override
    public void testInRange()  {
        // X
        connect4.play(-1, 1);
        connect4.play(0, 1);
        connect4.play(Connect4.WIDTH + 1, 1);

        // Y
        connect4.play(1, -1);
        connect4.play(1, 0);
        connect4.play(1, Connect4.HEIGHT + 1);
        Assert.assertEquals("Game should have no valid round yet", 0, connect4.getPlayedRounds());
    }

    @Override
    public void testDraw()  {
        connect4.play(3, 6);
        connect4.play(2, 6);
        connect4.play(6, 6);
        connect4.play(4, 6);
        connect4.play(5, 6);
        connect4.play(7, 6);
        connect4.play(3, 5);
        connect4.play(2, 5);
        connect4.play(6, 5);
        connect4.play(4, 5);
        connect4.play(3, 4);
        connect4.play(2, 4);
        connect4.play(2, 3);
        connect4.play(2, 2);
        connect4.play(5, 5);
        connect4.play(7, 5);
        connect4.play(7, 4);
        connect4.play(6, 4);
        connect4.play(6, 3);
        connect4.play(5, 4);
        connect4.play(2, 1);
        connect4.play(6, 2);
        connect4.play(4, 4);
        connect4.play(7, 3);
        connect4.play(7, 2);
        connect4.play(3, 3);
        connect4.play(1, 6);
        connect4.play(1, 5);
        connect4.play(3, 2);
        connect4.play(7, 1);
        connect4.play(1, 4);
        connect4.play(1, 3);
        connect4.play(5, 3);
        connect4.play(4, 3);
        connect4.play(4, 2);
        connect4.play(3, 1);
        connect4.play(5, 2);
        connect4.play(5, 1);
        connect4.play(1, 2);
        connect4.play(4, 1);
        connect4.play(6, 1);
        connect4.play(1, 1);
        Assert.assertEquals(true, connect4.isDraw());
        Assert.assertEquals(true, connect4.isGameOver());
    }

    @Override
    public void testFieldPrints()  {
        connect4.play(1, 6);
        connect4.play(3, 6);
        connect4.play(2, 6);
        connect4.play(5, 6);
        connect4.play(6, 6);
        connect4.play(1, 5);
        connect4.play(2, 5);
        connect4.play(7, 6);
        connect4.play(3, 5);
        connect4.play(4, 6);
        connect4.play(4, 5);
        connect4.play(5, 5);
        connect4.play(7, 5);
        connect4.play(6, 5);
        connect4.play(3, 4);
        connect4.play(5, 4);
        connect4.play(4, 4);
        connect4.play(4, 3);
        super.testFieldPrints();
    }

    @Override
    public void testGameLog() {
        connect4.play(0, 6);
        connect4.play(1, 6);
        connect4.play(2, 6);
        connect4.play(3, 6);
        connect4.play(4, 6);
        connect4.play(5, 6);
        connect4.play(6, 6);
        connect4.play(7, 6);
        final RoundResult lastRound = connect4.play(8, 6);
        Assert.assertEquals("Result of Round: Chosen position (8, 6) is not in range!", lastRound.toString());
        super.testGameLog();
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testOneInputParam()  {
        connect4.play(3);
    }

    @Test
    public void testOverrideUsedField()  {
        RoundResult round1 = connect4.play(3, 3);
        Assert.assertEquals(true, round1.isValid());
        Assert.assertEquals(PLAYER1, round1.getPlayerName());
        final RoundResult round2 = connect4.play(3, 3);
        Assert.assertEquals(false, round2.isValid());
        Assert.assertEquals(PLAYER2, round2.getPlayerName());
        final RoundResult round3 = connect4.play(4, 2);
        Assert.assertEquals(true, round3.isValid());
        Assert.assertEquals(PLAYER2, round3.getPlayerName());
    }

}
