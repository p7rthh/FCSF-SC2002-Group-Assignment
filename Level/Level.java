package com.sc2002.arena.level;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Goblin;
import com.sc2002.arena.combat.Wolf;
import com.sc2002.arena.shared.DifficultyLevel;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int number;
    private final DifficultyLevel difficulty;
    private final Wave initialWave;
    private final Wave backupWave;
    private boolean backupSpawned;

    public Level(int number, DifficultyLevel difficulty, Wave initialWave, Wave backupWave) {
        this.number = number;
        this.difficulty = difficulty;
        this.initialWave = initialWave;
        this.backupWave = backupWave;
        this.backupSpawned = false;
    }

    public int getNumber() {
        return number;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public boolean hasBackupWave() {
        return backupWave != null;
    }

    public List<Enemy> createInitialWave() {
        return initialWave.createCopy().getEnemies();
    }

    public List<Enemy> createBackupWave() {
        if (backupWave == null) {
            return new ArrayList<>();
        }
        return backupWave.createCopy().getEnemies();
    }

    public boolean shouldSpawnBackup(List<Enemy> currentEnemies) {
        if (backupWave == null || backupSpawned) {
            return false;
        }
        for (Enemy enemy : currentEnemies) {
            if (enemy.isAlive()) {
                return false;
            }
        }
        return true;
    }

    public void markBackupSpawned() {
        backupSpawned = true;
    }

    public boolean isBackupSpawned() {
        return backupSpawned;
    }

    public static Level fromDifficulty(DifficultyLevel difficultyLevel) {
        return switch (difficultyLevel) {
            case EASY -> new Level(1, DifficultyLevel.EASY,
                    new Wave(List.of(new Goblin("Goblin A"), new Goblin("Goblin B"), new Goblin("Goblin C"))),
                    null);
            case MEDIUM -> new Level(2, DifficultyLevel.MEDIUM,
                    new Wave(List.of(new Goblin("Goblin"), new Wolf("Wolf"))),
                    new Wave(List.of(new Wolf("Wolf A"), new Wolf("Wolf B"))));
            case HARD -> new Level(3, DifficultyLevel.HARD,
                    new Wave(List.of(new Goblin("Goblin A"), new Goblin("Goblin B"))),
                    new Wave(List.of(new Goblin("Goblin C"), new Wolf("Wolf A"), new Wolf("Wolf B"))));
        };
    }
}
