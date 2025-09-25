import java.util.Random;

public class Campana extends Thread {

    private String suonoCampana;

    private int numRintocchi;

    private Semaforo s1;
    private Semaforo s2;

    public Campana(String suonoCampana, int numRintocchi, Semaforo s1, Semaforo s2) {
        this.suonoCampana = suonoCampana;
        this.numRintocchi = numRintocchi;
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public void run() {
        for (int i = 0; i< numRintocchi; i++){
            s1.p();
            Random r = new Random();
            Main.suonaCampana(suonoCampana);
            s2.v();
        }

    }
}
