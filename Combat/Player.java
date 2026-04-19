package com.sc2002.arena.combat;

import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;

public abstract class Player extends Combatant {
    private final Inventory inventory;

    protected Player(String name, int maxHp, int baseAttack, int baseDefense, int speed) {
        super(name, maxHp, baseAttack, baseDefense, speed);
        this.inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean canUseSpecialSkill() {
        return getCooldown() == 0;
    }

    public int getDisplayedCooldownAtTurnStart() {
        return Math.max(0, getCooldown() - 1);
    }

    public abstract boolean specialSkillRequiresTarget();

    public abstract ActionResult useSpecialSkill(BattleContext context, ActionResolver resolver, Enemy target);
}
