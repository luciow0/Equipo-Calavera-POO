package FumameSiPuedes.src.Modelo;

public class Plataforma extends Obstaculo {
    private double posicionRelativaX;
    private double posicionRelativaY;
    private double anchoRelativo;
    private double altoRelativo;

    public Plataforma(double posicionRelativaX, double posicionRelativaY, double anchoRelativo, double altoRelativo, String rutaImagen) {
        super(0, 0, 0, 0, rutaImagen);  // Valores iniciales (0,0) se recalculan en Lienzo
        this.posicionRelativaX = posicionRelativaX;
        this.posicionRelativaY = posicionRelativaY;
        this.anchoRelativo = anchoRelativo;
        this.altoRelativo = altoRelativo;
    }




    // Getters para las posiciones y tamaños relativos
    public double getPosicionRelativaX() {
        return posicionRelativaX;
    }

    public double getPosicionRelativaY() {
        return posicionRelativaY;
    }

    public double getAnchoRelativo() {
        return anchoRelativo;
    }

    public double getAltoRelativo() {
        return altoRelativo;
    }

    @Override
    public void actualizar() {
        // Implementar lógica de movimiento si fuera necesario
    }
}
