package src.domain;

public interface GameEventListener {
    void onPlantAttack(int row, int col);
    void onZombieDie(Zombie zombie);
    void onZombieAttack(int row, int column);
    void onPlantDie(Plant plant);
}