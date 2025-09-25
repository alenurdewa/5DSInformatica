public class Main {

    public synchronized static void suonaCampana(String suonoCampana){
        System.out.print(suonoCampana+" ");
    }

    public static void main(String[] args) {
        Semaforo s1 = new Semaforo(1);
        Semaforo s2 = new Semaforo(0);
        Semaforo s3 = new Semaforo(0);


        Thread campana1 = new Campana("din",4,s1, s2);
        Thread campana2 = new Campana("don",4, s2, s3);
        Thread campana3 = new Campana("dan",4, s3, s1);
        campana1.start();
        campana2.start();
        campana3.start();
    }
}