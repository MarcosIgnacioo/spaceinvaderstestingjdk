public class EjemploRunnable implements Runnable {
    private volatile int disparoY;
    public EjemploRunnable(int disparoY){
        this.disparoY = disparoY;
    }

    private volatile int contador = 0;

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disparoY+=10;
            System.out.println("Contador: " + contador);
        }
    }

    public int getDisparoY(){
        return disparoY;
    }
}
