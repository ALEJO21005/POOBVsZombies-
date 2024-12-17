package src.domain;

import java.awt.*;

public class ZombieBasic extends Zombie {

    public ZombieBasic(int xPos, int yPos) {
        super(xPos, yPos, 100, 100, 1.0, 0.5, true);
    }

    @Override
    public void attack(Character character) {
        super.attack(character);
    }
}

