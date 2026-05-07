<?php
require "createDB.php";
session_start();
if(!isset($_SESSION["username"])){
    header("location:index.php");
}

$queryPost = "SELECT 
                POST.*, 
                ARGOMENTO.nome AS nome_argomento, 
                UTENTE.username AS nome_utente 
              FROM POST 
              JOIN ARGOMENTO ON POST.argomento = ARGOMENTO.id
              JOIN UTENTE ON POST.utente = UTENTE.username
              ORDER BY POST.data DESC"; 
$posts = $pdo->query($queryPost)->fetchAll();


$queryArg = "SELECT * FROM ARGOMENTO";
$arguments = $pdo ->query($queryArg) ->fetchAll();

if (isset($_POST["argomento"])) {
    $idArg = $_POST["argomento"];
    $testo = $_POST["testo"];
    $utente = $_SESSION["username"];
    $date = date("Y-m-d H:i:s");
    
    $stmt = $pdo ->prepare ("INSERT INTO POST (argomento, testo, utente, data) VALUES (:argomento, :testo, :utente, :currentDate)");
    $stmt ->bindParam(":argomento", $idArg);
    $stmt ->bindParam(":testo", $testo);
    $stmt ->bindParam(":utente", $utente);
    $stmt ->bindParam(":currentDate", $date);

    $stmt -> execute();
    
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Negro</title>
</head>
<body>
    <h1>homepage</h1>
    <div>
        <form method="post">
            <label for="argomento">Argomento: </label>
            <select name="argomento" id="argomento">
                <?php
                foreach ($arguments as $arg) {
                    echo "<option value=\"".$arg["id"]."\">".$arg["nome"]."</option>";
                } 
                ?>
            </select>

            <label for="testo">Testo: </label>
            <textarea name="testo" id="testo" rows="5" cols="100"></textarea>
            <button value="submit"><p>Crea post</p></button>
        </form>
    </div>

    <?php foreach($posts as $post){
        echo "<div>
        <h3>".$post['nome']."</h3>
        <h3>".$post['utente']."</h3>
        <h5>".$post['data']."</h5>
        <p>".$post['testo']."</p>

        </div>";
    }
    ?>

    
    


</body>
</html>