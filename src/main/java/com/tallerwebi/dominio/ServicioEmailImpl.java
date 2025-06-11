package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.presentacion.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ServicioEmailImpl implements ServicioEmail {

    private JavaMailSender emailSender;
    private ServicioUsuario servicioUsuario;
    private RepositorioUsuarioNutriya repositorioUsuario;



    @Autowired
    public ServicioEmailImpl(JavaMailSender emailSender,ServicioUsuario servicioUsuario,RepositorioUsuarioNutriya repositorioUsuario){
        this.repositorioUsuario=repositorioUsuario;
        this.emailSender=emailSender;
        this.servicioUsuario=servicioUsuario;
    }


    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpoMensaje) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nutriya0@gmail.com");
            message.setTo(destinatario);
            message.setSubject(asunto);
            message.setText(cuerpoMensaje);

            emailSender.send(message);
            System.out.println("Correo enviado a: " + destinatario);
    }

}

