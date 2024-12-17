package src.domain;

public  abstract class Character {
    protected int row;
    protected int column;
    protected int health;
    protected boolean isAlive;

    public Character(int row, int column, int health, boolean isAlive) {
        this.row = row;
        this.column = column;
        this.health = health;
        this.isAlive = isAlive;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    public void die() {
        if(health <= 0) {
            isAlive = false;
        }
    }

    public void reduceHealth(int damage){
        this.health -= damage;
        if(this.health <= 0){
            die();
        }
    }




    public int getHealth() {return health;}

    public int getRow() {return row;}

    public int getColumn() {return column;}

}
