package com.tim04.school.facturing.controller.Supplier;

import com.tim04.school.facturing.persistence.supplier.Supplier;
import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.supplier.SupplierService;
import com.tim04.school.facturing.user.UserService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("company-profile")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ModelAndView company() throws FileNotFoundException, JRException {
        ModelAndView modelAndView = new ModelAndView();
        Supplier supplier = supplierService.findSupplierbyUserMail();
        modelAndView.addObject("supplier", supplier);
        modelAndView.setViewName("Profile/company-profile");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView companyProfile(@ModelAttribute(value = "supplier") Supplier supplier, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Profile/company-profile");
        } else {
            if( supplier != null) {
                Supplier theSupplier = supplierService.setFields(supplier);
                supplierService.save(theSupplier);
                modelAndView.addObject("successMessage", "Company profile has been succesfully modified !");
                modelAndView.addObject("supplier", supplier);
            }
            modelAndView.setViewName("Profile/company-profile");
        }
        return modelAndView;

    }
}
