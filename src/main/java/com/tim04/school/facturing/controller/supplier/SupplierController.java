package com.tim04.school.facturing.controller.supplier;

import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.mailProperties.MailProperties;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.tokenService.MailPropService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("company-profile")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;
    @Autowired
    private MailPropService mailPropService;

    @GetMapping
    public ModelAndView company() {
        ModelAndView modelAndView = new ModelAndView();


        Optional<Supplier> optionalSuppplier = Optional.ofNullable(supplierService.getTheSupplier());
        Supplier supplier = optionalSuppplier.orElse(new Supplier());
        modelAndView.addObject("supplier", supplier);

        modelAndView.setViewName("Profile/company-profile");
        return modelAndView;
    }

    @PostMapping(value = "/supplierForm")
    public ModelAndView companyProfile(@ModelAttribute(value = "supplier") Supplier supplier, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Supplier getSupplier = supplierService.getTheSupplier();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Profile/company-profile");
        } else{
            if (getSupplier != null) {
                supplierService.updateSupplier(supplier);
                redirectAttributes.addFlashAttribute("successMessage", "Company profile has been succesfully modified !");
                redirectAttributes.addAttribute("supplier", supplier);
                modelAndView.setViewName("redirect:/company-profile");
            } else {
                supplierService.save(supplier);
                redirectAttributes.addFlashAttribute("successMessage", "Company profile has been succesfully added !");
                redirectAttributes.addAttribute("supplier", supplier);
                modelAndView.setViewName("redirect:/company-profile");
            }
        }

        return modelAndView;
    }
  /*  @PostMapping(value = "/mailPropForm")
    public ModelAndView companyProfile(@ModelAttribute(value = "mailProperties") MailProperties mailProperties, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<MailProperties> optionalMail = mailPropService.findByUsername(mailProperties.getUsername());
        MailProperties findMail = mailPropService.getMailProperties();

        *//*if (optionalMail.isPresent()) {
            bindingResult.rejectValue("from", "error.mailProperties", "There is another mail with this name");
        }*//*
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Profile/company-profile");
        } else {
            if (findMail != null) {
                mailPropService.updateFields(mailProperties);
                redirectAttributes.addFlashAttribute("mailPropValidation", "Company profile has been succesfully modified !");
                redirectAttributes.addAttribute("mailProperties", mailProperties);
                modelAndView.setViewName("redirect:/company-profile");
            } else {
                mailPropService.save(mailProperties);
                redirectAttributes.addFlashAttribute("mailPropValidation", "Company profile has been succesfully added !");
                redirectAttributes.addAttribute("mailProperties", mailProperties);
                modelAndView.setViewName("redirect:/company-profile");
            }
        }
        return modelAndView;
    }*/
}
