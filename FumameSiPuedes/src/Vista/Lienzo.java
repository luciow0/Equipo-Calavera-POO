package FumameSiPuedes.src.Vista;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import FumameSiPuedes.src.Modelo.Cigarrillo;

public class Lienzo extends JPanel implements KeyListener{

   /* private Image imagen;

    public void ImagenPanel(String ruta){
        ImageIcon icono = new ImageIcon(ruta);
        imagen = icono.getImage();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    } */

    Modelo.CigarrilloSmooki cigarrilloSmooki = new Modelo.CigarrilloSmooki();

    private JLabel prue;

    JLabel imagenPersonaje = cigarrilloSmooki.getImagenPanel("FumameSiPuedes/src/Vista/imgs/Smooki-removebg-preview(1).png");

    //ImageIcon icono = new ImageIcon("src/Vista/imgs/Smooki-removebg-preview(1).png");
    //private JLabel prueba = new JLabel(icono);

    public Lienzo(){
        addKeyListener(this);
        setFocusable(true);
        add(imagenPersonaje);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int step = 25; // Tamaño del paso de movimiento

        // Tamaño del personaje y del panel
        int width = imagenPersonaje.getWidth();
        int height = imagenPersonaje.getHeight();
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Movimiento con verificación de límites
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
            if (imagenPersonaje.getY() - step >= 0) {
                imagenPersonaje.setLocation(imagenPersonaje.getX(), imagenPersonaje.getY() - step);
            }
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
            if (imagenPersonaje.getY() + step + height <= panelHeight) {
                imagenPersonaje.setLocation(imagenPersonaje.getX(), imagenPersonaje.getY() + step);
            }
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
            if (imagenPersonaje.getX() - step >= 0) {
                imagenPersonaje.setLocation(imagenPersonaje.getX() - step, imagenPersonaje.getY());
            }
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
            if (imagenPersonaje.getX() + step + width <= panelWidth) {
                imagenPersonaje.setLocation(imagenPersonaje.getX() + step, imagenPersonaje.getY());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
