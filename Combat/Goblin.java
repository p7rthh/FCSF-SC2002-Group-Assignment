package com.sc2002.arena.combat;

public class Goblin extends Enemy {
    public Goblin() {
        this("Goblin");
    }

    public Goblin(String name) {
        super(name, 55, 35, 15, 25);
    }

    @Override
    public Enemy createCopy() {
        return new Goblin(getName());
    }
}
