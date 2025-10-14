package com.Proyecto.Medic.MedicSalud.Controller;


import com.Proyecto.Medic.MedicSalud.Entity.Mail;
import com.Proyecto.Medic.MedicSalud.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/envio")
    public String envio (@RequestBody Mail mail){
        mailService.enviar(mail);
        return "El mail se a enviado";
    }

}
