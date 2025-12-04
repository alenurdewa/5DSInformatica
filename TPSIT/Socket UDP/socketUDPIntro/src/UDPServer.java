import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        final int PORT = 9876;

        try {
            // Creazione del socket UDP
            DatagramSocket serverSocket = new DatagramSocket(PORT);
            System.out.println("Server UDP in ascolto sulla porta " + PORT + "...");

            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // Preparazione del pacchetto per ricevere dati
                DatagramPacket receivePacket = new DatagramPacket(
                        receiveBuffer,
                        receiveBuffer.length
                );

                // Ricezione del pacchetto (bloccante)
                serverSocket.receive(receivePacket);

                // Estrazione dei dati
                String message = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength()
                );

                // Informazioni sul client
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Messaggio ricevuto da " +
                        clientAddress.getHostAddress() + ":" + clientPort + " - " + message);

                // Preparazione della risposta
                String response = "Server ha ricevuto: " + message;
                byte[] sendBuffer = response.getBytes();

                // Invio della risposta al client
                DatagramPacket sendPacket = new DatagramPacket(
                        sendBuffer,
                        sendBuffer.length,
                        clientAddress,
                        clientPort
                );

                serverSocket.send(sendPacket);
                System.out.println("Risposta inviata al client");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}