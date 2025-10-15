<?php

include("funzioni.php");

$utente = ["nome" => "Alessandro", "cognome" => "Berti", "eta" =>18];

$utente = trasforma($utente);

[$n, $eta, $isMagg] = $utente;

if ($isMagg) {
    echo "L'utente $n che $eta è maggiorenne";
} else {
    echo "L'utente $n che $eta è minorenne";
}


?>