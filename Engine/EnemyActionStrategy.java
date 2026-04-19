package com.sc2002.arena.engine;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;

public interface EnemyActionStrategy {
    Action chooseAction(Enemy enemy, Player target, BattleContext context, ActionResolver resolver);
}
