public class StazioneRifornimento {

    private int carburante;


    public StazioneRifornimento() {
        this.carburante = 100;
    }

    public void prendiCarburante() {
        if (carburante<=60){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        carburante -= 20;

    }

    public int getCarburante() {
        return carburante;
    }

    public synchronized void refillCarburante() {
        carburante = 100;
    }
}
