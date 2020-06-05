package com.tim04.school.facturing.user.TokenService;

import com.tim04.school.facturing.persistence.user.ResetToken.ResetToken;
import com.tim04.school.facturing.persistence.user.ResetToken.ResetTokenRepository;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResetTokenService {
    @Autowired
    private UserService userService;
    @Autowired
    private ResetTokenRepository resetTokenRepository;
    @Autowired
    private MailPropService sendingMailService;

    public Optional findUserByResetToken(String resetToken) {
        return resetTokenRepository.findByUserMail(resetToken);
    }

    public void createResetPwdRequest(User user) throws NullPointerException{

        List<ResetToken> listResetTokens = resetTokenRepository.findAllByUserMail(user.getMail());
        User findUser = userService.findUserByEmail(user.getMail());
        ResetToken resetToken = new ResetToken();
        if(listResetTokens.isEmpty()){
            resetToken.setUser(findUser);
            resetTokenRepository.save(resetToken);
        } else {
            resetToken = listResetTokens.get(0);
        }
        sendingMailService.sendResetPwdRequest(user.getMail(),resetToken.getToken());
    }
    public Optional findUserByToken(String token) {
        return resetTokenRepository.findUserByToken(token);
    }

    public ResetToken findByUser(User user) {
        return resetTokenRepository.findByUser(user);
    }
    public Optional findByToken(String token) {
        return resetTokenRepository.findByToken(token);
    }
    @Transactional
    public ResetToken save(ResetToken resetToken) {
        return resetTokenRepository.save(resetToken);
    }

/*    public String verifyResetToken(String token){
        List<ResetToken> resetTokens = resetTokenRepository.findAllByToken(token);
        String result;
        if (resetTokens.isEmpty()) {
            return result="Invalid reset token";
        }

        VerificationToken verificationToken = resetTokens.get(0);
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            return result="Your reset link has expired";
        }
        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationToken.getUser().setActive(true);
        resetTokenRepository.save(verificationToken);

        return result = "You have successfully verified your account !";
    }*/
}
