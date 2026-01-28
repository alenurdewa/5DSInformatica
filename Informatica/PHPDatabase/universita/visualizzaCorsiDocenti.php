<?php
require_once 'db.php';

if(isset($_GET['nomeDoc']) and isset($_GET['cognomeDoc'])){
    $cercaNome = $_GET['nomeDoc'];
    $cercaCognome = $_GET['cognomeDoc'];
}

if(!isset($cercaNome)){
    $cercaNome='';
}
if(!isset($cercaCognome)){
    $cercaCognome='';
}

if($cercaNome != "" and $cercaCognome != ""){
    $sql = "
    SELECT corso.nome, corso.anno
    FROM docente
    JOIN insegna ON docente.matricola = insegna.matricola_docente
    JOIN corso ON insegna.id_corso = corso.id
    WHERE docente.nome = :nome
    AND docente.cognome = :cognome
    ";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([':nome' => $cercaNome, ':cognome' => $cercaCognome]);
    $corsi = $stmt->fetchAll();
}
?>
<html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        table, th, td {
            border: 1px solid #000;
        }

        th, td {
            padding: 8px;
        }
    </style>
</head>
<body>
    <a href="tabelle.php">Torna all'elenco</a>
    <h1>Visualizzazione dei corsi del singolo docente</h1>

    <form method="GET" action="visualizzaCorsiDocenti.php">
        <label for="cognomeDoc">Inserire il cognome del docente</label>
        <input required type="text" name="cognomeDoc" id="cognomeDoc" value="<?php echo $cercaCognome ?>">
        <br>

        <label for="nomeDoc">Inserire il nome del docente</label>
        <input required type="text" name="nomeDoc" id="nomeDoc" value="<?php echo $cercaNome ?>">
        <br>

        <input type="submit" name="invio" id="invio">
    </form>

    <?php if($cercaNome != "" and $cercaCognome != "" and count($corsi)==0): ?>
        <h2>Nessun risultato trovato per <?php echo "$cercaCognome $cercaNome"; ?></h2>

    <?php elseif($cercaNome != "" and $cercaCognome != "" and count($corsi)>0): ?>
        <h1>Risultati per <?php echo "$cercaCognome $cercaNome"; ?></h1>
        <table>
            <tr>
                <td><strong>Nome Corso</strong></td>
                <td><strong>Anno Corso</strong></td>
            </tr>
            <?php foreach($corsi as $corso): ?>
                <tr>
                    <td><?php echo $corso['nome'];?></td>
                    <td><?php echo $corso['anno'];?></td>
                </tr>
            <?php endforeach?>  
        </table>
    <?php endif?>  
</body>
</html>
