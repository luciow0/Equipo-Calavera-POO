package Vista;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import FumameSiPuedes.src.Modelo.Plataforma;

public class Lienzo extends JPanel implements KeyListener {

    private Image imagenFondo;
    private Image imagenCajaCigarrillos;
    private Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();
    private Modelo.CigarrilloLazySlim cigarrilloLazySlim = new Modelo.CigarrilloLazySlim();
    private Modelo.CigarrilloMentaSplash cigarrilloMentaSplash = new Modelo.CigarrilloMentaSplash();

    private JLabel imagenPersonaje;
    private JLabel imagenPlataformaFinal;
    private boolean enSalto = false;
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;
    private ArrayList<Plataforma> plataformas = new ArrayList<>();
    private Timer timer;

    private int pasoIzquierda = 0;
    private int pasoDerecha = 0;
    private int desplazamientoVertical = 0;

    private Image imagenFinJuego;
    private boolean juegoTerminado = false;
    private Timer timerFinJuego;

    // Temporizador del juego
    private Timer timerJuego;
    private int tiempoRestante = 90; // Tiempo en segundos para llegar a la última plataforma
    private JLabel etiquetaTiempo;


    public Lienzo(String eleccion) {
        imagenFondo = new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/fondoEstirado.jpg").getImage();
        imagenCajaCigarrillos = new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/CAJA_CIGARRILLOS-removebg-preview.png").getImage();
        imagenFinJuego = Toolkit.getDefaultToolkit().getImage("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/NO FUMES POR FAVOR, SALVA TU VIDA.png");

        crearPlataformas();

        addKeyListener(this);
        setFocusable(true);
        setLayout(null);

        String smokiDerechaParado = "FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png";
        String mintyDerechaParado = "FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png";
        String lazyslimDerechaParado = "FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png";

        if (eleccion.equals(smokiDerechaParado)) {
            imagenPersonaje = cigarrilloSmooki.getImagenLabel(smokiDerechaParado);
        } else if (eleccion.equals(mintyDerechaParado)) {
            imagenPersonaje = cigarrilloMentaSplash.getImagenLabel(mintyDerechaParado);
        } else if (eleccion.equals(lazyslimDerechaParado)) {
            imagenPersonaje = cigarrilloLazySlim.getImagenLabel(lazyslimDerechaParado);
        }

        add(imagenPersonaje);
        crearPlataformas();
        posicionarPersonajeCentro();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarPlataformas();
                posicionarPersonajeCentro();
            }
        });

        // Timer para actualizar el movimiento y verificar colisiones
        timer = new Timer(20, e -> {
            actualizarMovimiento(eleccion);
            verificarColisiones();
        });
        timer.start();

        // Inicializar el temporizador del juego
        etiquetaTiempo = new JLabel("Tiempo restante: " + tiempoRestante);
        etiquetaTiempo.setFont(new Font("Arial", Font.BOLD, 20));
        etiquetaTiempo.setForeground(Color.RED);
        etiquetaTiempo.setBounds(10, 10, 200, 30);
        add(etiquetaTiempo);

        timerJuego = new Timer(1000, e -> {
            tiempoRestante--;
            etiquetaTiempo.setText("Tiempo restante: " + tiempoRestante);
            if (tiempoRestante <= 0) {
                finalizarJuegoPorTiempo();
            }
        });
        timerJuego.start();
    }
    private void finalizarJuegoPorTiempo() {
        timer.stop();
        timerJuego.stop();
        juegoTerminado = true;
        repaint();

        Timer cierreTimer = new Timer(5000, e -> System.exit(0));
        cierreTimer.setRepeats(false);
        cierreTimer.start();
    }

    private void crearPlataformas() {
        int anchoPanel = getWidth();
        int altoPanel = getHeight();

        plataformas.add(new Plataforma(0, altoPanel * 0.95, anchoPanel, altoPanel * 0.05));
        plataformas.add(new Plataforma(0.5, 0.9, 0.2, 0.05));
        plataformas.add(new Plataforma(0.5, 0.7, 0.2, 0.05));

        double plataformaX = 0.5;
        double plataformaY = 0.6;
        double plataformaAncho = 0.2;
        double plataformaAlto = 0.05;
        double plataformaSeparacionY = 0.2;
        int contador = 0;

        for (int i = 0; i < 29; i++) {
            plataformaX = Math.max(0.05, Math.min(0.75, plataformaX));
            plataformas.add(new Plataforma(plataformaX, plataformaY, plataformaAncho, plataformaAlto));
            plataformaY -= plataformaSeparacionY;
            if (contador <= 5 && contador > 0) {
                plataformaX += 0.25 * Math.random() - 0.3;
                contador--;
            } else if (contador <= 0 && contador > -5) {
                plataformaX += 0.25 * Math.random() - 0.01;
                contador++;
            }
        }

        plataformaX = Math.max(0.05, Math.min(0.75, plataformaX));
        Plataforma ultimaPlataforma = new Plataforma(plataformaX, plataformaY, plataformaAncho, plataformaAlto);
        plataformas.add(ultimaPlataforma);
        imagenPlataformaFinal = new JLabel(new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/cenicero-removebg-preview.png"));
        add(imagenPlataformaFinal);

        redimensionarPlataformas();
    }

    private void redimensionarPlataformas() {
        for (int i = 0; i < plataformas.size(); i++) {
            Plataforma plataforma = plataformas.get(i);
            int nuevoX = (int) (plataforma.getPosicionRelativaX() * getWidth());
            int nuevoY = (int) (plataforma.getPosicionRelativaY() * getHeight() + desplazamientoVertical);
            int nuevoAncho = (int) (plataforma.getAnchoRelativo() * getWidth());
            int nuevoAlto = (int) (plataforma.getAltoRelativo() * getHeight());

            // Asegúrate de que las dimensiones no sean negativas
            nuevoAncho = Math.max(nuevoAncho, 1);
            nuevoAlto = Math.max(nuevoAlto, 1);
            nuevoY = Math.max(nuevoY, 0); // Asegura que Y no sea negativa

            plataforma.setBounds(nuevoX, nuevoY, nuevoAncho, nuevoAlto);

            // Redimensionar la imagen en la última plataforma
            if (i == plataformas.size() - 1) {
                imagenPlataformaFinal.setBounds(nuevoX, nuevoY - nuevoAlto, nuevoAncho, nuevoAlto);
            }
        }

        revalidate(); // Actualiza el layout
        repaint(); // Redibuja el componente
    }

    private void verificarColisionConImagenFinal() {
        if (imagenPersonaje.getBounds().intersects(imagenPlataformaFinal.getBounds())) {
            timerJuego.stop();
            finalizarJuego();
        }
    }

    private void finalizarJuego() {
        timer.stop();
        terminarJuego();
        Timer cierreTimer = new Timer(5000, e -> System.exit(0));
        cierreTimer.setRepeats(false);
        cierreTimer.start();
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
        long milis = 20;

        // Movimiento hacia la izquierda
        if (moviendoIzquierda && x - step >= 0) {
            // Cambiar imagen a la versión "izquierda"
            if (eleccion.equals("FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png")) {
                // Alterna entre las imágenes de caminar a la izquierda
                if (pasoIzquierda % 2 == 0) {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/Smoki/smoki-izquierda.png"));

                } else {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/Smoki/smoki-izquierda-caminando.png"));

                }
            } else if (eleccion.equals("FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png")) {
                if (pasoIzquierda % 2 == 0) {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-izquieda.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                } else {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-izquieda-caminando.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                }

            } else if (eleccion.equals("FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png")) {
                if (pasoIzquierda % 2 == 0) {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-izquierda.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                } else {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-izquierda-caminando.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                }
            }
            pasoIzquierda++;
            imagenPersonaje.setLocation(x - step, y);
        }

        // Movimiento hacia la derecha
        if (moviendoDerecha && x + step + width <= panelWidth) {
            // Cambiar imagen a la versión "derecha"
            if (eleccion.equals("FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png")) {
                // Alterna entre las imágenes de caminar a la derecha
                    if (pasoDerecha % 2 == 0) {
                        imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png"));

                    } else {
                        imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha-caminando.png"));

                    }

            } else if (eleccion.equals("FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png")) {
                if (pasoDerecha % 2 == 0) {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                } else {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha-caminando.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                }
            } else if (eleccion.equals("FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png")) {
                if (pasoDerecha % 2 == 0) {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                } else {
                    imagenPersonaje.setIcon(new ImageIcon("FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha-caminando.png"));
                    try{
                        Thread.sleep(milis);
                    } catch (InterruptedException ex) {
                        System.out.println("el hilo ha sido interrumpido");
                    }
                }
            }
            pasoDerecha++;
            imagenPersonaje.setLocation(x + step, y);
        }
        verificarColisiones();
    }

    private void verificarColisiones() {
        boolean colisionDetectada = false;

        // Definir la subhitbox para los pies del personaje
        int alturaHitboxPies = 1; // Define un pequeño margen para la hitbox de los pies
        int posicionInferiorPies = imagenPersonaje.getY() + imagenPersonaje.getHeight() - alturaHitboxPies;

        for (Plataforma plataforma : plataformas) {
            if (plataforma.colisionaCon(imagenPersonaje)) {
                int posicionSuperiorPlataforma = plataforma.getY();

                // Si la subhitbox de los pies colisiona con la plataforma y el personaje está cayendo
                if (posicionInferiorPies >= posicionSuperiorPlataforma && imagenPersonaje.getY() + imagenPersonaje.getHeight() <= plataforma.getY() + plataforma.getHeight()) {
                    int y = (posicionSuperiorPlataforma - imagenPersonaje.getHeight()) + 1; // Ajuste para quedar sobre la plataforma

                    // Solo actualizar la posición si el personaje está cayendo o no está bien posicionado
                    if (imagenPersonaje.getY() != y) {
                        imagenPersonaje.setLocation(imagenPersonaje.getX(), y);
                        moverMapa();
                        enSalto = false; // Finaliza el salto para que caiga en la plataforma
                        colisionDetectada = true;
                        break;
                    }
                }
            }
        }

        // Si no hay colisión detectada, aplica gravedad
        if (!colisionDetectada) {
            aplicarGravedad();
        }

        // Verificar colisión con la imagen de la última plataforma
        verificarColisionConImagenFinal();
    }

    private void moverMapa() {
        desplazamientoVertical += 2; // Mantiene el control del desplazamiento acumulado
        repaint(); // Redibuja el fondo y las plataformas
    }

    private void aplicarGravedad() {
        if (!enSalto) {
            int y = imagenPersonaje.getY();
            int nuevoY = y + 10; // Cambia 5 a 1 para aplicar gravedad más suave

            boolean sobrePlataforma = false;
            for (Plataforma plataforma : plataformas) {
                if (plataforma.colisionaCon(imagenPersonaje)) {
                    int yPlataforma = plataforma.getY() - imagenPersonaje.getHeight();
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), yPlataforma + 1);
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

        if (juegoTerminado) {
            g.drawImage(imagenFinJuego, 0, 0, getWidth(), getHeight(), this);
        } else {
            int altoVentana = getHeight();
            int yRecorte = Math.max(0, 1280 - altoVentana - desplazamientoVertical);

            g.drawImage(imagenFondo, 0, 0, getWidth(), altoVentana,
                    0, yRecorte, 426, yRecorte + altoVentana, this);

            int posicionY = 670 + desplazamientoVertical;
            if (posicionY >= 0) {
                g.drawImage(imagenCajaCigarrillos, 10, posicionY, this);
            }

            redimensionarPlataformas();
            g.setColor(Color.BLACK);
            for (Plataforma plataforma : plataformas) {
                g.fillRect(plataforma.getX(), plataforma.getY(), plataforma.getWidth(), plataforma.getHeight());
            }
        }
    }

    // Método para finalizar el juego
    public void terminarJuego() {
        juegoTerminado = true;

        // Configurar el timer para que se ejecute el fin del juego en 5 segundos
        timerFinJuego = new Timer(100, e -> repaint()); // Cada 100 ms se repinta la pantalla
        timerFinJuego.start();
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

    private void iniciarSalto() {
        enSalto = true;
        int alturaMaxima = 200;
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
