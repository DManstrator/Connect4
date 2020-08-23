package tk.dmanstrator.connectfour.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tk.dmanstrator.connectfour.log.GameLogEntry.RoundTitle;

/**
 * Class representing a log for a game.
 * @author DManstrator
 *
 */
public class GameLog {

    private final List<GameLogEntry> entries = new ArrayList<>();

    /**
     * Adds a given entry to the internal game log.
     * If an entry with the round number already exists, it won't be added.
     * Invalid entries can be added multiple times.
     * 
     * @param entry Entry to add to the game log
     * @return {@code true} if entry was added, else {@code false} since it was already in the log
     */
    public boolean addEntry(final GameLogEntry entry)  {
        if (entry.getRoundTitle() != RoundTitle.DEFAULT ||
                !getEntryFromRound(entry.getRoundNumber()).isPresent())  {
            return entries.add(entry);
        }  else  {
            return false;
        }
    }

    /**
     * Removes a given entry from the internal game log.
     * 
     * @param entry Entry to remove from the game log
     * @return {@code true} if entry was removed, else {@code false} since it wasn't in the log
     */
    public boolean removeEntry(final GameLogEntry entry)  {
        return entries.remove(entry);  // checks for null and contains
    }

    /**
     * Removes the entry with a given round number from the log.
     * 
     * @param roundNr Round number of entry to remove from the game log
     * @return {@code true} if entry was removed, else {@code false} since it wasn't in the log
     */
    public boolean removeEntryFromRound(final int roundNr)  {
        return removeEntry(getEntryFromRound(roundNr).orElse(null));
    }

    /**
     * Gets the log entry from a given position.
     * If the given index is out of bounds, {@code null} will be returned.
     * 
     * @param index Index to access in the internal log
     * @return Possibly null entry at the given index.
     */
    public GameLogEntry getEntry(final int index)  {
        if (index < 0 || index >= entries.size())  {
            return null;
        }  else  {
            return entries.get(index);
        }
    }

    /**
     * Gets the log entry from a specific round.
     * 
     * @param roundNr Round number to check the game log for
     * @return Possibly empty optional containing the entry from the given round
     */
    public Optional<GameLogEntry> getEntryFromRound(final int roundNr)  {
        return entries.stream()
                .filter(entry -> entry.getRoundNumber() == roundNr)
                .findFirst();
    }

    /**
     * Gets the last entry from the log.
     * @return Possibly null entry being the last entry from the log
     */
    public GameLogEntry getLastEntry()  {
        return entries.isEmpty()
            ? null
            : getEntry(entries.size() - 1);
    }

    /**
     * Returns an immutable view of the current entries.
     * @return An immutable view of the current entries
     */
    public List<GameLogEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Returns all valid entries from the log.
     * @return All valid entries from the log
     */
    public List<GameLogEntry> getValidEntries()  {
        return getEntries(true);
    }

    /**
     * Returns all invalid entries from the log.
     * @return All invalid entries from the log
     */
    public List<GameLogEntry> getInvalidEntries()  {
        return getEntries(false);
    }

    /**
     * Gets all valid or invalid entries from the log as a string separated with a newline.
     * 
     * @param valid Flag determining if valid or invalid entries are wanted
     * @return (In)valid entries from the log as a string
     */
    public String getEntriesAsString(final boolean valid)  {
        return getEntriesAsString(getEntries(valid));
    }

    /**
     * Gets all entries from the log as a string separated with a newline.
     * @return All entries from the log as a string
     */
    public String getAllEntriesAsString()  {
        return getEntriesAsString(entries);
    }

    /**
     * Gets all entries from the given list and collects it to a string.
     * 
     * @param entries List of entry to collect
     * @return All entries from the given list as a string
     */
    private String getEntriesAsString(final List<GameLogEntry> entries)  {
        return entries.stream()
                .map(GameLogEntry::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        }

    /**
     * Returns all valid or invalid entries from the log.
     * 
     * @param valid Flag determining if valid or invalid entries are wanted
     * @return All (in)valid entries from the log
     */
    private List<GameLogEntry> getEntries(final boolean valid)  {
        List<GameLogEntry> entries = new ArrayList<>(this.entries);
        entries.removeIf(entry -> entry.isValidRound() != valid);
        return entries;
    }

    /**
     * Returns all entries as a string.
     * @return All entries as a string
     * @see GameLog#getAllEntriesAsString()
     */
    @Override
    public String toString() {
        return getAllEntriesAsString();
    }

}
