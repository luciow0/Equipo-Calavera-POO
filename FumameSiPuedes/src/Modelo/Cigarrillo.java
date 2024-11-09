package FumameSiPuedes.src.Modelo;

import javax.swing.*;

public abstract class Cigarrillo {

    public Cigarrillo(){}

    public JLabel getImagenLabel(String path) {
        // Cargar el icono desde la ruta especificada
        ImageIcon icono = new ImageIcon(path);

        // Crear un JLabel con el icono
        return new JLabel(icono); // Devolver el JLabel directamente
    }
}
