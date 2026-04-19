package com.sc2002.arena.item;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.effect.SmokeBombEffect;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.shared.ItemType;

public class SmokeBomb extends Item {
    public SmokeBomb() {
        super("Smoke Bomb", ItemType.SMOKE_BOMB);
    }

    @Override
    public ActionResult use(Player user, BattleContext context, Enemy target, ActionResolver resolver) {
        user.addEffect(new SmokeBombEffect(), context);
        String message = String.format(
                "%s uses Smoke Bomb. Enemy attacks deal 0 damage this turn and the next turn.",
                user.getName());
        return new ActionResult(0, message, false);
    }
}
