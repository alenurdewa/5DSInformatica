<?php
include("config.php");

// 1️⃣ Cookie per contare le visite
if (isset($_COOKIE["visite_login"])) {
    $visite = $_COOKIE["visite_login"] + 1;
} else {
    $visite = 1;
}
setcookie("visite_login", $visite, time() + 3600); // dura 1 ora

// 2️⃣ Se l’utente ha inviato il form
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nome = trim($_POST["nome"]);
    $email = trim($_POST["email"]);
    $password = trim($_POST["password"]);

    // Controlli di validità base
    if (empty($nome) || empty($email) || empty($password)) {
        $errore = "Tutti i campi sono obbligatori.";
    } elseif (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $errore = "Email non valida.";
    } else {
        // Login corretto → salva dati in sessione
        $_SESSION["utente"] = [
            "nome" => $nome,
            "email" => $email,
        ];
        header("Location: segnalazione.php");
        exit();
    }
}
?>

<h1>Login</h1>
<?php if (isset($errore)) echo "<p style='color:red;'>$errore</p>"; ?>

<form method="POST">
    Nome: <input type="text" name="nome"><br>
    Email: <input type="text" name="email"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Accedi">
</form>

<p>Visite a questa pagina: <?= $visite ?></p>
