package FumameSiPuedes.src;

import FumameSiPuedes.src.Vista.Lienzo;
import FumameSiPuedes.src.Vista.imgs.ImagenPanel;

import javax.swing.*;

public class Main {

    public static void main (String[] args) {
        JFrame ventana = new JFrame("Teclado");

        ImagenPanel imagenPanel = new ImagenPanel("FumameSiPuedes/src/imgs/fondo.jpg");


        ventana.add(new Lienzo());

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(300,300);
        ventana.setVisible(true);
    }
}
