<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="index.php" method="POST">
        Inserire matricola: <input type="text" name ="matricola">
        <input type="submit">

    </form>

    
</body>
</html>


<?php
    $utenti = array("1" => "Alessandro", "2" => "Antonio", "3" => "Davide");
    $matricola = $_POST["matricola"];

    echo "Matricola inserita: " . htmlspecialchars($matricola) . "<br>";

    if (isset($utenti[$matricola])) {
        echo "Nome: " . $utenti[$matricola];
    } else {
        echo "Matricola non trovata.";
    }
    
    echo "<br><br>Tutti gli studenti";
    foreach ($utenti as $matricola => $nome) {
        echo "<br>$matricola - $nome";
    }


    function nomeFunzione(){

        return "sborro";
    }

?>

