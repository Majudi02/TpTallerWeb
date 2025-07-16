document.addEventListener("DOMContentLoaded", function () {
    const forms = document.querySelectorAll(".agregar-plato-form");
    actualizarCarrito();

    forms.forEach(form => {
        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const platoId = form.querySelector('input[name="platoId"]').value;
            const nombrePlato = form.getAttribute('data-plato-nombre') || 'Plato';

            fetch("/pedido/agregar", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `platoId=${platoId}`
            }).then(response => {
                actualizarCarrito();
                mostrarPopup(`Se agregó "${nombrePlato}" al carrito.`);
            });
        });
    });
});

function mostrarPopup(mensaje) {
    let contenedor = document.getElementById('popup-container');
    if (!contenedor) {
        contenedor = document.createElement('div');
        contenedor.id = 'popup-container';
        contenedor.style.position = 'fixed';
        contenedor.style.top = '20px';
        contenedor.style.right = '20px';
        contenedor.style.zIndex = '9999';
        document.body.appendChild(contenedor);
    }

    const popup = document.createElement('div');
    popup.textContent = mensaje;
    popup.style.backgroundColor = '#38BD48';
    popup.style.color = 'white';
    popup.style.padding = '10px 20px';
    popup.style.marginTop = '10px';
    popup.style.borderRadius = '5px';
    popup.style.boxShadow = '0 2px 6px rgba(0,0,0,0.3)';
    popup.style.opacity = '1';
    popup.style.transition = 'opacity 0.5s ease';

    contenedor.appendChild(popup);

    setTimeout(() => {
        popup.style.opacity = '0';
        setTimeout(() => {
            if (contenedor.contains(popup)) {
                contenedor.removeChild(popup);
            }
        }, 500);
    }, 3000);
}

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
                    const plato = pedidoPlato.plato;
                    const precio = plato.precioConDescuento != null
                        ? plato.precioConDescuento
                        : plato.precio;

                    const item = document.createElement('li');
                    item.className = 'list-group-item d-flex justify-content-between align-items-center';
                    item.innerHTML = `
                        <div>
                            <span>${plato.nombre}</span>
                        </div>
                        <div class="d-flex align-items-center">
                            <span class="me-2">$${precio.toFixed(2)}</span>
                            <button type="button" class="btn btn-danger btn-sm eliminar-item" data-id="${plato.id}">
                                <i class="bi bi-trash"></i>
                            </button>
                        </div>
                    `;
                    listaCarrito.appendChild(item);
                    total += parseFloat(precio);
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
