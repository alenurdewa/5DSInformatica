<?php
require "config.php";

$stmt = $pdo->query("SELECT titolo, autore FROM libri");
$righe = $stmt->fetchAll();

foreach ($righe as $riga) {
    echo $riga['titolo'] . " - " . $riga['autore'] . "<br>";
}


foreach ($righe as $riga) {
    $stmt = $pdo->prepare("INSERT INTO libri (autore, nazionalita, titolo, editore, pagine) 
                           VALUES (?, ?, ?, ?, ?)");
    $stmt->execute([
        $riga['autore'],
        $riga['nazionalita'],
        $riga['titolo'],
        $riga['editore'],
        $riga['pagine']
    ]);
}

