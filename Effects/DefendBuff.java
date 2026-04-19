package com.sc2002.arena.effect;

public class DefendBuff extends StatusEffect {
    public DefendBuff() {
        super("Defend", DurationUnit.ROUND, 2);
    }

    @Override
    public int getDefenseModifier() {
        return 10;
    }
}
