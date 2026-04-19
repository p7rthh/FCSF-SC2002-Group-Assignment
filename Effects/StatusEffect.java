package com.sc2002.arena.effect;

import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.engine.BattleContext;

public abstract class StatusEffect {
    private final String name;
    private final DurationUnit durationUnit;
    private int remainingDuration;

    protected StatusEffect(String name, DurationUnit durationUnit, int remainingDuration) {
        this.name = name;
        this.durationUnit = durationUnit;
        this.remainingDuration = remainingDuration;
    }

    public String getName() {
        return name;
    }

    public DurationUnit getDurationUnit() {
        return durationUnit;
    }

    public boolean isExpired() {
        return remainingDuration <= 0;
    }

    public void decrement() {
        if (remainingDuration != Integer.MAX_VALUE && remainingDuration > 0) {
            remainingDuration--;
        }
    }

    public void onApply(Combatant owner, BattleContext context) {
    }

    public void onOwnerTurnStart(Combatant owner, BattleContext context) {
    }

    public void onOwnerTurnEnd(Combatant owner, BattleContext context) {
    }

    public void onRoundEnd(Combatant owner, BattleContext context) {
    }

    public void expire(Combatant owner, BattleContext context) {
    }

    public int getAttackModifier() {
        return 0;
    }

    public int getDefenseModifier() {
        return 0;
    }

    public boolean negatesIncomingEnemyDamage() {
        return false;
    }

    public boolean preventsAction() {
        return false;
    }
}
