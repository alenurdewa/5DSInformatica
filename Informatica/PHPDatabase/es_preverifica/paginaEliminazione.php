<?php
require "config.php"; // Se hai la connessione qui
require "db.php";     // O qui
require "funzioniEliminazione.php";

$messaggio = "";

// LOGICA DI GESTIONE DEI FORM
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
    // 1. Eliminazione Partecipante
    if (isset($_POST['btnEliminaPartecipante']) && isset($_POST['idPartecipante'])) {
        eliminaPartecipante($_POST['idPartecipante'], $pdo);
        $messaggio = "Partecipante (e relative prenotazioni) eliminato con successo!";
    }

    // 2. Eliminazione Evento
    if (isset($_POST['btnEliminaEvento']) && isset($_POST['idEvento'])) {
        eliminaEvento($_POST['idEvento'], $pdo);
        $messaggio = "Evento (e relative prenotazioni) eliminato con successo!";
    }

    // 3. Eliminazione Prenotazione Singola
    if (isset($_POST['btnEliminaPrenotazione']) && isset($_POST['idPartPren']) && isset($_POST['idEventoPren'])) {
        eliminaPrenotazione($_POST['idPartPren'], $_POST['idEventoPren'], $pdo);
        $messaggio = "Prenotazione cancellata con successo!";
    }
}

// RECUPERO DATI PER RIEMPIRE LE SELECT
$partecipanti = $pdo->query("SELECT * FROM PARTECIPANTE")->fetchAll();
$eventi = $pdo->query("SELECT * FROM EVENTO")->fetchAll();

// Query un po' più complessa per mostrare le prenotazioni esistenti in modo leggibile
$sqlPren = "SELECT PR.CodPartecipante, PR.CodEvento, P.Nome, P.Cognome, E.Titolo 
            FROM PRENOTAZIONE PR
            JOIN PARTECIPANTE P ON PR.CodPartecipante = P.CodPartecipante
            JOIN EVENTO E ON PR.CodEvento = E.CodEvento";
$prenotazioni = $pdo->query($sqlPren)->fetchAll();
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Eliminazione Dati</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .box-elimina { border: 1px solid #cc0000; padding: 15px; margin-bottom: 20px; border-radius: 5px; background-color: #fff0f0; }
        h2 { color: #cc0000; margin-top: 0; }
        select, input[type=submit] { padding: 5px; margin: 5px 0; }
        .msg { color: green; font-weight: bold; padding: 10px; border: 1px solid green; background-color: #eaffea; }
    </style>
</head>
<body>

    <a href="index.php">Torna alla Home</a>
    <h1>Gestione Eliminazioni</h1>

    <?php if($messaggio): ?>
        <div class="msg"><?php echo $messaggio; ?></div>
        <br>
    <?php endif; ?>

    <div class="box-elimina">
        <h2>Elimina Partecipante</h2>
        <p><em>Attenzione: Verranno eliminate anche tutte le sue prenotazioni.</em></p>
        <form action="" method="POST">
            <label>Seleziona Persona:</label><br>
            <select name="idPartecipante">
                <?php foreach($partecipanti as $p): ?>
                    <option value="<?php echo $p['CodPartecipante']; ?>">
                        <?php echo $p['Nome'] . " " . $p['Cognome']; ?>
                    </option>
                <?php endforeach; ?>
            </select>
            <br>
            <input type="submit" name="btnEliminaPartecipante" value="Elimina Partecipante">
        </form>
    </div>

    <div class="box-elimina">
        <h2>Elimina Evento</h2>
        <p><em>Attenzione: Verranno eliminate anche tutte le prenotazioni per questo evento.</em></p>
        <form action="" method="POST">
            <label>Seleziona Evento:</label><br>
            <select name="idEvento">
                <?php foreach($eventi as $e): ?>
                    <option value="<?php echo $e['CodEvento']; ?>">
                        <?php echo $e['Titolo'] . " (" . $e['DataEvento'] . ")"; ?>
                    </option>
                <?php endforeach; ?>
            </select>
            <br>
            <input type="submit" name="btnEliminaEvento" value="Elimina Evento">
        </form>
    </div>

    <div class="box-elimina">
        <h2>Elimina Singola Prenotazione</h2>
        <p><em>Cancella solo la prenotazione, mantenendo l'utente e l'evento.</em></p>
        <form action="" method="POST">
            <label>Seleziona Prenotazione esistente:</label><br>
            <select name="prenotazioneCombinata">
                 </select>
            
            <label>Utente:</label>
             <select name="idPartPren">
                <?php foreach($partecipanti as $p): ?>
                    <option value="<?php echo $p['CodPartecipante']; ?>">
                        <?php echo $p['Nome'] . " " . $p['Cognome']; ?>
                    </option>
                <?php endforeach; ?>
             </select>
             
             <label>Evento:</label>
             <select name="idEventoPren">
                <?php foreach($eventi as $e): ?>
                    <option value="<?php echo $e['CodEvento']; ?>">
                        <?php echo $e['Titolo']; ?>
                    </option>
                <?php endforeach; ?>
             </select>

            <br>
            <input type="submit" name="btnEliminaPrenotazione" value="Cancella Prenotazione">
        </form>
    </div>

</body>
</html>