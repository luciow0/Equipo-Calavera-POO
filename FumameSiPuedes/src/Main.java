package FumameSiPuedes.src;

import FumameSiPuedes.src.Vista.Lienzo;
import FumameSiPuedes.src.Vista.imgs.ImagenPanel;

import javax.swing.*;

public class Main {

    public static void main (String[] args) {
        JFrame ventana = new JFrame("Ventana bien piola");


        ventana.add(new Lienzo());

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(700,700);
        ventana.setVisible(true);
    }
}
