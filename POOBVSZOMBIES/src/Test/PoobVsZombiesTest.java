package src.Test;

import src.domain.*;
import org.junit.Test;

import static org.junit.Assert.*;
public class PoobVsZombiesTest {


    @Test
    public void accordingCIShouldAddPlantWithSufficientSuns() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        game.receiveSun(200);

        Plant plant = new Peashooter(0, 0);
        game.addPlant(plant, 2, 3);

        assertEquals(100, game.getSuns());
        assertNotNull(game.getBoard().getPlant(2, 3));
    }

    @Test
    public void accordingCIShouldNotAddPlantWithoutSufficientSuns() {
        POOBVsZombies game = new POOBVsZombies();
        Plant plant = new Peashooter(0, 0);

        try {
            game.addPlant(plant, 2, 3);
            //fail("Se esperaba una excepción POOBVsZombiesException");
        } catch (POOBVsZombiesException e) {
            assertEquals(POOBVsZombiesException.NOT_ENOUGH_SUNS, e.getMessage());
        }
    }

    @Test
    public void accordingCIShouldNotAddPlantToOccupiedCell() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        game.setSuns(200);
        Plant sunflower = new Sunflower(0, 0);
        Plant peashooter = new Peashooter(0, 0);

        game.addPlant(sunflower, 2, 3);

        try {
            game.addPlant(peashooter, 2, 3);
        } catch (POOBVsZombiesException e) {
            assertEquals(POOBVsZombiesException.CELL_OCCUPIED, e.getMessage());
        }
    }

    @Test
    public void accordingCIShouldDeletePlant() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        game.setSuns(50);
        Plant sunflower = new Sunflower(0, 0);

        game.addPlant(sunflower, 1, 1);
        game.deletePlant(1, 1);
        assertNull(game.getBoard().getPlant(1, 1));
    }

    @Test
    public void accordingCIShouldReceiveSun() {
        POOBVsZombies game = new POOBVsZombies();
        game.receiveSun(50);
        game.receiveSun(25);

        assertEquals(75, game.getSuns());
    }

    @Test
    public void accordingCIShouldAddZombie() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        int row = 1;
        int column = 7;
        ZombieBasic basic = new ZombieBasic(row,column);
        game.addZombie(basic, row, column);

        assertNotNull(game.getBoard().getZombie(row, column));
    }

    @Test
    public void accordingCIShouldNotAddZombieToOccupiedCell() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        ZombieBasic basic = new ZombieBasic(1,2);
        Sunflower sunflower = new Sunflower(1,2);
        game.receiveSun(50);


        game.addZombie(basic,3,5);
        try {
            game.addPlant(sunflower,3,5);
        }catch (POOBVsZombiesException e) {
            assertEquals(POOBVsZombiesException.CELL_OCCUPIED, e.getMessage());
        }


    }

    @Test
    public void accordingCIshouldDeleteZombieSuccessfully() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        ZombieBasic zombie = new ZombieBasic(1,2);
        zombie.setRow(1);
        zombie.setColumn(2);
        game.addZombie(zombie, 1, 2);
        game.deleteZombie(1, 2);
        assertFalse(game.getZombies().contains(zombie));
    }




    @Test
    public void accordingCIshouldUpdateZombiePosition() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        ZombieBasic zombie = new ZombieBasic(1,2);

        game.addZombie(zombie, 1, 2);

        game.updateZombiePosition(zombie, 2, 3);
        assertEquals(zombie, game.getBoard().getZombie(2, 3));
    }


    @Test
    public void accordingCIShouldPlantAttackZombieSuccessfully() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        Peashooter peashooter = new Peashooter(1,2);
        peashooter.setRow(1);
        peashooter.setColumn(2);
        game.receiveSun(100);
        ZombieBasic zombie = new ZombieBasic(1,3);
        zombie.setRow(1);
        zombie.setColumn(3);

        game.addPlant(peashooter, 1, 2);
        game.addZombie(zombie, 1, 3);

        game.PlantCanAttack();

        assertNotNull(game.getBoard().getZombie(1, 3));
    }


        @Test
        public void accordingCIShouldNotPlantAttackWithoutZombiesInRow() throws POOBVsZombiesException{
            POOBVsZombies game = new POOBVsZombies();
            Peashooter peashooter = new Peashooter(1,2);
            peashooter.setRow(1);
            peashooter.setColumn(2);
            game.receiveSun(100);

            game.addPlant(peashooter, 1, 2);

            game.PlantCanAttack();

            assertTrue(game.getZombies().isEmpty());
        }


    @Test
    public void accordingCIPeaShouldMoveCorrectly() {
        POOBVsZombies game = new POOBVsZombies();
        Pea pea = new Pea(1, 2);
        pea.move();
        assertEquals(1, pea.getRow());
        assertEquals(3, pea.getColumn());
    }



    @Test
    public void accordingCIPeaShouldHitZombie() {
        POOBVsZombies game = new POOBVsZombies();
        ZombieConeHead zombie = new ZombieConeHead(1,4);
        zombie.setRow(1);
        zombie.setColumn(4);
        zombie.setHealth(10);

        Pea pea = new Pea(1, 2);
        while (pea.getColumn() < zombie.getColumn()) {
            pea.move();
        }

        assertEquals(4, pea.getColumn());

        zombie.receiveDamage(5);
        assertEquals(5, zombie.getHealth());
    }

    @Test
    public void accordingCIsunShouldBeCreatedWithCorrectAttributes() {
        Sun sun = new Sun(10, 5, 6);

        assertEquals(5, sun.getxPos());
        assertEquals(6, sun.getyPos());
        assertFalse(sun.isCollected());
    }


    @Test
    public void accordingCISunShouldBeCollected() {
        Sun sun = new Sun(10, 5, 6);
        sun.collect();
        assertTrue(sun.isCollected());
    }


    @Test
    public void accordingCISunShouldChangePosition() {
        Sun sun = new Sun(10, 5, 6);
        sun.setPosition(3, 4);
        assertEquals(4, sun.getxPos());
        assertEquals(3, sun.getyPos());
    }

    @Test
    public void accordingCISunAttributesShouldUpdateCorrectly() {
        Sun sun = new Sun(15, 2, 3);
        sun.setPosition(1, 1);
        sun.collect();
        assertEquals(1, sun.getxPos());
        assertEquals(1, sun.getyPos());
        assertTrue(sun.isCollected());
    }


    @Test
    public void accordingCIShouldGenerateSunSuccessfully() {
        Sunflower sunflower = new Sunflower(3, 4);

        Sun generatedSun = sunflower.generateSun();
        assertEquals(3, generatedSun.getxPos());
        assertEquals(4, generatedSun.getyPos());
        assertFalse(generatedSun.isCollected());
    }

    @Test
    public void accordingCIShouldNotGenerateSunAfterDeath() {
        Sunflower sunflower = new Sunflower(3, 4);
        sunflower.die();

        Sun generatedSun = sunflower.generateSun();

        assertEquals(3, generatedSun.getxPos());
        assertEquals(4, generatedSun.getyPos());
        assertFalse(generatedSun.isCollected());
    }

    @Test
    public void accordingCIShouldReturnCorrectPosition() {
        Sunflower sunflower = new Sunflower(2, 5);

        assertEquals(2, sunflower.getxPos());
        assertEquals(5, sunflower.getyPos());
    }

    @Test
    public void accordingCIShouldStopTimerOnDie() {
        Sunflower sunflower = new Sunflower(3, 4);

        assertNotNull(sunflower);
        sunflower.die();
        Sun generatedSun = sunflower.generateSun();
        assertEquals(3, generatedSun.getxPos());
        assertEquals(4, generatedSun.getyPos());
    }



    @Test
    public void accordingCIShouldCreateWallNutWithCorrectAttributes() {
        WallNut wallNut = new WallNut(2, 3);

        assertEquals(2, wallNut.getxPos());
        assertEquals(3, wallNut.getyPos());
        assertEquals(4000, wallNut.getHealth());
    }

    @Test
    public void accordingCIShouldAddWallNutToBoardSuccessfully() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        game.receiveSun(50);

        WallNut wallNut = new WallNut(2, 3);
        game.addPlant(wallNut, 2, 3);

        Plant plantInCell = game.getBoard().getPlant(2, 3);
        assertNotNull(plantInCell);
        assertTrue(plantInCell instanceof WallNut);
    }

    @Test
    public void accordingCIShouldNotAddWallNutToOccupiedCell() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        game.receiveSun(100);
        WallNut wallNut1 = new WallNut(2, 3);
        WallNut wallNut2 = new WallNut(2, 3);

        game.addPlant(wallNut1, 2, 3);

        try {
            game.addPlant(wallNut2, 2, 3);
        } catch (POOBVsZombiesException e) {
            assertEquals(POOBVsZombiesException.CELL_OCCUPIED, e.getMessage());
        }
    }

    @Test
    public void accordingCIPeaShouldHitZombieBuckethead() {
        ZombieBucketHead zombie = new ZombieBucketHead(1,4);
        zombie.setRow(1);
        zombie.setColumn(4);
        zombie.setHealth(10);

        Pea pea = new Pea(1, 2);

        while (pea.getColumn() < zombie.getColumn()) {
            pea.move();
        }
        assertEquals(4, pea.getColumn());
        zombie.receiveDamage(5);
        assertEquals(5, zombie.getHealth());
    }


    @Test
    public void accordingCIShouldInitializeZombieBrainsteinCorrectly() {
        ZombieBrainstein zombie = new ZombieBrainstein(3, 4);

        assertEquals(3, zombie.getxPos());
        assertEquals(4, zombie.getyPos());
        assertEquals(300, zombie.getHealth());
    }

    @Test
    public void accordingCIShouldGenerateBrainCorrectly() {
        ZombieBrainstein zombie = new ZombieBrainstein(2, 5);

        Brain brain = zombie.generateBrain();
        assertNotNull(brain);
        assertEquals(2, brain.getxPos());
        assertEquals(5, brain.getyPos());
    }

    @Test
    public void accordingCIShouldStopBrainTimerOnDie() {
        ZombieBrainstein zombie = new ZombieBrainstein(2, 5);
        zombie.startGenerateBranis();
        assertNotNull(zombie);
    }

    @Test
    public void accordingCIShouldInitializePotatoMineCorrectly() {
        PotatoMine potatoMine = new PotatoMine(1, 2);

        assertEquals(1, potatoMine.getxPos());
        assertEquals(2, potatoMine.getyPos());
        assertEquals(100, potatoMine.getHealth());  // Salud esperada
    }

    @Test
    public void accordingCIShouldAddPotatoMineToBoard() throws POOBVsZombiesException {
        POOBVsZombies game = new POOBVsZombies();
        PotatoMine potatoMine = new PotatoMine(2, 3);
        game.receiveSun(100);
        game.addPlant(potatoMine, 2, 3);
        assertNotNull(game.getBoard().getPlant(2, 3));
    }




}










