package src.domain;
import java.util.ArrayList;

public class POOBVsZombies {
    private ArrayList<Zombie> zombies;
    private ArrayList<Plant> plants;
    private ArrayList<Sun> sunsGenerated;
    private ArrayList<Brain> brainsGenerated;
    private Board board;
    private int sunsCollected;
    private int brainsCollected;
    private GameEventListener eventListener;


    /**
     * Constructor de la clase
     */
    public POOBVsZombies() {
        this.zombies = new ArrayList<>();
        this.plants = new ArrayList<>();
        this.board = new Board();
        this.sunsGenerated = new ArrayList<>();
        this.brainsGenerated = new ArrayList<>();
    }

    /**
     * Este método permite añadir una planta al tablero del juego
     *
     * @param p,      Planta a añadir
     * @param row,    Fila en la que se añade la planta
     * @param column, columna en la que se añade la planta
     * @throws POOBVsZombiesException, si la celda está ocupada o no hay suficientes soles
     */
    public void addPlant(Plant p, int row, int column) throws POOBVsZombiesException {
        System.out.println("suns: " + sunsCollected);
        System.out.println("costoPlanta: " + p.getCost());
        if (sunsCollected < p.getCost()) {
            throw new POOBVsZombiesException(POOBVsZombiesException.NOT_ENOUGH_SUNS);
        }
        if (board.getPlant(row, column) != null) {
            throw new POOBVsZombiesException(POOBVsZombiesException.CELL_OCCUPIED);
        }
        board.addPlant(p, row, column);
        plants.add(p);
        this.sunsCollected -= p.getCost();
    }

    public void addZombie(Zombie z, int row, int column) throws POOBVsZombiesException {
        board.addZombie(z, row, column);
        zombies.add(z);
    }

    /**
     * Permite eliminar una planta del tablero del juego
     *
     * @param row
     * @param column
     */
    public void deletePlant(int row, int column) {
        board.deletePlant(row, column);
        plants.remove(board.getPlant(row, column));
    }

    public void deleteZombie(int row, int column) {
        board.deletePlant(row, column);
        zombies.remove(board.getZombie(row, column));
    }

    /**
     * Permite generar soles al azar en el tablero del juego
     * @param suns
     */
    public void receiveSun(int suns) {
        this.sunsCollected += suns;
    }

    /**
     * Retorna la cantidad de soles obtenidos en el juego
     * @return, la cantidad de soles obtenidos en el juego
     */
    public int getSuns() {
        return this.sunsCollected;
    }


    public int getBrains() {return this.brainsCollected;}

    /**
     * Retorna un arreglo con la cantidad de plantas que hay en el juego
     *
     * @return, arreglo con la cantidad de plantas que hay en el juego
     */
    public ArrayList<Plant> getPlants() {
        return plants;
    }

    /**
     * Retorna un arreglo con la cantidad de zombies que hay en el juego
     * @return, arreglo con la cantidad de zombies que hay en el juego
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }


    public void setSuns(int suns) {
        this.sunsCollected = Math.max(0, suns); // Evitar que los soles sean negativos
    }

    public void setBrains(int brains) {this.brainsCollected = Math.max(0,brains);}
    /**
     * Actualiza la posición de un zombie en el tablero del juego
     * @param zombie, zombie a actualizar
     * @param fila, fila en la que se actualiza el zombie
     * @param columna, columna en la que se actualiza el zombie
     * @throws POOBVsZombiesException, si la celda está ocupada o no hay suficientes soles
     */

    public void updateZombiePosition(Zombie zombie, int fila, int columna) throws POOBVsZombiesException {
        int actualRow = zombie.getRow();
        int actualColumn = zombie.getColumn();
        board.deletePlant(actualRow, actualColumn);
        zombie.setRow(fila);
        zombie.setColumn(columna);
        board.addZombie(zombie, fila, columna);
    }

    /**
     * Obtiene un zombie del tablero del juego
     * @param row, fila del zombie
     * @param column, columna del zombie
     * @return, zombie en la posición especificada
     */
    public Zombie getZombie(int row, int column) {
        return board.getZombie(row, column);
    }


    /**
     * public void importar01(File file) throws POOBVsZombiesException {
     * try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
     * String line;
     * line = reader.readLine();
     * <p>
     * while ((line = reader.readLine()) != null) {
     * String[] parts = line.split(" ");
     * if (parts.length != 2) {
     * throw new POOBVsZombiesException("Línea malformada: " + line);
     * }
     * String position = parts[0];
     * String type = parts[1];
     * <p>
     * try {
     * int row = Integer.parseInt(position.substring(3, position.indexOf("column")));
     * int col = Integer.parseInt(position.substring(position.indexOf("column") + 6));
     * decipherMessage(type, row, col);
     * <p>
     * } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
     * throw new ReplicateException(e.getMessage());
     * }
     * }
     * } catch (IOException e) {
     * throw new ReplicateException("Error leyendo el archivo: " + file.getName());
     * }
     * }
     **/


    /**
     * Devuelve un zombie que se encuentra en la misma fila que la planta
     * @return
     */
    public Board getBoard() {
        return this.board;
    }

    public void addBrains(Brain brains) {this.brainsGenerated.add(brains);}

    /**
     * Agrega soles al juego
     * @param suns, cantidad de soles a agregar
     */
    public void addSuns(Sun suns) {
        this.sunsGenerated.add(suns);
    }

    public void collectBrains(Brain brains){
        if (!brains.isCollected()) {
            brains.collect();
            sunsCollected += 25 ;
        }
    }

    public void collectSuns(Sun suns) {
        if (!suns.isCollected()) {
            suns.collect();
            sunsCollected += 25;
        }

    }


    public void setEventListener(GameEventListener listener) {
        this.eventListener = listener;
    }

    public void removeEventListener() {
        this.eventListener = null;
    }

    public void PlantCanAttack() {
        long currentTime = System.currentTimeMillis();
        for (Plant plant : plants) {
            if (plant instanceof Peashooter) {
                Peashooter peashooter = (Peashooter) plant;
                if (peashooter.canShoot(currentTime)) {
                    Zombie zombie = isZombieInSamePlantRow(plant.getRow(), plant.getColumn());
                    if (zombie != null) {
                        if (eventListener != null) {
                            peashooter.attack(zombie);
                            eventListener.onPlantAttack(plant.getRow(), plant.getColumn());
                        }
                        if (!zombie.isAlive) {
                            zombie.die();
                            board.deleteZombie(zombie.getRow(), zombie.getColumn());
                            if (eventListener != null) {
                                eventListener.onZombieDie(zombie);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void ZombieCanAttack() {
        long currentTime = System.currentTimeMillis();
        for (Zombie zombie : zombies) {
            if (zombie.isAlive) {
                Plant plant = isPlantInAdjacentColumn(zombie.getRow(), zombie.getColumn());
                if (plant != null) {
                    if (eventListener != null) {
                        zombie.attack(plant);
                        eventListener.onZombieAttack(zombie.getRow(), zombie.getColumn());
                    }
                    if (!plant.isAlive) {
                        plant.die();
                        board.deletePlant(plant.getRow(), plant.getColumn());
                        if (eventListener != null) {
                            eventListener.onPlantDie(plant);
                        }
                    }
                }
            }
        }
    }

    private Plant isPlantInAdjacentColumn(int row, int zombieColumn) {
        int adjacentColumn = zombieColumn - 1;
        if (adjacentColumn >= 0) {
            Plant plant = board.getPlant(row, adjacentColumn);
            if (plant != null && plant.isAlive) {
                return plant;
            }
        }
        return null;
    }

    public Zombie isZombieInSamePlantRow(int row, int plantColumn) {
        for (Zombie zombie : zombies) {
            if (zombie.getRow() == row && zombie.getColumn() >= plantColumn && zombie.isAlive) {
                return zombie;
            }
        }
        return null;
    }

    public ArrayList<Sun> getSunsGenerated() {return this.sunsGenerated;}

    public void startGame() {
        Thread gameThread = new Thread(() -> {
            while (true) {
                try {
                    PlantCanAttack();
                    ZombieCanAttack();
                    Thread.sleep(3000); // Espera 1 segundo entre verificaciones
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        gameThread.start();
    }

    public Plant[][] getPlantsMatrix(){
        return board.getSquares();
    }

    public Zombie [][] getZombiesMatrix() {
        return board.getZombies();
    }

    public void endGame() {

    }

    public void restartGame() {

    }

    public void generateZombie() {

    }

    public void increaseLevel() {

    }
}
