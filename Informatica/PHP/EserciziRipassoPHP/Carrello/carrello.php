<?php
// prendo il carrello esistente dal cookie
$carrello = isset($_COOKIE['carrello']) ? json_decode($_COOKIE['carrello'], true) : [];

// se arrivo da POST, aggiungo prodotto
if(isset($_POST['prodotto'])) {
    $p = $_POST['prodotto'];

    if(isset($carrello[$p])) {
        $carrello[$p] += 1; // aumento quantità
    } else {
        $carrello[$p] = 1;  // aggiungo prodotto nuovo
    }

    // riscrivo il cookie (7 giorni)
    setcookie('carrello', json_encode($carrello), time() + 7*24*60*60);
}

// prodotti per mostrare nomi
$prodotti_nome = [
    "p1" => "Maglietta",
    "p2" => "Pantaloni",
    "p3" => "Scarpe"
];
?>

<h2>Carrello</h2>
<?php if(empty($carrello)): ?>
    <p>Carrello vuoto :)</p>
<?php else: ?>
    <ul>
    <?php foreach($carrello as $id => $qt): ?>
        <li><?= $prodotti_nome[$id] ?> - Quantità: <?= $qt ?></li>
    <?php endforeach; ?>
    </ul>
<?php endif; ?>

<a href="index.php">Torna al negozio</a> | 
<a href="svuota.php">Svuota carrello</a>
