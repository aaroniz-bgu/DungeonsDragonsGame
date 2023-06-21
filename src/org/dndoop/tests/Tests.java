package org.dndoop.tests;
import org.dndoop.game.board.GameBoard;
import org.dndoop.game.board.GameManager;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.players.*;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.utils.files.TileFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    private static final int numOfPlayerCharacters = 7;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private String[] GenerateMapWithTileBelowPlayer(Character c) {
        String[] map = new String[] {
                "####",
                "#@"+c.toString()+"#",
                "####"
        };
        return map;
    }
    private Player player;
    private GameManager manager;
    private GameBoard board;
    private TileFactory factory;

    private final static Character[] ENEMIESMAP = new Character[] {
            's','k','q','z','b','g','w','M','C','K','B','Q','D'
    };

    private final static Character[] NONTRAPENEMIESMAP = new Character[] {
            's','k','q','z','b','g','w','M','C','K'
    };

    private void CheckIfGivenStringMatchesSystemOut(String outputExpected) {
        assertEquals(outputExpected.trim(), outputStreamCaptor.toString().trim());
    }

    /**
     * Generates a random number in a given range [min, max)
     * @param min
     * @param max
     * @return the random number
     */
    private int generateNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Generates a random number in the given range [0, max)
     * @param max
     * @return the random number
     */
    private int generateNumber(int max) {
        return (int) (Math.random() * (max));
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @BeforeEach
    void Construct() {
        manager = new GameManager();
        this.factory = manager.generateAndReturnFactory();
        player = factory.producePlayer(generateNumber(numOfPlayerCharacters)+1);
        manager.setPlayer(player);
    }

    private void setBoardWithGivenMap(String[] map) {
        board = new GameBoard(factory, player, map);
        manager.setBoard(board);
        manager.getNotifier().addListener(board);
    }

    //region Player tests
    @Test
    void testSwapPlacesWithEnemyWhenKilled() {
        String[] map = GenerateMapWithTileBelowPlayer(ENEMIESMAP[generateNumber(ENEMIESMAP.length)]);
        setBoardWithGivenMap(map);
        Enemy enemy = board.getEnemies().get(0);
        Position position = enemy.getPosition();
        player.getHealth().setHealthAmount(100000);
        enemy.getHealth().setHealthAmount(0);
        player.attack(enemy);
        assertTrue(player.getPosition().equals(position));
    }

    @Test
    void testDoNotSwapPlacesWithEnemyWhenNotKilled() {
        String[] map = GenerateMapWithTileBelowPlayer(ENEMIESMAP[generateNumber(ENEMIESMAP.length)]);
        setBoardWithGivenMap(map);
        Enemy enemy = board.getEnemies().get(0);
        Position position = new Position(player.getPosition());
        enemy.getHealth().setHealthAmount(100000);
        player.attack(enemy);
        assertTrue(player.getPosition().equals(position));
    }

    @Test
    void testLevelUp() {
        int attack = player.getStats().getAttackPoints();
        int defense = player.getStats().getDefensePoints();
        int health = player.getHealth().getHealthPool();
        player.gainXp(100);
        player.levelUp();
        assertNotEquals(attack, player.getStats().getAttackPoints());
        assertNotEquals(defense, player.getStats().getDefensePoints());
        assertNotEquals(health, player.getHealth().getHealthPool());
    }

    @Test
    void testAbilityCast() {
        String[] map = new String[] {
                "#q##",
                "z@k#",
                "####"
        };

        for (int i = 1;i<=numOfPlayerCharacters;i++) {
            abilityCastHelper(i);
            setBoardWithGivenMap(map);
            for (int j=0;j<100;j++) { //make them strong so low likelihood of missing
                player.levelUp();
            }
            player.castAbility();
            boolean hpChanged = false;
            for (Enemy enemy : board.getEnemies()) {
                if (enemy.getHealth().getHealthAmount()!=enemy.getHealth().getHealthPool())
                    hpChanged = true;
            }
            assertTrue(hpChanged);
        }

    }

    private void abilityCastHelper(int idx) {
        manager = new GameManager();
        this.factory = manager.generateAndReturnFactory();
        player = factory.producePlayer(idx);
        manager.setPlayer(player);
    }
    //endregion

    //region Enemy tests
    @Test
    void testEnemyKilledRemovedFromBoard() {
        String[] map = GenerateMapWithTileBelowPlayer(ENEMIESMAP[generateNumber(ENEMIESMAP.length)]);
        setBoardWithGivenMap(map);
        Enemy enemy = board.getEnemies().get(0);
        assertFalse(board.getEnemies().isEmpty());
        enemy.defend(enemy.getHealth().getHealthAmount()
                +enemy.getStats().getDefensePoints(),player);
        assertTrue(board.getEnemies().isEmpty());
    }

    @Test
    void testEnemyMoveToPlayer() {
        String[] map = GenerateMapWithTileBelowPlayer(NONTRAPENEMIESMAP[generateNumber(NONTRAPENEMIESMAP.length)]);
        setBoardWithGivenMap(map);
        Enemy enemy = board.getEnemies().get(0);
        player.getHealth().setHealthAmount(0);
        enemy.move(Direction.UP);
        assertEquals(player.getCharacter(),'X');
    }
    //endregion

    //region Unit tests
    @Test
    void testMoveToEmpty() {
        String[] map = GenerateMapWithTileBelowPlayer('.');
        setBoardWithGivenMap(map);
        Position position = new Position(player.getPosition());
        player.move(Direction.DOWN);
        assertFalse(position.equals(player.getPosition()));
    }

    @Test
    void testMoveToWall() {
        String[] map = GenerateMapWithTileBelowPlayer('#');
        setBoardWithGivenMap(map);
        Position position = new Position(player.getPosition());
        player.move(Direction.DOWN);
        assertTrue(position.equals(player.getPosition()));
    }

    /**
     * Very small chance to fail since it's random.
     */
    @Test
    void testAttackDealsDamage() {
        String[] map = GenerateMapWithTileBelowPlayer(ENEMIESMAP[generateNumber(ENEMIESMAP.length)]);
        setBoardWithGivenMap(map);
        Enemy enemy = board.getEnemies().get(0);
        enemy.getHealth().setHealthAmount(10000000);
        player.getStats().increaseAttackPoints(100000);
        int enemyHealth = enemy.getHealth().getHealthAmount();
        player.attack(enemy);
        assertNotEquals(enemyHealth, enemy.getHealth().getHealthAmount());
    }
    //endregion
}

