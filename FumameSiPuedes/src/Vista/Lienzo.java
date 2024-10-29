package FumameSiPuedes.src.Vista;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Lienzo extends JPanel implements KeyListener{

    private Image imagen;

    public void ImagenPanel(String ruta){
        ImageIcon icono = new ImageIcon(ruta);
        imagen = icono.getImage();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }



    private JLabel prueba = new JLabel("oaaa");

    public Lienzo(){
        addKeyListener(this);
        setFocusable(true);
        add(prueba);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_UP){
            prueba.setLocation(prueba.getX(), prueba.getY() - 5);
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            prueba.setLocation(prueba.getX(), prueba.getY() + 5);
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT){
            prueba.setLocation(prueba.getX() - 5, prueba.getY());
        }

        if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            prueba.setLocation(prueba.getX() + 5, prueba.getY());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
