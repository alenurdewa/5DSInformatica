<?php
require "createDB.php";
session_start();
if(!isset($_SESSION["username"])){
    header("location:index.php");
}

// Logica di inserimento (Invariata)
if (isset($_POST["argomento"])) {
    $idArg = $_POST["argomento"];
    $testo = $_POST["testo"];
    $utente = $_SESSION["username"];
    $date = date("Y-m-d H:i:s");
    
    $stmt = $pdo->prepare("INSERT INTO POST (argomento, testo, utente, data) VALUES (:argomento, :testo, :utente, :currentDate)");
    $stmt->bindParam(":argomento", $idArg);
    $stmt->bindParam(":testo", $testo);
    $stmt->bindParam(":utente", $utente);
    $stmt->bindParam(":currentDate", $date);
    $stmt->execute();
    
    // Refresh per vedere il nuovo post subito ed evitare reinvii del form al ricaricamento
    header("Location: homepage.php");
    exit();
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
$arguments = $pdo->query($queryArg)->fetchAll();
?>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forum Homepage</title>
    <style>
        /* Reset e Font */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f2f5;
            color: #333;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 { color: #1c1e21; }

        /* Contenitore Form */
        .container-form {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
            margin-bottom: 30px;
        }

        .form-group { margin-bottom: 15px; }

        label { font-weight: bold; display: block; margin-bottom: 5px; }

        select, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box; /* Evita che escano dai bordi */
        }

        button {
            background-color: #1877f2;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover { background-color: #166fe5; }

        /* Stile dei Post */
        .post-card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
            margin-bottom: 20px;
            position: relative;
        }

        .post-card h3 { margin: 0 0 5px 0; color: #1c1e21; font-size: 1.2rem; }
        .post-card .meta { font-size: 0.85rem; color: #65676b; margin-bottom: 15px; }
        .post-card .badge {
            background: #e4e6eb;
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 0.75rem;
            font-weight: bold;
            text-transform: uppercase;
        }
        .post-card p { line-height: 1.5; white-space: pre-wrap; }
    </style>
</head>
<body>

    <h1>Homepage del Forum</h1>

    <div class="container-form">
        <form method="post">
            <div class="form-group">
                <label for="argomento">Argomento</label>
                <select name="argomento" id="argomento">
                    <?php foreach ($arguments as $arg): ?>
                        <option value="<?= $arg["id"] ?>"><?= htmlspecialchars($arg["nome"]) ?></option>
                    <?php endforeach; ?>
                </select>
            </div>

            <div class="form-group">
                <label for="testo">A cosa stai pensando?</label>
                <textarea name="testo" id="testo" rows="4" placeholder="Scrivi qui il tuo post..."></textarea>
            </div>

            <button type="submit">Pubblica Post</button>
        </form>
    </div>

    <div style="width: 100%; max-width: 600px;">
        <?php foreach($posts as $post): ?>
            <div class="post-card">
                <span class="badge"><?= htmlspecialchars($post['nome_argomento']) ?></span>
                <h3><?= htmlspecialchars($post['nome_utente']) ?></h3>
                <div class="meta">Pubblicato il: <?= date("d/m/Y H:i", strtotime($post['data'])) ?></div>
                <p><?= nl2br(htmlspecialchars($post['testo'])) ?></p>
            </div>
        <?php endforeach; ?>
    </div>

</body>
</html>