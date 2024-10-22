package FumameSiPuedes.src.Modelo;

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

    public void moverse() {}

    public void saltar() {}

    public void agacharse() {}

    // Método que disminuye la vida del cigarrillo si recibe daño
    public void recibirDaño(int daño) {
        this.vida -= daño;
        if (this.vida < 0) this.vida = 0;
    }
}
