package org.dndoop.game.utils.files;

import org.dndoop.game.board.GameBoard;
import org.dndoop.game.tile.Empty;
import org.dndoop.game.tile.Wall;
import org.dndoop.game.tile.enemies.Enemy;
import org.dndoop.game.tile.enemies.Monster;
import org.dndoop.game.tile.enemies.Trap;
import org.dndoop.game.tile.players.Mage;
import org.dndoop.game.tile.players.Player;
import org.dndoop.game.tile.players.Rogue;
import org.dndoop.game.tile.players.Warrior;
import org.dndoop.game.tile.tile_utils.Health;
import org.dndoop.game.tile.tile_utils.Position;
import org.dndoop.game.tile.tile_utils.UnitStats;
import org.dndoop.game.utils.events.GameEventNotifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TileFactory {
    private List<Supplier<Player>> playersList;
    private Map<Character, Supplier<Enemy>> enemiesMap;
    private Player selected;

    private GameEventNotifier gameEventNotifier;

    public TileFactory(GameEventNotifier gameEventNotifier){
        playersList = initPlayers();
        enemiesMap = initEnemies();
        this.gameEventNotifier = gameEventNotifier;
    }

    private Map<Character, Supplier<Enemy>> initEnemies() {
        List<Supplier<Enemy>> enemies = Arrays.asList(
                () -> new Monster('s', "Lannister Solider", 80, 8,3, 25, 3, gameEventNotifier),
                () -> new Monster('k', "Lannister Knight", 200, 14, 8, 50, 4, gameEventNotifier),
                () -> new Monster('q', "Queen's Guard", 400, 20, 15, 100,  5, gameEventNotifier),
                () -> new Monster('z', "Wright", 600, 30, 15,100, 3, gameEventNotifier),
                () -> new Monster('b', "Bear-Wright", 1000, 75, 30, 250,  4, gameEventNotifier),
                () -> new Monster('g', "Giant-Wright",1500, 100, 40,500,   5, gameEventNotifier),
                () -> new Monster('w', "White Walker", 2000, 150, 50, 1000, 6, gameEventNotifier),
                //() -> new Boss('M', "The Mountain", 1000, 60, 25,  500, 6, 5, gameEventNotifier),
                //() -> new Boss('C', "Queen Cersei", 100, 10, 10,1000, 1, 8, gameEventNotifier),
                //() -> new Boss('K', "Night's King", 5000, 300, 150, 5000, 8, 3, gameEventNotifier),
                () -> new Trap('B', "Bonus Trap", 1, 1, 1, 250,  1, 10, gameEventNotifier),
                () -> new Trap('Q', "Queen's Trap", 250, 50, 10, 100, 3, 10, gameEventNotifier),
                () -> new Trap('D', "Death Trap", 500, 100, 20, 250, 1, 10, gameEventNotifier)
        );

        return enemies.stream().collect(Collectors.toMap(s -> s.get().getTile(), Function.identity()));
    }

    private List<Supplier<Player>> initPlayers() {
        return Arrays.asList(
                () -> new Warrior("Jon Snow", 300, 30, 4, 3, gameEventNotifier),
                () -> new Warrior("The Hound", 400, 20, 6, 5, gameEventNotifier),
                () -> new Mage("Melisandre", 100, 5, 1, 300, 30, 15, 5, 6, gameEventNotifier),
                () -> new Mage("Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4, gameEventNotifier),
                () -> new Rogue("Arya Stark", 150, 40, 2, 20, gameEventNotifier),
                () -> new Rogue("Bronn", 250, 35, 3, 50, gameEventNotifier)
                //() -> new Hunter("Ygritte", 220, 30, 2, 6, gameEventNotifier)
        );
    }


    public List<Player> listPlayers(){
        return playersList.stream().map(Supplier::get).collect(Collectors.toList());
    }


    // TODO: Add additional callbacks of your choice

    public Enemy produceEnemy(char tile, Position position, GameBoard gameboard) {
        if (!enemiesMap.containsKey(tile))
            throw new IllegalArgumentException();
        Enemy enemy = enemiesMap.get(tile).get();
        enemy.setPosition(position);
        enemy.setTilesAccess(gameboard);
        return enemy;
    }

    public Player producePlayer(int idx) {
        if (!(idx>0)&&(idx<=playersList.size()))
            throw new IllegalArgumentException();
        Player player = playersList.get(idx).get();
        return player;
    }

    public Empty produceEmpty(Position position) {
        return new Empty(position);
    }

    public Wall produceWall(Position position) {
        return new Wall(position);
    }


}