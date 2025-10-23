<?php

session_start();

// $_SERVER, $_GET, $_POST, $_REQUEST, $_SESSION, $_FILES, $_COOKIE
// $GLOBALS

$nome = $_POST['nome'] ?? 'Non dato';
$eta = isset($_POST['eta']) ? (int)$_POST['eta'] : 'NULL'; //isset controlla se c'è il valore, se c'è lo setta ad intero, se no lo setta a null

$mail =filter_input(INPUT_POST, "mail", FILTER_VALIDATE_EMAIL); 
//var_dump($GLOBALS);

echo "<br>Registrazione effettuata da $nome, con età $eta e email $mail";

echo "<br>";
echo "<br>";
echo "<br>";

//var_dump($_SERVER);
$metodo = $_SERVER['REQUEST_METHOD'];
$user_a = $_SERVER['HTTP_USER_AGENT'];
$ip = $_SERVER['REMOTE_ADDR'];

echo "Il metodo è: $metodo, ip $ip, $user_a";


$_SESSION["visite"] = 1;
$_SESSION["nomeUtente"] = $nome;

echo "<br>";
var_dump($_SESSION);
//session

session_destroy();
?>