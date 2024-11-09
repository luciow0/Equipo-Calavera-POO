package FumameSiPuedes.src;

import Vista.VentanaInicio;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String rutaImagen1 = "FumameSiPuedes/src/Vista/imgs/Smoki/smoki-derecha.png";
        String rutaImagen2 = "FumameSiPuedes/src/Vista/imgs/MentaSplash/minty-derecha.png";
        String rutaImagen3 = "FumameSiPuedes/src/Vista/imgs/LazySlim/lazyslim-derecha.png";

        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventanaInicioJuego = new VentanaInicio(rutaImagen1, rutaImagen2, rutaImagen3);

            ventanaInicioJuego.setVisible(true);


        });
    }
}
