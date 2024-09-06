package com.coffee.event.listener;

import com.coffee.event.dao.UserDao;
import com.coffee.event.entity.User;
import com.coffee.event.event.SendEmailEvent;
import com.coffee.event.exceptions.CoffeeException;
import com.coffee.event.util.ErrorConstants;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailEventListener {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final UserDao userDao;

    @Async // Ensure this method is executed asynchronously
    @EventListener
    public void handleSendEmailEvent(SendEmailEvent event) {
        User user = event.getUser();
        sendEmail(user);
    }

    private void sendEmail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(user.getUserEmail());
            helper.setSubject("Event Registration");

            Context context = new Context();
            context.setVariable("user", user);

            String htmlContent = templateEngine.process("event-registration-email", context);
            helper.setText(htmlContent, true);
            helper.setFrom("your-email@gmail.com");

            mailSender.send(mimeMessage);

            // Update emailReceivedAt field and save user
            LocalDateTime now = LocalDateTime.now();
            user.setEmailReceivedAt(convertToDateViaInstant(now));
            // Save the user if necessary
             userDao.saveUser(user);

        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new CoffeeException(ErrorConstants.MAIL_SEND_ERROR_CODE, ErrorConstants.MAIL_SEND_ERROR_MESSAGE);
        }
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
