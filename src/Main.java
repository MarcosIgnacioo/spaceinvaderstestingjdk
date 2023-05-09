import java.awt.EventQueue;
import java.awt.Image;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Splash sp = new Splash();
                sp.setVisible(true);
            }
        });
    }
}