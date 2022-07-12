package org.skou.functions.service;

import org.jetbrains.annotations.NotNull;
import org.skou.functions.model.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.function.Function;

@Component
public class EmailService implements Function<EmailDTO, String> {

    private static final String EMAIL_ADDRESS = "skouna3@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String apply(EmailDTO mail) {
        String response = "Success";
        try {
            send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
            response = "fail";
        }
        return response;
    }

    @NotNull
    @Override
    public <V> Function<V, String> compose(@NotNull Function<? super V, ? extends EmailDTO> before) {
        return Function.super.compose(before);
    }

    @NotNull
    @Override
    public <V> Function<EmailDTO, V> andThen(@NotNull Function<? super String, ? extends V> after) {
        return Function.super.andThen(after);
    }

    private void send(EmailDTO mail) throws MessagingException, MailException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(EMAIL_ADDRESS);
        mimeMessageHelper.setFrom(EMAIL_ADDRESS);
        mimeMessageHelper.setSubject("Sent From:" + mail.getFirstName() + " " + mail.getLastName() + " " + mail.getSubject());
        mimeMessageHelper.setText(mail.getMessage());
        mimeMessageHelper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);
    }

}