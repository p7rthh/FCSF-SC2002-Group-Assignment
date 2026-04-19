package com.sc2002.arena.engine;

public class BattleResult {
    private final boolean victory;
    private final int totalRounds;
    private final int remainingHP;
    private final int enemiesRemaining;

    public BattleResult(boolean victory, int totalRounds, int remainingHP, int enemiesRemaining) {
        this.victory = victory;
        this.totalRounds = totalRounds;
        this.remainingHP = remainingHP;
        this.enemiesRemaining = enemiesRemaining;
    }

    public boolean isVictory() {
        return victory;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getRemainingHP() {
        return remainingHP;
    }

    public int getEnemiesRemaining() {
        return enemiesRemaining;
    }
}
