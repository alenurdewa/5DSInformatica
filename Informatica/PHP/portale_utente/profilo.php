<?php
session_start();

if (!isset($_SESSION["email"])) {
    header("Location: login.php");
    exit();
}

$email = $_SESSION["email"];
$ruolo = $_SESSION["ruolo"];
$browserInfo = $_SERVER['HTTP_USER_AGENT'];

if (isset($_GET["logout"])) {
    session_destroy();
    header("Location: login.php");
    exit();
}
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente</title>
</head>
<body>
    <h2>Benvenuto, <?php echo htmlspecialchars($email); ?>!</h2>
    <p><strong>Ruolo:</strong> <?php echo htmlspecialchars($ruolo); ?></p>

    <h3>Informazioni sul browser</h3>
    <p><?php echo htmlspecialchars($browserInfo); ?></p>

    <a href="?logout=1">Logout</a>
</body>
</html>
