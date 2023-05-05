import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SpaceTest extends JFrame implements KeyListener{

    JPanel panelJuego = new JPanel();
    JFrame frame = new JFrame();

    JPanel panelInferior = new JPanel();
    int nivel = 1;
    int iColisioanda;
    int jColisionada;
    Color colorParedes[] = {Color.decode("#3b3a36"), Color.decode("#3b3a36")};

    private Image xwing;
    private Image tie;

    int jugadorX = 420; // POSISICION X INICIAL DEL JUGADOR
    int jugadorY = 650; // POSICION Y INICIAL DEL JUGADOS

    int jugadorVelocidad = 10; // VELOCIDAD DEL JUGADOR
    int jugadorWidth = 15; // TAMANO DEL JUGADOR
    int jugadorHeight= 15; // TAMANO DEL JUGADOR

    int tecla = 100; // NO SE QUE ES
    int width = 900; // TAMPOCO SE QUE ES

    int heigth = 900; // TAMPOCO SE QUE ES
    int columnas = 45; // COLUMNAS DE LA MATRIZ
    int filas = 38; // FILAS DE LA MATRIZ

    Rect disparo = null; // UNA VARIABLE RECT QUE SERA UTILIZADA PARA EL DISPARO
    int disparoX = 450;
    int disparoY = 600;

    Bala runnable = new Bala(); // NO SE QUE ES
    Thread thread = new Thread(runnable); // UN HILITO
    int disparoWidth = 5; // ANCHO DEL DISPARO
    int disparoHeight = 15; // ALTO DEL DISPARO

    JLabel tiempoLbl = new JLabel(); // JLabel para el tiempo nada mas
    Rect jugadorSprite; // Sprite para el jugador

    public SpaceTest() {
        generaMurosColisionadores();
        this.setLayout(new BorderLayout());

        panelJuego.setBackground(Color.decode("#000000"));


        try {
            xwing = ImageIO.read(new File("src\\sprites\\navepro.png"));
            tie = ImageIO.read(new File("src\\sprites\\tiefighter.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addKeyListener(this);

        //HACER QUE APAREZCA EN EL CENTRO, AUNQUE NO FUNCIONA XD
        this.setLocationRelativeTo(null);

        //INICIALIZAR TEXTO PARA EL TIEMPO
        tiempoLbl.setFont(new Font("Arial", Font.BOLD, 30));
        tiempoLbl.setForeground(Color.WHITE);

        //INICIAR EL CRONOMETRO
        Cronometro.iniciar(tiempoLbl);

        //AL PANEL DONDE SE ENCUENTRA NUESTRO JUEGO LE AGREGAMOS LOS ELEMENTOS VISUALES
        panelJuego.add(new MyGraphics());
        panelJuego.setPreferredSize(new Dimension(900, 900));

        //ELEMENTOS PARA EL PANEL INFERIOR
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(Color.decode("#3b3a36"));
        panelInferior.add(tiempoLbl, BorderLayout.WEST);
        panelInferior.setPreferredSize(new Dimension(500,100));

        this.setFocusable(true); // HACEMOS QUE SE PUEDA INTERACTUAR CON EL TECLADO
        this.requestFocus(); // MAS DE LO MISMO

        //SELECCIONAMOS LOS BORDES DE NUESTRA VENTANA
        this.add(panelInferior, BorderLayout.SOUTH);
        this.add(panelJuego, BorderLayout.CENTER);

        //TAMANO DE LA VENTANA
        this.setPreferredSize(new Dimension(900,900));
        this.pack();

        //SET VISIBLE - CERRAR LA EJECUCION UNA VEZ QUE SE CIERRE
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //REPAINT Y REVALIDATE
        this.repaint();
        this.revalidate();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        tecla = e.getKeyCode();
        int teclaAnterior;

        //IZQUIERDA
        if ((e.getKeyCode() == 65 && jugadorX > 10)){
            if (!jugadorSprite.colisionLabIzquierda(pLista)){
                jugadorX -= jugadorVelocidad;
            }

        }

        //DERECHA
        if (e.getKeyCode() == 68 && jugadorX < 880){
            if (!jugadorSprite.colisionLabDerecha(pLista)){
                jugadorX += jugadorVelocidad;
            }
        }

        //ARRIBA
        if ((e.getKeyCode() == 87 && jugadorY > 10 )){
            if (!jugadorSprite.colisionLabArriba(pLista)){
                jugadorY-=jugadorVelocidad;
            }
        }
        //ABAJO
        if (e.getKeyCode() == 83 && jugadorY < 700){
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
                panelJuego.repaint();
                panelJuego.revalidate();
                System.out.println(disparoY);
                System.out.println(thread.isAlive());
                if (!thread.isAlive()){
                    thread.start();
                }
            }
            System.out.println();
        }
        panelJuego.repaint();
        panelJuego.revalidate();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    //Matriz del mapa, 1 == Muro, 0 == Vacio ---------------------------------------------------------------------------------------------------------
    int mapa[][] = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0},
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
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}

            ,};

    // FIN MATRIZ DEL MAPA --------------------------------------------------------------------------------------------------------------------------------------
    Rect pLista[][] = new Rect[filas][columnas]; // Sigo intentando agarrar la logica


    public class MyGraphics extends JComponent {
        MyGraphics() {
            setPreferredSize(new Dimension(width, heigth));
        }
        @Override
        public void paintComponent(Graphics g) {

            //SPRITE DEL JUGADOR
            jugadorSprite = new Rect(jugadorX, jugadorY,jugadorWidth, jugadorHeight, Color.black);
            g.drawImage(xwing, jugadorX, jugadorY, 50,50, null);
            g.setColor(jugadorSprite.c);

            g.fillRect(jugadorSprite.x, jugadorSprite.y, jugadorSprite.w, jugadorSprite.h);
            g.setColor(Color.black);

            g.drawRect(jugadorSprite.x, jugadorSprite.y, jugadorSprite.w, jugadorSprite.h);
            super.paintComponent(g);

            g.setColor(Color.CYAN);
            disparo = new Rect(disparoX, disparoY, disparoWidth, disparoHeight, Color.black);
            g.fillRect(disparoX, disparoY, disparoWidth, disparoHeight);

            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (mapa[i][j] == 1){
                        g.setColor(Color.black);
                        g.fillRect(j*20,i*20,20,20);
                        g.drawImage(tie, j*20, i*20, 50,50, null);
                    }
                    else if (mapa[i][j] == 2){
                        g.setColor(Color.RED);
                        g.fillRect(j*20,i*20,20,20);
                        g.drawImage(tie, j*20, i*20, 50,50, null);
                    }
                }
            }

        }
    }

    //CLASE RECT
    public class Rect{
        int x =0;
        int y = 0;
        int w = 0;
        int h = 0;
        Color c = Color.BLACK;
        Image img;

        Rect(int x, int y, int w, int h, Color c){
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

        //COLISION DE ARRIBA
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

        //COLISION DE ABAJO
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
                            //victoriaRoyal();
                            //GANASTE
                            return false;
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        //
        public Boolean colisionLabArribaBala(Rect target[][]){
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (this.colisionArriba(target[i][j]) == true && target[i][j] != null){
                        System.out.println(i);
                        System.out.println(j);
                        iColisioanda = i;
                        jColisionada = j;
                        if (target[i][j].c.equals(Color.RED)){
                            //GANASTE
                            //victoriaRoyal();
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
                            //GANASTE
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

            while (true) {
                try {
                    Thread.sleep(500); // Espera 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Esta vive");
                System.out.println("i: " + iColisioanda + " j: " + jColisionada);
                if(!disparo.colisionLabArribaBala(pLista) && !jugadorSprite.colisionLabArriba(pLista)){
                    System.out.println(disparoY);
                    pLista[iColisioanda][jColisionada] = null;
                    mapa[iColisioanda][jColisionada] = 0;
                    panelJuego.repaint();
                    panelJuego.revalidate();

                    frame.repaint();
                    frame.revalidate();
                }

                disparoY-=jugadorVelocidad;
            }
        }
    }
}