package FumameSiPuedes.src.Vista;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import FumameSiPuedes.src.Modelo.Cigarrillo;

public class Lienzo extends JPanel implements KeyListener {

    private Image imagenFondo;  // Imagen de fondo
    private Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();
    private JLabel imagenPersonaje = cigarrilloSmooki.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png");
    private boolean enSalto = false; // Control de estado del salto

    public Lienzo() {
        // Cargar la imagen de fondo
        imagenFondo = new ImageIcon("FumameSiPuedes/src/Vista/imgs/fondo.jpg").getImage();

        // Configurar el panel y el personaje
        addKeyListener(this);
        setFocusable(true);
        setLayout(null); // Para ubicar manualmente el personaje
        imagenPersonaje.setBounds(50, 680, imagenPersonaje.getIcon().getIconWidth(), imagenPersonaje.getIcon().getIconHeight()); // Posición inicial
        add(imagenPersonaje);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo que ocupe todo el tamaño del panel
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int step = 25; // Tamaño del paso de movimiento
        int x = imagenPersonaje.getX();
        int y = imagenPersonaje.getY();
        int width = imagenPersonaje.getWidth();
        int height = imagenPersonaje.getHeight();
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Movimiento con verificación de límites y estado de salto
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP && !enSalto) {
            iniciarSalto();
        }
        else if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE && !enSalto) {
            iniciarSalto();
        }
        else if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN && y + step + height <= (panelHeight - 180)) {
            imagenPersonaje.setLocation(x, y + step);
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT && x - step >= 0) {
            imagenPersonaje.setLocation(x - step, y);
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT && x + step + width <= panelWidth) {
            imagenPersonaje.setLocation(x + step, y);
        }
    }

    // Método para iniciar el salto
    private void iniciarSalto() {
        enSalto = true; // Iniciar el salto
        int alturaMaxima = 100; // Altura máxima del salto
        int velocidad = 10;     // Velocidad de cada paso de la animación
        int delay = 20;         // Tiempo entre cada paso (milisegundos)

        Timer timer = new Timer(delay, null);
        final int[] alturaActual = {0}; // Posición actual en la altura del salto
        final boolean[] subiendo = {true}; // Controla si está subiendo o bajando

        timer.addActionListener(e -> {
            int y = imagenPersonaje.getY();

            if (subiendo[0]) {
                // Movimiento hacia arriba con límite de altura
                if (alturaActual[0] < alturaMaxima) {
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), y - velocidad);
                    alturaActual[0] += velocidad;
                } else {
                    subiendo[0] = false; // Cambia a la fase de caída
                }
            } else {
                // Movimiento hacia abajo hasta regresar al suelo
                if (alturaActual[0] > 0) {
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), y + velocidad);
                    alturaActual[0] -= velocidad;
                } else {
                    timer.stop();
                    enSalto = false; // Termina el salto
                }
            }
        });

        timer.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
