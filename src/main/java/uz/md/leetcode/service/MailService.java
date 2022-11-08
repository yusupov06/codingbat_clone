
package uz.md.leetcode.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.md.leetcode.domain.ActivationCode;
import uz.md.leetcode.domain.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class MailService {


    private final Configuration configuration;

    private final JavaMailSender javaMailSender;

    private final ActivationCodeService activationCodeService;

    @Value("${activation.link.base.path}")
    private String activationLinkBasePath;

    @Async
    public void sendActivation(User user) throws MessagingException, TemplateException, IOException {

        ActivationCode activationCode = activationCodeService.generateCode(user);
        String activationLink = activationLinkBasePath + activationCode.getActivationCode();

        String subject = "Activation link for Trello";
        String senDTO = user.getEmail();
        Map<String, Object> model = Map.of(
                "username", user.getUsername(),
                "activation_link", activationLink
        );
        sendEmail(subject, senDTO, model, "activation.ftlh");
    }

    @Async
    public void sendEmail(String subject, String receiverEmail, Map<String, Object> model, String templateName) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setTo(receiverEmail);
        String emailContent = getEmailContent(model, templateName);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    private String getEmailContent(Map<String, Object> model, String templateName) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = configuration.getTemplate(templateName);
        template.process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}

