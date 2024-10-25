package in.raj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    private Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    public boolean sendEmail(String subject, String body, String to, File f) {
        boolean isMailSent = false;
        try {
            MimeMessage mimeMsg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setTo(to);
            // For Attachment
            helper.addAttachment("IES-Elig-Notice",f);
            mailSender.send(mimeMsg);
            isMailSent = true;
        }catch (Exception e)
        {
           logger.error("Exception Occured",e);
        }
        return isMailSent;
    }
}
