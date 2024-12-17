package src.domain;

import java.awt.*;

public class ZombieBucketHead extends Zombie{
    private boolean hasBucket;

    public ZombieBucketHead(int xPos, int yPos) {
        super(xPos, yPos, 800, 100, 1, 0.5, false);
        hasBucket = true;
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

    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health <= 270 && this.hasBucket) {
            this.hasBucket = false;
        }
        if(this.health <= 0){
            die();
        }
    }
}
