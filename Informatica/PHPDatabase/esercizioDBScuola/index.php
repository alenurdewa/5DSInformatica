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
