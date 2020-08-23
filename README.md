# Connect4
Java library providing the logic for playing a game of Connect Four.

## Table Of Contents
1. [Game Details](#game-details)
2. [Download](#download)

## Game Details

### Game Modes

#### Default Connect4 Game
Start a new default Connect4 game with
```java
final Connect4 game = new DefaultConnect4(playerOne, playerTwo);
```

Play a round by calling
```java
final RoundResult result = game.play(xCord);
```
**Note: To be more intuitive for players, not indices (0-6) are expected but columns (1-7).**

The `RoundResult` will give you information if the call was successful or not (for example no more space in a column or if out of bounds). Additionally, a message for the user will be provided.

The game will automatically detect which player a round is assigned to. **So you need to make sure that if one player makes multiple requests, only the first valid is meant to be from the user.**

Example (for example if you integrate it into a Discord Bot):
```
User1#1234: !play 10  // invalid
User1#1234: !play 3   // valid
User1#1234: !play 5   // should be ignored by your bot
```

Since images tell more than words, you can get the current field by calling
```java
final String currentField = game.getCurrentField(true|false);
```
Depending on the boolean flag, the row and column numbers are also provided.

If a game is over can be checked by calling
```java
final boolean gameOver = game.isGameOver();
```

Additionally, you can also check if the game ended in a draw by calling
```java
final boolean draw = game.isDraw();
```

**Note: The Round Result of the final round will also tell that information so you don't have to get it from the logs to tell the user who has won or if it was a draw.**

#### Free positioned Connect4 Game

Due to some.. retardation, I designed the game so that a player is able to determine column and row. I recognized my mistake first when starting to write tests. After that I implemented the correct / default mode but I implemented it so that existing code can be used for it. So feel free to play a bigger version of Tic-Tac-Toe like that.

Start a new default Connect4 game with
```java
final Connect4 game = new FreePositionedConnect4(playerOne, playerTwo);
```

Play a round by calling
```java
final RoundResult result = game.play(xCord, yCord);
```

For the rest, the same rules from [above](#default-connect4-game) apply to this mode.

### Game Log

I also implemented a game log so that you can provide information for each played round can be provided to the players without an own implementation.

You can get the game log for a game with
```java
final GameLog gameLog = game.getGameLog();
```

You can get the whole game as a string, information for every valid round, all invalid rounds and many more. Check the `GameLog` class for all methods.

## Download
Latest Version: version

**Make sure to replace the `VERSION` below with a real version as the one shown above!**

**The builds are distributed to [JCenter through Bintray](https://bintray.com/dmanstrator/maven/Connect4) so you need to add JCenter to the repositories!**

### Maven
```xml
<repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>https://jcenter.bintray.com</url>
</repository>

<dependency>
    <groupId>tk.dmanstrator</groupId>
    <artifactId>Connect4</artifactId>
    <version>VERSION</version>
</dependency>
```

### Gradle
```groovy
repositories {
    jcenter()
}

dependencies {
    implementation group: 'tk.dmanstrator', name: 'Connect4', version: 'VERSION'
}
```