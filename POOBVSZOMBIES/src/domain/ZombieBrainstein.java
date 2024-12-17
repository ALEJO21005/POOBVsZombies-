package src.domain;

import javax.swing.*;
import java.awt.*;


public class ZombieBrainstein extends Zombie implements Attack{
    private static final int BRAIN_GENERATION_INTERVAL = 20000;
    private Timer brainTimer;
    private int xPos, yPos;


    public ZombieBrainstein(int xPos, int yPos) {
        super(xPos, yPos, 300, 0, 0, 0, true);
        this.xPos = xPos;
        this.yPos = yPos;
    }


    public void startGenerateBranis(){
        brainTimer = new Timer(BRAIN_GENERATION_INTERVAL, e -> generateBrain());
        brainTimer.start();
    }

    public Brain generateBrain() {return new Brain(20,xPos, yPos);}

    @Override
    public void die(){
        super.die();
        if(brainTimer != null){
            brainTimer.stop();
        }
    }

    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}

}
