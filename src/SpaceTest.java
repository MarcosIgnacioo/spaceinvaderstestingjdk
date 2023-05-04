import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SpaceTest {
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    JPanel reiniciarP = new JPanel();
    int nivel = 1;
    Color colorParedes[] = {Color.decode("#3b3a36"), Color.decode("#3b3a36")};


    final AudioPlayer[] ap = {null};
    final AudioPlayer[] ap2 = {null};
    int jugadorX = 420;
    int jugadorY = 600;

    int jugadorVelocidad = 10;
    int jugadorWidth = 10;
    int jugadorHeight= 10;
    int tecla = 100;
    int width = 900;
    int heigth = 900;
    int columnas = 45;
    int filas = 38;
    Rect disparo = null;
    int disparoX = 450;
    int disparoY = 600;

    Bala runnable = new Bala();
    Thread thread = new Thread(runnable);
    int disparoWidth = 10;
    int disparoHeight = 10;


    JLabel tiempoLbl = new JLabel();

    Rect jugadorSprite;
    int mapa[][] = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}

            ,};

    Rect pLista[][] = new Rect[filas][columnas];
    public class MyGraphics extends JComponent {
        MyGraphics() {
            setPreferredSize(new Dimension(width, heigth));
        }

        @Override
        public void paintComponent(Graphics g) {
            jugadorSprite = new Rect(jugadorX, jugadorY,jugadorWidth,jugadorHeight, Color.red);
            super.paintComponent(g);
            g.setColor(jugadorSprite.c);
            g.fillRect(jugadorSprite.x, jugadorSprite.y, jugadorSprite.w, jugadorSprite.h);
            g.setColor(Color.black);
            g.drawRect(jugadorSprite.x, jugadorSprite.y, jugadorSprite.w, jugadorSprite.h);
            g.setColor(Color.CYAN);
            disparo = new Rect(disparoX, disparoY, disparoWidth, disparoHeight, Color.black);
            g.fillRect(disparoX, disparoY, disparoWidth, disparoHeight);
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (mapa[i][j] == 1){
                        g.setColor(colorParedes[nivel-1]);
                        g.fillRect(j*20,i*20,20,20);
                    }
                    else if (mapa[i][j] == 2){
                        g.setColor(Color.RED);
                        g.fillRect(j*20,i*20,20,20);
                    }
                }
            }
        }
    }

    public void createGUI() {
        generaMurosColisionadores();
        frame.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#f9df28"));
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                tecla = e.getKeyCode();
                int teclaAnterior;
                if ((e.getKeyCode() == 65)){
                    if (!jugadorSprite.colisionLabIzquierda(pLista)){
                        jugadorX -= jugadorVelocidad;
                    }

                }
                if (e.getKeyCode() == 68){
                    if (!jugadorSprite.colisionLabDerecha(pLista)){
                        jugadorX += jugadorVelocidad;
                    }
                }

                if ((e.getKeyCode() == 87)){
                    if (!jugadorSprite.colisionLabArriba(pLista)){
                            jugadorY-=jugadorVelocidad;
                    }
                }
                if (e.getKeyCode() == 83){
                    if (!jugadorSprite.colisionLabAbajo(pLista)){
                        jugadorY+=jugadorVelocidad;
                    }

                }

                if ((e.getKeyCode() == 37)){
                    if (!disparo.colisionLabIzquierda(pLista)){
                        disparoX -= jugadorVelocidad;
                    }

                }
                if (e.getKeyCode()==39){
                    if (!disparo.colisionLabDerecha(pLista)){
                        disparoX += jugadorVelocidad;
                    }
                }

                if ((e.getKeyCode() == 38)){
                    if (!disparo.colisionLabArriba(pLista)){
                        disparoY -= jugadorVelocidad;
                    }
                }
                if (e.getKeyCode() == 40){
                    if (!disparo.colisionLabAbajo(pLista)){
                        disparoY+=jugadorVelocidad;
                    }

                }

                if (e.getKeyCode() == 32){
                    if (!jugadorSprite.colisionLabArriba(pLista)){
                        disparoX = jugadorX;
                        disparoY = jugadorY;
                        System.out.println(disparoY);
                        System.out.println(thread.isAlive());
                        if(!thread.isAlive()){
                            thread.start();
                        }
                    }
                    /*if (disparo == null){
                        disparoX = jugadorX;
                        disparoY = jugadorY;
                        disparo = new Rect(disparoX, disparoY, disparoWidth, disparoHeight, Color.black);
                        generaMurosColisionadores();
                    }
                    else{
                        generaMurosColisionadores();
                        if (!disparo.colisionAbajo(jugadorSprite)){
                            System.out.println("wep");
                            disparoY += 10;
                        }
                    }*/
                    System.out.println();
                }
                panel.repaint();
                panel.revalidate();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        JButton reiniciarBtn = new JButton("Reiniciar");
        reiniciarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                int rn = rnd.nextInt(100);
                System.out.println(rn);
                if (rn >= 50){
                    cambiarLab();
                }
                resetearPosicion();
                generaMurosColisionadores();
            }
        });

        tiempoLbl.setFont(new Font("Arial", Font.BOLD, 30));
        tiempoLbl.setForeground(Color.WHITE);
        reiniciarBtn.setFont(new Font("Arial", Font.BOLD, 30));
        reiniciarBtn.setForeground(Color.BLACK);
        Cronometro.iniciar(tiempoLbl);
        panel.add(new MyGraphics());
        panel.setPreferredSize(new Dimension(900,900));
        reiniciarP.setLayout(new BorderLayout());
        reiniciarP.setBackground(Color.decode("#3b3a36"));
        reiniciarP.add(reiniciarBtn, BorderLayout.EAST);
        reiniciarP.add(tiempoLbl, BorderLayout.WEST);
        frame.setFocusable(true);
        frame.requestFocus();
        reiniciarP.setPreferredSize(new Dimension(500,100));
        frame.add(reiniciarP, BorderLayout.SOUTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(900,900));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.repaint();
        frame.revalidate();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public class Rect{
        int x =0;
        int y = 0;
        int w = 0;
        int h = 0;
        Color c = Color.BLACK;
        Rect(int x, int y, int w, int h, Color c){
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.c = c;
        }
        public void weAreSetting(int x, int y, int w, int h, Color c){
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.c = c;
        }
        public Boolean colisionIzquierda(Rect target){
            if (target != null){
                if (this.x < target.x + target.w  + jugadorVelocidad && this.x + this.w> target.x
                        && this.y < target.y + target.h  && this.h +this.y > target.y){
                    return true;
                }
                return false;
            }
            return false;
        }
        public Boolean colisionDerecha(Rect target){
            if (target != null){
                if (this.x < target.x + target.w   && this.x + this.w + jugadorVelocidad > target.x
                        && this.y < target.y + target.h  && this.h +this.y > target.y){
                    return true;
                }
                return false;
            }
            return false;
        }

        public Boolean colisionArriba(Rect target){
            if (target != null){
                if (this.x < target.x + target.w   && this.x + this.w > target.x
                        && this.y < target.y + target.h + jugadorVelocidad && this.h +this.y > target.y){
                    return true;
                }
                return false;
            }
            return false;
        }
        public Boolean colisionAbajo(Rect target){
            if (target != null){
                if (this.x < target.x + target.w   && this.x + this.w > target.x
                        && this.y < target.y + target.h && this.h +this.y + jugadorVelocidad > target.y){
                    return true;
                }
                return false;
            }
            return false;
        }

        public Boolean colisionLabArriba(Rect target[][]){
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (this.colisionArriba(target[i][j]) == true && target[i][j] != null){
                        if (target[i][j].c.equals(Color.RED)){
                            victoriaRoyal();
                            return false;
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        public Boolean colisionLabAbajo(Rect target[][]){
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (this.colisionAbajo(target[i][j]) == true && target[i][j] != null){
                        if (target[i][j].c.equals(Color.RED)){
                            victoriaRoyal();
                            return false;
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        public Boolean colisionLabIzquierda(Rect target[][]){
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (this.colisionIzquierda(target[i][j]) == true&& target[i][j] != null){
                        return true;
                    }
                }
            }
            return false;
        }
        public Boolean colisionLabDerecha(Rect target[][]){
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (this.colisionDerecha(target[i][j]) == true && target[i][j] != null){
                        return true;
                    }
                }
            }
            return false;
        }
    }
    public void generaMurosColisionadores(){
        for (int i = 0; i < mapa.length; i++){
            for (int j = 0; j< columnas; j++){
                pLista[i][j] = null;
                if (mapa[i][j] == 1){
                    pLista[i][j] = new Rect(j*20, i*20, 20,20, Color.CYAN);
                    //g.fillRect(j*20,i*20,20,20);
                }
                else if (mapa[i][j] == 2){
                    pLista[i][j] = new Rect(j*20, i*20, 20,20, Color.RED);
                }
            }
        }
    }

    public class Bala implements Runnable {
        public Bala(){
        }
        public void run() {

            while (!disparo.colisionLabArriba(pLista)) {
                try {
                    Thread.sleep(1000); // Espera 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!disparo.colisionLabArriba(pLista) && !jugadorSprite.colisionLabArriba(pLista)){
                    System.out.println(disparoY);
                    disparoY-=jugadorVelocidad;
                    panel.repaint();
                    panel.revalidate();
                    frame.repaint();
                    frame.revalidate();
                }
            }
        }
    }

    public static void invertirMatriz(int[][] matriz) {
        int longitud = matriz.length;
        for (int i = 0; i < longitud / 2; i++) {
            int[] temp = matriz[i];
            matriz[i] = matriz[longitud - i - 1];
            matriz[longitud - i - 1] = temp;
        }
    }
    public void victoriaRoyal(){
        Cronometro.detener();
        if (nivel == 1){
            nivel =2;
            JOptionPane.showMessageDialog(null, "Ganast en este tiempo " + tiempoLbl.getText(), "WIN", JOptionPane.INFORMATION_MESSAGE);
            panel.setBackground(Color.decode("#d7fe0c"));
        }
        else{
            nivel = 1;
            Cronometro.detener();
            JOptionPane.showMessageDialog(null, "Ganast en este tiempo " + tiempoLbl.getText(), "WIN", JOptionPane.INFORMATION_MESSAGE);
            panel.setBackground(Color.decode("#f9df28"));
        }
        invertirMatriz(mapa);
        generaMurosColisionadores();
        resetearPosicion();
    }
    public void resetearPosicion(){
        Cronometro.reiniciar(tiempoLbl);
        Cronometro.iniciar(tiempoLbl);
        if (nivel == 1){
            jugadorX = 420;
            jugadorY = 30;
        }
        else{
            jugadorX = 430;
            jugadorY = 650;
        }
        panel.repaint();
        panel.revalidate();
        frame.setFocusable(true);
        frame.requestFocus();
    }
    public void cambiarLab(){
        if (nivel == 1){
            nivel =2;
            panel.setBackground(Color.decode("#d7fe0c"));
        }
        else{
            nivel = 1;
            panel.setBackground(Color.decode("#f9df28"));
        }
        invertirMatriz(mapa);
        generaMurosColisionadores();
        resetearPosicion();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SpaceTest GUI = new SpaceTest();
                GUI.createGUI();
            }
        });
    }
}