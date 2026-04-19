package com.sc2002.arena.action;

import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.effect.DefendBuff;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.shared.ActionType;

public class DefendAction implements Action {
    private final Combatant actor;
    private final BattleContext context;

    public DefendAction(Combatant actor, BattleContext context) {
        this.actor = actor;
        this.context = context;
    }

    @Override
    public ActionResult execute() {
        actor.addEffect(new DefendBuff(), context);
        String message = String.format(
                "%s uses Defend and gains +10 defense for this round and the next round.",
                actor.getName());
        return new ActionResult(0, message, false);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.DEFEND;
    }
}
