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
                body: `pedidoPlatoId=${pedidoPlatoId}`
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




    const botonesFinalizarPedido = document.querySelectorAll(".finalizar-pedido");
    botonesFinalizarPedido.forEach(boton => {
        boton.addEventListener("click", function () {
            const pedidoId = this.closest('.finalizar-pedido-completo').querySelector("input[name='pedidoId']").value;

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
                    const card = this.closest('.card');
                    if (card) {
                        card.remove();
                    }
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