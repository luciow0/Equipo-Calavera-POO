package FumameSiPuedes.src.Modelo;

import javax.swing.*;
import java.awt.*;

public class Plataforma extends Obstaculo {
    private double posicionRelativaX;
    private double posicionRelativaY;
    private double anchoRelativo;
    private double altoRelativo;

    public Plataforma(double posicionRelativaX, double posicionRelativaY, double anchoRelativo, double altoRelativo) {
        super(0, 0, 0, 0, null); // Sin ruta de imagen
        this.posicionRelativaX = posicionRelativaX;
        this.posicionRelativaY = posicionRelativaY;
        this.anchoRelativo = anchoRelativo;
        this.altoRelativo = altoRelativo;

        setBackground(Color.BLUE); // Color para la plataforma
        setOpaque(true); // Necesario para que se muestre el color
    }

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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, getWidth(), getHeight()); // Dibuja un rectángulo que llena la plataforma
    }
}
