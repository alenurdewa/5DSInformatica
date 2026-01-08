import java.net.*;
import java.util.*;
import java.io.IOException;

public class ServerTelecronaca {
    private static final int PORT = 9876;
    private static Set<SocketAddress> clientList = Collections.synchronizedSet(new HashSet<>());
    private static DatagramSocket socket;
    private static Partita partita;
    private static int minutoCorrente = 0;
    private static boolean partitaInCorso = false;

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket(PORT);
            System.out.println("SERVER TELECRONACA AVVIATO sulla porta " + PORT);
            Scanner scanner = new Scanner(System.in);

            // --- FASE 1: Setup Iniziale ---
            int sceltaIniziale;
            do{

                System.out.println("Seleziona opzione:");
                System.out.println("1. Crea nuova partita");
                System.out.println("2. Entra in partita esistente (Simulato - avvia comunque)");
                sceltaIniziale = Integer.parseInt(scanner.nextLine());
            }while(sceltaIniziale!=1 && sceltaIniziale!=2);

            if (partita == null) {
                System.out.println("\n--- CONFIGURAZIONE PARTITA ---");
                System.out.print("Squadra 1 (Casa): ");
                String s1 = scanner.nextLine();
                System.out.print("Squadra 2 (Ospite): ");
                String s2 = scanner.nextLine();
                System.out.print("Orario di gioco: ");
                String orario = scanner.nextLine();
                System.out.print("Campo: ");
                String campo = scanner.nextLine();
                System.out.print("Citta: ");
                String citta = scanner.nextLine();

                partita = new Partita(s1, s2, citta, campo, orario);
                partitaInCorso = true;
                System.out.println("Partita Creata! I client possono connettersi.");
            }

            // --- AVVIO THREADS ---

            // 1. Thread per ascoltare i messaggi dai client (Chat utente)


            new Thread(() -> ascoltaClient()).start();

            // 2. Thread per il minutaggio automatico
            new Thread(() -> gestisciTimer()).start();

            // --- FASE 2: Loop Operatore (Thread Main) ---
            while (partitaInCorso) {
                System.out.println("\n--- MENU OPERATORE ---");
                System.out.println("1. Inserisci Evento Partita");
                System.out.println("2. Invia Commento in Chat");
                System.out.println("3. Termina Partita");
                System.out.print("Scelta: ");

                String scelta = scanner.nextLine();

                if (scelta.equals("1")) {
                    gestisciEvento(scanner);
                } else if (scelta.equals("2")) {
                    System.out.print("Inserisci commento: ");
                    String commento = scanner.nextLine();
                    broadcastMessage("OPERATORE", commento);
                } else if (scelta.equals("3")) {
                    partitaInCorso = false;
                    broadcast("--- LA PARTITA E' TERMINATA ---");
                    System.out.println("Server in chiusura...");
                    System.exit(0);
                }
            }
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gestione Eventi Partita (Menu 1)
    private static void gestisciEvento(Scanner scanner) {
        System.out.println("Seleziona Evento:");
        System.out.println("1. Gol Casa");
        System.out.println("2. Gol Ospite");
        System.out.println("3. Cartellino Giallo");
        System.out.println("4. Cartellino Rosso");
        System.out.println("5. Rigore");
        System.out.println("6. Fine primo tempo");

        String tipo = scanner.nextLine();
        String evento = "";

        switch (tipo) {
            case "1":
                partita.aggiungiGolCasa();
                evento = "GOL! " + partita.getScoreString();
                break;
            case "2":
                partita.aggiungiGolOspite();
                evento = "GOL! " + partita.getScoreString();
                break;
            case "3": evento = "Cartellino Giallo"; break;
            case "4": evento = "Cartellino Rosso"; break;
            case "5": evento = "CALCIO DI RIGORE!"; break;
            case "6": evento = "FINE PRIMO TEMPO"; break;
            default: evento = "Evento generico"; break;
        }

        // Broadcast dell'evento formattato
        broadcast(minutoCorrente + "' - " + evento);
    }

    // Thread: Minutaggio Automatico
    private static void gestisciTimer() {
        try {
            while (partitaInCorso) {
                // Per testare velocemente, 1 minuto di gioco = 2 secondi reali. 
                // In produzione mettere 60000 ms.
                Thread.sleep(2000);
                minutoCorrente++;

                // Opzionale: notifica il minuto ogni tanto, oppure lascia che appaia solo con gli eventi
                // broadcast("Aggiornamento minuto: " + minutoCorrente + "'"); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Thread: Ricezione UDP dai Client
    private static void ascoltaClient() {
        byte[] buffer = new byte[1024];
        while (partitaInCorso) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                SocketAddress clientAddr = packet.getSocketAddress();
                String received = new String(packet.getData(), 0, packet.getLength());

                // Se è un nuovo client, lo aggiungiamo alla lista e mandiamo l'header
                if (!clientList.contains(clientAddr)) {
                    clientList.add(clientAddr);
                    System.out.println("Nuovo client connesso: " + clientAddr);

                    // Invia Header della partita al nuovo arrivato
                    sendTo(partita.getHeader(), clientAddr);
                }

                // Se il messaggio inizia con "CHAT:", è un messaggio utente
                if (received.startsWith("CHAT:")) {
                    String[] parts = received.split(":", 3); // CHAT:Username:Messaggio
                    if (parts.length == 3) {
                        String user = parts[1];
                        String msg = parts[2];
                        broadcastMessage(user, msg);
                    }
                }

            } catch (IOException e) {
                if(partitaInCorso) e.printStackTrace();
            }
        }
    }

    // Invia un messaggio formattato: Minuto - User: Messaggio
    private static void broadcastMessage(String user, String msg) {
        String output = minutoCorrente + "' [" + user + "]: " + msg;
        System.out.println(output); // Lo vede anche l'operatore
        broadcast(output);
    }

    // Invia stringa raw a tutti i client registrati
    private static void broadcast(String msg) {
        // Copia per evitare ConcurrentModificationException durante l'iterazione
        Set<SocketAddress> targets;
        synchronized(clientList) {
            targets = new HashSet<>(clientList);
        }

        for (SocketAddress dest : targets) {
            sendTo(msg, dest);
        }
    }

    // Metodo helper per inviare pacchetto singolo
    private static void sendTo(String msg, SocketAddress dest) {
        try {
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, dest);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}