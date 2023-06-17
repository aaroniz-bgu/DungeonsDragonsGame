package org.dndoop.tests;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.*;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.events.GameEventNotifier;
import org.dndoop.game.utils.files.TileFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private List<Player> players;
    private List<Enemy> enemies;
    private GameEventNotifier NOTIFIER;
    private TileFactory factory;
    private Direction direction;
    private List<Character> enemyChars;
    private int numOfPlayerCharacters;

    @BeforeAll
    void Generate() {
        NOTIFIER = new GameEventNotifier();
        factory = new TileFactory(NOTIFIER,null); //TODO see if this makes issues giving null.
        enemyChars = new LinkedList<>();
        enemyChars.add('s');
        enemyChars.add('k');
        enemyChars.add('q');
        enemyChars.add('z');
        enemyChars.add('b');
        enemyChars.add('g');
        enemyChars.add('w');
        enemyChars.add('M');
        enemyChars.add('C');
        enemyChars.add('K');
        numOfPlayerCharacters = 7;
    }
    @BeforeEach
    void Construct() {
        for (int i = 0;i<numOfPlayerCharacters;i++) {
            players.add(factory.producePlayer(i));
        }
        int positionIndex = 0;
        for (Character c : enemyChars) {
            enemies.add(factory.produceEnemy(c, new Position(positionIndex, positionIndex),null));
            //TODO see if this makes issues giving null.
            positionIndex++;
        }
    }

    @Test
    void testGainXp() {

    }

    @Test
    void testMoveToEmpty() {

    }

    @Test
    void testMoveToWall() {

    }

    @Test
    void testMoveToEnemy() {

    }
}
