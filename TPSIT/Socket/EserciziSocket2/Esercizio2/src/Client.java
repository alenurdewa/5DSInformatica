import java.io.*;
import java.net.*;

public class Client {

    private String nomeServer = "localhost";
    private int porta = 6789;
    private Socket mioSocket;
    private DataOutputStream versoServer;
    private DataInputStream dalServer;
    private BufferedReader tastiera;

    public void connetti() {
        try {
            System.out.println("Connessione al server " + nomeServer + " sulla porta " + porta + "...");
            mioSocket = new Socket(nomeServer, porta);
            versoServer = new DataOutputStream(mioSocket.getOutputStream());
            dalServer = new DataInputStream(mioSocket.getInputStream());
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connessione riuscita!");
        } catch (Exception e) {
            System.out.println("Errore di connessione: " + e.getMessage());
        }
    }

    public void inviaStringhe() {
        try {
            System.out.println("\nInserisci 5 stringhe da inviare al server:");

            for (int i = 0; i < 5; i++) {
                System.out.print("Stringa [" + (i + 1) + "]: ");
                String testo = tastiera.readLine();
                versoServer.writeUTF(testo);
            }

            System.out.println("\nTutte le stringhe sono state inviate. Attendo il report...");

            String report = dalServer.readUTF();
            System.out.println("\n--- REPORT DAL SERVER ---");
            System.out.println(report);
            System.out.println("--------------------------");

            chiudiConnessione();

        } catch (Exception e) {
            System.out.println("Errore durante l'invio delle stringhe: " + e.getMessage());
        }
    }

    private void chiudiConnessione() {
        try {
            mioSocket.close();
            System.out.println("Connessione chiusa correttamente.");
        } catch (IOException e) {
            System.out.println("Errore durante la chiusura: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.connetti();
        c.inviaStringhe();
    }
}
