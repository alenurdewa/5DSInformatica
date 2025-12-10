<?php

$host = "localhost";
$user = "root";
$password ="";
$dsn = "mysql:host=$host;port:3306;charset=utf8"; //specifica il nome del driver che voglia utilizzare

$pdo = new PDO($dsn, $user, $password);

$pdo->exec("CREATE DATABASE IF NOT EXISTS prova");

$pdo->exec("USE prova");

$pdo->exec("CREATE TABLE IF NOT EXISTS user(
    id INT AUTO_INCREMENT PRIMARY KEY, 
    nome VARCHAR(20) NOT NULL,
    cognome VARCHAR(20) NOT NULL
    )
");


?>