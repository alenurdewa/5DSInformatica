<?php

$utentiPassword = [
    "admin" => "admin",
    "studente" => "password1",
    "docente"=> "password2",
];



$nome = $_POST["nome"] ?? "Not given";
$email = $_POST["email"] ?? "Not given";




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
    <h1>Login</h1>
    <form action="informazioni.php" method="post"></form>
    <input type="text" name="nome">
    <input type="email" name="mail">
    <input type="submit" name="invio">


</body>
</html>



</html>