<?php

function inserireNuovoPartecipante ($nome, $cognome, $pdo){

    $sql = "INSERT INTO PARTECIPANTE (Nome, Cognome) VALUES (:nome, :cognome)";
    $stmt = $pdo->prepare($sql);
    $stmt ->bindParam(":nome", $nome);
    $stmt -> bindParam(":cognome", $cognome);
    $stmt -> execute();
}


function inserireNuovoEvento ($titolo, $dataEvento, $pdo){

    $sql = "INSERT INTO EVENTO (DataEvento, Titolo) VALUES (:dataEvento, :titolo)";
    $stmt = $pdo->prepare($sql);
    $stmt ->bindParam(":dataEvento", $dataEvento);
    $stmt -> bindParam(":titolo", $titolo);
    $stmt -> execute();
}