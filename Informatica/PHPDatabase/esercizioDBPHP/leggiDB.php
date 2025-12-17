<?php
require "config.php";

$stmt = $pdo->query("SELECT titolo, autore FROM libri");
$righe = $stmt->fetchAll(PDO::FETCH_ASSOC);

foreach ($righe as $riga) {
    echo $riga['titolo'] . " - " . $riga['autore'] . "<br>";
}
