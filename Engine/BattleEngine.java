package com.sc2002.arena.engine;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.DefendAction;
import com.sc2002.arena.action.UseItemAction;
import com.sc2002.arena.action.UseSpecialAbilityAction;
import com.sc2002.arena.combat.Combatant;
import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.item.PowerStone;
import com.sc2002.arena.level.Level;
import com.sc2002.arena.shared.ActionChoice;
import com.sc2002.arena.shared.ActionType;
import com.sc2002.arena.shared.BattlePresenter;
import com.sc2002.arena.shared.PlayerActionSource;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private final Player player;
    private final List<Enemy> enemies;
    private final Level level;
    private final TurnOrderStrategy turnStrategy;
    private final EnemyActionStrategy enemyStrategy;
    private final ActionResolver actionResolver;
    private final PlayerActionSource actionSource;
    private final BattlePresenter presenter;
    private final BattleState state;
    private final BattleContext context;

    public BattleEngine(Player player,
                        Level level,
                        TurnOrderStrategy turnStrategy,
                        EnemyActionStrategy enemyStrategy,
                        PlayerActionSource actionSource,
                        BattlePresenter presenter) {
        this.player = player;
        this.level = level;
        this.turnStrategy = turnStrategy;
        this.enemyStrategy = enemyStrategy;
        this.actionSource = actionSource;
        this.presenter = presenter;
        this.actionResolver = new ActionResolver();
        this.enemies = new ArrayList<>(level.createInitialWave());
        this.state = new BattleState(1, player, enemies);
        this.state.setBackupPending(level.hasBackupWave());
        this.context = new BattleContext(player, enemies, 1);
    }

    public BattleResult runBattle() {
        presenter.displayMessage("Battle begins!");
        while (!checkEnd()) {
            presenter.displayState(state);
            processRound();
            if (!checkEnd()) {
                state.incrementRound();
                context.setRound(state.getRound());
            }
        }
        BattleResult result = new BattleResult(
                player.isAlive(),
                state.getRound(),
                player.getHp(),
                context.getLivingEnemies().size());
        presenter.displayBattleOutcome(result);
        return result;
    }

    public void processRound() {
        List<Combatant> order = turnStrategy.determineTurnOrder(context);
        presenter.displayMessage("Turn order: " + formatTurnOrder(order));
        for (Combatant actor : order) {
            if (!actor.isAlive()) {
                processEliminatedTurn(actor);
                continue;
            }
            if (checkEnd()) {
                break;
            }
            processTurn(actor);
        }

        if (player.isAlive()) {
            player.endRound(context);
        }
        for (Enemy enemy : enemies) {
            enemy.endRound(context);
        }
        recordDefeatedEnemies();
    }

    public void processTurn(Combatant actor) {
        actor.beginTurn(context);

        if (!actor.canAct()) {
            String effectName = actor.getBlockingEffectName().toLowerCase();
            presenter.displayResult(new ActionResult(0,
                    actor.getName() + " is " + effectName + " and skips the turn.", false));
            actor.endTurn(context);
            recordDefeatedEnemies();
            return;
        }

        Action action;
        if (actor instanceof Player currentPlayer) {
            action = buildPlayerAction(currentPlayer);
        } else {
            Enemy enemy = (Enemy) actor;
            action = enemy.decideAction(player, context, enemyStrategy, actionResolver);
        }

        ActionResult result = actionResolver.execute(action);
        presenter.displayResult(result);
        actor.endTurn(context);
        recordDefeatedEnemies();
        checkBackupSpawn();
    }

    public boolean checkEnd() {
        if (!player.isAlive()) {
            return true;
        }
        return context.getLivingEnemies().isEmpty() && (!level.hasBackupWave() || level.isBackupSpawned());
    }

    public BattleState getState() {
        return state;
    }

    private Action buildPlayerAction(Player currentPlayer) {
        ActionChoice choice = actionSource.promptAction(currentPlayer, context);
        ActionType actionType = choice.getActionType();
        Enemy target = resolveEnemyTarget(choice.getTargetIndex());

        return switch (actionType) {
            case BASIC_ATTACK -> new BasicAttackAction(currentPlayer, target, actionResolver);
            case DEFEND -> new DefendAction(currentPlayer, context);
            case USE_ITEM -> buildItemAction(currentPlayer, choice, target);
            case SPECIAL_SKILL -> new UseSpecialAbilityAction(currentPlayer, target, context, actionResolver, false);
        };
    }

    private Action buildItemAction(Player currentPlayer, ActionChoice choice, Enemy target) {
        List<Item> items = currentPlayer.getInventory().getItems();
        if (choice.getItemIndex() < 0 || choice.getItemIndex() >= items.size()) {
            return invalidAction(ActionType.USE_ITEM, "Item use failed: invalid item selection.");
        }
        Item item = currentPlayer.getInventory().removeItem(choice.getItemIndex());
        Enemy itemTarget = item instanceof PowerStone ? target : null;
        return new UseItemAction(currentPlayer, item, itemTarget, context, actionResolver);
    }

    private Enemy resolveEnemyTarget(int targetIndex) {
        List<Enemy> livingEnemies = context.getLivingEnemies();
        if (livingEnemies.isEmpty()) {
            return null;
        }
        if (targetIndex < 0 || targetIndex >= livingEnemies.size()) {
            return null;
        }
        return livingEnemies.get(targetIndex);
    }

    private void checkBackupSpawn() {
        if (level.shouldSpawnBackup(context.getLivingEnemies())) {
            List<Enemy> backupEnemies = level.createBackupWave();
            for (Enemy enemy : backupEnemies) {
                enemies.add(enemy);
                state.addEnemy(enemy);
            }
            level.markBackupSpawned();
            state.setBackupPending(false);
            presenter.displayMessage("Backup Spawn triggered! " + backupEnemies.size() + " enemy combatants enter simultaneously.");
        }
    }

    private void recordDefeatedEnemies() {
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) {
                state.defeatEnemy(enemy);
            }
        }
    }

    private void processEliminatedTurn(Combatant actor) {
        if (actor.hasActiveOwnerTurnEffects()) {
            presenter.displayResult(new ActionResult(0,
                    actor.getName() + " is eliminated and skips the turn.", false));
            actor.endTurn(context);
        }
    }

    private Action invalidAction(ActionType actionType, String message) {
        return new Action() {
            @Override
            public ActionResult execute() {
                return new ActionResult(0, message, false);
            }

            @Override
            public ActionType getActionType() {
                return actionType;
            }
        };
    }

    private String formatTurnOrder(List<Combatant> order) {
        List<String> names = new ArrayList<>();
        for (Combatant combatant : order) {
            names.add(combatant.getName() + " (SPD " + combatant.getSpeed() + ")");
        }
        return String.join(" -> ", names);
    }
}
