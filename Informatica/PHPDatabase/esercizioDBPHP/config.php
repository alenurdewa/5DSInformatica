<?php
$host = "localhost";
$user = "root";
$password = "";

$pdo = new PDO("mysql:host=$host;charset=utf8mb4", $user, $password);
$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$pdo->exec("CREATE DATABASE IF NOT EXISTS biblioteca");
$pdo->exec("USE biblioteca");

$pdo->exec("
    CREATE TABLE IF NOT EXISTS libri (
        id INT AUTO_INCREMENT PRIMARY KEY,
        autore VARCHAR(255),
        nazionalita VARCHAR(100),
        titolo VARCHAR(255),
        editore VARCHAR(255),
        pagine INT
    )
");
?>