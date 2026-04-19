package com.sc2002.arena.engine;

public class ActionResult {
    private final int damage;
    private final String message;
    private final boolean targetDefeated;

    public ActionResult(int damage, String message, boolean targetDefeated) {
        this.damage = damage;
        this.message = message;
        this.targetDefeated = targetDefeated;
    }

    public int getDamage() {
        return damage;
    }

    public String getMessage() {
        return message;
    }

    public boolean isTargetDefeated() {
        return targetDefeated;
    }
}
