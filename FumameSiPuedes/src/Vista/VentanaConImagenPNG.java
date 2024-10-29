package FumameSiPuedes.src.Vista;
import javax.swing.*;

public class VentanaConImagenPNG {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana con Imagen PNG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Cargar la imagen PNG
        ImageIcon icono = new ImageIcon("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png");

        // Crear un JLabel con el icono de imagen
        JLabel etiquetaImagen = new JLabel(icono);

        // Agregar el JLabel al JFrame
        frame.add(etiquetaImagen);

        frame.setVisible(true);
    }
}

