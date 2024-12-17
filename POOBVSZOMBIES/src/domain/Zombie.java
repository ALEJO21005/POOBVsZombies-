package src.domain;

import java.awt.*;

public abstract class Zombie extends Character implements Attack {
    protected int damage;
    private double speed;
    private double attackSpeed;
    private double xPos;
    private long lastAttackTime;
    private boolean canEat;
    private int cost;

    public Zombie(int xPos, int yPos, int health, int damage, double speed, double attackSpeed, boolean isAlive) {
        super(xPos, yPos, health, isAlive);
        this.damage = damage;
        this.speed = speed;
        this.attackSpeed = attackSpeed;
        this.canEat = false;
    }


    public Zombie(int xPos, int yPos, int health, int damage, double speed, double attackSpeed, boolean isAlive, int cost) {
        super(xPos, yPos, health, isAlive);
        this.damage = damage;
        this.speed = speed;
        this.attackSpeed = attackSpeed;
        this.cost = cost;
        this.canEat = false;
    }
    
    @Override
    public void attack(Character character) {
        if (character instanceof Plant) {
            Plant plant = (Plant) character;
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastAttackTime >= 500) { // 500 ms = 0.5 segundos
                plant.reduceHealth(this.damage);
                lastAttackTime = currentTime;

                if (plant.getHealth() <= 0) {
                    plant.die();
                }
            }
        }
    }

    public int getCost(){ return this.cost;}

}