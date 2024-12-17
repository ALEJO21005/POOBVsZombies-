package src.domain;

import java.awt.*;

public class ZombieConeHead extends Zombie{
    private boolean hasConeHead;

    public ZombieConeHead(int xPos, int yPos) {
        super(xPos, yPos, 370,100,1,0.5, true);
        hasConeHead = true;
    }


    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health <= 270 && this.hasConeHead) {
            this.hasConeHead = false;
        }
        if(this.health <= 0){
            die();
        }
    }

    @Override
    public void attack(Character character) {
        if(character instanceof Plant){
            Plant plant = (Plant) character;
            character.reduceHealth(100);

            if(plant.getHealth() <= 0){
                plant.die();
            }
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
