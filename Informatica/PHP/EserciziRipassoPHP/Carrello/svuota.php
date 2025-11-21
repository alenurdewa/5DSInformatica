<?php
setcookie('carrello', '', time() - 3600);
echo "Carrello svuotato :)<br>";
echo '<a href="index.php">Torna al negozio</a>';
