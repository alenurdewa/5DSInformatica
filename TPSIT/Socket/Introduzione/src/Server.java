import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket server;
    Socket client;
    DataOutputStream versoClient;
    DataInputStream dalClient;
    String messaggioClient;
    String messaggioServer;


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

    public void comunica () {
        try {
            System.out.println("Scrivi un messaggio e te lo rinvio in maiuscolo: ... ");
            messaggioClient = dalClient.readLine();
            messaggioServer = messaggioClient.toUpperCase();
            System.out.println("Inoltro il messaggio: " + messaggioServer + " al client ... ");

            versoClient.writeBytes(messaggioServer + "\n");
            System.out.println("Server: chiusura connessione ... ");
            client.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
