import java.awt.EventQueue;
import java.awt.Image;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SpaceTest GUI = new SpaceTest();
                GUI.setVisible(true);
                //GUI.crearInterfaz();
            }
        });
    }
}