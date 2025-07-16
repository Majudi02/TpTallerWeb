document.addEventListener("DOMContentLoaded", function () {
    const inputBusqueda = document.getElementById("busqueda");
    const contenedorSugerencias = document.getElementById("sugerencias");

    inputBusqueda.addEventListener("input", function () {
        const texto = inputBusqueda.value.trim();

        if (texto.length < 2) {
            contenedorSugerencias.innerHTML = "";
            return;
        }

        fetch("/sugerencias?texto=" + encodeURIComponent(texto))
            .then(response => response.json())
            .then(data => {
                contenedorSugerencias.innerHTML = "";

                // data es la lista directa de sugerencias
                if (data.length > 0) {
                    data.forEach(sug => {
                        const btn = document.createElement("button");
                        btn.type = "button";
                        btn.className = "list-group-item list-group-item-action";
                        btn.textContent = sug.texto;

                        btn.addEventListener("click", function () {
                            if (sug.tipo === "plato") {
                                window.location.href = "/pedido/plato?id=" + sug.id;
                            } else if (sug.tipo === "restaurante") {
                                window.location.href = "/restaurantes/" + sug.id;
                            } else {
                                window.location.href = "/buscar?busqueda=" + encodeURIComponent(sug.texto);
                            }
                        });

                        contenedorSugerencias.appendChild(btn);
                    });
                }
            });
    });

    inputBusqueda.addEventListener("blur", function () {
        setTimeout(() => contenedorSugerencias.innerHTML = "", 200);
    });
});