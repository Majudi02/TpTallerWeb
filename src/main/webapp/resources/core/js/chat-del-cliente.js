let stompClient = null;
let remitenteId = null;
let destinatarioId = null;
let pedidoId = null;
let modalChat = null;

function conectarWebSocket() {
    const socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/chat/pedido/' + pedidoId, function (contenido) {
            const msg = JSON.parse(contenido.body);
            const div = document.createElement('div');
            const texto = msg.contenido || '[mensaje vacío]';
            div.textContent = (msg.remitenteId == remitenteId ? 'Vos: ' : 'Repartidor: ') + texto;
            document.getElementById('chat-mensajes').appendChild(div);
            scrollChat();
        });
    });
}

/*

function desconectarWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
        stompClient = null;
    }
}

 */

function abrirChat(event = null, btn = null) {
    if (event) {
        event.stopPropagation();
        event.preventDefault();
    }

    if (btn) {
        pedidoId = btn.getAttribute('data-pedido-id');
        destinatarioId = btn.getAttribute('data-repartidor-id');
        remitenteId = document.querySelector('main').getAttribute('data-cliente-id');
    }

    const chatMensajes = document.getElementById('chat-mensajes');
    chatMensajes.innerHTML = '';
    document.getElementById('mensaje-input').value = '';

    if (!modalChat) {
        modalChat = new bootstrap.Modal(document.getElementById('chatModal'));
    }
    modalChat.show();

    fetch('/chat/mensajes/' + pedidoId)
        .then(response => response.json())
        .then(mensajes => {
            mensajes.forEach(mensaje => {
                const div = document.createElement('div');
                div.textContent = (mensaje.remitenteId == remitenteId ? 'Vos: ' : 'Repartidor: ') + mensaje.contenido;
                chatMensajes.appendChild(div);
            });
            scrollChat();
        });

    conectarWebSocket();
}

function enviarMensaje() {
    const input = document.getElementById('mensaje-input');
    const contenido = input.value.trim();
    if (contenido && stompClient && stompClient.connected) {
        stompClient.send('/app/chat', {}, JSON.stringify({
            mensaje: contenido,
            remitenteId,
            destinatarioId,
            pedidoId
        }));
        input.value = '';
    }
}

function scrollChat() {
    const chatBox = document.getElementById('chat-mensajes');
    chatBox.scrollTop = chatBox.scrollHeight;
}

document.getElementById('chatModal').addEventListener('hidden.bs.modal', function () {
    desconectarWebSocket();
    pedidoId = null;
    destinatarioId = null;
    remitenteId = null;
    modalChat = null;
});