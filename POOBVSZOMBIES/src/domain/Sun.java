package src.domain;

public class Sun {
    private int timeDuration;
    private int xPos;
    private int yPos;
    private boolean isCollected;

    public Sun(int timeDuration, int xPos, int yPos) {
        this.timeDuration = timeDuration;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isCollected = false;
    }

    public void collect() {
        this.isCollected = true;
    }

    public boolean isCollected() {
        return this.isCollected;
    }

    public int getxPos(){return this.xPos;}

    public int getyPos(){return this.yPos;}

    public void setPosition(int row, int column){
        this.xPos = column;
        this.yPos = row;
    }
}
