package com.sc2002.arena.effect;

public class ArcaneBlastBuff extends StatusEffect {
    private final int attackBonus;

    public ArcaneBlastBuff(int attackBonus) {
        super("Arcane Blast Bonus", DurationUnit.BATTLE, Integer.MAX_VALUE);
        this.attackBonus = attackBonus;
    }

    @Override
    public int getAttackModifier() {
        return attackBonus;
    }
}
