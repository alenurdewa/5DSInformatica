<?php
require "db.php";

$tabella = $_GET['tabella'];
if(!isset($tabella)){
    $tabella='studente';
}
$tabelle_valide = ['studente', 'docente', 'corso', 'insegna', 'frequenta'];

if (!in_array($tabella, $tabelle_valide)) {
    $tabella = 'studente';
}

$rows = $pdo->query("SELECT * FROM $tabella")->fetchAll();
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Visualizza Tabelle</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2 f2 f2; }
        div { margin-bottom: 20px; }
    </style>
</head>
<body>
    <h1>Gestione Università</h1>
    <div>
        <a href="tabelle.php?tabella=studente">Studenti</a> | 
        <a href="tabelle.php?tabella=docente">Docenti</a> | 
        <a href="tabelle.php?tabella=corso">Corsi</a> | 
        <a href="tabelle.php?tabella=insegna">Insegnamenti</a> | 
        <a href="tabelle.php?tabella=frequenta">Frequenze</a> | 
        <strong><a href="ricerca.php">Ricerca Studente</a></strong> | 
        <a href="visualizzaCorsiStud.php">Corsi studente</a> | 
        <a href="visualizzaCorsiDocenti.php">Corsi docenti</a> | 
    </div>

    <h2>Tabella: <?php echo $tabella; ?></h2>

    <?php if (count($rows) > 0): ?>
        <table>
            <thead>
                <tr>
                    <?php for ($i = 0; $i < count(array_keys($rows[0])); $i+=2): ?>
                        <th><?php echo array_keys($rows[0])[$i]; ?></th>
                    <?php endfor; ?>
                </tr>
            </thead>
            <tbody>
                <?php foreach ($rows as $row): ?>
                    <tr>
                        <?php for ($i = 0; $i < count($row)/2; $i++): ?>
                            <td><?php echo $row[$i]; ?></td>
                        <?php endfor; ?>
                    </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    <?php else: ?>
        <p>Nessun dato presente in questa tabella.</p>
    <?php endif; ?>
</body>
</html>