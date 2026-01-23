# TelecronacaLive — README approfondito

> Questo README spiega in dettaglio la logica di ogni classe del progetto, come le classi lavorano insieme, il flusso di comunicazione client-server (metodi, formati messaggi), come è implementata la "broadcast" (simulazione di multicast), come più client si connettono allo stesso server e come funzionano le user interface (Client e Server). Leggi ogni sezione con attenzione per capire il comportamento runtime e i punti critici.

---

## Indice
1. Panoramica architetturale
2. Formato messaggi
3. Classi di dominio (Campionato, Squadra, Giocatore, Partita)
4. Server: `ServerGUI` e `ServerLogica` — dettagli e flussi
5. Client: `ClientGUI` e `ClientLogica` — dettagli e flussi
6. Comunicazione client ↔ server: metodi, thread e sequenze
7. "Multicast" nel progetto (broadcast applicativo) vs vero multicast IP
8. Come si connettono più client allo stesso server
9. UI (Swing) — come è costruita e come si integra con la logica
10. Concorrenza, limiti attuali e possibili miglioramenti

---

## 1) Panoramica architetturale

L'applicazione è divisa in due macro-componenti:
- **Server**: `ServerGUI` (interfaccia grafica) + `ServerLogica` (gestione socket UDP, lista client, timer partita, broadcast). Il server espone una porta UDP e aspetta pacchetti dai client.
- **Client**: `ClientGUI` (interfaccia grafica) + `ClientLogica` (socket UDP, invio messaggi, thread di ricezione). Il client si connette al server inviando un messaggio `JOIN` e da quel momento riceve pacchetti UDP dal server.

Le classi di dominio (`Campionato`, `Squadra`, `Giocatore`, `Partita`) sono usate sul server per configurare e gestire lo stato della partita (squadre, rose, gol, intestazione, ecc.).

La comunicazione usa UDP (DatagramSocket/DatagramPacket). Il server conserva gli `SocketAddress` dei client che hanno inviato `JOIN` e invia loro pacchetti unicast per realizzare una forma di broadcast applicativo.

---

## 2) Formato messaggi

Il protocollo è testuale e basato su prefissi semplici, separati da `:`. I prefissi più importanti sono:

- `JOIN:<username>` — inviato dal client al server per registrarsi.
- `CHAT:<username> - <testo>` — chat utente inoltrata dal client al server e poi ridistribuita.
- `SYS:<messaggio>` — messaggio di sistema generato dal server e inviato ai client.
- `HEADER:<intestazione_partita>` — aggiornamento dell'intestazione partita (es. "Inter 1 - 0 Milan | Stadio (...)").
- `TIME:<minuto>'` — aggiornamento del tempo di gioco.
- `EVENTO:<descrizione evento>` — evento di cronaca (gol, cartellini, ecc.).

Queste stringhe vengono convertite in bytes con `String.getBytes()` ed inviate come payload UDP.

---

## 3) Classi di dominio

### `Giocatore`
- **Campi**: `nome` (String), `numero` (int)
- **Metodi**: costruttore, getters, `toString()` che restituisce `numero + " - " + nome`.
- **Ruolo**: rappresentare un giocatore singolo; usato nelle selection dialog del ServerGUI per scegliere chi segna o prende cartellini.

### `Squadra`
- **Campi**: `nome`, `presidente`, `rosa` (array di `Giocatore` lungo 30), `contaGiocatori`.
- **Metodi**: costruttore, `aggiungiGiocatore(String,int)`, `getRosaEffettiva()` che costruisce un array senza `null` utile per le JComboBox, `toString()` che ritorna il nome (utile per combo).
- **Ruolo**: mantiene la rosa di giocatori e dati della squadra; fornisce la lista effettiva per UI.

### `Campionato`
- **Campi**: `Squadra[] squadre` (qui inizializzato a 3 squadre con giocatori hard-coded).
- **Metodi**: costruttore (popola squadre), `getSquadre()`.
- **Ruolo**: repository locale usato nella fase di configurazione della partita in `ServerGUI`.

### `Partita`
- **Campi**: `casa`, `ospite` (Squadra), `citta`, `campo`, `orario`, `golCasa`, `golOspite`.
- **Metodi**: costruttore, `segnaCasa()`, `segnaOspite()`, `getIntestazione()` che costruisce la stringa visualizzata nell'header della GUI server e inviata ai client, getters per le squadre.
- **Ruolo**: stato runtime della partita (score, intestazione) — fonte d'informazione per il server quando invia `HEADER` e `EVENTO`.

**Interazione fra classi di dominio**: `ServerGUI` usa `Campionato` per leggere le squadre e costruire l'oggetto `Partita`. `Partita` legge le `Squadra` per aggiornare intestazioni e per scegliere marcatori. Le UI leggono i getters per visualizzare i dati e i pulsanti chiamano metodi sul `ServerLogica` che aggiorna lo stato e broadcasta.

---

## 4) Server: `ServerGUI` e `ServerLogica`

### `ServerGUI` (ruolo e componenti principali)
- Costruisce l'interfaccia grafica Swing:
  - Header con `lblHeader` (intestazione partita), `lblTempo`, `lblInfo`.
  - Area centrale con `areaLog` (log operatore) e `areaChat` (chat pubblica).
  - Pannello inferiore con bottoni di controllo partita (start, secondo tempo, stop) e bottoni evento (gol, cartellini, evento libero, invia messaggio chat).
- Logica GUI:
  - `effettuaLogin()` chiede username e password (password fissa `mondoCalcio`).
  - `configuraPartita()` mostra le combo con le squadre prese da `Campionato` e costruisce `Partita`.
  - I bottoni invocano metodi su `ServerLogica` (es. `avviaPartitaTimer()`, `avviaSecondoTempo()`, `inviaEvento(msg)`, `broadcast(msg)`).
  - Metodi helper `appendLog`, `appendChat`, `setTempo`, `setBottoniSecondoTempo`, `setBottoniEventi` aggiornano la GUI in modo thread-safe usando `SwingUtilities.invokeLater`.

### `ServerLogica` (ruolo e componenti principali)
- **Campi principali**:
  - `DatagramSocket socket` aperto su porta configurata.
  - `SocketAddress[] clients` e `numClients` per tenere traccia dei client registrati.
  - stato partita: `minuto`, `inCorso`, `secondoTempo`, `nomeOperatore`.
- **Metodi**:
  - Costruttore: apre `DatagramSocket(port)` e lancia `startReceiver()`.
  - `startReceiver()`: crea un thread che cicla su `socket.receive(packet)` e gestisce i messaggi ricevuti (JOIN/CHAT, attualmente). Per ogni pacchetto: estrae `String msg` e `SocketAddress mittente`.
  - `aggiungiClient(SocketAddress nuovo, String user)`: verifica duplicati (con equals su SocketAddress), aggiunge nella lista clients, incrementa `numClients`, manda a tutti un `SYS:...` di benvenuto e invia singolarmente a quel client `HEADER` e `TIME` iniziali.
  - `broadcast(String msg)`: itera su `clients[0..numClients-1]` e chiama `inviaA(msg, clients[i])` per fare il reinvio unicast.
  - `inviaA(String msg, SocketAddress dest)`: costruisce DatagramPacket (con `dest`) e fa `socket.send(packet)`.
  - Timer e eventi partita: `avviaPartitaTimer()` e `avviaSecondoTempo()` creano thread temporizzati che incrementano `minuto`, inviano `TIME:` periodicamente e disabilitano/abilitano bottoni GUI via callback.
  - `inviaEvento(String testo)`: logga e `broadcast("EVENTO:" + testo)`.

### Note sul flusso server internamente
- Il thread `startReceiver` è l'entrypoint per tutti i messaggi client -> server. Quando arriva un `JOIN`, si registra l'indirizzo e risponde con `HEADER` e `TIME` solo a quel client.
- Tutti gli update tempo, header, evento vengono notificati ai client tramite `broadcast` (loop di `inviaA` su tutti gli `SocketAddress` registrati).

---

## 5) Client: `ClientGUI` e `ClientLogica`

### `ClientGUI`
- All'avvio chiede via `JOptionPane` indirizzo IP server, porta e username. Se l'utente annulla, chiude.
- Costruisce la GUI:
  - Header con `lblHeader` e `lblTempo` (mostrati in alto).
  - Area centrale: `areaChat` (log/testi ed eventi ricevuti).
  - Pannello inferiore: casella di testo e pulsante "Invia" per inviare messaggi chat.
- Interazione:
  - Quando l'utente invia un messaggio, la GUI chiama `logica.invia("CHAT:" + username + ": " + testo)`.
  - Implementa `elaboraMessaggio(String msg)` che interpreta i prefissi `TIME:`, `HEADER:`, `SYS:`, `EVENTO:` e `CHAT:` e aggiorna la GUI usando `SwingUtilities.invokeLater`.

### `ClientLogica`
- **Campi**: `DatagramSocket socket` (client), `InetAddress indirizzoServer`, `int portaServer`, flag `inEsecuzione`.
- **Metodi principali**:
  - `connettiti(String ip, int port, String username)`: crea `DatagramSocket()` (porta locale casuale), risolve `InetAddress.getByName(ip)`, salva porta e indirizzo, invia `JOIN:username` al server usando `invia(...)` e lancia `ascolta()` che avvia un thread ricevente.
  - `invia(String msg)`: costruisce DatagramPacket con `indirizzoServer` e `portaServer` (quindi pacchetti unicast verso il server) e li manda con `socket.send(packet)`.
  - `ascolta()`: thread che esegue `socket.receive(packet)` in loop e per ogni messaggio chiama `gui.elaboraMessaggio(msg)`.
  - `disconnettiti()`: setta `inEsecuzione = false` e chiude il socket.

### Note sul flusso client internamente
- Il client inoltra tutti i messaggi al server (es. `JOIN`, `CHAT`). Non ascolta direttamente altri client: riceve solo ciò che il server invia.
- La ricezione è gestita da un thread separato per evitare di bloccare la UI.

---

## 6) Comunicazione client ↔ server: sequenze e metodi

### Sequenza tipica di connessione
1. ClientGUI parte, mostra dialog per IP/porta/username.
2. `ClientLogica.connettiti(ip, port, username)`:
   - crea DatagramSocket locale
   - risolve l'indirizzo server
   - `invia("JOIN:" + username)` -> costruisce DatagramPacket -> `socket.send(packet)` verso server
   - avvia thread `ascolta()` che resta in `socket.receive()`
3. Server riceve il pacchetto nel thread `startReceiver()` (sul `DatagramSocket` del server): legge `JOIN:username` e chiama `aggiungiClient(mittente, user)`.
4. `aggiungiClient` salva `SocketAddress` del client, incrementa `numClients`, manda `SYS` broadcast e invia `HEADER` e `TIME` solo a quel client (con `inviaA(...)`).
5. A questo punto il client riceve i messaggi `HEADER` e `TIME` via UDP nel suo thread di ascolto, e la GUI li visualizza.

### Sequenza tipica di chat
- Client invia `CHAT:username: messaggio` -> server riceve, chiama `gui.appendChat(...)` e poi `broadcast(msg)` -> server itera su clients e invia unicast UDP di quel messaggio a ciascuno -> ogni client riceve ed esegue `elaboraMessaggio` per visualizzarlo.

### Sequenza tipica di evento di gioco
- Operatore nel `ServerGUI` preme un bottone (es. GOL) -> `ServerGUI` richiama `logica.inviaEvento(evento)` -> `ServerLogica.inviaEvento` fa `gui.appendLog(minuto + "' - " + testo)` e `broadcast("EVENTO:" + testo)` -> tutti i client ricevono e mostrano l'evento.

---

## 7) "Multicast" nel progetto (broadcast applicativo) vs vero multicast IP

**Cosa fa il progetto ora**
- Il server implementa una **broadcast applicativa**: mantiene una lista di `SocketAddress` (IP:porta) dei client e per ogni messaggio da distribuire chiama `inviaA(msg,dest)` in un ciclo. In pratica è un *reinoltro unicast* verso ciascun client.

**Vero multicast IP**
- Il vero multicast usa indirizzi di classe D (224.0.0.0/4) e `MulticastSocket` in Java. In quel caso il server invierebbe un singolo datagram indirizzato a un gruppo multicast e la rete (router/switch) si occuperebbe di replicare il pacchetto a tutti i partecipanti che si sono iscritti al gruppo.

**Differenze pratiche**:
- Attuale: il server invia N pacchetti separati (uno per ogni client). Carico CPU/Network del server cresce con N. Funziona anche attraverso NATs tipici? No — quando c'è NAT e client dietro NAT, il server può inviare se ha l'address/porta esterna che il client ha usato per contattarlo (funziona nella maggior parte dei casi UDP), ma NATs restrittivi o firewall possono bloccare.
- Multicast: un singolo invio da server, la rete si occupa della duplicazione; però multicast non è ben supportato su Internet pubblico (più usato in reti locali) e richiede configurazione di rete e router che supportino IGMP, PIM, ecc.

**Conclusione**: il progetto NON usa `MulticastSocket`; simula il comportamento con un ciclo di invii unicast (`broadcast` in `ServerLogica`). Se vuoi vera multicast locale, serve riprogettare il canale usando `MulticastSocket` e gruppi di indirizzi.

---

## 8) Come si connettono più client allo stesso server

- Ogni client invia `JOIN:username` a `serverIP:serverPort`.
- Il server, ricevendo un datagram, legge `packet.getSocketAddress()` (che contiene IP remoto e porta UDP sorgente) e lo salva nell'array `clients[]` se non è già presente.
- Per sapere se un client è già registrato, il server confronta `SocketAddress.equals(...)`.
- Quando il server invia messaggi (broadcast), itera su `0..numClients-1` e invia pacchetti a ciascun `clients[i]`.

**Limiti pratici dell'approccio attuale**
- L'array `clients` è di dimensione fissa (100). Se `numClients` arriva a 100, nuovi client saranno ignorati.
- Non c'è rimozione automatica dei client che si disconnettono o diventano non raggiungibili: nessun timeout/heartbeat.
- L'accesso all'array `clients[]` non è sincronizzato; poiché ricezione e broadcast possono succedere in thread diversi, c'è il rischio di race condition. Attualmente il codice non usa strutture thread-safe.

**Comportamento tipico se molti client**
- Server invia N pacchetti ad ogni broadcast: throughput e CPU aumentano linearmente con N.

---

## 9) UI (Swing) — come funzionano ClientGUI e ServerGUI

### Principi generali
- Tutta la UI è costruita con Swing. Eventi UI (clic sui bottoni, invio testo) vengono gestiti nel thread Event Dispatch Thread (EDT).
- Aggiornamenti provenienti da thread non-UI (es. thread ricevente, timer partita) usano `SwingUtilities.invokeLater` per aggiornare componenti Swing in modo thread-safe.

### `ServerGUI` (dettagli)
- Layout: BorderLayout principale con:
  - `NORTH`: pannello con intestazione e tempo (GridLayout).
  - `CENTER`: area log + chat (GridLayout 1x2).
  - `SOUTH`: griglia di bottoni 3x3 per controllo partita ed eventi.
- Azioni dei bottoni:
  - `btnStart` -> chiama `logica.avviaPartitaTimer()`; disabilita bottoni appropriati, abilita `btnStop`.
  - `btnGolCasa` -> apre dialog con lista di giocatori (`JOptionPane.showInputDialog` con array `Giocatore[]`) e quindi chiama `logica.inviaEvento(evento)` e `logica.broadcast("HEADER:" + partita.getIntestazione())`.
  - `btnMsg` -> dialog per messaggio libero, chiama `logica.broadcast("CHAT:" + testo)`.
- Visualizzazione: `appendLog` e `appendChat` aggiungono testo alle rispettive JTextArea.

### `ClientGUI` (dettagli)
- Layout: BorderLayout con header in `NORTH`, chat al centro e input in `SOUTH`.
- All'avvio: dialog per IP/porta/username.
- Input: il tasto `Invia` e la pressione di Enter invocano lo stesso `ActionListener` che manda `CHAT:` al server tramite `logica.invia(...)`.
- Ricezione: `ClientLogica.ascolta` chiama `gui.elaboraMessaggio(msg)`, che aggiorna le UI label/text area a seconda del prefisso.

---

## 10) Concorrenza, limiti attuali e possibili miglioramenti (consigli pratici)

### Limiti osservati
1. **Array `clients[]` non sincronizzato** — potenziale race fra `startReceiver` (aggiunta client) e `broadcast` (iterazione). Rischio di ConcurrentModification-like bugs su letture/scritture incrociate.
2. **Nessuna gestione disconnect** — se un client si chiude senza inviare alcun messaggio di leave, rimane nella lista `clients` per sempre.
3. **Buffer fisso e ignoranza degli errori** — buffer 1024, eccezioni spesso silenziate con catch `{}`; questo nasconde errori utili.
4. **Scalabilità** — inviare N pacchetti per broadcast non scala bene oltre poche centinaia di client.
5. **Affidabilità** — UDP non garantisce consegna né ordine; il progetto non ha ACK, ritrasmissioni o controllo di sequenza.

### Miglioramenti consigliati (per produzione o test avanzati)
- Sostituire `SocketAddress[]` con una struttura thread-safe (es. `CopyOnWriteArrayList<SocketAddress>` o sincronizzare l'accesso con `synchronized`).
- Implementare un meccanismo di **heartbeat** o timeout: ogni client manda periodicamente un `PING` oppure il server rimuove client inattivi dopo X secondi senza ricevere pacchetti.
- Gestione `DISCONNECT` esplicita: quando il client chiude, inviare `LEAVE` prima di chiudere il socket.
- Logging delle eccezioni: non svuotare i catch; loggare o appendere alla GUI.
- Se serve affidabilità: aggiungere ack/seq o usare TCP per messaggi critici (es. JOIN/LEAVE), mantenendo UDP per lo stream delle TIME se si vuole bassa latenza.
- Per vera multicast in LAN: usare `MulticastSocket`/`InetAddress.getByName("224.0.x.x")` e `joinGroup()`. Ricordare che i router generalmente non inoltrano multicast su Internet pubblico.
- Usare un formato strutturato (JSON) per i messaggi invece del semplice prefisso testuale, così da aggiungere campi senza ambiguità.

---

## Conclusione

Questo progetto è una ottima base didattica che mostra:
- costruzione GUI con Swing per operatore e spettatore;
- comunicazione UDP semplice con `DatagramSocket` e `DatagramPacket`;
- come implementare un broadcast applicativo tramite lista di `SocketAddress`;
- come orchestrare eventi di gioco e trasmetterli ai client.

Le parti critiche su cui concentrarsi per produzione sono: robustezza (gestire eccezioni), concorrenza (sincronizzazione lista client), scalabilità (N invii per broadcast) e affidabilità (perdita packet UDP). Se vuoi, posso generare una versione migliorata del `ServerLogica` che usa `CopyOnWriteArrayList` + gestione timeout o una versione che usa JSON per i messaggi.

---

Se vuoi che lo converta in un documento README pronto per GitHub (con esempi di run, comando `javac`/`java`, e snippet dei messaggi) dimmi e lo aggiungo.

Buon debugging, bro — dimmi se vuoi che aggiunga diagrammi di sequenza o codice di esempio per i miglioramenti suggeriti.

