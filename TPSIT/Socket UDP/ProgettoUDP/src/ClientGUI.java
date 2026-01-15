import javax.swing.*;
import java.awt.*;
import java.net.*;

public class ClientGUI extends JFrame {
    private static final String SERVER_ADDR = "127.0.0.1"; // Indirizzo Server (Localhost)
    private static final int SERVER_PORT = 9876;

    private DatagramSocket socket;
    private InetAddress serverIp;
    private String username;
    private boolean running = true;

    // Elementi GUI
    private JTextArea chatArea;
    private JLabel lblHeader;
    private JLabel lblTime;
    private JTextField inputField;

    public ClientGUI() {
        super("Telecronaca Live - Spettatore");

        username = JOptionPane.showInputDialog("Inserisci il tuo Username:");
        if (username == null || username.trim().isEmpty()) System.exit(0);

        initGUI();
        connectToServer();
        startReceiver();
    }

    private void initGUI() {
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Partita
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(new Color(50, 50, 50));

        lblHeader = new JLabel("In attesa di dati partita...", SwingConstants.CENTER);
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

        lblTime = new JLabel("0'", SwingConstants.CENTER);
        lblTime.setForeground(Color.YELLOW);
        lblTime.setFont(new Font("SansSerif", Font.BOLD, 18));

        topPanel.add(lblHeader);
        topPanel.add(lblTime);
        add(topPanel, BorderLayout.NORTH);

        // Area Chat/Cronaca
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Input
        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton btnSend = new JButton("Invia");

        btnSend.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sendMessage() {
        String txt = inputField.getText();
        if (!txt.trim().isEmpty()) {
            sendPacket("CHAT:" + username + ":" + txt);
            inputField.setText("");
        }
    }

    private void connectToServer() {
        try {
            socket = new DatagramSocket();
            serverIp = InetAddress.getByName(SERVER_ADDR);
            sendPacket("JOIN:" + username);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore connessione: " + e.getMessage());
        }
    }

    private void sendPacket(String msg) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverIp, SERVER_PORT);
            socket.send(packet);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void startReceiver() {
        new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (running) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());

                    SwingUtilities.invokeLater(() -> processMessage(msg));
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

    private void processMessage(String msg) {
        if (msg.startsWith("HEADER:")) {
            // Aggiorna intestazione (Squadre, Risultato)
            lblHeader.setText("<html><center>" + msg.substring(7).replaceAll("\n", "<br>") + "</center></html>");
        } else if (msg.startsWith("TIME:")) {
            // Aggiorna Minuto
            lblTime.setText(msg.substring(5));
        } else if (msg.startsWith("EVENTO:")) {
            // Mostra Eventi Partita
            String evento = msg.substring(7);
            chatArea.append(" >>> " + evento + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        } else if (msg.startsWith("CHAT:")) {
            // Mostra Chat Utenti
            String chat = msg.substring(5);
            chatArea.append(chat + "\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        } else if (msg.startsWith("SYS:")) {
            // Messaggi di sistema (es. "Tizio è entrato")
            chatArea.append("[INFO] " + msg.substring(4) + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }
}