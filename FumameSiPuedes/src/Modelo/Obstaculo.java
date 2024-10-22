package FumameSiPuedes.src.Modelo;

import javax.swing.*;
import java.awt.*;

public abstract class Obstaculo extends JLabel {
    protected int x, y, ancho, alto;

    public Obstaculo(int x, int y, int ancho, int alto, String rutaImagen) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        setBounds(x, y, ancho, alto);
        setIcon(new ImageIcon(rutaImagen));
    }

    // Método que define qué hace el obstáculo en cada actualización (puede moverse, o no)
    public abstract void actualizar();

    // Método para verificar colisiones con el personaje
    public boolean colisionaCon(JLabel character) {
        Rectangle obstaculoRect = new Rectangle(x, y, ancho, alto);
        Rectangle personajeRect = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
        return obstaculoRect.intersects(personajeRect);
    }
}
