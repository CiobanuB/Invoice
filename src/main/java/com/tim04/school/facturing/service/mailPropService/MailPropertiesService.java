package com.tim04.school.facturing.service.mailPropService;

import com.tim04.school.facturing.persistence.user.mailProperties.MailProperties;
import com.tim04.school.facturing.persistence.user.mailProperties.MailPropertiesRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MailPropertiesService {
    private final MailPropertiesRepository mailPropertiesRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public MailPropertiesService(MailPropertiesRepository mailPropertiesRepository) { this.mailPropertiesRepository = mailPropertiesRepository;}


    @Transactional
    public void save(MailProperties mailProperties){
        User loggedUser = userService.findLogged();
        mailProperties.setUser(loggedUser);
        mailPropertiesRepository.save(mailProperties);
    }

    public void updateMail(MailProperties mailProperties){
        User loggedUser = userService.findLogged();
        Optional<MailProperties> optionalMailProperties = mailPropertiesRepository.findByUser(loggedUser);
        mailProperties.setUser(loggedUser);
        MailProperties findMailProp = optionalMailProperties.get();

        findMailProp.setFromName(mailProperties.getFromName());
        findMailProp.setFrom(mailProperties.getFrom());
        findMailProp.setPassword(mailProperties.getPassword());
        findMailProp.setHost(mailProperties.getHost());
        findMailProp.setPort(mailProperties.getPort());
        findMailProp.setSmtp(mailProperties.getSmtp());
        findMailProp.setUsername(mailProperties.getUsername());
        mailPropertiesRepository.save(mailProperties);

    }


}
