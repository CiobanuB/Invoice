package com.tim04.school.facturing.controller.Sign;

import com.tim04.school.facturing.persistence.user.ResetToken.ResetToken;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.TokenService.ResetTokenService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("change")
public class ChangePwdController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResetTokenService resetTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   /* @GetMapping
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token, RedirectAttributes redirectAttrs) {
        Optional<ResetToken> optional = resetTokenService.findByToken(token);
        ResetToken resetToken = optional.get();
        if (optional.isPresent()) {
            modelAndView.addObject("token", new User());
            modelAndView.setViewName("Signing/Change-password");
        } else if (resetToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            redirectAttrs.addFlashAttribute("errorToken", "Your activation link has expired. Please request another one");
            modelAndView.setViewName("redirect:/expired-token");
        }

        return modelAndView;
    }*/
    @GetMapping
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token, RedirectAttributes redirectAttrs) {
        Optional<ResetToken> optional = resetTokenService.findByToken(token);
        ResetToken resetToken = optional.get();
        User findUser = resetToken.getUser();
        if (optional.isPresent()) {
            modelAndView.addObject("token", token);

            modelAndView.addObject("user", findUser);
            modelAndView.setViewName("Signing/Change-password");
        } else if (resetToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            redirectAttrs.addFlashAttribute("errorToken", "Your activation link has expired. Please request another one");
            modelAndView.setViewName("redirect:/expired-token");
        }

        return modelAndView;
    }

 /*   @PostMapping()
    public ModelAndView recoverPassword( User user, @RequestParam Map<String,String> parameters, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        ModelAndView modelAndView = new ModelAndView();
        //Optional<User> optional = resetTokenService.findUserByResetToken(token);
        Optional<ResetToken> optionalReset = resetTokenService.findByToken(parameters.get("token"));
        ResetToken resetToken = optionalReset.get();
        User findUser = resetToken.getUser();
        if (findUser == null) {
            bindingResult.rejectValue("password", "error.password", "We could not change the password");
        }
        if (resetToken.getStatus() == "CHANGED") {
            bindingResult.rejectValue("password", "error.password", "You already changed the password with this request. Please make another request !");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("Signing/Change-password");
        } else {
            resetToken.setStatus(ResetToken.STATUS_CHANGED);
            resetToken.setConfirmedDateTime(LocalDateTime.now() );
            findUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.saveUser(findUser);
            resetTokenService.save(resetToken);
            modelAndView.addObject("validation","Your password has been succesfully changed !");
            modelAndView.setViewName("Signing/Change-password");
        }
        return modelAndView;
    }*/
 @PostMapping()
 public ModelAndView recoverPassword(@ModelAttribute(value = "user") User user, @RequestParam("token") String token, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
     ModelAndView modelAndView = new ModelAndView();
     //Optional<User> optional = resetTokenService.findUserByResetToken(token);

     Optional<ResetToken> optionalReset = resetTokenService.findByToken(token);
     ResetToken resetToken = optionalReset.get();
     User findUser = resetToken.getUser();
     if (findUser == null) {
         bindingResult.rejectValue("password", "error.password", "We could not change the password");
     }
     if (resetToken.getStatus() == "CHANGED") {
         bindingResult.rejectValue("password", "error.password", "You already changed the password with this request. Please make another request !");
     }
     if(bindingResult.hasErrors()){
         modelAndView.setViewName("Signing/Change-password");
     } else {
         resetToken.setStatus(ResetToken.STATUS_CHANGED);
         resetToken.setConfirmedDateTime(LocalDateTime.now() );
         findUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
         userService.saveUser(findUser);
         resetTokenService.save(resetToken);
         modelAndView.addObject("validation","Your password has been succesfully changed !");
         modelAndView.setViewName("Signing/Change-password");
     }
     return modelAndView;
 }
}
