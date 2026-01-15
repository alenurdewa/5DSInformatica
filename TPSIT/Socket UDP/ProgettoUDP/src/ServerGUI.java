import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.*;

public class ServerGUI extends JFrame {
    // Configurazione Rete
    private static final int PORT = 9876;
    private DatagramSocket socket;
    private Set<SocketAddress> clientList = Collections.synchronizedSet(new HashSet<>());
    private boolean serverRunning = true;

    // Logica Partita
    private Partita partita;
    private Campionato campionato;
    private String nomeOperatore;
    private int minuto = 0;
    private boolean inCorso = false;
    private int tempoDiGioco = 1; // 1 = Primo Tempo, 2 = Secondo Tempo

    // Elementi Grafici
    private JTextArea logArea;
    private JLabel lblPunteggio;
    private JLabel lblMinuto;
    private JPanel panelControlli;
    private JButton btnStart;

    public ServerGUI() {
        super("Server Telecronaca - Pannello Operatore");
        campionato = new Campionato();

        // --- STEP 1: LOGIN OPERATORE ---
        if (!effettuaLogin()) {
            System.exit(0);
        }

        // --- STEP 2: SETUP PARTITA ---
        if (!configuraPartita()) {
            System.exit(0);
        }

        // --- STEP 3: AVVIO INTERFACCIA E THREAD ---
        initMainInterface();
        startServerThreads();
    }

    private boolean effettuaLogin() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        loginPanel.add(new JLabel("Username Operatore:"));
        loginPanel.add(userField);
        loginPanel.add(new JLabel("Password (mondoCalcio):"));
        loginPanel.add(passField);

        int result = JOptionPane.showConfirmDialog(null, loginPanel, "Login Operatore", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String pass = new String(passField.getPassword());
            if (!pass.equals("mondoCalcio")) {
                JOptionPane.showMessageDialog(null, "Password Errata!");
                return false;
            }
            nomeOperatore = userField.getText();
            if(nomeOperatore.isEmpty()) nomeOperatore = "Operatore";
            return true;
        }
        return false;
    }

    private boolean configuraPartita() {
        JPanel setupPanel = new JPanel(new GridLayout(5, 2));
        JComboBox<Squadra> comboCasa = new JComboBox<>(new Vector<>(campionato.getSquadre()));
        JComboBox<Squadra> comboOspite = new JComboBox<>(new Vector<>(campionato.getSquadre()));
        JTextField txtCitta = new JTextField("Milano");
        JTextField txtCampo = new JTextField("San Siro");
        JTextField txtOrario = new JTextField("20:45");

        setupPanel.add(new JLabel("Squadra Casa:")); setupPanel.add(comboCasa);
        setupPanel.add(new JLabel("Squadra Ospite:")); setupPanel.add(comboOspite);
        setupPanel.add(new JLabel("Città:")); setupPanel.add(txtCitta);
        setupPanel.add(new JLabel("Campo:")); setupPanel.add(txtCampo);
        setupPanel.add(new JLabel("Orario:")); setupPanel.add(txtOrario);

        int result = JOptionPane.showConfirmDialog(null, setupPanel, "Configurazione Partita", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            partita = new Partita((Squadra)comboCasa.getSelectedItem(), (Squadra)comboOspite.getSelectedItem(),
                    txtCitta.getText(), txtCampo.getText(), txtOrario.getText());
            return true;
        }
        return false;
    }

    private void initMainInterface() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(Color.LIGHT_GRAY);
        lblPunteggio = new JLabel(partita.getIntestazione(), SwingConstants.CENTER);
        lblPunteggio.setFont(new Font("Arial", Font.BOLD, 16));
        lblMinuto = new JLabel("Minuto: 0' - IN ATTESA DI AVVIO", SwingConstants.CENTER);
        header.add(lblPunteggio);
        header.add(lblMinuto);
        add(header, BorderLayout.NORTH);

        // Log Centrale
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        // Pannello Controlli
        panelControlli = new JPanel(new GridLayout(2, 4, 5, 5));

        JButton btnGolCasa = new JButton("GOL " + partita.getSquadraCasa().getNome());
        JButton btnGolOspite = new JButton("GOL " + partita.getSquadraOspite().getNome());
        JButton btnGiallo = new JButton("Cartellino Giallo");
        JButton btnRosso = new JButton("Cartellino Rosso");
        JButton btnRigore = new JButton("Rigore");
        JButton btnMsg = new JButton("Invia Messaggio");
        btnStart = new JButton("AVVIA PARTITA");
        btnStart.setBackground(Color.GREEN);

        // Listener Pulsanti
        btnGolCasa.addActionListener(e -> gestioneGol(partita.getSquadraCasa(), true));
        btnGolOspite.addActionListener(e -> gestioneGol(partita.getSquadraOspite(), false));
        btnGiallo.addActionListener(e -> gestioneCartellino("Giallo"));
        btnRosso.addActionListener(e -> gestioneCartellino("Rosso"));
        btnRigore.addActionListener(e -> inviaEvento("CALCIO DI RIGORE!"));

        btnMsg.addActionListener(e -> {
            String msg = JOptionPane.showInputDialog("Messaggio operatore:");
            if(msg != null && !msg.isEmpty()) broadcast("CHAT:" + nomeOperatore + " (OP):" + msg);
        });

        btnStart.addActionListener(e -> {
            if(!inCorso) {
                inCorso = true;
                btnStart.setEnabled(false);
                broadcast("SYS:Partita Iniziata! Benvenuti da " + nomeOperatore);
                new Thread(this::gestisciTimer).start();
            }
        });

        panelControlli.add(btnStart);
        panelControlli.add(btnGolCasa);
        panelControlli.add(btnGolOspite);
        panelControlli.add(btnGiallo);
        panelControlli.add(btnRosso);
        panelControlli.add(btnRigore);
        panelControlli.add(btnMsg);

        add(panelControlli, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void gestioneGol(Squadra s, boolean isCasa) {
        // Chiede chi ha segnato selezionandolo dalla rosa della squadra
        Giocatore marcatore = (Giocatore) JOptionPane.showInputDialog(this,
                "Chi ha segnato?", "Marcatore", JOptionPane.QUESTION_MESSAGE, null,
                s.getRosa().toArray(), s.getRosa().get(0));

        String nomeMarcatore = (marcatore != null) ? marcatore.getNome() : "Sconosciuto";

        if(isCasa) partita.segnaCasa(); else partita.segnaOspite();

        lblPunteggio.setText(partita.getIntestazione());
        inviaEvento("GOOOL! " + s.getNome() + "! Ha segnato " + nomeMarcatore + "!");

        // Aggiorna header su tutti i client
        broadcast("HEADER:" + partita.getIntestazione());
    }

    private void gestioneCartellino(String colore) {
        String chi = JOptionPane.showInputDialog("A chi?");
        inviaEvento("Cartellino " + colore + " per " + (chi != null ? chi : "sconosciuto"));
    }

    private void inviaEvento(String testo) {
        String msg = minuto + "' - " + testo;
        appendLog(msg);
        broadcast("EVENTO:" + msg);
    }

    // --- LOGICA TIMER AUTOMATICO ---
    private void gestisciTimer() {
        try {
            // Primo tempo
            while (inCorso && minuto < 45) {
                Thread.sleep(1000); // 1 secondo reale = 1 minuto simulato
                minuto++;
                aggiornaMinutoGUI();
            }

            // Fine primo tempo
            inviaEvento("FINE PRIMO TEMPO");
            broadcast("SYS:--- Intervallo ---");
            Thread.sleep(3000); // Pausa intervallo (3 secondi simulati)

            // Secondo tempo
            inviaEvento("INIZIO SECONDO TEMPO");
            tempoDiGioco = 2;
            minuto = 45;

            while (inCorso && minuto < 90) {
                Thread.sleep(1000);
                minuto++;
                aggiornaMinutoGUI();
            }

            inviaEvento("FISCHIO FINALE! La partita è terminata.");
            broadcast("SYS:--- PARTITA TERMINATA ---");
            inCorso = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void aggiornaMinutoGUI() {
        SwingUtilities.invokeLater(() -> lblMinuto.setText("Minuto: " + minuto + "' (" + (tempoDiGioco==1?"1T":"2T") + ")"));
        broadcast("TIME:" + minuto + "'");
    }

    // --- RETE UDP ---
    private void startServerThreads() {
        try {
            socket = new DatagramSocket(PORT);
            appendLog("Server avviato su porta " + PORT);

            new Thread(() -> {
                byte[] buffer = new byte[1024];
                while (serverRunning) {
                    try {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String received = new String(packet.getData(), 0, packet.getLength());
                        SocketAddress clientAddr = packet.getSocketAddress();

                        if (received.startsWith("JOIN:")) {
                            String nomeClient = received.split(":")[1];
                            if (!clientList.contains(clientAddr)) {
                                clientList.add(clientAddr);
                                appendLog("Nuovo spettatore: " + nomeClient);
                                broadcast("SYS:" + nomeClient + " è entrato nella sessione.");
                                // Invia info iniziali SOLO al nuovo arrivato
                                sendTo("HEADER:" + partita.getIntestazione(), clientAddr);
                            }
                        } else if (received.startsWith("CHAT:")) {
                            String[] parts = received.split(":", 3);
                            String user = parts[1];
                            String msg = parts[2];
                            broadcast("CHAT:" + user + ": " + msg);
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }).start();

        } catch (SocketException e) { e.printStackTrace(); }
    }

    private void broadcast(String msg) {
        synchronized(clientList) {
            for (SocketAddress dest : clientList) sendTo(msg, dest);
        }
        // Log locale (pulendo i tag di protocollo per leggibilità)
        String clean = msg.replaceAll("^(CHAT:|SYS:|EVENTO:)", "");
        if (!msg.startsWith("TIME:") && !msg.startsWith("HEADER:")) {
            appendLog(clean);
        }
    }

    private void sendTo(String msg, SocketAddress dest) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, dest);
            socket.send(packet);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void appendLog(String txt) {
        SwingUtilities.invokeLater(() -> logArea.append(txt + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerGUI::new);
    }
}