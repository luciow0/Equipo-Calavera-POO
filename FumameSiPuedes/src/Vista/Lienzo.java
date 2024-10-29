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
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP){
            imagenPersonaje.setLocation(imagenPersonaje.getX(), imagenPersonaje.getY() - 25);
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            imagenPersonaje.setLocation(imagenPersonaje.getX(), imagenPersonaje.getY() + 25);
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
            imagenPersonaje.setLocation(imagenPersonaje.getX() - 25, imagenPersonaje.getY());
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            imagenPersonaje.setLocation(imagenPersonaje.getX() + 25, imagenPersonaje.getY());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
