# README: Spiegazione del codice PHP per la ricerca studenti

## Descrizione
Questo progetto contiene una semplice pagina PHP che permette di **ricercare studenti per cognome** in un database e mostrare i risultati in una tabella HTML. Il codice include una connessione al database tramite PDO, un form di ricerca e la visualizzazione dei risultati.

---

## 1. Connessione al Database
```php
require_once 'db.php';
```
- Include il file `db.php` che crea la connessione al database tramite PDO.
- L'oggetto `$pdo` viene utilizzato per eseguire query.

---

## 2. Preparazione dei dati della ricerca
```php
$risultati = [];
$cerca = $_GET['cognome'];
if(!isset($cerca)){
    $cerca='';
}
```
- `$risultati` è un array vuoto per memorizzare i risultati.
- `$cerca` prende il cognome dal parametro GET.
- Se non è settato, viene impostato come stringa vuota.

> ⚠ Nota: Meglio usare `empty()` per intercettare anche stringhe vuote.

---

## 3. Query al database
```php
if ($cerca !== '') {
    $risultati = $pdo->query("SELECT * FROM studente WHERE cognome='$cerca'")->fetchAll();
}
```
- Se `$cerca` non è vuoto, cerca nel database gli studenti con quel cognome.
- `fetchAll()` restituisce tutti i risultati.

> ⚠ **Pericolo SQL injection**: Usare query preparate è più sicuro:
```php
$stmt = $pdo->prepare("SELECT * FROM studente WHERE cognome = ?");
$stmt->execute([$cerca]);
$risultati = $stmt->fetchAll();
```

---

## 4. Form di ricerca
```html
<form method="GET" action="ricerca.php">
    <label>Cognome: </label>
    <input type="text" name="cognome" value="<?php echo $cerca; ?>">
    <button type="submit">Cerca</button>
</form>
```
- Permette all'utente di inserire il cognome.
- Il campo viene precompilato con il valore cercato.

---

## 5. Visualizzazione dei risultati
```php
<?php if ($cerca !== ''): ?>
    <h3>Risultati per: "<?php echo $cerca; ?>"</h3>
    <?php if (count($risultati) > 0): ?>
        <table>
            <tr>
                <th>Matricola</th>
                <th>Nome</th>
                <th>Cognome</th>
            </tr>
            <?php foreach ($risultati as $s): ?>
            <tr>
                <td><?php echo $s['matricola']; ?></td>
                <td><?php echo htmlspecialchars($s['nome']); ?></td>
                <td><?php echo htmlspecialchars($s['cognome']); ?></td>
            </tr>
            <?php endforeach; ?>
        </table>
    <?php else: ?>
        <p>Nessun match trovato per il cognome indicato.</p>
    <?php endif; ?>
<?php endif; ?>
```
- Controlla se l'utente ha cercato qualcosa.
- Mostra i risultati in tabella o un messaggio se non ci sono match.
- `htmlspecialchars()` previene attacchi XSS.

---

## 6. Stile CSS
```css
table { border-collapse: collapse; width: 100%; margin-top: 20px; }
th, td { border: 1px solid #ddd; padding: 8px; }
```
- Stile semplice per la tabella: bordi sottili e padding.

---

## 7. Note finali
- Questa pagina serve come esercizio base per la ricerca su database.
- **Sicurezza:** sempre usare query preparate con PDO e `htmlspecialchars()`.
- Permette di capire come gestire GET, query SQL, e visualizzazione dinamica in PHP.

---

**Autore:** Alessandro Berti

