<?php
session_start();

$users = [
    "studente@example.com" => ["password" => "1234", "ruolo" => "Studente"],
    "docente@example.com" => ["password" => "abcd", "ruolo" => "Docente"],
    "admin@example.com"   => ["password" => "admin", "ruolo" => "Amministratore"]
];

if (isset($_SESSION["email"])) {
    header("Location: profilo.php");
    exit();
}

$error = "";
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    $email = $_POST["email"] ?? "";
    $password = $_POST["password"] ?? "";

    if (isset($users[$email]) && $users[$email]["password"] === $password) {
        $_SESSION["email"] = $email;
        $_SESSION["ruolo"] = $users[$email]["ruolo"];
        header("Location: profilo.php");
        exit();
    } else {
        $error = "Email o password errati!";
    }
}
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Login Utente</title>
</head>
<body>
    <h2>Login Utente</h2>
    <?php if ($error): ?>
        <p style="color:red;"><?php echo $error; ?></p>
    <?php endif; ?>

    <form method="POST" action="">
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <button type="submit">Accedi</button>
    </form>
</body>
</html>
