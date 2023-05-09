import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    Clip clip = null;
    FloatControl gainControl = null;

    public AudioPlayer(String cancion) {
        File audioFile = new File(cancion);
        AudioInputStream audioStream = null;
    }

    public AudioPlayer(String cancion, boolean loop) {
        File audioFile = new File(cancion);
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(audioStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Obtener el control de volumen (FloatControl)
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        clip.start();
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void detener() {
        clip.stop();
    }

    public boolean isPlaying() {
        return clip.isActive();
    }

    public void ajustarVolumen(float gain) {
        gainControl.setValue(gain);
    }
}
