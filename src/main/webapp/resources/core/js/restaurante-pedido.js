document.addEventListener("DOMContentLoaded", function () {
    const formsFinalizarPlato = document.querySelectorAll(".cambiar-estado-del-plato");

    formsFinalizarPlato.forEach(form => {
        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const pedidoPlatoId = this.querySelector("input[name='pedidoPlatoId']").value;

            fetch("/restaurante/finalizar-plato-pedido", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `pedidoPlatoId=${encodeURIComponent(pedidoPlatoId)}`
            })
                .then(response => {
                    const boton = this.querySelector("button[type='submit']");
                    boton.style.display = 'none';

                    const liPedidoPlato = this.closest('li');
                    if (liPedidoPlato) {
                        const badgeEstado = liPedidoPlato.querySelector('span.badge.ms-2');
                        if (badgeEstado) {
                            badgeEstado.textContent = "FINALIZADO";
                            badgeEstado.classList.remove('bg-warning', 'text-dark');
                            badgeEstado.classList.add('bg-success');
                        }
                    }

                    const pedidoCardBody = this.closest('.card-body');
                    if (pedidoCardBody) {
                        verificarPlatosFinalizados(pedidoCardBody);
                    }
                })
                .catch(error => {
                    alert("Error: " + error.message);
                });
        });
    });

    // SSE: Notificaciones de nuevo plato
    if (!!window.EventSource) {
        const source = new EventSource("/restaurante/notificaciones-pedidos");

        source.addEventListener("nuevo-plato", function (event) {
            mostrarNotificacion(event.data);
        });

        source.onerror = function () {
            console.error("Error en la conexión con SSE.");
            source.close();
        };
    }

    function mostrarNotificacion(mensaje) {
        const toastContainer = document.getElementById("toastContainer");
        const toastEl = document.createElement("div");
        toastEl.className = "toast align-items-center text-white bg-success border-0 show";
        toastEl.setAttribute("role", "alert");
        toastEl.setAttribute("aria-live", "assertive");
        toastEl.setAttribute("aria-atomic", "true");
        toastEl.innerHTML = `
            <div class="d-flex">
                <div class="toast-body">
                    ${mensaje}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>`;
        toastContainer.appendChild(toastEl);

        const bsToast = new bootstrap.Toast(toastEl);
        bsToast.show();

        setTimeout(() => toastEl.remove(), 5000);
    }

    // Evento para finalizar pedido completo
    const botonesFinalizarPedido = document.querySelectorAll(".finalizar-pedido");

    botonesFinalizarPedido.forEach(boton => {
        boton.addEventListener("click", function () {
            const pedidoId = this.closest('.finalizar-pedido-completo').querySelector("input[name='pedidoId']").value;
            const card = this.closest('.card');

            fetch("/restaurante/finalizar-pedido-completo", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `pedidoId=${pedidoId}`
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("No se pudo finalizar el pedido");
                    }

                    // Quitar pedido de la lista "Pedidos en preparación"
                    if (card) {
                        card.remove();
                    }

                    // Mostrar alerta si no hay más pedidos en preparación
                    const listaEnPreparacion = document.getElementById("lista-pedidos-en-preparacion");
                    if (listaEnPreparacion && listaEnPreparacion.children.length === 0) {
                        const alertaPrep = document.getElementById("contenedor-pedidos-en-preparacion");
                        if (alertaPrep) {
                            alertaPrep.innerHTML = `
            <div class="alert alert-warning text-center">
                No tenés pedidos en preparación por el momento.
            </div>`;
                        }
                    }

                    // Insertar el pedido en la sección "Pedidos listos para enviar"
                    const listaPedidosListos = document.getElementById("lista-pedidos-listos");
                    if (listaPedidosListos) {
                        const alertaListos = document.getElementById("contenedor-pedidos-listos");
                        if (alertaListos) {
                            alertaListos.innerHTML = "";
                        }

                        const nuevoPedidoHTML = `
        <div class="card mb-3 shadow-sm border-success">
            <div class="card-header bg-success text-white d-flex justify-content-between">
                Pedido ID: <span>${pedidoId}</span>
                <span class="badge bg-light text-success">LISTO PARA ENVIAR</span>
            </div>
            <div class="card-body">
                <ul class="list-group list-group-flush mb-3">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div>Todos los platos finalizados</div>
                        <span class="badge bg-success ms-2">FINALIZADO</span>
                    </li>
                </ul>
                <div class="text-end">
                    <p>Esperando a Repartidor</p>
                </div>
            </div>
        </div>`;
                        listaPedidosListos.insertAdjacentHTML('beforeend', nuevoPedidoHTML);
                    }

                    window.location.reload();

                })
                .catch(error => {
                    alert(error.message);
                });
        });
    });

    function verificarPlatosFinalizados(pedidoCardBody) {
        const badges = pedidoCardBody.querySelectorAll('.list-group-item .badge');
        const todosFinalizados = Array.from(badges).every(badge => badge.textContent.trim() === "FINALIZADO");

        if (todosFinalizados) {
            const finalizarBtnContainer = pedidoCardBody.querySelector('.finalizar-pedido-completo');
            if (finalizarBtnContainer) {
                finalizarBtnContainer.classList.remove('d-none');
            }
        }
    }
});

