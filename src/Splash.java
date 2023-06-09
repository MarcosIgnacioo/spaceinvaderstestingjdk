import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Splash extends JDialog {

    private JProgressBar barra;
    private JLabel l2;
    private boolean skipInt = true;

    final AudioPlayer[] musica = {null};
    final AudioPlayer[] musicaMenu = {null};
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

        JButton skipIntro = new JButton("SALTAR");
        skipIntro.setFont(new Font("Tahoma", Font.PLAIN, 18));
        skipIntro.setBackground(new Color(255, 255, 255));
        skipIntro.setBounds(800, 775, 135, 35);

        if (musica[0] == null || !musica[0].isPlaying()){
            musica[0] = new AudioPlayer("src//sonidos//starswarstheme.wav",true);
        }
        AudioPlayer finalMusica = musica[0];
        skipIntro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //dispose();
                //comoJugar skip = new comoJugar();
                //skip.setVisible(true);
                //inicioHilo();
                skipInt = false;
                finalMusica.detener();
                if (musicaMenu[0] == null || !musicaMenu[0].isPlaying()){
                    //musicaMenu[0] = new AudioPlayer("src//sonidos//menumusic.wav",true);
                }
            }
        });
        add(skipIntro);
        l2 = new JLabel();
        l2.setFont(new Font("Tahoma", Font.PLAIN,18));
        l2.setBounds(260,810,300,300);
        getContentPane().add(l2);
        getContentPane().add(etiqueta);
    }


    public class comoJugar extends JFrame {
        public comoJugar() {
            super("How to Play"); // título de la ventana
            //Imagen de fondo
            ImageIcon imagen = new ImageIcon("src//sprites//comoJugar.png");
            JLabel etiqueta = new JLabel(imagen);

            // Agrega la etiqueta al panel
            etiqueta.setSize(1054, 900);
            etiqueta.setLocation(0, 0);

            setSize(1054, 900); // tamaño de la ventana
            setLocationRelativeTo(null);
            getContentPane().setLayout(null);
            setUndecorated(true);
            getContentPane().setBackground(Color.black);

            JButton atras = new JButton("JUGAR");
            atras.setFont(new Font("Tahoma", Font.PLAIN, 18));
            atras.setBackground(new Color(255, 255, 255));
            atras.setBounds(448, 775, 135, 35);

            atras.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    SpaceTest v1 = new SpaceTest();
                    v1.setVisible(true);
                    v1.setLocationRelativeTo(null);
                }
            });
            add(atras);
            add(etiqueta);
        }
    }

    private void inicioHilo() {
        Thread hilo = new Thread(new Runnable() {
            int x = 0;
            String texto = " ";
            AudioPlayer finalMusica = musica[0];

            public void run() {
                try {
                    while (x <= 100 && skipInt == true) {
                        barra.setValue(x);
                        x++;
                        Thread.sleep(932); // 952 50

                        if (x == 5) {
                            texto = "Cargando...";
                            l2.setText(texto);
                        } else if (x == 50) {
                            texto = "Iniciando...";
                            l2.setText(texto);
                        }
                    }
                    finalMusica.detener();
                    musicaMenu[0] = new AudioPlayer("src//sonidos//menumusic.wav",true);

                    ImageIcon imagen = new ImageIcon("src//sprites//MenuPro.png");
                    // Crea el objeto JLabel
                    JLabel etiqueta = new JLabel(imagen);

                    etiqueta.setSize(1054, 900);
                    etiqueta.setLocation(0, 0);

                    JButton play = new JButton("JUGAR");
                    play.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    play.setBackground(new Color(255, 255, 255));
                    play.setBounds(57, 405, 335, 45);

                    JButton comoJugar = new JButton("¿CÓMO JUGAR?");
                    comoJugar.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    comoJugar.setBackground(new Color(255, 255, 255));
                    comoJugar.setBounds(241, 536, 335, 45);

                    JButton salir = new JButton("SALIR");
                    salir.setFont(new Font("Tahoma", Font.PLAIN, 18));
                    salir.setBackground(new Color(255, 255, 255));
                    salir.setBounds(389, 685, 335, 45);

                    getContentPane().removeAll();
                    getContentPane().add(play);
                    getContentPane().add(comoJugar);
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

                    comoJugar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                            comoJugar v1 = new comoJugar();
                            v1.setVisible(true);
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