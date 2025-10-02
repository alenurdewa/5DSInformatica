//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public synchronized static void prendiCarburante(String nomeAuto, int serbatoio, int carburante) {
        System.out.println("Sono l'auto " + nomeAuto + " e ora ho " +serbatoio+ " litri nel serbatoio");
        System.out.println("Carburante nella stazione: "+carburante+"\n");
    }


    public synchronized static void refillCarburante(String nomeRifornitore) {
        System.out.println("\nSono il rifornitore " + nomeRifornitore + " e ho riempito a 100 la stazione di rifornimento\n");
    }

    public static void main(String[] args) {

        int numeroAuto = 3;
        Semaforo[] semaforo = new Semaforo[3];
        for(int i = 0; i<numeroAuto; i++){
            if (i==0){
                semaforo[i] = new Semaforo(1);
            }else{
                semaforo[i] = new Semaforo(0);
            }
        }

        StazioneRifornimento stazRif = new StazioneRifornimento();
        Rifornitore rifornitore = new Rifornitore("Rifornitore", stazRif);
        Automobile auto1 = new Automobile(stazRif, "KIMI", semaforo[0], semaforo[1]);
        Automobile auto2 = new Automobile(stazRif, "Skibidi", semaforo[1], semaforo[2]);
        Automobile auto3 = new Automobile(stazRif, "Roberto Barra", semaforo[2], semaforo[0]);

        rifornitore.start();

        auto1.start();
        auto2.start();
        auto3.start();

    }
}