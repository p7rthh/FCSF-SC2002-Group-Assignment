package com.sc2002.arena.effect;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect() {
        super("Smoke Bomb Invulnerability", DurationUnit.ROUND, 2);
    }

    @Override
    public boolean negatesIncomingEnemyDamage() {
        return true;
    }
}
