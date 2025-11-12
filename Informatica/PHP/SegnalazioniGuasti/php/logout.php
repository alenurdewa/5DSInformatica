<?php
include("config.php");

// Cancella tutti i dati di sessione
$_SESSION = [];
session_destroy();

echo "<h1>Logout effettuato</h1>";
echo "<p>I tuoi dati di login sono stati rimossi, ma le statistiche restano salvate.</p>";
echo "<a href='login.php'>Torna al login</a>";
