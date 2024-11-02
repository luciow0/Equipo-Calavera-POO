package FumameSiPuedes.src.Modelo;

import javax.swing.*;
import java.awt.*;

public abstract class Obstaculo extends JLabel {
    protected int x;
    protected int y;
    public int ancho;
    public int alto;

    public Obstaculo(int x, int y, int ancho, int alto, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        setBounds(x, y, ancho, alto);
        setIcon(new ImageIcon(rutaImagen));
    }

    public abstract void actualizar();

    public boolean colisionaCon(JLabel personaje) {
        Rectangle plataformaRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        Rectangle personajeRect = new Rectangle(personaje.getX(), personaje.getY(), personaje.getWidth(), personaje.getHeight());
        return plataformaRect.intersects(personajeRect);
    }

}
