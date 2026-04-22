<?php
/*
====================================
FUNZIONI DATABASE OPENDATA
====================================
*/

require "connessioneDB.php";

/**
 * 1. Visualizza tutti i civici con relativa strada e sobborgo
 */
function getTuttiCivici($pdo) {
    $sql = "SELECT e.NumCivico, s.NomeVia, s.CodiceVia, e.Sobborgo, e.Cap
            FROM EDIFICIO e
            JOIN STRADA s ON e.Strada = s.CodiceVia
            ORDER BY s.NomeVia, e.NumCivico";
    return $pdo->query($sql)->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 2. Ogni rastrelliera con strada e civico associato
 */
function getTutteRastrelliere($pdo) {
    $sql = "SELECT r.Id, r.Tipologia, r.NPosti, r.TotBici, s.NomeVia as 'Strada', r.Civico, r.Anno
            FROM RASTRELLIERA r
            LEFT JOIN STRADA s ON r.CodiceVia = s.CodiceVia
            ORDER BY s.NomeVia, r.Id";
    return $pdo->query($sql)->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 3 & 12. Statistiche rastrelliere per Sobborgo
 */
function getStatistichePerSobborgo($pdo) {
    $sql = "SELECT e.Sobborgo, 
                   COUNT(DISTINCT r.Id) as NumRastrelliere, 
                   SUM(r.NPosti) as TotalePosti, 
                   SUM(r.TotBici) as TotaleBici,
                   AVG(r.NPosti) as MediaPosti
            FROM EDIFICIO e
            LEFT JOIN RASTRELLIERA r ON e.NumCivico = r.Civico AND e.Strada = r.CodiceVia
            GROUP BY e.Sobborgo
            ORDER BY NumRastrelliere DESC";
    return $pdo->query($sql)->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 4a. Elenca rastrelliere per zona specifica
 */
function getRastrellierePerSobborgo($pdo, $sobborgo) {
    $sql = "SELECT r.Id, r.Tipologia, r.NPosti, s.NomeVia, e.Sobborgo
            FROM RASTRELLIERA r
            JOIN STRADA s ON r.CodiceVia = s.CodiceVia
            JOIN EDIFICIO e ON r.Civico = e.NumCivico AND r.CodiceVia = e.Strada
            WHERE e.Sobborgo = :sobborgo
            ORDER BY s.NomeVia";
    $stmt = $pdo->prepare($sql);
    $stmt->execute(['sobborgo' => $sobborgo]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 4b. Elenca rastrelliere per tipologia specifica
 */
function getRastrellierePerTipologia($pdo, $tipologia) {
    $sql = "SELECT r.Id, r.Tipologia, r.NPosti, s.NomeVia, e.Sobborgo
            FROM RASTRELLIERA r
            JOIN STRADA s ON r.CodiceVia = s.CodiceVia
            JOIN EDIFICIO e ON r.Civico = e.NumCivico AND r.CodiceVia = e.Strada
            WHERE r.Tipologia = :tipologia
            ORDER BY s.NomeVia";
    $stmt = $pdo->prepare($sql);
    $stmt->execute(['tipologia' => $tipologia]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 5. Trova la zona (sobborgo) con il maggior numero di posti bici
 */
function getSobborgoTopPosti($pdo) {
    $sql = "SELECT e.Sobborgo, SUM(r.NPosti) as TotalePosti
            FROM EDIFICIO e
            LEFT JOIN RASTRELLIERA r ON e.NumCivico = r.Civico AND e.Strada = r.CodiceVia
            GROUP BY e.Sobborgo
            ORDER BY TotalePosti DESC
            LIMIT 1";
    return $pdo->query($sql)->fetch(PDO::FETCH_ASSOC);
}

/**
 * 6. Conta i civici per ciascuna strada
 */
function getConteggioCiviciPerStrada($pdo) {
    $sql = "SELECT s.NomeVia, COUNT(e.NumCivico) as NumCivici
            FROM STRADA s
            LEFT JOIN EDIFICIO e ON s.CodiceVia = e.Strada
            GROUP BY s.CodiceVia, s.NomeVia
            ORDER BY NumCivici DESC";
    return $pdo->query($sql)->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 7 & 8. Strade con almeno una rastrelliera e relativi conteggi
 */
function getStradeConRastrelliere($pdo) {
    $sql = "SELECT s.CodiceVia, s.NomeVia, COUNT(r.Id) as NumRastrelliere, SUM(r.NPosti) as TotalePosti
            FROM STRADA s
            INNER JOIN RASTRELLIERA r ON s.CodiceVia = r.CodiceVia
            GROUP BY s.CodiceVia, s.NomeVia
            ORDER BY NumRastrelliere DESC";
    return $pdo->query($sql)->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 9. Elenca i civici senza alcuna rastrelliera
 */
function getCiviciSenzaRastrelliera($pdo) {
    $sql = "SELECT e.NumCivico, s.NomeVia, e.Sobborgo
            FROM EDIFICIO e
            JOIN STRADA s ON e.Strada = s.CodiceVia
            WHERE NOT EXISTS (
                SELECT 1 FROM RASTRELLIERA r 
                WHERE r.Civico = e.NumCivico AND r.CodiceVia = e.Strada
            )
            ORDER BY s.NomeVia";
    return $pdo->query($sql)->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 10. Rastrelliere installate in un determinato anno
 */
function getRastrellierePerAnno($pdo, $anno) {
    $sql = "SELECT r.Id, r.Tipologia, s.NomeVia, r.Anno
            FROM RASTRELLIERA r
            LEFT JOIN STRADA s ON r.CodiceVia = s.CodiceVia
            WHERE r.Anno = :anno";
    $stmt = $pdo->prepare($sql);
    $stmt->execute(['anno' => $anno]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

/**
 * 11. Dettaglio completo di una strada (civici e rastrelliere) tramite CodiceVia
 */
function getDettaglioStrada($pdo, $codiceVia) {
    $sql = "SELECT s.NomeVia, e.NumCivico, r.Id as RastrellieraId, r.Tipologia
            FROM STRADA s
            LEFT JOIN EDIFICIO e ON s.CodiceVia = e.Strada
            LEFT JOIN RASTRELLIERA r ON e.NumCivico = r.Civico AND e.Strada = r.CodiceVia
            WHERE s.CodiceVia = :codiceVia
            ORDER BY e.NumCivico";
    $stmt = $pdo->prepare($sql);
    $stmt->execute(['codiceVia' => $codiceVia]);
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}


?>