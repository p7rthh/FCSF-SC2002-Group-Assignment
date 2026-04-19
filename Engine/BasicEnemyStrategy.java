package com.sc2002.arena.engine;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;

public class BasicEnemyStrategy implements EnemyActionStrategy {
    @Override
    public Action chooseAction(Enemy enemy, Player target, BattleContext context, ActionResolver resolver) {
        return new BasicAttackAction(enemy, target, resolver);
    }
}
