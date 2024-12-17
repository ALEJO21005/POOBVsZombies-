package src.presentation;
import src.domain.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class ScreenGamePvsP extends JPanel {
    private PlantsVsZombiesGUI gameGUI;
    private POOBVsZombies game;
    private JPanel dayGame;
    private JLayeredPane panelDayGame;
    private JButton menuButton;
    private JButton resumeButton, saveButton,
            quitButton, sunflowerButton,
            peashooterButton, wallnutButton, potatoMineButton;
    private JButton basicZombieButton, coneheadZombieButton, bucketZombieButton,
            brainsteinButton, ECIZombiebutton;
    private JButton importButton, exportButton;
    private SunCounter sunCounter;
    private Timer zombieTimer;
    private JLabel contadorSunLabel, countBrainLabel;
    private String selectedPlant = null;
    private String selectedZombie = null;
    private ArrayList<Pea> peas = new ArrayList<>();
    private Timer guisanteTimer;
    private HashMap<Zombie, JLabel> zombieLabels = new HashMap<>();
    private SelectPlantsZombies plantsZombies;
    private JLabel timerLabel;
    private Timer countdownTimer;
    private boolean canPlacePlants = true;



    public ScreenGamePvsP(PlantsVsZombiesGUI mainGUI) {
        game = new POOBVsZombies();
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
                if(!canPlacePlants){
                    JOptionPane.showMessageDialog(null, "No puedes plantar, el tiempo se ha agotado");
                    return;
                }
                selectedPlant = "sunflower";
            }
        });

        peashooterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!canPlacePlants){
                    JOptionPane.showMessageDialog(null, "No puedes plantar, el tiempo se ha agotado");
                    return;
                }
                selectedPlant = "peashooter";
            }
        });

        wallnutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!canPlacePlants){
                    JOptionPane.showMessageDialog(null, "No puedes plantar, el tiempo se ha agotado");
                    return;
                }
                selectedPlant = "wallnut";
            }
        });

        potatoMineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!canPlacePlants){
                    JOptionPane.showMessageDialog(null, "No puedes plantar, el tiempo se ha agotado");
                    return;
                }
                selectedPlant = "potatomine";
            }
        });

        basicZombieButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               selectedZombie = "basicZombie";
           }
        });

        coneheadZombieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               selectedZombie = "coneheadZombie";
           }
        });

        bucketZombieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               selectedZombie = "bucketZombie";
           }
        });

        brainsteinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               selectedZombie = "brainstein";
           }
        });

        /**ECIZombiebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               selectedZombie = "ECIZombie";
           }
        });**/
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

    /**
    @Override
    public void onPlantAttack(int row, int col) {
        addPea(row, col);
    }

    @Override
    public void onZombieDie(Zombie zombie) {
    }**/

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
        Font font = new Font("Arial", Font.BOLD, 25); // Usa Arial, negrita, tamaño 25
        contadorSunLabel.setFont(font);

        contadorSunLabel.setForeground(new Color(0, 0, 0)); // Amarillo intenso
        contadorSunLabel.setBackground(new Color(243, 252, 169));  // Fondo oscuro
        contadorSunLabel.setOpaque(true);

        contadorSunLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        contadorSunLabel.setText("0");
        dayGame.add(contadorSunLabel);

        countBrainLabel = new JLabel();
        countBrainLabel.setBounds(972, 58, 60, 30);
        countBrainLabel.setForeground(new Color(0, 0, 0));
        countBrainLabel.setBackground(new Color(243,252,169));
        countBrainLabel.setOpaque(true);
        countBrainLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        countBrainLabel.setText("0");
        countBrainLabel.setFont(font);
        dayGame.add(countBrainLabel);

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
        JLayeredPane zombiesSelector = prepareZombieSelector();
        panelDayGame.add(zombiesSelector, JLayeredPane.PALETTE_LAYER);
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

    public JLayeredPane prepareZombieSelector(){
        JLayeredPane zombieSelector = new JLayeredPane();
        zombieSelector.setBounds(961, 10, 400, 87);
        zombieSelector.setOpaque(false);

        ImageIcon selectorIcon = new ImageIcon(getClass().getResource("/Images/ZombieSelector.png"));
        JLabel selectorLabel = new JLabel(selectorIcon);
        selectorLabel.setBounds(0, 0, selectorIcon.getIconWidth(), selectorIcon.getIconHeight());
        zombieSelector.add(selectorLabel, JLayeredPane.PALETTE_LAYER);

        // Añadir botones para los zombies
        int buttonWidth = 49;
        int buttonHeight = 75;
        int startX = 1049;
        int startY = 11;
        int gap = 14;

        basicZombieButton = new JButton();
        basicZombieButton.setBounds(startX + (buttonWidth + gap) * 0, startY, buttonWidth, buttonHeight);
        zombieSelector.add(basicZombieButton, JLayeredPane.MODAL_LAYER);
        
        coneheadZombieButton = new JButton();
        coneheadZombieButton.setBounds(startX + (buttonWidth + gap) * 1, startY, buttonWidth, buttonHeight);
        zombieSelector.add(coneheadZombieButton, JLayeredPane.DEFAULT_LAYER);
        
        bucketZombieButton = new JButton();
        bucketZombieButton.setBounds(startX + (buttonWidth + gap) * 2, startY, buttonWidth, buttonHeight);
        zombieSelector.add(bucketZombieButton, JLayeredPane.DEFAULT_LAYER);

        brainsteinButton = new JButton();
        brainsteinButton.setBounds(startX + (buttonWidth + gap) * 3, startY, buttonWidth, buttonHeight);
        zombieSelector.add(brainsteinButton, JLayeredPane.DEFAULT_LAYER);


        return zombieSelector;
    }


    private void startZombieTimer() {
        zombieTimer = new Timer(20000, new ActionListener() {
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
        String[] lista = {"basic", "conehead", "buckethead", "brainstein"};
        Random random = new Random();
        int index = random.nextInt(lista.length);
        int fila = random.nextInt(5);
        Zombie zombie = createZombie(lista[index], fila, 9);
        game.addZombie(zombie, fila, 9);
        //System.out.println("Tipo de zombie: " + lista[index]);
        JLabel zombieLabel = addCharacterToBoard(fila, 9, "/gifs/" + lista[index] + ".gif");
        zombieLabels.put(zombie, zombieLabel);
        startZombieMovement(zombie, zombieLabel, fila);
    }

    private Zombie createZombie(String selectedZombie, int fila, int col) {
        switch (selectedZombie) {
            case "basicZombie":
                return new ZombieBasic(fila, 10);
            case "coneheadZombie":
                return new ZombieConeHead(fila, 10);
            case "bucketheadZombie":
                return new ZombieBucketHead(fila, 10);
            case "brainstein":
                return new ZombieBrainstein(fila, 10);
            default:
                throw new IllegalArgumentException("Invalid zombie type: " + selectedZombie);
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
                    System.out.println("Fila: " + zombie.getRow() + ", columna: " + zombie.getColumn());
                    currentX -= pixelsPerUpdate;
                    zombieLabel.setLocation((int) currentX, y);
                    int newColumn = 9 - (int) ((startX - currentX) / cellWidth);
                    if (newColumn != currentColumn) {
                        // El zombie ha cambiado de columna
                        currentColumn = newColumn;
                        // Actualizamos la posición del zombie en el dominio
                        try {
                            // System.out.println("currentColumn: " + currentColumn);
                            game.updateZombiePosition(zombie, fila, currentColumn);
                            //System.out.println(game.getZombie(fila, currentColumn));
                        } catch (POOBVsZombiesException ex) {
                            JOptionPane.showMessageDialog(null, POOBVsZombiesException.INDEX_OUT_OF_RANGE);
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


    private void addCountdownTimer(JPanel parentPanel) {
        timerLabel = new JLabel("02:00"); // Tiempo inicial
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setBounds(550, 10, 100, 40); // Ajusta la posición y tamaño
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setOpaque(true);
        timerLabel.setForeground(new Color(0, 0, 0)); // Amarillo intenso
        timerLabel.setBackground(new Color(243, 252, 169));  // Fondo oscuro
        parentPanel.add(timerLabel);

        // Crear el Timer
        countdownTimer = new Timer(1000, new ActionListener() {
            int timeLeft = 120; // 2 minutos en segundos

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    int minutes = timeLeft / 60;
                    int seconds = timeLeft % 60;
                    timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
                } else {
                    countdownTimer.stop();
                    timerLabel.setText("00:00");
                    canPlacePlants = false;
                    //disablePlantSelection();
                    JOptionPane.showMessageDialog(null, "¡Tiempo agotado para seleccionar plantas!");
                }
            }
        });
        countdownTimer.start();
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
        //System.out.println("initialColumn: " + initialColumn);
        Pea pea = new Pea(row, initialColumn);  // Crear una instancia de Pea en el dominio

        Timer peaTimer = new Timer(50, new ActionListener() {
            int currentColumn = initialColumn;
            boolean hasDamaged = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentX = peaLabel.getX();
                if (currentX < 1000 && !hasDamaged) {
                    Zombie zombie = game.getZombie(row, pea.getColumn());
                    if (zombie != null && zombie.getHealth() > 0) {
                        System.out.println("Vida del zombie: " + zombie.getHealth());
                        zombie.reduceHealth(Peashooter.DAMAGE);
                        //System.out.println("Vida del zombie: " + zombie.getHealth());
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
                        //System.out.println("Guisante cambió a columna: " + currentColumn);
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

    /** PrepareLawn
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

    private void putZombies(int row, int col){
        Zombie zombie = null;
        String imagePath = null;

        switch (selectedZombie) {
            case "basic":
                zombie = new ZombieBasic(row, col);
                imagePath = "/gifs/basic.gif";
                break;
            case "conehead":
                zombie = new ZombieConeHead(row, col);
                imagePath = "/gifs/conehead.gif";
                break;
            case "buckethead":
                zombie = new ZombieBucketHead(row, col);
                imagePath = "/gifs/buckethead.gif";
                break;
            case "brainstein":
                zombie = new ZombieBrainstein(row, col);
                imagePath = "/gifs/brainstein.gif";
                break;
            default:
                return;
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

    private void verifyPutZombies(Zombie zombie, int row, int col, String imagePath) throws POOBVsZombiesException {
        int newBrainCount = game.getBrains();
        game.addZombie(zombie, row, col);
        newBrainCount -= zombie.getCost();
        game.setBrains(newBrainCount);
        countBrainLabel.setText(String.valueOf(newBrainCount));
        addCharacterToBoard(row, col, imagePath);
        selectedZombie = null;

        if(zombie instanceof ZombieBrainstein){
            startGeneratingBrainSteinsForBrainstein((ZombieBrainstein) zombie, row, col);
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


    private void startGeneratingBrainSteinsForBrainstein(ZombieBrainstein brainstein, int row, int column) {
        Timer brainGenerationTimer = new Timer(10000, e -> { // Generar un asteroide cada 5 segundos
            Brain brain = brainstein.generateBrain(); // Generar el objeto Brainstein en el dominio
            //brain.setPosition(190 + (column * 80), 100 + (row * 140)); // Configurar la posición del asteroide en la interfaz
            brain.setPosition(brainstein.getRow(), brainstein.getColumn());
            game.addBrains(brain); // Registrar el asteroide en el dominio
            addBrainToBoard(brain); // Mostrar el asteroide en la interfaz gráfica
        });
        brainGenerationTimer.start();
    }


    /** startGameManually
     * Se encarga de empezar el juego después de seleccionar las plantas
     */

    public void startGameManual() {
        addCountdownTimer(dayGame);
        startZombieTimer();
        game.startGame();
        contadorSunLabel.setText("0");
        countBrainLabel.setText("0");
        //Imprlemetar brain counter

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

    private void addBrainToBoard(Brain brain){
        ImageIcon brainIcon = new ImageIcon(getClass().getResource("/gifs/brain.gif"));
        JLabel brainLabel = new JLabel(brainIcon);
        brainLabel.setBounds(brain.getxPos(), brain.getyPos(), brainIcon.getIconWidth(), brainIcon.getIconHeight());

        brainLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.collectBrains(brain);
                panelDayGame.remove(brainLabel);
                panelDayGame.revalidate();
                panelDayGame.repaint();
                countBrainLabel.setText(String.valueOf(game.getBrains()));
            }
        });
        panelDayGame.add(brainLabel, JLayeredPane.MODAL_LAYER);
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



    private int [] calculateScore(){
        int  plantsScore = 0;
        int zombiesScore = 0;

        Plant [][] plantCost = game.getPlantsMatrix();
        Zombie [][] zombieCost = game.getZombiesMatrix();

        for(int i = 0; i < plantCost.length; i++){
            for(int j = 0; j < plantCost[i].length; j++){
                if(plantCost[i][j]!= null){
                    plantsScore += plantCost[i][j].getCost();
                }
            }
        }


        return new int[]{plantsScore, zombiesScore};
    }

}
