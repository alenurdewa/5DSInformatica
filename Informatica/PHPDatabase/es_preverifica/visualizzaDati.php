<?php
require "config.php";
require "db.php";


$sql = "SELECT * FROM PARTECIPANTE";
$datiPartecipanti = $pdo -> query($sql) -> fetchAll();

$sql = "SELECT * FROM EVENTO";
$datiEventi = $pdo -> query($sql) -> fetchAll();


$sql = "SELECT * FROM PRENOTAZIONE";
$datiPrenotazioni = $pdo->query($sql) -> fetchAll();


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


        .Partecipanti{
            border: 5px solid #0056b3;
            border-radius: 10px;
            padding: 10px;
            background-color: lightblue;
            
        }
        body{
            margin: 15px;
            padding: 5px;
        }

        </style>
    </head>
    <body>
        
        <a href="index.php">Ritorna alla home</a>
        <div class="Partecipanti">
        <h1>Partecipanti</h1>
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
        </div>






        <br>
        <h1>Eventi</h1>
        <table>
            <tr>
                <td>Nome evento</td>
                <td>Data evento</td>
            </tr>
            <?php foreach($datiEventi as $dataEvento) :?>
                <tr>
                    <td><?php echo $dataEvento["Titolo"] ?></td>
                    <td><?php echo $dataEvento["DataEvento"] ?></td>
                </tr>
            <?php endforeach?>
        </table>

        <br>
        <h1>Prenotazioni</h1>
        <table>
            <tr>
                <td>Nominativo</td>
                <td>Evento</td>
                <td>DataPrenotazione</td>
            </tr>
            
            <?php foreach($datiPrenotazioni as $datoPrenotazione) :?>
            <tr>
                <?php
                $sql = "SELECT Nome,Cognome FROM PARTECIPANTE WHERE CodPartecipante  =:codPart";
                $stmt = $pdo -> prepare($sql);
                $stmt -> bindParam(":codPart", $datoPrenotazione["CodPartecipante"]);
                $stmt->execute();
                $nomePartecipante = $stmt -> fetchAll();

                $sql = "SELECT Titolo FROM EVENTO WHERE CodEvento  =:codEv";
                $stmt = $pdo -> prepare($sql);
                $stmt -> bindParam(":codEv", $datoPrenotazione["CodEvento"]);
                $stmt->execute();
                $nomeEvento = $stmt -> fetchColumn();

                
            
                ?>

                <td><?php echo $nomePartecipante[0]['Nome'].' '.$nomePartecipante[0]['Cognome']?></td>
                <td><?php echo $nomeEvento?></td>
                <td><?php echo $datoPrenotazione['DataPrenotazione']?></td>
            </tr>
            <?php endforeach?>

        </table>

      
    </body>
    
</html>


