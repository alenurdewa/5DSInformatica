<?php
require "db.php";

$pdo->exec("CREATE TABLE IF NOT EXISTS PARTECIPANTE(
    CodPartecipante INT AUTO_INCREMENT PRIMARY KEY,
    Nome varchar(20) NOT NULL,
    Cognome varchar(20) NOT NULL
)");

$pdo->exec("CREATE TABLE IF NOT EXISTS EVENTO(
    CodEvento INT AUTO_INCREMENT PRIMARY KEY,
    DataEvento DATE NOT NULL,
    Titolo varchar(20) NOT NULL
)");


$pdo->exec("CREATE TABLE IF NOT EXISTS PRENOTAZIONE(
    CodPartecipante INT NOT NULL,
    CodEvento INT NOT NULL,
    DataPrenotazione DATE NOT NULL,
    PRIMARY KEY (CodPartecipante, CodEvento), 
    FOREIGN KEY (CodPartecipante) REFERENCES PARTECIPANTE(CodPartecipante) ON DELETE CASCADE,
    FOREIGN KEY (CodEvento) REFERENCES EVENTO(CodEvento) ON DELETE CASCADE
)");
?>