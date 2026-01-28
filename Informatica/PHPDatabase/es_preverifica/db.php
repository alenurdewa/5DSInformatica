<?php 
$user = "root";
$password ="";
$host = "localhost";
$dsn = "mysql:host=$host;port=3306;charset=utf8";

$pdo = new PDO($dsn, $user, $password);

$pdo->exec("CREATE DATABASE IF NOT EXISTS SistemaInformativo");
$pdo->exec("USE SistemaInformativo");