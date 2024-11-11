package Vista;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import FumameSiPuedes.src.Modelo.Plataforma;

public class Lienzo extends JPanel implements KeyListener {

    private Image imagenFondo;
    private Image imagenCajaCigarrillos;
    private Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();
    private Modelo.CigarrilloLazySlim cigarrilloLazySlim = new Modelo.CigarrilloLazySlim();
    private Modelo.CigarrilloMentaSplash cigarrilloMentaSplash = new Modelo.CigarrilloMentaSplash();

    private JLabel imagenPersonaje;
    private JLabel imagenPlataformaFinal; // Nueva imagen para la última plataforma
    private boolean enSalto = false;
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;
    private ArrayList<Plataforma> plataformas = new ArrayList<>();
    private Timer timer;

    //index para los pasos
    private int pasoIzquierda = 0;
    private int pasoDerecha = 0;

    private int desplazamientoVertical = 0;


    private Image imagenFinJuego;
    private boolean juegoTerminado = false;
    private Timer timerFinJuego;  // Timer para la imagen de fin de juego
    private int tiempoMostrado = 0;  // Variable para llevar el tiempo de 5 segundos




    public Lienzo(String eleccion) {
        imagenFondo = new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/fondoEstirado.jpg").getImage();
        imagenCajaCigarrillos = new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/CAJA_CIGARRILLOS-removebg-preview.png").getImage();

       imagenFinJuego = Toolkit.getDefaultToolkit().getImage("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/NO FUMES POR FAVOR, SALVA TU VIDA.png");

        // Cargar plataformas
        crearPlataformas();

        addKeyListener(this);
        setFocusable(true);
        setLayout(null);

        // Inicializar las imágenes de los personajes
        String smokiDerechaParado = "FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png";
        String mintyDerechaParado = "FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png";
        String lazyslimDerechaParado = "FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png";

        // Setear imagen de personaje recibiendo por argumento en el constructor el path
        if (eleccion.equals(smokiDerechaParado)) {
            imagenPersonaje = cigarrilloSmooki.getImagenLabel(smokiDerechaParado);
        } else if (eleccion.equals(mintyDerechaParado)) {
            imagenPersonaje = cigarrilloMentaSplash.getImagenLabel(mintyDerechaParado);
        } else if (eleccion.equals(lazyslimDerechaParado)) {
            imagenPersonaje = cigarrilloLazySlim.getImagenLabel(lazyslimDerechaParado);
        }

        add(imagenPersonaje);  // Añadir la imagen del personaje al JPanel
        crearPlataformas();  // Crear plataformas pero no redimensionarlas aún
        posicionarPersonajeCentro();  // Posicionar personaje en el centro

        // Añadir listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarPlataformas();  // Redimensionar plataformas primero
                posicionarPersonajeCentro(); // Posicionar el personaje después de redimensionar
            }
        });


        // Timer
        timer = new Timer(20, e -> {
            actualizarMovimiento(eleccion);
            verificarColisiones();
        });
        timer.start();
    }

    // Método para reproducir el audio en loop
    public void reproducirAudioEnLoop(String rutaArchivo) {
        try {
            // Cargar el archivo de audio
            File archivoAudio = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoAudio);

            // Obtener el clip de audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Reproducir el audio en loop infinito
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loops indefinitely
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Crear plataformas en el juego
    private void crearPlataformas() {
        int anchoPanel = getWidth();
        int altoPanel = getHeight();

        // Plataforma invisible en el piso
        plataformas.add(new Plataforma(0, altoPanel * 0.95, anchoPanel, altoPanel * 0.05));

        // Plataforma inicial
        plataformas.add(new Plataforma(0.5, 0.9, 0.2, 0.05));
        plataformas.add(new Plataforma(0.5, 0.7, 0.2, 0.05));

        // Agregar plataformas adicionales
        double plataformaX = 0.5;
        double plataformaY = 0.6; // Comienza un poco más arriba
        double plataformaAncho = 0.2;
        double plataformaAlto = 0.05;
        double plataformaSeparacionY = 0.2; // Separación vertical entre plataformas
        int contador = 0;

        for (int i = 0; i < 29; i++) {
            // Asegurarse de que la plataforma esté dentro de los límites del panel
            plataformaX = Math.max(0.05, Math.min(0.75, plataformaX));
            plataformas.add(new Plataforma(plataformaX, plataformaY, plataformaAncho, plataformaAlto));
            plataformaY -= plataformaSeparacionY; // Sube la plataforma
            if (contador <= 5 && contador > 0) {
                plataformaX += 0.25 * Math.random() - 0.3; // Desplaza ligeramente la plataforma
                contador--;
            } else if (contador <= 0 && contador > -5) {
                plataformaX += 0.25 * Math.random() - 0.01; // Desplaza ligeramente la plataforma
                contador++;
            }
        }

        // Última plataforma con imagen
        plataformaX = Math.max(0.05, Math.min(0.75, plataformaX));
        Plataforma ultimaPlataforma = new Plataforma(plataformaX, plataformaY, plataformaAncho, plataformaAlto);
        plataformas.add(ultimaPlataforma);
        imagenPlataformaFinal = new JLabel(new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/cenicero-removebg-preview.png"));
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
            finalizarJuego();
        }
    }

    private void finalizarJuego() {
        timer.stop();
        terminarJuego(); // Mostrar la imagen de fin de juego

        // Configurar el timer para que se ejecute el fin del juego en 5 segundos
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

    // Aquí puedes añadir tu lógica de colisiones o de perder, por ejemplo:
    private void verificarPerdida() {
        // Si el personaje cae fuera de la pantalla
        if (imagenPersonaje.getBounds().getMaxY() > getHeight()) {
            terminarJuego(); // Llama al método para finalizar el juego
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
