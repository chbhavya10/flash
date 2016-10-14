package com.sermon.mynote.components;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;



@Service("emailService")
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private VelocityEngine templateEngine;

    
    
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(messageSource.getMessage("mail.from.address", null, null, null));
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.error("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }
    
    @Async
    public void forgotPassword(final String tomail, final String resetPasswordUrl, final String licenseeName) {
        String subject = messageSource.getMessage("forgot.password",null,null);
    	Map<String,Object> model = new HashMap<String,Object>();
    	model.put("licenseeName",licenseeName);
    	model.put("resetPasswordUrl",resetPasswordUrl);
        String content = VelocityEngineUtils.mergeTemplateIntoString(templateEngine, "vmtemplates/forgot-password.vm", CharEncoding.UTF_8,model);
        sendEmail(tomail, subject, content, false, true);
    }
    
}
