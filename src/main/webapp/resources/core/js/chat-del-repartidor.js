let stompClient = null;

function conectarWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
        stompClient = null;
    }

    const socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/chat/pedido/' + pedidoId, function (contenido) {
            const msg = JSON.parse(contenido.body);
            const div = document.createElement('div');
            const texto = msg.contenido || '[mensaje vacío]';

            if (msg.remitenteId == remitenteId) {
                div.textContent = 'Vos: ' + texto;
            } else {
                div.textContent = 'Cliente: ' + texto;
            }

            document.getElementById('chat-mensajes').appendChild(div);
            scrollChat();
        });
    });
}

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


    new bootstrap.Modal(document.getElementById('chatModal')).show();


    fetch('/chat/mensajes/' + pedidoId)
        .then(response => response.json())
        .then(mensajes => {
            mensajes.forEach(mensaje => {
                const div = document.createElement('div');
                if (mensaje.remitenteId == remitenteId) {
                    div.textContent = 'Vos: ' + mensaje.contenido;
                } else {
                    div.textContent = 'Cliente: ' + mensaje.contenido;
                }
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
