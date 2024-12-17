package src.domain;

import java.awt.*;

public class WallNut extends Plant {
    private int xPos;
    private int yPos;
    public WallNut(int xPos, int yPos) {
        super(0, 50, 50,  xPos, yPos, 4000);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() { return xPos; }

    public int getyPos() { return yPos; }
    @Override
    public void attack(Character character) {};

}
