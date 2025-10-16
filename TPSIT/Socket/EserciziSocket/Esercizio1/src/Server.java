import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {


    ServerSocket server;
    Socket client;
    DataOutputStream versoClient;
    DataInputStream dalClient;
    int[] messaggioClient = new int [10];

    public void attendi () {
        try {
            System.out.println("Server partito ... ");
            server = new ServerSocket(6789);
            client = server.accept();
            server.close();
            versoClient = new DataOutputStream(client.getOutputStream());
            dalClient = new DataInputStream(client.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private float calcolaMedia(int[] numInteri){
        int somma = 0;

        for (int i= 0; i<numInteri.length;i++){
            somma+=numInteri[i];
        }
        return (float) somma /numInteri.length;
    }

    public void comunica () {
        try {
            for (int i = 0; i<messaggioClient.length; i++){
                messaggioClient[i] = dalClient.readByte();
            }

            System.out.println("Ho ricevuto i numeri interi dal client " +Arrays.toString(messaggioClient));
            System.out.println("Ora calcolo la media");
            float media = calcolaMedia(messaggioClient);
            System.out.println("Inoltro la media: " + media + " al client ... ");
            versoClient.writeBytes(String.valueOf(media));
            System.out.println("Server: chiusura connessione ... ");
            client.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
