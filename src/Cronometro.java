import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cronometro{
    private static Timer timer;
    private static int horas;
    private static int minutos;
    private static int segundos;
    public static void iniciar(JLabel tiempo) {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                segundos++;
                if (segundos == 60) {
                    segundos = 0;
                    minutos++;
                }
                if (minutos == 60) {
                    minutos = 0;
                    horas++;
                }
                tiempo.setText(String.format("    %02d:%02d:%02d", horas, minutos, segundos));
                tiempo.setHorizontalAlignment(JLabel.CENTER);
                tiempo.setVerticalAlignment(JLabel.CENTER);
            }
        });
        timer.start();
    }

    public static void reiniciar(JLabel tiempo) {
        horas = minutos = segundos = 0;
        tiempo.setText("    00:00:00");
        tiempo.setHorizontalAlignment(JLabel.CENTER);
        tiempo.setVerticalAlignment(JLabel.CENTER);
        if (timer != null) {
            timer.stop();
        }
    }
    public static void detener(){
        timer.stop();
    }
}
