package FumameSiPuedes.src.Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame {
    private boolean inicioJuego = false;
    private String rutaImagen1;
    private String rutaImagen2;
    private String rutaImagen3;

    public VentanaInicio(String rutaImagen1, String rutaImagen2, String rutaImagen3) {
        this.rutaImagen1 = rutaImagen1;
        this.rutaImagen2 = rutaImagen2;
        this.rutaImagen3 = rutaImagen3;

        setTitle("Fumame si puedes");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de bienvenida
        JPanel panelBienvenida = new JPanel();
        panelBienvenida.setLayout(new FlowLayout());
        JLabel labelBienvenida = new JLabel("Bienvenido, Seleccione un cigarrillo para continuar");
        panelBienvenida.add(labelBienvenida);
        add(panelBienvenida, BorderLayout.NORTH);

        // Panel central con imágenes y botones
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(3, 2, 10, 10));

        // Cargar y mostrar las imágenes
        String[] rutasImagenes = {rutaImagen1, rutaImagen2, rutaImagen3};
        for (int x = 0; x < 3; x++) {
            ImageIcon imagenIcono = new ImageIcon(rutasImagenes[x]);
            JLabel labelImagen = new JLabel();
            labelImagen.setIcon(imagenIcono);
            labelImagen.setHorizontalAlignment(JLabel.CENTER);
            panelCentro.add(labelImagen);

            JButton botonSeleccionar = new JButton("Seleccionar Personaje " + (x + 1));
            int finalX = x + 1;
            botonSeleccionar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Acción para seleccionar el personaje
                    JOptionPane.showMessageDialog(null, "Personaje " + finalX + " seleccionado");
                }
            });
            panelCentro.add(botonSeleccionar);
        }

        add(panelCentro, BorderLayout.CENTER);



        // Panel de botón de inicio
        JPanel panelInicio = new JPanel();
        JButton botonInicio = new JButton("Empezar Juego");
        botonInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inicioJuego = true;
            }
        });
        panelInicio.add(botonInicio);
        add(panelInicio, BorderLayout.SOUTH);
    }

    public boolean getInicioJuego() {
        return inicioJuego;
    }
}
