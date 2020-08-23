package tk.dmanstrator.connectfour;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import tk.dmanstrator.connectfour.log.GameLog;
import tk.dmanstrator.connectfour.log.GameLogEntry;
import tk.dmanstrator.connectfour.log.GameLogEntryBuilder;
import tk.dmanstrator.connectfour.log.GameLogEntry.RoundTitle;

public abstract class Connect4Test {
    private static final String NEWLINE = System.lineSeparator();

    protected static final String PLAYER1 = "Test1";
    protected static final String PLAYER2 = "Test2";

    @Test
    public void testPlayers()  {
        final Connect4 connect4 = getConnect4Game();
        Assert.assertEquals(null, connect4.getGameLog().getLastEntry());

        String namePlayerOne = connect4.getFirstPlayerName();
        String namePlayerTwo = connect4.getSecondPlayerName();

        Assert.assertEquals(PLAYER1, namePlayerOne);
        Assert.assertEquals(PLAYER2, namePlayerTwo);
    }

    @Test
    public abstract void testNormalGame();

    @Test
    public abstract void testLast4OfMatrixAreValid();

    @Test
    public abstract void testFiveInARow();

    @Test
    public abstract void testInRange();

    @Test
    public abstract void testDraw();

    @Test
    public void testFieldPrints()  {
        final Connect4 connect4 = getConnect4Game();
        final String withoutDetails = connect4.toString();
        final String withDetails = connect4.getCurrentField();
        final String expectedFieldWithDetails =
            "  1234567"  + NEWLINE +
            "1[       ]" + NEWLINE +
            "2[       ]" + NEWLINE +
            "3[   y   ]" + NEWLINE +
            "4[  rry  ]" + NEWLINE +
            "5[yrrryyr]" + NEWLINE +
            "6[rryyyry]";
        final String expectedFieldWithoutDetails =
            "[       ]" + NEWLINE +
            "[       ]" + NEWLINE +
            "[   y   ]" + NEWLINE +
            "[  rry  ]" + NEWLINE +
            "[yrrryyr]" + NEWLINE +
            "[rryyyry]";
        Assert.assertEquals("Expected field to be identical",
                expectedFieldWithDetails, withDetails);
        Assert.assertEquals("Expected field to be identical",
                expectedFieldWithoutDetails, withoutDetails);
    }

    @Test
    public void testGameLog() {
        final Connect4 connect4 = getConnect4Game();
        final GameLog gameLog = connect4.getGameLog();
        Assert.assertEquals("Expected 2 invalid entries",
                2, gameLog.getInvalidEntries().size());
        Assert.assertEquals("Expected 7 valid entries",
                7, gameLog.getValidEntries().size());
        Assert.assertEquals("Expected 9 entries in total",
                9, gameLog.getEntries().size());

        final GameLogEntry lastEntry = gameLog.getLastEntry();
        Assert.assertEquals(-1, lastEntry.getRoundNumber());
        Assert.assertEquals(2, lastEntry.getPlayerNumber());
        Assert.assertEquals(RoundTitle.INVALID, lastEntry.getRoundTitle());
        Assert.assertEquals("Chosen position (8, 6) is not in range!", lastEntry.getMessage());
        Assert.assertEquals("[invalid!] [Player 2]: Chosen position (8, 6) is not in range!", lastEntry.toString());

        final boolean addedInvalidDuplicate = gameLog.addEntry(lastEntry);
        Assert.assertEquals(true, addedInvalidDuplicate);

        final boolean removedExistingEntry = gameLog.removeEntry(lastEntry);
        Assert.assertEquals(true, removedExistingEntry);

        final GameLogEntry negativeEntry = gameLog.getEntry(-1);
        final GameLogEntry nonExistingEntry = gameLog.getEntry(10);
        Assert.assertEquals(null, negativeEntry);
        Assert.assertEquals(null, nonExistingEntry);

        final boolean removeNonExistingEntry = gameLog.removeEntryFromRound(10);
        Assert.assertEquals(false, removeNonExistingEntry);

        Optional<GameLogEntry> optEntryFromRound = gameLog.getEntryFromRound(1);
        Assert.assertEquals(true, optEntryFromRound.isPresent());

        final GameLogEntry firstEntry = optEntryFromRound.get();
        final boolean addedDuplicate = gameLog.addEntry(firstEntry);
        Assert.assertEquals(false, addedDuplicate);

        final GameLogEntry emptyEntry = new GameLogEntryBuilder().build();
        final boolean removeEmptyEntry = gameLog.removeEntry(emptyEntry);
        Assert.assertEquals(false, removeEmptyEntry);

        Assert.assertEquals("[invalid!] [Player 1]: Chosen position (0, 6) is not in range!" + System.lineSeparator() +
                "[invalid!] [Player 2]: Chosen position (8, 6) is not in range!", gameLog.getEntriesAsString(false));
        Assert.assertEquals("[invalid!] [Player 1]: Chosen position (0, 6) is not in range!" + System.lineSeparator() +
                "[Round 01] [Player 1]: Position (1, 6) belongs now to player Test1" + System.lineSeparator() +
                "[Round 02] [Player 2]: Position (2, 6) belongs now to player Test2" + System.lineSeparator() +
                "[Round 03] [Player 1]: Position (3, 6) belongs now to player Test1" + System.lineSeparator() +
                "[Round 04] [Player 2]: Position (4, 6) belongs now to player Test2" + System.lineSeparator() +
                "[Round 05] [Player 1]: Position (5, 6) belongs now to player Test1" + System.lineSeparator() +
                "[Round 06] [Player 2]: Position (6, 6) belongs now to player Test2" + System.lineSeparator() +
                "[Round 07] [Player 1]: Position (7, 6) belongs now to player Test1" + System.lineSeparator() +
                "[invalid!] [Player 2]: Chosen position (8, 6) is not in range!", gameLog.toString());
    }

    public abstract Connect4 getConnect4Game();

}