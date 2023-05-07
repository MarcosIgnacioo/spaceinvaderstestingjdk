import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JDialog {

    private JProgressBar barra;
    private JLabel l2;

    //PROPIEDADES DIALOOGO
    public Splash() {
        inicio();
        setSize(1054,900);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setUndecorated(true);

        getContentPane().setBackground(Color.BLACK);

        repaint();
        revalidate();
        inicioHilo();
    }

    //PROPIEDADES ELEMENTOS
    private void inicio() {
        //Imagen de fondo
        ImageIcon imagen = new ImageIcon("src//sprites//introLetras.gif");
        JLabel etiqueta = new JLabel(imagen);

        // Agrega la etiqueta al panel
        etiqueta.setSize(1054, 558);
        etiqueta.setLocation(0, 120);

        barra = new JProgressBar();
        barra.setBounds(154,7097,294,22);
        barra.setOpaque(true);
        barra.setForeground(Color.decode("#3E4532"));
        getContentPane().add(barra);

        l2 = new JLabel();
        l2.setFont(new Font("Tahoma", Font.PLAIN,18));
        l2.setBounds(260,810,300,300);
        getContentPane().add(l2);

        getContentPane().add(etiqueta);
    }


    private void inicioHilo() {
        Thread hilo = new Thread(new Runnable() {
            int x = 0;
            String texto = " ";

            public void run() {
                try {

                    while (x <= 100) {
                        barra.setValue(x);
                        x++;
                        Thread.sleep(952); // 952

                        if (x == 5) {
                            texto = "Cargando...";
                            l2.setText(texto);
                        } else if (x == 50) {
                            texto = "Iniciando...";
                            l2.setText(texto);
                        }

                    }

                    ImageIcon imagen = new ImageIcon("src//sprites//MenuPro.png");
                    // Crea el objeto JLabel
                    JLabel etiqueta = new JLabel(imagen);

                    etiqueta.setSize(1054, 900);
                    etiqueta.setLocation(0, 0);

                    JButton play = new JButton("PLAY     >");
                    play.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    play.setBackground(new Color(255, 255, 255));
                    play.setBounds(105, 427, 335, 45);

                    JButton salir = new JButton("EXIT GAME");
                    salir.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    salir.setBackground(new Color(255, 255, 255));
                    salir.setBounds(359, 658, 335, 45);

                    getContentPane().removeAll();
                    getContentPane().add(play);
                    getContentPane().add(salir);
                    getContentPane().add(etiqueta);

                    revalidate();
                    repaint();

                    play.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            SpaceTest v1 = new SpaceTest();
                            v1.setVisible(true);
                            v1.setLocationRelativeTo(null);
                        }
                    });
                    salir.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            System.exit(0);
                        }
                    });
                } catch (Exception e) {
                    System.out.println("Excepcion: " + e.getMessage());
                }
            }
        });
        hilo.start();
    }

}