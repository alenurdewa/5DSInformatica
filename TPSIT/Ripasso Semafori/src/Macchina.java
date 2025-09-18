public class Macchina  extends  Thread{
    private Semaforo s;
    private int id;

    public Macchina(Semaforo s, int id) {
        this.s = s;
        this.id = id;
    }
//grazie totr <3 miao :3 UWU
    @Override
    public void run() {
        try {
            s.p();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Main.entra(id);
        try {
            Thread.sleep((int)(Math.random()*4)*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Main.esci(id);
        s.v();
    }
}
