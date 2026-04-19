package com.sc2002.arena.item;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.shared.ItemType;

public abstract class Item {
    private final String name;
    private final ItemType itemType;

    protected Item(String name, ItemType itemType) {
        this.name = name;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public abstract ActionResult use(Player user, BattleContext context, Enemy target, ActionResolver resolver);

    @Override
    public String toString() {
        return name;
    }
}
