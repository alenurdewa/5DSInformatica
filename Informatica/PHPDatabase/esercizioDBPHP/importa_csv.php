<?php
require "config.php";

if (($handle = fopen("libri.csv", "r")) !== FALSE) {
    fgetcsv($handle);

    $stmt = $pdo->prepare(
        "INSERT INTO libri (autore, nazionalita, titolo, editore, pagine)
         VALUES (?, ?, ?, ?, ?)"
    );

    while (($data = fgetcsv($handle, 1000, ",")) !== FALSE) {
        $stmt->execute($data);
    }

    fclose($handle);
    echo "Importazione completata.";
} else {
    echo "Errore apertura CSV.";
}
?>