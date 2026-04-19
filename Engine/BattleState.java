package com.sc2002.arena.engine;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;

import java.util.ArrayList;
import java.util.List;

public class BattleState {
    private int round;
    private final Player player;
    private final List<Enemy> activeEnemies;
    private final List<Enemy> defeatedEnemies;
    private boolean backupPending;

    public BattleState(int round, Player player, List<Enemy> activeEnemies) {
        this.round = round;
        this.player = player;
        this.activeEnemies = new ArrayList<>(activeEnemies);
        this.defeatedEnemies = new ArrayList<>();
        this.backupPending = false;
    }

    public int getRound() {
        return round;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getActiveEnemies() {
        return new ArrayList<>(activeEnemies);
    }

    public List<Enemy> getDefeatedEnemies() {
        return new ArrayList<>(defeatedEnemies);
    }

    public boolean isVictory() {
        return !backupPending && activeEnemies.stream().allMatch(enemy -> !enemy.isAlive());
    }

    public boolean isDefeat() {
        return !player.isAlive();
    }

    public void incrementRound() {
        round++;
    }

    public void addEnemy(Enemy enemy) {
        activeEnemies.add(enemy);
    }

    public void defeatEnemy(Enemy enemy) {
        if (!defeatedEnemies.contains(enemy)) {
            defeatedEnemies.add(enemy);
        }
    }

    public void setBackupPending(boolean backupPending) {
        this.backupPending = backupPending;
    }
}
