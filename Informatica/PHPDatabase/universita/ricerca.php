<?php
require_once 'db.php';

$risultati = [];
$cerca = $_GET['cognome'];
if(!isset($cerca)){
    $cerca='';
}

if ($cerca !== '') {
    $risultati = $pdo->query("SELECT * FROM studente WHERE cognome='$cerca'")->fetchAll();
}
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Ricerca Studente</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; }
    </style>
</head>
<body>
    <a href="tabelle.php">Torna all'elenco</a>
    <h1>Ricerca Studente</h1>
    
    <form method="GET" action="ricerca.php">
        <label>Cognome: </label>
        <input type="text" name="cognome" value="<?php echo $cerca; ?>">
        <button type="submit">Cerca</button>
    </form>

    <?php if ($cerca !== ''): ?>
        <h3>Risultati per: "<?php echo $cerca; ?>"</h3>
        <?php if (count($risultati) > 0): ?>
            <table>
                <tr>
                    <th>Matricola</th>
                    <th>Nome</th>
                    <th>Cognome</th>
                </tr>
                <?php foreach ($risultati as $s): ?>
                <tr>
                    <td><?php echo $s['matricola']; ?></td>
                    <td><?php echo htmlspecialchars($s['nome']); ?></td>
                    <td><?php echo htmlspecialchars($s['cognome']); ?></td>
                </tr>
                <?php endforeach; ?>
            </table>
        <?php else: ?>
            <p>Nessun match trovato per il cognome indicato.</p>
        <?php endif; ?>
    <?php endif; ?>

<?php if ($cerca!== ''):?>


    <?php else:?>
        <p>Nessun match trovato</p>
    <?php endif?>

</body>
</html>