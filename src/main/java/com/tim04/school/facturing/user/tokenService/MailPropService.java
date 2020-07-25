package com.tim04.school.facturing.user.tokenService;

import com.tim04.school.facturing.persistence.client.Client;
import com.tim04.school.facturing.persistence.invoice.invoice.Invoice;
import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.mailProperties.MailProperties;
import com.tim04.school.facturing.persistence.user.mailProperties.MailPropertiesRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
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
    private InvoiceService invoiceService;

    @Autowired
    public MailPropService(MailPropertiesRepository mailPropertiesRepository, Configuration templates) {
        this.mailPropertiesRepository = mailPropertiesRepository;
        this.templates = templates;
    }

    public Optional<MailProperties> getAdminMailProperties() {
        User user = userService.findUserByEmail("ciobanu.bogdann@gmail.com");
        //Optional<MailPropService> optional = mailPropertiesRepository.findByUser(user);
        Optional<MailProperties> optionalMailProperties = mailPropertiesRepository.findByUser(user);
        if (optionalMailProperties.isPresent()) return optionalMailProperties;
        return Optional.empty();
    }

    public boolean sendVerificationMail(String toEmail, String verificationCode) {
        Optional<MailProperties> optionalMailProperties = getAdminMailProperties();
        MailProperties mailProperties = optionalMailProperties.get();
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
        Optional<MailProperties> optionalMailProperties = getAdminMailProperties();
        MailProperties mailProperties = optionalMailProperties.get();
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
        Optional<MailProperties> optionalMailProperties = getAdminMailProperties();
        MailProperties mailProperties = optionalMailProperties.get();
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

    public boolean sendInvoiceMail(Invoice invoice) {
        Invoice findInvoice = invoiceService.findInvoice(invoice.getClientName());
        Client getClient = findInvoice.getClient();
        Supplier supplier = findInvoice.getSupplier();

        String subject = "Invoice" + supplier.getName();
        String body = "";
        try {
            Template t = templates.getTemplate("sendInvoice.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("contactPerson", getClient.getContactPerson());
            map.put("supplierCompany", supplier.getName());
            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return sendInvoiceAsUser(getClient.getMail(), subject, body, invoice);
    }

    private boolean sendInvoiceAsUser(String toEmail, String subject, String body, Invoice invoice) {
        Optional<MailProperties> optionalMailProperties = getMailProperties();
        MailProperties mailProperties = optionalMailProperties.get();
        User loggedUser = userService.findLogged();
        String path = loggedUser.getDefaultPath();
        String entirePath = path + invoice.getClientName() + invoiceService.getCurrentDate() + ".pdf";
        try {

            Properties props = System.getProperties();
            //props.put("mail.transport.protocol", mailProperties.getSmtp());
            props.put("mail.smtp.host", mailProperties.getHost());
            props.put("mail.smtp.port", mailProperties.getPort());
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.user", mailProperties.getUsername());
            props.put("mail.password", mailProperties.getPassword());
            System.out.println(mailProperties.getPassword());
            System.out.println(mailProperties.getUsername());

            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailProperties.getFrom(), mailProperties.getFromName()));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //msg.setSubject(subject);
            //msg.setContent(body, "text/html");

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            File generateFile = checkOrGenerate(invoice);
            // add atachments
            MimeBodyPart attachPart = new MimeBodyPart();
            try {
                attachPart.attachFile(generateFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            multipart.addBodyPart(attachPart);
            msg.setContent(multipart);

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
        mailProperties.setFromName(loggedUser.getFirstName());
        mailProperties.setUser(loggedUser);
        mailProperties.setPassword(bCryptPasswordEncoder.encode(mailProperties.getPassword()));
        mailProperties.setUsername(mailProperties.getFrom());
        mailPropertiesRepository.save(mailProperties);
    }

    @Transactional
    public void updateFields(MailProperties mailProperties) {

        User loggedUser = userService.findLogged();
        Optional<MailProperties> optionalMailProperties = mailPropertiesRepository.findByUser(loggedUser);

        if (optionalMailProperties.isPresent()) {
            MailProperties findProperties = optionalMailProperties.get();
            findProperties.setSmtp(mailProperties.getSmtp());
            findProperties.setHost(mailProperties.getHost());
            findProperties.setFromName(loggedUser.getFirstName());
            findProperties.setFrom(mailProperties.getFrom());
            findProperties.setUsername(mailProperties.getFrom());
            findProperties.setPassword(bCryptPasswordEncoder.encode(mailProperties.getPassword()));
            mailPropertiesRepository.save(findProperties);
        }

    }

    public File checkOrGenerate(Invoice invoice) {
        User loggedUser = userService.findLogged();
        String path = loggedUser.getDefaultPath();
        String entirePath = path + "\\" + invoice.getClientName() + " " + invoiceService.getCurrentDate() + ".pdf";
        File file = new File(entirePath);
        if (!file.isFile()) {
            invoiceService.generateReport(invoice);
        }
        return file;
    }

    public Optional<MailProperties> getMailProperties() {
        User loggedUser = userService.findLogged();
        Optional<MailProperties> optionalMailProperties = mailPropertiesRepository.findByUser(loggedUser);
        if (optionalMailProperties.isPresent()) return optionalMailProperties;
        return Optional.empty();
    }

    public Optional<MailProperties> findByUsername(String username) {
        Optional<MailProperties> optional = mailPropertiesRepository.findByUsername(username);
        if (!optional.isPresent()) return Optional.empty();
        return optional;
    }

    public MailProperties getMailProp() {
        User loggedUser = userService.findLogged();
        Optional<MailProperties> optionalMailProperties = mailPropertiesRepository.findByUser(loggedUser);
        if (optionalMailProperties.isPresent()) {
            return optionalMailProperties.get();
        }
        return null;

    }
}
