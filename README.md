# Istituto Scolastico – Import CSV con PHP e PDO

> Piano del Genio del Male: importare un CSV, popolare il database e dominare... ehm, *studiare*.
> Segui il piano passo passo e copia il codice dove indicato. Non serve essere buoni, serve essere precisi.

## Obiettivo
Collegare PHP (PDO) a MySQL, creare il database e le tabelle, importare i dati dal CSV, mostrare i risultati e avere una vista completa con JOIN. Tutto in un file PHP pronto da eseguire.

---

## File CSV di esempio (`istitutoScolastico.csv`)
```
nome_studente,cognome_studente,corso,docente,anno
Mario,Rossi,Informatica,Bianchi,2024
Luisa,Verdi,Matematica,Rossi,2024
Marco,Bianchi,Informatica,Bianchi,2024
Anna,Neri,Storia,Verdi,2023
```

La prima riga è l'intestazione e viene ignorata dallo script.

---

## Il file PHP unico (copia-incolla e distruggi... il problema)
Salva questo contenuto in `importa_istituto.php` nella stessa cartella del CSV e aprilo dal browser.

```php
<?php

$dsn = "mysql:host=localhost;port=3306;charset=utf8";
$user = "Alessandro";
$password = "miao";

$pdo = new PDO($dsn, $user, $password);

$pdo->exec("CREATE DATABASE IF NOT EXISTS istitutoScolastico");
$pdo->exec("USE istitutoScolastico");

$pdo->exec("
CREATE TABLE IF NOT EXISTS docenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50)
)
");

$pdo->exec("
CREATE TABLE IF NOT EXISTS corsi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    anno INT,
    docente_id INT,
    FOREIGN KEY (docente_id) REFERENCES docenti(id)
)
");

$pdo->exec("
CREATE TABLE IF NOT EXISTS studenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    corso_id INT,
    FOREIGN KEY (corso_id) REFERENCES corsi(id)
)
");

$file = fopen("istitutoScolastico.csv", "r");
fgetcsv($file);

while (($row = fgetcsv($file, 1000, ",")) !== false) {

    [$nome, $cognome, $corso, $docente, $anno] = $row;

    $stmt = $pdo->prepare("SELECT id FROM docenti WHERE nome = ?");
    $stmt->execute([$docente]);
    $docente_id = $stmt->fetchColumn();

    if (!$docente_id) {
        $stmt = $pdo->prepare("INSERT INTO docenti (nome) VALUES (?)");
        $stmt->execute([$docente]);
        $docente_id = $pdo->lastInsertId();
    }

    $stmt = $pdo->prepare("SELECT id FROM corsi WHERE nome = ? AND anno = ?");
    $stmt->execute([$corso, $anno]);
    $corso_id = $stmt->fetchColumn();

    if (!$corso_id) {
        $stmt = $pdo->prepare("INSERT INTO corsi (nome, anno, docente_id) VALUES (?, ?, ?)");
        $stmt->execute([$corso, $anno, $docente_id]);
        $corso_id = $pdo->lastInsertId();
    }

    $stmt = $pdo->prepare("INSERT INTO studenti (nome, cognome, corso_id) VALUES (?, ?, ?)");
    $stmt->execute([$nome, $cognome, $corso_id]);
}

fclose($file);

echo "<h2>Vista completa (JOIN)</h2>";

$stmt = $pdo->query("
SELECT 
    studenti.nome,
    studenti.cognome,
    corsi.nome AS corso,
    corsi.anno,
    docenti.nome AS docente
FROM studenti
JOIN corsi ON studenti.corso_id = corsi.id
JOIN docenti ON corsi.docente_id = docenti.id
");

foreach ($stmt as $row) {
    echo $row["nome"] . " " .
         $row["cognome"] . " - " .
         $row["corso"] . " (" .
         $row["anno"] . ") - " .
         $row["docente"] . "<br>";
}

?>
```

---

## Ricerca per cognome (facoltativa)
Se vuoi aggiungere una pagina con una ricerca per cognome, crea `ricerca.php` con questo codice:

```php
<?php
$dsn = "mysql:host=localhost;port=3306;dbname=istitutoScolastico;charset=utf8";
$user = "Alessandro";
$password = "miao";
$pdo = new PDO($dsn, $user, $password);

if (isset($_GET['cognome'])) {
    $term = "%" . $_GET['cognome'] . "%";
    $stmt = $pdo->prepare("SELECT studenti.nome, studenti.cognome, corsi.nome AS corso, corsi.anno, docenti.nome AS docente
                          FROM studenti
                          JOIN corsi ON studenti.corso_id = corsi.id
                          JOIN docenti ON corsi.docente_id = docenti.id
                          WHERE studenti.cognome LIKE ?");
    $stmt->execute([$term]);
    $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
} else {
    $results = [];
}
?>
<form method="get">
    <input name="cognome" placeholder="Cognome">
    <button>Cerca</button>
</form>
<?php foreach ($results as $r): ?>
    <?= htmlspecialchars($r['nome']) ?> <?= htmlspecialchars($r['cognome']) ?> - <?= htmlspecialchars($r['corso']) ?> (<?= htmlspecialchars($r['anno']) ?>) - <?= htmlspecialchars($r['docente']) ?><br>
<?php endforeach; ?>
```

---

## Query JOIN spiegata (in parole semplici)
La query unisce tre tabelle usando le chiavi esterne per ottenere una riga "completa" per ogni studente. In pratica mette insieme:
- il nome dello studente (da `studenti`)
- il corso e l'anno (da `corsi`)
- il docente (da `docenti`)

Esempio di query usata:

```sql
SELECT studenti.nome, studenti.cognome, corsi.nome AS corso, corsi.anno, docenti.nome AS docente
FROM studenti
JOIN corsi ON studenti.corso_id = corsi.id
JOIN docenti ON corsi.docente_id = docenti.id;
```

---

## Consigli pratici del Genio del Male
- Assicurati che MySQL sia avviato e che l'utente e la password nel file PHP siano corretti.
- Metti il file PHP e il CSV nella stessa cartella (htdocs per XAMPP).
- Se ricevi errori, abilita visualizzazione errori in PHP o guarda i log di Apache/MySQL.
- Per sicurezza, non lasciare questo script su un server pubblico senza protezione.

---

## Licenza
Per i tuoi malefici esercizi scolastici — MIT, fai quello che vuoi.

---
