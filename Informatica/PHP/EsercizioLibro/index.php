<?php
$autore = [
    "nome" => "Umberto",
    "cognome" => "Eco",
    "nazionalità" => "Italiana"
];

$libro = [
    "titolo" => "Il nome della rosa",
    "autore" => $autore["nome"] . " " . $autore["cognome"],
    "prezzo" => 12.50,
    "anno" => 1980,
    "copieVendute" => 5000000
];

[
    "titolo" => $titolo,
    "autore" => $autoreNome,
    "prezzo" => $prezzo,
    "anno" => $anno,
    "copieVendute" => $copieVendute
] = $libro;

$economico = $prezzo < 15 ? "economico" : "non economico";
$bestseller = $copieVendute > 2000 ? "bestseller" : "non bestseller";

echo "<html><head><title>Esercizio Libro</title></head><body>";
echo "<h2>Informazioni sul libro</h2>";
echo "<p>Il libro '<strong>$titolo</strong>' di <strong>$autoreNome</strong>, pubblicato nel <strong>$anno</strong>, costa <strong>" . number_format($prezzo, 2, ',', '.') . " €</strong>, è $economico e $bestseller.</p>";
echo "</body></html>";
?>
