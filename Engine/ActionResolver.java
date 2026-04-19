package com.sc2002.arena.engine;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.combat.Enemy;

public class ActionResolver {
    public ActionResult execute(Action action) {
        return action.execute();
    }

    public int calculateDamage(Combatant attacker, Combatant target) {
        if (attacker instanceof Enemy && target.negatesIncomingEnemyDamage()) {
            return 0;
        }
        return Math.max(0, attacker.getEffectiveAttack() - target.getEffectiveDefense());
    }
}
