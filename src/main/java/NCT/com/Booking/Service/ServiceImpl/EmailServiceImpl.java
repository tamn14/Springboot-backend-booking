package NCT.com.Booking.Service.ServiceImpl;

import NCT.com.Booking.Service.ServiceInterface.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender ;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void SendMessage(String from, String to, byte[] qrBytes) {
        MimeMessage message =javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Vé may bay điện tử");
            helper.setText("<p>Xin chào,</p><p>Đây là vé của bạn:</p>" ) ;
            helper.addInline("qrCode", new ByteArrayResource(qrBytes), "image/png");
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        // thực hiện hành động gửi email
        javaMailSender.send(message);
    }
}
