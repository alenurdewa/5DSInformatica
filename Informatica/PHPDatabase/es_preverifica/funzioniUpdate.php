<?php

function aggiornaPartecipante($id, $nuovoNome, $nuovoCognome, $pdo) {
    $sql = "UPDATE PARTECIPANTE 
            SET Nome = :nome, Cognome = :cognome 
            WHERE CodPartecipante = :id";
    
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':nome', $nuovoNome);
    $stmt->bindParam(':cognome', $nuovoCognome);
    $stmt->bindParam(':id', $id);
    $stmt->execute();
}

function aggiornaEvento($id, $nuovoTitolo, $nuovaData, $pdo) {
    $sql = "UPDATE EVENTO 
            SET Titolo = :titolo, DataEvento = :dataEv 
            WHERE CodEvento = :id";
            
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':titolo', $nuovoTitolo);
    $stmt->bindParam(':dataEv', $nuovaData);
    $stmt->bindParam(':id', $id);
    $stmt->execute();
}

function aggiornaDataPrenotazione($codPartecipante, $codEvento, $nuovaDataPren, $pdo) {
    $sql = "UPDATE PRENOTAZIONE 
            SET DataPrenotazione = :nuovaData 
            WHERE CodPartecipante = :codP AND CodEvento = :codE";
            
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':nuovaData', $nuovaDataPren);
    $stmt->bindParam(':codP', $codPartecipante);
    $stmt->bindParam(':codE', $codEvento);
    $stmt->execute();
}
?>