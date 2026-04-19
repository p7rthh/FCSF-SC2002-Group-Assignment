package com.sc2002.arena.action;

import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.shared.ActionType;

public class BasicAttackAction implements Action {
    private final Combatant attacker;
    private final Combatant target;
    private final ActionResolver resolver;

    public BasicAttackAction(Combatant attacker, Combatant target, ActionResolver resolver) {
        this.attacker = attacker;
        this.target = target;
        this.resolver = resolver;
    }

    @Override
    public ActionResult execute() {
        if (attacker == null || target == null || !attacker.isAlive() || !target.isAlive()) {
            return new ActionResult(0, "Attack failed due to invalid target.", false);
        }
        int damage = resolver.calculateDamage(attacker, target);
        int actualDamage = target.takeDamage(damage);
        String message = String.format("%s uses BasicAttack on %s for %d damage.",
                attacker.getName(), target.getName(), actualDamage);
        return new ActionResult(actualDamage, message, !target.isAlive());
    }

    @Override
    public ActionType getActionType() {
        return ActionType.BASIC_ATTACK;
    }
}
