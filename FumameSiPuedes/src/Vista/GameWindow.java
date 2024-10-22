package FumameSiPuedes.src.Vista;

import FumameSiPuedes.src.Modelo.Obstaculo;
import FumameSiPuedes.src.Modelo.ObstaculoMovil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameWindow extends Canvas {

    private JFrame frame;
    private JPanel gamePanel;
    private JLabel character;
    private List<Obstaculo> obstaculos;  // Lista de obstáculos
    private int characterX = 50;
    private int characterY = 300;
    private int moveSpeed = 10;

    public GameWindow(int width, int height, String title) {
        frame = new JFrame(title);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bgImage = new ImageIcon("FumameSiPuedes/src/Vista/imgs/Villa-31-01.jpg");
                g.drawImage(bgImage.getImage(), 0, 0, width, height, null);
            }
        };
        gamePanel.setLayout(null);
        gamePanel.setPreferredSize(new Dimension(width, height));

        character = new JLabel(new ImageIcon("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png"));
        character.setBounds(characterX, characterY, 50, 50);
        gamePanel.add(character);

        // Crear la lista de obstáculos
        obstaculos = new ArrayList<>();
        // Añadir obstáculos móviles
        obstaculos.add(new ObstaculoMovil(300, 400, 50, 50, "FumameSiPuedes/src/Vista/imgs/cenicero-removebg-preview.png", 5));

        // Añadir obstáculos al panel del juego
        for (Obstaculo obstaculo : obstaculos) {
            gamePanel.add(obstaculo);
        }

        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Movimiento del personaje
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moveCharacter(e);
            }
        });
        setFocusable(true);
    }

    private void moveCharacter(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                characterX = Math.max(0, characterX - moveSpeed);
                break;
            case KeyEvent.VK_RIGHT:
                characterX = Math.min(gamePanel.getWidth() - character.getWidth(), characterX + moveSpeed);
                break;
            case KeyEvent.VK_UP:
                characterY = Math.max(0, characterY - moveSpeed);
                break;
            case KeyEvent.VK_DOWN:
                characterY = Math.min(gamePanel.getHeight() - character.getHeight(), characterY + moveSpeed);
                break;
        }
        character.setBounds(characterX, characterY, character.getWidth(), character.getHeight());
        gamePanel.repaint();

        // Verificar colisiones con obstáculos
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.colisionaCon(character)) {
                System.out.println("¡Colisión con un obstáculo!");
                // Aquí podrías implementar la lógica de daño al personaje
            }
        }

        // Actualizar posición de los obstáculos
        for (Obstaculo obstaculo : obstaculos) {
            obstaculo.actualizar();
        }
    }
}
