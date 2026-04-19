package com.sc2002.arena.combat;

import com.sc2002.arena.effect.DurationUnit;
import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.engine.BattleContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Combatant {
    private final String name;
    private final int maxHp;
    private final int baseAttack;
    private final int baseDefense;
    private final int speed;

    private int hp;
    private int cooldown;
    private final List<StatusEffect> effects;

    protected Combatant(String name, int maxHp, int baseAttack, int baseDefense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.speed = speed;
        this.hp = maxHp;
        this.cooldown = 0;
        this.effects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void resetCooldown() {
        this.cooldown = 3;
    }

    public void clearCooldown() {
        this.cooldown = 0;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int takeDamage(int amount) {
        int actualDamage = Math.max(0, amount);
        int newHp = Math.max(0, hp - actualDamage);
        int dealt = hp - newHp;
        hp = newHp;
        return dealt;
    }

    public int heal(int amount) {
        int newHp = Math.min(maxHp, hp + Math.max(0, amount));
        int healed = newHp - hp;
        hp = newHp;
        return healed;
    }

    public void addEffect(StatusEffect effect, BattleContext context) {
        effect.onApply(this, context);
        effects.add(effect);
    }

    public List<StatusEffect> getEffects() {
        return new ArrayList<>(effects);
    }

    public boolean hasEffect(Class<? extends StatusEffect> effectClass) {
        for (StatusEffect effect : effects) {
            if (effectClass.isInstance(effect) && !effect.isExpired()) {
                return true;
            }
        }
        return false;
    }

    public boolean canAct() {
        if (!isAlive()) {
            return false;
        }
        for (StatusEffect effect : effects) {
            if (effect.preventsAction() && !effect.isExpired()) {
                return false;
            }
        }
        return true;
    }

    public String getBlockingEffectName() {
        for (StatusEffect effect : effects) {
            if (effect.preventsAction() && !effect.isExpired()) {
                return effect.getName();
            }
        }
        return "unable to act";
    }

    public boolean hasActiveOwnerTurnEffects() {
        for (StatusEffect effect : effects) {
            if (!effect.isExpired() && effect.getDurationUnit() == DurationUnit.OWNER_TURN) {
                return true;
            }
        }
        return false;
    }

    public void beginTurn(BattleContext context) {
        if (cooldown > 0) {
            cooldown--;
        }
        for (StatusEffect effect : effects) {
            effect.onOwnerTurnStart(this, context);
        }
    }

    public void endTurn(BattleContext context) {
        for (StatusEffect effect : effects) {
            effect.onOwnerTurnEnd(this, context);
        }
        decrementEffects(DurationUnit.OWNER_TURN, context);
    }

    public void endRound(BattleContext context) {
        for (StatusEffect effect : effects) {
            effect.onRoundEnd(this, context);
        }
        decrementEffects(DurationUnit.ROUND, context);
    }

    private void decrementEffects(DurationUnit unit, BattleContext context) {
        Iterator<StatusEffect> iterator = effects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            if (effect.getDurationUnit() == unit) {
                effect.decrement();
                if (effect.isExpired()) {
                    effect.expire(this, context);
                    iterator.remove();
                }
            }
        }
    }

    public int getEffectiveAttack() {
        int total = baseAttack;
        for (StatusEffect effect : effects) {
            total += effect.getAttackModifier();
        }
        return total;
    }

    public int getEffectiveDefense() {
        int total = baseDefense;
        for (StatusEffect effect : effects) {
            total += effect.getDefenseModifier();
        }
        return total;
    }

    public boolean negatesIncomingEnemyDamage() {
        for (StatusEffect effect : effects) {
            if (effect.negatesIncomingEnemyDamage()) {
                return true;
            }
        }
        return false;
    }

    public String getStatusSummary() {
        List<String> names = new ArrayList<>();
        for (StatusEffect effect : effects) {
            if (!effect.isExpired()) {
                names.add(effect.getName());
            }
        }
        return String.join(", ", names);
    }
}
