package com.tallerwebi.dominio;

public interface ServicioEmail {
    void enviarEmail(String destinatario, String asunto, String cuerpoMensaje);

}
