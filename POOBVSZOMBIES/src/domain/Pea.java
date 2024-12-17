package src.domain;

import java.awt.Rectangle;

public class Pea {
    private int row;
    private int column;
    private int width;
    private int height;

    public Pea(int row, int column) {
        this.row = row;
        this.column = column;
        this.width = 20;
        this.height = 20;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void move() {
        this.column += 1;
    }


}