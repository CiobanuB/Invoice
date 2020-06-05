package com.tim04.school.facturing.user.TokenService;

import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.MailProperties.MailProperties;
import com.tim04.school.facturing.persistence.user.MailProperties.MailPropertiesRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class MailPropService {

    @Autowired
    private UserService userService;
    private final MailPropertiesRepository mailPropertiesRepository;
    private final Configuration templates;
    @Autowired
    private Configuration freemarkerConfig;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MailPropService(MailPropertiesRepository mailPropertiesRepository, Configuration templates) {
        this.mailPropertiesRepository = mailPropertiesRepository;
        this.templates = templates;
    }

    public MailProperties getAdminMailProperties() {
        User user = userService.findUserByEmail("ciobanu.bogdann@gmail.com");
        //Optional<MailPropService> optional = mailPropertiesRepository.findByUser(user);
        MailProperties mailProperties = mailPropertiesRepository.findByUser(user);
        return mailProperties;
    }

    public boolean sendVerificationMail(String toEmail, String verificationCode) {
        MailProperties mailProperties = getAdminMailProperties();
        User user = userService.findUserByEmail(toEmail);
        String subject = "Please verify activate your account";
        String body = "";
        try {
            Template t = templates.getTemplate("email.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("VERIFICATION_URL", mailProperties.getVerificationapi() + verificationCode);
            map.put("firstname", user.getFirstName());
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return sendMailAsAdmin(toEmail, subject, body);
    }

    public boolean sendResetPwdRequest(String toEmail, String verificationCode) {
        MailProperties mailProperties = getAdminMailProperties();
        User user = userService.findUserByEmail(toEmail);
        String subject = "Reset your password account";
        String body = "";
        try {
            Template t = templates.getTemplate("reset-password.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("RESET_URL", mailProperties.getResetPwdApi() + verificationCode);
            map.put("firstname", user.getFirstName());
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return sendMailAsAdmin(toEmail, subject, body);
    }

    private boolean sendMailAsAdmin(String toEmail, String subject, String body) {
        MailProperties mailProperties = getAdminMailProperties();
        try {

            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", mailProperties.getPort());
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailProperties.getFrom(), mailProperties.getFromName()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject(subject);
            msg.setContent(body, "text/html");

            Transport transport = session.getTransport();
            transport.connect(mailProperties.getSmtp(), mailProperties.getUsername(), mailProperties.getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            return true;
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            System.out.println("Did not send the mail");
        }
        return false;
    }

    @Transactional
    public void save(MailProperties mailProperties) {
        User loggedUser = userService.findLogged();
        String entireName = loggedUser.getFirstName() + loggedUser.getLastName();
        mailProperties.setFromName(entireName);
        mailProperties.setUser(loggedUser);
        mailProperties.setPassword(bCryptPasswordEncoder.encode(mailProperties.getPassword()));
        mailProperties.setFrom(mailProperties.getUsername());
        mailPropertiesRepository.save(mailProperties);
    }
    @Transactional
    public void updateFields(MailProperties mailProperties) {

        User loggedUser = userService.findLogged();
        MailProperties findProperties = mailPropertiesRepository.findByUser(loggedUser);
        String entireName = loggedUser.getFirstName() + loggedUser.getLastName();
        findProperties.setFromName(entireName);
        findProperties.setFrom(mailProperties.getUsername());
        findProperties.setPassword(bCryptPasswordEncoder.encode(mailProperties.getPassword()));
        mailPropertiesRepository.save(findProperties);
    }

    /*public MailProperties getMailProperties() {
        User loggedUser = userService.findLogged();
        MailProperties theMailProp = mailPropertiesRepository.findByUser(loggedUser);
        return theMailProp;

    }*/
  /*   public Optional<MailProperties> getMailProperties() {
        User loggedUser = userService.findLogged();
        Optional<MailProperties> optional = mailPropertiesRepository.findByUser(loggedUser);
        if(!optional.isPresent())) return Optional.empty();

        return optional;

    }*/
       public MailProperties getMailProperties() {
        User loggedUser = userService.findLogged();
        //Optional<MailProperties> optional = mailPropertiesRepository.findByUser(loggedUser);
           MailProperties mailProperties = mailPropertiesRepository.findByUser(loggedUser);
        if(mailProperties == null) return null;

        return mailProperties;
    }
    public Optional<MailProperties> findByUsername(String username){
           Optional<MailProperties> optional = mailPropertiesRepository.findByUsername(username);
           if(!optional.isPresent()) return Optional.empty();
           return optional;
    }
}
