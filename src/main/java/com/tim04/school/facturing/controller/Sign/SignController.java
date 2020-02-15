package com.tim04.school.facturing.controller.Sign;

import com.tim04.school.facturing.persistence.user.User;
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

    @GetMapping("/login")
    public String login(Model modelAndView) {
//        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addAttribute("user", user);
//        modelAndView.addObject("user", user);
//        modelAndView.setViewName("Signing/Sign-in");
        return "Signing/Sign-in.html";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute User user) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("user", user);
        modelAndView.setViewName("Signing/Sign-in");
        return modelAndView;
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
            modelAndView.addObject("successMessage", "User has been created succesfully !");

            modelAndView.addObject("user", new User());
            modelAndView.setViewName("Signing/Sign-up");

        }
        return modelAndView;
    }
}
