package src.presentation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class PlantsVsZombiesGUI extends JFrame {
    private ScreenGame screenGame;
    private ScreenGamePvsP screenGamePvsP;
    private SelectPlantsZombies selectPlantsZombies;
    private JPanel panels;
    private JPanel panelPrincipal, startPanel, gameMode;
    private JButton startButton, exitButton, instructionsButton, backButton, pvsMButton, pvsPButton, mvsMButton, backButtonGameMode;
    private CardLayout cardLayout;
    private JButton dia, noche, techo;
    private Clip backgroundMusic;
    private String currentGameMode;


    public PlantsVsZombiesGUI() {
        prepareElements();
        prepareActions();

    }


    public static void main(String[] args) {
        PlantsVsZombiesGUI plantsVsZombiesGUI = new PlantsVsZombiesGUI();
        plantsVsZombiesGUI.setVisible(true);
    }

    private void prepareElements() {
        setTitle("POOBVsZombies");
        Dimension actualSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (actualSize.getWidth() / 2), (int) (actualSize.getHeight() / 2));
        setLocationRelativeTo(null);

        panels = new JPanel();
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel();
        selectPlantsZombies = new SelectPlantsZombies(this);
        screenGame = new ScreenGame(this);
        screenGamePvsP = new ScreenGamePvsP(this);
        panels.setLayout(cardLayout);

        panelPrincipal = prepareMainMenu();
        startPanel = prepareStartMenu();
        gameMode = gameMode();

        panels.add(panelPrincipal, "Menu");
        panels.add(startPanel, "start");
        panels.add(gameMode, "gameMode");
        panels.add(screenGame, "screenGame");
        panels.add(selectPlantsZombies, "selectPlantsZombies");
        panels.add(screenGamePvsP, "screenGamePvsP");

        setContentPane(panels);
        playBackgroundMusic("/resources/Sounds/MainMenu.wav");
    }


    private void prepareActions() {
        prepareActionsStartMenu();
        prepareActionsgameMode();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {cardLayout.show(panels, "start");}});
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {exit();}});
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {cardLayout.show(panels, "Menu");}});
    }

    private void prepareActionsStartMenu() {
        dia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panels, "gameMode");
            }
        });
    }

    private void prepareActionsgameMode() {
        pvsMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentGameMode("PvsM");
                showSelectPlantsZombies();
            }
        });
        pvsPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentGameMode("PvsP");
                showSelectPlantsZombies();}
        });

        backButtonGameMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {cardLayout.show(panels, "start");}});
    }

    public void setCurrentGameMode(String gameMode){this.currentGameMode = gameMode;}

    public String getCurrentGameMode(){return this.currentGameMode;}






    public void showSelectPlantsZombies() {
        System.out.println("Mostrando selectePlants");
        cardLayout.show(panels, "selectPlantsZombies");
        repaint();
    }

    public void showGameMode(){
        cardLayout.show(panels, "gameMode");
        revalidate();
        repaint();
    }

    public void showGameScreen() {
        stopBackgroundMusic();
        cardLayout.show(panels, "screenGame");
        playBackgroundMusic("/resources/Sounds/DayStage.wav");
        screenGame.startGameManually();
        revalidate();
        repaint();
    }

    public void showScreenGamePvsP(){
        cardLayout.show(panels, "screenGamePvsP");
        screenGamePvsP.startGameManual();
        revalidate();
        repaint();
    }

    public void showMainMenu() {
    cardLayout.show(panels, "Menu");
    stopBackgroundMusic();
    playBackgroundMusic("/resources/Sounds/MainMenu.wav");
    repaint();
}

    private void exit() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres cerrar la aplicación?", "Confirmación de Cierre", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JPanel prepareMainMenu() {
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/Images/layout_clean.png"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

        ImageIcon graveImage = new ImageIcon(getClass().getResource("/resources/Images/grave1.jpg"));
        JLabel graveLabel = new JLabel();
        graveLabel.setBounds(520, 150, graveImage.getIconWidth(), graveImage.getIconHeight());

        // Botones
        startButton = createHoverButton("Iniciar Partida", 645,320, 250, 50);
        instructionsButton = createHoverButton("Instrucciones", 645, 390, 250, 50);
        exitButton = createHoverButton("Salir", 645, 460, 250, 50);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), backgroundImage.getIconHeight()));

        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER); // Fondo
        layeredPane.add(graveLabel, JLayeredPane.PALETTE_LAYER);      // Tumba
        layeredPane.add(startButton, JLayeredPane.MODAL_LAYER);       // Botones
        layeredPane.add(instructionsButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(exitButton, JLayeredPane.MODAL_LAYER);

        panelPrincipal.add(layeredPane);
        return panelPrincipal;
    }


    private JPanel prepareStartMenu() {
        //Fondo

        ImageIcon startbackgroundImage = new ImageIcon(getClass().getResource("/resources/Images/start.png"));
        JLabel startbackgroundLabel = new JLabel(startbackgroundImage);
        startbackgroundLabel.setBounds(0, 0, startbackgroundImage.getIconWidth(), startbackgroundImage.getIconHeight());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Usar null layout para control manual
        layeredPane.setPreferredSize(new Dimension(startbackgroundImage.getIconWidth(), startbackgroundImage.getIconHeight()));
        layeredPane.add(startbackgroundLabel, JLayeredPane.DEFAULT_LAYER);

        //Botones
        backButton = createHoverButton("", 70, 60, 150, 40);
        dia = createHoverButton("Día", 665, 338, 206, 59);


        layeredPane.add(backButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(dia, JLayeredPane.MODAL_LAYER);

        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.add(layeredPane, BorderLayout.CENTER);
        setContentPane(startPanel);

        return startPanel;
    }

    /**
     * Este método permite seleccionar que modalidad se jugará en la partida (PvsM) (MvsM) (PvsP)
     * @return
     */

    private JPanel gameMode(){
        JPanel gameModePanel = new JPanel();
        ImageIcon gameModebackgroundImage = new ImageIcon(getClass().getResource("/resources/Images/gameModeScreen.png"));
        JLabel gameModebackgroundLabel = new JLabel(gameModebackgroundImage);
        gameModebackgroundLabel.setBounds(0, 0, gameModebackgroundImage.getIconWidth(), gameModebackgroundImage.getIconHeight());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setPreferredSize(new Dimension(gameModebackgroundImage.getIconWidth(), gameModebackgroundImage.getIconHeight()));
        layeredPane.add(gameModebackgroundLabel, JLayeredPane.DEFAULT_LAYER);

        JLabel titleLabel = new JLabel("Modalidad de juego");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 36));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBounds(576, 67, 400, 50);
        layeredPane.add(titleLabel, JLayeredPane.MODAL_LAYER);

        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>"
                + "PVSM: Jugador VS Maquina<br>"
                + "MVSM: Maquina VS Maquina<br>"
                + "PVSP: Jugador VS Jugador"
                + "</div></html>");
        infoLabel.setFont(new Font("Algerian", Font.BOLD, 29));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(114, 294, 400, 241); // Ajusta el tamaño y posición según sea necesario
        layeredPane.add(infoLabel, JLayeredPane.MODAL_LAYER);

        pvsMButton = createHoverButton("PvsM", 668, 329, 200, 50);
        mvsMButton = createHoverButton("MvsM", 668, 407, 200, 50);
        pvsPButton = createHoverButton("PvsP", 668, 485, 200, 50);
        backButtonGameMode = createHoverButton("", 29, 73, 165, 92);

        layeredPane.add(pvsMButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(mvsMButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(pvsPButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(backButtonGameMode, JLayeredPane.PALETTE_LAYER);

        gameModePanel.add(layeredPane, BorderLayout.CENTER);

        System.out.println("Width: " + gameModePanel.getWidth() + ", Height: " + gameModePanel.getHeight());
        return gameModePanel;
    }


    private JButton createHoverButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Algerian", Font.BOLD, 25));
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(79, 76, 76));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return button;
    }



    private void playBackgroundMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioInput);
                backgroundMusic.start();
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("No se pudo encontrar el archivo de música.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    private void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }
}
