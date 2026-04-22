<?php
require "connessioneDB.php";
require "Queries.php";

/**
 * Caricamento dati tramite le funzioni definite in Queries.php
 * Nota: assicurati che in Queries.php le funzioni accettino $pdo come argomento.
 */


$sqlEdifici = "SELECT * FROM EDIFICIO";
$stmtEdifici = $pdo->query($sqlEdifici);
$edifici = $stmtEdifici->fetchAll();

$sqlStrade = "SELECT * FROM STRADA";
$stmtStrade = $pdo->query($sqlStrade);
$strade = $stmtStrade->fetchAll();

$sqlRastrelliere = "SELECT * FROM RASTRELLIERA";
$stmtRastrelliere = $pdo->query($sqlRastrelliere);
$rastrelliere = $stmtRastrelliere->fetchAll();



?>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Open Data</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 20px;
            background-color: #f4f4f9;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        /* Container per affiancare le tabelle */
        .container-tabelle {
            display: flex;
            flex-wrap: wrap; /* Permette di andare a capo su schermi piccoli */
            gap: 30px;      /* Spazio tra le colonne */
            justify-content: center;
            align-items: flex-start;
        }

        /* Contenitore della singola tabella */
        .sezione-tabella {
            flex: 1;
            min-width: 400px; /* Larghezza minima prima di andare a capo */
            background: white;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        h2 {
            border-bottom: 2px solid #007bff;
            padding-bottom: 5px;
            color: #007bff;
        }
    </style>
</head>
<body>

    <h1>Open Data Dashboard</h1>

    <div class="container-tabelle">
        
        <div class="sezione-tabella">
            <h2>Edifici</h2>
            <table>
                <thead>
                    <tr>
                        <th>Num Civico</th>
                        <th>Strada</th>
                        <th>Sobborgo</th>
                        <th>Cap</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($edifici as $edificio): ?>
                        <tr>
                            <td><?php echo htmlspecialchars($edificio['NumCivico']); ?></td>
                            <td><?php echo htmlspecialchars($edificio['Strada']); ?></td>
                            <td><?php echo htmlspecialchars($edificio['Sobborgo']); ?></td>
                            <td><?php echo htmlspecialchars($edificio['Cap']); ?></td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>

        <div class="sezione-tabella">
            <h2>Strade</h2>
            <table>
                <thead>
                    <tr>
                        <th>Codice Via</th>
                        <th>Nome Via</th>
                        <th>Senso Unico</th>
                        <th>Nome Strada</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($strade as $strada): ?>
                        <tr>
                            <td><?php echo htmlspecialchars($strada['CodiceVia']); ?></td>
                            <td><?php echo htmlspecialchars($strada['NomeVia']); ?></td>
                            <td><?php echo $strada['IsSensoUnico'] ? 'Sì' : 'No'; ?></td>
                            <td><?php echo htmlspecialchars($strada['NomeStrada']); ?></td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>
        
        <div class="sezione-tabella">
            <h2>Rastrelliere</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tipologia</th>
                        <th>Anno</th>
                        <th>Numero Posti</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($rastrelliere as $rastrelliera): ?>
                        <tr>
                            <td><?php echo htmlspecialchars($rastrelliera['Id']); ?></td>
                            <td><?php echo htmlspecialchars($rastrelliera['Tipologia']); ?></td>
                            <td><?php echo htmlspecialchars($rastrelliera['Anno']); ?></td>
                            <td><?php echo htmlspecialchars($rastrelliera['NPosti']); ?></td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>
        </div>

    </div>

</body>
</html>