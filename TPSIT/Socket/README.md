# 🧠 Socket Java — Comunicazione Client/Server

Questo progetto mostra come creare una **connessione TCP/IP** tra un **Client** e un **Server** in Java, scambiando messaggi testuali tramite **socket**.

---

## 📁 Struttura del progetto

```
├── Client.java
├── MainClient.java
├── Server.java
├── MainServer.java
├── README.md
```

---

## ⚙️ Cos’è un Socket

Un **socket** è un canale di comunicazione tra due dispositivi (ad esempio due programmi Java) collegati tramite rete.  
In pratica:
- Il **Server** “ascolta” su una porta e resta in attesa di richieste.
- Il **Client** si “collega” al server su quella porta e inizia a scambiare dati.

---

## 💬 Funzionamento del codice con esempi

### 🔹 1. Client.java

```java
Socket mio;                     // Rappresenta la connessione TCP al server
DataOutputStream versoServer;    // Per inviare messaggi al server
DataInputStream dalServer;       // Per ricevere messaggi dal server
BufferedReader tastiera;         // Per leggere input dall'utente
```

**Metodo di connessione:**
```java
public void connetti() {
    tastiera = new BufferedReader(new InputStreamReader(System.in));
    mio = new Socket(nomeServer, porta);      // Connessione al server
    versoServer = new DataOutputStream(mio.getOutputStream());
    dalServer = new DataInputStream(mio.getInputStream());
}
```
Spiegazione: si inizializzano tutti gli stream e si crea il socket verso il server.

**Metodo di comunicazione:**
```java
public void comunica() {
    messaggioClient = tastiera.readLine();         // Legge il messaggio dall'utente
    versoServer.writeBytes(messaggioClient + "\n"); // Invia al server
    messaggioServer = dalServer.readLine();         // Legge la risposta dal server
    mio.close();                                    // Chiude la connessione
}
```
Spiegazione: l’utente scrive un messaggio, viene inviato al server e poi si legge la risposta.

### 🔹 2. MainClient.java

```java
public static void main(String[] args) {
    Client c = new Client();
    c.connetti();
    c.comunica();
}
```
Spiegazione: istanzia il client e chiama i metodi di connessione e comunicazione.

### 🔹 3. Server.java (semplificato)

```java
ServerSocket server = new ServerSocket(6789); // Ascolta sulla porta 6789
Socket client = server.accept();               // Accetta la connessione del client
DataInputStream in = new DataInputStream(client.getInputStream());
DataOutputStream out = new DataOutputStream(client.getOutputStream());
```
Spiegazione: il server resta in ascolto e, quando arriva un client, accetta la connessione e prepara gli stream di comunicazione.

### 🔹 4. MainServer.java

```java
public static void main(String[] args) {
    Server s = new Server();
    s.attendi();
    s.comunica();
}
```
Spiegazione: avvia il server, attende la connessione e gestisce la comunicazione.

---

## 🚀 Come usare il progetto

1. Avvia `MainServer` per far partire il server.
2. Avvia `MainClient` per connettersi al server.
3. Inserisci un messaggio nella console del client.
4. Leggi la risposta del server.
5. Chiudi la connessione.

---

## ⚠️ Note importanti

- Entrambi i programmi devono usare **la stessa porta**.
- `localhost` può essere sostituito con un indirizzo IP se client e server sono su macchine diverse.
- Il server deve essere avviato **prima** del client.
- `writeBytes()` aggiunge un `\n` per far capire al server dove finisce il messaggio.
- `readLine()` legge fino al primo `\n` ricevuto.

---

## 📝 Conclusione

Questo esempio mostra la base della comunicazione client/server in Java tramite socket. È il primo passo per creare applicazioni più complesse come chat, giochi in rete o sistemi di monitoraggio remoto.

