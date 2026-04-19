package com.sc2002.arena.item;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.shared.ItemType;

public class PowerStone extends Item {
    public PowerStone() {
        super("Power Stone", ItemType.POWER_STONE);
    }

    @Override
    public ActionResult use(Player user, BattleContext context, Enemy target, ActionResolver resolver) {
        ActionResult specialResult = user.useSpecialSkill(context, resolver, target);
        String message = String.format(
                "%s uses Power Stone. %s Cooldown is unchanged.",
                user.getName(), specialResult.getMessage());
        return new ActionResult(specialResult.getDamage(), message, specialResult.isTargetDefeated());
    }
}
