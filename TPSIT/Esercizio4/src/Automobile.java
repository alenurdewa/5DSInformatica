public class Automobile extends Thread {

    private int serbatoio;
    private String nomeAuto;
    private Semaforo semaforo1;
    private Semaforo semaforo2;
    private StazioneRifornimento stazRif;

    public Automobile(StazioneRifornimento stazRif, String nomeAuto, Semaforo semaforo1, Semaforo semaforo2) {
        this.serbatoio = 0;
        this.stazRif = stazRif;
        this.nomeAuto = nomeAuto;
        this.semaforo1 = semaforo1;
        this.semaforo2 = semaforo2;
    }

    @Override
    public void run() {
        while (serbatoio < 100) {
            semaforo1.p();
            if (stazRif.getCarburante()<=60){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            stazRif.prendiCarburante();
            serbatoio += 20;
            Main.prendiCarburante(nomeAuto, serbatoio, stazRif.getCarburante());
            semaforo2.v();

        }
    }
}
