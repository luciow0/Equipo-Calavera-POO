package FumameSiPuedes.src;
import FumameSiPuedes.src.Vista.GameWindow;

public class Main {
    public static void main(String[] args) {
        // Definir dimensiones de la ventana y título
        int width = 800;
        int height = 600;
        String title = "Fumame Si Puedes";

        // Crear la ventana del juego
        new GameWindow(width, height, title);
    }
}
