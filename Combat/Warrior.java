package com.sc2002.arena.combat;

import com.sc2002.arena.effect.StunEffect;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;

public class Warrior extends Player {
    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
    }

    @Override
    public boolean specialSkillRequiresTarget() {
        return true;
    }

    @Override
    public ActionResult useSpecialSkill(BattleContext context, ActionResolver resolver, Enemy target) {
        if (target == null || !target.isAlive()) {
            return new ActionResult(0, "Shield Bash failed: invalid target.", false);
        }

        int damage = resolver.calculateDamage(this, target);
        int actualDamage = target.takeDamage(damage);
        if (target.isAlive()) {
            target.addEffect(new StunEffect(), context);
        }

        String message = getName() + " slams " + target.getName()
                + " with Shield Bash for " + actualDamage + " damage. "
                + (target.isAlive() ? target.getName() + " is stunned!" : target.getName() + " is defeated.");
        return new ActionResult(actualDamage, message, !target.isAlive());
    }
}
