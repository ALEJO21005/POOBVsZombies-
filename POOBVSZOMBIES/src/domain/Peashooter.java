package src.domain;

import java.awt.*;

public class Peashooter extends Plant {
    public static final int DAMAGE = 20;
    private long lastShotTime;
    private static final long SHOOT_INTERVAL = 2000; // 3 segundos entre disparos

    public Peashooter(int row, int col) {
        super(DAMAGE, 100, 1.5, row, col, 300);
        this.lastShotTime = 0;
    }


    public boolean canShoot(long currentTime) {
        if (currentTime - lastShotTime >= SHOOT_INTERVAL) {
            lastShotTime = currentTime;
            return true;
        }
        return false;
    }

    @Override
    public void attack(Character character) {
        if (character instanceof Zombie) {
            Zombie zombie = (Zombie) character;
            zombie.reduceHealth(DAMAGE);
        }
    }


}