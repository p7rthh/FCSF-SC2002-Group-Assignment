package com.sc2002.arena.engine;

import com.sc2002.arena.combat.Combatant;

import java.util.List;

public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(BattleContext context);
}
