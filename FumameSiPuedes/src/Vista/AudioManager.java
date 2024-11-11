package FumameSiPuedes.src.Vista;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {

    private static Clip musicaClip;

    public static void reproducirAudioEnLoop(String rutaArchivo) {

        System.out.println("aca reproduciend a pleno" + rutaArchivo);

        if (musicaClip != null && musicaClip.isRunning()) {
            return; // Evita reiniciar la música si ya está sonand
        }

        try {
            File archivoAudio = new File(rutaArchivo);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoAudio);
            musicaClip = AudioSystem.getClip();
            musicaClip.open(audioStream);
            musicaClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicaClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}