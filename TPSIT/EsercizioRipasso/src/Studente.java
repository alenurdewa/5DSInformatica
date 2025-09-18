public class Studente extends Thread{

    private Semaforo s;
    private int id;

    public Studente (Semaforo s, int id) {

        this.s = s;
        this.id = id;
    }

    public void run () {

        try {
            s.p();
            Main.entra(id);

            sleep((int)(Math.random()*4)*100);

            Main.esci(id);
            s.v();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
