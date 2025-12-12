public class MainClient {
    public static void main(String[] args) {
        //istanzio il client
        Client c = new Client();
        //lo faccio connettere al server
        c.connetti();
        //inizio la comunicazione
        c.comunica();
    }
}
