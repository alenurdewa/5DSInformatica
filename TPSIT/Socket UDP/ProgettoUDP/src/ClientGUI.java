import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {


    private ClientLogica logica;
    private JTextArea areaChat;
    private JLabel lblHeader;
    private JLabel lblTempo;
    private String username;

    public ClientGUI() {
        super("Telecronaca Live - Spettatore");

        // Dialog iniziale: IP, porta, username
        JTextField ipField = new JTextField("127.0.0.1");
        JTextField portField = new JTextField("9876");
        JTextField userField = new JTextField();

        Object[] message = {
                "IP Server:", ipField,
                "Porta Partita (es. 9876):", portField,
                "Tuo Username:", userField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Entra in Partita", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION)
            System.exit(0);

        username = userField.getText();
        int port = Integer.parseInt(portField.getText());

        initGUI();

        // Inizializza la logica client e si connette
        logica = new ClientLogica(this);
        boolean connesso = logica.connettiti(ipField.getText(), port, username);
        if (!connesso) {
            JOptionPane.showMessageDialog(this, "Errore connessione!");
            System.exit(0);
        }
    }

    private void initGUI() {
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel pnlNord = new JPanel(new GridLayout(2, 1));
        pnlNord.setBackground(Color.DARK_GRAY);

        lblHeader = new JLabel("In attesa dati...", SwingConstants.CENTER);
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel pnlTime = new JPanel(new BorderLayout());
        pnlTime.setBackground(Color.DARK_GRAY);

        lblTempo = new JLabel("0'", SwingConstants.CENTER);
        lblTempo.setForeground(Color.YELLOW);
        lblTempo.setFont(new Font("Arial", Font.BOLD, 20));

        // Bottone "Cambia Partita" -> disconnette e riavvia GUI
        JButton btnEsci = new JButton("Cambia Partita");
        btnEsci.addActionListener(e -> {
            logica.disconnettiti();
            dispose();
            new ClientGUI();
        });

        pnlTime.add(lblTempo, BorderLayout.CENTER);
        pnlTime.add(btnEsci, BorderLayout.EAST);

        pnlNord.add(lblHeader);
        pnlNord.add(pnlTime);

        add(pnlNord, BorderLayout.NORTH);

        // Area chat (centrale)
        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(areaChat), BorderLayout.CENTER);

        // Input chat (sud)
        JPanel pnlSud = new JPanel(new BorderLayout());
        JTextField txtInput = new JTextField();
        JButton btnInvia = new JButton("Invia");

        ActionListener sendAction = e -> {
            if (!txtInput.getText().isEmpty()) {
                // Invio messaggio al server
                logica.invia("CHAT:" + username + ": " + txtInput.getText());
                txtInput.setText("");
            }
        };

        btnInvia.addActionListener(sendAction);
        txtInput.addActionListener(sendAction);

        pnlSud.add(txtInput, BorderLayout.CENTER);
        pnlSud.add(btnInvia, BorderLayout.EAST);

        add(pnlSud, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Riceve messaggi dal server e li elabora in base al prefisso
    public void elaboraMessaggio(String msg) {
        SwingUtilities.invokeLater(() -> {
            if (msg.startsWith("TIME:")) {
                lblTempo.setText(msg.substring(5));
            } else if (msg.startsWith("HEADER:")) {
                lblHeader.setText(msg.substring(7));
            } else if (msg.startsWith("SYS:")) {
                areaChat.append("[INFO] " + msg.substring(4) + "\n");
                areaChat.setCaretPosition(areaChat.getDocument().getLength());
            } else if (msg.startsWith("EVENTO:")) {
                areaChat.append(">>> " + msg.substring(7).toUpperCase() + " <<<\n");
                areaChat.setCaretPosition(areaChat.getDocument().getLength());
            } else if (msg.startsWith("CHAT:")) {
                areaChat.append(msg.substring(5) + "\n");
                areaChat.setCaretPosition(areaChat.getDocument().getLength());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }
}
