package in.raj.utils;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailUtils {

    public boolean sendEmail(String subject, String body, String to, File file){
        // Logic to send email with attachment
        return true;
    }
}
