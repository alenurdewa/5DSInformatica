import java.net.*;

public class ServerLogica {

    private DatagramSocket socket;
    private ServerGUI gui;

    // Lista di client connessi (IP+porta)
    private SocketAddress[] clients = new SocketAddress[100];
    private int numClients = 0;

    private String nomeOperatore;
    private int minuto = 0;
    private boolean inCorso = false;
    private boolean secondoTempo = false;

    public ServerLogica(ServerGUI gui, int port, String nomeOp) {
        this.gui = gui;
        this.nomeOperatore = nomeOp;

        try {
            // Apri socket UDP sulla porta scelta
            socket = new DatagramSocket(port);
            gui.appendLog("SERVER AVVIATO SU PORTA: " + port);
            startReceiver(); // inizia ad ascoltare i client
        } catch (Exception e) {
            gui.appendLog("Errore avvio server: " + e.getMessage());
        }
    }

    // Thread che riceve messaggi dai client
    private void startReceiver() {
        new Thread(() -> {
            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String msg = new String(packet.getData(), 0, packet.getLength());
                    SocketAddress mittente = packet.getSocketAddress();

                    if (msg.startsWith("JOIN:")) {
                        String user = msg.split(":")[1];
                        aggiungiClient(mittente, user);

                    } else if (msg.startsWith("CHAT:")) {
                        String testoChat = msg.substring(5);
                        gui.appendChat(testoChat);
                        broadcast(msg);
                    }

                } catch (Exception e) {
                    // socket chiuso -> termina thread
                    break;
                }
            }
        }).start();
    }

    // Aggiunge un client alla lista (se non già presente)
    private void aggiungiClient(SocketAddress nuovo, String user) {
        boolean esiste = false;

        for (int i = 0; i < numClients && !esiste; i++) {
            if (clients[i].equals(nuovo)) {
                esiste = true;
            }
        }

        if (!esiste && numClients < clients.length) {
            clients[numClients] = nuovo;
            numClients++;

            String logMsg = user + " è entrato nella sessione.";
            gui.appendLog(logMsg);

            // Notifica tutti i client del nuovo utente
            broadcast("SYS:" + logMsg);

            // Invia solo al nuovo client header e tempo attuali
            inviaA("HEADER:" + gui.getPartita().getIntestazione(), nuovo);
            inviaA("TIME:" + minuto + "'", nuovo);
        }
    }

    // Avvia il timer del primo tempo
    public void avviaPartitaTimer() {
        if (inCorso) return;
        inCorso = true;

        new Thread(() -> {
            try {
                broadcast("SYS:Partita Iniziata! Vi accompagna: " + nomeOperatore);
                inviaEvento("FISCHIO D'INIZIO!");

                while (minuto < 45) {
                    Thread.sleep(1000);
                    minuto++;
                    broadcast("TIME:" + minuto + "'");
                    gui.setTempo(minuto + "'");

                    if (minuto == 45) {
                        gui.setBottoniEventi(false);
                        gui.setBottoniSecondoTempo(true);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Avvia il timer del secondo tempo
    public void avviaSecondoTempo() {
        if (!inCorso || secondoTempo) return;
        secondoTempo = true;

        new Thread(() -> {
            try {
                inviaEvento("INIZIO SECONDO TEMPO");
                broadcast("SYS:Si riparte!");

                while (minuto < 90) {
                    Thread.sleep(1000);
                    minuto++;
                    broadcast("TIME:" + minuto + "'");
                    gui.setTempo(minuto + "'");

                    if (minuto == 90) {
                        inviaEvento("FISCHIO FINALE!");
                        broadcast("SYS:--- PARTITA TERMINATA ---");
                        inCorso = false;
                        gui.setBottoniEventi(false);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Termina partita manualmente
    public void terminaPartita() {
        inCorso = false;
        broadcast("SYS:Partita terminata dall'operatore.");
        gui.setBottoniEventi(false);
        gui.setBottoniSecondoTempo(false);
    }

    // Invia un evento (gol, cartellino, ecc.)
    public void inviaEvento(String testo) {
        gui.appendLog(minuto + "' - " + testo);
        broadcast("EVENTO:" + testo);
    }

    // Broadcast: invia il messaggio a tutti i client connessi
    public void broadcast(String msg) {
        for (int i = 0; i < numClients; i++) {
            inviaA(msg, clients[i]);
        }
    }

    // Invia un messaggio UDP ad un client specifico
    private void inviaA(String msg, SocketAddress dest) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, dest);
            socket.send(packet);
        } catch (Exception e) {}
    }

    public int getMinuto() {
        return minuto;
    }
}
