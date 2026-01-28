<?php
require_once 'db.php';

if(isset($_GET['nomeStud']) and isset($_GET['cognomeStud'])){
    $cercaNome = $_GET['nomeStud'];
    $cercaCognome = $_GET['cognomeStud'];
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
    FROM studente
    JOIN frequenta ON studente.matricola = frequenta.matricola_studente
    JOIN corso ON frequenta.id_corso = corso.id
    WHERE studente.nome = :nome
    AND studente.cognome = :cognome
    ";
    $stmt = $pdo->prepare($sql);
    $corsi = $stmt->execute([':nome' => $cercaNome,':cognome' => $cercaCognome]);
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
        <h1>Visualizzazione dei corsi del singolo studente</h1>
        <form method="GET" action="visualizzaCorsiStud.php">
            <label for="cognomeStud">Inserire il cognome dello studente</label>
            <input required type="text" name="cognomeStud" id="cognomeStud" value=<?php echo $cercaCognome?>>
            <br>

            <label for="nomeStud">Inserire il nome dello studente</label>
            <input required type="text" name="nomeStud" id="nomeStud" value="<?php echo $cercaNome?>">
            
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