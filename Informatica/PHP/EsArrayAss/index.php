<?php
echo "<h1>Utilizzo di funzioni con file esterno </h1>";

include("funzioni.php");

// Array di esempio
$numeri = [67,69,104,41,420];

// Richiama le funzioni definite in funzioni.php
echo "Array: " . implode(", ", $numeri) . "<br>";
echo "Somma: " . somma($numeri) . "<br>";
echo "Media: " . media($numeri) . "<br>";
echo "Massimo: " . massimo($numeri) . "<br>";
echo "Minimo: " . minimo($numeri) . "<br>";
?>