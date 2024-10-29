package FumameSiPuedes.src.Vista.imgs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagenPanel extends JPanel {
    private BufferedImage imagen;

    public ImagenPanel(String ruta) {
        try {
            imagen = ImageIO.read(new File(ruta)); // Carga la imagen desde la ruta especificada
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this); // Dibuja la imagen ajustada al tamaño del panel
        }
    }
}
