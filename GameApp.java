package com.sc2002.arena;

import com.sc2002.arena.cli.BattleController;
import com.sc2002.arena.cli.GameCLI;

public class GameApp {
    public static void main(String[] args) {
        GameCLI cli = new GameCLI();
        BattleController controller = new BattleController(cli);
        controller.playGame();
    }
}
