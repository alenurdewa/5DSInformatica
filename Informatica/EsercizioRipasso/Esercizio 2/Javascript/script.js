let list = document.getElementById("list")
let input = document.getElementById("itemInput")
let addBtn = document.getElementById("addBtn")

let spesa = localStorage.getItem("spesa")
if (!spesa) spesa = ""

let items //per contenere gli elementi della lista della spesa

if (spesa) {
  items = spesa.split("|")
} else {
  items = []
}

for (let i = 0; i < items.length; i++) {
  creaLi(items[i])
}

function creaLi(text) {
  let li = document.createElement("li")
  li.textContent = text
  //per far si che se schiaccio l'elemento si rimuova
  li.onclick = function() {
    let index = items.indexOf(text)
    if (index !== -1) {
        items.splice(index, 1)
    }
    let nuovaSpesa = items.join("|")
    localStorage.setItem("spesa", nuovaSpesa)
    list.removeChild(li)
  }
  list.appendChild(li)
}

function aggiungi() {
  if (input.value != "") {
    items.push(input.value)
    localStorage.setItem("spesa", items.join("|"))
    creaLi(input.value)
    input.value = ""
  }
}


