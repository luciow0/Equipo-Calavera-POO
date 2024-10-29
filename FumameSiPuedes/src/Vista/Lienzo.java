package FumameSiPuedes.src.Vista;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Lienzo extends JPanel implements KeyListener {

    private Image imagenFondo;
    private Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();
    private JLabel imagenPersonaje;
    private boolean enSalto = false;

    public Lienzo() {
        imagenFondo = new ImageIcon("FumameSiPuedes/src/Vista/imgs/fondo.jpg").getImage();

        addKeyListener(this);
        setFocusable(true);
        setLayout(null);

        imagenPersonaje = cigarrilloSmooki.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png");
        add(imagenPersonaje);

        posicionarPersonajeCentro();

        // Añadir un listener para el cambio de tamaño del panel
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                posicionarPersonajeCentro();
            }
        });
    }

    private void posicionarPersonajeCentro() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int personajeWidth = imagenPersonaje.getIcon().getIconWidth();
        int personajeHeight = imagenPersonaje.getIcon().getIconHeight();

        // Posicionar el personaje en el centro
        int posicionX = (panelWidth - personajeWidth) / 2;
        int posicionY = panelHeight - personajeHeight - 100;  // Ajuste de altura para no quedar al borde inferior
        imagenPersonaje.setBounds(posicionX, posicionY, personajeWidth, personajeHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int step = 25;
        int x = imagenPersonaje.getX();
        int y = imagenPersonaje.getY();
        int width = imagenPersonaje.getWidth();
        int height = imagenPersonaje.getHeight();
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (e.getExtendedKeyCode() == KeyEvent.VK_UP && !enSalto) {
            iniciarSalto();
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE && !enSalto) {
            iniciarSalto();
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT && x - step >= 0) {
            imagenPersonaje.setLocation(x - step, y);
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT && x + step + width <= panelWidth) {
            imagenPersonaje.setLocation(x + step, y);
        }
        //else if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN && y + step + height <= (panelHeight - 20)) {
          //  imagenPersonaje.setLocation(x, y + step);
    }

    private void iniciarSalto() {
        enSalto = true;
        int alturaMaxima = 100;
        int velocidad = 10;
        int delay = 20;

        Timer timer = new Timer(delay, null);
        final int[] alturaActual = {0};
        final boolean[] subiendo = {true};

        timer.addActionListener(e -> {
            int y = imagenPersonaje.getY();

            if (subiendo[0]) {
                if (alturaActual[0] < alturaMaxima) {
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), y - velocidad);
                    alturaActual[0] += velocidad;
                } else {
                    subiendo[0] = false;
                }
            } else {
                if (alturaActual[0] > 0) {
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), y + velocidad);
                    alturaActual[0] -= velocidad;
                } else {
                    timer.stop();
                    enSalto = false;
                }
            }
        });

        timer.start();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void addNotify() {
        super.addNotify();
        posicionarPersonajeCentro(); // Centra el personaje cuando el panel se añade a la ventana
    }
}
