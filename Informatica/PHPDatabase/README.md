# README – Introduzione a PDO, XAMPP e Database in PHP

## 1. Cos’è PDO
PDO (PHP Data Objects) è un’estensione di PHP che serve per collegarsi a un database e lavorare con i dati.

Il suo scopo principale è fornire **un unico modo standard** per accedere a database diversi, senza dover cambiare il codice se cambia il tipo di database.

Con PDO è possibile:
- collegarsi a un database
- eseguire query SQL
- gestire errori in modo controllato
- inserire e leggere dati in modo sicuro

PDO è considerato moderno, leggero e sicuro.

---

## 2. Perché si usa PDO

PDO è importante perché:
- funziona con molti database (MySQL, SQLite, PostgreSQL, ecc.)
- riduce il rischio di SQL Injection
- permette di gestire gli errori tramite eccezioni
- separa il codice PHP dalla logica del database

Oggi è il metodo consigliato per lavorare con database in PHP.

---

## 3. Cos’è XAMPP

XAMPP è un pacchetto che contiene tutto il necessario per sviluppare in locale:
- Apache (server web)
- MySQL / MariaDB (database)
- PHP (linguaggio)

Per usare PDO con MySQL è necessario che:
- Apache sia attivo
- MySQL sia attivo

Questo si controlla dal pannello di controllo di XAMPP.

---

## 4. Connessione al database con PDO

Per collegarsi a un database si crea un oggetto PDO.

### Elementi necessari
- **DSN**: indica il tipo di database e dove si trova
- **Username**: utente del database (in locale spesso `root`)
- **Password**: spesso vuota in XAMPP

Esempio di DSN per MySQL:
```
mysql:host=localhost;port=3306;charset=utf8
```

Esempio di connessione:
```php
$pdo = new PDO($dsn, $user, $password);
```

---

## 5. Gestione degli errori

Se la connessione fallisce, PDO genera una **PDOException**.

È buona pratica usare:
```php
try {
    // connessione
} catch (PDOException $e) {
    // gestione errore
}
```

In produzione **non bisogna mostrare gli errori a schermo**, perché possono rivelare:
- nome del server
- utente del database
- struttura del database

Per questo si disattiva `display_errors`.

---

## 6. Esecuzione di comandi SQL

PDO mette a disposizione il metodo `exec()`.

`exec()` serve per eseguire comandi SQL che **non restituiscono risultati**, come:
- CREATE
- INSERT
- UPDATE
- DELETE

Esempi:
```php
$pdo->exec("CREATE DATABASE IF NOT EXISTS prova");
$pdo->exec("USE prova");
```

---

## 7. Creazione di una tabella

Con `exec()` è possibile creare tabelle:
```php
$pdo->exec("CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL
)");
```

La tabella contiene:
- `id`: identificatore unico
- `nome`: nome dell’utente
- `cognome`: cognome dell’utente

---

## 8. Inserimento dati in modo sicuro

Per inserire dati è fondamentale usare **prepare()** e **execute()**.

Questo metodo:
- separa SQL dai dati
- previene SQL Injection

Esempio:
```php
$stmt = $pdo->prepare("INSERT INTO user (nome, cognome) VALUES (?, ?)");
$stmt->execute([$nome, $cognome]);
```

---

## 9. Importazione dati da file CSV

Un file CSV è un file di testo in cui i dati sono separati da virgole.

Esempio riga CSV:
```
Mario,Rossi
```

### Passaggi principali
1. Controllo che il file esista
2. Apertura del file
3. Lettura riga per riga
4. Separazione dei valori con `explode()`
5. Inserimento nel database con `prepare()`

Questo permette di importare grandi quantità di dati in modo ordinato e sicuro.

---

## 10. Lettura dei dati dal database

Per leggere dati si usa `query()` insieme a `fetch()` o `fetchAll()`.

Esempio:
```php
$stmt = $pdo->query("SELECT titolo, autore FROM libri");
$righe = $stmt->fetchAll(PDO::FETCH_ASSOC);
```

I dati vengono poi usati in PHP, ad esempio con un ciclo `foreach`.

---

## 11. Riassunto finale

- PDO è il metodo consigliato per lavorare con database in PHP
- Funziona con molti database
- Usa `prepare()` ed `execute()` per sicurezza
- `exec()` serve per comandi SQL senza risultati
- XAMPP permette di lavorare in locale
- Gli errori non vanno mostrati in produzione

Questo è tutto ciò che serve sapere per iniziare a usare PDO in modo corretto e sicuro.

