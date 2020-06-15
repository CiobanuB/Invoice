package com.tim04.school.facturing.controller.sign;

import com.tim04.school.facturing.persistence.user.User;

import com.tim04.school.facturing.user.tokenService.VerificationTokenService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SignController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @GetMapping("/login")
    public String login(Model modelAndView) {
       User user = new User();
        modelAndView.addAttribute("user", user);
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("Signing/Sign-in");
        return "Signing/Sign-in.html";
    }



    @GetMapping("/Sign-up")
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("Signing/Sign-up");
        return modelAndView;
    }

    @PostMapping("/Sign-up")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User existingUser = userService.findUserByEmail(user.getMail());
        if (existingUser != null) {
            bindingResult.rejectValue("mail", "error.user", "There is another mail registered ! ");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Signing/Sign-up");
        } else {
            userService.saveUser(user);
            verificationTokenService.createVerification(user);
            modelAndView.addObject("user", new User());
            modelAndView.addObject("successMessage", "Your account has been created ! Check your mail to activate it ! ");
            modelAndView.setViewName("Signing/Sign-up");
        }
        return modelAndView;
    }
  /*  @GetMapping("/verify-email")
    @ResponseBody
    public String emailVerification(String code,ModelAndView modelAndView) {
       *//* modelAndView.setViewName("");
        String validationMessage = VerificationTokenService.verifyEmail(code).getBody();
        modelAndView.addObject("verification", validationMessage);*//*
        modelAndView.setViewName("Signing/Email-verification");
        return verificationTokenService.verifyEmail(code).getBody();
    }*/
  @GetMapping("/verify-email")
  public ModelAndView emailVerification(String code) {
      ModelAndView modelAndView = new ModelAndView();
      String validationMessage = verificationTokenService.verifyEmail(code);
      modelAndView.addObject("validation", validationMessage);
      modelAndView.setViewName("Signing/Email-verification");
      return modelAndView;
  }
    @GetMapping("/logout")
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Signing/login");
        return modelAndView;
    }

}
