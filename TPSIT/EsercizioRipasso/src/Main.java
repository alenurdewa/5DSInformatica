public class Main {

    private static int posti = 5;
    private static int posti_occupati = 0;

    public static synchronized void entra (int id) {
        posti_occupati ++;
        System.out.println("Studente " + id + " si è seduto. Seduti: " + posti_occupati);
    }

    public static synchronized void esci (int id) {
        posti_occupati --;
        System.out.println("Studente " + id + " si è alzato. Seduti: " + posti_occupati);
    }


    public static void main (String[] args) {

        Semaforo s = new Semaforo(posti);

        for (int i = 0; i < 10; i ++) {
            new Studente(s, (i + 1)).start();
        }
    }
}