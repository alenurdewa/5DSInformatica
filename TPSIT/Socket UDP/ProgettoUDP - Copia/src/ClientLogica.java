import java.net.*;

public class ClientLogica {

    private DatagramSocket socket;
    private InetAddress indirizzoServer;
    private int portaServer;
    private ClientGUI gui;
    private boolean inEsecuzione = true;

    public ClientLogica(ClientGUI gui) {
        this.gui = gui;
    }


    // Connette il client al server UDP
    public boolean connettiti(String ip, int port, String username) {
        try {
            this.portaServer = port;
            this.indirizzoServer = InetAddress.getByName(ip);
            this.socket = new DatagramSocket();

            // Invia JOIN al server
            invia("JOIN:" + username);

            // Avvia thread che ascolta i messaggi in arrivo
            ascolta();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Disconnette il client chiudendo il socket
    public void disconnettiti() {
        inEsecuzione = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    // Invia un messaggio UDP al server
    public void invia(String msg) {
        try {
            byte[] dati = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(dati, dati.length, indirizzoServer, portaServer);
            socket.send(packet);
        } catch (Exception e) {
        }
    }

    // Thread che riceve messaggi dal server
    private void ascolta() {
        new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (inEsecuzione) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String msg = new String(packet.getData(), 0, packet.getLength());
                    gui.elaboraMessaggio(msg);

                } catch (Exception e) {
                }
            }
        }).start();
    }
}
