<?php
require "config.php";
require "db.php";


$sql = "SELECT * FROM PARTECIPANTE";
$datiPartecipanti = $pdo -> query($sql) -> fetchAll();

$sql = "SELECT * FROM EVENTO";
$datiEventi = $pdo -> query($sql) -> fetchAll();


?>

<html>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Visualizza Dati</title>
        <style> table {
            border-collapse: collapse;
            width: 100%;
        }

        table, th, td {
            border: 1px solid;
            padding: 8px;
        }
        </style>
    </head>
    <body>
        <table>
            <tr>
                <td>Nome</td>
                <td>Cognome</td>
            </tr>
            <?php foreach($datiPartecipanti as $partecipante) :?>
                <tr>
                    <td><?php echo $partecipante['Nome'] ?></td>
                    <td><?php echo $partecipante['Cognome'] ?></td>
                </tr>
                
            <?php endforeach?>
        </table>
        
    </body>
    </html>
</html>


