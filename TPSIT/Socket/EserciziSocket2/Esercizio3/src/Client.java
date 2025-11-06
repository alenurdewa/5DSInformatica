import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private DataInputStream dalServer;
    private DataOutputStream versoServer;
    private BufferedReader tastiera;

    public void connetti() {
        try {
            socket = new Socket("localhost", 6769);
            dalServer = new DataInputStream(socket.getInputStream());
            versoServer = new DataOutputStream(socket.getOutputStream());
            tastiera = new BufferedReader(new InputStreamReader(System.in));

            for (int i = 0; i < 5; i++) {
                String domanda = dalServer.readLine();
                System.out.println("\n" + domanda);
                for (int j = 0; j < 3; j++) {
                    System.out.println(dalServer.readLine());
                }

                System.out.print("Risposta (A/B/C): ");
                String risposta = tastiera.readLine();
                versoServer.writeBytes(risposta + "\n");
            }

            System.out.println("\n" + dalServer.readLine());
            String line;
            while ((line = dalServer.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();

        } catch (Exception e) {
            System.out.println("Errore client: " + e.getMessage());
        }
    }


}
