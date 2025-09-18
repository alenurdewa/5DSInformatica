public class Main {

    private  static int postiLiberi = 3;

    public static synchronized  void entra(int id){
        postiLiberi--;
    }

    public static synchronized  void esci(int id){
        postiLiberi++;
    }


    public static void main(String[] args) {
        System.out.println("Hello, World!");

        Semaforo s = new Semaforo(postiLiberi);

        for (int i = 0; i<10; i++){
            new Macchina(s,i+1).start();
            try {
                Thread.sleep((int)(Math.random()*4)*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}