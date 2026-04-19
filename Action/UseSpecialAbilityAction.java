package com.sc2002.arena.action;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.shared.ActionType;

public class UseSpecialAbilityAction implements Action {
    private final Player actor;
    private final Enemy target;
    private final BattleContext context;
    private final ActionResolver resolver;
    private final boolean freeUse;

    public UseSpecialAbilityAction(Player actor, Enemy target, BattleContext context, ActionResolver resolver, boolean freeUse) {
        this.actor = actor;
        this.target = target;
        this.context = context;
        this.resolver = resolver;
        this.freeUse = freeUse;
    }

    @Override
    public ActionResult execute() {
        if (!freeUse && !actor.canUseSpecialSkill()) {
            return new ActionResult(0,
                    actor.getName() + " cannot use SpecialSkill because it is on cooldown.",
                    false);
        }

        if (actor.specialSkillRequiresTarget() && (target == null || !target.isAlive())) {
            return new ActionResult(0,
                    actor.getName() + " cannot use SpecialSkill due to an invalid target.",
                    false);
        }

        if (!actor.specialSkillRequiresTarget() && context.getLivingEnemies().isEmpty()) {
            return new ActionResult(0,
                    actor.getName() + " cannot use SpecialSkill because there are no living enemies.",
                    false);
        }

        ActionResult result = actor.useSpecialSkill(context, resolver, target);

        if (!freeUse) {
            actor.resetCooldown();
        }

        return result;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.SPECIAL_SKILL;
    }
}
