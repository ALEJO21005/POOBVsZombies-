package src.presentation;

import src.domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ScreenGame extends JPanel implements GameEventListener{
    private PlantsVsZombiesGUI gameGUI;
    private POOBVsZombies game;
    private JPanel dayGame;
    private JLayeredPane panelDayGame;
    private JButton menuButton;
    private JButton resumeButton, saveButton,
            quitButton, sunflowerButton,
            peashooterButton, wallnutButton, potatoMineButton;
    private JButton importButton, exportButton;
    private SunCounter sunCounter;
    private Timer zombieTimer;
    private JLabel contadorSunLabel;
    private String selectedPlant = null;
    private ArrayList<Pea> peas = new ArrayList<>();
    private Timer guisanteTimer;
    private HashMap<Zombie, JLabel> zombieLabels = new HashMap<>();
    private SelectPlantsZombies plantsZombies;


    public ScreenGame(PlantsVsZombiesGUI mainGUI) {
        game = new POOBVsZombies();
        game.setEventListener(this);
        this.gameGUI = mainGUI;
        plantsZombies = new SelectPlantsZombies(gameGUI);
        setLayout(new BorderLayout());
        prepareElements();
        prepareActions();
        add(dayGame, BorderLayout.CENTER);
        peas = new ArrayList<>();
    }

    public void prepareElements() {
        dayGame = prepareDayGame();
        prepareActionsdayGame();
        prepareActions();
        //startGame();
    }

    private void prepareActions() {prepareActionsdayGame();}

    private void prepareActionsdayGame() {
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareGameMenu();
            }
        });

        sunflowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPlant = "sunflower";
            }
        });

        peashooterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPlant = "peashooter";
            }
        });

        wallnutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPlant = "wallnut";
            }
        });

        potatoMineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPlant = "potatomine";
            }
        });
    }



    private void prepareGameMenuActions(JDialog gameMenuDialog) {
        resumeButton.addActionListener(e -> gameMenuDialog.dispose());
        quitButton.addActionListener(e -> {
            backToMainMenu();
            //resetGame();
        });

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public void onPlantAttack(int row, int col) {
        addPea(row, col);
    }

    @Override
    public void onZombieDie(Zombie zombie) {

    }

    @Override
    public void onZombieAttack(int row, int column) {
        Zombie zombie = game.getZombie(row, column);
        zombieGifAttack(zombie);
    }

    @Override
    public void onPlantDie(Plant plant) {

    }

    public void zombieGifAttack(Zombie zombie) {
        JLabel zombieLabel = zombieLabels.get(zombie);
        ImageIcon CharacterGif = new ImageIcon(getClass().getResource("/gifs/basicZombieEating.gif"));
        zombieLabel.setIcon(CharacterGif);
    }

    public void backToMainMenu() {
        gameGUI.showMainMenu();
    }

    private JPanel prepareDayGame() {
        dayGame = new JPanel();
        dayGame.setLayout(null);

        panelDayGame = new JLayeredPane();
        panelDayGame.setLayout(null);

        contadorSunLabel = new JLabel();
        contadorSunLabel.setBounds(15, 60, 60, 30);
        Font font = new Font("Arial", Font.BOLD, 25); // Usa Arial, negrita, tamaño 30
        contadorSunLabel.setFont(font);

        contadorSunLabel.setForeground(new Color(0, 0, 0)); // Amarillo intenso
        contadorSunLabel.setBackground(new Color(243, 252, 169));  // Fondo oscuro
        contadorSunLabel.setOpaque(true);

        contadorSunLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        contadorSunLabel.setText("0");
        dayGame.add(contadorSunLabel);

        Dimension actualSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) actualSize.getWidth();
        int screenHeight = (int) actualSize.getHeight();

        ImageIcon originalImage = new ImageIcon(getClass().getResource("/Images/board.jpg"));
        Image scaledImage = originalImage.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel interfazLabel = new JLabel(scaledIcon);
        interfazLabel.setBounds(0, 0, screenWidth, screenHeight);

        panelDayGame.setPreferredSize(new Dimension(screenWidth, screenHeight));
        panelDayGame.setSize(screenWidth, screenHeight);
        panelDayGame.add(interfazLabel, JLayeredPane.DEFAULT_LAYER);

        JLayeredPane selector = preparePlantSelector();
        panelDayGame.add(selector, JLayeredPane.PALETTE_LAYER);
        prepareLawn();

        JLayeredPane menuLayeredPane = new JLayeredPane();
        menuLayeredPane.setBounds(screenWidth - 150, 10, 100, 40);

        ImageIcon menuImg = new ImageIcon(getClass().getResource("/Images/botonMenu.png"));
        JLabel menuImgLabel = new JLabel(menuImg);
        menuImgLabel.setBounds(0, 0, 100, 40);

        menuButton = new JButton();
        menuButton.setBounds(0, 0, 100, 40);
        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);

        menuLayeredPane.add(menuButton, JLayeredPane.DEFAULT_LAYER);
        menuLayeredPane.add(menuImgLabel, JLayeredPane.PALETTE_LAYER);
        panelDayGame.add(menuLayeredPane, JLayeredPane.PALETTE_LAYER);
        dayGame.add(panelDayGame);

        verifySuns();

        return dayGame;
    }

    private void verifySuns() {
        sunCounter = new SunCounter(() -> {
            game.receiveSun(25);
            contadorSunLabel.setText("" + game.getSuns());

        });
    }



    private JLayeredPane preparePlantSelector() {
        JLayeredPane plantSelector = new JLayeredPane();
        plantSelector.setPreferredSize(new Dimension(400, 87));
        plantSelector.setBounds(10, 10, 400, 87);

        ImageIcon selectorIcon = new ImageIcon(getClass().getResource("/Images/selector.png"));
        JLabel selectorLabel = new JLabel(selectorIcon);
        selectorLabel.setBounds(0, 0, selectorIcon.getIconWidth(), selectorIcon.getIconHeight());
        plantSelector.add(selectorLabel, JLayeredPane.PALETTE_LAYER);

        // Añadir botones para las plantas
        int buttonWidth = 50;
        int buttonHeight = 70;
        int startX = 80;
        int startY = 5;
        int gap = 10;

        sunflowerButton = new JButton();
        sunflowerButton.setBounds(startX + (buttonWidth + gap) * 0, startY, buttonWidth, buttonHeight);
        plantSelector.add(sunflowerButton, JLayeredPane.DEFAULT_LAYER);

        peashooterButton = new JButton();
        peashooterButton.setBounds(startX + (buttonWidth + gap) * 1, startY, buttonWidth, buttonHeight);
        plantSelector.add(peashooterButton, JLayeredPane.DEFAULT_LAYER);

        wallnutButton = new JButton();
        wallnutButton.setBounds(startX + (buttonWidth + gap) * 2, startY, buttonWidth, buttonHeight);
        plantSelector.add(wallnutButton, JLayeredPane.DEFAULT_LAYER);

        potatoMineButton = new JButton();
        potatoMineButton.setBounds(startX + (buttonWidth + gap) * 3, startY, buttonWidth, buttonHeight);
        plantSelector.add(potatoMineButton, JLayeredPane.DEFAULT_LAYER);

        return plantSelector;
    }


    private void startZombieTimer() {
        zombieTimer = new Timer(15000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    spawnZombies();
                } catch (POOBVsZombiesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        zombieTimer.setRepeats(true);
        zombieTimer.start();
    }

    private void spawnZombies() throws POOBVsZombiesException {
        String[] lista = {"basic", "conehead"};
        Random random = new Random();
        int index = random.nextInt(lista.length);
        int fila = random.nextInt(5);
        Zombie zombie = createZombie("conehead", fila, 9);
        game.addZombie(zombie, fila, 9);
        //System.out.println("Tipo de zombie: " + lista[index]);
        JLabel zombieLabel = addCharacterToBoard(fila, 9, "/gifs/" + lista[index] + ".gif");
        zombieLabels.put(zombie, zombieLabel);
        startZombieMovement(zombie, zombieLabel, fila);
    }

    private Zombie createZombie(String type, int fila, int col) {
        switch (type) {
            case "basic":
                return new ZombieBasic(fila, 10);
            case "conehead":
                return new ZombieConeHead(fila, 10);
            case "buckethead":
                return new ZombieBucketHead(fila, 10);
            default:
                throw new IllegalArgumentException("Invalid zombie type: " + type);
        }
    }
    private void startZombieMovement(Zombie zombie, JLabel zombieLabel, int fila) {
        int cellWidth = 90;
        int cellHeight = 120;
        int startX = 150 + (10 * cellWidth - (2 + 10) * 10); // Posición inicial en la columna 10
        int endX = 150; // Posición final en la columna 0
        int y = 80 + (fila * cellHeight); // Calculamos la posición vertical correcta

        int totalDistance = startX - endX;
        int duration = 60000; // 60 segundos para atravesar todo el tablero
        int updateInterval = 50; // Actualizar cada 50 ms para un movimiento más suave
        double pixelsPerUpdate = (double) totalDistance / (duration / updateInterval);

        Timer timer = new Timer(updateInterval, new ActionListener() {
            double currentX = startX;
            int currentColumn = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentX > endX) {
                    currentX -= pixelsPerUpdate;
                    zombieLabel.setLocation((int) currentX, y);
                    int newColumn = 9 - (int) ((startX - currentX) / cellWidth);
                    if (newColumn != currentColumn) {
                        // El zombie ha cambiado de columna
                        currentColumn = newColumn;
                        // Actualizamos la posición del zombie en el dominio
                        try {
                            System.out.println("Nueva columna: " + currentColumn);
                            // System.out.println("currentColumn: " + currentColumn);
                            game.updateZombiePosition(zombie, fila, currentColumn);
                            //System.out.println(game.getZombie(fila, currentColumn));
                        } catch (POOBVsZombiesException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }



    /**
     private void moveZombie(ActionEvent e, int endX, double pixelsPerUpdate, JLabel zombieLabel, int startX, int y, int fila, Zombie zombie) {
     double currentX = startX;
     int currentColumn = 10;
     if (currentX > endX) {
     currentX -= pixelsPerUpdate;
     zombieLabel.setLocation((int) currentX, y);
     int newColumn = 9 - (int) ((startX - currentX) / 90);
     if (newColumn != currentColumn) {
     // El zombie ha cambiado de columna
     currentColumn = newColumn;
     // Actualizamos la posición del zombie en el dominio
     try {
     //System.out.println("currentColumn: " + currentColumn);
     //System.out.println("fila: " + fila);
     game.updateZombiePosition(zombie, fila, currentColumn);
     System.out.println(game.getZombie(fila, currentColumn));
     } catch (POOBVsZombiesException ex) {
     JOptionPane.showMessageDialog(null, POOBVsZombiesException.INDEX_OUT_OF_RANGE);
     }
     }
     } else {
     ((Timer) e.getSource()).stop();
     }
     }
     **/

    private void prepareGameMenu() {
        JDialog gameMenuDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Menu del Juego", true);
        gameMenuDialog.setSize(300, 200);
        gameMenuDialog.setLocationRelativeTo(this);
        gameMenuDialog.setLayout(new GridLayout(3, 1, 10, 10));

        resumeButton = new JButton("Reanudar");
        saveButton = new JButton("Guardar Partida");//Exportar partida
        quitButton = new JButton("Salir al Menú Principal");
        importButton = new JButton("Cargar Partida");


        gameMenuDialog.add(resumeButton);
        gameMenuDialog.add(saveButton);
        gameMenuDialog.add(quitButton);
        gameMenuDialog.add(importButton);


        prepareGameMenuActions(gameMenuDialog);
        gameMenuDialog.setVisible(true);
    }

    private JLabel addCharacterToBoard(int fila, int columna, String rutaGif) {
        ImageIcon CharacterGif = new ImageIcon(getClass().getResource(rutaGif));
        JLabel CharacterLabel = new JLabel(CharacterGif);
        int x, y = 0;
        int cellWidth = 80;
        int cellHeight = 140;
        x = 190 + (columna * cellWidth);
        y = 105 + (fila * cellHeight);
        CharacterLabel.setBounds(x, y, cellWidth, cellHeight);
        panelDayGame.add(CharacterLabel, JLayeredPane.PALETTE_LAYER);
        return CharacterLabel;
    }

    /**
     private void addPea(int row, int col) {
     ImageIcon peaIcon = new ImageIcon(getClass().getResource("/Images/guisante.png"));
     JLabel peaLabel = new JLabel(peaIcon);
     int cellWidth = 80;
     int cellHeight = 140;
     int x = 190 + (col * cellWidth) + cellWidth / 2;  // Centrar el guisante en la celda
     int y = 100 + (row * cellHeight) + cellHeight / 2 - peaIcon.getIconHeight() / 2;  // Centrar verticalmente

     peaLabel.setBounds(x, y, peaIcon.getIconWidth(), peaIcon.getIconHeight());
     panelDayGame.add(peaLabel, JLayeredPane.PALETTE_LAYER);
     panelDayGame.revalidate();
     panelDayGame.repaint();
     movePea(peaLabel, row, col);
     }
     **/

    private void addPea(int row, int col) {
        ImageIcon peaIcon = new ImageIcon(getClass().getResource("/Images/guisante.png"));
        JLabel peaLabel = new JLabel(peaIcon);
        int cellWidth = 80;
        int cellHeight = 140;
        int x = 190 + (col * cellWidth) + cellWidth / 2;
        int y = 100 + (row * cellHeight) + cellHeight / 2 - peaIcon.getIconHeight() / 2;
        peaLabel.setBounds(x, y, peaIcon.getIconWidth(), peaIcon.getIconHeight());
        panelDayGame.add(peaLabel, JLayeredPane.PALETTE_LAYER);
        panelDayGame.revalidate();
        panelDayGame.repaint();
        movePea(peaLabel, row, col);
    }



    /**
     * private void movePea(JLabel peaLabel, int row) {
     * Timer peaTimer = new Timer(50, new ActionListener() {
     *
     * @Override public void actionPerformed(ActionEvent e) {
     * int currentX = peaLabel.getX();
     * if (currentX < 1000) {
     * peaLabel.setLocation(currentX + 5, peaLabel.getY());
     * } else {
     * ((Timer)e.getSource()).stop();
     * panelDayGame.remove(peaLabel);
     * panelDayGame.revalidate();
     * panelDayGame.repaint();
     * }
     * }
     * });
     * peaTimer.start();
     * }
     **/

    /** movePea
     * Se encarga de desplazar los guisantes(peas) generados por un peashooter
     * @param peaLabel Etiqueta que representa los guisantes(peas)
     * @param row Fila donde se encuentra el guisante
     * @param initialColumn Columna donde se encuentra el guisante
     */
    private void movePea(JLabel peaLabel, int row, int initialColumn) {
        int cellWidth = 80;  // Ancho de cada celda// Posición X inicial basada en la columna inicial
        Pea pea = new Pea(row, initialColumn);  // Crear una instancia de Pea en el dominio

        Timer peaTimer = new Timer(50, new ActionListener() {
            int currentColumn = initialColumn;
            boolean hasDamaged = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentX = peaLabel.getX();
                if (currentX < 1000 && !hasDamaged) {
                    Zombie zombie = game.getZombie(row, pea.getColumn());
                    if (zombie != null) {
                        hasDamaged = true;
                        panelDayGame.remove(peaLabel);
                        if (zombie.getHealth() <= 0) {
                            JLabel zombieLabel = zombieLabels.remove(zombie);
                            if (zombieLabel != null) {
                                panelDayGame.remove(zombieLabel);
                                game.deleteZombie(row, pea.getColumn());
                            }
                        }
                        panelDayGame.revalidate();
                        panelDayGame.repaint();
                        ((Timer) e.getSource()).stop();
                        return;
                    }
                    int newColumn = (currentX - 190) / cellWidth;
                    if (newColumn != currentColumn) {
                        currentColumn = newColumn;
                        pea.move();
                    }
                    peaLabel.setLocation(currentX + 5, peaLabel.getY());  // Mover el label
                } else {
                    ((Timer) e.getSource()).stop();
                    panelDayGame.remove(peaLabel);
                    panelDayGame.revalidate();
                    panelDayGame.repaint();
                }
            }
        });
        peaTimer.start();
    }

    /**
     * Este metodo se encagarga de preparar todo el campo de juego para plantar en las posiciones indicadas.
     */
    private void prepareLawn() {
        int rows = 5;
        int cols = 11;
        int cellWidth = 80;  // Ancho deseado para cada celda
        int cellHeight = 140; // Alto deseado para cada celda
        int startX = 190;    // Posición X inicial
        int startY = 100;    // Posición Y inicial

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JButton cellButton = new JButton();
                cellButton.setBounds(startX + (col * cellWidth), startY + (row * cellHeight), cellWidth, cellHeight);
                cellButton.setOpaque(false);
                cellButton.setContentAreaFilled(false);
                //cellButton.setBorderPainted(false);
                cellButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                int finalRow = row;
                int finalCol = col;
                cellButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (selectedPlant != null) {
                            //System.out.println("Columna: " + finalCol + ", Fila: " + finalRow);
                            putPlant(finalRow, finalCol);
                        }
                    }
                });
                panelDayGame.add(cellButton, JLayeredPane.PALETTE_LAYER);
            }
        }
    }


    /**putPlant
     *  Método encargado de plantar en el campo las plantas disponibles
     * @param row fila donde irá la planta
     * @param col columna donde irá la planta
     */
    private void putPlant(int row, int col) {
        try {
            Plant plant = null;
            String imagePath = null;

            switch (selectedPlant) {
                case "sunflower":
                    plant = new Sunflower(row, col);
                    imagePath = "/gifs/sunflower.gif";
                    verifyPlant(plant, row, col, imagePath);
                    break;
                case "peashooter":
                    plant = new Peashooter(row, col);
                    imagePath = "/gifs/peashooter.gif";
                    verifyPlant(plant, row, col, imagePath);
                    break;
                case "wallnut":
                    plant = new WallNut(row, col);
                    imagePath = "/gifs/wallnut.gif";
                    verifyPlant(plant, row, col, imagePath);
                    break;
                case "potatomine":
                    plant = new PotatoMine(row, col);
                    imagePath = "/gifs/potatomine.gif";
                    verifyPlant(plant, row, col, imagePath);
                    break;
                default:
                    return; // Si no hay planta seleccionada, no hacemos nada
            }

        } catch (POOBVsZombiesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /** verifyPlant
     * Método encargado de verificar si una planta puede ser plantada en la posición seleccionada
     * @param plant Tipo de planta (sunflower, peashooter, wallnut, potatoMine, ECIPlant)
     * @param row Fila donde irá la planta
     * @param col Columna donde irá la planta
     * @param imagePath String que indica la ruta de la representación de la planta
     * @throws POOBVsZombiesException
     */
    private void verifyPlant(Plant plant, int row, int col, String imagePath) throws POOBVsZombiesException {
        int newSunCount = game.getSuns();
        game.addPlant(plant, row, col);
        newSunCount -= plant.getCost();
        //System.out.println("newSunCount: " + newSunCount);
        game.setSuns(newSunCount);
        contadorSunLabel.setText(String.valueOf(newSunCount));
        addCharacterToBoard(row, col, imagePath);
        selectedPlant = null; // Reseteamos la selección después de colocar la planta

        if (plant instanceof Sunflower) {
            startGeneratingSunsForSunflower((Sunflower) plant, row, col);
        }
    }


    /** startGeneratingSunsForSunflower
     * Se encarga de generar los soles al plantar un sunflower
     * @param sunflower Planta que generea soles
     * @param row Fila donde se encuentra la sunflower
     * @param col Columna donde se encuentra la sunflower
     */
    private void startGeneratingSunsForSunflower(Sunflower sunflower, int row, int col) {
        Timer sunGenerationTimer = new Timer(10000, e -> { // Generar un sol cada 5 segundos
            Sun sun = sunflower.generateSun(); // Generar el objeto Sun en el dominio
            //sun.setPosition(190 + (col * 80), 100 + (row * 140)); // Configurar la posición del sol en la interfaz
            sun.setPosition(sunflower.getRow(), sunflower.getColumn());
            game.addSuns(sun); // Registrar el sol en el dominio
            addSunToBoard(sun); // Mostrar el sol en la interfaz gráfica
        });
        sunGenerationTimer.start();
    }


    /** startGameManually
     * Se encarga de empezar el juego después de seleccionar las plantas
     */

    public void startGameManually() {
        startZombieTimer();
        game.setEventListener(this);
        game.startGame();
        contadorSunLabel.setText("0");
        sunCounter.reset();
        sunCounter.start();
        syncGeneratedSuns();
        System.out.println("El juego ha empezado");
    }

    /** addSunToBoard
     * Se encarga de generar visualmente los soles generados por un sunflower
     * @param sun Instancia de Sun
     */
    private void addSunToBoard(Sun sun) {
        ImageIcon sunIcon = new ImageIcon(getClass().getResource("/gifs/sun.gif"));
        JLabel sunLabel = new JLabel(sunIcon);
        sunLabel.setBounds(sun.getxPos(), sun.getyPos(), sunIcon.getIconWidth(), sunIcon.getIconHeight());

        sunLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.collectSuns(sun);
                panelDayGame.remove(sunLabel);
                panelDayGame.revalidate();
                panelDayGame.repaint();
                contadorSunLabel.setText(String.valueOf(game.getSuns()));
            }
        });
        panelDayGame.add(sunLabel, JLayeredPane.MODAL_LAYER);
        panelDayGame.revalidate();
        panelDayGame.repaint();
    }

    private void syncGeneratedSuns() {
        Timer syncTimer = new Timer(1000, e -> {
            ArrayList<Sun> suns = game.getSunsGenerated();
            for (Sun sun : suns) {
                if (!sun.isCollected()) {
                    addSunToBoard(sun); // Mostrar soles no recogidos en el tablero
                }
            }
        });
        syncTimer.start();
    }

    /**public void resetGame() {
     if (sunCounter != null) {
     sunCounter.stop();
     }
     if (zombieTimer != null) {
     zombieTimer.stop();
     }

     panelDayGame.removeAll();
     prepareLawn();
     contadorSunLabel.setText("0");
     selectedPlant = null;
     panelDayGame.revalidate();
     panelDayGame.repaint();
     }**/

    /**public void importAction() {
     JFileChooser fileChooser = new JFileChooser();
     fileChooser.setDialogTitle("Seleccione un archivo para importar");
     fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos texto", "txt"));
     int value = fileChooser.showOpenDialog(this);

     if (value == JFileChooser.APPROVE_OPTION) {
     File selectedFile = fileChooser.getSelectedFile();

     try {
     game.importar(selectedFile);
     JOptionPane.showMessageDialog(this, "Archivo importado exitosamente. ", "Exito", JOptionPane.INFORMATION_MESSAGE);
     photo.repaint();
     } catch (POOBVsZombiesException e) {
     JOptionPane.showMessageDialog(null, e.getMessage());
     }
     }
     }**/

}
