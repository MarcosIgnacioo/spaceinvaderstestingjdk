import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SpaceTest extends JFrame implements KeyListener{

    private boolean Izq = false , Der = false;
    private Timer movimientoTimer;
    JPanel panelJuego = new JPanel();
    JFrame frame = new JFrame();
    JPanel reiniciarP = new JPanel();
    JPanel panelInferior = new JPanel();
    //implementacion de las vidas y el puntaje
    int puntaje = 0, vidas = 3;
    int nivel = 1;
    int iColisioanda;
    int jColisionada;
    int iColisioandaBE;
    int jColisionadaBE;
    Color colorParedes[] = {Color.decode("#3b3a36"), Color.decode("#3b3a36")};

    private Image xwing;
    private Image tie;
    private Image tieCuatro;
    private Image tieCinco;
    private Image roca;

    boolean isDisparando = false;
    boolean isDisparandoEnemigo = false;
    boolean reseteaDisparo = false;

    int jugadorX = 420; // POSISICION X INICIAL DEL JUGADOR
    int jugadorY = 650; // POSICION Y INICIAL DEL JUGADOS

    int jugadorVelocidad = 10; // VELOCIDAD DEL JUGADOR
    int jugadorWidth = 50; // TAMANO DEL JUGADOR
    int jugadorHeight= 15; // TAMANO DEL JUGADOR

    int tecla = 100; // NO SE QUE ES
    int width = 900; // TAMPOCO SE QUE ES

    int heigth = 900; // TAMPOCO SE QUE ES
    int columnas = 45; // COLUMNAS DE LA MATRIZ
    int filas = 38; // FILAS DE LA MATRIZ

    Rect disparoEnemigo = null;
    Rect disparo = null; // UNA VARIABLE RECT QUE SERA UTILIZADA PARA EL DISPARO

    int disparoEnemigoX = jugadorX;
    int disparoEnemigoY = jugadorY;
    int disparoEnemigoWidth = 7; // ANCHO DEL DISPARO VERDE
    int disparoEnemigoHeight = 40; // ALTO DEL DISPARO VERDE

    int disparoX = 450;
    int disparoY = 600;

    Bala runnableBalaPlayer = new Bala(); // NO SE QUE ES
    Thread threadBalaPlayer = new Thread(runnableBalaPlayer); // UN HILITO

    BalaEnemiga runnableBalaEnemiga = new BalaEnemiga();
    Thread threadBalaEnemiga = new Thread(runnableBalaEnemiga);

    EnemigosMovimiento runnableEnemigosMov = new EnemigosMovimiento();
    Thread threadEnemigosMovimiento = new Thread(runnableEnemigosMov);

    int disparoWidth = 5; // ANCHO DEL DISPARO
    int disparoHeight = 15; // ALTO DEL DISPARO
    int bloqueActualizadorX = 0;
    int getBloqueActualizadorY = 0;

    JLabel tiempoLbl = new JLabel(); // JLabel para el tiempo nada mas
    Rect jugadorSprite; // Sprite para el jugador

    public SpaceTest() {
        generaMurosColisionadores();
        this.setLayout(new BorderLayout());

        panelJuego.setBackground(Color.decode("#000000"));


        try {
            xwing = ImageIO.read(new File("src//sprites//navepro.png"));
            tie = ImageIO.read(new File("src//sprites//tiefighter.png"));
            tieCuatro = ImageIO.read(new File("src//sprites//tiefighter4.png"));
            tieCinco = ImageIO.read(new File("src//sprites//tieFighter5.png"));
            roca = ImageIO.read(new File("src//sprites//roca.png"));
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

        //AL PANEL DONDE SE ENCUENTRA NUESTRO JUEGO LE AGREGAMOS LOS ELEMENTOS VISUALES
        panelJuego.add(new MyGraphics());
        panelJuego.setPreferredSize(new Dimension(900, 900));

        //ELEMENTOS PARA EL PANEL INFERIOR
        panelInferior.setLayout(new BorderLayout());
        panelInferior.setBackground(Color.decode("#2d2d2d"));
        panelInferior.add(tiempoLbl, BorderLayout.WEST);
        panelInferior.setPreferredSize(new Dimension(500,50));

        this.setFocusable(true); // HACEMOS QUE SE PUEDA INTERACTUAR CON EL TECLADO
        this.requestFocus(); // MAS DE LO MISMO

        //SELECCIONAMOS LOS BORDES DE NUESTRA VENTANA
        this.add(panelInferior, BorderLayout.SOUTH);
        this.add(panelJuego, BorderLayout.CENTER);

        //TAMANO DE LA VENTANA
        int x=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,
            y=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setBounds((x-900)/2,0,900,1100);
        this.setPreferredSize(new Dimension(900,1100));
        this.pack();

        //SET VISIBLE - CERRAR LA EJECUCION UNA VEZ QUE SE CIERRE
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //REPAINT Y REVALIDATE
        this.repaint();
        this.revalidate();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        threadBalaEnemiga.start();
        threadEnemigosMovimiento.start();

        JButton reiniciarBtn = new JButton("Reiniciar");
        reiniciarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rnd = new Random();
                int rn = rnd.nextInt(100);
                if (rn >= 50){
                    cambiarLab();
                }
                generaMurosColisionadores();
            }
        });

    }

    private void Movimiento(){
        if (!jugadorSprite.colisionLabIzquierda(pLista) && Izq && jugadorX > 10){
            jugadorX -= jugadorVelocidad;
        }

        if (!jugadorSprite.colisionLabDerecha(pLista) && Der && jugadorX < 840){
            jugadorX += jugadorVelocidad;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        tecla = e.getKeyCode();
        //IZQUIERDA
        if ((e.getKeyCode() == KeyEvent.VK_A)){
            Izq = true;
        }
        //DERECHA
        if (e.getKeyCode() == KeyEvent.VK_D){
            Der = true;
        }

        if (e.getKeyCode() == 32){
            if (!jugadorSprite.colisionLabArriba(pLista)){
                if (!isDisparando){
                    isDisparando = true;
                    disparoX = jugadorX;
                    disparoY = jugadorY;
                    panelJuego.repaint();
                    panelJuego.revalidate();
                }
                if (!threadBalaPlayer.isAlive()){
                    threadBalaPlayer.start();
                }
            }
        }
        panelJuego.repaint();
        panelJuego.revalidate();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        //IZQUIERDA
        if ((e.getKeyCode() == KeyEvent.VK_A)){
            Izq = false;
        }
        //DERECHA
        if (e.getKeyCode() == KeyEvent.VK_D){
            Der = false;
        }
    }

    //Matriz del mapa, 1 == Muro, 0 == Vacio ---------------------------------------------------------------------------------------------------------
    int mapa[][] = {
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0}, //PRIMER LINEA DE TIE FIGHTERS
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,0,4,0,0,5,0,0,4,0,0,4,0,0,4,0,0,5,0,0,4,0,0,4,0,0,4,0,0,5,0,0,4,0,0,0,0,0,0,0}, //SEGUNDA LINEA MIXTA
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0}, // TERCERA LINEA
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
            {0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0,0,7,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,7,7,7,0,0,0,0,0,0,0,0,0,7,7,7,0,0,0,0,0,0,0,0,0,7,7,7,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,7,7,7,7,7,0,0,0,0,0,0,0,7,7,7,7,7,0,0,0,0,0,0,0,7,7,7,7,7,0,0,0,0,0,0,0,0},
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

            g.setColor(Color.ORANGE);
            g.drawString("Puntaje: "+puntaje, 10,10);
            g.drawString("Vidas: "+vidas,10,30);
            Movimiento();
            super.paintComponent(g);

            if(isDisparando){
                g.setColor(Color.RED);
                disparo = new Rect(disparoX, disparoY, disparoWidth, disparoHeight, Color.black);
                g.fillRect(disparoX, disparoY, disparoWidth, disparoHeight);
            }

            if (isDisparandoEnemigo){
                disparoEnemigo = new Rect(disparoEnemigoX,disparoEnemigoY,disparoEnemigoWidth, disparoEnemigoHeight, Color.green);
                g.setColor(disparoEnemigo.c);
                g.fillRect(disparoEnemigoX, disparoEnemigoY, disparoEnemigoWidth, disparoEnemigoHeight);
            }


            //TAMANO DE LA NAVE ------------------------------------------------------------------------------------------------------------------------------
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){

                    //TAMANO DE LA NAVE
                    if (mapa[i][j] == 1){
                        g.setColor(Color.BLACK);
                        g.fillRect(j*20,i*20,40,40);
                        g.drawImage(tie, j*20, i*20, 40,40, null);
                    }

                    else if (mapa[i][j] == 2){
                        g.setColor(Color.GREEN);
                        g.fillRect(j*20,i*20,50,50);
                        g.drawImage(tie, j*20, i*20, 50,50, null);
                    }

                    //NAVE DE ATAQUE
                    if (mapa[i][j] == 4){
                        g.setColor(Color.BLACK);
                        g.fillRect(j*20,i*20,45,45);
                        g.drawImage(tieCuatro, j*20, i*20, 45,45, null);
                    }

                    //NAVE ROJA
                    if (mapa[i][j] == 5){
                        g.setColor(Color.BLACK);
                        g.fillRect(j*20,i*20,45,45);
                        g.drawImage(tieCinco, j*20, i*20, 45,45, null);
                    }

                    //ROCA
                    if (mapa[i][j] == 7){
                        g.setColor(Color.BLACK);
                        g.fillRect(j*20,i*20,25,25);
                        g.drawImage(roca, j*20, i*20, 25,25, null);
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
                if (this.x <= target.x + target.w   && this.x + this.w >= target.x
                        && this.y <= target.y + target.h && this.h +this.y + jugadorVelocidad >= target.y){
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
                        iColisioanda = i;
                        jColisionada = j;
                        //Implementacion de la obtencion de puntos
                        if(mapa[i][j] != 7)
                            puntaje += (100*mapa[i][j]);
                        return true;
                    }
                }
            }
            return false;
        }

        public Boolean colisionLabAbajoBalaEnemiga(Rect target[][]){
            for (int i = 0; i < mapa.length; i++){
                for (int j = 0; j< columnas; j++){
                    if (this.colisionAbajo(target[i][j]) == true && target[i][j] != null && mapa[i][j] == 7){
                        iColisioandaBE = i;
                        jColisionadaBE = j;
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
                    pLista[i][j] = new Rect(j*20, i*20, 50,50, Color.CYAN);
                }
                if (mapa[i][j] == 4){
                    pLista[i][j] = new Rect(j*20, i*20, 50,50, Color.RED);
                }
                if (mapa[i][j] == 5){
                    pLista[i][j] = new Rect(j*20, i*20, 50,50, Color.RED);
                }
                if (mapa[i][j] == 7){
                    pLista[i][j] = new Rect(j*20, i*20, 50,50, Color.RED);
                }
            }
        }
    }

    public class Bala implements Runnable {
        public Bala(){
        }
        public void run() {
            boolean hiloMotor = true;

            do{
                try {
                    Thread.sleep(15); // Espera 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!disparo.colisionLabArribaBala(pLista) && !jugadorSprite.colisionLabArriba(pLista) && isDisparando){
                    disparoY-=jugadorVelocidad;
                    panelJuego.repaint();
                    panelJuego.revalidate();
                    actualizarPaint();
                    if(disparoY < 0){
                        disparoX = jugadorX;
                        disparoY = jugadorY;
                        actualizarPaint();
                        hiloMotor = false;
                        panelJuego.repaint();
                        panelJuego.revalidate();
                        isDisparando = false;
                    }
                }

                else{
                    if(mapa[iColisioanda][jColisionada] != 7){
                        //COLISION DISPARO CON CUADRADO
                        mapa[iColisioanda][jColisionada] = 0;
                        pLista[iColisioanda][jColisionada] = null;
                        disparoX = jugadorX;
                        disparoY = jugadorY;
                        actualizarPaint();
                        hiloMotor = false;
                        panelJuego.repaint();
                        panelJuego.revalidate();
                        isDisparando = false;
                    }
                    else{
                        disparoX = jugadorX;
                        disparoY = jugadorY;
                        panelJuego.repaint();
                        panelJuego.revalidate();
                        isDisparando = false;
                    }
                    //resetea la posicion de la bala o hazla null para ver si eso actualiza el pinchi graphics
                    //resetea la posicion de la bala o hazla null para ver si eso actualiza el pinchi graphics
                }
            } while (true);
        }
    }

    public class BalaEnemiga implements Runnable {
        public BalaEnemiga(){
        }
        public void run() {
            isDisparandoEnemigo = true;
            Random rnd = new Random();
            ArrayList<Integer> posiciones;
            posiciones = generadorDePosicionDeBalasEnemigasAleatorio();
            int posicionRandom = rnd.nextInt(((posiciones.size())-1));
            disparoEnemigoX = posiciones.get(posicionRandom+1);
            disparoEnemigoY = posiciones.get((posicionRandom));
            int pop = 0;
            boolean segundoDisparo = false;
            Rect testSiVieneDeNave;
            do{
                testSiVieneDeNave = new Rect(disparoEnemigoX,disparoEnemigoY,disparoEnemigoWidth,disparoEnemigoHeight,Color.green);
                while(testSiVieneDeNave != null && !testSiVieneDeNave.colisionLabArriba(pLista) && !segundoDisparo)
                {
                    posicionRandom = rnd.nextInt(((posiciones.size())-1));
                    disparoEnemigoX = posiciones.get(posicionRandom+1);
                    disparoEnemigoY = posiciones.get((posicionRandom));
                    testSiVieneDeNave.x = disparoEnemigoX;
                    testSiVieneDeNave.y = disparoEnemigoY;
                }
                try {
                    Thread.sleep(15); // Espera 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(disparoEnemigo !=null && !disparoEnemigo.colisionAbajo(jugadorSprite) &&
                  ((disparoEnemigo.colisionLabArriba(pLista)) || (segundoDisparo)) && !disparoEnemigo.colisionLabAbajoBalaEnemiga(pLista)){
                    disparoEnemigoY+=jugadorVelocidad;
                    panelJuego.repaint();
                    panelJuego.revalidate();
                    segundoDisparo = true;
                    if(disparoEnemigoY > 620){
                        posicionRandom = rnd.nextInt(posiciones.size()-1);
                        disparoEnemigoX = posiciones.get(posicionRandom+1);
                        disparoEnemigoY = posiciones.get(posicionRandom);
                        panelJuego.repaint();
                        panelJuego.revalidate();
                        segundoDisparo = false;
                    }
                }
                else if (disparoEnemigo !=null && disparoEnemigo.colisionAbajo(jugadorSprite)){
                    //COLISION DISPARO CON EL JUGADOR
                    vidas--;
                    segundoDisparo = false;
                    posicionRandom = rnd.nextInt(posiciones.size()-1);
                    disparoEnemigoX = posiciones.get(posicionRandom+1);
                    disparoEnemigoY = posiciones.get(posicionRandom);
                    panelJuego.repaint();
                    panelJuego.revalidate();
                    //resetea la posicion de la bala o hazla null para ver si eso actualiza el pinchi graphics
                }
                else{
                    if(mapa[iColisioandaBE][jColisionadaBE] == 7){
                        //COLISION DISPARO CON CUADRADO
                        mapa[iColisioandaBE][jColisionadaBE] = 0;
                        pLista[iColisioandaBE][jColisionadaBE] = null;
                        segundoDisparo = false;
                        posicionRandom = rnd.nextInt(posiciones.size()-1);
                        disparoEnemigoX = posiciones.get(posicionRandom+1);
                        disparoEnemigoY = posiciones.get(posicionRandom);
                        panelJuego.repaint();
                        panelJuego.revalidate();
                        actualizarPaint();
                    }
                    else{
                        segundoDisparo = false;
                        posicionRandom = rnd.nextInt(posiciones.size()-1);
                        disparoEnemigoX = posiciones.get(posicionRandom+1);
                        disparoEnemigoY = posiciones.get(posicionRandom);
                        panelJuego.repaint();
                        panelJuego.revalidate();
                    }
                    panelJuego.repaint();
                    panelJuego.revalidate();
                    isDisparando = false;
                }
                posiciones = generadorDePosicionDeBalasEnemigasAleatorio();
            } while (true);
        }
    }

    public ArrayList<Integer> generadorDePosicionDeBalasEnemigasAleatorio(){
        int variableUno = 0;
        int variableDos = 0;
        Random rnd = new Random();
        ArrayList<Integer> posicionesUno = new ArrayList<Integer>();
        for(int i = 0; i < mapa.length; i++){
            for (int j = 0; j < mapa[0].length; j++){
                if(mapa[i][j] == 1){
                    variableUno = i*20;
                    variableDos = j*20;
                    posicionesUno.add(variableUno);
                    posicionesUno.add(variableDos);
                }
            }
        }
        return posicionesUno;
    }

    public class EnemigosMovimiento implements Runnable {
        public EnemigosMovimiento(){
        }
        public void run() {
            do{
                try {
                    Thread.sleep(3000); // Espera 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = mapa.length - 1; i > 0; i--) {
                    if(mapa[i][0] == 1){
                        mapa[i] = mapa[i-1];
                    }
                }
                for (int j = 0; j < mapa[0].length; j++) {
                    if(mapa[0][j] == 1){
                        mapa[0][j] = 0;
                    }
                }

                panelJuego.repaint();
                panelJuego.revalidate();
                generaMurosColisionadores();
            } while (true);
        }
    }

    public void actualizarPaint(){
        bloqueActualizadorX +=1;
        getBloqueActualizadorY +=1;
    }

    public static void invertirMatriz(int[][] matriz) {
        int longitud = matriz.length;
        for (int i = 0; i < longitud / 2; i++) {
            int[] temp = matriz[i];
            matriz[i] = matriz[longitud - i - 1];
            matriz[longitud - i - 1] = temp;
        }
    }
    public void cambiarLab(){
        if (nivel == 1){
            nivel =2;
            panelJuego.setBackground(Color.decode("#d7fe0c"));
        }
        else{
            nivel = 1;
            panelJuego.setBackground(Color.decode("#f9df28"));
        }
        invertirMatriz(mapa);
        generaMurosColisionadores();
    }
}