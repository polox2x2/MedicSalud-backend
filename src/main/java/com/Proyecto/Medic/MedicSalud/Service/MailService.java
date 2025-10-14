package com.Proyecto.Medic.MedicSalud.Service;


import com.Proyecto.Medic.MedicSalud.Entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService    {

    @Autowired
    private JavaMailSender javaMailSender;
    private final String from;

    public MailService(JavaMailSender mailSender,
                       @Value("${spring.mail.username}") String from) {
        this.javaMailSender = mailSender;
        this.from = from;
    }

    public void enviar (Mail mail){
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(mail.getTo());
        mensaje.setSubject(mail.getSubject());
        mensaje.setText(mail.getText());
        mensaje.setFrom(from);



        javaMailSender.send(mensaje);
    }
}
