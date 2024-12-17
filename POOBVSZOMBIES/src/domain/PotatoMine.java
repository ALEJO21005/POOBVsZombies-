package src.domain;

import java.awt.*;

public class PotatoMine extends Plant {
    private boolean isActive;
    private int xPos;
    private int yPos;

    public PotatoMine(int xPos, int yPos) {
        super(1000, 25, 0,  xPos, yPos, 100);
        this.isActive = false;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void attack(Character character) {

    }

    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}


}
