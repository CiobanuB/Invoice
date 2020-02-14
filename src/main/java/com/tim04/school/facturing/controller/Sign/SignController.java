package com.tim04.school.facturing.controller.Sign;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SignController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
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
        if (existingUser != null){
            bindingResult.rejectValue("mail","error.user","There is another mail registered ! ");
        }

       if(bindingResult.hasErrors()){
           modelAndView.setViewName("Signing/Sign-up");
       } else {
           userService.saveUser(user);
           modelAndView.addObject("successMessage","User has been created succesfully !");

           modelAndView.addObject("user", new User());
           modelAndView.setViewName("Signing/Sign-up");

       }
        return modelAndView;
    }
}
