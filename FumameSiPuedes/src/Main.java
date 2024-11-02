package FumameSiPuedes.src;

import Vista.Lienzo;
import Vista.VentanaInicio;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        boolean iniciarJuego = false;

        // Aquí defines las rutas de las imágenes
        String rutaImagen1 = "FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png";
        String rutaImagen2 = "FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png";
        String rutaImagen3 = "FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png";
        // Rutas para Feli que tiene Linux :D
        //String rutaImagen1 = "/home/pipeta/Documents/Code/TP_POO/Equipo-Calavera-POO/FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png";
        //String rutaImagen2 = "/home/pipeta/Documents/Code/TP_POO/Equipo-Calavera-POO/FumameSiPuedes/src/Vista/imgs/Menta_Splash-removebg-preview.png";
        //String rutaImagen3 = "/home/pipeta/Documents/Code/TP_POO/Equipo-Calavera-POO/FumameSiPuedes/src/Vista/imgs/Lazy_Slim-removebg-preview.png";


        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventanaInicioJuego = new VentanaInicio(rutaImagen1, rutaImagen2, rutaImagen3);
            ventanaInicioJuego.setVisible(true);});

    }
}
