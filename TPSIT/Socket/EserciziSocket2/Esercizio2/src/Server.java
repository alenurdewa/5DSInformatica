import java.io.*;
import java.net.*;

public class Server {

    private ServerSocket server;
    private Socket client;
    private DataInputStream dalClient;
    private DataOutputStream versoClient;

    public void attendi() {
        try {
            System.out.println("Server avviato. In attesa di connessioni...");
            server = new ServerSocket(6789);
            client = server.accept();
            System.out.println("Connessione stabilita con: " + client.getInetAddress());

            dalClient = new DataInputStream(client.getInputStream());
            versoClient = new DataOutputStream(client.getOutputStream());

        } catch (Exception e) {
            System.out.println("Errore durante l'attesa del client: " + e.getMessage());
        }
    }

    public void analizzaStringhe() {
        try {
            System.out.println("\n--- Inizio ricezione delle stringhe dal client ---");
            StringBuilder report = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                String testo = dalClient.readUTF();
                System.out.println("Stringa ricevuta [" + (i + 1) + "]: " + testo);

                int lunghezza = testo.length();
                int vocali = contaVocali(testo);
                int consonanti = contaConsonanti(testo);
                boolean palindromo = isPalindromo(testo);

                report.append("Stringa n°").append(i + 1).append(": ").append(testo).append("\n");
                report.append(" - Lunghezza: ").append(lunghezza).append("\n");
                report.append(" - Vocali: ").append(vocali).append("\n");
                report.append(" - Consonanti: ").append(consonanti).append("\n");
                report.append(" - Palindromo: ").append(palindromo ? "Sì" : "No").append("\n\n");
            }

            System.out.println("\nAnalisi completata. Invio del report al client...");
            versoClient.writeUTF(report.toString());

            chiudiConnessioni();

        } catch (Exception e) {
            System.out.println("Errore durante l'elaborazione: " + e.getMessage());
        }
    }

    private int contaVocali(String s) {
        int count = 0;
        s = s.toLowerCase();
        for (char c : s.toCharArray()) {
            if ("aeiou".indexOf(c) != -1)
                count++;
        }
        return count;
    }

    private int contaConsonanti(String s) {
        int count = 0;
        s = s.toLowerCase();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c) && "aeiou".indexOf(c) == -1)
                count++;
        }
        return count;
    }

    private boolean isPalindromo(String s) {
        String pulita = s.replaceAll("[^a-zA-Z]", "").toLowerCase();
        String inversa = new StringBuilder(pulita).reverse().toString();
        return pulita.equals(inversa);
    }

    private void chiudiConnessioni() {
        try {
            dalClient.close();
            versoClient.close();
            client.close();
            server.close();
            System.out.println("Connessioni chiuse correttamente.");
        } catch (IOException e) {
            System.out.println("Errore durante la chiusura delle connessioni: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.attendi();
        s.analizzaStringhe();
    }
}
