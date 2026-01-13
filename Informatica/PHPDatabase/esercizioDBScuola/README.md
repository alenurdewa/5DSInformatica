# README — Progetto: Istituto Scolastico (semplificato)

Breve guida (semplificata) per capire il progetto PHP che hai condiviso: uso di PDO, lettura di CSV, HTML con form GET e struttura del database.

---

## 1. Struttura file (esempio)

- `index.php` — pagina principale + HTML + gestione `$_GET['view']` e `q`
- `funzioni.php` — funzioni per interrogare il DB (getStudenti, getCorsi...)
- `config.php` — istanza PDO / credenziali
- `setup_db.php` — script per creare DB e tabelle (lo hai già)
- `import_csv.php` (opzionale) — esempio per importare CSV

---

## 2. `config.php` — Classe/uso PDO (semplice)

Usa questo pattern per creare un'istanza PDO riutilizzabile.

```php
<?php
// config.php
$dsn = "mysql:host=localhost;port=3306;charset=utf8";
$user = "Alessandro"; // cambia con il tuo utente
$password = "miao";   // cambia con la tua password

try {
    $pdo = new PDO($dsn, $user, $password, [
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
        PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8"
    ]);
} catch (PDOException $e) {
    die("Errore DB: " . $e->getMessage());
}
```

**Note:**

- `ATTR_ERRMODE => ERRMODE_EXCEPTION` aiuta nel debugging.
- Imposta sempre `charset=utf8` nel DSN.

---

## 3. Funzioni per leggere il DB (`funzioni.php`)

Esempi semplici (usano PDO e prepared statements quando necessario):

```php
<?php
require 'config.php';

function getStudenti($pdo) {
    return $pdo->query("SELECT * FROM studenti")->fetchAll();
}

function getCorsi($pdo) {
    return $pdo->query("SELECT * FROM corsi")->fetchAll();
}

function getDocenti($pdo) {
    return $pdo->query("SELECT * FROM docenti")->fetchAll();
}

function getTutto($pdo) {
    $sql = "SELECT s.nome, s.cognome, c.nome as corso, d.nome as docente
            FROM studenti s
            JOIN corsi c ON s.corso_id = c.id
            JOIN docenti d ON c.docente_id = d.id";
    return $pdo->query($sql)->fetchAll();
}

function cercaStudente($pdo, $cognome) {
    $sql = "SELECT s.nome, s.cognome, c.nome as corso, d.nome as docente
            FROM studenti s
            JOIN corsi c ON s.corso_id = c.id
            JOIN docenti d ON c.docente_id = d.id
            WHERE s.cognome LIKE ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute(["%$cognome%"]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}
```

**Sicurezza:** usa sempre prepared statements per parametri provenienti dall'esterno (es. ricerca `$q`).

---

## 4. `index.php` — Gestione vista e HTML (semplificato)

Ecco la logica essenziale:

```php
<?php
require 'funzioni.php';
$view = $_GET['view'] ?? 'join';
?>
<!DOCTYPE html>
<html>
<head><meta charset="utf-8"><title>Scuola</title></head>
<body>
<nav>
    <a href="?view=join">Vista Completa</a> |
    <a href="?view=studenti">Studenti</a> |
    <a href="?view=corsi">Corsi</a> |
    <a href="?view=docenti">Docenti</a>
</nav>

<form method="GET">
    <input type="hidden" name="view" value="cerca">
    Cerca Cognome: <input type="text" name="q">
    <button>Cerca</button>
</form>

<table border="1">
<?php
// qui includi la logica vista come nel tuo file (studenti, corsi, docenti, cerca, join)
?>
</table>
</body>
</html>
```

**Importante:** quando stampi dati che vengono dall'utente o da GET/DB, usa `htmlspecialchars()` per evitare XSS.

Esempio:

```php
echo '<td>' . htmlspecialchars($r['cognome']) . '</td>';
```

---

## 5. Lettura/Import CSV (esempio semplificato)

Formato CSV atteso (esempio che hai fornito):

```
nome,cognome,corso,docente,anno
Mario,Rossi,Informatica,Bianchi,2024
Luisa,Verdi,Matematica,Rossi,2024
```

Script d'importazione semplificato:

```php
<?php
require 'config.php';

$fp = fopen('dati.csv', 'r');
if (!$fp) die('Impossibile aprire CSV');

// salta header se c'è
$header = fgetcsv($fp);

$pdo->beginTransaction();
try {
    $stmtDoc = $pdo->prepare("INSERT IGNORE INTO docenti (nome) VALUES (?)");
    $stmtCorso = $pdo->prepare("INSERT IGNORE INTO corsi (nome, anno, docente_id) VALUES (?, ?, ?)");
    $stmtStud = $pdo->prepare("INSERT INTO studenti (nome, cognome, corso_id) VALUES (?, ?, ?)");

    while (($row = fgetcsv($fp)) !== false) {
        list($nome, $cognome, $corsoNome, $docenteNome, $anno) = $row;
        // 1) inserisci / trova docente
        $stmt = $pdo->prepare("SELECT id FROM docenti WHERE nome = ?");
        $stmt->execute([$docenteNome]);
        $doc = $stmt->fetch();
        if (!$doc) {
            $pdo->prepare("INSERT INTO docenti(nome) VALUES(?)")->execute([$docenteNome]);
            $docId = $pdo->lastInsertId();
        } else {
            $docId = $doc['id'];
        }
        // 2) inserisci / trova corso
        $stmt = $pdo->prepare("SELECT id FROM corsi WHERE nome = ? AND anno = ?");
        $stmt->execute([$corsoNome, $anno]);
        $c = $stmt->fetch();
        if (!$c) {
            $pdo->prepare("INSERT INTO corsi (nome, anno, docente_id) VALUES (?, ?, ?)")
                ->execute([$corsoNome, $anno, $docId]);
            $corsoId = $pdo->lastInsertId();
        } else {
            $corsoId = $c['id'];
        }
        // 3) inserisci studente
        $stmtStud->execute([$nome, $cognome, $corsoId]);
    }
    $pdo->commit();
    echo "Import completato.";
} catch (Exception $e) {
    $pdo->rollBack();
    echo "Errore import: " . $e->getMessage();
}
fclose($fp);
```

**Nota:** `INSERT IGNORE` è comodo ma valuta duplicati o vincoli in base al tuo schema.

---

## 6. Creare il DB (tuo `setup_db.php` già funziona)

Esempio che usi:

```php
$pdo->exec("CREATE DATABASE IF NOT EXISTS istitutoScolastico");
$pdo->exec("USE istitutoScolastico");
$pdo->exec("CREATE TABLE IF NOT EXISTS docenti (id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(50))");
$pdo->exec("CREATE TABLE IF NOT EXISTS corsi (id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(50), anno INT, docente_id INT, FOREIGN KEY (docente_id) REFERENCES docenti(id))");
$pdo->exec("CREATE TABLE IF NOT EXISTS studenti (id INT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(50), cognome VARCHAR(50), corso_id INT, FOREIGN KEY (corso_id) REFERENCES corsi(id))");
```

Dopo aver eseguito `php setup_db.php` avrai le tabelle pronte.

---

## 7. Differenza GET vs POST (breve)

- `GET` manda i dati nell'URL ed è visibile; buono per ricerche o filtri.
- `POST` manda i dati nel body; meglio per operazioni che cambiano DB (inserimenti/aggiornamenti) o quando non vuoi parametri nella URL.

Nel tuo caso la ricerca è corretta come `GET`.

---

## 8. Suggerimenti finali / best practices (semplici)

- Sempre prepared statements per input esterni.
- Escape dell'output con `htmlspecialchars()` per prevenire XSS.
- Gestisci gli errori con try/catch e logga gli errori sensibili solo in sviluppo.
- Valuta l'uso di `password_hash()` per password reali (qui non necessarie).

---

## 9. Esempio CSV (riga completa)

```
Mario,Rossi,Informatica,Bianchi,2024
Luisa,Verdi,Matematica,Rossi,2024
Marco,Bianchi,Informatica,Bianchi,2024
Anna,Neri,Storia,Verdi,2023
```

---

Se vuoi, posso:

- generare lo script `import_csv.php` completo pronto (te lo fornisco),
- creare una versione migliorata dell'interfaccia HTML,
- o esportare questo README in un file scaricabile (lo trovi qui nella canvas).

Buono studio!

