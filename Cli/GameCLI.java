package com.sc2002.arena.cli;

import com.sc2002.arena.combat.Enemy;
import com.sc2002.arena.combat.Player;
import com.sc2002.arena.engine.ActionResult;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.engine.BattleResult;
import com.sc2002.arena.engine.BattleState;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.item.PowerStone;
import com.sc2002.arena.shared.ActionChoice;
import com.sc2002.arena.shared.BattlePresenter;
import com.sc2002.arena.shared.DifficultyLevel;
import com.sc2002.arena.shared.ItemType;
import com.sc2002.arena.shared.PlayerActionSource;
import com.sc2002.arena.shared.PlayerClassType;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameCLI implements BattlePresenter, PlayerActionSource {
    private final Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void start() {
        System.out.println("==== Turn-Based Combat Arena ====");
        System.out.println("Choose your player, items, and difficulty to begin.\n");
        printEnemyReference();
    }

    public PlayerClassType promptPlayerClass() {
        System.out.println("Choose a player class:");
        System.out.println("1. Warrior (HP 260, ATK 40, DEF 20, SPD 30)");
        System.out.println("   Special Skill: Shield Bash - deal basic attack damage to one enemy and stun it for the current turn and next turn.");
        System.out.println("2. Wizard  (HP 200, ATK 50, DEF 10, SPD 20)");
        System.out.println("   Special Skill: Arcane Blast - deal basic attack damage to all enemies; each kill gives +10 ATK until end of level.");
        int choice = promptInt(1, 2);
        return choice == 1 ? PlayerClassType.WARRIOR : PlayerClassType.WIZARD;
    }

    public List<ItemType> promptItems() {
        System.out.println("Choose 2 single-use items (duplicates allowed):");
        printItemMenu();
        List<ItemType> selected = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            System.out.print("Select item " + i + ": ");
            int choice = promptInt(1, 3);
            selected.add(switch (choice) {
                case 1 -> ItemType.POTION;
                case 2 -> ItemType.POWER_STONE;
                default -> ItemType.SMOKE_BOMB;
            });
        }
        return selected;
    }

    public DifficultyLevel promptDifficulty() {
        System.out.println("Choose difficulty:");
        System.out.println("1. Easy   - Initial Spawn: 3 Goblins");
        System.out.println("2. Medium - Initial Spawn: 1 Goblin + 1 Wolf | Backup Spawn: 2 Wolves");
        System.out.println("3. Hard   - Initial Spawn: 2 Goblins | Backup Spawn: 1 Goblin + 2 Wolves");
        int choice = promptInt(1, 3);
        return switch (choice) {
            case 1 -> DifficultyLevel.EASY;
            case 2 -> DifficultyLevel.MEDIUM;
            default -> DifficultyLevel.HARD;
        };
    }

    @Override
    public void displayState(BattleState state) {
        System.out.println();
        System.out.println("----- Round " + state.getRound() + " -----");
        Player player = state.getPlayer();
        System.out.printf("%s HP: %d/%d | ATK: %d | DEF: %d | SPD: %d | Special Cooldown: %d%n",
                player.getName(), player.getHp(), player.getMaxHp(), player.getEffectiveAttack(),
                player.getEffectiveDefense(), player.getSpeed(), player.getDisplayedCooldownAtTurnStart());
        if (!player.getStatusSummary().isBlank()) {
            System.out.println("Player Effects: " + player.getStatusSummary());
        }
        System.out.println("Inventory: " + formatInventory(player.getInventory().getItems()));
        System.out.println("Enemies:");
        for (Enemy enemy : state.getActiveEnemies()) {
            String status = enemy.isAlive() ? "ALIVE" : "ELIMINATED";
            String effects = enemy.getStatusSummary().isBlank() ? "" : " | Effects: " + enemy.getStatusSummary();
            System.out.printf("- %s HP: %d/%d | ATK: %d | DEF: %d | SPD: %d | %s%s%n",
                    enemy.getName(), enemy.getHp(), enemy.getMaxHp(), enemy.getEffectiveAttack(),
                    enemy.getEffectiveDefense(), enemy.getSpeed(), status, effects);
        }
    }

    @Override
    public ActionChoice promptAction(Player player, BattleContext context) {
        List<Enemy> livingEnemies = context.getLivingEnemies();
        while (true) {
            System.out.println("Choose action:");
            System.out.println("1. BasicAttack");
            System.out.println("2. Defend");
            System.out.println("3. Use Item");
            System.out.println("4. SpecialSkill" + (player.canUseSpecialSkill() ? "" : " (currently on cooldown)"));

            int choice = promptInt(1, 4);
            switch (choice) {
                case 1:
                    return ActionChoice.basicAttack(promptEnemyTarget(livingEnemies));
                case 2:
                    return ActionChoice.defend();
                case 3:
                    if (player.getInventory().isEmpty()) {
                        System.out.println("Inventory is empty. Choose another action.");
                        continue;
                    }
                    return promptItemChoice(player, livingEnemies);
                case 4:
                    if (!player.canUseSpecialSkill()) {
                        System.out.println("SpecialSkill is on cooldown. Choose another action.");
                        continue;
                    }
                    return ActionChoice.specialSkill(player.specialSkillRequiresTarget() ? promptEnemyTarget(livingEnemies) : -1);
                default:
                    throw new IllegalStateException("Unexpected action choice: " + choice);
            }
        }
    }

    @Override
    public void displayResult(ActionResult result) {
        System.out.println(result.getMessage());
    }

    @Override
    public void displayBattleOutcome(BattleResult result) {
        System.out.println();
        if (result.isVictory()) {
            System.out.println("Victory! Congratulations, you have defeated all your enemies.");
            System.out.printf("Remaining HP: %d | Total Rounds: %d%n", result.getRemainingHP(), result.getTotalRounds());
        } else {
            System.out.println("Defeated. Don't give up, try again!");
            System.out.printf("Enemies remaining: %d | Total Rounds Survived: %d%n",
                    result.getEnemiesRemaining(), result.getTotalRounds());
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public int promptPostGameOption() {
        System.out.println("Choose next step:");
        System.out.println("1. Replay with same settings");
        System.out.println("2. Start a new game");
        System.out.println("3. Exit");
        return promptInt(1, 3);
    }

    private ActionChoice promptItemChoice(Player player, List<Enemy> livingEnemies) {
        List<Item> items = player.getInventory().getItems();
        System.out.println("Choose an item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, items.get(i).getName());
        }
        int itemChoice = promptInt(1, items.size()) - 1;
        Item selected = items.get(itemChoice);
        int targetIndex = -1;
        if (selected instanceof PowerStone && player.specialSkillRequiresTarget()) {
            targetIndex = promptEnemyTarget(livingEnemies);
        }
        return ActionChoice.useItem(itemChoice, targetIndex);
    }

    private int promptEnemyTarget(List<Enemy> livingEnemies) {
        if (livingEnemies.isEmpty()) {
            return -1;
        }
        System.out.println("Choose target enemy:");
        for (int i = 0; i < livingEnemies.size(); i++) {
            Enemy enemy = livingEnemies.get(i);
            System.out.printf("%d. %s (HP %d/%d)%n", i + 1, enemy.getName(), enemy.getHp(), enemy.getMaxHp());
        }
        return promptInt(1, livingEnemies.size()) - 1;
    }

    private String formatInventory(List<Item> items) {
        if (items.isEmpty()) {
            return "[empty]";
        }
        List<String> names = new ArrayList<>();
        for (Item item : items) {
            names.add(item.getName());
        }
        return String.join(", ", names);
    }

    private void printEnemyReference() {
        System.out.println("Enemy reference:");
        System.out.println("- Goblin (HP 55, ATK 35, DEF 15, SPD 25)");
        System.out.println("- Wolf   (HP 40, ATK 45, DEF 5,  SPD 35)");
        System.out.println();
    }

    private void printItemMenu() {
        System.out.println("1. Potion     - Heal 100 HP (capped at max HP)");
        System.out.println("2. Power Stone- Trigger special skill once without changing cooldown");
        System.out.println("3. Smoke Bomb - Enemy attacks deal 0 damage this turn and next turn");
    }

    private int promptInt(int min, int max) {
        while (true) {
            try {
                System.out.print("> ");
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Enter a number between %d and %d.%n", min, max);
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
