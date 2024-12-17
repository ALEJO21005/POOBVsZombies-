package src.presentation;
import src.domain.POOBVsZombiesException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

public class SelectPlantsZombies extends JPanel {

    private PlantsVsZombiesGUI gameScreen;
    private JPanel plantSelectPanel;
    private JPanel zombiesSelectPanel;
    private JPanel mainPanel;
    private JButton peashooterButton, sunflowerButton, wallnutButton, mineButton;
    private JButton basicZombieButton, coneZombieButton, bucketZombieButton;
    private JButton nextButton, previousButton;
    private Queue<String> selectedObjectQueue;
    private Queue<String> selectedZombies;
    private boolean plantIsSelected = false;

    public SelectPlantsZombies(PlantsVsZombiesGUI mainInterface) {
        this.gameScreen = mainInterface;
        selectedObjectQueue = new LinkedList<>();
        prepareElements();
        prepareActionsSelectPlants();
        prepareActionsSelectZombies();
        prepareActionButton();
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
    }

    public void prepareElements() {
        mainPanel = new JPanel();
        selectedZombies = new LinkedList<>();
        mainPanel.setLayout(new BorderLayout());
        prepareMainPanel();
    }

    public JPanel prepareMainPanel() {
        // Fondo
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/resources/Images/PlantSelector.png"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());

        // Paneles para selección de plantas y zombies
        plantSelectPanel = new JPanel();
        plantSelectPanel.setBounds(75, 560, 680, 200);
        plantSelectPanel.setOpaque(false);
        plantSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        zombiesSelectPanel = new JPanel();
        zombiesSelectPanel.setBounds(785, 560, 680, 200);
        zombiesSelectPanel.setOpaque(false);
        zombiesSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));


        //Botones
        peashooterButton = prepareButtons(154, 225, 114, 160);
        sunflowerButton = prepareButtons(290, 225, 114, 160);
        wallnutButton = prepareButtons(425, 225, 114, 160);
        mineButton = prepareButtons(561, 225, 114, 160);
        basicZombieButton = prepareButtons(953, 225, 114, 160);
        coneZombieButton = prepareButtons(1111, 225, 114, 160);
        bucketZombieButton = prepareButtons(1268, 225, 114, 160);
        previousButton = prepareButtons(61, 86, 286, 92);
        nextButton = prepareButtons(1192, 86, 286, 92);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), backgroundImage.getIconHeight()));
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER); // Fondo
        layeredPane.add(plantSelectPanel, JLayeredPane.MODAL_LAYER); // Plantas seleccionadas
        layeredPane.add(zombiesSelectPanel, JLayeredPane.MODAL_LAYER); // Zombies seleccionados
        layeredPane.add(peashooterButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(sunflowerButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(wallnutButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(mineButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(basicZombieButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(coneZombieButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(bucketZombieButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(previousButton, JLayeredPane.MODAL_LAYER);
        layeredPane.add(nextButton, JLayeredPane.MODAL_LAYER);
        mainPanel.add(layeredPane, BorderLayout.CENTER);
        return mainPanel;
    }

    private JButton prepareButtons(int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
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

    private void prepareActionsSelectPlants() {
        peashooterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón presionado: Peashooter"); // Debug
                addToPlantsPanel(plantSelectPanel, "/resources/Images/semillaLanzaguisantes.jpg");
            }
        });

        sunflowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToPlantsPanel(plantSelectPanel, "/resources/Images/GirasolSemilla.jpg");
            }
        });

        wallnutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToPlantsPanel(plantSelectPanel, "/resources/Images/NuezSemilla.jpg");
            }
        });

        mineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToPlantsPanel(plantSelectPanel, "/resources/Images/PapapumSemilla.jpg");
            }
        });
    }

    private void prepareActionsSelectZombies() {
        basicZombieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToZombiesPanel(zombiesSelectPanel, "/resources/Images/basicZombie.png");
            }
        });
        coneZombieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToZombiesPanel(zombiesSelectPanel, "/resources/Images/coneZombie.png");
            }
        });
        bucketZombieButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToZombiesPanel(zombiesSelectPanel, "/resources/Images/bucketZombie.png");
            }
        });
    }

    private void prepareActionButton() {
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showNextWindow();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousWindow();
            }
        });
    }

    public JPanel addToPlantsPanel(JPanel panel, String imagePath) {
        if(panel == null){
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
            //panel.setOpaque(false);
        }

        try {
            if (isObjectOnQueue(imagePath)) {
                throw new POOBVsZombiesException(POOBVsZombiesException.PLANT_DUPLICATE);
            }
            selectedObjectQueue.add(imagePath);
            System.out.println(selectedObjectQueue);
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
            JLabel plantLabel = new JLabel(imageIcon);
            plantLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            plantLabel.setOpaque(false);

            JPanel finalPanel = panel;
            plantLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    finalPanel.remove(plantLabel);
                    selectedObjectQueue.remove(imagePath);
                    finalPanel.revalidate();
                    finalPanel.repaint();
                }
            });

            panel.add(plantLabel);
            panel.revalidate();
            panel.repaint();
        } catch (POOBVsZombiesException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return plantSelectPanel;
    }




    public Queue addToZombiesPanel(JPanel panel, String imagePath) {

        try {
            if (isObjectOnQueue(imagePath)) {
                throw new POOBVsZombiesException(POOBVsZombiesException.PLANT_DUPLICATE);
            }
            selectedZombies.add(imagePath);
            System.out.println(selectedZombies);
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
            JLabel plantLabel = new JLabel(imageIcon);
            plantLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            plantLabel.setOpaque(false);

            plantLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    panel.remove(plantLabel);
                    selectedZombies.remove(imagePath);
                    panel.revalidate();
                    panel.repaint();
                }
            });

            panel.add(plantLabel);
            panel.revalidate();
            panel.repaint();
        } catch (POOBVsZombiesException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return selectedZombies;
    }

    private boolean isObjectOnQueue(String imagePath) {
        return selectedObjectQueue.contains(imagePath);
    }


    public void showNextWindow() {
        String mode = gameScreen.getCurrentGameMode();
        if (mode == "PvsM") {
            gameScreen.showGameScreen();
        } else if (mode == "PvsP") {
            gameScreen.showScreenGamePvsP();
        }
    }

    public void showPreviousWindow() {
        gameScreen.showGameMode();
    }





}
