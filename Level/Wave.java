package com.sc2002.arena.level;

import com.sc2002.arena.combat.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Wave {
    private final List<Enemy> enemies;

    public Wave(List<Enemy> enemies) {
        this.enemies = new ArrayList<>(enemies);
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public boolean isCleared() {
        return enemies.stream().noneMatch(Enemy::isAlive);
    }

    public Wave createCopy() {
        List<Enemy> copies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            copies.add(enemy.createCopy());
        }
        return new Wave(copies);
    }
}
