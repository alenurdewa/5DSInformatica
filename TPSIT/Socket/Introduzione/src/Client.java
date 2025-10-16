import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {


    String nomeServer = "localhost";
    int porta = 6789;
    Socket mio;
    BufferedReader tastiera;
    DataOutputStream versoServer;
    DataInputStream dalServer;
    String messaggioClient;
    String messaggioServer;


    public void connetti () {
        try {
            tastiera = new BufferedReader (new InputStreamReader(System.in) );
            mio = new Socket (nomeServer,porta) ;
            versoServer = new DataOutputStream ( mio.getOutputStream () );
            dalServer = new DataInputStream (mio.getInputStream());

        } catch (Exception e) {
            System.out.println (e.getMessage ());

        }

    }

    public void comunica () {
        try {
            System.out.println("Inserisci un messaggio da inviare al server: . .. ");
            messaggioClient = tastiera.readLine();
            System.out.println("Inoltro il messaggio: " + messaggioClient + " al server ... ");
            versoServer.writeBytes(messaggioClient + "\n");
            messaggioServer = dalServer.readLine();
            System.out.println("Risposta del server: " + messaggioServer);
            System.out.println("Client: chiusura connessione ... ");
            mio.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }



}
