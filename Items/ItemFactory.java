package com.sc2002.arena.item;

import com.sc2002.arena.shared.ItemType;

public final class ItemFactory {
    private ItemFactory() {
    }

    public static Item create(ItemType itemType) {
        return switch (itemType) {
            case POTION -> new Potion();
            case POWER_STONE -> new PowerStone();
            case SMOKE_BOMB -> new SmokeBomb();
        };
    }
}
