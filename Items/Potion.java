package com.sc2002.arena.item;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.shared.ItemType;

public class Potion extends Item {
    public Potion() {
        super("Potion", ItemType.POTION);
    }

    @Override
    public ActionResult use(Player user, BattleContext context, Enemy target, ActionResolver resolver) {
        int healed = user.heal(100);
        String message = String.format("%s uses Potion and heals %d HP.", user.getName(), healed);
        return new ActionResult(0, message, false);
    }
}
