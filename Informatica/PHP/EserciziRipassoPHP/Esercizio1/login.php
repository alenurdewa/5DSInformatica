<?php
session_start();

$username = $_POST['username'];
$password = $_POST['password'];

if ($username == 'admin' && $password == '1234') {

    // Salvo chi ha fatto accesso
    $_SESSION['user'] = $username;

    // Reindirizzo all'area riservata
    header('Location: area_riservata.php');
    exit;

} else {

    echo "Credenziali scorrette<br>";
    echo '<a href="index.php">Torna al login</a>';

}
?>
