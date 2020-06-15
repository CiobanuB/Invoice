package com.tim04.school.facturing.user.tokenService;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.persistence.user.UserRepository;
import com.tim04.school.facturing.persistence.user.verificationToken.VerificationToken;
import com.tim04.school.facturing.persistence.user.verificationToken.VerificationTokenRepository;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VerificationTokenService {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private MailPropService sendingMailService;

    @Autowired
    public VerificationTokenService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailPropService sendingMailService){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.sendingMailService = sendingMailService;
    }

    public void createVerification(User newUser) throws NullPointerException{
        List<User> users = userRepository.findBymail(newUser.getMail());
        User user;
        if (users.isEmpty()) {
            user = new User();
            user.setMail(newUser.getMail());
            user.setFirstName(newUser.getFirstName());
            user.setActive(false);
            userService.saveUser(user);
        } else {
            user = users.get(0);
        }

        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserMail(newUser.getMail());
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = verificationTokens.get(0);
        }

        sendingMailService.sendVerificationMail(newUser.getMail(), verificationToken.getToken());
    }

    public String verifyEmail(String token){
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByToken(token);
        String result;
        if (verificationTokens.isEmpty()) {
           return result="Invalid request";
        }

        VerificationToken verificationToken = verificationTokens.get(0);
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
          return result="Your activation has expired";
        }
        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationToken.getUser().setActive(true);
        verificationTokenRepository.save(verificationToken);

        return result = "You have successfully verified your account !";
    }
}
