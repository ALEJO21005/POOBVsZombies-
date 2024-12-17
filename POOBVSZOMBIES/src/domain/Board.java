package src.domain;

public class Board {
    private Plant[][] squares;
    private Zombie[][] zombies;
    private static final int CELL_WIDTH = 80;
    private static final int CELL_HEIGHT = 140;


    public Board() {
        this.squares = new Plant[5][11];
        this.zombies = new Zombie[5][11];
    }

    public Plant getPlant(int row, int column) {
        return squares[row][column];
    }

    public void addPlant(Plant p, int row, int column) throws POOBVsZombiesException {
        if (row < 0 || row >= 5 || column < 0 || column >= 10) {
            throw new POOBVsZombiesException(POOBVsZombiesException.INDEX_OUT_OF_RANGE);
        }
        if(squares[row][column] != null) {
            throw new POOBVsZombiesException(POOBVsZombiesException.CELL_OCCUPIED);
        }
        squares[row][column] = p;
    }

    public Plant[][] getSquares() {return squares;}

    public Zombie [][] getZombies() {return zombies;}

    public void deletePlant(int row, int column) {
        squares[row][column] = null;
    }

    public void deleteZombie(int row, int column) {
        zombies[row][column] = null;
    }

    public Zombie getZombie(int row, int column) {return zombies[row][column];}


    public void addZombie(Zombie z, int row, int column) throws POOBVsZombiesException {
        /**if (row < 0 || row >= 5 || column < 0 || column >= 10) {
            throw new POOBVsZombiesException(POOBVsZombiesException.INDEX_OUT_OF_RANGE);
        }**/
        zombies[row][column] = z;
    }
}
