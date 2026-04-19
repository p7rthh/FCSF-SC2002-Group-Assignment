package com.sc2002.arena.shared;

public class ActionChoice {
    private final ActionType actionType;
    private final int targetIndex;
    private final int itemIndex;

    public ActionChoice(ActionType actionType, int targetIndex, int itemIndex) {
        this.actionType = actionType;
        this.targetIndex = targetIndex;
        this.itemIndex = itemIndex;
    }

    public static ActionChoice basicAttack(int targetIndex) {
        return new ActionChoice(ActionType.BASIC_ATTACK, targetIndex, -1);
    }

    public static ActionChoice defend() {
        return new ActionChoice(ActionType.DEFEND, -1, -1);
    }

    public static ActionChoice useItem(int itemIndex, int targetIndex) {
        return new ActionChoice(ActionType.USE_ITEM, targetIndex, itemIndex);
    }

    public static ActionChoice specialSkill(int targetIndex) {
        return new ActionChoice(ActionType.SPECIAL_SKILL, targetIndex, -1);
    }

    public ActionType getActionType() {
        return actionType;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public int getItemIndex() {
        return itemIndex;
    }
}
