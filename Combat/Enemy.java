package com.sc2002.arena.combat;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.engine.EnemyActionStrategy;

public abstract class Enemy extends Combatant {
    protected Enemy(String name, int maxHp, int baseAttack, int baseDefense, int speed) {
        super(name, maxHp, baseAttack, baseDefense, speed);
    }

    public Action decideAction(Player target, BattleContext context, EnemyActionStrategy strategy, ActionResolver resolver) {
        return strategy.chooseAction(this, target, context, resolver);
    }

    public abstract Enemy createCopy();
}
