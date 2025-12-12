import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public class Client {
    private String nomeServer ="localhost";
    private int portaServer = 6789;
    private Socket mio;
    private BufferedReader reader;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    private String messaggioServer;

    public void connetti() {
        try {
            //inizializzo reader, socket per la comunicazione, e gli stream di out e input
            reader = new BufferedReader(new InputStreamReader(System.in));
            mio = new Socket(nomeServer, portaServer);
            outStream = new DataOutputStream(mio.getOutputStream());
            inStream = new
        (mio.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void comunica() {
        try {
            //inizio la comunicazione
            System.out.println("Inizio della comunicazione");
            boolean interrompi = false;
            String linea;

            //leggo la prima riga inviata dal server (chiede in che posizione giocare)
            System.out.println(inStream.readLine());
            //mando la risposta
            String l = reader.readLine();
            outStream.writeBytes(l + "\n");

            //ciclo che continua a leggere la comunicazione fino a quando il server comunica una vincita/pareggio
            while (!interrompi) {

                //leggo la tabella di gioco inviatami dal server
                for (int i = 0; i < 6; i++) {
                    System.out.println(inStream.readLine());
                }

                //leggo la linea successiva alla mia mossa, che potrebbe contenere dati di vincita o pareggio
                linea = inStream.readLine();

                //se c'è un punto la partita è finita, per via di come l'ho strutturata nel server
                int indice = linea.indexOf('.');
                if (indice != -1) {
                    //comunico la fine della partita
                    System.out.print("Partita finita, ");
                    //determino se è un pareggio
                    if (linea.charAt(linea.indexOf('.') + 2) == 'P') {
                        System.out.println(" pareggio");
                    } else {
                        //se non lo è stampo il vincitore
                        System.out.println("Partita finita, v" + linea.substring(linea.indexOf('.') + 3));
                    }
                    //interrompo la comunicazione
                    interrompi = true;
                } else {
                    //se la partita non è finita vado avanti; stampo la comunicazione dal server
                    System.out.println(linea);

                    //in caso di mosse irregolari il server stamperà una serie di righe vuote, metto questa condizione
                    //perché se applico il charAt a una stringa vuota verrà sollevata un'eccezione
                    if (!linea.isEmpty()) {
                        //in caso mi chieda di inserire la colonna in cui giocare prendo l'input dell'utente e lo invio
                        if (linea.charAt(0) == 'I') {
                            l = reader.readLine();
                            outStream.writeBytes(l + "\n");
                        }
                    }
                }
            }


            //comunico la chiusura della connessione e la chiudo
            System.out.println("Client: chiusura connessione ");
            mio.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

