import javax.swing.*;
import java.awt.*;

public class ServerGUI extends JFrame {

    private ServerLogica logica;

    private JTextArea areaLog;
    private JTextArea areaChat;

    private JLabel lblHeader;
    private JLabel lblTempo;

    private Campionato campionato;
    private Partita partitaCorrente;
    private String nomeOperatore;
    private int portServer = 9876;

    // BOTTONI
    private JButton btnStart;
    private JButton btnSecondoTempo;
    private JButton btnStop;
    private JButton btnGolCasa;
    private JButton btnGolOspite;
    private JButton btnGiallo;
    private JButton btnRosso;
    private JButton btnEventoLibero;
    private JButton btnMsg;

    public ServerGUI() {
        super("Server Telecronaca");
        campionato = new Campionato();

        // Login operatore
        if (!effettuaLogin()) System.exit(0);

        // Configura partita (scegli squadre e porta)
        if (!configuraPartita()) System.exit(0);

        initGUI();

        // Avvia logica server
        logica = new ServerLogica(this, portServer, nomeOperatore);
    }

    private void initGUI() {
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER
        JPanel panelNord = new JPanel(new GridLayout(3, 1));
        panelNord.setBackground(Color.LIGHT_GRAY);

        lblHeader = new JLabel(partitaCorrente.getIntestazione(), SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));

        lblTempo = new JLabel("0'", SwingConstants.CENTER);
        lblTempo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblInfo = new JLabel("Operatore: " + nomeOperatore + " | Porta: " + portServer, SwingConstants.CENTER);

        panelNord.add(lblHeader);
        panelNord.add(lblTempo);
        panelNord.add(lblInfo);

        add(panelNord, BorderLayout.NORTH);

        // LOG + CHAT
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 5, 5));

        areaLog = new JTextArea();
        areaLog.setEditable(false);

        areaChat = new JTextArea();
        areaChat.setEditable(false);

        panelCentro.add(new JScrollPane(areaLog));
        panelCentro.add(new JScrollPane(areaChat));

        add(panelCentro, BorderLayout.CENTER);

        // BOTTONI
        JPanel panelSud = new JPanel(new GridLayout(3, 3, 5, 5));

        btnStart = new JButton("AVVIA PARTITA");
        btnSecondoTempo = new JButton("AVVIA SECONDO TEMPO");
        btnStop = new JButton("TERMINA PARTITA");

        btnGolCasa = new JButton("GOL " + partitaCorrente.getSquadraCasa().getNome());
        btnGolOspite = new JButton("GOL " + partitaCorrente.getSquadraOspite().getNome());

        btnGiallo = new JButton("Cartellino Giallo");
        btnRosso = new JButton("Cartellino Rosso");
        btnEventoLibero = new JButton("Evento Libero");
        btnMsg = new JButton("Invia Messaggio Chat");

        // Bottoni disattivi inizialmente
        setEventButtonsEnabled(false);
        btnSecondoTempo.setEnabled(false);
        btnStop.setEnabled(false);

        // Azioni bottoni
        btnStart.addActionListener(e -> {
            logica.avviaPartitaTimer();
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
            appendLog("--- PARTITA AVVIATA (Timer partito) ---");
            setEventButtonsEnabled(true);
        });

        btnSecondoTempo.addActionListener(e -> {
            logica.avviaSecondoTempo();
            btnSecondoTempo.setEnabled(false);
            setEventButtonsEnabled(true);
        });

        btnStop.addActionListener(e -> {
            logica.terminaPartita();
            btnStop.setEnabled(false);
            setEventButtonsEnabled(false);
        });

        btnGolCasa.addActionListener(e -> gestisciGol(partitaCorrente.getSquadraCasa(), true));
        btnGolOspite.addActionListener(e -> gestisciGol(partitaCorrente.getSquadraOspite(), false));

        btnGiallo.addActionListener(e -> gestisciCartellino("Cartellino Giallo"));
        btnRosso.addActionListener(e -> gestisciCartellino("Cartellino Rosso"));

        btnEventoLibero.addActionListener(e -> {
            String msg = JOptionPane.showInputDialog("Inserisci evento:");
            if (msg != null) logica.inviaEvento(msg);
        });

        btnMsg.addActionListener(e -> {
            String msg = JOptionPane.showInputDialog("Messaggio:");
            if (msg != null) logica.broadcast("CHAT:" + logica.getMinuto() + "' - " + nomeOperatore + " (OP): " + msg);
        });

        panelSud.add(btnStart);
        panelSud.add(btnSecondoTempo);
        panelSud.add(btnStop);

        panelSud.add(btnGolCasa);
        panelSud.add(btnGolOspite);
        panelSud.add(btnGiallo);

        panelSud.add(btnRosso);
        panelSud.add(btnEventoLibero);
        panelSud.add(btnMsg);

        add(panelSud, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Abilita/disabilita bottoni evento
    private void setEventButtonsEnabled(boolean enabled) {
        btnGolCasa.setEnabled(enabled);
        btnGolOspite.setEnabled(enabled);
        btnGiallo.setEnabled(enabled);
        btnRosso.setEnabled(enabled);
        btnEventoLibero.setEnabled(enabled);
        btnMsg.setEnabled(enabled);
    }

    // Login operatore
    private boolean effettuaLogin() {
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        Object[] message = {
                "Username Operatore:", userField,
                "Password (mondoCalcio):", passField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login Server", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String pass = new String(passField.getPassword());

            if (pass.equals("mondoCalcio")) {
                nomeOperatore = userField.getText();
                if (nomeOperatore.isEmpty()) nomeOperatore = "Admin";
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Password Errata!");
            }
        }
        return false;
    }

    // Configura la partita scegliendo le squadre e la porta
    private boolean configuraPartita() {
        Squadra[] arraySquadre = campionato.getSquadre();
        JComboBox<Squadra> comboCasa = new JComboBox<>(arraySquadre);
        JComboBox<Squadra> comboOspite = new JComboBox<>(arraySquadre);

        JTextField txtPorta = new JTextField("9876");
        JTextField txtCitta = new JTextField("Milano");

        Object[] message = {
                "Squadra Casa:", comboCasa,
                "Squadra Ospite:", comboOspite,
                "Porta Server (es. 9876, 9877):", txtPorta,
                "Città:", txtCitta
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Configura Partita", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Squadra c = (Squadra) comboCasa.getSelectedItem();
            Squadra o = (Squadra) comboOspite.getSelectedItem();

            try {
                portServer = Integer.parseInt(txtPorta.getText());
            } catch (NumberFormatException e) {
                portServer = 9876;
            }

            if (c.getNome().equals(o.getNome())) {
                JOptionPane.showMessageDialog(null, "Le squadre devono essere diverse!");
                return configuraPartita();
            }

            partitaCorrente = new Partita(c, o, txtCitta.getText(), "Stadio", "20:45");
            return true;
        }
        return false;
    }

    // Gestisce gol: scegli il marcatore e invia evento
    private void gestisciGol(Squadra s, boolean isCasa) {
        Giocatore[] rosa = s.getRosaEffettiva();

        Giocatore marcatore = (Giocatore) JOptionPane.showInputDialog(
                this,
                "Chi ha segnato?",
                "GOL",
                JOptionPane.QUESTION_MESSAGE,
                null,
                rosa,
                rosa[0]
        );

        if (marcatore != null) {
            if (isCasa) partitaCorrente.segnaCasa();
            else partitaCorrente.segnaOspite();

            lblHeader.setText(partitaCorrente.getIntestazione());

            String evento = "GOOOL! " + s.getNome() + "! Ha segnato " + marcatore.getNome();
            logica.inviaEvento(evento);
            logica.broadcast("HEADER:" + partitaCorrente.getIntestazione());
        }
    }

    // Gestisce cartellini: scegli squadra e giocatore
    private void gestisciCartellino(String tipo) {
        Squadra[] squadre = { partitaCorrente.getSquadraCasa(), partitaCorrente.getSquadraOspite() };

        Squadra sqScelta = (Squadra) JOptionPane.showInputDialog(
                this,
                "Squadra?",
                tipo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                squadre,
                squadre[0]
        );

        if (sqScelta == null) return;

        Giocatore[] rosa = sqScelta.getRosaEffettiva();

        Giocatore giuocoScelto = (Giocatore) JOptionPane.showInputDialog(
                this,
                "Giocatore?",
                tipo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                rosa,
                rosa[0]
        );

        if (giuocoScelto != null) {
            logica.inviaEvento(tipo + " per " + giuocoScelto.getNome() + " (" + sqScelta.getNome() + ")");
        }
    }

    // Metodi per aggiornare la GUI
    public void appendLog(String txt) {
        SwingUtilities.invokeLater(() -> areaLog.append(txt + "\n"));
    }

    public void appendChat(String txt) {
        SwingUtilities.invokeLater(() -> areaChat.append(txt + "\n"));
    }

    public void setTempo(String tempo) {
        SwingUtilities.invokeLater(() -> lblTempo.setText(tempo));
    }

    public void setBottoniSecondoTempo(boolean enabled) {
        SwingUtilities.invokeLater(() -> btnSecondoTempo.setEnabled(enabled));
    }

    public void setBottoniEventi(boolean enabled) {
        SwingUtilities.invokeLater(() -> setEventButtonsEnabled(enabled));
    }

    public Partita getPartita() {
        return partitaCorrente;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ServerGUI::new);
    }
}
