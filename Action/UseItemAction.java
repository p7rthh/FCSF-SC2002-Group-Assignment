package com.sc2002.arena.action;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.shared.ActionType;

public class UseItemAction implements Action {
    private final Player actor;
    private final Item item;
    private final Enemy target;
    private final BattleContext context;
    private final ActionResolver resolver;

    public UseItemAction(Player actor, Item item, Enemy target, BattleContext context, ActionResolver resolver) {
        this.actor = actor;
        this.item = item;
        this.target = target;
        this.context = context;
        this.resolver = resolver;
    }

    @Override
    public ActionResult execute() {
        if (actor == null || !actor.isAlive()) {
            return new ActionResult(0, "Item use failed because the actor is invalid.", false);
        }
        if (item == null) {
            return new ActionResult(0, "No item selected.", false);
        }
        return item.use(actor, context, target, resolver);
    }

    @Override
    public ActionType getActionType() {
        return ActionType.USE_ITEM;
    }
}
