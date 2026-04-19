package com.sc2002.arena.engine;

import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;

import java.util.ArrayList;
import java.util.List;

public class BattleContext {
    private final Player player;
    private final List<Enemy> enemies;
    private int round;

    public BattleContext(Player player, List<Enemy> enemies, int round) {
        this.player = player;
        this.enemies = enemies;
        this.round = round;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<Enemy> getLivingEnemies() {
        List<Enemy> living = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                living.add(enemy);
            }
        }
        return living;
    }

    public List<Combatant> getAllCombatants() {
        List<Combatant> combatants = new ArrayList<>();
        if (player.isAlive()) {
            combatants.add(player);
        }
        combatants.addAll(getLivingEnemies());
        return combatants;
    }
}
