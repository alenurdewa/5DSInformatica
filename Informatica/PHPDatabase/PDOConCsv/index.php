<?php

$host = "localhost";
$user = "root";
$password = "";
$dsn = "mysql:host=$host;port=3306;charset=utf8";

$pdo = new PDO($dsn, $user, $password);


$pdo->exec("CREATE DATABASE IF NOT EXISTS prova");
$pdo->exec("USE prova");

$pdo->exec("CREATE TABLE IF NOT EXISTS user(
    id INT AUTO_INCREMENT PRIMARY KEY, 
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL
    )
");



$csvFile = "users.csv"; 

if (file_exists($csvFile)) {

    $handle = fopen($csvFile, "r");

    if ($handle) {

        while (($line = fgets($handle)) !== false) {

            $line = trim($line);

            $parts = explode(",", $line);

            $nome = trim($parts[0]);
            $cognome = trim($parts[1]);

            $stmt = $pdo->prepare("INSERT INTO user (nome, cognome) VALUES (?, ?)");
            $stmt->execute([$nome, $cognome]);
        }

        fclose($handle);
    }
}

echo "Importazione completata!";
?>
