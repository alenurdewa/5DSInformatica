<?php

function eliminaPartecipante($id, $pdo) {
    // Grazie al CASCADE, basta eliminare il partecipante e le prenotazioni spariranno da sole
    $sql = "DELETE FROM PARTECIPANTE WHERE CodPartecipante = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $id);
    $stmt->execute();
}

function eliminaEvento($id, $pdo) {
    // Grazie al CASCADE, basta eliminare l'evento e le prenotazioni spariranno da sole
    $sql = "DELETE FROM EVENTO WHERE CodEvento = :id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':id', $id);
    $stmt->execute();
}

function eliminaPrenotazione($codPartecipante, $codEvento, $pdo) {
    // Qui cancelliamo solo la relazione specifica, senza toccare Partecipante o Evento
    $sql = "DELETE FROM PRENOTAZIONE WHERE CodPartecipante = :codP AND CodEvento = :codE";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':codP', $codPartecipante);
    $stmt->bindParam(':codE', $codEvento);
    $stmt->execute();
}
?>