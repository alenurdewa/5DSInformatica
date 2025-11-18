import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Server {

    private ServerSocket server;
    private Socket client;
    private DataInputStream dalClient;
    private DataOutputStream versoClient;

    public void attendi() {
        try {
            System.out.println("Server avviato, in attesa di connessioni...");
            server = new ServerSocket(6789);
            client = server.accept();
            System.out.println("Connessione stabilita con il client: " + client.getInetAddress());

            dalClient = new DataInputStream(client.getInputStream());
            versoClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println("Errore durante l'attesa: " + e.getMessage());
        }
    }

    public void calcola() {
        try {
            System.out.println("Ricezione dei numeri dal client...");
            float[] numeri = new float[15];

            for (int i = 0; i < 15; i++) {
                numeri[i] = dalClient.readFloat();
                System.out.println("Ricevuto numero [" + (i + 1) + "]: " + numeri[i]);
            }

            float media = calcolaMedia(numeri);
            float mediana = calcolaMediana(numeri);
            float massimo = trovaMassimo(numeri);
            float minimo = trovaMinimo(numeri);

            System.out.println("Calcoli completati:");
            System.out.println("Media = " + media);
            System.out.println("Mediana = " + mediana);
            System.out.println("Massimo = " + massimo);
            System.out.println("Minimo = " + minimo);

            String risultato = String.format(
                    "Statistiche calcolate:\nMedia: %.2f\nMediana: %.2f\nMassimo: %.2f\nMinimo: %.2f",
                    media, mediana, massimo, minimo
            );

            versoClient.writeUTF(risultato);
            System.out.println("Risultati inviati al client.");

            chiudiConnessioni();

        } catch (Exception e) {
            System.out.println("Errore durante il calcolo: " + e.getMessage());
        }
    }

    private float calcolaMedia(float[] numeri) {
        float somma = 0;
        for (float n : numeri) somma += n;
        return somma / numeri.length;
    }

    private float calcolaMediana(float[] numeri) {
        Arrays.sort(numeri);
        int n = numeri.length;
        if (n % 2 == 0) {
            return (numeri[n / 2 - 1] + numeri[n / 2]) / 2;
        } else {
            return numeri[n / 2];
        }
    }

    private float trovaMassimo(float[] numeri) {
        float max = numeri[0];
        for (float n : numeri) if (n > max) max = n;
        return max;
    }

    private float trovaMinimo(float[] numeri) {
        float min = numeri[0];
        for (float n : numeri) if (n < min) min = n;
        return min;
    }

    private void chiudiConnessioni() {
        try {
            dalClient.close();
            versoClient.close();
            client.close();
            server.close();
            System.out.println("Connessioni chiuse correttamente.");
        } catch (IOException e) {
            System.out.println("Errore durante la chiusura: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server s = new Server();
        s.attendi();
        s.calcola();
    }
}
