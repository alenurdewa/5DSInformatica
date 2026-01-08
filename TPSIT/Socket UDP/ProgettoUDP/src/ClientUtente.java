import java.net.*;
import java.util.Scanner;

public class ClientUtente {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 9876;
    private static DatagramSocket socket;
    private static boolean running = true;

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket(); // Porta casuale
            InetAddress serverIp = InetAddress.getByName(SERVER_ADDR);
            Scanner scanner = new Scanner(System.in);

            System.out.print("Inserisci il tuo Username: ");
            String username = scanner.nextLine();

            // Inviamo un pacchetto vuoto o di "Join" per farci registrare dal server
            String joinMsg = "JOIN";
            byte[] joinData = joinMsg.getBytes();
            DatagramPacket joinPacket = new DatagramPacket(joinData, joinData.length, serverIp, SERVER_PORT);
            socket.send(joinPacket);

            System.out.println("Connesso alla telecronaca! Attendi i dati...");

            // --- Thread per Ricevere Messaggi dal Server (Cronaca + Chat) ---
            Thread receiver = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    while (running) {
                        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                        socket.receive(response);
                        String msg = new String(response.getData(), 0, response.getLength());

                        // Stampa semplice: il server ha già formattato tutto (minuti, user, ecc)
                        System.out.println(msg);
                    }
                } catch (Exception e) {
                    if (running) e.printStackTrace();
                }
            });
            receiver.start();

            // --- Loop Principale per Inviare Chat ---
            while (running) {
                // Non mettiamo "System.out.print" qui per non sporcare la chat in arrivo
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    running = false;
                    break;
                }

                // Protocollo: CHAT:Username:Messaggio
                String packetContent = "CHAT:" + username + ":" + input;
                byte[] data = packetContent.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverIp, SERVER_PORT);
                socket.send(sendPacket);
            }

            socket.close();
            scanner.close();
            System.out.println("Client chiuso.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}