import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int SERVER_PORT = 9876;

        try {
            // Creazione del socket UDP (porta casuale)
            DatagramSocket clientSocket = new DatagramSocket();

            // Configurazione dell'indirizzo del server
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            Scanner scanner = new Scanner(System.in);

            System.out.println("Client UDP pronto. Scrivi 'exit' per uscire.");

            while (true) {
                // Input del messaggio
                System.out.print("Inserisci messaggio: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Invio del messaggio al server
                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        serverAddress,
                        SERVER_PORT
                );

                clientSocket.send(sendPacket);
                System.out.println("Messaggio inviato al server");

                // Ricezione della risposta
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                );

                clientSocket.receive(receivePacket);

                String response = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength()
                );

                System.out.println("Risposta dal server: " + response);
            }

            clientSocket.close();
            scanner.close();
            System.out.println("Client chiuso.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}