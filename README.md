# FCSF-SC2002-Group-Assignment
# Turn-Based Combat Arena
SC2002 Group Assignment AY25/26 Semester 2
(This readme is just a sample, Original Report is attached as a SEPARATE FILE)
A command-line turn-based combat game built in Java, designed with Object-Oriented Design Principles (OODP) and SOLID architecture in mind.

## Table of Contents
- Game Overview
- Features
- Characters
- Items
- Levels
- Architecture
- SOLID Principles
- Getting Started
- Project Structure
- Team

## Game Overview

Fight through waves of enemies in a turn-based combat arena. Choose your hero, equip items, and survive increasingly difficult encounters.

- Turn order is determined by each combatant's Speed stat
- Player selects actions each round via a CLI menu
- Enemies always execute a Basic Attack
- Win by defeating all enemies; Lose if your HP reaches 0

## Features

| Feature | Description |
|---------|-------------|
| 2 Playable Classes | Warrior and Wizard, each with unique special skills |
| 2 Enemy Types | Goblin and Wolf with distinct stats |
| 3 Difficulty Levels | Easy, Medium, Hard with backup enemy spawns |
| Status Effects | Stun, Defense Buff, Invulnerability via Smoke Bomb |
| Usable Items | Potion, Power Stone, Smoke Bomb |
| Special Skills | Cooldown-based abilities unique to each class |
| Backup Spawning | New enemy waves appear after initial enemies are defeated |
| CLI Interface | Clean, readable command-line interface |

## Characters

### Players

| Attribute | Warrior | Wizard |
|-----------|---------|--------|
| HP | 260 | 200 |
| Attack | 40 | 50 |
| Defense | 20 | 10 |
| Speed | 30 | 20 |
| Special Skill | Shield Bash | Arcane Blast |

Shield Bash: Deals BasicAttack damage to one enemy and stuns them for 2 turns. Cooldown: 3 turns.

Arcane Blast: Deals BasicAttack damage to all enemies. Each kill grants +10 permanent Attack for the level. Cooldown: 3 turns.

### Enemies

| Attribute | Goblin | Wolf |
|-----------|--------|------|
| HP | 55 | 40 |
| Attack | 35 | 45 |
| Defense | 15 | 5 |
| Speed | 25 | 35 |

## Items

| Item | Effect |
|------|--------|
| Potion | Heals 100 HP, capped at max HP |
| Power Stone | Triggers your special skill for free with no cooldown change |
| Smoke Bomb | Enemy attacks deal 0 damage this turn and the next |

Two single-use items are selected at the start of the game. Duplicates are allowed.

## Levels

| Level | Difficulty | Initial Spawn | Backup Spawn |
|-------|-----------|---------------|--------------|
| 1 | Easy | 3 Goblins | None |
| 2 | Medium | 1 Goblin + 1 Wolf | 2 Wolves |
| 3 | Hard | 2 Goblins | 1 Goblin + 2 Wolves |

Backup enemies spawn all at once after the initial wave is fully defeated.

## Architecture

The system is built around a clean separation of concerns:

- UI Layer: CLI display and input collection
- Engine Layer: BattleEngine, TurnOrderStrategy, action processing
- Domain Layer: Combatants, Actions, Items, StatusEffects

Key abstractions:
- Combatant: shared base for Player and Enemy
- Action: interface for all performable actions (BasicAttack, Defend, Item, SpecialSkill)
- StatusEffect: applied to combatants, tracked per turn, auto-expired
- TurnOrderStrategy: determines combatant action sequence using the Strategy Pattern
- BattleEngine: orchestrates rounds and delegates to strategies and actions

## SOLID Principles

| Principle | How It Is Applied |
|-----------|-----------------|
| SRP | Each class has a single, well-defined responsibility |
| OCP | New Action or StatusEffect types can be added without modifying BattleEngine |
| LSP | Warrior and Enemy are interchangeable as Combatant |
| ISP | Interfaces are lean with no bloated or forced method contracts |
| DIP | BattleEngine depends on abstractions, not concrete implementations |

## Getting Started

Prerequisites: Java 17 or higher and any Java IDE such as IntelliJ IDEA, Eclipse, or VS Code.

git clone https://github.com/p7rthh/SC 2002 Group Project :.git
cd SC 2002 Group Project :
javac -d out src/**/*.java
java -cp out Main // optional, we provided the zip file with all necessary files. VSCode is preffered to run the code.

## Project Structure

```

SC2002-TurnBasedCombat/
    src/
        main/
            Main.java
        engine/
            BattleEngine.java
            TurnOrderStrategy.java
        characters/
            Combatant.java
            Player.java
            Warrior.java
            Wizard.java
            Enemy.java
            Goblin.java
            Wolf.java
        actions/
            Action.java
            BasicAttack.java
            Defend.java
            SpecialSkill.java
            UseItem.java
        items/
            Item.java
            Potion.java
            PowerStone.java
            SmokeBomb.java
        effects/
            StatusEffect.java
            StunEffect.java
            DefendEffect.java
            SmokeBombEffect.java
        levels/
            LevelConfig.java
        ui/
            CLI.java
    docs/
        UML_ClassDiagram.pdf
        UML_SequenceDiagram.pdf
    report/
        SC2002_Report.pdf
    README.md
```



## Team

Parth Kumar Chamoli
Neel Chandra Auprup
Shijun Cai
Christopher Chan Yao Zu


