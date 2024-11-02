package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import FumameSiPuedes.src.Modelo.Plataforma;

public class Lienzo extends JPanel implements KeyListener {

    private Image imagenFondo;
    private Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();
    private Modelo.CigarrilloLazySlim cigarrilloLazySlim = new Modelo.CigarrilloLazySlim();
    private Modelo.CigarrilloMentaSplash cigarrilloMentaSplash = new Modelo.CigarrilloMentaSplash();

    private JLabel imagenPersonaje;
    private boolean enSalto = false;
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;
    private ArrayList<Plataforma> plataformas = new ArrayList<>();
    private Timer timer;

    public Lienzo(String eleccion) {
        imagenFondo = new ImageIcon("FumameSiPuedes/src/Vista/imgs/fondo.jpg").getImage();

        addKeyListener(this);
        setFocusable(true);
        setLayout(null);

        // setear imagen de personaje recibiendo por argumento en el constructor el path
        if (eleccion == "FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png"){
            imagenPersonaje = cigarrilloSmooki.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png");
        }
        if (eleccion == "FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png"){
            imagenPersonaje = cigarrilloMentaSplash.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png");
        }
        if (eleccion == "FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png"){
            imagenPersonaje = cigarrilloMentaSplash.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png");
        }

        add(imagenPersonaje); // aniade la imagen al jpanel lienzo
        crearPlataformas(); // Crear plataformas pero no redimensionarlas aún, invoca metodo
        posicionarPersonajeCentro(); // invoca metodo posicionar personaje

        // añadir listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarPlataformas();  // Redimensiona las plataformas primero
                posicionarPersonajeCentro(); // Posiciona el personaje después de redimensionar
            }
        });
        // añadir listener

        // timer
        Timer timer = new Timer(20, e -> {
            actualizarMovimiento(eleccion);
            verificarColisiones();
        });
        timer.start();
        // timer
    }

    private void crearPlataformas() {
        // Plataforma invisible en el piso (por ejemplo, altura del 5% de la pantalla)
        plataformas.add(new Plataforma(0, 0.95, 1.0, 0.15, null));  // `null` como imagen para que sea invisible
        // Agregar las demás plataformas visibles
        plataformas.add(new Plataforma(0.2, 0.6, 0.2, 0.05, "ruta/a/la/imagen/de/plataforma.png"));
        plataformas.add(new Plataforma(0.5, 0.8, 0.2, 0.05, "ruta/a/la/imagen/de/plataforma.png"));

        for (Plataforma plataforma : plataformas) {
            add(plataforma);
        }
        redimensionarPlataformas();
    }

    private void redimensionarPlataformas() {
        for (Plataforma plataforma : plataformas) {
            int nuevoX = (int) (plataforma.getPosicionRelativaX() * getWidth());
            int nuevoY = (int) (plataforma.getPosicionRelativaY() * getHeight());
            int nuevoAncho = (int) (plataforma.getAnchoRelativo() * getWidth());
            int nuevoAlto = (int) (plataforma.getAltoRelativo() * getHeight());

            plataforma.setBounds(nuevoX, nuevoY, nuevoAncho, nuevoAlto);

            // Verificar posición y dimensiones
            System.out.println("Plataforma - PosX: " + nuevoX + ", PosY: " + nuevoY +
                    ", Ancho: " + nuevoAncho + ", Alto: " + nuevoAlto);
        }
    }

    private void posicionarPersonajeCentro() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int personajeWidth = imagenPersonaje.getIcon().getIconWidth();
        int personajeHeight = imagenPersonaje.getIcon().getIconHeight();

        int posicionX = (panelWidth - personajeWidth) / 15;
        int posicionY = panelHeight - personajeHeight - 100;  // Ajuste de altura para no quedar al borde inferior
        imagenPersonaje.setBounds(posicionX, posicionY, personajeWidth, personajeHeight);
    }

    private void actualizarMovimiento(String eleccion) {
        int step = 5;
        int x = imagenPersonaje.getX();
        int y = imagenPersonaje.getY();
        int width = imagenPersonaje.getWidth();
        int panelWidth = getWidth();

        if (moviendoIzquierda && x - step >= 0) {
            if (eleccion == "FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png"){
                imagenPersonaje = cigarrilloSmooki.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-izquierda.png");
            }
            if (eleccion == "FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png"){
                imagenPersonaje = cigarrilloMentaSplash.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-zquierda.png");
            }
            if (eleccion == "FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png"){
                imagenPersonaje = cigarrilloMentaSplash.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-izquierdapng.png");
            }
            imagenPersonaje.setLocation(x - step, y);

        }
        if (moviendoDerecha && x + step + width <= panelWidth) {
            if (eleccion == "FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png"){
                imagenPersonaje = cigarrilloSmooki.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png");
            }
            if (eleccion == "FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png"){
                imagenPersonaje = cigarrilloMentaSplash.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png");
            }
            if (eleccion == "FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png"){
                imagenPersonaje = cigarrilloMentaSplash.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png");
            }
            imagenPersonaje.setLocation(x + step, y);
        }
    }

    private void verificarColisiones() {
        boolean colisionDetectada = false;

        for (Plataforma plataforma : plataformas) {
            if (plataforma.colisionaCon(imagenPersonaje)) {
                int posicionInferiorPersonaje = imagenPersonaje.getY() + imagenPersonaje.getHeight();
                int posicionSuperiorPlataforma = plataforma.getY();

                if (posicionInferiorPersonaje <= posicionSuperiorPlataforma + 5) {
                    int y = plataforma.getY() - imagenPersonaje.getHeight();
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), y);
                    enSalto = false;
                    colisionDetectada = true;
                    break;
                }
            }
        }

        if (!colisionDetectada && !enSalto) {
            aplicarGravedad();
        }
    }

    private void aplicarGravedad() {
        if (!enSalto) {
            int y = imagenPersonaje.getY();
            int nuevoY = y + 5;

            boolean sobrePlataforma = false;
            for (Plataforma plataforma : plataformas) {
                if (plataforma.colisionaCon(imagenPersonaje)) {
                    int yPlataforma = plataforma.getY() - imagenPersonaje.getHeight();
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), yPlataforma);
                    sobrePlataforma = true;
                    break;
                }
            }

            if (!sobrePlataforma && y + imagenPersonaje.getHeight() < getHeight()) {
                imagenPersonaje.setLocation(imagenPersonaje.getX(), nuevoY);
            } else if (y + imagenPersonaje.getHeight() >= getHeight()) {
                int yLimite = getHeight() - imagenPersonaje.getHeight();
                imagenPersonaje.setLocation(imagenPersonaje.getX(), yLimite);
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

        // Redimensionar plataformas antes de dibujar
        redimensionarPlataformas();

        // Dibujar plataformas
        g.setColor(Color.BLACK);
        for (Plataforma plataforma : plataformas) {
            g.fillRect(plataforma.getX(), plataforma.getY(), plataforma.getWidth(), plataforma.getHeight());
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP && !enSalto) {
            iniciarSalto();
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE && !enSalto) {
            iniciarSalto();
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
            moviendoIzquierda = true;
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
            moviendoDerecha = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
            moviendoIzquierda = false;
        } else if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
            moviendoDerecha = false;
        }
    }


    private void caer() {
        int y = imagenPersonaje.getY();
        int nuevoY = y + 5; // Velocidad de caída
        boolean enObstaculo = false;
    }

    private void iniciarSalto() {
        enSalto = true;
        int alturaMaxima = 140;
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
            verificarColisiones(); // Verificar colisiones durante el salto
        });

        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        posicionarPersonajeCentro(); // Centra el personaje cuando el panel se añade a la ventana
    }
}
