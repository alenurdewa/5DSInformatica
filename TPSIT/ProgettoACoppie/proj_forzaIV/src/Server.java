import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Random;

public class Server {
    private ServerSocket server;
    private Socket client;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    private String messaggioServer;
    private Forza4 forza4;

    public void attendi () {
        try {
            //istanzio tutte le informazioni che mi servono
            System.out.println("Server partito ... ");
            server = new ServerSocket(6789) ;
            client = server.accept();
            server.close();
            outStream = new DataOutputStream(client.getOutputStream());
            inStream = new DataInputStream(client.getInputStream());
        } catch (Exception e) {
            System. out.println (e.getMessage());
        }
    }

    public void comunica() {
        //inizio la comunicazione
        Random r = new Random();
        int numeroRandom;
        try {
            //istanzio la classe di gioco
            forza4 = new Forza4();
            //continuo a giocare fino a quando non ho né vincitore né pareggio
            while (Objects.equals(forza4.getWinner(), "n")){
                //chiedo la base in cui giocare al client
                outStream.writeBytes("Inserire la base in cui giocare\n");
                int baseGiocata = Integer.parseInt(inStream.readLine());
                //vedo se il turno è andato a buon fine
                if (forza4.giocaTurno(baseGiocata-1)) {
                    //in caso lo sia mando la griglia dopo l'intervento del client
                    outStream.writeBytes(forza4.toString());
                    //se il client non ha vinto provo a giocare un turno fino a quando non va a buon fine
                    if (Objects.equals(forza4.getWinner(), "n") && forza4.getpAttuale() == 'b') {
                        do {
                            numeroRandom = r.nextInt(7);
                        } while (!forza4.giocaTurno(numeroRandom));
                        //comunico al client la giocata del server
                        outStream.writeBytes("Dopo giocata del server: \n" + forza4.toString());
                    }
                } else {
                    //se il turno non è andato a buon fine soddisfo i readLine del client e comunico l'errore
                    for (int i = 0; i < 12; i++) {
                        outStream.writeBytes("\n");
                    }
                    outStream.writeBytes("Posizione non valida \n");
                }
            }

            //mando al client il messaggio contenente il vincitore o il messaggio di pareggio
            if (Objects.equals(forza4.getWinner(), "p")){
                outStream.writeBytes("1. Pareggio");
            }else {
                outStream.writeBytes("1. Vincitore: "+ forza4.getWinner());
            }

            //comunico la chiusura della connessione e la chiudo
            System.out.println("Server: chiusura connessione ");
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

