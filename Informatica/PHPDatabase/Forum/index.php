<?php
require "createDB.php";

$errorMSG = "/";

function checkUser($user, $psw){
    global $pdo;
    $userExists = false;



    $stmt = $pdo -> prepare("
    SELECT * FROM UTENTE 
    WHERE username = :username AND pwd = :pwd
    ");

    $stmt -> bindParam (":username", $user);
    $stmt -> bindParam (":pwd", $psw);
    $stmt -> execute();
    $risultato = $stmt -> fetchall();

    if (count($risultato) > 0) {
        $userExists = true;
    }
    return $userExists;

}


if(isset($_POST["user"])){
    $username = $_POST["user"];
    $Password = $_POST["Password"];

    if (checkUser($username, $Password)) {
        session_start();
        $_SESSION["username"] = $username;
        header ("location: homepage.php");
    } else {
        $errorMSG = "Utente o password non corretto";
    }

}

?>


<html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Pagina Login</h1>

    <form action="index.php" method="POST">
        <label for="user">Username</label>
        <input type="text" name="user" id="user">
        <br>
        <label for="Password">Password</label>
        <input type="text" name="Password" id="Password">
        <br>
        <input type="submit" name="invio" id="invio">

    </form>
    <p style="color: red">
    <?php
        if ($errorMSG != "/") {
            echo $errorMSG;
        }
    ?>
    </p>
</body>
</html>

</html>
