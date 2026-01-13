<?php
require 'config.php';

function getStudenti($pdo) {
    return $pdo->query("SELECT * FROM studenti")->fetchAll();
}

function getCorsi($pdo) {
    return $pdo->query("SELECT * FROM corsi")->fetchAll();
}

function getDocenti($pdo) {
    return $pdo->query("SELECT * FROM docenti")->fetchAll();
}

function getTutto($pdo) {
    $sql = "SELECT s.nome, s.cognome, c.nome as corso, d.nome as docente 
            FROM studenti s 
            JOIN corsi c ON s.corso_id = c.id 
            JOIN docenti d ON c.docente_id = d.id";
    return $pdo->query($sql)->fetchAll();
}

function cercaStudente($pdo, $cognome) {
    $sql = "SELECT s.nome, s.cognome, c.nome as corso, d.nome as docente 
            FROM studenti s
            JOIN corsi c ON s.corso_id = c.id
            JOIN docenti d ON c.docente_id = d.id
            WHERE s.cognome LIKE ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute(["%$cognome%"]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}
?>