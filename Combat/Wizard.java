package com.sc2002.arena.combat;

import com.sc2002.arena.effect.ArcaneBlastBuff;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;

import java.util.List;

public class Wizard extends Player {
    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
    }

    @Override
    public boolean specialSkillRequiresTarget() {
        return false;
    }

    private void addArcaneBonus(int amount, BattleContext context) {
        addEffect(new ArcaneBlastBuff(amount), context);
    }

    @Override
    public ActionResult useSpecialSkill(BattleContext context, ActionResolver resolver, Enemy ignoredTarget) {
        List<Enemy> livingEnemies = context.getLivingEnemies();
        if (livingEnemies.isEmpty()) {
            return new ActionResult(0, "Arcane Blast failed: no enemies are alive.", false);
        }

        StringBuilder builder = new StringBuilder();
        int totalDamage = 0;
        boolean anyDefeated = false;
        builder.append(getName()).append(" uses Arcane Blast. ");
        for (Enemy enemy : livingEnemies) {
            int damage = resolver.calculateDamage(this, enemy);
            int actualDamage = enemy.takeDamage(damage);
            totalDamage += actualDamage;
            builder.append(enemy.getName()).append(" takes ").append(actualDamage).append(" damage. ");
            if (!enemy.isAlive()) {
                anyDefeated = true;
                addArcaneBonus(10, context);
                builder.append(enemy.getName()).append(" is defeated. Wizard ATK +10. ");
            }
        }

        return new ActionResult(totalDamage, builder.toString().trim(), anyDefeated);
    }
}
