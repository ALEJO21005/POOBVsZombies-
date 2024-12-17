package src.domain;

public class POOBVsZombiesException extends Exception {
    public static final String INDEX_OUT_OF_RANGE = "El índice está fuera de rango";
    public static final String NOT_ENOUGH_SUNS = "No tienes soles suficientes para plantar la planta";
    public static final String CELL_OCCUPIED = "Esta celda ya está ocupada por otra planta";
    public static final String PLANT_DUPLICATE = "Ya has seleccionado este objeto";
    public static final String TIME_FINISHED = "El tiempo de plantar se ha terminado";
    public static final String NOT_ENOUGH_BRAINS = "No tienes cerebros suficientes para poner un Zombie";

    public POOBVsZombiesException(String message) {
        super(message);
    }
}
