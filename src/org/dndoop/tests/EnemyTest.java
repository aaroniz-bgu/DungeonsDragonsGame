package org.dndoop.tests;
import org.dndoop.game.tile.enemies.*;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.tile_utils.Direction;
import org.dndoop.game.utils.events.GameEventNotifier;
import org.dndoop.game.utils.files.TileFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    private Enemy enemy;
    private Player player;
    private GameEventNotifier NOTIFIER;
    private TileFactory factory;
    private Direction direction;

    @BeforeEach
    void Construct() {

    }

    @Test
    void testMoveToEmpty() {

    }

    @Test
    void testMoveToWall() {

    }

    @Test
    void testMoveToPlayer() {

    }

}
