import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dalClient;
    private DataOutputStream versoClient;

    private String[] domande = {
            "Qual è la capitale d'Italia?",
            "Chi ha scritto 'La Divina Commedia'?",
            "Qual è il pianeta più grande del sistema solare?",
            "In che anno è iniziata la Seconda Guerra Mondiale?",
            "Qual è l'elemento chimico con simbolo O?"
    };

    private String[][] opzioni = {
            {"A) Roma", "B) Milano", "C) Napoli"},
            {"A) Dante Alighieri", "B) Alessandro Manzoni", "C) Giovanni Boccaccio"},
            {"A) Marte", "B) Giove", "C) Saturno"},
            {"A) 1939", "B) 1915", "C) 1945"},
            {"A) Oro", "B) Ossigeno", "C) Ozono"}
    };

    private char[] risposteCorrette = {'A', 'A', 'B', 'A', 'B'};

    public void avvia() {
        try {
            serverSocket = new ServerSocket(6769);
            System.out.println("Server in attesa di connessione...");
            socket = serverSocket.accept();
            System.out.println("Client connesso.");

            dalClient = new DataInputStream(socket.getInputStream());
            versoClient = new DataOutputStream(socket.getOutputStream());

            int punteggio = 0;
            StringBuilder resoconto = new StringBuilder();

            for (int i = 0; i < domande.length; i++) {
                versoClient.writeBytes(domande[i] + "\n");
                for (int j = 0; j < 3; j++) {
                    versoClient.writeBytes(opzioni[i][j] + "\n");
                }

                String risposta = dalClient.readLine();
                if (risposta != null && risposta.equalsIgnoreCase(String.valueOf(risposteCorrette[i]))) {
                    punteggio++;
                    resoconto.append("Domanda ").append(i + 1).append(": Corretta\n");
                } else {
                    resoconto.append("Domanda ").append(i + 1).append(": Errata (Corretto: ").append(risposteCorrette[i]).append(")\n");
                }
            }

            versoClient.writeBytes("Punteggio finale: " + punteggio + "/5\n");
            versoClient.writeBytes(resoconto.toString());
            socket.close();
            serverSocket.close();

        } catch (Exception e) {
            System.out.println("Errore server: " + e.getMessage());
        }
    }

}
