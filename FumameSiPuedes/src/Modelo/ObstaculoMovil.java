package FumameSiPuedes.src.Modelo;


public class ObstaculoMovil extends Obstaculo {
    private int velocidadX;

    public ObstaculoMovil(int x, int y, int ancho, int alto, String rutaImagen, int velocidadX) {
        super(x, y, ancho, alto, rutaImagen);
        this.velocidadX = velocidadX;
    }

    @Override
    public void actualizar() {
        x += velocidadX;
        // Si el obstáculo sale de los límites de la pantalla, se mueve en la otra dirección
        if (x < 0 || x + ancho > 800) {
            velocidadX = -velocidadX;
        }
        setBounds(x, y, ancho, alto);
    }
}
