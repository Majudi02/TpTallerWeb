document.addEventListener("DOMContentLoaded", function () {
    const forms = document.querySelectorAll(".agregar-plato-form");
    actualizarCarrito();
    forms.forEach(form => {
        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const platoId = form.querySelector('input[name="platoId"]').value;

            fetch("/pedido/agregar", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `platoId=${platoId}`
            }).then(response => {
                actualizarCarrito();
            })
        });
    });
});

function actualizarCarrito() {
    fetch("/pedido/carrito")
        .then(response => response.json())
        .then(data => {
            const listaCarrito = document.getElementById('lista-carrito');
            const precioTotalElemento = document.getElementById('precioTotal');
            const resumenNutricional = document.getElementById('resumen-nutricional');

            listaCarrito.innerHTML = '';
            let total = 0;

            const platos = data.platos;
            const totales = data.totales;

            if (platos.length === 0) {
                listaCarrito.innerHTML = '<li class="list-group-item text-center text-muted">El carrito está vacío.</li>';
                resumenNutricional.innerHTML = '';
                precioTotalElemento.textContent = `$0.00`;
            } else {
                platos.forEach(pedidoPlato => {
                    const item = document.createElement('li');
                    item.className = 'list-group-item d-flex justify-content-between align-items-center';
                    item.innerHTML = `
                    <div>
                        <span>${pedidoPlato.plato.nombre}</span>
                    </div>
                    <div class="d-flex align-items-center">
                        <span class="me-2">$${pedidoPlato.plato.precio.toFixed(2)}</span>
                        <button type="button" class="btn btn-danger btn-sm eliminar-item" data-id="${pedidoPlato.plato.id}">
                            <i class="bi bi-trash"></i>
                        </button>
                    </div>
                `;
                    listaCarrito.appendChild(item);
                    total += pedidoPlato.plato.precio;
                });


                resumenNutricional.innerHTML = `
                    <strong>Valores nutricionales</strong>
                    <ul style="list-style-type: none; padding-left: 0; margin: 0;">
                      <li>Calorías: ${totales.calorias.toFixed(0)} kcal</li>
                      <li>Proteínas: ${totales.proteinas.toFixed(1)} g</li>
                      <li>Carbohidratos: ${totales.carbohidratos.toFixed(1)} g</li>
                      <li>Grasas: ${totales.grasas.toFixed(1)} g</li>
                    </ul>
                    `;

                precioTotalElemento.textContent = `$${total.toFixed(2)}`;
            }
        });
}

document.addEventListener("click", function (e) {
    if (e.target.closest(".eliminar-item")) {
        const boton = e.target.closest(".eliminar-item");
        const platoId = boton.getAttribute("data-id");

        fetch("/pedido/eliminar", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `platoId=${platoId}`
        })
            .then(response => {
                actualizarCarrito();
            });
    }
});