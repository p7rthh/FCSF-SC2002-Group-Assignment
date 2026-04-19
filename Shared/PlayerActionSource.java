package com.sc2002.arena.shared;

import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.combat.Player;

public interface PlayerActionSource {
    ActionChoice promptAction(Player player, BattleContext context);
}
