package com.tim04.school.facturing.controller.mailProperties;


import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.mailProperties.MailProperties;
import com.tim04.school.facturing.user.tokenService.MailPropService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("mail-properties")
public class MailPropertiesController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailPropService mailPropService;

    @GetMapping()
    public ModelAndView getMailProp() {
        ModelAndView modelAndView = new ModelAndView();
        Optional<MailProperties> findMailPropOptional = mailPropService.getMailProperties();
        MailProperties mailProp = mailPropService.getMailProp();

        Optional<MailProperties> optionalMailProperties = Optional.ofNullable(mailProp);
        MailProperties mailProperties = optionalMailProperties.orElse(new MailProperties());
        modelAndView.addObject("mailProperties", mailProperties);
        modelAndView.setViewName("MailProperties/mail-properties");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView saveMailProperties(@ModelAttribute(value = "mailProperties") MailProperties mailProperties, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<MailProperties> optionalMailProperties = mailPropService.getMailProperties();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("MailProperties/mail-properties");
        } else {
            if (optionalMailProperties.isPresent()) {
                mailPropService.updateFields(mailProperties);
                modelAndView.addObject("mailPropValidation", "Mail properties have been modified");
                modelAndView.addObject("mailProperties", mailProperties);
                modelAndView.setViewName("redirect:/company-profile");
            } else {
                mailPropService.save(mailProperties);
                modelAndView.addObject("mailPropValidation", "Mail properties have been added");
                modelAndView.addObject("mailProperties", mailProperties);
            }
            modelAndView.setViewName("MailProperties/mail-properties");
        }
        return modelAndView;
    }
}
