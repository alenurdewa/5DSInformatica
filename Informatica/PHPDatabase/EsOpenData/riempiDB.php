<?php
require "createDB.php";

// ==========================================
// 1. IMPORTAZIONE STRADA
// ==========================================
$sqlStrada = "INSERT IGNORE INTO STRADA (CodiceVia, NomeVia, IsSensoUnico, NomeStrada) 
              VALUES (:codiceVia, :nomeVia, :isSensoUnico, :nomeStrada)";
$stmtStrada = $pdo->prepare($sqlStrada);

$fileStrada = fopen("csv/Strada.csv", "r");

if ($fileStrada !== false) {
    fgetcsv($fileStrada, 1000, ";"); // Salta l'intestazione

    while (($row = fgetcsv($fileStrada, 1000, ";")) !== false) {
        // Trim dei campi per rimuovere spazi extra
        $row = array_map('trim', $row);
        
        if (count($row) >= 4) {
            [$codiceVia, $nomeVia, $isSensoUnico, $nome_strada] = $row;

            // TRUCCO PER ID = 0: Trasforma il nome della strada in un numero intero univoco
            if ($codiceVia == 0) {
                // abs() assicura che il numero generato sia positivo
                $codiceVia = abs(crc32($nome_strada));
            }

            $sensoUnicoBool = (strtolower(trim($isSensoUnico)) == 'true' || $isSensoUnico == '1') ? 1 : 0;

            $stmtStrada->execute([
                ':codiceVia'    => $codiceVia,
                ':nomeVia'      => $nomeVia,
                ':isSensoUnico' => $sensoUnicoBool,
                ':nomeStrada'   => $nome_strada
            ]);
        }
    }
    fclose($fileStrada);
    echo "Importazione STRADA completata!<br>";
}

// ==========================================
// 2. IMPORTAZIONE EDIFICIO (Con controllo della COPPIA)
// ==========================================
// INSERT IGNORE controllerà che la COPPIA (NumCivico, Strada) non sia un doppione.
$sqlEdificio = "INSERT IGNORE INTO EDIFICIO (NumCivico, Strada, Cap, Sobborgo) 
                VALUES (:num_civico, :strada, :cap, :sobborgo)";
$stmtEdificio = $pdo->prepare($sqlEdificio);

$fileEdificio = fopen("csv/Edificio.csv", "r"); // NOTA: File corretto per gli edifici

if ($fileEdificio !== false) {
    fgetcsv($fileEdificio, 1000, ";");

    while (($row = fgetcsv($fileEdificio, 1000, ";")) !== false) {
        // Trim dei campi per rimuovere spazi extra
        $row = array_map('trim', $row);
        
        if (count($row) >= 4) {
            [$Num_Civico, $strada, $cap, $sobborgo] = $row;
            
            // Salta righe con dati incompleti
            if (empty($Num_Civico) || empty($strada) || empty($cap)) {
                continue;
            }

            $stmtEdificio->execute([
                ':num_civico' => $Num_Civico,
                ':strada'     => $strada,
                ':cap'        => $cap,
                ':sobborgo'   => $sobborgo
            ]);
        }
    }
    fclose($fileEdificio);
    echo "Importazione EDIFICIO completata!<br>";
}

// ==========================================
// 3. IMPORTAZIONE RASTRELLIERA
// ==========================================
// INSERT IGNORE controllerà la chiave primaria (Id)
$sqlRastrelliera = "INSERT IGNORE INTO RASTRELLIERA (Id, Tipologia, NPosti, Anno, TotBici, CodiceVia, Civico, Tipo_Edificio) 
                    VALUES (:id, :tipologia, :nPosti, :anno, :totBici, :codiceVia, :civico, :tipoEdificio)";
$stmtRastrelliera = $pdo->prepare($sqlRastrelliera);

$fileRastrelliera = fopen("csv/Rastrelliere.csv", "r");

if ($fileRastrelliera !== false) {
    fgetcsv($fileRastrelliera, 1000, ";"); 

    while (($row = fgetcsv($fileRastrelliera, 1000, ";")) !== false) {
        // Trim dei campi e rimozione valori vuoti trailing
        $row = array_map('trim', $row);
        $row = array_filter($row, 'strlen'); // Rimuove elementi vuoti
        
        if (count($row) >= 8) {
            [$id, $tipologia, $n_posti, $anno, $tot_bici, $codice_via, $civico, $edificio] = array_pad($row, 8, '');
            
            // Salta se manca l'ID (campo obbligatorio)
            if (empty($id)) {
                continue;
            }

            $stmtRastrelliera->execute([
                ':id'           => $id,
                ':tipologia'    => $tipologia,
                ':nPosti'       => $n_posti,
                ':anno'         => $anno,
                ':totBici'      => $tot_bici,
                ':codiceVia'    => $codice_via,
                ':civico'       => $civico,
                ':tipoEdificio' => $edificio
            ]);
        }
    }
    fclose($fileRastrelliera);
    echo "Importazione RASTRELLIERA completata!<br>";
}



?>