public class Rifornitore extends Thread{

    private String nomeRifornitore;
    private StazioneRifornimento stazRif;

    public Rifornitore(String nomeRifornitore, StazioneRifornimento stazRif) {
        this.nomeRifornitore = nomeRifornitore;
        this.stazRif = stazRif;
    }


    @Override
    public void run() {
        for (int i = 0; i<10; i++){

            if (stazRif.getCarburante()<=60){
                stazRif.refillCarburante();
                Main.refillCarburante(nomeRifornitore);
            }
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
