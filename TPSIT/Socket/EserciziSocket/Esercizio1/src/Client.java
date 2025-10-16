import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {


    String nomeServer = "localhost";
    int porta = 6789;
    Socket server;
    DataOutputStream versoServer;
    DataInputStream dalServer;
    BufferedReader tastiera;
    int[] numInteri = new int[10];


    public void comunica() {
        try {
            System.out.println("Inserire 10 numeri interi\n");
            for (int i = 0; i<numInteri.length;i++){
                System.out.println("Inserisci il "+(i+1)+" numero ");
                numInteri[i] = Integer.parseInt(tastiera.readLine());
                versoServer.writeByte(numInteri[i]);
            }
            String strMedia = dalServer.readLine();
            System.out.println("Sono il client, media ricevuta dal server: " +strMedia);
            System.out.println("Chiusura connessione");
            server.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void connetti () {
        try {
            tastiera = new BufferedReader (new InputStreamReader(System.in) );
            server = new Socket (nomeServer,porta);
            versoServer = new DataOutputStream ( server.getOutputStream () );
            dalServer = new DataInputStream (server.getInputStream());

        } catch (Exception e) {
            System.out.println (e.getMessage ());

        }

    }

}
