package com.tim04.school.facturing.controller;

import com.tim04.school.facturing.persistence.user.User;
import com.tim04.school.facturing.service.invoice.InvoiceService;
import com.tim04.school.facturing.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private InvoiceService invoiceService;

   @GetMapping
    public ModelAndView home() {
       ModelAndView modelAndView = new ModelAndView();
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userService.findUserByEmail(auth.getName());
       Integer numberToBeInvoiced = invoiceService.numberInvoiced();
       Integer numberClients = invoiceService.clientNumbers();
       modelAndView.addObject("numberToBeInvoiced",numberToBeInvoiced);
       modelAndView.addObject("clientNumbers",numberClients);
       modelAndView.setViewName("index");
       Map<String,Object> map = invoiceService.statisticsItems();
       modelAndView.addObject("map",map);
      /*  for(Map.Entry<String,Integer> entry : items.entrySet()){
            modelAndView.addObject(entry.getKey(),entry.getValue());
        }
*/
       return modelAndView;
   }
}
