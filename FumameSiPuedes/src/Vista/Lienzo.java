package Vista;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import FumameSiPuedes.src.Modelo.Plataforma;

public class Lienzo extends JPanel implements KeyListener {

    // declaracion de las imagenes fondo y caja cigarrillos para ser aniadidos al lienzo e
    // instanciacion de los personajes (cigarrillos)
    private Image imagenFondo;
    private Image imagenCajaCigarrillos;
    private Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();
    private Modelo.CigarrilloLazySlim cigarrilloLazySlim = new Modelo.CigarrilloLazySlim();
    private Modelo.CigarrilloMentaSplash cigarrilloMentaSplash = new Modelo.CigarrilloMentaSplash();

    // declaracion de Jlabel para los componentes visuales del juego, personaje y palyaforma, varaibles booleanas de movimiento,
    // arreglo donde se almacenaran las plataformas creadas
    // y el timer para controlar el movimiento del personaje, este timer actua como 'bucle principal' del juego
    // y detecta los movimientos en tiempo real
    private JLabel imagenPersonaje;
    private JLabel imagenPlataformaFinal;
    private boolean enSalto = false;
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;
    private ArrayList<Plataforma> plataformas = new ArrayList<>();
    private Timer timer;

    // declaracion de variables de movimiento del personaje
    private int pasoIzquierda = 0;
    private int pasoDerecha = 0;
    private int desplazamientoVertical = 0;

    // declaracion de la imagen que se le presenta al usuario al finalizar el juego, de la forma que sea
    // declaracion del timer que le asigna un tiempo maximo a la imagen que se muestra al finalizar el juego,
    // transcurrido ese tiempo el juego se cerrara automaticamente
    private Image imagenFinJuego;
    private boolean juegoTerminado = false;
    private Timer timerFinJuego;

    // Temporizador del juego, este temporizador es el encargado de definirle al usuario un tiempo limite para finalizar
    private Timer timerJuego;
    private int tiempoRestante = 90; // Tiempo en segundos para llegar a la última plataforma
    private JLabel etiquetaTiempo;


    // El constructor de lienzo inicializa y configura todos los elementos necesarios para que el juego pueda comenzar,
    // este metodo no implementa ningun tipo de logica acerca del funcionamiento del juego, simplemente inicializa la estructura visual
    public Lienzo(String eleccion) {
        // asignacion de las imagenes e instanciacion a las variables previamente definidas para las imagenes
        imagenFondo = new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/fondoEstirado.jpg").getImage();
        imagenCajaCigarrillos = new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/CAJA_CIGARRILLOS-removebg-preview.png").getImage();
        imagenFinJuego = Toolkit.getDefaultToolkit().getImage("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/NO FUMES POR FAVOR, SALVA TU VIDA.png");

        // se invoca al metodo crear plataformas el cual despliega las plataformas por el mapa
        crearPlataformas();

        // aniadir un escuchador de eventos del teclado al componente actual (this)
        // this indica que la propia clase actua como escuchador, por eso esta clase implementa keyListener
        addKeyListener(this);

        // permite que el componente actual reciba el foco, sin esto por mas que tengamos el escuchador de eventos acitvados
        // para que un componente pueda detectar eventos de teclado debe ser "enfocable"
        setFocusable(true);

        // desactiva el administrador de disenio 'layoutManager', el cual utiliza swing para posicionar y dimnesionar
        // automaticamente los componentes dentro de un contenedor, estableciendolo en null, se indica que el programador
        // realizara esto de forma manual
        setLayout(null);

        // asignacion de rutas a las imagenes de los personajes
        String smokiDerechaParado = "FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png";
        String mintyDerechaParado = "FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png";
        String lazyslimDerechaParado = "FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png";

        // verificacion de que personaje eligio el jugador mediante la variable "eleccion" la cual contiene el path de la imagen del personaje que escogio
        // al determinar el pj elegido se trae la imagen del personaje indicado a la variable imagenPersonaje utilizando las rutas definidas anteriormente
        if (eleccion.equals(smokiDerechaParado)) {
            imagenPersonaje = cigarrilloSmooki.getImagenLabel(smokiDerechaParado);
        } else if (eleccion.equals(mintyDerechaParado)) {
            imagenPersonaje = cigarrilloMentaSplash.getImagenLabel(mintyDerechaParado);
        } else if (eleccion.equals(lazyslimDerechaParado)) {
            imagenPersonaje = cigarrilloLazySlim.getImagenLabel(lazyslimDerechaParado);
        }

        // se aniade la imagen del pj al panel
        add(imagenPersonaje);

        // se invoca al metodo posicionarPersonajeCentro, si bien este metodo no posiciona al personaje en el centro
        // ya que el nombre quedo desactualizado, sirve para ubicar al personaje en una posicion especifica al inicio del juego
        // para dar comienzo al mismo
        posicionarPersonajeCentro();

        // se aniade un escuchador de componentes, al componente actual (this)
        // es un objeto que se utiliza para escuchar cambios en un componente, como,
        // cambio de tamanio, posicion, mostrar u ocultar componentes, se utiliza en swing para reaccionar dinamicamente a cambios en componentes graficos
        // COMPONENT-ADAPTER implementacion parcial de la intefaz componentListener
        // COMPONEN-RESIZED se ejecuta automaticamente cuando el componente cambia de tamanio
        // se invoca al metodo redimensionarPlataformas para ajustarlas al tamanio relativo de la ventana
        // se invoca al metodo posicionarPersonajeCentro, reposiciona el personaje basandose en el cambio en la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                redimensionarPlataformas();
                posicionarPersonajeCentro();
            }
        });

        // Timer para actualizar el movimiento y verificar colisiones
        // este es el timer mencionado anteriormente para manejar el juego y los eventos del usuario
        // el cual invoca a los metdos actualizarMovimiento y verificarColisiones
        timer = new Timer(20, e -> {
            actualizarMovimiento(eleccion);
            verificarColisiones();
        });
        timer.start();

        // Inicializar el temporizador del juego
        // etiqueta visible que mostara el tiempo restante que le queda al jugador para finalizar el juego
        // se definen sus atributos
        etiquetaTiempo = new JLabel("Tiempo restante: " + tiempoRestante);
        etiquetaTiempo.setFont(new Font("Arial", Font.BOLD, 20));
        etiquetaTiempo.setForeground(Color.RED);
        etiquetaTiempo.setBounds(10, 10, 200, 30);
        add(etiquetaTiempo);

        // definicion del timer y su logica para finalizar el juego en caso de que el jugador no haya llegado a destino a tiempo
        // maneja el tiempo maximo de la partida
        timerJuego = new Timer(1000, e -> {
            tiempoRestante--;
            etiquetaTiempo.setText("Tiempo restante: " + tiempoRestante);
            if (tiempoRestante <= 0) {
                finalizarJuegoPorTiempo();
            }
        });
        timerJuego.start();
    }

    // declaracion del metodo que se ejecutara en caso de que el jugador no finalice el juego a tiempo
    // metodo privado, no retorna nada detiene los timers que manejan la logica del juego y el tiempo maximo
    // e inicializa un ultimo timer que dara cierre al juego transcurrido el tiempo establecido
    private void finalizarJuegoPorTiempo() {
        timer.stop();
        timerJuego.stop();
        juegoTerminado = true;
        repaint();

        Timer cierreTimer = new Timer(5000, e -> System.exit(0));
        cierreTimer.setRepeats(false);
        cierreTimer.start();
    }

    // declaracion del metodo que inicaliza las plataformas en el lienzo
    // metodo privado, no retorna nada
    private void crearPlataformas() {
        // definicion del ancho de la ventana para calcular de forma relativa el tamanio de las plataformas
        int anchoPanel = getWidth();
        int altoPanel = getHeight();

        // plataformas iniciales para que el jugador pueda empezar a subir
        plataformas.add(new Plataforma(0, altoPanel * 0.95, anchoPanel, altoPanel * 0.05));
        plataformas.add(new Plataforma(0.5, 0.9, 0.2, 0.05));
        plataformas.add(new Plataforma(0.5, 0.7, 0.2, 0.05));

        // declaracion de valores de tipo double para la creacion de las plataformas bajo bucle ya que el metodo utilizado
        // precisa estos valores
        // declaracion del contador, la funcion de esta variable es generar una alternancia entre la cantidad de plataformas que
        // van hacia la izquierda y las que van hacia la derecha
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

        // creacion de la ultima plataforma donde se ubicara la imagen del cenicero al que el jugador debera saltar para finalizarlo
        plataformaX = Math.max(0.05, Math.min(0.75, plataformaX));
        Plataforma ultimaPlataforma = new Plataforma(plataformaX, plataformaY, plataformaAncho, plataformaAlto);
        plataformas.add(ultimaPlataforma);
        imagenPlataformaFinal = new JLabel(new ImageIcon("FumameSiPuedes/src/Vista/imgs/ImagenesUtilitarias/cenicero-removebg-preview.png"));
        add(imagenPlataformaFinal);

        // invocacion del metodo de redimensionarPlataformas para justarlas al ancho de la ventana del juego
        redimensionarPlataformas();
    }

    // declaracion del metodo que redimensiona las paltaformas segun el ancho de la ventana
    // el metodo recorre plataforma por plataforma, averiguando su tamanio con el metodo .size de la interfaz list
    // re asignando los valores a las paltaformas con los valores actualizados
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

            // se establecen los nuevos valores
            plataforma.setBounds(nuevoX, nuevoY, nuevoAncho, nuevoAlto);

            // Redimensionar la imagen en la última plataforma
            if (i == plataformas.size() - 1) {
                imagenPlataformaFinal.setBounds(nuevoX, nuevoY - nuevoAlto, nuevoAncho, nuevoAlto);
            }
        }

        revalidate(); // Actualiza el layout
        repaint(); // Redibuja el componente
    }

    // declaracion del metodo que verifica que el jugador haya colisionado con la imagen de la ultima plataforma del juego
    // en caso de cumplirse la condicion se invoca al metodo finalizar juego
    private void verificarColisionConImagenFinal() {
        if (imagenPersonaje.getBounds().intersects(imagenPlataformaFinal.getBounds())) {
            timerJuego.stop();
            finalizarJuego();
        }
    }

    // declaracion del metodo que, detiene el timer que controla el movimiento del jugador,
    // invoca al metodo terminarJuego y
    // crea el timer final que cerrara la ventana transcurrido un tiempo
    private void finalizarJuego() {
        timer.stop();
        terminarJuego();
        Timer cierreTimer = new Timer(5000, e -> System.exit(0));
        cierreTimer.setRepeats(false);
        cierreTimer.start();
    }

    // Metodo para finalizar el juego
    // actualiza la interfaz del juego para mostrar la imagen del fin
    public void terminarJuego() {
        juegoTerminado = true;

        // Configurar el timer para que se ejecute el fin del juego en 5 segundos
        timerFinJuego = new Timer(100, e -> repaint()); // Cada 100 ms se repinta la pantalla
        timerFinJuego.start();
    }

    // declaracion del metodo para posicionar al personaje, si bien el nombre dice centro
    // el metodo es utilizado para ubicar al personaje de forma conveniente hacia el lado izquierdo de la pantalla
    // al inicio del juego
    private void posicionarPersonajeCentro() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int personajeWidth = imagenPersonaje.getIcon().getIconWidth();
        int personajeHeight = imagenPersonaje.getIcon().getIconHeight();

        int posicionX = (panelWidth - personajeWidth) / 15;
        int posicionY = panelHeight - personajeHeight - 100;  // Ajuste de altura para no quedar al borde inferior
        imagenPersonaje.setBounds(posicionX, posicionY, personajeWidth, personajeHeight);
    }

    // declaracion del metodo que produce el movimiento del personaje en la pantalla
    // el metodo extrae las posiciones del personaje y la pantalla para sumar el valor del paso
    // en la direccion que el jugador haya caminado (izquierda o derecha)
    private void actualizarMovimiento(String eleccion) {
        int step = 5; // longitud del paso del personaje
        int x = imagenPersonaje.getX();
        int y = imagenPersonaje.getY();
        int width = imagenPersonaje.getWidth();
        int panelWidth = getWidth();
        long milis = 20; // variable utilizada para producir un retardo en la caminata del personaje y que no de los pasos de forma instantanea

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

    // declaracion del metodo verificarColisiones
    // el metodo recorre el arreglo de las paltaformas utilizando un for each
    // para verificar si el personaje se encuentra colisionando con alguna de las paltaformas
    // utilizando el metodo colisionaCon definido en la clase Obstaculo el cual verifica si la imagen del personaje se encuentra
    // en las mismas coordenadas que la plataforma, retornando true o false respectivamente
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

    // declaracion del metodo moverMapa el cual redibuja la ventana moviento la ventana hacia arriba para ir subiendo
    private void moverMapa() {
        desplazamientoVertical += 2; // Mantiene el control del desplazamiento acumulado
        repaint(); // Redibuja el fondo y las plataformas
    }

    // declaracion del metodo que aplica la gravedad al personaje al momento de detectar que ya no esta saltando
    private void aplicarGravedad() {
        if (!enSalto) { // la gravedad aplica solo si el personaje no esta saltando, esto evita interferir con el salto
            int y = imagenPersonaje.getY(); // se obtiene la posicion vertical actual del personaje
            int nuevoY = y + 10; // se le aniade un nuevo valor que 'empuja' la imagen del personaje hacia abajo, dependiendo del valor el empuje sera mas o menos fuerte

            boolean sobrePlataforma = false;
            // se procede a verificar si el personaje esta colisionando con alguna de las paltaformas
            for (Plataforma plataforma : plataformas) {
                if (plataforma.colisionaCon(imagenPersonaje)) {
                    // en caso de detectar una colision con la plataforma ajusta la posicion del pj
                    // para que quede justo por encima + 1 pixel para evitar problemas de colision
                    int yPlataforma = plataforma.getY() - imagenPersonaje.getHeight();
                    imagenPersonaje.setLocation(imagenPersonaje.getX(), yPlataforma + 1);
                    sobrePlataforma = true;
                    break;
                }
            }

            // si el personaje no esta sobre una plataforma y no alcanzo el limite inferior del area del juego
            // la gravedad se aplica moviendo al personaje hacia abajo
            if (!sobrePlataforma && y + imagenPersonaje.getHeight() < getHeight()) {
                imagenPersonaje.setLocation(imagenPersonaje.getX(), nuevoY);
            } else if (y + imagenPersonaje.getHeight() >= getHeight()) {
                int yLimite = getHeight() - imagenPersonaje.getHeight();
                imagenPersonaje.setLocation(imagenPersonaje.getX(), yLimite);
            }
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

    // sobrescritura del metodo paintComponent perteneciente a la clase JComponent
    // este metodo es el encargado de dibujar las imagenes del juego, la del fondo, caja de cigarrillos e imagen final
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

    @Override
    public void keyTyped(KeyEvent e) {} // no usado

    // metodos para verificar si las teclas fueron presionadas
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

    @Override
    public void addNotify() {
        super.addNotify();
        posicionarPersonajeCentro(); // Centra el personaje cuando el panel se añade a la ventana
    }
}
