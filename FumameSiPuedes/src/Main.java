package FumameSiPuedes.src;

import FumameSiPuedes.src.Vista.Lienzo;
import FumameSiPuedes.src.Vista.VentanaInicio;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {


        // Aquí defines las rutas de las imágenes
        String rutaImagen1 = "FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png";
        String rutaImagen2 = "FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png";
        String rutaImagen3 = "FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png";

        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventanaInicioJuego = new VentanaInicio(rutaImagen1, rutaImagen2, rutaImagen3);
            ventanaInicioJuego.setVisible(true);
            boolean iniciarJuego = ventanaInicioJuego.getInicioJuego();
            if(iniciarJuego){
            JFrame ventana = new JFrame("Fumame si puedes)");
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

        });
        

    }
}
