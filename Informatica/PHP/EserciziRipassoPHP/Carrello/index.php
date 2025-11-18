<?php
// prodotti finti
$prodotti = [
    "p1" => "Maglietta",
    "p2" => "Pantaloni",
    "p3" => "Scarpe"
];
?>

<h2>Negozio</h2>
<ul>
<?php foreach($prodotti as $id => $nome): ?>
    <li>
        <?= $nome ?>
        <form method="POST" action="carrello.php" style="display:inline">
            <input type="hidden" name="prodotto" value="<?= $id ?>">
            <button type="submit">Aggiungi al carrello</button>
        </form>
    </li>
<?php endforeach; ?>
</ul>

<a href="carrello.php">Vai al carrello</a>
