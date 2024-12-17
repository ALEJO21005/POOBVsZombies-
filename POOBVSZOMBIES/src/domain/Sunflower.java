package src.domain;

import javax.swing.Timer;
import java.awt.*;

public class Sunflower extends Plant {
    private static final int SUN_GENERATION_INTERVAL = 20000;
    private Timer sunTimer;
    private int xPos, yPos;

    public Sunflower(int xPos, int yPos) {
        super(0, 50, 50, xPos, yPos, 300);
        this.xPos = xPos;
        this.yPos = yPos;
        startGenerateSuns();
    }

    private void startGenerateSuns() {
        sunTimer = new Timer(SUN_GENERATION_INTERVAL, e -> generateSun());
        sunTimer.start();
    }

    public Sun generateSun() {
        return new Sun(20 ,xPos, yPos);
    }

    public int getxPos() {return this.xPos;}

    public int getyPos() {return this.yPos;}

    @Override
    public void die() {
        super.die();
        if (sunTimer != null) {
            sunTimer.stop();
        }
    }

    @Override
    public void attack(Character character) {
    }
}
