package com.sc2002.arena.effect;

public class StunEffect extends StatusEffect {
    public StunEffect() {
        super("Stunned", DurationUnit.OWNER_TURN, 2);
    }

    @Override
    public boolean preventsAction() {
        return true;
    }
}
