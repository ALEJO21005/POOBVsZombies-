package src.domain;

import java.awt.*;

public abstract class Plant extends Character implements Attack {
    protected int damage;
    protected int cost;
    protected double rechargeTime;


    public Plant(int damage, int cost, double rechargeTime, int xPos, int yPos, int health) {
        super(xPos, yPos, health, true);
        this.damage = damage;
        this.cost = cost;
        this.rechargeTime = rechargeTime;

    }


    /**
     * public Plant(int damage, int cost, double rechargeTime, int xPos, int yPos, int health) {
     * super();
     * }
     **/

    public void receiveDamage(int damage) {
        this.health -= damage;
    }

    public int getCost() {
        return this.cost;
    }


    /**
     * Verifica si esta planta colisiona con otro objeto rectangular.
     * @param otherX La coordenada X del otro objeto.
     * @param otherY La coordenada Y del otro objeto.
     * @param otherWidth El ancho del otro objeto.
     * @param otherHeight El alto del otro objeto.
     * @return true si hay colisión, false en caso contrario.
     */
    /**
     * public boolean collidesWith(int otherX, int otherY, int otherWidth, int otherHeight) {
     * return xPos < otherX + otherWidth &&
     * xPos + width > otherX &&
     * yPos < otherY + otherHeight &&
     * yPos + height > otherY;
     * }
     **/

    @Override
    public abstract void attack(Character character);

}
