<?php
include("config.php");

// ✅ Funzione 1: conta le segnalazioni con priorità alta
function contaSegnalazioniAltaPriorita($segnalazioni) {
    $conteggio = 0;
    foreach ($segnalazioni as $s) {
        if ($s["priorita"] == "Alta") $conteggio++;
    }
    return $conteggio;
}

// ✅ Funzione 2: mostra un riepilogo in tabella
function riepilogoSegnalazioni($segnalazioni) {
    if (empty($segnalazioni)) {
        echo "<p>Nessuna segnalazione presente.</p>";
        return;
    }

    echo "<table border='1' cellpadding='5'>";
    echo "<tr><th>Laboratorio</th><th>Postazione</th><th>Descrizione</th><th>Priorità</th></tr>";
    foreach ($segnalazioni as $s) {
        echo "<tr>
            <td>{$s['laboratorio']}</td>
            <td>{$s['postazione']}</td>
            <td>{$s['descrizione']}</td>
            <td>{$s['priorita']}</td>
        </tr>";
    }
    echo "</table>";
}

// ✅ Funzione 3: mostra info tecniche dal server
function mostraInfoTecniche() {
    echo "<p><b>IP Client:</b> " . $_SERVER["REMOTE_ADDR"] . "</p>";
    echo "<p><b>Browser:</b> " . $_SERVER["HTTP_USER_AGENT"] . "</p>";
    echo "<p><b>Metodo richiesta:</b> " . $_SERVER["REQUEST_METHOD"] . "</p>";
}
?>

<?php
// Se non c'è login → rimanda al login
if (!isset($_SESSION["utente"])) {
    header("Location: login.php");
    exit();
}

// Inizializza array segnalazioni nella sessione
if (!isset($_SESSION["segnalazioni"])) {
    $_SESSION["segnalazioni"] = [];
}

// Aggiorna cookie di visite della pagina segnalazione
if (isset($_COOKIE["visite_segnalazione"])) {
    $visiteSegn = $_COOKIE["visite_segnalazione"] + 1;
} else {
    $visiteSegn = 1;
}
setcookie("visite_segnalazione", $visiteSegn, time() + 3600);

// Se l’utente invia una segnalazione
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $lab = $_POST["laboratorio"];
    $post = $_POST["postazione"];
    $desc = $_POST["descrizione"];
    $prio = $_POST["priorita"];
    $data = date("Y-m-d H:i:s");

    $nuova = [
        "laboratorio" => $lab,
        "postazione" => $post,
        "descrizione" => $desc,
        "priorita" => $prio,
        "data" => $data
    ];

    $_SESSION["segnalazioni"][] = $nuova;

    // Aggiorna cookie statistici
    setcookie("totale_segnalazioni", count($_SESSION["segnalazioni"]), time() + 3600);
    setcookie("ultimo_laboratorio", $lab, time() + 3600);
    setcookie("ultima_data", $data, time() + 3600);
}
?>

<h1>Segnalazione Guasti - <?= $nome_scuola ?></h1>
<p>Benvenuto, <?= $_SESSION["utente"]["nome"] ?> (<?= $_SESSION["utente"]["email"] ?>)</p>

<form method="POST">
    Laboratorio: <input type="text" name="laboratorio" value="<?= $laboratorio_predefinito ?>"><br>
    Postazione: <input type="text" name="postazione" required><br>
    Descrizione: <input type="text" name="descrizione" required><br>
    Priorità:
    <select name="priorita">
        <option>Bassa</option>
        <option>Media</option>
        <option>Alta</option>
    </select><br>
    <input type="submit" value="Invia Segnalazione">
</form>

<h2>Riepilogo Segnalazioni</h2>
<?php riepilogoSegnalazioni($_SESSION["segnalazioni"]); ?>

<p><b>Totale segnalazioni:</b> <?= $_COOKIE["totale_segnalazioni"] ?? 0 ?></p>
<p><b>Segnalazioni alta priorità:</b> <?= contaSegnalazioniAltaPriorita($_SESSION["segnalazioni"]) ?></p>
<p><b>Ultimo laboratorio segnalato:</b> <?= $_COOKIE["ultimo_laboratorio"] ?? "N/D" ?></p>
<p><b>Ultima data segnalazione:</b> <?= $_COOKIE["ultima_data"] ?? "N/D" ?></p>

<h2>Informazioni Tecniche</h2>
<?php mostraInfoTecniche(); ?>

<p><a href="logout.php">Logout</a></p>
