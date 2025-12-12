public class MainServer {
    public static void main(String[] args) {
        //istanzio il server
        Server s = new Server();
        //attendo una connessione, metodo bloccante
        s.attendi();
        //quando un client si connette lo sblocca e inizia a comunicare
        s.comunica();
    }
}
