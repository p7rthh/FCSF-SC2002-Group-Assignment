package com.sc2002.arena.engine;

import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.combat.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SpeedStrategy implements TurnOrderStrategy {
    @Override
    public List<Combatant> determineTurnOrder(BattleContext context) {
        List<Combatant> order = new ArrayList<>(context.getAllCombatants());
        order.sort(Comparator
                .comparingInt(Combatant::getSpeed)
                .reversed()
                .thenComparingInt(c -> c instanceof Player ? 0 : 1));
        return order;
    }
}
