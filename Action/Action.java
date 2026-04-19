package com.sc2002.arena.action;

import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.shared.ActionType;

//So that one turn can excute different kinds of actions
public interface Action {
    ActionResult execute();
    ActionType getActionType();
}
