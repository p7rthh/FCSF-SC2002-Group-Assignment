package com.sc2002.arena.cli;

import com.sc2002.arena.combat.Player;
import com.sc2002.arena.combat.Warrior;
import com.sc2002.arena.combat.Wizard;
import com.sc2002.arena.engine.BasicEnemyStrategy;
import com.sc2002.arena.engine.BattleEngine;
import com.sc2002.arena.engine.SpeedStrategy;
import com.sc2002.arena.item.ItemFactory;
import com.sc2002.arena.level.Level;
import com.sc2002.arena.shared.DifficultyLevel;
import com.sc2002.arena.shared.ItemType;
import com.sc2002.arena.shared.PlayerClassType;

import java.util.List;

public class BattleController {
    private final GameCLI gameCLI;
    private PlayerClassType lastPlayerClassType;
    private List<ItemType> lastItems;
    private DifficultyLevel lastDifficulty;

    public BattleController(GameCLI gameCLI) {
        this.gameCLI = gameCLI;
    }

    public void startNewGame() {
        gameCLI.start();
        lastPlayerClassType = gameCLI.promptPlayerClass();
        lastItems = gameCLI.promptItems();
        lastDifficulty = gameCLI.promptDifficulty();
        playConfiguredGame();
    }

    public void playGame() {
        boolean running = true;
        while (running) {
            startNewGame();
            int option = gameCLI.promptPostGameOption();
            if (option == 1) {
                replayWithSameSettings();
                option = gameCLI.promptPostGameOption();
            }
            if (option == 2) {
                continue;
            }
            if (option == 3) {
                running = false;
            }
        }
    }

    private void replayWithSameSettings() {
        playConfiguredGame();
    }

    private void playConfiguredGame() {
        Player player = createPlayer(lastPlayerClassType);
        for (ItemType itemType : lastItems) {
            player.getInventory().addItem(ItemFactory.create(itemType));
        }
        Level level = Level.fromDifficulty(lastDifficulty);
        BattleEngine engine = new BattleEngine(
                player,
                level,
                new SpeedStrategy(),
                new BasicEnemyStrategy(),
                gameCLI,
                gameCLI);
        engine.runBattle();
    }

    private Player createPlayer(PlayerClassType playerClassType) {
        return switch (playerClassType) {
            case WARRIOR -> new Warrior();
            case WIZARD -> new Wizard();
        };
    }
}
