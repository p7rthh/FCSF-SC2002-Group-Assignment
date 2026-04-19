package com.sc2002.arena.combat;

public class Wolf extends Enemy {
    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        super(name, 40, 45, 5, 35);
    }

    @Override
    public Enemy createCopy() {
        return new Wolf(getName());
    }
}
