<?php
session_start();

// Se NON c'è la sessione, blocco l'accesso
if (!isset($_SESSION['user'])) {
    echo "Non sei loggato, torna al login :)<br>";
    echo '<a href="index.php">Vai al login</a>';
    exit;
}

echo "Ciao " . $_SESSION['user'] . " :)<br>";
echo "Benvenuto nell'area riservata<br><br>";

echo '<a href="logout.php">Logout</a>';
?>
