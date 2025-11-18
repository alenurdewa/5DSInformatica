import java.io.*;
import java.net.Socket;

public class Client {

    private String nomeServer = "localhost";
    private int porta = 6789;
    private Socket mioSocket;
    private float[] numeri;

    private BufferedReader tastiera;
    private DataOutputStream versoServer;
    private DataInputStream dalServer;

    public void connetti() {
        try {
            System.out.println("Connessione al server " + nomeServer + " sulla porta " + porta + "...");
            mioSocket = new Socket(nomeServer, porta);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            versoServer = new DataOutputStream(mioSocket.getOutputStream());
            dalServer = new DataInputStream(mioSocket.getInputStream());
            numeri = new float[15];
            System.out.println("Connessione riuscita!");
        } catch (Exception e) {
            System.out.println("Errore di connessione: " + e.getMessage());
        }
    }

    public void inserisciNumeri() {
        try {
            System.out.println("\nInserisci 15 numeri decimali (usa il punto per i decimali, es: 3.14):");

            for (int i = 0; i < 15; i++) {
                System.out.print("Numero [" + (i + 1) + "]: ");
                numeri[i] = Float.parseFloat(tastiera.readLine());
                versoServer.writeFloat(numeri[i]);
            }

            System.out.println("\nNumeri inviati al server. Attendo la risposta...");

            // Legge la stringa di risposta dal server
            String risposta = dalServer.readUTF();

            System.out.println("\n--- RISULTATI DAL SERVER ---");
            System.out.println(risposta);
            System.out.println("----------------------------");

            chiudiConnessione();

        } catch (Exception e) {
            System.out.println("Errore durante l'invio dei dati: " + e.getMessage());
        }
    }

    private void chiudiConnessione() {
        try {
            mioSocket.close();
            System.out.println("\nConnessione chiusa correttamente.");
        } catch (IOException e) {
            System.out.println("Errore durante la chiusura: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connetti();
        c.inserisciNumeri();
    }
}
