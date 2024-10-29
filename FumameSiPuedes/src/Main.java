package FumameSiPuedes.src;

import FumameSiPuedes.src.Vista.Lienzo;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame ventana = new JFrame("Ventana bien piola");
        Lienzo lienzo = new Lienzo(); // Crear una instancia de Lienzo
        ventana.add(lienzo); // Agregar el lienzo al frame

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar para usar todo el tamaño de pantalla
        ventana.setResizable(true); // Permitir el redimensionado
        ventana.setLocationRelativeTo(null); // Centrar la ventana

        // Escuchar cambios de tamaño en la ventana para redibujar el lienzo
        ventana.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                lienzo.repaint(); // Redibujar cuando se redimensiona la ventana
            }
        });

        ventana.setVisible(true);
    }
}
