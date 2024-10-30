package FumameSiPuedes.src.Modelo;

import javax.swing.*;

public abstract class Cigarrillo {
    private int vida;
    private float salto;
    private float velocidadMov;

    public Cigarrillo(int vida, float salto, float velocidadMov) {
        this.vida = vida;
        this.salto = salto;
        this.velocidadMov = velocidadMov;
    }

    public Cigarrillo(){}

    public javax.swing.JLabel getImagenPanel(String path) {
        // Crear un nuevo JPanel
        JPanel panel = new JPanel();

        // Cargar el icono desde la ruta especificada
        ImageIcon icono = new ImageIcon(path);

        // Crear un JLabel con el icono
        JLabel imagenLabel = new JLabel(icono);

        // Añadir el JLabel al panel
        panel.add(imagenLabel);

        return imagenLabel; // Devolver el panel con la imagen configurada
    }



    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public float getSalto() {
        return salto;
    }

    public void setSalto(float salto) {
        this.salto = salto;
    }

    public float getVelocidadMov() {
        return velocidadMov;
    }

    public void setVelocidadMov(float velocidadMov) {
        this.velocidadMov = velocidadMov;
    }


    // Método que disminuye la vida del cigarrillo si recibe daño
    public void recibirDaño(int daño) {
        this.vida -= daño;
        if (this.vida < 0) this.vida = 0;
    }
}
