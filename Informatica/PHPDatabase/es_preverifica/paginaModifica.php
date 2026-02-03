<?php
require "db.php";
require "funzioniUpdate.php";

$messaggio = "";

// --- GESTIONE INVIO DATI (POST) ---
if ($_SERVER["REQUEST_METHOD"] == "POST") {

    // 1. Modifica Partecipante
    if (isset($_POST['btnModificaPartecipante'])) {
        $id = $_POST['idPartecipante'];
        $nome = $_POST['nuovoNome'];
        $cognome = $_POST['nuovoCognome'];
        
        if(!empty($nome) && !empty($cognome)) {
            aggiornaPartecipante($id, $nome, $cognome, $pdo);
            $messaggio = "Partecipante aggiornato correttamente!";
        } else {
            $messaggio = "Errore: Inserire Nome e Cognome.";
        }
    }

    // 2. Modifica Evento
    if (isset($_POST['btnModificaEvento'])) {
        $id = $_POST['idEvento'];
        $titolo = $_POST['nuovoTitolo'];
        $data = $_POST['nuovaDataEvento'];
        
        if(!empty($titolo) && !empty($data)) {
            aggiornaEvento($id, $titolo, $data, $pdo);
            $messaggio = "Evento aggiornato correttamente!";
        } else {
            $messaggio = "Errore: Inserire Titolo e Data.";
        }
    }

    // 3. Modifica Prenotazione (Cambio Data)
    if (isset($_POST['btnModificaPrenotazione'])) {
        $idPart = $_POST['idPartPren'];
        $idEv = $_POST['idEventoPren'];
        $data = $_POST['nuovaDataPren'];
        
        if(!empty($data)) {
            aggiornaDataPrenotazione($idPart, $idEv, $data, $pdo);
            $messaggio = "Data della prenotazione aggiornata!";
        } else {
            $messaggio = "Errore: Inserire una data.";
        }
    }
}

// --- RECUPERO DATI PER LE SELECT ---
$partecipanti = $pdo->query("SELECT * FROM PARTECIPANTE")->fetchAll();
$eventi = $pdo->query("SELECT * FROM EVENTO")->fetchAll();
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Modifica Dati</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .box-modifica { border: 1px solid #0056b3; padding: 15px; margin-bottom: 20px; border-radius: 5px; background-color: #f0f8ff; }
        h2 { color: #0056b3; margin-top: 0; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input[type=text], input[type=date], select { width: 100%; padding: 8px; margin-top: 5px; box-sizing: border-box;}
        input[type=submit] { margin-top: 15px; padding: 10px 20px; background-color: #0056b3; color: white; border: none; cursor: pointer; }
        .msg { color: green; font-weight: bold; padding: 10px; border: 1px solid green; background-color: #eaffea; margin-bottom: 10px;}
    </style>
</head>
<body>

    <a href="index.php">Torna alla Home</a>
    <h1>Gestione Modifiche (Update)</h1>

    <?php if($messaggio): ?>
        <div class="msg"><?php echo $messaggio; ?></div>
    <?php endif; ?>

    <div class="box-modifica">
        <h2>Modifica Partecipante</h2>
        <form action="" method="POST">
            <label>Seleziona Chi Modificare:</label>
            <select name="idPartecipante">
                <?php foreach($partecipanti as $p): ?>
                    <option value="<?php echo $p['CodPartecipante']; ?>">
                        <?php echo "ID: " . $p['CodPartecipante'] . " - " . $p['Nome'] . " " . $p['Cognome']; ?>
                    </option>
                <?php endforeach; ?>
            </select>
            
            <label>Nuovo Nome:</label>
            <input type="text" name="nuovoNome" required>
            
            <label>Nuovo Cognome:</label>
            <input type="text" name="nuovoCognome" required>
            
            <input type="submit" name="btnModificaPartecipante" value="Aggiorna Partecipante">
        </form>
    </div>

    <div class="box-modifica">
        <h2>Modifica Evento</h2>
        <form action="" method="POST">
            <label>Seleziona Evento da Modificare:</label>
            <select name="idEvento">
                <?php foreach($eventi as $e): ?>
                    <option value="<?php echo $e['CodEvento']; ?>">
                        <?php echo "ID: " . $e['CodEvento'] . " - " . $e['Titolo']; ?>
                    </option>
                <?php endforeach; ?>
            </select>
            
            <label>Nuovo Titolo:</label>
            <input type="text" name="nuovoTitolo" required>
            
            <label>Nuova Data Evento:</label>
            <input type="date" name="nuovaDataEvento" required>
            
            <input type="submit" name="btnModificaEvento" value="Aggiorna Evento">
        </form>
    </div>

    <div class="box-modifica">
        <h2>Modifica Data Prenotazione</h2>
        <p><small>Seleziona la coppia Persona/Evento e cambia la data di prenotazione.</small></p>
        <form action="" method="POST">
            
            <label>Utente:</label>
            <select name="idPartPren">
                <?php foreach($partecipanti as $p): ?>
                    <option value="<?php echo $p['CodPartecipante']; ?>">
                        <?php echo $p['Nome'] . " " . $p['Cognome']; ?>
                    </option>
                <?php endforeach; ?>
            </select>

            <label>Evento Prenotato:</label>
            <select name="idEventoPren">
                <?php foreach($eventi as $e): ?>
                    <option value="<?php echo $e['CodEvento']; ?>">
                        <?php echo $e['Titolo']; ?>
                    </option>
                <?php endforeach; ?>
            </select>

            <label>Nuova Data Prenotazione:</label>
            <input type="date" name="nuovaDataPren" required>

            <input type="submit" name="btnModificaPrenotazione" value="Aggiorna Data Prenotazione">
        </form>
    </div>

</body>
</html>