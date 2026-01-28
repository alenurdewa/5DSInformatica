<?php
require("db.php");
require("config.php");
require("inserimentoDati.php");

$tipoDiInvio = "";
$nomePartecipante = "";
$cognomePartecipante = "";


$titoloEvento ="";
$dataEvento = "";

if(isset($_POST["nomePartecipante"]) and isset($_POST["cognomePartecipante"] )){
    $tipoDiInvio = "InserisciPartecipante";
    $nomePartecipante = $_POST["nomePartecipante"];
    $cognomePartecipante = $_POST["cognomePartecipante"];
    
};

if(isset($_POST["titoloEvento"]) and isset($_POST["dataEvento"] )){
    $tipoDiInvio = "InserisciEvento";
    $titoloEvento = $_POST["titoloEvento"];
    $dataEvento = $_POST["dataEvento"];
    
};


if(isset($_POST["nomePartecipante"]) and isset($_POST["CognomePartecipante"] )){
    $tipoDiInvio = "InserisciPartecipante";
};

if(isset($_POST["nomePartecipante"]) and isset($_POST["CognomePartecipante"] )){
    $tipoDiInvio = "InserisciPartecipante";
};

?>

<html>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sistema informativo</title>
    </head>
    <body>
        <h1>Sistema informativo</h1>

        <br>

        <h2>Inserire nuovo partecipante</h2>
        <form action="index.php" method="POST">
        
            <label for="nomePartecipante">Nome</label>
            <input type="text" name="nomePartecipante" id="nomePartecipante" value=<?php echo $nomePartecipante?>>
            <br>

            <label for="CognomePartecipante">Cognome</label>
            <input type="text" name="cognomePartecipante" id="cognomePartecipante" value=<?php echo $cognomePartecipante?>>
            <br>

            <input type="submit" name="invio" id="invio">

        </form>


        <h2>Inserire nuovo evento</h2>
        <form action="index.php" method="POST">
        
            <label for="dataEvento">Data</label>
            <input type="date" name="dataEvento" id="dataEvento">
            <br>

            <label for="titoloEvento">Titolo</label>
            <input type="text" name="titoloEvento" id="titoloEvento">
            <br>

            <input type="submit" name="invio" id="invio">

        </form>

        
        <?php
            if($tipoDiInvio === "InserisciPartecipante"){
                inserireNuovoPartecipante($nomePartecipante, $cognomePartecipante, $pdo);
                echo "Partecipante $nomePartecipante $cognomePartecipante inserito correttamente nel database";
            }else if($tipoDiInvio === "InserisciEvento"){
                inserireNuovoEvento($titoloEvento, $dataEvento, $pdo);
                echo "Evento $titoloEvento  alla data $dataEvento è stato inserito correttamente nel database";
            }

        ?>

    </body>
    </html>
</html>
