package Modelo;

import javax.swing.*;
import java.awt.*;
import FumameSiPuedes.src.Modelo.Cigarrillo;
public class CigarrilloSmooki extends Cigarrillo {

       public CigarrilloSmooki() {
           super(100, 2.3f, 2.8f);
       }

    public javax.swing.JLabel getImagenPanel(String path) {
        // Crear un nuevo JPanel
        JPanel panel = new JPanel();

        // Cargar el icono desde la ruta especificada
        ImageIcon icono = new ImageIcon(path);

        // Crear un JLabel con el icono
        JLabel imagenLabel = new JLabel(icono);

        // Añadir el JLabel al panel
        panel.add(imagenLabel);

        return imagenLabel; // Devolver el panel con la imagen configurada
    }
}


