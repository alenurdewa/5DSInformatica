<?php

$dsn = "mysql:host=localhost;port=3306;charset=utf8";
$user = "Alessandro";
$password = "miao";

$pdo = new PDO($dsn, $user, $password);

$pdo->exec("CREATE DATABASE");


?>