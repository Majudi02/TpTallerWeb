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