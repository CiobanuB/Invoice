package com.tim04.school.facturing.controller.User;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.user.MyUserDetailService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/my-profile")
public class UserProfileController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public ModelAndView getProfile() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUSer = auth.getName();
        User user = userService.findUserByEmail(currentUSer);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("Profile/my-profile");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView saveUpdateProfile(@ModelAttribute(value = "user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUSer = auth.getName(); //get logged in username
        User theUser = userService.findUserByEmail(currentUSer);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Profile/my-profile");
        } else {
            if (currentUSer != null ) {
                theUser.setFirstName(user.getFirstName());
                theUser.setLastName(user.getLastName());
                theUser.setAge(user.getAge());
                theUser.setPassword(user.getPassword());
                userService.saveUser(theUser);
                modelAndView.addObject("successMessage", "User Profile has been succesfully saved !");
                modelAndView.addObject("user", new User());
            }
                modelAndView.setViewName("Profile/my-profile");
            }
        return modelAndView;
    }
}
