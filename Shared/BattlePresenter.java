package com.sc2002.arena.shared;

import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleResult;
import com.sc2002.arena.engine.BattleState;

public interface BattlePresenter {
    void displayMessage(String message);
    void displayState(BattleState state);
    void displayResult(ActionResult result);
    void displayBattleOutcome(BattleResult result);
}
